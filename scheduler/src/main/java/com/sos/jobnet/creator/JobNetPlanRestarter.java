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

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.classes.NodeStatus;
import com.sos.jobnet.db.EventsDBLayer;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnet.db.JobNetPlanDBLayer;
import com.sos.jobnet.db.JobNetPlanFilter;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

public class JobNetPlanRestarter extends JobNetPlanExecutor implements Runnable {
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id$";
	
	private final static int NOT_PROCESSED = NodeStatus.NOT_PROCESSED.getIndex();

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_E_0011 = "JOBNETC_E_0011"; // no jobnet plan found for uuid %1$s.
	private final static String JOBNETC_E_0012 = "JOBNETC_E_0012"; // jobnet plan with uuid %1$s has more than one bootstrap order.
	private final static String JOBNETC_E_0013 = "JOBNETC_E_0013"; // jobnet plan with uuid %1$s has no bootstrap order.

	private final static Logger logger = Logger.getLogger(JobNetPlanRestarter.class);
	
	private final JobNetPlanDBLayer planDbLayer;
	private final EventsDBLayer eventsDbLayer;
	private final String uuid;

	private JobNetPlanDBItem bootStrapOrder = null;

	public JobNetPlanRestarter(JobNetCreatorOptions options) {
		super(options);
		this.uuid = options.uuid_jobnet_identifier.Value();
		File configFile = new File(options.hibernate_connection_config_file.Value());
		this.planDbLayer = new JobNetPlanDBLayer(configFile, options.SCHEDULER_ID.Value());
		this.eventsDbLayer = new EventsDBLayer(configFile);
	}

	@Override
	public void run() {

		try {
			logger.info("Try to restart jobnet with id " + uuid);
			if(planDbLayer.isJobnetRunning(uuid)) {
				String msg = "Jobnet instance " + uuid + " is already running - could not restart.";
				logger.error(msg);
				throw new JobNetException(msg);
			}
			
			JobNetPlanDBItem bootStrapOrder = prepareForRestart();
			super.startOrderImmediately(bootStrapOrder);
		} catch(RuntimeException e) {
			throw e;
		} finally {
			planDbLayer.closeSession();
			eventsDbLayer.closeSession();
		}
	}
	
	private JobNetPlanDBItem prepareForRestart() {
		
		try {
			logger.info("Try to restart jobnet with id " + uuid);
			List<JobNetPlanDBItem> jobNetPlan = getJobNetPlan(uuid);
			
			planDbLayer.beginTransaction();
			for(JobNetPlanDBItem item : jobNetPlan) {
				resetJobNetPlanRecord(item);
			}
			if (bootStrapOrder == null) {		// no bootstrap order
				String msgText = msg.getMsg(JOBNETC_E_0013,uuid);
				logger.error(msgText);
				throw new JobNetException(msgText);
			}
			planDbLayer.commit();

			eventsDbLayer.beginTransaction();
			eventsDbLayer.deleteEventsForClass(uuid);
			eventsDbLayer.commit();
			
			logger.info("uuid " + uuid + " prepared for restart.");
			
		} catch(RuntimeException e) {
			String msg = "Error preparing jobnet instance " + uuid + " for restart.";
			logger.error(msg,e);
			throw new JobNetException(msg,e);
		}
		return bootStrapOrder;
	}
	
	private void resetJobNetPlanRecord(JobNetPlanDBItem item) {
		
		if (item.getBootstrap() ) {		// multiple bootstrap orders
			if (bootStrapOrder != null) {
				String msgText = msg.getMsg(JOBNETC_E_0012,uuid);
				logger.error(msgText);
				throw new JobNetException(msgText);
			}
			bootStrapOrder = item;
		}
		
		item.setMessage("");
		item.setExitCode(0L);
		item.setIsDispatcherSkipped(false);
		item.setIsRunnerSkipped(false);
		item.setIsWaiterSkipped(false);
		item.setStatusNode(NOT_PROCESSED);
		item.setStatusRunner(NOT_PROCESSED);
		item.setStatusWaiter(NOT_PROCESSED);
		planDbLayer.update(item);
	}
	
	private List<JobNetPlanDBItem> getJobNetPlan(String uuid) {
		JobNetPlanFilter filter = new JobNetPlanFilter();
		filter.setUuid(uuid);
		planDbLayer.setFilter(filter);
		List<JobNetPlanDBItem> list = planDbLayer.getJobnetPlanList(0);
		if (list.size() == 0) {
			String msgText = msg.getMsg(JOBNETC_E_0011,uuid);
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		return list;
	}
	
}
