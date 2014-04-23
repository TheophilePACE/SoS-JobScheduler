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
import java.util.Date;

import com.sos.dailyschedule.db.DailyScheduleDBItem;
import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dialog.interfaces.ITableView;
import com.sos.hibernate.classes.DbItem;
import com.sos.scheduler.history.db.SchedulerOrderHistoryDBItem;
import com.sos.scheduler.history.db.SchedulerTaskHistoryDBItem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;

public class SOSDashboardTableViewExecuted extends SOSDashboardTableView implements ITableView {
	private static final int	NUMBER_OF_COLUMNS_IN_GRID	= 9;
 
	private boolean				ignoreListEnabled			= true;

	public SOSDashboardTableViewExecuted(Composite composite_) {
		super(composite_);
		colPosForSort = 4;
	}

	private void showLog() {
		this.showLog(tableList);
	}

	@Override
	public void createMenue() {
		Menu contentMenu = new Menu(tableList);
		tableList.setMenu(contentMenu);
		// =============================================================================================
		MenuItem showLogs = new MenuItem(contentMenu, SWT.PUSH);
		showLogs.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_log_in_new_tab));
		showLogs.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				SosTabLogItem tbtmLog = new SosTabLogItem(DashBoardConstants.conTabLOG, logTabFolder);
				logTabFolder.setSelection(tbtmLog);
				showLog();
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
				//tableDataProvider.setShowJobChains(false);
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
				//tableDataProvider.setShowJobs(false);
				tableDataProvider.setShowJobChains(orderJobs.getSelection());
				buildTable();
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		// =============================================================================================
        final MenuItem showRunning = new MenuItem(contentMenu, SWT.CHECK);
        showRunning.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_running));
        showRunning.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
              public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                  tableDataProvider.setShowRunning(showRunning.getSelection());
                  buildTable();                  
              }

              public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
              }
          });
       // =============================================================================================
          final MenuItem showWithError = new MenuItem(contentMenu, SWT.CHECK);
            showWithError.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_with_error));
            showWithError.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
                public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                    tableDataProvider.setShowWithError(showWithError.getSelection());
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
			    if (today.getSelection()) {
	                tableDataProvider.setFrom(new Date());
	                tableDataProvider.setTo(new Date());
			    }
				getList();
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		new MenuItem(contentMenu, SWT.SEPARATOR);
		// =============================================================================================
		MenuItem addIgnore = new MenuItem(contentMenu, SWT.PUSH);
		addIgnore.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_addIgnore));
		addIgnore.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				TableItem[] t = tableList.getSelection();
				if (t.length > 0) {
					DbItem h = (DbItem) t[0].getData();
					
					tableDataProvider.addToIgnorelist(prefs, h);
				}
				getList();
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		
		// =============================================================================================
		MenuItem addIgnoreJobChainOrder = new MenuItem(contentMenu, SWT.PUSH);
		addIgnoreJobChainOrder.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_addIgnoreJobChainOrder));
		addIgnoreJobChainOrder.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				TableItem[] t = tableList.getSelection();
				if (t.length > 0) {
					if (t[0].getData().getClass() == SchedulerOrderHistoryDBItem.class){
					SchedulerOrderHistoryDBItem h = (SchedulerOrderHistoryDBItem) t[0].getData();
					h.setOrderId(null);
					tableDataProvider.addToIgnorelist(prefs, h);
				}
				getList();
				}
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});

		
		if (ignoreListEnabled) {
			MenuItem disableIgnore = new MenuItem(contentMenu, SWT.PUSH);
			disableIgnore.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_disableIgnore));
			disableIgnore.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					ignoreListEnabled = !ignoreListEnabled;
					tableDataProvider.disableIgnoreList(prefs);
					getList();
					createMenue();
				}

				public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
				}
			});
		}
		else {
			MenuItem enableIgnore = new MenuItem(contentMenu, SWT.PUSH);
			enableIgnore.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_enableIgnore));
			enableIgnore.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					ignoreListEnabled = !ignoreListEnabled;
					tableDataProvider.setIgnoreList(prefs);
					createMenue();
					getList();
				}

				public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
				}
			});
		}
		// =============================================================================================
		MenuItem removeIgnore = new MenuItem(contentMenu, SWT.PUSH);
		removeIgnore.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_resetIgnore));
		removeIgnore.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tableDataProvider.resetIgnoreList(prefs);
				getList();
			}

			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		new MenuItem(contentMenu, SWT.SEPARATOR);
		// =============================================================================================
		MenuItem reset = new MenuItem(contentMenu, SWT.PUSH);
		reset.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_Reset));
		reset.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				tableDataProvider.resetFilter();

		        standAlone.setSelection(true);
		        orderJobs.setSelection(true);
		        showRunning.setSelection(false);
		        showWithError.setSelection(false);
		        today.setSelection(false);


		 
				tableDataProvider.setIgnoreList(prefs);
				tableDataProvider.setFrom(new Date());
				tableDataProvider.setTo(new Date());
				sosDashboardHeader.reset();
				// sosDashboardHeaderPlanned.reset();
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
                        //Nicht schön....
                        DbItem h = null;
                        if (t[0].getData().getClass() == SchedulerOrderHistoryDBItem.class){
                             h = (SchedulerOrderHistoryDBItem) t[0].getData();
                        }else {
                             h = (SchedulerTaskHistoryDBItem) t[0].getData();
                        }
                        start(h);
                    }
                }

                public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                }
            });
        }		
		
	}

	public void createTable() {
		mainViewComposite = new Composite(leftTabFolder, SWT.NONE);
		GridLayout layout = new GridLayout(NUMBER_OF_COLUMNS_IN_GRID, false);
		mainViewComposite.setLayout(layout);
		sosDashboardHeader = new SosDashboardHeader(mainViewComposite, this);
		tableList = new SosExecutedTable(mainViewComposite, SWT.FULL_SELECTION);
		super.createTable();
	}
}
