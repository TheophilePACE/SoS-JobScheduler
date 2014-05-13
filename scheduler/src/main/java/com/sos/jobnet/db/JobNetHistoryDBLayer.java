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
package com.sos.jobnet.db;

import com.sos.hibernate.layer.SOSHibernateDBLayer;
import com.sos.jobnet.classes.JobNetException;
import com.sos.jobnet.classes.JobNetStatus;
import com.sos.jobnet.classes.NodeStatus;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.File;
import java.util.Date;
import java.util.List;

// import org.apache.log4j.Logger;

/**
 * 
 * \class SchedulerJobNetHistoryDBLayer \brief SchedulerJobNetHistoryDBLayer -
 * 
 * \details
 * 
 * \section SchedulerJobNetHistoryDBLayer.java_intro_sec Introduction
 * 
 * \section SchedulerJobNetHistoryDBLayer.java_samples Some Samples
 * 
 * \code .... code goes here ... \endcode
 * 
 * <p style="text-align:center">
 * <br />
 * --------------------------------------------------------------------------- <br />
 * APL/Software GmbH - Berlin <br />
 * ##### generated by ClaviusXPress (http://www.sos-berlin.com) ######### <br />
 * ---------------------------------------------------------------------------
 * </p>
 * \author Uwe Risse \version 13.08.2013 \see reference
 * 
 * Created on 13.08.2013 14:40:18
 */

public class JobNetHistoryDBLayer extends SOSHibernateDBLayer {
	

 
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String START_TIME_TO = "startTimeTo";
    private static final String START_TIME_FROM = "startTimeFrom";
    private static final String NET_ID = "netId";
    private static final String HISTORY_ID = "historyId";
    private static final String EXIT_CODE = "exitCode";
    private static final String JOBNET_ID = "jobnetId";
    private static final String STATUS = "status";
    private static final String JOBNET = "jobNet";
    private static final String UUID = "uuid";
    private static final String SCHEDULER_ID = "schedulerId";
    
    private final static Logger logger = Logger.getLogger(JobNetHistoryDBLayer.class);

    @SuppressWarnings("unused")
    private final String conClassName = "SchedulerJobNetHistoryDBLayer";
    public final String conSVNVersion = "$Id: SchedulerJobNetHistoryDBLayer.java 20037 2013-05-03 04:55:36Z ss $";


 

    private JobNetHistoryFilter filter = null;


    public JobNetHistoryDBLayer(File configurationFile_, String schedulerId_) {
        super();

        this.filter = new JobNetHistoryFilter();

        this.filter.setSchedulerId(schedulerId_);

        this.setConfigurationFile(configurationFile_);
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(UUID);
        this.filter.setSchedulerId(schedulerId_);
    }

    public JobNetHistoryDBLayer(File configurationFile_) {
        super();
        this.filter = new JobNetHistoryFilter();

        this.setConfigurationFile(configurationFile_);
        this.filter.setDateFormat(DATE_FORMAT);
        this.filter.setOrderCriteria(UUID);
     }
    
    private Query setQueryParams(String hql) {
        Query query = session.createQuery(hql);

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            query.setTimestamp(START_TIME_FROM, filter.getStartTimeFrom());
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            query.setTimestamp(START_TIME_TO, filter.getStartTimeTo());
        }

