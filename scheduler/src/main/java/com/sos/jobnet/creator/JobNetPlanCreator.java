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
package com.sos.jobnet.creator;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.classes.*;
import com.sos.jobnet.db.*;
import com.sos.jobnet.graph.JobNetGraphBuilder;
import com.sos.jobnet.graph.JobNetGraphBuilderOptions;
import com.sos.jobnet.interfaces.IStartOrder;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.jobnet.options.SOSOptionStartJobnet;
import com.sos.localization.Messages;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.objects.JSObjRunTime;
import com.sos.scheduler.model.objects.Param;
import com.sos.scheduler.model.objects.Params;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sos.spooler.Spooler;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * \file JobNetPlanCreator.java
 * \brief creating a jobnet plan 
 *  
 * \class JobNetPlanCreator
 * \brief creating a jobnet plan
 * 
 * \details
 * This class assumes a complete jobnet stored in the database for the bootstrap order which the class is 
 * called for (see com.sos.jobnet.creator.JobNetCreator). Therefore the first thing calling the run method
 * is to execute the creation of the jobnet with the run method of com.sos.jobnet.creator.JobNetCreator.
 *
 * Based on this jobnet and the order definitions in the JobScheduler configuration a jobnet plan is created and
 * stored in the database in table SCHEDULER_JOB_NET_PLAN. Among other things the jobnet plan contains the 
 * complete configuration of the order. This is the base to execute the jobnet.
 * \code
  \endcode
 *
 * \author Stefan Schaedlich
 * \version 1.0 - 03.04.2012 10:12:21
 */
public class JobNetPlanCreator extends JobNetCreator implements Runnable, IStartOrder {
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetPlanCreator.java 21040 2013-09-09 16:51:14Z ss $";

	private final static Logger logger = Logger.getLogger(JobNetPlanCreator.class);

	private final static DateTimeFormatter fmtDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//	private final static DateTime now = new DateTime();
//	private final static DateTime onDemand = now.minusMillis(now.getMillisOfDay());

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_I_0010 = "JOBNETC_I_0010"; // job net plan created for order %1$s.
	private final static String JOBNETC_I_0012 = "JOBNETC_I_0012"; // creating jobnet with uuid %1$s.
//	private final static String JOBNETC_W_0001 = "JOBNETC_W_0001"; // the period %1$s does not represent a single start.
//	private final static String JOBNETC_E_0006 = "JOBNETC_E_0006"; // the bootstrap order %1$s contains no single_start.
	private final static String JOBNETC_E_0007 = "JOBNETC_E_0007"; // starttime %1$s is not parsable.
	private final static String JOBNETC_E_0008 = "JOBNETC_E_0008"; // the external uuid already exists.
	private final static String JOBNETC_E_0009 = "JOBNETC_E_0009"; // more than one starttime for an external uuid.
	private final static String JOBNETC_E_0015 = "JOBNETC_E_0015"; // error creating the jobnet plan
	private final static String JOBNETC_E_0018 = "JOBNETC_E_0018"; // error creating the jobnet graph

	private final Interval interval4StartTimes;
	private final boolean startOrder;
	private final boolean checkFrequency;
	private final SOSOptionStartJobnet restart;
	private final String jobChain;
	// private final String externalUUID;
	private final JobNetPlanExecutor executor;
	private final FrequencyChecker checker;
	private final String schedulerId;
	private final String jobnetId;
	private final String jobnetParameterName;
	private final File configFile;
	private final JobNetNodeDBLayer nodeDbLayer;
	private final JobNetHistoryDBLayer historyDbLayer;
	private final JobNetPlanDBLayer planDbLayer;
	private final EventsDBLayer eventsDbLayer;
	private final boolean createGraph;
	private final SchedulerObjectFactory liveObjectFactory;

	private List<JobNetPlanDBItem> plannedOrders = new ArrayList<JobNetPlanDBItem>();
	private List<String> collectedUUID = new ArrayList<String>();
	
	private IStartOrder orderStarter = null;

