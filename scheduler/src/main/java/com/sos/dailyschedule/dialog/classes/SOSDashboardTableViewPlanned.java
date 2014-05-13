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
package com.sos.dailyschedule.dialog.classes;

import java.io.File;
import java.util.Date;
 
import com.sos.scheduler.model.commands.JSCmdModifyOrder;
import com.sos.scheduler.model.commands.JSCmdStartJob;
import com.sos.scheduler.model.objects.Spooler;

import com.sos.dailyschedule.db.DailyScheduleDBItem;
import com.sos.dashboard.globals.DashBoardConstants;

import com.sos.dialog.interfaces.ITableView;
import com.sos.hibernate.classes.DbItem;
 
 
import com.sos.scheduler.db.SchedulerInstancesDBItem;
import com.sos.scheduler.db.SchedulerInstancesDBLayer;
import com.sos.scheduler.history.db.SchedulerOrderHistoryDBItem;
import com.sos.scheduler.history.db.SchedulerOrderHistoryDBLayer;
import com.sos.scheduler.history.db.SchedulerTaskHistoryDBItem;
import com.sos.scheduler.history.db.SchedulerTaskHistoryDBLayer;
import com.sos.scheduler.model.SchedulerObjectFactory;

import org.eclipse.swt.SWT;
 
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;

public class SOSDashboardTableViewPlanned extends SOSDashboardTableView implements ITableView {

    private static final int                NUMBER_OF_COLUMNS_IN_GRID           = 9;
    private static final String conTabLOG = "Log";

