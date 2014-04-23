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
package com.sos.jobnet.db;

import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnetmodel.objects.JobnetNodeType;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @uthor ss
 * at 05.09.13 19:48
 */
public class JobNetContextDBReader {

    private static final Logger logger = Logger.getLogger(JobNetContextDBReader.class);

    private final JobNetEdgesDBLayer edgesDBLayer;
    private final JobNetPlanDBLayer planDBLayer;
    private final JobNetPlanDBItem planDBItem;
    private final List<JobNetPlanDBItem> nodeList = new ArrayList<JobNetPlanDBItem>();
    private final JobNetPlanTree tree;

    private Map<Long,JobNetPlanDBItem> buffer = new HashMap<Long,JobNetPlanDBItem>();
    private List<JobNetPlanDBItem> result = new ArrayList<JobNetPlanDBItem>();

    protected JobNetContextDBReader(JobNetPlanDBLayer planDBLayer, JobNetPlanDBItem planDBItem) {
        if (planDBItem == null)
            throw new JobNetException("The constructor parameter planDBItem should not be null");
        this.planDBLayer = planDBLayer;
        this.edgesDBLayer = new JobNetEdgesDBLayer(planDBLayer.getConfigurationFile());
        this.planDBItem = planDBItem;
        this.tree = new JobNetPlanTree(planDBItem);
        resolveJobnet();
    }

    public List<JobNetPlanDBItem> getMemberList() {
        return  nodeList;
    }

    public JobNetPlanTree getMemberTree() {
        return tree;
    }

    private void resolveJobnet() {
        if (!isJob(planDBItem)) addMember(planDBItem);
        iterateJobnet(planDBItem,tree);
        logger.info("The memberlist contains " + nodeList.size() + " elements.");
    }

    private void iterateJobnet(JobNetPlanDBItem item, JobNetPlanTree currentTree) {
        List<JobNetPlanDBItem> childNodes = getNodesOfNextLevel(item);
        for(JobNetPlanDBItem child : childNodes) {
            JobNetPlanTree newTree = currentTree.addSuccessor(child);
            addMember(child);
            iterateJobnet(child,newTree);
        }
    }

    private void addMember(JobNetPlanDBItem item) {
        nodeList.add(item);
    }

    public List<JobNetPlanDBItem> getNodesOfNextLevel(JobNetPlanDBItem item) {
        List<JobNetPlanDBItem> result = new ArrayList<JobNetPlanDBItem>();
        while(true) {
            if (isSubnet(item)) {
                result = getConnectors(item);
                break;
            }
            if (isConnector(item)) {
                result = getSubnetOrJob(item);
                break;
            }
            break;          // a job has no entries at next level
        }
        return result;
    }

    private List<JobNetPlanDBItem> getConnectors(JobNetPlanDBItem item) {
        if (isSubnet(item)) {
            Long subnetId = item.getNodeId();
            JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
            jobNetPlanFilter.setSubnetId(subnetId);
            jobNetPlanFilter.setNodeType(JobnetNodeType.CONNECTOR.name());
            planDBLayer.setFilter(jobNetPlanFilter);
            result = planDBLayer.getJobnetPlanList(0);

            // Tranform the result in an indexed HashMap
            buffer.clear();
            for(JobNetPlanDBItem i : result) {
                buffer.put(i.getPlanId(),i);
            }
            result.clear();

            // put the nodes in the right sequence to the resultlist
            iterateSubnet(item);

        }
        return result;
    }

    private void iterateSubnet(JobNetPlanDBItem item) {
        List<JobNetEdgesDBItem> edges = edgesDBLayer.getSuccessors(item.getJobNetNodeDBItem());
        for(JobNetEdgesDBItem edge : edges) {
            Long childId = edge.getChildNodeId();
            JobNetPlanDBItem childNode = planDBLayer.getByNodeIdOrNull(childId);
            if(buffer.containsKey(childNode.getPlanId()))
                result.add(childNode);
            if(result.size() == buffer.size())
                break;
            iterateSubnet(childNode);
        }
    }

    private List<JobNetPlanDBItem> getSubnetOrJob(JobNetPlanDBItem item) {
        List<JobNetPlanDBItem> result = new ArrayList<JobNetPlanDBItem>();
        if (isConnector(item)) {

            Long connectorId = item.getNodeId();

            // get all JOBS under the CONNECTOR
            JobNetPlanFilter jobNetPlanFilter = new JobNetPlanFilter();
            jobNetPlanFilter.setConnectorId(connectorId);
            planDBLayer.setFilter(jobNetPlanFilter);
            jobNetPlanFilter.setNodeType(JobnetNodeType.JOB.name());
            result = planDBLayer.getJobnetPlanList(0);

            // get all SUBNETs under the CONNECTOR
            List<JobNetEdgesDBItem> edges = edgesDBLayer.getSuccessors(item.getJobNetNodeDBItem());
            for(JobNetEdgesDBItem edge : edges) {
                Long childId = edge.getChildNodeId();
                JobNetPlanDBItem childNode = planDBLayer.getByNodeIdOrNull(childId);
                if(isSubnet(childNode))
                    result.add(childNode);
            }
        }
        return result;
    }

    private boolean isSubnet(JobNetPlanDBItem item) {
        return item.getNodeType().equalsIgnoreCase(JobnetNodeType.SUBNET.name());
    }

    private boolean isConnector(JobNetPlanDBItem item) {
        return item.getNodeType().equalsIgnoreCase(JobnetNodeType.CONNECTOR.name());
    }

    private boolean isJob(JobNetPlanDBItem item) {
        return item.getNodeType().equalsIgnoreCase(JobnetNodeType.JOB.name());
    }

}