	public JobNetPlanCreator(JobNetCreatorOptions options) {
		super(options);
		DateTime from = new DateTime();
		DateTime to = new DateTime(options.TimeHorizon.getEndFromNow());
		this.interval4StartTimes = new Interval(from,to);
		this.startOrder = options.StartOrder.value();
		this.jobChain = options.JobChainName.Value();
		this.checkFrequency = options.CheckFrequency.value();
		this.executor =  new JobNetPlanExecutor(options);
		executor.registerOrderStarter(this);
		this.jobnetId = options.JobNetId.Value();
		this.jobnetParameterName = options.JobNetIdParameterName.Value();
		this.checker = new FrequencyChecker(options);
        this.schedulerId = getOptions().SCHEDULER_ID.Value();
		// this.externalUUID = (jobnetId.isEmpty()) ? "" : jobChain + JobNetConstants.JOBNET_ID_DELIMITER + jobnetId;
		this.createGraph = options.CreateGraph.value();
		this.restart = options.StartOption;
		this.configFile = new File(getOptions().hibernate_connection_config_file.Value());
		this.planDbLayer = new JobNetPlanDBLayer(configFile, schedulerId);
		this.historyDbLayer = new JobNetHistoryDBLayer(configFile, schedulerId);
		this.nodeDbLayer = new JobNetNodeDBLayer(configFile);
		this.eventsDbLayer = new EventsDBLayer(configFile);
		this.liveObjectFactory = options.getLiveConnector().getFactory();
		logger.info("Timeinterval to search for start times: " + fmtDateTime.print(from) + " to " + fmtDateTime.print(to));
	}

	@Override
	public void run() {
		
		try {

//			if (restart.isNormal()) {
//				JobNetPlanRestarter restarter = new JobNetPlanRestarter(getOptions());
//				restarter.registerOrderStarter(orderStarter);
//				restarter.run();
//			} else {
				runJobnet();
//			}

		} finally {
			planDbLayer.closeSession();
			eventsDbLayer.closeSession();
		}

	}

	private void runJobnet() {
		
//		if (restart.isAlways())
//			prepareRestart();		// delete all records in jobnet_plan

		createJobnetInstance();
		
		if (checkFrequency)
			for (JobNetPlanDBItem planItem : getPlannedOrders())
				checker.processJobNet(planItem);
		
		if (createGraph) {
			JobNetGraphBuilderOptions graphOptions;
			try {
				graphOptions = new JobNetGraphBuilderOptions(getOptions().Settings());
				for(String uuid : collectedUUID) {
					graphOptions.uuid_jobnet_identifier.Value(uuid);
					logger.info("graph will be created for uuid " + uuid);
					JobNetGraphBuilder graph = new JobNetGraphBuilder(graphOptions);
					graph.setBuildStatic(true);
					graph.run();
					graph.addToIndexFile(jobChain + graph.getExtension());
				}
			} catch (Exception e) {
				String msgText = msg.getMsg(JOBNETC_E_0018);
				logger.error(msgText);
				throw new JobSchedulerException(msgText,e);
			}
		}
		
		if (startOrder) {
			getBootstrapOrder().getJobNetJobChainNameWithPath();
			for (JobNetPlanDBItem planItem : getPlannedOrders()) {
				logger.info("Start order for id " + planItem.getOrderId());
				if (restart.isAlways())
					executor.startOrderImmediately(planItem);
				else
					executor.startOrder(planItem);
			}
		}

	}

    /*
	private void prepareRestart() {
		if (hasExternalUUID()) {
            planDbLayer.beginTransaction();
            eventsDbLayer.beginTransaction();
			planDbLayer.deleteJobnetInstance(externalUUID);
			eventsDbLayer.deleteEventsForClass(externalUUID);
            // eventsDbLayer.commit();
            planDbLayer.commit();  // includes commit for events table also
		}
	}
	*/
	
