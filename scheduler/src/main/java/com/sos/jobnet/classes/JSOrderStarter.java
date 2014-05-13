/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.jobnet.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

// import sos.spooler.Spooler;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionTransferType;
import com.sos.jobnet.interfaces.IMessageListener;
import com.sos.jobnet.options.SOSOptionStartType;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.answers.Answer;
import com.sos.scheduler.model.answers.ERROR;
import com.sos.scheduler.model.answers.JSCmdBase;
import com.sos.scheduler.model.commands.JSCmdAddOrder;
import com.sos.scheduler.model.objects.Param;
import com.sos.scheduler.model.objects.Params;
import com.sos.scheduler.model.objects.Spooler;

public class JSOrderStarter implements Runnable {
	
	private final Logger logger = Logger.getLogger(JSOrderStarter.class);

	private final SchedulerObjectFactory objectFactory;
	private final JSCmdAddOrder cmdAddOrder;
	private final String jobnetId;
	private final sos.spooler.Spooler spooler;
	
//	private boolean useObjectModel = false;
	private SOSOptionStartType.Type startType = SOSOptionStartType.Type.tcp;
	
	private List<IMessageListener> listener = new ArrayList<IMessageListener>();
	
	public JSOrderStarter(String host, Integer port, String jobChain, String uuid, sos.spooler.Spooler spooler) {
		this.objectFactory = new SchedulerObjectFactory(host, port);
		this.objectFactory.initMarshaller(Spooler.class);
		this.jobnetId = getJobNetIdFromUUID(uuid);
		this.cmdAddOrder = objectFactory.createAddOrder();
		this.cmdAddOrder.setJobChain(jobChain);
		this.spooler = spooler;
		logger.info("Starting order for uuid " + uuid +": " + jobChain);
	}
	
	private static String getJobNetIdFromUUID(String uuid) {
		int i = uuid.lastIndexOf(".");
		return (i == 0) ? uuid : uuid.substring(i+1);
	}
	
	public void setStartType(SOSOptionStartType startType) {
		String type = startType.Value().toLowerCase();
		try {
			this.startType = SOSOptionStartType.Type.valueOf(type);
		} catch (Exception e) {
			throw new JobNetException("The Starttype " + type + " is not valid (TCP, UDP or DIRECT are valid).");
		}
	}
	
	public void setStartType(String startType) {
		String type = startType.toLowerCase();
		try {
			this.startType = SOSOptionStartType.Type.valueOf(type);
		} catch (Exception e) {
			throw new JobNetException("The Starttype " + type + " is not valid (TCP, UDP or DIRECT are valid).");
		}
	}
	
//	public void useObjectModel(boolean useObjectModel) {
//		this.useObjectModel = useObjectModel;
//	}
	
	public String getJobnetId() {
		return jobnetId;
	}

	public void addParam(String name, String value) {
		Param p = objectFactory.createParam(name, value);
		if (cmdAddOrder.getParams() == null) {
			Params params = objectFactory.createParams();
			setParams(params);
		}
		cmdAddOrder.getParams().getParamOrCopyParamsOrInclude().add(p);
	}
	
	public void setParams(Params params) {
		cmdAddOrder.setParams(params);
	}
	
	public void setState(String state) {
		cmdAddOrder.setState(state);
	}
	
	public void setTitle(String title) {
		cmdAddOrder.setTitle(title);
	}
	
	public void setReplace(boolean replace) {
		cmdAddOrder.setReplace(cmdAddOrder.setYesOrNo(replace));
	}
	
//	public void setIdAndAddJobnetId(String id) {
//		orderId = id + JobNetConstants.JOBNET_ID_DELIMITER + jobnetId;
//	}
	
	public void setId(String id) {
		cmdAddOrder.setId(id);
	}
	
	public void setAt(String at) {
		cmdAddOrder.setAt(at);
	}
	
	@Override
	public void run() {

		notifyListener(Level.INFO,"Order will be started with TransferType " + startType);
		try{
			notifyListener(Level.INFO,String.format("Starting order: %1s",cmdAddOrder.getId()));
			if (startType == SOSOptionStartType.Type.direct)
				doCommandDirect(cmdAddOrder);
			else {
				objectFactory.Options().TransferMethod.Value(startType.name().toLowerCase());
				doCommand(cmdAddOrder);
			}
		} catch (Exception e) {
			String msg = "Error starting order " + cmdAddOrder.getId();
			notifyListener(Level.ERROR,msg);
			throw new JobSchedulerException(msg,e);
		}

	}

	public void doCommand(JSCmdBase command) {
		
		try{
			command.run();
			notifyListener(Level.INFO,command.toXMLString());
		} catch (Exception e) {
			String msg = "Error fetching command.";
			notifyListener(Level.ERROR,msg);
			throw new JobSchedulerException(msg,e);
		}
		
		notifyListener(Level.DEBUG,"Command submitted - waiting for answer ...");
		Answer objA = command.getAnswer();
		ERROR objE = objA.getERROR();
		notifyListener(Level.DEBUG,"JS answer received.");
		if (objE != null) {
			notifyListener(Level.ERROR,"The answer contains an error - order not started. Errortext from JS: " + objE.getText());
		}

		try {
			objectFactory.getSocket().doClose();
		} catch (IOException e) {
			String msg = "Error closing JobScheduler socket.";
			notifyListener(Level.ERROR,msg);
			throw new JobSchedulerException(msg,e);
		}

	}
	
	public void doCommandDirect(JSCmdBase command) {
		
		try {
			notifyListener(Level.INFO,command.toXMLString());
			String request = command.toXMLString();
			String response = spooler.execute_xml(request);
			notifyListener(Level.DEBUG,"JS answer received.");
			Answer answer = objectFactory.getAnswer(response);
			if (answer != null) {
				if (answer.getERROR() != null) {
					String msg = "Error fetching XML command." + "\n" + 
							"command:\n" + request + "\n" +
							"answer:\n" + response;
					notifyListener(Level.ERROR,msg);
					throw new JobSchedulerException(msg);
																							// an invalid or wrong command\n
				}
			}
		} catch (Exception e) {
			String msg = "Error starting order.";
			notifyListener(Level.ERROR,msg);
			throw new JobNetException(msg,e);
		}

	}
	
	
	private void notifyListener(Level level, String message) {
		if(listener.size() == 0) {
			logger.log(level,message);
		} else {
			for(IMessageListener l : listener)
				l.onMessage(level, message);
		}
	}
	
	public void addMessageListener(IMessageListener listener) {
		this.listener.add(listener);
	}
	
	public SchedulerObjectFactory getFactory() {
		return objectFactory;
	}
}
