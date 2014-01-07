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
import com.sos.jobnet.classes.EdgeType;
import com.sos.jobnet.classes.EdgeTypeValues.enuEdgeType;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.db.*;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class JobNetOrderDBAdapter {
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetOrderDBAdapter.java 19754 2013-03-27 13:27:29Z ss $";
	
	private final static Logger logger = Logger.getLogger(JobNetOrderDBAdapter.class);

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_I_0009 = "JOBNETC_I_0009"; // SAVE: jobchain=%1$s, node=%2$s, schedulerid=%3$s
	private final static String JOBNETC_E_0005 = "JOBNETC_E_0005"; // could not create %1$s for %2$s - no record found in job net nodes.

    private final JobNetConifigurationLayer jobNetLayer;
	private final JobNetDBLayer dbLayerJobnet;
	private final JobNetCmdHistoryDBLayer dbLayerCommands;
	private final JobNetNodeDBLayer dbLayerNodes;
	private final JobNetEdgesDBLayer dbLayerEdges;
	private final JobNetOrder order;
	private final JobNetNodeDBItem item;


    public JobNetOrderDBAdapter(JobNetConifigurationLayer dbLayer, JobNetOrder order) {
		this.jobNetLayer = dbLayer;
        this.dbLayerJobnet = dbLayer.getDbLayerJobnet();
        this.dbLayerCommands = dbLayer.getDbLayerCommands();
        this.dbLayerNodes = dbLayer.getDbLayerNodes();
        this.dbLayerEdges = dbLayer.getDbLayerEdges();
		this.order = order;
		this.item = (order.getNodeDBItem() == null) ? getJobNetNodeOrNull() : order.getNodeDBItem();
	}

	public void deleteAllEdges() {
		if (itemExists()) {
			JobNetEdgesFilter filter = new JobNetEdgesFilter();
			filter.setParentNodeId(item.getNodeId());
			dbLayerEdges.setFilter(filter);
			dbLayerEdges.delete();
		}
	}

	public JobNetDBItem createJobNet() {
		JobNetDBItem result = new JobNetDBItem();
		try {
			result.setJobNet(order.getNodeDBItem().getJobnet());
			result.setSchedulerId(order.getNodeDBItem().getSchedulerId());
            result.setStartNodeId(order.getNodeDBItem().getNodeId());
            dbLayerJobnet.save(result);
		} catch (Exception e) {
			throw new JobNetException("Error creating record in table SCHEDULER_JOB_NET.");
		}
		return result;
	}

	public JobNetCmdHistoryDBItem createJobNetCmdHistory(JobNetDBItem parent) {
		JobNetCmdHistoryDBItem result = new JobNetCmdHistoryDBItem();
        JobNetCreatorOptions options = jobNetLayer.getOptions();
		try {
            result.setHistoryId(parent.getNetId());
            result.setStartOption(options.StartOption.Value());
            result.setNodeType(" ");                             // not necessary
            // result.setStartTime( new Date() );               // should be set if jobnet instance is started
            result.setCreated( new Date() );
            result.setCreatedBy( this.getClass().getName() );
            dbLayerCommands.save(result);
		} catch (Exception e) {
			throw new JobNetException("Error creating record in table SCHEDULER_JOB_NET_CMD_HISTORY.");
		}
		return result;
	}

	/**
	 * Creates a new record in table SCHEDULER_JOB_NET_NODES
	 * @return
	 */
	public JobNetNodeDBItem createJobNetNode() {
		JobNetNodeDBItem result = new JobNetNodeDBItem();
		try {
			result.setJobnet(order.getJobNetJobChainNameWithPath());
			result.setNode(order.getJobNetOrderName());
			result.setSchedulerId(order.getClusterId());
            dbLayerNodes.save(result);
			logger.debug(msg.getMsg(JOBNETC_I_0009, result.getJobChain(), result.getNode(), result.getSchedulerId()));
		} catch (Exception e) {
			throw new JobNetException("Error creating record in table SCHEDULER_JOB_NET_NODES.");
		}
		return result;
	}

	private JobNetEdgesDBItem createJobNetEdge(enuEdgeType type, JobNetNodeDBItem child) {
		JobNetEdgesDBItem result = new JobNetEdgesDBItem();
		try {
			logger.info(type.value() + ": " + item.getNodeId() + " - " + child.getNodeId());
			result.setJobnetNodeDBItem(item);
			result.setParentNodeId(item.getNodeId());
			result.setChildNodeId(child.getNodeId());
			result.setEdgeType(type.value());
            dbLayerEdges.save(result);
		} catch (Exception e) {
			throw new JobNetException("Error creating record in table SCHEDULER_JOB_NET_EDGES.");
		}
		return result;
	}
	
	public JobNetNodeDBItem getItem() {
		return item;
	}
	
	public boolean itemExists() {
		return (item == null) ? false : true;
	}
	
	public JobNetEdgesDBItem createSuccessor(JobNetNodeDBItem child) {
		if (!itemExists()) {
			String msgText = msg.getMsg(JOBNETC_E_0005,"successor",order.getJobNetNodeName());
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		// JobNetEdgesDBItem result = getEdgeOrNull(enuEdgeType.S, child);
		// if (result == null)
        JobNetEdgesDBItem result = createJobNetEdge(enuEdgeType.S, child);
		return result;
	}
	
	public JobNetEdgesDBItem createPredecessor(JobNetNodeDBItem child) {
		if (!itemExists()) {
			String msgText = msg.getMsg(JOBNETC_E_0005,"predecessor",order.getJobNetNodeName());
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		// JobNetEdgesDBItem result = getEdgeOrNull(enuEdgeType.P, child);
		// if (result == null)
        JobNetEdgesDBItem result = createJobNetEdge(enuEdgeType.P, child);
		return result;
	}

	private JobNetEdgesDBItem getEdgeOrNull(enuEdgeType type, JobNetNodeDBItem child) {
		JobNetEdgesDBItem result = null;
		JobNetEdgesFilter filter;
		try {
			filter = getUniqueEdgeFilter(type,child);
		} catch (Exception e) {
			logger.error("error getting a unique filter for edge type " + type + " (nodeId=" + child.getNodeId() + ")");
			throw new JobNetException(e.getMessage());
		}
		dbLayerEdges.setFilter(filter);
		List<JobNetEdgesDBItem> list = dbLayerEdges.getJobnetEdgesList(0);
		if (list.size() > 0)
			result = list.get(0);
		return result;
	}

	private JobNetEdgesFilter getUniqueEdgeFilter(enuEdgeType type, JobNetNodeDBItem child) throws Exception {
		EdgeType t = new EdgeType();
		t.setEdgeType(type.value());
		JobNetEdgesFilter filter = new JobNetEdgesFilter();
		filter.setEdgeType(t);
		filter.setParentNodeId(item.getNodeId());
		filter.setChildNodeId(child.getNodeId());
		filter.setEdgeType(type.value()); 
		return filter;
	}

	private JobNetNodeFilter getUniqueNodeFilter() {
		JobNetNodeFilter filter = new JobNetNodeFilter();
		filter.setJobnet(order.getJobNetJobChainNameWithPath());
		filter.setSchedulerId(order.getClusterId());
		filter.setNode(order.getJobNetOrderName());
		return filter;
	}

	private JobNetNodeDBItem getJobNetNodeOrNull() {
		JobNetNodeDBItem result = null;
		JobNetNodeFilter filter = getUniqueNodeFilter();
		dbLayerNodes.setFilter(filter);
		List<JobNetNodeDBItem> list = dbLayerNodes.getJobnetNodeList(0);
		if (list.size() > 0)
			result = list.get(0);
		return result;
	}

}
