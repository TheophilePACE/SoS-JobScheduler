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
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.context.Context;
import com.sos.jobnet.db.JobNetNodeDBItem;
import com.sos.jobnetmodel.objects.JobnetNodeType;
import com.sos.localization.Messages;
import com.sos.scheduler.model.objects.JSObjOrder;
import com.sos.scheduler.model.objects.JSObjParams;
import com.sos.scheduler.model.tools.PathResolver;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class JobNetOrder extends JSObjOrder {

	@SuppressWarnings("unused")
	private static final String			conSVNVersion				= "$Id: JobNetOrder.java 21038 2013-09-09 11:25:23Z ss $";

	private final static Logger			logger						= Logger.getLogger(JobNetOrder.class);

	private static final Messages		msg							= new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String			JOBNETC_I_0007				= "JOBNETC_I_0007";													// jobnet nodename of %1$s is %2$s
	private final static String			JOBNETC_I_0008				= "JOBNETC_I_0008";													// try to read file %1$s.
	private final static String			JOBNETC_E_0003				= "JOBNETC_E_0003";													// the jobchain %1$s (needed in %2$s) does not exist
	private final static String			JOBNETC_E_0004				= "JOBNETC_E_0004";													// could not red file %1$s.

	private final static String			DELIMITER_FOR_ORDERS		= ",";
	private final static String			BOOTSTRAP_PARAMETER_NAME	= "bootstrap";
	private final static String			SUCCESSORS_PARAMETER_NAME	= "successor";
	private final static String			PREDECESSORS_PARAMETER_NAME	= "predecessor";
	private final static String			ON_DEMAND_PARAMETER_NAME	= "on_demand";
	private final static String			NODETYPE_PARAMETER_NAME	    = "nodetype";
	private final static String			SUBNET_PARAMETER_NAME	    = "subnet";
	private final static String			CONNECTOR_PARAMETER_NAME	= "connector";
	private final static String			SCRIPT_TO_EXECUTE_PARAMETER_NAME	= "script_to_execute";
	private final static String			USE_ERROR_HANDLER_PARAMETER_NAME	= "error_handler";
	private final static String			EVENT_ON_ERROR_PARAMETER_NAME	= "event_on_error";
	private final static String			EVENT_ON_SUCCESS_PARAMETER_NAME	= "event_on_success";
	private final static String			WAIT_FOR_EVENTS_PARAMETER_NAME	= "wait_for_events";

	private final String				orderId;
	private final String				jobChain;
	private final String				clusterId;
	private final String				jobNetBaseName;
	private final String				jobNetNodeName;
	private final String				jobNetOrderName;
	private final String				jobNetJobChainName;
	private final JobnetNodeType		jobNetNodeType;
	
	private final String 				scriptToExecute;
	private final boolean 				useErrorHandler;
	private final boolean 				isOnDemandOrder;
	private final String  			    waitForEvents;
	private final String                eventOnError;
	private final String                eventOnSuccess;

    private JobNetOrder					subnet;
    private JobNetOrder					connector;

	private final ObjectModelConnector	connection;
	private final JSObjParams			params;
	private final boolean				hasSuccessors;
	private final boolean				hasPredecessors;
	private final OrderNameCreator		nameComponents;
	private final Context		context;

    private JobNetBootstrapOrder bootstrapOrder = null;

	private List<JobNetOrder>			successors					= null;
	private List<JobNetOrder>			predeccessors				= null;

	private JobNetNodeDBItem			nodeDBItem					= null;

    public JobNetOrder(final ObjectModelConnector connection, final String baseFolder, final String clusterId, final String orderId, final String jobChain) {
		super(connection.getFactory(), getVirtualFile(connection, baseFolder, orderId, jobChain));
		this.clusterId = clusterId;
		this.orderId = orderId;
		this.jobChain = jobChain;
		this.connection = connection;

		this.nameComponents = new OrderNameCreator(baseFolder,orderId,jobChain);
		this.jobNetBaseName = PathResolver.getRelativePath(connection.getRootDirectory(), baseFolder, nameComponents.getObjectFilePath());
		this.jobNetNodeName = jobNetBaseName + "/" + nameComponents.getObjectName();
		this.jobNetOrderName = nameComponents.getObjectName();
		logger.debug(msg.getMsg(JOBNETC_I_0007,nameComponents.getObjectName(),jobNetNodeName));
		this.params = connection.getFactory().createParams(getParams());
		this.jobNetJobChainName = jobNetBaseName + "/" + jobChain + (params.hasParameterValue("split") ? "_split" : "");
		this.hasSuccessors = params.hasParameterValue(SUCCESSORS_PARAMETER_NAME);
		this.hasPredecessors = params.hasParameterValue(PREDECESSORS_PARAMETER_NAME);		// the path containing the bootstrap order is the base for all subsequent orders in the jobnet
		this.connection.setWorkingDirectory(getFullPath());
        //this.isBootstrap = getBooleanParameter(BOOTSTRAP_PARAMETER_NAME,false);
        this.isOnDemandOrder = getBooleanParameter(ON_DEMAND_PARAMETER_NAME,false);
		this.jobNetNodeType = getNodeType(params);
		
		this.waitForEvents = getStringParameterOrEmpty(WAIT_FOR_EVENTS_PARAMETER_NAME);
		this.eventOnError = getStringParameterOrEmpty(EVENT_ON_ERROR_PARAMETER_NAME);
		this.eventOnSuccess = getStringParameterOrEmpty(EVENT_ON_SUCCESS_PARAMETER_NAME);
		this.scriptToExecute = getStringParameterOrEmpty(SCRIPT_TO_EXECUTE_PARAMETER_NAME);
        this.useErrorHandler = getBooleanParameter(USE_ERROR_HANDLER_PARAMETER_NAME,true);

		ObjectNameCreator chainComponents = new ObjectNameCreator(getFullPath(), this.jobChain, ObjectNameCreator.Type.JOB_CHAIN);
		ISOSVirtualFile fileChain = connection.getFileHandler().getFileHandle(chainComponents.getFullName());
		String msgText = msg.getMsg(JOBNETC_E_0003, this.jobChain, jobNetNodeName);
		try {
			if (!fileChain.FileExists()) {
				logger.error(msgText);
				throw new JobNetException(msgText);
			}
		}
		catch (Exception e) {
			logger.error(msgText);
			throw new JobNetException(msgText);
		}

        String jobName = getOrderWithoutPath();
        String connectorName = getStringParameterOrEmpty(CONNECTOR_PARAMETER_NAME);
        String subnetName = getStringParameterOrEmpty(SUBNET_PARAMETER_NAME);
        this.context = new Context(subnetName, connectorName, jobName);

	}

    private String getStringParameterOrEmpty(String parameterName) {
        return getStringParameterOrEmpty(parameterName,"");
    }

    private String getStringParameterOrEmpty(String parameterName, String defaultValue) {
        return (params.hasParameterValue(parameterName)) ? params.values().get(parameterName) : defaultValue;
    }

    private boolean getBooleanParameter(String parameterName, boolean defaultValue) {
        String result = getStringParameterOrEmpty(parameterName);
        return (result.isEmpty()) ? defaultValue : getYesOrNo(result);
    }

	private JobnetNodeType getNodeType(JSObjParams params) {
		JobnetNodeType type = JobnetNodeType.JOB;		// default
		if (params.hasParameterValue(NODETYPE_PARAMETER_NAME)) {
			String typeAsText = params.values().get(NODETYPE_PARAMETER_NAME).toLowerCase();
			for(JobnetNodeType t : JobnetNodeType.values()) {
				if(typeAsText.equals(t.name().toLowerCase())) {
					type = t;
					break;
				}
			}
		}
		return type;
	}
	
//	/**
//	 * The jobnet configuration can be placed under a live folder such /.jobnet, but the real orders has to be 
//	 * created in /jobnet.
//	 * 
//	 * @param baseName
//	 * @return
//	 */
//	private String getRealBaseName(String baseName) {
//		return baseName.replaceAll("/\\.", "/");
//	}
	
	private static ISOSVirtualFile getVirtualFile(final ObjectModelConnector connection, final String baseFolder, final String orderId, final String jobChain) {
		ISOSVirtualFile result = null;
		String orderFileName = getFullOrderName(baseFolder, orderId, jobChain);
		try {
			logger.debug("search for order: " + orderFileName);
			result = connection.getFileHandler().getFileHandle(orderFileName);
			if (!result.FileExists())
				throw new FileNotFoundException();
		}
		catch (Exception e) {
			String msgText = msg.getMsg(JOBNETC_E_0004, orderFileName);
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		return result;
	}

	private static String getFullOrderName(final String baseFolder, final String orderWithPath, final String jobChain) {
		OrderNameCreator nameCreator = new OrderNameCreator(baseFolder, orderWithPath, jobChain);
		logger.debug(msg.getMsg(JOBNETC_I_0008, nameCreator.getFullName()));
		return nameCreator.getFullName();
	}

	@Override
	public String getJobChain() {
		return jobChain;
	}

	@Override
	public String getId() {
		return orderId;
	}

	public String getJobNetNodeName() {
		return jobNetNodeName;
	}

	public String getClusterId() {
		return clusterId;
	}

	public JSObjParams getOrderParams() {
		return params;
	}

	public boolean hasPredecessors() {
		return hasPredecessors;
		//		return params.hasParameterValue(PREDECESSORS_PARAMETER_NAME);
	}

	public boolean hasPredecessor(final JobNetOrder parent) {
		getPredecessors();
		boolean result = false;
		for (JobNetOrder o : getPredecessors()) {
			if (o.getJobNetNodeName().equals(parent.getJobNetNodeName())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean hasSuccessors() {
		return hasSuccessors;
	}

	public boolean hasSuccessor(final JobNetOrder child) {
		getSuccessors();
		boolean result = false;
		for (JobNetOrder o : getSuccessors()) {
			if (o.getJobNetNodeName().equals(child.getJobNetNodeName())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public List<JobNetOrder> getSuccessors() {
		if (successors == null)
			successors = getOrderList(SUCCESSORS_PARAMETER_NAME);
		return successors;
	}

	public List<JobNetOrder> getPredecessors() {
		if (predeccessors == null)
			predeccessors = getOrderList(PREDECESSORS_PARAMETER_NAME);
		return predeccessors;
	}

	public String getFullPath() {
		return nameComponents.getFullPath();
	}
	
	// The name of the order without path
	public String getOrderWithoutPath() {
		return nameComponents.getObjectName();
	}

	private List<JobNetOrder> getOrderList(final String forParameter) {
        if(bootstrapOrder == null)
            throw new JobNetException("No bootstrap order registered - call registerBootstarpOrder first.");

		List<JobNetOrder> result = new ArrayList<JobNetOrder>();
		connection.setWorkingDirectory(getFullPath()); // all relative successors based on the path of its parent
		if (params.hasParameterValue(forParameter)) {
			String successors = params.values().get(forParameter);
			String[] arr = successors.split(DELIMITER_FOR_ORDERS);
			for (String id : arr) {
				String baseFolder = connection.selectBaseFolder(id);
				String key = getNodeName(baseFolder, id, jobChain);
				JobNetOrder newJobNetNode = null;
				if (bootstrapOrder.getJobNet().containsKey(key)) {
				    newJobNetNode = bootstrapOrder.getJobNet().get(key);
				} else {
					newJobNetNode = new JobNetOrder(connection, baseFolder, clusterId, id, jobChain);
                    newJobNetNode.registerBootstrapOrder(bootstrapOrder);
				}
				result.add(newJobNetNode);
			}
		}
		return result;
	}

	private String getNodeName(final String baseFolder, final String orderId, final String jobChain) {
		OrderNameCreator c = new OrderNameCreator(baseFolder, orderId, jobChain);
		String baseName = PathResolver.getRelativePath(connection.getRootDirectory(), baseFolder, c.getObjectFilePath());
		return baseName + "/" + c.getObjectName();
	}

	/**
	 * \brief check if the bootstrap order should start on demand 
	 * \detail
	 * An order starts on demand if the order parameter 'on_demand' is set or if the order
	 * does not contain a runtime defintion.
	 *
	 * @return
	 */
	public boolean isOnDemandOrder() {
        return isOnDemandOrder;
	}

	public JobNetNodeDBItem getNodeDBItem() {
		return nodeDBItem;
	}

	public void setNodeDBItem(final JobNetNodeDBItem nodeDBItem) {
		this.nodeDBItem = nodeDBItem;
	}

	public boolean hasNodeDBItem() {
		return nodeDBItem == null ? false : true;
	}

    /**
     * Indicates that this is NOT a bootstrap order (isBootstarp() is always false). The bootstrap order is available via
     * the class JobNetBootStarpOrder().
     * @return
     */
	public boolean isBootstrap() {
		return false;
	}

	public String getJobNetJobChainNameWithPath() {
		return jobNetJobChainName;
	}

	public String getJobNetOrderName() {
		return jobNetOrderName;
	}
	
	public JobnetNodeType getNodeType() {
		return jobNetNodeType;
	}
	
	public void setSubnet(JobNetOrder subnet) {
		this.subnet = subnet;
	}
	
	public JobNetOrder getSubnet() {
		return this.subnet;
	}
	
	public void setConnector(JobNetOrder connector) {
		this.connector = connector;
	}
	
	public JobNetOrder getConnector() {
		return this.connector;
	}

	public String getScriptToExecute() {
		return this.scriptToExecute;
	}

	public boolean getUseErrorHandler() {
		return this.useErrorHandler;
	}

	public String getWaitForEvents() {
		return this.waitForEvents;
	}

	public String getEventOnError() {
		return this.eventOnError;
	}

	public String getEventOnSuccess() {
		return this.eventOnSuccess;
	}

    public Context getContext() {
        return context;
    }

    public OrderNameCreator getNameComponents() {
        return nameComponents;
    }

    public void registerBootstrapOrder(JobNetBootstrapOrder bootstrapOrder) {
        this.bootstrapOrder = bootstrapOrder;
    }
}
