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
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class JobNetNodeList extends HashMap<String,JobNetOrder> {

	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetNodeList.java 16852 2012-03-22 04:59:03Z ss $";

	private static final long serialVersionUID = -3056305420256851206L;

	private final static Logger logger = Logger.getLogger(JobNetNodeList.class);

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_I_0013 = "JOBNETC_I_0013"; // node %1$s (of max. %2$s) added to internal collection: %3$s. 
	private final static String JOBNETC_E_0017 = "JOBNETC_E_0017"; // the jobnet contains mor than %1$s  nodes (maybe a recursion?). Set Parameter MaxJobNetNodes if you need larger jobnets. 

	private final int maxNodes;
	
	public JobNetNodeList(int maxNodes) {
		this.maxNodes = maxNodes;
	}

	public void addOrderToNodesCollection(JobNetOrder order) {
		if (size() == maxNodes) {
			String msgText = msg.getMsg(JOBNETC_E_0017,maxNodes);
			logger.error(msgText);
			throw new JobNetException(msgText);
		}
		String key = order.getJobNetNodeName();
		if (!containsKey(key)) {
			put(key,order);
			logger.debug(msg.getMsg(JOBNETC_I_0013,size(),maxNodes,key));
		}
	}

}
