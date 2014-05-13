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
import com.sos.jobnet.db.JobNetDBItem;
import com.sos.jobnet.db.JobNetEdgesDBItem;
import com.sos.jobnet.db.JobNetNodeDBItem;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.jobnet.options.StartModes;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

/**
 * This class is for creating a jobnet. A jobnet contains of a list of nodes connected by edges. Each jobnet has a special node called
 * bootstrap node (bootstrap order). The bootstrap node marks the starting point of the jobnet.
 * 
 * By calling the run method the complete jobnet (a collection of JobScheduler orders) will read from the filesystem and stored in the
 * database in the tables SCHEDULER_JOB_NET, SCHEDULER_JOB_NET_NODES and SCHEDULER_JOB_NET_EDGES.
 *
 * @author ss
 */
public class JobNetCreator implements Runnable {
	
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetCreator.java 21015 2013-09-07 08:30:23Z ss $";
	
	private final static Logger logger = Logger.getLogger(JobNetCreator.class);

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private static final String	JOBNETC_I_0001 = "JOBNETC_I_0001";		// create job net for order %1$s (jobchain %2$s).
	private static final String JOBNETC_I_0002 = "JOBNETC_I_0002"; 		// %1$s nodes readed from file system.
	private static final String JOBNETC_I_0003 = "JOBNETC_I_0003";		// jobnet successfully written in database.
	private static final String JOBNETC_I_0005 = "JOBNETC_I_0005";		// the texture of the jobnet will not be checked.
	private static final String JOBNETC_I_0006 = "JOBNETC_I_0006";		// checking the texture of the jobnet.

	private final static String JOBNETC_E_0014 = "JOBNETC_E_0014"; 		// error creating jobnet
	
	private final JobNetCreatorOptions options;

	private final String orderId;
	private final String jobChain;
	private final String clusterId;
	private final boolean checkJobNetTexture;
    private final StartModes startOption;

	private JobNetBootstrapOrder bootstrapOrder;
    private long jobnetId;
	
	public JobNetCreator(JobNetCreatorOptions options) {
		this.options = options;
		this.orderId = options.OrderId.Value();
		this.jobChain = options.JobChainName.Value();
		this.clusterId = options.SCHEDULER_ID.Value();
		this.checkJobNetTexture = options.CheckJobNetTexture.value();
        this.startOption = options.StartOption.asEnum();
	}

	@Override
	public void run() {
		
        if(startOption.createUUID())
            convertConfigFilesToDB();
        else
            logger.info("For " + startOption.name() + " it is not necessary to create a new representation of the Jobnet in the batabase.");

	}

	public void convertConfigFilesToDB() {

        logger.info(msg.getMsg(JOBNETC_I_0001,orderId,jobChain));
        bootstrapOrder = new JobNetBootstrapOrder(options);
        bootstrapOrder.createJobNet();

        if (checkJobNetTexture) {
            logger.info(msg.getMsg(JOBNETC_I_0006));
            bootstrapOrder.validateJobNet();
        } else {
            logger.info(msg.getMsg(JOBNETC_I_0005));
        }

        logger.info(msg.getMsg(JOBNETC_I_0002,getBootstrapOrder().getJobNet().size()));
        jobnetId = writeToDB();

        logger.info(msg.getMsg(JOBNETC_I_0003));

	}

	private long writeToDB() {

        JobNetConifigurationLayer layer = new JobNetConifigurationLayer(options);
        long netId = 0;
		try {
            layer.beginTransaction();

			// Create (or update) one record for each order in table SCHEDULER_JOB_NET_NODES
			for(JobNetOrder o : bootstrapOrder.getJobNet().values()) {
				JobNetOrderDBAdapter adapter = new JobNetOrderDBAdapter(layer, o);
                JobNetNodeDBItem nodeItem = adapter.createJobNetNode();
                o.setNodeDBItem(nodeItem);
                logger.info("Create record with ID " + nodeItem.getNodeId());
			}
	
			// Create the edges for the jobnet in table SCHEDULER_JOB_NET_EDGES
			JobNetEdgesDBItem edgeItem;
			for(JobNetOrder parent : bootstrapOrder.getJobNet().values()) {
				JobNetOrderDBAdapter parentAdapter = new JobNetOrderDBAdapter(layer, parent);
				if (parentAdapter.itemExists()) {
					for(JobNetOrder child : parent.getSuccessors()) {
						JobNetOrderDBAdapter childAdapter = new JobNetOrderDBAdapter(layer, child);
						if (childAdapter.itemExists()) {
                            logger.info("Create edges for " + parentAdapter.getItem().getNodeId() + " - " + childAdapter.getItem().getNodeId());
							parentAdapter.createSuccessor(childAdapter.getItem());
							childAdapter.createPredecessor(parentAdapter.getItem());
						}
					}
				}
			}

            // Create (or update) one record for the whole jobnet in table SCHEDULER_JOB_NET
            JobNetOrderDBAdapter jobnetAdapter = new JobNetOrderDBAdapter(layer, bootstrapOrder);
            JobNetDBItem jobnetItem = jobnetAdapter.createJobNet();
            jobnetAdapter.createJobNetCmdHistory(jobnetItem);
            netId = jobnetItem.getNetId();

			layer.commit();
		} finally {
			layer.closeSession();
		}
	    return netId;
    }

	public JobNetCreatorOptions getOptions() {
		return options;
	}

	public JobNetBootstrapOrder getBootstrapOrder() {
		return bootstrapOrder;
	}

	public long getNetId() {
		return jobnetId;
	}

}