    public SOSDashboardTableViewPlanned(Composite composite_) {
        super(composite_);
        colPosForSort = 4;

    }

  
    @Override
    public void createMenue() {
        Menu contentMenu = new Menu(tableList);
        tableList.setMenu(contentMenu);

        MenuItem showLog = new MenuItem(contentMenu, SWT.PUSH);

        showLog.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_log_in_new_tab));
        showLog.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                SosTabLogItem tbtmLog = new SosTabLogItem(conTabLOG, logTabFolder);
                logTabFolder.setSelection(tbtmLog);
                showLog(tableList);
 
            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================

        new MenuItem(contentMenu, SWT.SEPARATOR);

        final MenuItem standAlone = new MenuItem(contentMenu, SWT.CHECK);
        standAlone.setSelection(true);
        standAlone.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_stand_alone_jobs));
        standAlone.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                tableDataProvider.setShowJobs(standAlone.getSelection());
               // tableDataProvider.setOnlyJobChains(false);

                buildTable();

            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================
        final MenuItem orderJobs = new MenuItem(contentMenu, SWT.CHECK);
        orderJobs.setSelection(true);
        orderJobs.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_job_chains));
        orderJobs.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                tableDataProvider.setShowJobChains(orderJobs.getSelection());
             //   tableDataProvider.setOnlyJobs(false);

                buildTable();

            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================
        final MenuItem late = new MenuItem(contentMenu, SWT.CHECK);

        late.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_late));
        late.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                tableDataProvider.setLate(late.getSelection());
                buildTable();

            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================
        final MenuItem today = new MenuItem(contentMenu, SWT.CHECK);
        today.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_Today));
        today.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (today.getSelection()){
                    tableDataProvider.setFrom(new Date());
                    tableDataProvider.setTo(new Date());
                    detailHistoryDataProvider.setTo(new Date());
            
                }else {
            
                }

                getList();
            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================
        final MenuItem executed = new MenuItem(contentMenu, SWT.CHECK);
        executed.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_only_executed));
        executed.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (executed.getSelection()) {
                   tableDataProvider.setStatus(DashBoardConstants.STATUS_EXECUTED);
                }else {
                    tableDataProvider.setStatus("");
                }
                buildTable();
            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });
        // =============================================================================================
        final MenuItem waiting = new MenuItem(contentMenu, SWT.CHECK);

        waiting.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_waiting));
        waiting.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                
                if (executed.getSelection()) {
                    tableDataProvider.setStatus(DashBoardConstants.STATUS_WAITING);
                 }else {
                    tableDataProvider.setStatus("");
                 }
                buildTable();
            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });

        // =============================================================================================
        MenuItem reset = new MenuItem(contentMenu, SWT.PUSH);
        reset.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_Reset));
        reset.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                sosDashboardHeader.reset();
                tableDataProvider.resetFilter();
                tableDataProvider.setFrom(new Date());
                tableDataProvider.setTo(new Date());
                detailHistoryDataProvider.setTo(new Date());

                standAlone.setSelection(true);
                orderJobs.setSelection(true);
                late.setSelection(false);
                today.setSelection(false);
                executed.setSelection(false);
                waiting.setSelection(false);

                getList();
            }

            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
            }
        });

        // =============================================================================================

        if (objOptions != null && objOptions.getEnableJobStart().isTrue()) {
            new MenuItem(contentMenu, SWT.SEPARATOR);
            MenuItem startJob = new MenuItem(contentMenu, SWT.PUSH);

            startJob.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_start_now));
            startJob.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
                public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                    TableItem[] t = tableList.getSelection();
                    if (t.length > 0) {
                        DailyScheduleDBItem h = (DailyScheduleDBItem) t[0].getData();
                        SchedulerInstancesDBItem schedulerInstancesDBItem = start(h);
                        actualizePlan(h,schedulerInstancesDBItem);
                    }

                }

                public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                }
            });
        }
    }

  
    protected void actualizePlan(DailyScheduleDBItem dailyScheduleDBItem,SchedulerInstancesDBItem schedulerInstancesDBItem) {

        Date now = new Date();
 
         if (schedulerInstancesDBItem != null) {
            SchedulerObjectFactory objSchedulerObjectFactory = new SchedulerObjectFactory(schedulerInstancesDBItem.getHostName(), schedulerInstancesDBItem.getTcpPort());
            objSchedulerObjectFactory.initMarshaller(Spooler.class);
            if (dailyScheduleDBItem.isOrderJob()) {
                try {
                  
                    if (dailyScheduleDBItem.getScheduleExecuted() == null) {

                         
                        schedulerOrderHistoryDBLayer.getFilter().setOrderid(dailyScheduleDBItem.getOrderId());
                        schedulerOrderHistoryDBLayer.getFilter().setJobchain(dailyScheduleDBItem.getJobChain());
                        schedulerOrderHistoryDBLayer.getFilter().setSchedulerId(dailyScheduleDBItem.getSchedulerId());
                        schedulerOrderHistoryDBLayer.getFilter().setStartTime(now);
                        schedulerOrderHistoryDBLayer.getFilter().setOrderCriteria(DashBoardConstants.ORDER_CRITERIA);
                        schedulerOrderHistoryDBLayer.getFilter().setSortMode(DashBoardConstants.SORT_MODE);  

                        SchedulerOrderHistoryDBItem orderHistory = schedulerOrderHistoryDBLayer.getOrderHistoryItem();

                        tableDataProvider.beginTransaction();

                        DailyScheduleDBItem dailyScheduleDBItem2 = (DailyScheduleDBItem) tableDataProvider.getSession().load(DailyScheduleDBItem.class, dailyScheduleDBItem.getId());
                        dailyScheduleDBItem2.setScheduleExecuted(new Date());
                        if (orderHistory != null) {
                            dailyScheduleDBItem2.setSchedulerOrderHistoryId(orderHistory.getHistoryId());
                        }
                        tableDataProvider.update(dailyScheduleDBItem2);
                        tableDataProvider.commit();
                   }
                } catch (Exception ee) {

                    ee.printStackTrace();
                } finally {
                    this.RestoreCursor();
                }

            } else {

                try {
 
                    if (dailyScheduleDBItem.getScheduleExecuted() == null) {

                        schedulerTaskHistoryDBLayer.getFilter().setJobname(dailyScheduleDBItem.getJob());
                        schedulerTaskHistoryDBLayer.getFilter().setSchedulerId(dailyScheduleDBItem.getSchedulerId());
                        schedulerTaskHistoryDBLayer.getFilter().setStartTime(now);
                        schedulerTaskHistoryDBLayer.getFilter().setOrderCriteria(DashBoardConstants.ORDER_CRITERIA);
                        schedulerTaskHistoryDBLayer.getFilter().setSortMode(DashBoardConstants.SORT_MODE);

                        SchedulerTaskHistoryDBItem taskHistory = schedulerTaskHistoryDBLayer.getHistoryItem();

                        tableDataProvider.beginTransaction();

                        DailyScheduleDBItem dailyScheduleDBItem2 = (DailyScheduleDBItem) tableDataProvider.getSession().load(DailyScheduleDBItem.class, dailyScheduleDBItem.getId());
                        dailyScheduleDBItem2.setScheduleExecuted(new Date());
                        if (taskHistory != null) {
                            dailyScheduleDBItem2.setSchedulerHistoryId(taskHistory.getLogId());
                        }

                        tableDataProvider.update(dailyScheduleDBItem2);
                        tableDataProvider.commit();
                    }
                }

                catch (Exception ee) {
                    ee.printStackTrace();
                } finally {
                    getList();
                    this.RestoreCursor();
                }
            }
        }
        // Leider bekomme ich nicht die HistoryId in der Antwort des Startens.

    }

 
    public void createTable () {

        mainViewComposite = new Composite(leftTabFolder, SWT.NONE);
        GridLayout layout = new GridLayout(NUMBER_OF_COLUMNS_IN_GRID, false);
        mainViewComposite.setLayout(layout);
        sosDashboardHeader = new SosDashboardHeader(mainViewComposite, this);
        tableList = new SosPlannedTable(mainViewComposite,SWT.FULL_SELECTION);
        

        super.createTable();
    }
    
   

 

}
