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
package com.sos.jitl.sync;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class SyncNodeContainer {

	private static Logger		logger						= Logger.getLogger(SyncNodeContainer.class);

	private static final String	XPATH_CURRENT_JOB_CHAIN		= "//order[@id = '%s'][@job_chain = '%s']/payload/params/param[@name='sync_session_id']";
	private static final String	ATTRIBUTE_PARAMETER_VALUE	= "value";
	private static final String	ATTRIBUTE_JOB_CHAIN			= "job_chain";
	private static final String	ATTRIBUTE_ORDER_ID			= "id";
	private static final String	ATTRIBUTE_STATE				= "state";
	private static final String	ATTRIBUTE_END_STATE			= "end_state";
	private static final String	XPATH_FOR_ORDERS			= "//order_queue/order";
	private static final String	XPATH_FOR_JOB_CHAINS		= "//job_chains/job_chain/job_chain_node[@job = '%s']";
	private String				jobpath;
	private String				syncId						= "";
	private SyncNodeList		listOfSyncNodes;

	public void getNodes(final String xml) throws Exception {
		//logger.debug(String.format("adding nodes for sync job: %s", jobpath));
		listOfSyncNodes = new SyncNodeList();
		SyncXmlReader xmlReader = new SyncXmlReader(xml, String.format(XPATH_FOR_JOB_CHAINS, jobpath));
		while (!xmlReader.eof()) {
			logger.debug("reading next node");
			xmlReader.getNext();
			SyncNode sn = new SyncNode();
			sn.setSyncNodeJobchainName(xmlReader.getAttributeValueFromParent("name"));
			sn.setSyncNodeJobchainPath(xmlReader.getAttributeValueFromParent("path"));
			sn.setSyncNodeState(xmlReader.getAttributeValue("state"));
			logger.debug(String.format("adding node chain: %s state: %s", sn.getSyncNodeJobchainPath(), sn.getSyncNodeState()));
			listOfSyncNodes.addNode(sn);
		}
	}

	public void getOrders(final String xml) throws Exception {
		logger.debug("xml in getOrders = " + xml);
		SyncXmlReader xmlReader = new SyncXmlReader(xml, XPATH_FOR_ORDERS);
		while (!xmlReader.eof()) {
			xmlReader.getNext();
			String id = xmlReader.getAttributeValue(ATTRIBUTE_ORDER_ID);
			String chain = xmlReader.getAttributeValue(ATTRIBUTE_JOB_CHAIN);
			String state = xmlReader.getAttributeValue(ATTRIBUTE_STATE);
			String orderSyncId = xmlReader.getAttributeValueFromXpath(String.format(XPATH_CURRENT_JOB_CHAIN, id, chain), ATTRIBUTE_PARAMETER_VALUE);
			SyncNodeWaitingOrder o = new SyncNodeWaitingOrder(id, orderSyncId);
			o.setEndState(xmlReader.getAttributeValue(ATTRIBUTE_END_STATE));
			listOfSyncNodes.addOrder(o, chain, state, orderSyncId);
		}
	}

	public void setJobpath(final String jobpath) {
		this.jobpath = jobpath;
	}

	public void setSyncId(final String syncId) {
		this.syncId = syncId;
	}

	public SyncNodeList getListOfSyncNodes() {
		return listOfSyncNodes;
	}

	public boolean isReleased() {
		return listOfSyncNodes.isReleased();
	}

	public void setRequiredOrders(final HashMap<String, String> schedulerParameters) {
		Iterator<String> ii = schedulerParameters.keySet().iterator();

		if (schedulerParameters.get(SyncNodeList.CONST_PARAM_REQUIRED_ORDERS) != null) {
			String requiredOrders = schedulerParameters.get(SyncNodeList.CONST_PARAM_REQUIRED_ORDERS);
			try {
				listOfSyncNodes.setRequired(Integer.parseInt(requiredOrders));
			}
			catch (NumberFormatException e) {
				logger.warn(String.format("Could not convert %s to int", requiredOrders));
			}
		}

		/**
		 * hier kommt der absolute hack. variable parameternamen. wozu?
		 * *Der Job sollte kompatibel zur alten Implementierung sein. ur 17.7.2013
		 *
		 * Der Wert wird grottig zusammengebaut um dann sp‰ter genauso grenzwertig wieder isoliert zu werden.
		 * So ein Scheiﬂ! kb
		 * Solche Kommentare haben in der Software nichts zu suchen. ur 17.7.2013
		 */
		while (ii.hasNext()) {
			String key = ii.next();
			if (key.contains(SyncNodeList.CONST_PARAM_PART_REQUIRED_ORDERS)) {
				logger.debug(String.format("key = %s, setting %s", key, key + schedulerParameters.get(key)));
//				listOfSyncNodes.setRequired(key + "_" + schedulerParameters.get(key));
				listOfSyncNodes.setRequired(key + schedulerParameters.get(key));
			}
		}

	}

	public boolean eof() {
		return getListOfSyncNodes().eof();
	}

	public SyncNode getNextSyncNode() {
		return getListOfSyncNodes().getNextSyncNode();
	}
}
