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
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.db.*;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.localization.Messages;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.objects.JSObjOrder;
import com.sos.scheduler.model.objects.JSObjRunTime;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * \file FrequencyChecker.java
 * \brief check all nodes with state "not_processed" to decide if a node should run or not 
 *  
 * \class FrequencyChecker
 * \brief check all nodes with state "not_processed" to decide if a node should run or not 
 * 
 * \details
 * This class is reading all nodes of jobnets are not processed. For each node which is not a bootstrap order the class
 * decides if it should be processed or not. The decission is based on the runtime defintion for each node. If no runtime
 * definiton found for a node it will be processed anyway, that means the node in the database will not changed.
 * 
 * If a node has a runtime defintion it is necessary that the runtime is defined for the same day as the bootstrap order. Otherwise 
 * the node will marked with "isRunnerSkipped" in the database record of the jobnet plan.
 * 
 * It is not neccessary that the runtime defintion of a node has a period elements, e.g. 
 * <run_time let_run="no">
 *   <monthdays>
 *     <day day="20"/>
 *   </monthdays>
 * </run_time>
 * is a valid run_time defintion for a jobnet node.
 * 
 * The following run_time elements will be supported:
 * - monthdays/day
 * - monthdays/weekday
 * - weekdays/day
 * - ultimos/day
 * - period - that means every day
 * - date
 * 
 * \code
  \endcode
 *
 * \author Stefan Schaedlich
 * \version 1.0 - 20.03.2012 14:29:20
 */
public class FrequencyChecker {
	
	private final static Logger logger = Logger.getLogger(FrequencyChecker.class);
	private final static Logger testLogger = Logger.getLogger("frequencyCheckerTest");

	private final static DateTimeFormatter fmtDate = DateTimeFormat.forPattern("yyyy-MM-dd");
	private final static DateTimeFormatter fmtDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	
	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETCH_D_0001 = "JOBNETCH_D_0001"; // searching for start times of job net nodes from %1$s to %2$s
	private final static String JOBNETCH_D_0002 = "JOBNETCH_D_0002"; // bootstrap order has node id %2$s.
//	private final static String JOBNETCH_D_0003 = "JOBNETCH_D_0003"; // searching for starts at %2$s.
	private final static String JOBNETCH_D_0004 = "JOBNETCH_D_0004"; // the jobnet contains %1$s nodes.
	private final static String JOBNETCH_D_0008 = "JOBNETCH_D_0008"; // start time: %1$s.
	private final static String JOBNETCH_I_0001 = "JOBNETCH_I_0001"; // processing jobnet with id %1$s.
	private final static String JOBNETCH_I_0002 = "JOBNETCH_I_0002"; // order %1$s (node id %2$s) is already skipped.
	private final static String JOBNETCH_I_0003 = "JOBNETCH_I_0003"; // order %1$s (node id %2$s) has next start time for %3$s and will be skipped.
	private final static String JOBNETCH_I_0004 = "JOBNETCH_I_0004"; // order %1$s (node id %2$s) has no start time and will be NOT skipped.
	private final static String JOBNETCH_I_0005 = "JOBNETCH_I_0005"; // order %1$s (node id %2$s) has a start time for %3$s and will NOT skipped.
	private final static String JOBNETCH_I_0006 = "JOBNETCH_I_0006"; // order %1$s (node id %2$s) has to be started on demand.
	private final static String JOBNETCH_E_0001 = "JOBNETCH_E_0001"; // bootstrap order has no single_start
	private final static String JOBNETCH_E_0002 = "JOBNETCH_E_0002"; // no jobnet found for UUID %1$s.
	private final static String JOBNETCH_E_0003 = "JOBNETCH_E_0003"; // job net node with id $1%s not found.

	private final ObjectModelConnector connection;
	private final JobNetPlanDBLayer planDbLayer;
	private final JobNetNodeDBLayer nodeDbLayer;
	private final SchedulerObjectFactory factory;
	
	public FrequencyChecker(JobNetCreatorOptions options) {
		this.connection = ObjectModelConnector.getInstance(options.SCHEDULER_HOST, options.SCHEDULER_TCP_PORT, options.SCHEDULER_CONFIGURATION_DIRECTORY);
		this.factory = connection.getFactory();
		File configFile = new File(options.hibernate_connection_config_file.Value());
		this.planDbLayer = new JobNetPlanDBLayer(configFile, options.SCHEDULER_ID.Value());
		this.nodeDbLayer = new JobNetNodeDBLayer(configFile, options.SCHEDULER_ID.Value());
	}

