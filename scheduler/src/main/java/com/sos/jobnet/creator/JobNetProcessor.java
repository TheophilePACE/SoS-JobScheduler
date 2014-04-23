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
import com.sos.jobnet.db.*;
import com.sos.jobnet.interfaces.IStartOrder;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.jobnet.options.SOSOptionStartJobnet;
import com.sos.jobnet.options.StartModes;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.sos.jobnet.context.ContextList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobNetProcessor extends JobNetPlanCreator implements Runnable, IStartOrder {

	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetProcessor.java 21003 2013-09-06 08:13:52Z ss $";

	private final static Logger logger = Logger.getLogger(JobNetProcessor.class);

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

    private final SOSOptionStartJobnet startOption;
    private final StartModes startMode;

    private final String jobnetId;
    private final String jobChain;
    private final String orderId;
    private final String uuid;
    private final String schedulerId;
    private final File configFile;
    private final JobNetPlanDBLayer planDbLayer;
    private final JobNetHistoryDBLayer historyDbLayer;

    private JobNetHistoryDBItem historyRecord = null;

    /*
	private final Interval interval4StartTimes;
	private final boolean startOrder;
	private final boolean checkFrequency;
	private final JobNetPlanExecutor executor;
	private final FrequencyChecker checker;
	private final JobNetNodeDBLayer nodeDbLayer;
	private final EventsDBLayer eventsDbLayer;
	private final boolean createGraph;
	private final SchedulerObjectFactory liveObjectFactory;
	*/

	private List<JobNetPlanDBItem> plannedOrders = new ArrayList<JobNetPlanDBItem>();
	private List<String> collectedUUID = new ArrayList<String>();

	private IStartOrder orderStarter = null;

	public JobNetProcessor(JobNetCreatorOptions options) {
		super(options);
        this.startOption = options.StartOption;
        this.startMode = startOption.asEnum();
        this.jobnetId = options.JobNetId.Value();
        this.jobChain = options.JobChainName.Value();
        this.orderId = options.OrderId.Value();
        this.uuid = jobChain + JobNetConstants.JOBNET_ID_DELIMITER + jobnetId;

        this.schedulerId = getOptions().SCHEDULER_ID.Value();
        this.configFile = new File(getOptions().hibernate_connection_config_file.Value());
        this.planDbLayer = new JobNetPlanDBLayer(configFile, schedulerId);
        this.historyDbLayer = new JobNetHistoryDBLayer(configFile, schedulerId);

        /*
		DateTime from = new DateTime();
		DateTime to = new DateTime(options.TimeHorizon.getEndFromNow());
		this.interval4StartTimes = new Interval(from,to);
		this.startOrder = options.StartOrder.value();
		this.checkFrequency = options.CheckFrequency.value();
		this.executor =  new JobNetPlanExecutor(options);
		executor.registerOrderStarter(this);
		this.checker = new FrequencyChecker(options);
		// this.externalUUID = (jobnetId.isEmpty()) ? "" : jobChain + JobNetConstants.JOBNET_ID_DELIMITER + jobnetId;
		this.createGraph = options.CreateGraph.value();
		this.nodeDbLayer = new JobNetNodeDBLayer(configFile);
		this.eventsDbLayer = new EventsDBLayer(configFile);
		this.liveObjectFactory = options.getLiveConnector().getFactory();
		logger.info("Timeinterval to search for start times: " + fmtDateTime.print(from) + " to " + fmtDateTime.print(to));
		*/
	}

	@Override
	public void run() {

		try {

            if (startMode.checkUUID()) {
                boolean isUUIDPresent = isUUIDPresent();
                if(startMode.needUUID() && !isUUIDPresent)
                    throw new JobNetException("The StartOption " + startMode.name() + " needs an instance of the jobnet for JobNetId " + jobnetId + " - start a jobnet instance with StartOption 'start' first.");
                if(!startMode.needUUID() && isUUIDPresent)
                    throw new JobNetException("In instance of the jobnet with JobNetId " + jobnetId + " is already started.");
            }

            if(!startMode.needUUID()) {
                super.run();
            } else {
                JobNetPlanDBItem node = getStartNode();
                ContextList contextList = getContextList( node );
                if(startMode == StartModes.restart_ignore_error) {
                    if(contextList.hasRunningNodes())
                        throw new JobNetException("Start with option " + startMode.name() + " not possible, because there are running nodes in the context of node " + node.getJobnetNodeDBItem().getNode());
                }
            }

		} finally {
//			planDbLayer.closeSession();
//			eventsDbLayer.closeSession();
		}

	}


    private boolean isUUIDPresent() {
        boolean result = false;         // in case of real UUIDs this function always returns false
        if(!jobnetId.isEmpty()) {
            if (historyRecord == null)
                historyRecord = historyDbLayer.getJobNetInstance(uuid);
            result = (historyRecord != null);
        }
        return result;
    }

    /**
     * Read the startnode for a given jobnet instance
     * @return
     */
    private JobNetPlanDBItem getStartNode() {
        JobNetPlanFilter startNodeFilter = new JobNetPlanFilter();
        startNodeFilter.setSchedulerId(schedulerId);
        startNodeFilter.setUuid(historyRecord.getUuid());
        startNodeFilter.setNode(orderId);
        planDbLayer.setFilter(startNodeFilter);
        List<JobNetPlanDBItem> records = planDbLayer.getJobnetPlanList(0);
        if(records.size() != 1)
            throw new JobNetException("The orderId " + orderId + " is not unique in the jobnet instance with uuid " + uuid + ".");
        return records.get(0);
    }


    private ContextList getContextList(JobNetPlanDBItem startNode) {
        JobNetPlanDBItem node = getStartNode();
        List<JobNetPlanDBItem> contextNodes = planDbLayer.getMemberListForContext(node);
        return new ContextList(contextNodes);
    }


}
