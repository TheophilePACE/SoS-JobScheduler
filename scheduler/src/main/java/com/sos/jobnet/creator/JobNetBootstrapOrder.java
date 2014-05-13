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
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.context.ContextChecker;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.jobnet.options.StartModes;
import com.sos.jobnetmodel.objects.JobnetNodeType;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class JobNetBootstrapOrder extends JobNetOrder {
	
	private final static Logger logger = Logger.getLogger(JobNetBootstrapOrder.class);
	
	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_I_0004 = "JOBNETC_I_0004"; // %1$s is successor of %2$s

	private static final String JOBNETC_E_0001 = "JOBNETC_E_0001"; 		// %1$s is successor of %2$s but %3$s is not predecessor of %4$s
	private final static String JOBNETC_E_0002 = "JOBNETC_E_0002"; 		// %1$s is predecessor of %2$s but %3$s is not successor of %4$s.
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetBootstrapOrder.java 21038 2013-09-09 11:25:23Z ss $";

	private final StartModes startOption;
    private final ContextChecker checker;

	private static JobNetNodeList orderNodes;

	public JobNetBootstrapOrder(JobNetCreatorOptions options) {
		super(options.getLiveConnector(), options.getLiveConnector().getRootDirectory(), options.SCHEDULER_ID.Value(), getOrderId(options.OrderId.Value(),options.JobChainName.Value()), getJobChain(options.OrderId.Value(),options.JobChainName.Value()));
        logger.info("The root directory of the orders is " + getNameComponents().getFullPath());
		this.startOption = options.StartOption.asEnum();
        this.checker = new ContextChecker(getContext(), getNodeType());
        initNodeList(getMaxNodes(options));
	}
	
	private static int getMaxNodes(JobNetCreatorOptions options) {
		return Integer.parseInt(options.MaxJobNetNodes.Value());
	}
	
	private static JobNetNodeList initNodeList(int maxNodes) {
		orderNodes = new JobNetNodeList(maxNodes);
		return orderNodes;
	}

	/**
	 * Gets the orderId including the live folder path.
	 */
	private static String getOrderId(String orderId, String jobChain) {
		String result = orderId;
		if (jobChain.contains("/")) {
			String[] arr = jobChain.split("/");
			result = "/";
			for (int i = 0; i < arr.length-1; i++) {
				result += arr[i] + "/";
			}
			result += orderId;
		}
		return result;
	}
	
	/**
	 * Gets the jobChain name without live folder path
	 */
	private static String getJobChain(String orderId, String jobChain) {
		String result = jobChain;
		if (jobChain.contains("/")) {
			String[] arr = jobChain.split("/");
			result = arr [arr.length-1];
		}
		return result;
	}

    /*
     * Set subnet and connector objects. If subnet and/or connector is not set, the bootstrap order will be subnet/connector.
     * This part has be done after the records are present in the database.
     */
    private void setSubNetAndConnectorReferences() {
        for(JobNetOrder o : getJobNet().values()) {

            // reference to subnet node
            {
                String subnet = o.getContext().getSubnet();
                JobNetOrder order = getNodeByNameOrNull(subnet);
                o.setSubnet( (order == null) ? this : order );
            }

            // reference to connector node
            {
                String connector = o.getContext().getConnector();
                JobNetOrder order = getNodeByNameOrNull(connector);
                o.setConnector( (order == null) ? this : order);
            }

            logger.info("JobnetNode " + o.getJobNetOrderName() + ": Subnet=" + o.getSubnet().getJobNetOrderName() + ", Connector=" + o.getConnector().getJobNetOrderName() );
        }
    }
	
	private JobNetOrder getNodeByNameOrNull(String uniqueNodeName) {
		JobNetOrder result = null;
		for(JobNetOrder o : getJobNet().values()) {
			if(o.getJobNetOrderName().equalsIgnoreCase(uniqueNodeName)) {
				result = o;
				break;
			}
		}
		return result;
	}

    /**
     * Starts the iteration of the jobnet. Beginning with the bootstrap order (this) all orders of the jobnet will be reaad from the filesystem.
     */
    public void createJobNet() {
        logger.debug("Build the object references to the subnet and the connector nodes.");
        registerBootstrapOrder(this);
        iterateJobNet(this);
        setSubNetAndConnectorReferences();
    }

    /**
     * Iterates the jobnet. If StartOption requires a specific context iteration will stopped at the first node outside the context.
     * @param order
     */
    private void iterateJobNet(JobNetOrder order) {
        orderNodes.addOrderToNodesCollection(order);
        if (order.hasSuccessors()) {
            List<JobNetOrder> successors = order.getSuccessors();
            Iterator<JobNetOrder> it = successors.iterator();
            while(it.hasNext()) {
                JobNetOrder successor = it.next();
                if(!orderNodes.containsKey(successor.getJobNetNodeName())) {
                    if(isValidContext(order) && !isValidContext(successor) && successor.getNodeType() == JobnetNodeType.CONNECTOR) {
                        logger.info("The iteration of the jobnet ends with order " + successor.getOrderWithoutPath());
                        break;
                    }
                    logger.debug(msg.getMsg(JOBNETC_I_0004, successor.getJobNetNodeName(), order.getJobNetNodeName()));
                    iterateJobNet(successor);
                }
            }
        }
	}

	/**
	 * The context is valid either when the JobNetCreator starts with an option that does not perform context checking
	 * or when the given order is in the same context like the bootstrap order.
	 * 
	 * @param order
	 * @return
     */
	private boolean isValidContext(JobNetOrder order) {
		return (!startOption.checkContext() || checker.isInContext(order.getContext()));
	}

    /**
     * @return A list of all nodes in the jobnet
     */
    public JobNetNodeList getJobNet() {
        return orderNodes;
    }

    /**
     * Indicates that this is the bootstrap order (always true)
     * @return
     */
    public boolean isBootstrap() {
        return true;
    }

    /**
     * Method to perform if the option CheckJobNetTexture is set (the jobnet has to be configured with successors AND predecessors)
     */
    public void validateJobNet() {
        for(JobNetOrder order : getJobNet().values()) {
            for(JobNetOrder successor : order.getSuccessors()) {
                if (!successor.hasPredecessor(order)) {
                    String msgText = msg.getMsg(JOBNETC_E_0001,successor.getJobNetNodeName(),order.getJobNetNodeName(),order.getJobNetNodeName(),successor.getJobNetNodeName());
                    logger.error(msgText);
                    throw new JobNetException(msgText);
                }
            }
            for(JobNetOrder predecessor : order.getPredecessors()) {
                if (!predecessor.hasSuccessor(order)) {
                    String msgText = msg.getMsg(JOBNETC_E_0002,predecessor.getJobNetNodeName(),order.getJobNetNodeName(),order.getJobNetNodeName(),predecessor.getJobNetNodeName());
                    logger.error(msgText);
                    throw new JobNetException(msgText);
                }
            }
        }
    }

}
