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
package com.sos.dailyschedule.dialog;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.sos.JSHelper.Basics.JSVersionInfo;
import com.sos.dailyschedule.DailyScheduleDataProvider;
import com.sos.dailyschedule.dialog.classes.SOSDashboardTableViewExecuted;
import com.sos.dailyschedule.dialog.classes.SOSDashboardTableViewPlanned;
import com.sos.dailyschedule.dialog.classes.SOSTabJOE;
import com.sos.dailyschedule.dialog.classes.SosDialogGetHostPort;
import com.sos.dailyschedule.dialog.classes.SosHistoryTable;
import com.sos.dailyschedule.dialog.classes.SosTabJOC;
import com.sos.dailyschedule.dialog.classes.SosTabLogItem;
import com.sos.dashboard.globals.DashBoardConstants;
import com.sos.dashboard.globals.SOSDashboardOptions;
import com.sos.dialog.classes.FormBase;
import com.sos.eventing.dialog.classes.SOSTabEVENTS;
import com.sos.hibernate.classes.DbItem;
import com.sos.jobnet.dialog.classes.SOSTabJOBNET;
import com.sos.scheduler.db.SchedulerInstancesDBItem;
import com.sos.scheduler.db.SchedulerInstancesDBLayer;
import com.sos.scheduler.history.SchedulerHistoryDataProvider;

/*
 *
 */
public class DashboardShowDialog extends FormBase {
	private static final int RIGHT_MOUSE_BUTTON = 3;
    private static final String LIST_OF_SCHEDULERS = "list_of_schedulers";
	private static final String SOS_DASHBOARD = "SOS_DASHBOARD";
	private static final String TABNAME_DASHBOARD = "Dashboard";
	private static final String TABNAME_SCHEDULER_OPERATIONS_CENTER = "JOC";
	private static final String TABNAME_SCHEDULER_JOE = "JOE";
	private static final String TABNAME_SCHEDULER_EVENTS = "Events";
	private static final String TABNAME_SCHEDULER_JOBNET = "Jobnet";
	private static final int DEFAULT_PORT = 4444;
	private static final String DEFAULT_HOST = "localhost";
	private static final String conJOB_SCHEDULER_DASHBOARD = "JobScheduler Information Dashboard";
	private static final String conTabLOG = "Log";
	private SashForm logSashForm;
	private SashForm tablesSashForm;
	protected Composite composite = null;
	private Shell dashboardShell = null;
	private Display display;
	private Preferences prefs;
	private Label lbMessage;

	private Table tableHistoryDetail = null;
	public Group left;
	private Group right = null;
	private Group bottom = null;
	private Composite parent = null;
	private CTabFolder mainTabFolder = null;
	private CTabFolder browserTabFolder = null;
	private CTabFolder leftTabFolder = null;
	private CTabFolder logTabFolder = null;
	private Composite jocComposite;
	private Composite joeComposite;
	private Composite eventsComposite;
	private Composite dashboardComposite;

	private SOSTabEVENTS tbtmEvents;
	private SOSTabJOBNET tbtmJobnet;

	private SOSDashboardTableViewExecuted tableViewExecuted;
	private SOSDashboardTableViewPlanned tableViewPlanned;

	private SchedulerHistoryDataProvider detailHistoryDataProvider = null;
	private SchedulerHistoryDataProvider executedHistoryDataProvider = null;
	private DailyScheduleDataProvider dailyScheduleDataProvider = null;

 	
	private SchedulerInstancesDBLayer schedulerInstancesDBLayer;
	private SOSDashboardOptions objOptions = null;

	private boolean haveDb;

	public DashboardShowDialog(final Composite composite_) throws Exception {
		super(composite_, DashBoardConstants.conPropertiesFileName);
		composite = composite_;
	}