	private void createJobnetInstance() {

		try {
			super.run(); // ensures the representation of the jobNet table JOB_NET_NODES and JOB_NET_EDGES of the database
	
			/*
			 * In case of an external given uuid it has to be unique.
			if (hasExternalUUID()) {
				logger.info("Creating jobnnet instance with external id " + externalUUID);
				if (isJobNetInstance(planDbLayer, externalUUID)) {
					String msgText = msg.getMsg(JOBNETC_E_0008, externalUUID);
					logger.error(msgText);
					throw new JobSchedulerException(msgText);
				}
			}
			 */

			JobNetBootstrapOrder bootstrapOrder = getBootstrapOrder();
            historyDbLayer.beginTransaction();
            planDbLayer.beginTransaction();

			JSObjRunTime runTime = bootstrapOrder.getJSObjRunTime();
			JobNetPlanDBItem bootDBItem = null;
			if (bootstrapOrder.isOnDemandOrder()) {
				bootDBItem = createJobNetPlan4DemandOrderInDB(createUUID());
				logger.info("Order " + bootstrapOrder.getJobNetNodeName() + " will be start on demand");
				plannedOrders.add(bootDBItem);
			} else {
				logger.debug("hasPeriod? " + runTime.hasPeriod());
				logger.debug("hasAt? " + runTime.hasAt());
				logger.debug("hasDate? " + runTime.hasDate());
				logger.debug("hasWeekdays? " + runTime.hasWeekdays());
				logger.debug("hasMonth? " + runTime.hasMonth());
				logger.debug("hasMonthdays? " + runTime.hasMonthdays());
				logger.debug("hasUltimos? " + runTime.hasUltimos());
				if (runTime.hasSubsequentRunTimes()) {
					
					/*
					 * If the bootstrap order contains more than one start time, it is not possible to set the uuid external.
					 */
					List<DateTime> startTimes = runTime.getDtSingleStarts(interval4StartTimes);
                    if (startTimes.size() == 0) {
                        logger.info("No single starts found in runtime definition of order " + bootstrapOrder.getJobNetOrderName() + " - Order will be start immediately.");
                        bootDBItem = createJobNetPlan4OrderInDB(createUUID(), "now", 0);
                        logger.info("Order " + bootstrapOrder.getJobNetNodeName() + " will be start immediately");
                        plannedOrders.add(bootDBItem);
                    } else {
                        logger.info(startTimes.size() + " runtimes calculated for " + bootstrapOrder.getJobNetNodeName());
                        for (DateTime startTime : startTimes) {
                            String startString = fmtDateTime.print(startTime);
                            logger.info("Start time at " + startString);
                        }

                        /*
                        if (hasExternalUUID() && startTimes.size() > 1) {
                            logger.info("The external uuid is " + externalUUID);
                            String msgText = msg.getMsg(JOBNETC_E_0009);
                            logger.error(msgText);
                            throw new JobSchedulerException(msgText);
                        }
                        */

                        int cnt = 0;
                        for (DateTime startTime : startTimes) {
                            String startString = fmtDateTime.print(startTime);
                            bootDBItem = createJobNetPlan4OrderInDB(createUUID(), startString, cnt);
                            logger.info("Order " + bootstrapOrder.getJobNetNodeName() + " will be start at " + startString);
                            plannedOrders.add(bootDBItem);
                            cnt++;
                        }
                    }

				} else {
					bootDBItem = createJobNetPlan4OrderInDB(createUUID(), "now", 0);
					logger.info("Order " + bootstrapOrder.getJobNetNodeName() + " will be start immediately");
					plannedOrders.add(bootDBItem);
				}
			}
            // planDbLayer.commit();
            historyDbLayer.commit();

		} catch (Exception e) {
			String msgText = msg.getMsg(JOBNETC_E_0015);
			logger.error(msgText);
			throw new JobSchedulerException(msgText,e);
		} finally {
            planDbLayer.closeSession();
            historyDbLayer.closeSession();
        }

        /*
		try {
            planDbLayer.beginTransaction();
            for(String uuid : collectedUUID) {
                buildReferences(uuid);
            }
            planDbLayer.commit();

		} catch (Exception e) {
			String msgText = msg.getMsg(JOBNETC_E_0015);
			logger.error(msgText);
			throw new JobSchedulerException(msgText,e);
		} finally {
            planDbLayer.closeSession();
        }
        */

	}

	private String createUUID() {
        String baseId = (jobnetId.isEmpty()) ? UUID.randomUUID().toString() : jobChain + JobNetConstants.JOBNET_ID_DELIMITER + jobnetId;
        int subId = 0;
        String uuid = baseId;
        while (historyDbLayer.isJobNetInstance(uuid)) {
            logger.info("The uuid " + subId + " already exists, create a new one ...");
            subId++;
            uuid = baseId + "." + subId;
        }
        logger.info("The uuid is for the jobnet " + uuid);
        return uuid;
        // return (hasExternalUUID()) ? externalUUID : UUID.randomUUID().toString();
	}
	
	private JobNetPlanDBItem createJobNetPlan4DemandOrderInDB(String uuid) {
		String orderId = getUniqueOrderId(0);
		return createJobNetPlanAndWrite2Db(uuid, "now", true, orderId);
	}
	
	private JobNetPlanDBItem createJobNetPlan4OrderInDB(String uuid, String startTime, int orderIndex) {
		String orderId = getUniqueOrderId(orderIndex);
		return createJobNetPlanAndWrite2Db(uuid, startTime, false, orderId);
	}

