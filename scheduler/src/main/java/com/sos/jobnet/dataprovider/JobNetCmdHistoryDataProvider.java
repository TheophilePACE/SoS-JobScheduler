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
package com.sos.jobnet.dataprovider;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Table;
import org.hibernate.Session;

import com.sos.hibernate.classes.DbItem;
import com.sos.hibernate.interfaces.ISOSDashboardDataProvider;
import com.sos.hibernate.interfaces.ISOSHibernateDataProvider;
import com.sos.jobnet.classes.SosJobNetCmdHistoryTableItem;
import com.sos.jobnet.classes.SosJobNetPlanTableItem;
import com.sos.jobnet.db.JobNetCmdHistoryDBItem;
import com.sos.jobnet.db.JobNetCmdHistoryDBLayer;
import com.sos.jobnet.db.JobNetCmdHistoryFilter;
import com.sos.jobnet.db.JobNetDBItem;
import com.sos.jobnet.db.JobNetDBLayer;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnet.db.JobNetPlanDBLayer;
import com.sos.jobnet.db.JobNetPlanFilter;


/**
 * \class JobNetCommandHistoryDataProvider
 * 
 * \brief JobNetCmdHistoryDataProvider -
 * 
 * \details
 * 
 * \section JobNetCommandHistoryDataProvider.java_intro_sec Introduction
 * 
 * \section JobNetCommandHistoryDataProvider.java_samples Some Samples
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
 * \author Uwe Risse \version 19.01.2012 \see reference
 * 
 * Created on 19.01.2012 09:31:01
 */

public class JobNetCmdHistoryDataProvider implements ISOSHibernateDataProvider, ISOSDashboardDataProvider {

    @SuppressWarnings("unused")
    private final String conClassName = "JobNetCommandHistoryDataProvider";

    private List<JobNetCmdHistoryDBItem> listOfJobNetCmdHistoryDBItems = null;
    private JobNetCmdHistoryDBLayer jobNetCmdHistoryDBLayer = null;
    private static Logger logger = Logger.getLogger(JobNetCmdHistoryDataProvider.class);
    private Table tableJobNetPlan = null;

    public JobNetCmdHistoryDataProvider(File configurationFile, String schedulerId) {
        this.jobNetCmdHistoryDBLayer = new JobNetCmdHistoryDBLayer(configurationFile, schedulerId);
    }

    public JobNetCmdHistoryDataProvider(File configurationFile) {
        this.jobNetCmdHistoryDBLayer = new JobNetCmdHistoryDBLayer(configurationFile);
    }
    
    public JobNetCmdHistoryFilter getFilter() {
        return jobNetCmdHistoryDBLayer.getFilter();
    }

    public void resetFilter() {
        jobNetCmdHistoryDBLayer.setFilter(new JobNetCmdHistoryFilter());
    }

    public void getData() {
        listOfJobNetCmdHistoryDBItems = jobNetCmdHistoryDBLayer.getJobNetCmdHistoryList(0);
    }

    public void fillSchedulerIds(CCombo cbSchedulerId) {
        if (tableJobNetPlan != null) {
            Iterator jobNetPlanEntries = listOfJobNetCmdHistoryDBItems.iterator();
            while (jobNetPlanEntries.hasNext()) {
                JobNetPlanDBItem h = (JobNetPlanDBItem) jobNetPlanEntries.next();
                if (cbSchedulerId.indexOf(h.getJobnetNodeDBItem().getSchedulerId()) < 0) {
                    logger.debug("... cbSchedulerId --> : " + h.getJobnetNodeDBItem().getSchedulerId());
                    cbSchedulerId.add(h.getJobnetNodeDBItem().getSchedulerId());
                }
            }
        }
    }

   

    public void fillTable(Table table) {
        this.tableJobNetPlan = table;
        Iterator<JobNetCmdHistoryDBItem> jobnetCmdHistoryEntries = listOfJobNetCmdHistoryDBItems.iterator();
        while (jobnetCmdHistoryEntries.hasNext()) {
            DbItem h = jobnetCmdHistoryEntries.next();
            if (jobNetCmdHistoryDBLayer.getFilter().isFiltered(h)) {
            } else {

                final SosJobNetCmdHistoryTableItem newItemTableItem = new SosJobNetCmdHistoryTableItem(table, SWT.BORDER);
                newItemTableItem.setDBItem(h);
                newItemTableItem.setData(h);
                newItemTableItem.setColor();
                newItemTableItem.setColumns();
            }
        }
    }

    public File getConfigurationFile() {
        return jobNetCmdHistoryDBLayer.getConfigurationFile();

    }

    @Override
    public void beginTransaction() {
        jobNetCmdHistoryDBLayer.beginTransaction();
    }

    public void save(JobNetPlanDBItem dbItem) {
        jobNetCmdHistoryDBLayer.beginTransaction();
        jobNetCmdHistoryDBLayer.saveOrUpdate(dbItem);
        jobNetCmdHistoryDBLayer.commit();
    }

    @Override
    public void update(DbItem dbItem) {
        jobNetCmdHistoryDBLayer.update(dbItem);
    }

    public Session getSession() {
        return jobNetCmdHistoryDBLayer.getSession();
    }

    @Override
    public void commit() {
        jobNetCmdHistoryDBLayer.commit();
    }

    @Override
    public void setSchedulerId(String schedulerId) {
     }

    @Override
    public void setFrom(Date d) {
     }

    @Override
    public void setTo(Date d) {
     }

    @Override
    public void setSearchField(String s) {
     }

    @Override
    public void setShowJobs(boolean b) {
     }

    @Override
    public void setShowJobChains(boolean b) {
     }

    @Override
    public void setIgnoreList(Preferences prefs) {
     }

    @Override
    public void addToIgnorelist(Preferences prefs, DbItem h) {
     }

    @Override
    public void disableIgnoreList(Preferences prefs) {
     }

    @Override
    public void resetIgnoreList(Preferences prefs) {
     }

    @Override
    public void setLate(boolean b) {
     }

    @Override
    public void setStatus(String status) {
     }

    @Override
    public void setShowWithError(boolean b) {
        // TODO Auto-generated method stub
        
    }

	@Override
	public void setShowRunning(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
