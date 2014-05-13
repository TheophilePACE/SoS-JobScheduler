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
import com.sos.jobnet.db.*;
import com.sos.jobnet.options.JobNetCreatorOptions;
import com.sos.localization.Messages;
import org.apache.log4j.Logger;

import java.io.File;

public class JobNetConifigurationLayer {

	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: JobNetConifigurationLayer.java 21040 2013-09-09 16:51:14Z ss $";

	private final static Logger logger = Logger.getLogger(JobNetConifigurationLayer.class);

	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	private final static String JOBNETC_I_0009 = "JOBNETC_I_0009"; // SAVE: jobchain=%1$s, node=%2$s, schedulerid=%3$s
	private final static String JOBNETC_E_0005 = "JOBNETC_E_0005"; // could not create %1$s for %2$s - no record found in job net nodes.

	private final JobNetCreatorOptions options;

	private final JobNetDBLayer dbLayerJobnet;
	private final JobNetCmdHistoryDBLayer dbLayerCommands;
	private final JobNetNodeDBLayer dbLayerNodes;
	private final JobNetEdgesDBLayer dbLayerEdges;
    private final JobNetHistoryDBLayer dbLayerHistory;
    private final JobNetPlanDBLayer dbLayerPlan;

    public JobNetConifigurationLayer(JobNetCreatorOptions options) {
        File configFile = new File(options.hibernate_connection_config_file.Value());
        String clusterId = options.SCHEDULER_ID.Value();
        this.dbLayerJobnet = new JobNetDBLayer(configFile);
        this.dbLayerCommands = new JobNetCmdHistoryDBLayer(configFile);
        this.dbLayerNodes = new JobNetNodeDBLayer(configFile,clusterId);
        this.dbLayerHistory = new JobNetHistoryDBLayer(configFile,clusterId);
        this.dbLayerPlan = new JobNetPlanDBLayer(configFile);
        this.dbLayerEdges = new JobNetEdgesDBLayer(configFile);
        this.options = options;
	}

    public void beginTransaction() {
        dbLayerJobnet.beginTransaction();
        dbLayerCommands.beginTransaction();
        dbLayerNodes.beginTransaction();
        dbLayerEdges.beginTransaction();		// is necessary for writing the edges
        dbLayerHistory.beginTransaction();
        dbLayerPlan.beginTransaction();
    }

    public void commit() {
        dbLayerJobnet.commit();
    }

    public void deleteAllEntriesInJobNetTables() {
        dbLayerJobnet.setFilter(new JobNetFilter());
        dbLayerCommands.setFilter(new JobNetCmdHistoryFilter());
        dbLayerNodes.setFilter(new JobNetNodeFilter());
        dbLayerEdges.setFilter(new JobNetEdgesFilter());
        dbLayerPlan.setFilter(new JobNetPlanFilter());
        dbLayerHistory.setFilter( new JobNetHistoryFilter() );
        beginTransaction();
        dbLayerJobnet.delete();
        dbLayerCommands.delete();
        dbLayerNodes.delete();
        dbLayerEdges.delete();
        dbLayerHistory.delete();
        dbLayerPlan.delete();
        commit();
    }

    public void closeSession() {
        dbLayerJobnet.closeSession();
        dbLayerCommands.closeSession();
        dbLayerNodes.closeSession();
        dbLayerEdges.closeSession();
        dbLayerHistory.closeSession();
    }

    public JobNetDBLayer getDbLayerJobnet() {
        return dbLayerJobnet;
    }

    public JobNetCmdHistoryDBLayer getDbLayerCommands() {
        return dbLayerCommands;
    }

    public JobNetNodeDBLayer getDbLayerNodes() {
        return dbLayerNodes;
    }

    public JobNetEdgesDBLayer getDbLayerEdges() {
        return dbLayerEdges;
    }

    public JobNetHistoryDBLayer getDbLayerHistory() {
        return dbLayerHistory;
    }

    public JobNetPlanDBLayer getDbLayerPlan() {
        return dbLayerPlan;
    }

    public JobNetCreatorOptions getOptions() {
        return options;
    }

}