    private JobNetHistoryDBItem createJobNetHistoryEntryAndWrite2Db(String uuid) {
        JobNetHistoryDBItem historyItem = new JobNetHistoryDBItem();
        historyItem.setSchedulerId(schedulerId);
        historyItem.setNetId(getNetId());
        historyItem.setUuid(uuid);
        historyItem.setStatus(JobNetStatus.NOT_PROCESSED.getIndex());
        historyItem.setExitCode(0);
        historyItem.setExitMessage("");
        historyItem.setJobNetId(jobnetId);
        historyItem.setJobNetIdParameterName(jobnetParameterName);
        historyItem.setCreated(new Date());
        historyItem.setCreatedBy(this.getClass().getName());
        historyDbLayer.save(historyItem);
        return historyItem;
    }
	
	private JobNetPlanDBItem createJobNetPlanAndWrite2Db(String uuid, String startTime, boolean isOnDemand, String orderId) {
		JobNetPlanDBItem bootStrap = null;
		logger.info(msg.getMsg(JOBNETC_I_0012,uuid));

        JobNetHistoryDBItem historyDBItem = createJobNetHistoryEntryAndWrite2Db(uuid);

        List<JobNetPlanDBItem> newRecords = new ArrayList<JobNetPlanDBItem>();
		for (JobNetOrder order : getBootstrapOrder().getJobNet().values()) {
			order.setId(orderId);
			JobNetPlanDBItem item = new JobNetPlanDBItem();
			item.setUuid(uuid);                                             // a unique id for the jobnet
            item.setHistoryId(historyDBItem.getHistoryId());                // a reference to the jobnet header in SCHEDULER_JOB_NET_HISTORY

            item.setOrderXml( addAdditionalParametersToOrderXml(order,uuid).toXMLString() );
            item.setJobnetNodeDBItem(order.getNodeDBItem());
            // item.setPlanId(order.getNodeDBItem().getNodeId());
            item.setNodeType(order.getNodeType().name());
            item.setNodeId(order.getNodeDBItem().getNodeId());
            item.setSubnetId(order.getSubnet().getNodeDBItem().getNodeId());
            item.setConnectorId(order.getConnector().getNodeDBItem().getNodeId());
            item.setScriptToExecute(order.getScriptToExecute());
            item.setUseErrorHandler(order.getUseErrorHandler());

            // runtime informations
            Integer NOT_PROCESSED = NodeStatus.NOT_PROCESSED.getIndex();
			item.setStatusNode(NOT_PROCESSED);
			item.setStatusWaiter(NOT_PROCESSED);
			item.setStatusRunner(NOT_PROCESSED);
			item.setStatusDispatcher(NOT_PROCESSED);
            item.setExitCode(0L);
            item.setTaskId(0L);
            item.setFrequencyOverwrite(false);
            item.setIsWaiterSkipped(false);
            item.setIsRunnerSkipped(false);
            item.setIsDispatcherSkipped(false);
            item.setIsRunnerOnDemand(false);		// is set by FrequenyChecker (except for bootstarp order)
            item.setBootstrap(order.isBootstrap());

            // record history
			item.setCreated(new Date());
			item.setCreatedBy(JobNetPlanCreator.class.getName());
			item.setModified(new Date());
			item.setModifiedBy(JobNetPlanCreator.class.getName());


			if (order.isBootstrap()) {
				collectedUUID.add(uuid);
				item.setIsRunnerOnDemand(isOnDemand);
				try {
					item.setPlannedStartime(startTime);
				} catch (ParseException e) {
					String msgText = msg.getMsg(JOBNETC_E_0007,order.getAt());
					logger.error(msgText,e);
					throw new JobNetException(msgText);
				}
				bootStrap = item;
			}
			planDbLayer.save(item);
            newRecords.add(item);
			if (order.isBootstrap()) logger.info(msg.getMsg(JOBNETC_I_0010,order.getJobNetNodeName()));
		}

		return bootStrap;
	}