        if (filter.getSchedulerId() != null && !filter.getSchedulerId().equals("")) {
            query.setParameter(SCHEDULER_ID, filter.getSchedulerId());
        }

        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            query.setParameter(UUID, filter.getUuid());
        }

        if (filter.getStatus() != null && !filter.getStatus().equals("")) {
            query.setInteger(STATUS, filter.getStatus());
        }
        
        if (filter.getHistoryId() != null   ) {
            query.setParameter(HISTORY_ID, filter.getHistoryId());
        }

        if (filter.getNetId() != null ) {
            query.setParameter(NET_ID, filter.getNetId());
        }

        if (filter.getExitCode() != null   ) {
            query.setParameter(EXIT_CODE, filter.getExitCode());
        }
        
        if (filter.getJobnetId() != null   ) {
            query.setParameter(JOBNET_ID, filter.getJobnetId());
        }

        return query;
    }

    public int delete() {

        String hql = "delete from JobNetHistoryDBItem " + getWhere();

        Query query = setQueryParams(hql);
        int row = query.executeUpdate();

        return row;
    }

    private String getWhere() {
        String where = "";
        String and = "";

        if (filter.getStartTimeFrom() != null && !filter.getStartTimeFrom().equals("")) {
            where += and + " startTime>= :startTimeFrom";
            and = " and ";
        }

        if (filter.getStartTimeTo() != null && !filter.getStartTimeTo().equals("")) {
            where += and + " startTime< :startTimeTo";
            and = " and ";
        }

        if (!filter.getSchedulerId().equals("")) {
            where += and + " schedulerId = :schedulerId";
            and = " and "; 
        }

        if (filter.getUuid() != null && !filter.getUuid().equals("")) {
            where += and + " uuid = :uuid";
            and = " and ";
        }
      
        if (filter.getNetId() != null) {
            where += and + " netId = :netId";
            and = " and ";
        }

        if (filter.getStatus() != null) {
            where += and + " status  = :status";
            and = " and ";
        }

        if (filter.getExitCode() != null) {
            where += and + " exitCode  = :exitCode";
            and = " and ";
        }     
        
        if (filter.getHistoryId() != null) {
            where += and + " historyId  = :historyId";
            and = " and ";
        }     
                
        if (filter.getJobnetId() != null && !filter.getJobnetId().equals("")) {
            where += and + " jobnetId = :jobnetId";
            and = " and ";
        }

        if (where.trim().equals("")) {

        } else {
            where = "where " + where;
        }
        return where;

    }

    public List<JobNetHistoryDBItem> getSchedulerJobNetHistoryList(int limit) {
        initSession();
     

        Query query = setQueryParams("from JobNetHistoryDBItem jobnet " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());

        if (limit > 0) {
            query.setMaxResults(limit);
        }

        @SuppressWarnings("unchecked")
        List<JobNetHistoryDBItem> jobnetHistoryList = query.list();
        return jobnetHistoryList;

    }


    public List<JobNetHistoryDBItem> getSchedulerJobNetHistoryList(JobNetDBItem schedulerJobNetDBItem, int limit) {
        initSession();
     
        resetFilter();
        filter.setNetId(schedulerJobNetDBItem.getNetId());

        Query query = setQueryParams("from JobNetHistoryDBItem jobnet " + getWhere() + this.filter.getOrderCriteria() + this.filter.getSortMode());

        if (limit > 0) {
            query.setMaxResults(limit);
        }

        @SuppressWarnings("unchecked")
        List<JobNetHistoryDBItem> jobnetHistoryList = query.list();
        return jobnetHistoryList;

    }

   
  
	public boolean isJobnetEnded(String uuid) {
		JobNetHistoryFilter schedulerJobNetFilter = new JobNetHistoryFilter();
		schedulerJobNetFilter.setUuid(uuid);
		setFilter(schedulerJobNetFilter);
		List<JobNetHistoryDBItem> schedulerJobNetDBItems  = getSchedulerJobNetHistoryList(0);
		if(schedulerJobNetDBItems != null) {
            NodeStatus state = NodeStatus.valueOf(schedulerJobNetDBItems.get(0).getStatus());
		    return !(state != NodeStatus.FINISHED && state != NodeStatus.ERROR && state != NodeStatus.IGNORED);
		}else {
		    return true;
		}
	}
    
	public boolean isJobnetStarted(String uuid) {
		JobNetHistoryFilter schedulerJobNetFilter = new JobNetHistoryFilter();
		schedulerJobNetFilter.setUuid(uuid);
		setFilter(schedulerJobNetFilter);

		List<JobNetHistoryDBItem> schedulerJobNetDBItems  = getSchedulerJobNetHistoryList(0);
        if(schedulerJobNetDBItems != null) {
            NodeStatus state = NodeStatus.valueOf(schedulerJobNetDBItems.get(0).getStatus());
            return (state != NodeStatus.NOT_PROCESSED) ;
        }else {
            return false;
        }
	}
	
	public boolean isJobnetRunning(String uuid) {
		return(!isJobnetEnded(uuid) && isJobnetStarted(uuid));
	}

  
    public JobNetHistoryFilter getFilter() {
        return filter;
    }

    public void resetFilter() {
        filter = new JobNetHistoryFilter();
    }

    public void setFilter(JobNetHistoryFilter filter) {
        this.filter = filter;
    }
	
	public void deleteJobnetInstance(String uuid) {
		JobNetHistoryFilter filter = new JobNetHistoryFilter();
		filter.setUuid(uuid);
		setFilter(filter);
    	if (!getSession().isOpen()) initSession();
		Query query = setQueryParams("from JobNetHistoryDBItem plan " + getWhere());
		@SuppressWarnings("unchecked")
		List<JobNetHistoryDBItem> resultList = query.list();
		logger.info(resultList.size() + " records deleted for jobnet instance " + uuid + " deleted.");
		for(JobNetHistoryDBItem item : resultList) {
			delete(item);
		}
	}

    // update the history record for the jobnetz
    public JobNetHistoryDBItem startHistory(JobNetHistoryDBItem historyRecord) {
        Date now = new Date();
        historyRecord.setStatus(JobNetStatus.RUNNING.getIndex());
        historyRecord.setStartTime(now);
        historyRecord.setRunTime(now);
        historyRecord.setExitCode(0);
        historyRecord.setExitMessage("");
        historyRecord.setModified(now);
        historyRecord.setModifiedBy(this.getClass().getName() + ":startHistory");
        updateHistoryRecord(historyRecord);
        return historyRecord;
    }

    // update the history record for the jobnetz
    public void updateHistory(JobNetHistoryDBItem historyRecord) {
        Date now = new Date();
        historyRecord.setRunTime(now);
        historyRecord.setModified(now);
        historyRecord.setModifiedBy(this.getClass().getName() + ":updateHistory");
        updateHistoryRecord(historyRecord);
    }

    // update the history record for the jobnetz
    public void endHistory(JobNetHistoryDBItem historyRecord) {
        Date now = new Date();
        boolean hasError = (historyRecord.getStatus()!=0);
        JobNetStatus endState = (hasError) ? JobNetStatus.ERROR : JobNetStatus.FINISHED;
        historyRecord.setStatus( endState.getIndex() );
        historyRecord.setEndTime(now);
        historyRecord.setRunTime(now);
        historyRecord.setModified(now);
        historyRecord.setModifiedBy(this.getClass().getName() + ":endHistorySuccessful");
        updateHistoryRecord(historyRecord);
    }

    // update the history record for the jobnetz
    public void updateHistoryWithError(JobNetHistoryDBItem historyRecord, Integer exitCode, String exitMessage) {
        Date now = new Date();
        historyRecord.setEndTime(now);
        historyRecord.setRunTime(now);
        historyRecord.setModified(now);
        Integer currentExitCode = historyRecord.getExitCode();
        if (exitCode >= currentExitCode) {
            historyRecord.setExitCode(exitCode);
            historyRecord.setExitMessage(exitMessage);
        }
        historyRecord.setModifiedBy(this.getClass().getName() + ":endHistoryWithError");
        updateHistoryRecord(historyRecord);
    }

    public Interval getRunTime(JobNetHistoryDBItem historyRecord) {
        DateTime fromDate = new DateTime(historyRecord.getStartTime());
        DateTime toDate = new DateTime(historyRecord.getRunTime());
        return new Interval(fromDate, toDate);
    }

    // update the history record for the jobnet
    private void updateHistoryRecord(JobNetHistoryDBItem historyRecord) {
        beginTransaction();
        saveOrUpdate(historyRecord);
        commit();
    }

    public JobNetHistoryDBItem getJobNetInstance(String uuid) {
        JobNetHistoryFilter filter = new JobNetHistoryFilter();
        filter.setUuid(uuid);
        setFilter(filter);
        List<JobNetHistoryDBItem> records = getSchedulerJobNetHistoryList(0);
        if (records.size() > 1)
            throw new JobNetException("More than one instance detected for " + uuid + " in table SCHEDULER_JOB_NET_HISTORY");
        logger.info("The jobnet with id " + uuid + " contains " + records.size() + " nodes.");
        return (records.size() == 1) ? records.get(0) : null;
    }

    public boolean isJobNetInstance(String uuid) {
        JobNetHistoryDBItem record = getJobNetInstance(uuid);
        return (record != null);
    }

}
