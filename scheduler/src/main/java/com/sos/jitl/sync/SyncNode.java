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
package com.sos.jitl.sync;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SyncNode {

	private String						syncNodeJobchainName;
	private String						syncNodeJobchainPath;
	private String						syncNodeState;

	private int							required	= 1;
	private List<SyncNodeWaitingOrder>	listOfSyncNodeWaitingOrder;

	private static Logger				logger		= Logger.getLogger(SyncNode.class);

	public SyncNode() {
		super();
		listOfSyncNodeWaitingOrder = new ArrayList<SyncNodeWaitingOrder>();
	}

	public void addOrder(final SyncNodeWaitingOrder so) {
		if (listOfSyncNodeWaitingOrder == null) {
			listOfSyncNodeWaitingOrder = new ArrayList<SyncNodeWaitingOrder>();
		}

		listOfSyncNodeWaitingOrder.add(so);

	}

	public void addOrder(final SyncNodeWaitingOrder so, final String syncId) {
		if (listOfSyncNodeWaitingOrder == null) {
			listOfSyncNodeWaitingOrder = new ArrayList<SyncNodeWaitingOrder>();
		}

		logger.debug(String.format("check wether order: %s with syncId %s should be added to syncId %s", so.getId(), so.getSyncId(), syncId));
		if (syncId.equals("") || syncId == null || so.getSyncId().equals(syncId)) {
			logger.debug(" ----->added");
			listOfSyncNodeWaitingOrder.add(so);
		}

	}

	public String getSyncNodeJobchain() {
		return syncNodeJobchainName;
	}

	public String getSyncNodeState() {
		return syncNodeState;
	}

	public void setSyncNodeState(final String syncNodeState) {
		this.syncNodeState = syncNodeState;
	}

	public int getRequired() {
		return required;
	}

	public void setRequired(final int required) {
		logger.debug(String.format("%s: required orders=%s", syncNodeJobchainName, required));
		this.required = required;
	}

	public void setRequired(final String required) {
		try {
			logger.debug(String.format("%s: required orders=%s", syncNodeJobchainName, required));
			this.required = Integer.parseInt(required);
		}
		catch (NumberFormatException e) {
			logger.warn(String.format("could not convert %s", required));
		}
	}

	public List<SyncNodeWaitingOrder> getSyncNodeWaitingOrderList() {
		return listOfSyncNodeWaitingOrder;
	}

	public void setSyncNodeWaitingOrderList(final List<SyncNodeWaitingOrder> syncNodeWaitingOrderList) {
		listOfSyncNodeWaitingOrder = syncNodeWaitingOrderList;
	}

	public boolean isReleased() {
		boolean erg = listOfSyncNodeWaitingOrder.size() >= required;
		logger.debug(String.format("Jobchain: %s, State: %s,  required: %s, waiting: %s ----> %s", syncNodeJobchainPath, syncNodeState, required,
				listOfSyncNodeWaitingOrder.size(), erg));
		return listOfSyncNodeWaitingOrder.size() >= required;
	}

	public boolean isReleased(final String syncId) {
		return listOfSyncNodeWaitingOrder.size() >= required;
	}

	public void setSyncNodeJobchainName(final String syncNodeJobchainName) {
		this.syncNodeJobchainName = syncNodeJobchainName;
	}

	public String getSyncNodeJobchainPath() {
		return syncNodeJobchainPath;
	}

	public void setSyncNodeJobchainPath(final String syncNodeJobchainPath) {
		this.syncNodeJobchainPath = syncNodeJobchainPath;
	}

	public String getSyncNodeJobchainName() {
		return syncNodeJobchainName;
	}

}