    /*
	private void buildReferences(String uuid) {
        List<JobNetPlanDBItem> newRecords = planDbLayer.getJobnetPlanList(0);
		for (JobNetPlanDBItem item : newRecords) {
            Long subnetId = item.getSubnetId();
            Long connectorId = item.getConnectorId();
            item.setSubnetId( getPlanDBItemByNodeId(subnetId,uuid).getPlanId() );
            item.setConnectorId( getPlanDBItemByNodeId(connectorId,uuid).getPlanId() );
			planDbLayer.saveOrUpdate(item);
		}
	}

    private JobNetPlanDBItem getPlanDBItemByNodeId(Long nodeId, String uuid) {
        JobNetNodeDBItem nodeItem = nodeDbLayer.getNode(nodeId);
        String nodeName = nodeItem.getNode();
        JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
        jobNetPlanFilter.setUuid(uuid);
        jobNetPlanFilter.setNode(nodeName);
        planDbLayer.setFilter(jobNetPlanFilter);
        List<JobNetPlanDBItem> jobNetPlanDBItems  = planDbLayer.getJobnetPlanList(0);
        if (jobNetPlanDBItems.size() > 1)
            throw new JobNetException("More than one node found with name " + nodeName + " for UUID " + uuid);
        if (jobNetPlanDBItems.size() == 0)
            throw new JobNetException("No node found with name " + nodeName + " for UUID " + uuid);
        return jobNetPlanDBItems.get(0);
    }
    */

	/*
	 * Hier werden die Parameter in die Order übernommen, die im Waiter / Dispatcher benötigt werden.
	 */
	private JobNetOrder addAdditionalParametersToOrderXml(JobNetOrder order, String uuid) {
		
		Params params = order.getParams();
		
		String paramName = getOptions().JobNetIdParameterName.Value();
		if(!paramName.isEmpty()) {
			Param p = liveObjectFactory.createParam();
			p.setName(JobNetGlobalParams.jobnet_id_parameter_name.name());
			String jobNetIdParameterName = getOptions().JobNetIdParameterName.Value();
			p.setValue(jobNetIdParameterName);
			params.getParamOrCopyParamsOrInclude().add(p);

			p = liveObjectFactory.createParam();
			p.setName(jobNetIdParameterName);
			p.setValue(jobnetId);
			params.getParamOrCopyParamsOrInclude().add(p);
		}
		
		Param p = liveObjectFactory.createParam();
		p.setName(JobNetGlobalParams.uuid_jobnet_identifier.name());
		p.setValue(uuid);
		params.getParamOrCopyParamsOrInclude().add(p);
		
		p = liveObjectFactory.createParam();
		p.setName(JobNetGlobalParams.jobnet.name());
		p.setValue(getBootstrapOrder().getJobNetJobChainNameWithPath());
		params.getParamOrCopyParamsOrInclude().add(p);
		
		p = liveObjectFactory.createParam();
		p.setName(JobNetGlobalParams.GraphBuilderJobChain.name());
		p.setValue(getOptions().GraphBuilderJobChain.Value());
		params.getParamOrCopyParamsOrInclude().add(p);
		
		p = liveObjectFactory.createParam();
		p.setName(JobNetGlobalParams.JobChainName.name());
		p.setValue(getOptions().JobChainName.Value());
		params.getParamOrCopyParamsOrInclude().add(p);
		
		p = liveObjectFactory.createParam();
		p.setName(JobNetGlobalParams.TransferType.name());
		p.setValue(getOptions().TransferType.Value());
		params.getParamOrCopyParamsOrInclude().add(p);
		
		return order;
	}
	
	private String getUniqueOrderId(int orderIndex) {
		String result = getBootstrapOrder().getJobNetOrderName();
		return (orderIndex > 0) ? result + "_" + orderIndex : result;
	}

	public List<JobNetPlanDBItem> getPlannedOrders() {
		return plannedOrders;
	}

	public void registerOrderStarter(IStartOrder starter) {
		this.orderStarter = starter;
	}

    /**
     * This method is for an immidiately start of the jobchain cause by restart_option="always"
     *
     * @param item
     */
    @Override
	public void startOrderImmediately(JobNetPlanDBItem item) {
		if (orderStarter != null) {
			orderStarter.startOrderImmediately(item);
        } else
			logger.warn("No object of type IStartOrder is registered to start an order.");
	}

	@Override
	public void startOrder(JobNetPlanDBItem item) {
		if (orderStarter != null) {
			orderStarter.startOrder(item);
        } else
			logger.warn("No object of type IStartOrder is registered to start an order.");
	}

    @Override
	public void startOrderViaJobScheduler(Spooler spooler, JobNetPlanDBItem bootDBItem, boolean startImmediately) {
		executor.startOrderViaJobScheduler(spooler, bootDBItem, startImmediately);
	}
	
}
