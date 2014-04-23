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
package com.sos.jobnet.creator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sos.spooler.Spooler;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.classes.JSOrderStarter;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnet.interfaces.IMessageListener;
import com.sos.jobnet.interfaces.IStartOrder;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.jobnet.options.SOSOptionStartType;
import com.sos.localization.Messages;
import com.sos.scheduler.model.objects.JSObjOrder;
// import com.sos.scheduler.model.SchedulerObjectFactory;

/**
 * \file JobNetPlanExecutor.java
 * \brief creates a bootstrap order for every jobnet created with JobNetPlanCreator 
 *  
 * \class JobNetPlanExecutor
 * \brief creates a bootstrap order for every jobnet created with JobNetPlanCreator 
 * 
 * \details
 * The JobNetPlan creation for the given bootstrap order, which is called first in the run method, results 
 * one or more jobnet plans. For each plan an order is created in the JobScheduler by sending the 
 * command ADD_ORDER. 
 * \code
  \endcode
 *
 * \author Stefan Schaedlich
 * \version 1.0 - 03.04.2012 10:53:07
 */
public class JobNetPlanExecutor implements IMessageListener, IStartOrder {
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id$";
	
	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
//	private final static String JOBNETC_E_0010 = "JOBNETC_E_0010"; // order not started: %1$s.
//	private final static String JOBNETC_I_0011 = "JOBNETC_I_0011"; // order %1$s scheduled for %2$s.
	private final static String JOBNETC_E_0016 = "JOBNETC_E_0016"; // error creating the order for job net plan

	private final static Logger logger = Logger.getLogger(JobNetPlanExecutor.class);
//	private final static String YES = "yes";
	
//	private final ObjectModelConnector connection;
//	private final SchedulerObjectFactory factory;

//	private final String jobnetPrefix;
	private final String jobnetId;
//	private final String uuid;
	private final String host;
	private final int port;
	private final String jobChain;
	private final SOSOptionStartType startType;
	
	private List<String> commands = new ArrayList<String>();
	
	private IStartOrder orderStarter = null;
	
	public JobNetPlanExecutor(JobNetCreatorOptions options) {
		this.host = options.SCHEDULER_HOST.Value();
		this.port = options.SCHEDULER_TCP_PORT.value();
		this.startType = options.TransferType;
//		connection = ObjectModelConnector.getInstance(options.SCHEDULER_HOST, options.SCHEDULER_TCP_PORT, options.SCHEDULER_CONFIGURATION_DIRECTORY);
//		factory = connection.getFactory();
		this.jobnetId = options.JobNetId.Value();
		this.jobChain = options.JobChainName.Value();
//		this.jobnetPrefix = options.JobChainName.Value() + ".";
//		this.uuid = jobnetPrefix + jobnetId;
		String conMethodName = JobNetPlanExecutor.class.getSimpleName();
		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "JobNetId", jobnetId));
//		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "JobNetPrefix", jobnetPrefix));
//		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "UUID", uuid));
	}

	public void startOrderImmediately(JobNetPlanDBItem bootDBItem) {
		if (orderStarter != null)
			orderStarter.startOrderImmediately(bootDBItem);
		else
			logger.warn("No object of type IStartOrder is registered to start an order.");
		// startOrder(bootDBItem, true);
	}
	
	public void startOrder(JobNetPlanDBItem bootDBItem) {
		if (orderStarter != null)
			orderStarter.startOrder(bootDBItem);
		else
			logger.warn("No object of type IStartOrder is registered to start an order.");
		//startOrder(bootDBItem, false);
	}
	
	public void startOrderViaJobScheduler(Spooler spooler, JobNetPlanDBItem bootDBItem, boolean startImmediately) {
		
		String uuid = bootDBItem.getUuid();
		try {
			
			if (!bootDBItem.getBootstrap())
				throw new JobNetException("The order " + bootDBItem.getJobnetNodeDBItem().getJobnet() + " is not as bootstrap order");

			String jobChainWithPath = bootDBItem.getJobnetNodeDBItem().getJobnet();
			JSOrderStarter starter = new JSOrderStarter(host, port, jobChainWithPath, uuid, spooler);
			starter.setStartType(startType);

			JSObjOrder orderFromDB = new JSObjOrder(starter.getFactory());
			orderFromDB.getOrderFromXMLString(bootDBItem.getOrderXml());
			
			String id = orderFromDB.getId();
			String title = (orderFromDB.getTitle() == null) ? "<no title>" : orderFromDB.getTitle();
			logger.debug("Create order for UUID " + uuid + " with order id " + id );

			starter.addMessageListener(this);
			starter.setId(id);
			starter.setReplace(true);
			starter.setTitle(title);
			starter.setAt( (startImmediately) ? "now" : getStartTime(bootDBItem) );
			starter.setParams(orderFromDB.getParams());
			starter.run();
		} catch (Exception e) {
			String msgText = msg.getMsg(JOBNETC_E_0016);
			logger.error(msgText);
			throw new JobSchedulerException(msgText,e);
		}

	}
	
//	private void addParamToOrder(JSCmdAddOrder order, String name, String value) {
//		Param p = factory.createParam(name,value);
//		order.getParams().getParamOrCopyParamsOrInclude().add(p);
//		logger.info("Parameter set: " + name + "=" + value);
//	}
	
	private String getStartTime(JobNetPlanDBItem bootStrapDBItem) {
    	SimpleDateFormat df = new SimpleDateFormat(bootStrapDBItem.dateFormat);
		return df.format(bootStrapDBItem.getPlannedStartTime());
	}

	public List<String> getCommands() {
		return commands;
	}
	
//	public String getUUID() {
//		return uuid;
//	}

	@Override
	public void onMessage(Level level, String message) {
		logger.log(level, message);
	}
	
	public void registerOrderStarter(IStartOrder orderStarter) {
		this.orderStarter = orderStarter;
	}

}