	private void showBrowser() {
		if (objOptions != null && objOptions.getEnableJOC().isTrue()) {
			String host = DEFAULT_HOST;
			int port = DEFAULT_PORT;
			String listOfScheduler = prefs.node(SOS_DASHBOARD).get(LIST_OF_SCHEDULERS, "");
			if (haveDb && listOfScheduler.equals("")) { // Den ersten aus den
														// Instances öffnen.
				schedulerInstancesDBLayer.initFilter();
				List<SchedulerInstancesDBItem> instances = schedulerInstancesDBLayer.getSchedulerInstancesList();
				if (instances.size() > 0) {
					SchedulerInstancesDBItem schedulerInstancesDBItem = instances.get(0);
					host = schedulerInstancesDBItem.getHostName();
					port = schedulerInstancesDBItem.getTcpPort();
				}
			} else {// Den ersten aus den Preferences öffnen
				String hostPort = listOfScheduler.split(",")[0];
				
				String [] hostport = hostPort.split(":");
				if (hostport.length == 0){
					   host = DEFAULT_HOST;
				}else{
				       host = hostport[0];
				}
				if (hostport.length <= 1){
				   port = DEFAULT_PORT;
				}else{
				   port = this.getIntValue(hostport[1], DEFAULT_PORT);
				}
			}

			jocComposite = new Composite(mainTabFolder, SWT.NONE);
			jocComposite.setLayout(new GridLayout());

			CTabItem tbtmJOCs = new CTabItem(mainTabFolder, SWT.NONE);
			tbtmJOCs.setText(TABNAME_SCHEDULER_OPERATIONS_CENTER);
			tbtmJOCs.setControl(jocComposite);

			browserTabFolder = new CTabFolder(jocComposite, SWT.NONE);
			createContextMenuBrowserTabfolder(browserTabFolder);
			createContextMenuBrowserTabfolder(mainTabFolder);

			browserTabFolder.setTabPosition(SWT.BOTTOM);
			browserTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			new SosTabJOC(host + ":" + port, browserTabFolder, host, port);
			if (!listOfScheduler.equals("")) { // Die weiteren öffnen
				String[] hostPorts = listOfScheduler.split(",");
				for (int i = 1; i < hostPorts.length; i++) {
					this.openScheduler(hostPorts[i]);
				}
			}
			browserTabFolder.setSelection(0);
		}

	}