	public  void processJobNet(JobNetPlanDBItem bootstrapOrder) {
		
		String uuid = bootstrapOrder.getUuid();
		logger.info(msg.getMsg(JOBNETCH_I_0001,uuid)); 							// processing jobnet with id %1$s.
		logger.debug(msg.getMsg(JOBNETCH_D_0002,bootstrapOrder.getNodeId())); 	// bootstrap order has node id %2$s.
		
		Date d = bootstrapOrder.getPlannedStartTime();
		if (d == null) {
			String msgText = msg.getMsg(JOBNETCH_E_0001); // bootstrap order has no single_start
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		DateTime from = new DateTime(d);
		from = from.minusMillis(from.getMillisOfDay());
		DateTime to = from.plusDays(1);				
		Interval baseInterval = new Interval(from, to);
		logger.debug(msg.getMsg(JOBNETCH_D_0001, fmtDateTime.print(from), fmtDateTime.print(to))); // searching for start times of job net nodes from %1$s to %2$s
		LocalDate forDay = new LocalDate(from);
		
//		logger.debug(msg.getMsg(JOBNETCH_D_0003,fmtDate.print(baseInterval.getStart()))); // searching for starts at %2$s.
		List<JobNetPlanDBItem> jobnetOrders = getJobnet(uuid);
		if (jobnetOrders == null) {
			String msgText = msg.getMsg(JOBNETCH_E_0002,uuid);
			logger.error(msgText); // no jobnet found for UUID %1$s.
			throw new JobSchedulerException(msgText);
		}
		
		logger.debug(msg.getMsg(JOBNETCH_D_0004,jobnetOrders.size())); // the jobnet contains %1$s nodes.
		factory.setUseDefaultPeriod(true);		// allows the use of runtime elements without or with incomplete periods

		planDbLayer.beginTransaction();
		for(JobNetPlanDBItem currentOrder : jobnetOrders) {
			JobNetNodeDBItem currentNode = getJobNetNode(currentOrder.getNodeId());
			if (!currentOrder.getBootstrap()) {		// the properties for the bootstrap order has to be set in JobNetPlanCreator
				if (currentOrder.getIsRunnerSkipped()) {
					logger.info(msg.getMsg(JOBNETCH_I_0002,currentNode.getNode(),currentOrder.getNodeId())); // order %1$s (node id %2$s) is already skipped.
				} else {
					JSObjOrder jsOrder = getJSObjOrder(currentOrder.getOrderXml());
					JSObjRunTime runTime = jsOrder.getJSObjRunTime();
					if (runTime.hasSubsequentRunTimes()) {
						LocalDate startDay = getNextStartDayInIntervalOrNull(runTime, baseInterval);
						if (startDay == null) {
							currentOrder.setIsRunnerSkipped(true);
							// order %1$s (node id %2$s) has no start time for %3$s and will be skipped.
							logger.info(msg.getMsg(JOBNETCH_I_0003,currentNode.getNode(),currentOrder.getNodeId(),fmtDate.print(forDay)));
						} else {
							// order %1$s (node id %2$s) has a start time for %3$s and will NOT skipped.
							logger.debug(msg.getMsg(JOBNETCH_I_0005,currentNode.getNode(),currentOrder.getNodeId(),fmtDate.print(forDay)));
							if ( currentOrder.getIsRunnerOnDemand() ) {
								// order %1$s (node id %2$s) has to be started on demand.
								logger.info(msg.getMsg(JOBNETCH_I_0006,currentNode.getNode(),currentOrder.getNodeId()));
								currentOrder.setIsRunnerOnDemand(true);
							}
						}
					} else {
						if (currentOrder.getIsRunnerOnDemand()) {
							// order %1$s (node id %2$s) has no start time and will be NOT skipped.
							currentOrder.setIsRunnerOnDemand(true);
							logger.info(msg.getMsg(JOBNETCH_I_0004,currentNode.getNode(),currentOrder.getNodeId())); 
						} else {
							logger.debug("nothing to do - order has neither a start_time nor the parameter on_demand.");
						}
					}
					logger.debug(currentOrder.getOrderXml().replace("\n", ""));
					planDbLayer.update(currentOrder);
					
				}
			}
			logRecord(currentNode,currentOrder);
		}
		planDbLayer.commit();
	}
	
	private JobNetNodeDBItem getJobNetNode(long nodeId) {
		JobNetNodeFilter filter = new JobNetNodeFilter();
		filter.setNodeId(nodeId);
		nodeDbLayer.setFilter(filter);
		List<JobNetNodeDBItem> result = nodeDbLayer.getJobnetNodeList(0);
		if (result == null || result.size() == 0) {
			String msgText = msg.getMsg(JOBNETCH_E_0003,nodeId);
			logger.error(msgText); // job net node with id $1%s not found.
			throw new JobNetException(msgText);
		}
		return result.get(0);
	}
	
	
	/**
	 * \brief gets a list of all jobnodes for a single jobnet indenticated with uuid
	 * @param uuid
	 * @return
	 */
	private List<JobNetPlanDBItem> getJobnet(String uuid) {
		JobNetPlanFilter filter = new JobNetPlanFilter();
		filter.setUuid(uuid);
		planDbLayer.setFilter(filter);
		return planDbLayer.getJobnetPlanList(0);
	}
	
	/**
	 * \brief get the next single start for an order
	 * @return
	 */
	private LocalDate getNextStartDayInIntervalOrNull(JSObjRunTime runTime, Interval forDay) {
		List<DateTime> startTimes = runTime.getDtSingleStarts(forDay);
		for(DateTime start : startTimes) {
			logger.debug(msg.getMsg(JOBNETCH_D_0008,fmtDateTime.print(start))); // start time: %1$s.
		}
		return (startTimes.size() == 0) ? null : new LocalDate(startTimes.get(0));
	}
	
	/**
	 * \brief gets a order object for an order given by xml
	 * @param orderXml
	 * @return
	 */
	private JSObjOrder getJSObjOrder(String orderXml) {
		JSObjOrder order = new JSObjOrder(factory);
		return order.getOrderFromXMLString(orderXml);
	}

	private void logRecord(JobNetNodeDBItem node, JobNetPlanDBItem record) {
		String message = node.getNode() + ";skipped=" + record.getIsRunnerSkipped().toString() + ";ondemand=" + record.getIsRunnerOnDemand().toString();
		testLogger.debug(message);
	}
}
