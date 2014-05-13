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
package com.sos.jobnet.graph;

import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.db.JobNetEdgesDBItem;
import com.sos.jobnet.db.JobNetEdgesDBLayer;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnetmodel.objects.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * Adapter class to convert one node of the database representation of the jobnet into an internal jobnet node.
 */
public class JobNetPlanRecordAdapter implements ExternalJobnetNodeObject {
	
	private static Logger logger = Logger.getLogger(JobNetPlanRecordAdapter.class);
	
	private final JobNetEdgesDBLayer edgesDBReader;
	private final JobNetPlanDBItem dbRecord;
    private final List<String> successors;
    private final boolean isBootstrap;
    private final String uniqueName;
    private final JobnetNode netNode;
    
    private final static Pattern nodePattern = Pattern.compile("_step\\d\\d\\d");
	
	public JobNetPlanRecordAdapter(JobNetEdgesDBLayer edgesDBReader, JobNetPlanDBItem dbRecord) {
		this.dbRecord = dbRecord;
		this.edgesDBReader = edgesDBReader;
		this.successors = readSuccessors();
		this.isBootstrap = dbRecord.getBootstrap();
		this.uniqueName = dbRecord.getJobnetNodeDBItem().getNode();
        this.netNode = new JobnetNodeImpl(String.valueOf(dbRecord.getNodeId()), getType());

		logger.debug(getType().name() + ": Adapt jobnet node " + dbRecord.getJobnetNodeDBItem().getNode() + " (id " + dbRecord.getNodeId() + ")");
        netNode.setSuccessors(successors);
        netNode.setExternalNodeObject(this);
        netNode.setName(dbRecord.getJobnetNodeDBItem().getNode());
        
        NodeStatus s = NodeStatus.valueOf(dbRecord.getStatusNode());
        netNode.setNodeStatus(s);
	}
	
	private List<String> readSuccessors() {
		List<String> result = new ArrayList<String>();
		List<JobNetEdgesDBItem> jobnetEdgesList = edgesDBReader.getSuccessors(dbRecord.getJobnetNodeDBItem());
		for(JobNetEdgesDBItem edge : jobnetEdgesList) {
			logger.debug("Successor " + edge.getChildNodeId() + " detected.");
			result.add( String.valueOf(edge.getChildNodeId()) );
		}
		return result;
	}

	@Override
	public List<String> getSuccessors() {
		return successors;
	}

    @Override
    public JobnetNodeType getType() {
        JobnetNodeType result = null;
        try {
            result = JobnetNodeType.valueOf(dbRecord.getNodeType());
        } catch(Exception e) {
            throw new JobNetException("The nodetype " + dbRecord.getNodeType() + " of order " + dbRecord.getJobnetNodeDBItem().getNode() + " for UUID " + dbRecord.getUuid() + " is not valid.");
        }
        return result;
    }

    @Override
	public boolean hasSuccessors() {
		return (!successors.isEmpty());
	}

	@Override
	public boolean isBootstrap() {
		return isBootstrap;
	}

    @Override
    public String getInternalId() {
        return String.valueOf(dbRecord.getNodeId());
    }

    @Override
    public String getName() {
        return dbRecord.getJobNetNodeDBItem().getNode();
    }

    @Override
	public String getUniqueName() {
		return uniqueName;
	}

	@Override
	public JobnetNode getJobnetNodeObject() {
		return netNode;
	}

    @Override
    public String getTitle() {
        return dbRecord.getJobNetNodeDBItem().getNode();
    }

}