	private void createMainWindow() {
		if (objOptions != null && (objOptions.getEnableJOC().isTrue() || objOptions.getEnableJOE().isTrue() || objOptions.getEnableEvents().isTrue())) {
			mainTabFolder = new CTabFolder(dashboardShell, SWT.NONE);
			mainTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			mainTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

			mainTabFolder.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent e) {
					setRefreshState();
				}
			});

			dashboardComposite = new Composite(mainTabFolder, SWT.NONE);
			dashboardComposite.setLayout(new GridLayout());

			CTabItem tbtmDashboard = new CTabItem(mainTabFolder, SWT.NONE);
			tbtmDashboard.setText(TABNAME_DASHBOARD);
			tbtmDashboard.setControl(dashboardComposite);
		} else {
			dashboardComposite = dashboardShell;
		}
	}

	private void setRefreshState() {
		if (haveDb) {

			tableViewPlanned.getSosDashboardHeader().getRefreshTimer().cancel();
			tableViewExecuted.getSosDashboardHeader().getRefreshTimer().cancel();
			if (tbtmEvents != null) {
				tbtmEvents.disableRefresh();
			}

			if (mainTabFolder != null) {
				if (mainTabFolder.getSelection() == null || mainTabFolder.getSelection().getText().equals(TABNAME_DASHBOARD)) {
					tableViewPlanned.getSosDashboardHeader().resetRefreshTimer();
					tableViewExecuted.getSosDashboardHeader().resetRefreshTimer();

				} else {

					if (mainTabFolder.getSelection().getText().equals(TABNAME_SCHEDULER_EVENTS)) {
						if (tbtmEvents != null) {
							tbtmEvents.enableRefresh();
						}
					}
				}
			} else {
				tableViewPlanned.getSosDashboardHeader().getRefreshTimer().cancel();
				tableViewExecuted.getSosDashboardHeader().getRefreshTimer().cancel();
				if (tbtmEvents != null) {
					tbtmEvents.disableRefresh();
				}
			}
		}
	}

	private void showJoe() {
		if (objOptions != null && objOptions.getEnableJOE().isTrue()) {
			joeComposite = new Composite(mainTabFolder, SWT.NONE);
			joeComposite.setLayout(new GridLayout());

			CTabItem tbtmJoe = new SOSTabJOE(TABNAME_SCHEDULER_JOE, mainTabFolder, joeComposite);
			tbtmJoe.setControl(joeComposite);
		}
	}

	private void showEvents() {
		if (objOptions != null && objOptions.getEnableEvents().isTrue()) {
			tbtmEvents = new SOSTabEVENTS(objOptions, TABNAME_SCHEDULER_EVENTS, mainTabFolder);
		}
	}
	
	private void showJobnet() {
		if (objOptions != null && objOptions.getEnableJobnet().isTrue()) {
			tbtmJobnet = new SOSTabJOBNET(objOptions, TABNAME_SCHEDULER_JOBNET, mainTabFolder);
		}
	}

	
	private void createFormParts() {
		createMainWindow();
		showJoe();
		showBrowser();
		showEvents();
		showJobnet();

		logSashForm = new SashForm(dashboardComposite, SWT.SMOOTH | SWT.VERTICAL);
		GridData gd_sashForm = new GridData(GridData.FILL_BOTH);
		gd_sashForm.heightHint = 600;
		logSashForm.setLayoutData(gd_sashForm);

		tablesSashForm = new SashForm(logSashForm, SWT.HORIZONTAL);
		left = new Group(tablesSashForm, SWT.NONE);
		right = new Group(tablesSashForm, SWT.NONE);

		bottom = new Group(logSashForm, SWT.NONE);
		final FillLayout fl_bottom = new FillLayout();
		bottom.setLayout(fl_bottom);
		final FillLayout fl_right = new FillLayout();
		right.setLayout(fl_right);
		tablesSashForm.setWeights(new int[] { 730, 210 });
		dashboardComposite.layout();
	}

	private void createLeft() {
		parent = left;
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 9;
		parent.setLayout(gridLayout);
		dashboardShell.setText(conJOB_SCHEDULER_DASHBOARD + " (" + JSVersionInfo.getVersionString() + ")");
		dashboardShell.setSize(1000, 550);
		dashboardShell.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(final MouseEvent arg0) {
				if (haveDb && mainTabFolder != null && mainTabFolder.getSelection() != null) {
					if (mainTabFolder.getSelection().getText().equals(TABNAME_DASHBOARD)) {
						tableViewPlanned.getSosDashboardHeader().resetRefreshTimer();
						tableViewExecuted.getSosDashboardHeader().resetRefreshTimer();
					}
				}
			}
		});
		leftTabFolder = new CTabFolder(parent, SWT.NONE);
		leftTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (leftTabFolder.getSelectionIndex() == 0) {
					if (haveDb) {
						tableViewPlanned.buildTable();
					}
				} else {
					if (haveDb) {
						tableViewExecuted.buildTable();
					}
				}
			}
		});
		leftTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 9, 3));
		leftTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tableViewPlanned = new SOSDashboardTableViewPlanned(composite);
		tableViewPlanned.setObjOptions(objOptions);
		tableViewPlanned.setLeftTabFolder(leftTabFolder);
		tableViewPlanned.setPrefs(prefs);
		tableViewPlanned.setRight(right);
		tableViewPlanned.setLeft(left);
		tableViewPlanned.setTableDataProvider(dailyScheduleDataProvider);

		tableViewPlanned.setDetailHistoryDataProvider(detailHistoryDataProvider);
		tableViewPlanned.setDBLayer(schedulerInstancesDBLayer.getConfigurationFile());
		tableViewPlanned.createTable();
		tableViewPlanned.createMenue();
		if (haveDb) {
			tableViewPlanned.actualizeList();
			tableViewPlanned.getSchedulerIds();
		} else {
			tableViewPlanned.getSosDashboardHeader().getRefreshTimer().cancel();
			tableViewPlanned.getSosDashboardHeader().setEnabled(false);
			tableViewPlanned.getTableList().setEnabled(false);
		}

		tableViewExecuted = new SOSDashboardTableViewExecuted(composite);
		tableViewExecuted.setObjOptions(objOptions);
		tableViewExecuted.setDBLayer(schedulerInstancesDBLayer.getConfigurationFile());

		tableViewExecuted.setLeftTabFolder(leftTabFolder);
		tableViewExecuted.setPrefs(prefs);
		tableViewExecuted.setRight(right);
		tableViewExecuted.setLeft(left);
		tableViewExecuted.setTableDataProvider(executedHistoryDataProvider);
		tableViewExecuted.setDetailHistoryDataProvider(detailHistoryDataProvider);
		tableViewExecuted.createTable();
		tableViewExecuted.createMenue();
		if (haveDb) {
			tableViewExecuted.getSchedulerIds();
		} else {
			tableViewExecuted.getSosDashboardHeader().getRefreshTimer().cancel();
			tableViewExecuted.getSosDashboardHeader().setEnabled(false);
			tableViewExecuted.getTableList().setEnabled(false);
		}

		 
		CTabItem tbtmDailyPlan = new CTabItem(leftTabFolder, SWT.NONE);
		tbtmDailyPlan.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_NAME_TAB_PLANNED));
		tbtmDailyPlan.setControl(tableViewPlanned.getTablePlannedComposite());

		CTabItem tbtmHistory = new CTabItem(leftTabFolder, SWT.NONE);
		tbtmHistory.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_NAME_TAB_HISTORY));
		tbtmHistory.setControl(tableViewExecuted.getTableComposite());
 
		leftTabFolder.setSelection(0);

	}

	private void createRight() {
		tableHistoryDetail = new SosHistoryTable(right, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		tableHistoryDetail.setEnabled(haveDb);
		createMenueTableHistoryDetail();
		tableHistoryDetail.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.button == RIGHT_MOUSE_BUTTON) // rechte maustaste
				{
					setRightMausclick(true);
				} else {
					setRightMausclick(false);
				}
			}
		});
		tableHistoryDetail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (!isRightMouseclick()) {
					showLog(tableHistoryDetail);
				}
			}
		});
	}

	private void createBottom() {
		logTabFolder = new CTabFolder(bottom, SWT.NONE);
		createContextMenuLogTabfolder();
		logTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SosTabLogItem tItem = (SosTabLogItem) logTabFolder.getSelection();
				tItem.setSelection();
			}
		});
		logTabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		SosTabLogItem tbtmLog = new SosTabLogItem(conTabLOG, logTabFolder);
		lbMessage = new Label(dashboardShell, SWT.NONE);
		lbMessage.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_Message) + ":");
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		prefs = Preferences.userNodeForPackage(this.getClass());
		if (haveDb) {
			executedHistoryDataProvider.setIgnoreList(prefs);
		}
		dashboardShell = new Shell();
		dashboardShell.setLayout(new GridLayout());
		this.showWaitCursor();
		createFormParts();
		createLeft();
		createRight();
		createBottom();
		tableViewExecuted.setLogTabFolder(logTabFolder);
		tableViewExecuted.setTableHistoryDetail(tableHistoryDetail);
		tableViewPlanned.setLogTabFolder(logTabFolder);
		tableViewPlanned.setTableHistoryDetail(tableHistoryDetail);
		/*
		 * tableViewEventList.setLogTabFolder(logTabFolder);
		 * tableViewEventList.setTableHistoryDetail(tableHistoryDetail);
		 */
		this.RestoreCursor();
		parent = left;
		logSashForm.setWeights(new int[] { 309, 170 });
		dashboardComposite.layout();
		setRefreshState();
	}

	public void show() {
		createContents();
		InputStream imgInputStream = DashboardShowDialog.class.getResourceAsStream("/com/sos/dialog/dashboard.png");
		if (imgInputStream != null) {
			dashboardShell.setImage(new Image(shell.getDisplay(), imgInputStream));
		}
		dashboardShell.open();
		dashboardShell.layout();
	}

	private void openScheduler(final String hostport_) {
		
		String [] hostport = hostport_.split(":");
		String host = DEFAULT_HOST;
        if (hostport.length != 0){
        	host = hostport[0];
        }
		int port = DEFAULT_PORT;
		if (hostport.length > 1){
		    port = this.getIntValue(hostport[1], DEFAULT_PORT);
		}
		String schedulerInstance = host + ":" + port;
		SosTabJOC tbtmJoc = new SosTabJOC(schedulerInstance, browserTabFolder, host, port);
		browserTabFolder.setSelection(tbtmJoc);
		saveSchedulerTabs();
	}

	private void openScheduler(final String host, final int port) {
		String schedulerInstance = host + ":" + port;
		SosTabJOC tbtmJoc = new SosTabJOC(schedulerInstance, browserTabFolder, host, port);
		browserTabFolder.setSelection(tbtmJoc);
		saveSchedulerTabs();
	}

	private void saveSchedulerTabs() {
		String listOfScheduler = "";
		CTabItem[] tabs = browserTabFolder.getItems();
		for (CTabItem tab : tabs) {
			if (listOfScheduler.equals("")) {
				listOfScheduler = tab.getText();
			} else {
				listOfScheduler = listOfScheduler + "," + tab.getText();
			}
		}
		prefs.node(SOS_DASHBOARD).put(LIST_OF_SCHEDULERS, listOfScheduler);
	}

	private void createMenueTableHistoryDetail() {
		Menu contentMenuHistory = new Menu(tableHistoryDetail);
		tableHistoryDetail.setMenu(contentMenuHistory);
		// =============================================================================================
		MenuItem showLogHistory = new MenuItem(contentMenuHistory, SWT.PUSH);
		showLogHistory.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_show_log_in_new_tab));
		showLogHistory.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				SosTabLogItem tbtmLog = new SosTabLogItem(conTabLOG, logTabFolder);
				logTabFolder.setSelection(tbtmLog);
				showLog(tableHistoryDetail);
			}

			@Override
			public void widgetDefaultSelected(final org.eclipse.swt.events.SelectionEvent e) {
			}
		});
	}

	private void createContextMenuBrowserTabfolder(final CTabFolder cParent) {
		Menu contentMenu = new Menu(cParent);
		cParent.setMenu(contentMenu);
		MenuItem addItem = new MenuItem(contentMenu, SWT.PUSH);
		addItem.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_open_scheduler));
		addItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				SosDialogGetHostPort s = new SosDialogGetHostPort(dashboardShell);
				if (s.getHost() != null) {
					openScheduler(s.getHost(), s.getPort());
					for (int i = 0; i < mainTabFolder.getTabList().length; i++) {
						try {
							if (mainTabFolder.getItem(i).getText().equals(TABNAME_SCHEDULER_OPERATIONS_CENTER)) {
								mainTabFolder.setSelection(i);
							}

						} catch (Exception ee) {
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(final org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		// =============================================================================================
		MenuItem closeItem = new MenuItem(contentMenu, SWT.PUSH);
		closeItem.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_close));
		closeItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				SosTabJOC tbtmJoc = (SosTabJOC) browserTabFolder.getSelection();
				if (tbtmJoc != null) {
					if (browserTabFolder.getItemCount() > 1) {
						tbtmJoc.dispose();
						saveSchedulerTabs();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(final org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		// =============================================================================================
	}

	private void createContextMenuLogTabfolder() {
		Menu contentMenu = new Menu(logTabFolder);
		logTabFolder.setMenu(contentMenu);
		MenuItem addItem = new MenuItem(contentMenu, SWT.PUSH);
		addItem.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_new_log));
		addItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				SosTabLogItem tbtmLog = new SosTabLogItem(conTabLOG, logTabFolder);
				logTabFolder.setSelection(tbtmLog);
			}

			@Override
			public void widgetDefaultSelected(final org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		// =============================================================================================
		MenuItem closeItem = new MenuItem(contentMenu, SWT.PUSH);
		closeItem.setText(Messages.getLabel(DashBoardConstants.conSOSDashB_close));
		closeItem.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				SosTabLogItem tbtmLog = (SosTabLogItem) logTabFolder.getSelection();
				if (tbtmLog != null) {
					if (logTabFolder.getItemCount() > 1) {
						tbtmLog.dispose();
					} else {
						tbtmLog.clearLog();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(final org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		// =============================================================================================
	}

	public void open() {
		if (composite != null) {
			dashboardShell = composite.getShell();
		}
		display = Display.getDefault();
		show();
		while (!dashboardShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		System.exit(0);
	}

	public void setDataProvider(final DailyScheduleDataProvider dataProvider_) {
		haveDb = false;
		schedulerInstancesDBLayer = new SchedulerInstancesDBLayer(dataProvider_.getConfigurationFile());
		detailHistoryDataProvider = new SchedulerHistoryDataProvider(dataProvider_.getConfigurationFile());
		dailyScheduleDataProvider = dataProvider_;

		dailyScheduleDataProvider.getFilter().setPlannedFrom(new Date());
		dailyScheduleDataProvider.getFilter().setPlannedTo(new Date());
		dailyScheduleDataProvider.getData();

		executedHistoryDataProvider = new SchedulerHistoryDataProvider(dataProvider_.getConfigurationFile());
		executedHistoryDataProvider.setFrom(new Date());
		executedHistoryDataProvider.setTo(new Date());
		executedHistoryDataProvider.getData();
		

		haveDb = true;

	}

	
	 
	
	private void showLog(final Table table) {
		this.showWaitCursor();
		if (table.getSelectionIndex() >= 0 && table.getSelectionIndex() >= 0) {
			SosTabLogItem logItem = (SosTabLogItem) logTabFolder.getSelection();
			if (logItem == null) {
				logTabFolder.setSelection(0);
				logItem = (SosTabLogItem) logTabFolder.getSelection();
			}
			TableItem t = table.getItem(table.getSelectionIndex());
			DbItem d = (DbItem) t.getData();
			logItem.addLog(table, d.getTitle(), detailHistoryDataProvider.getLogAsString(d));
		}
		this.RestoreCursor();
	}

	public void setObjOptions(final SOSDashboardOptions objOptions) {
		this.objOptions = objOptions;
	}

}
