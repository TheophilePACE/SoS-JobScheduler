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
/**
 * Created on 06.03.2007
 *
 * Wizzard: Typ des Schedulers wird angegeben. Standalone Job oder Order Job
 * 
 *  @author mo
 * 
 */
package sos.scheduler.editor.conf.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.JobListener;

import com.swtdesigner.SWTResourceManager;

/**
 * Job Wizzard.
 * 
 * Auswahl zwischen der Standalone Jobs und Auftragsgesteuerte Jobs.
 * 
 * Es werden im n�chsten Dialog (Job Auswahl Dialoge) entsprechend Standalone Jobs oder 
 * Auftragsgesteuerte Jobs zur Auswahl gestellt.
 * 
 * Die Kriterien stehen in der Job Dokumentation.
 * Das bedeutet alle Job Dokumentationen aus der Verzeichnis <SCHEDULER_HOME>/jobs/*.xml werden parsiert. 
 * 
 * 
 * @author mueruevet.oeksuez@sos-berlin.com
 *
 */
public class JobAssistentTypeForms {
	@SuppressWarnings("unused")
	private final String conSVNVersion = "$Id: JobAssistentTypeForms.java 17989 2012-09-07 13:10:21Z ur $";
	
	private static final String	conTagNameJOB	= "job";

	/** Parameter: isStandaloneJob = true -> Standalone Job, sonst Order Job*/
	private boolean          isStandaloneJob  = true;	
	
	private Button           radStandalonejob = null;	
	
	private Button           radOrderjob      = null;		
	
	private SchedulerDom     dom              = null;
	
	private ISchedulerUpdate update           = null;
	
	private Button           butCancel        = null;
	
	private Button           butShow          = null;
	
	private Button           butNext          = null;	 
	
	private Shell            jobTypeShell     = null;
	
	private String           jobType          = ""; 
	
	private Element          jobBackUp        = null;
	
	private int              assistentType    = Editor.JOBS;
	
	/**
	 * Konstruktor 
	 * @param dom_ - Type SchedulerDom 
	 * @param update_ - Type ISchedulerUpdate
	 */
	public JobAssistentTypeForms(SchedulerDom dom_, ISchedulerUpdate update_) {
		dom = dom_;
		update = update_;		
	}
	
	public void showTypeForms(String type, Element job, int assistentType_) {
		jobType = type;
		jobBackUp = job;
		assistentType = assistentType_;
		showTypeForms();
	}
	
	public void showTypeForms() {
		try {
			jobTypeShell = new Shell(MainWindow.getSShell(), SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.BORDER);			
			jobTypeShell.setImage(ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
			
			final GridLayout gridLayout = new GridLayout();
			gridLayout.marginTop = 5;
			gridLayout.marginRight = 5;
			gridLayout.marginLeft = 5;
			gridLayout.marginBottom = 5;
			gridLayout.numColumns = 2;
			jobTypeShell.setLayout(gridLayout);			
			jobTypeShell.setText(SOSJOEMessageCodes.JOE_M_JobAssistent_JobType.params(SOSJOEMessageCodes.JOE_M_JobAssistent_Step1.label()));
			{
				final Group jobGroup = SOSJOEMessageCodes.JOE_G_JobAssistent_JobGroup.Control(new Group(jobTypeShell, SWT.NONE));
				jobGroup.setCapture(true);
				final GridData gridData_1 = new GridData(GridData.FILL, GridData.CENTER, true, true, 2, 1);
				gridData_1.heightHint = 99;
				gridData_1.verticalIndent = -1;
				jobGroup.setLayoutData(gridData_1);
				final GridLayout gridLayout_1 = new GridLayout();
				gridLayout_1.horizontalSpacing = 15;
				gridLayout_1.marginWidth = 10;
				gridLayout_1.marginHeight = 0;
				gridLayout_1.numColumns = 2;
				jobGroup.setLayout(gridLayout_1);
				
				{
					radOrderjob = SOSJOEMessageCodes.JOE_B_JobAssistent_OrderJob.Control(new Button(jobGroup, SWT.RADIO));					
					final GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, true);
					gridData.heightHint = 48;
					radOrderjob.setLayoutData(gridData);
					radOrderjob.setSelection(jobType != null && jobType.length() > 0 && jobType.equalsIgnoreCase("order"));
				}
				
				{
					radStandalonejob = SOSJOEMessageCodes.JOE_B_JobAssistent_StandaloneJob.Control(new Button(jobGroup, SWT.RADIO));
					radStandalonejob.setSelection(jobType == null || jobType.length() == 0 || jobType.equalsIgnoreCase("standalonejob"));					
					final GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, true, true);					
					radStandalonejob.setLayoutData(gridData);
				}
			}
			
			{
				butCancel = SOSJOEMessageCodes.JOE_B_JobAssistent_Cancel.Control(new Button(jobTypeShell, SWT.NONE));
				butCancel.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(final SelectionEvent e) {						
						close();							
					}
				});
			}			
			
			
			{
				final Composite composite = new Composite(jobTypeShell, SWT.NONE);
				final GridData gridData = new GridData(GridData.END, GridData.CENTER, false, false);
				composite.setLayoutData(gridData);
				final GridLayout gridLayout_1 = new GridLayout();
				gridLayout_1.marginHeight = 0;
				gridLayout_1.marginWidth = 0;
				gridLayout_1.numColumns = 3;
				composite.setLayout(gridLayout_1);
				
				{
					butShow = SOSJOEMessageCodes.JOE_B_JobAssistent_Show.Control(new Button(composite, SWT.NONE));
					butShow.setVisible(false);
					butShow.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
					butShow.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(final SelectionEvent e) {
							//dient nur f�r die Show Funktion 
							Element job = new Element(conTagNameJOB);
							Utils.setAttribute("order", isStandaloneJob ? "yes" : "no", job);
							MainWindow.message(jobTypeShell, Utils.getElementAsString(job), SWT.OK );
						}
					});
				}
				{
					butNext = SOSJOEMessageCodes.JOE_B_JobAssistent_Next.Control(new Button(composite, SWT.NONE));
					butNext.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
					final GridData gridData_1 = new GridData(GridData.END, GridData.CENTER, false, false);					
					butNext.setLayoutData(gridData_1);
					butNext.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(final SelectionEvent e) {
							Utils.startCursor(jobTypeShell);
							
							if(radOrderjob.getSelection()) {
								isStandaloneJob = false;						
							} else {
								isStandaloneJob = true;
							}							
							
							if(jobBackUp != null) {
								int cont = MainWindow.message(jobTypeShell, SOSJOEMessageCodes.JOE_M_JobAssistent_DiscardChanges.label(), SWT.ICON_QUESTION | SWT.YES |SWT.NO |SWT.CANCEL );
								if(cont == SWT.CANCEL) {
									return;
								}else if(cont != SWT.YES) {											
									JobAssistentImportJobsForm importJobs = new JobAssistentImportJobsForm( new JobListener(dom, jobBackUp, update) ,null,assistentType);
									importJobs.showAllImportJobs((Utils.getAttributeValue("order", jobBackUp).equals("yes")?"order":"standalonejob")) ;
									jobTypeShell.dispose();
									return;
								}	
							} 
							JobAssistentImportJobsForm importJobs = new JobAssistentImportJobsForm(dom, update, assistentType);
							importJobs.showAllImportJobs((isStandaloneJob ? "standalonejob" : "order"));
							Utils.stopCursor(jobTypeShell);
							
							jobTypeShell.dispose();
							
						}
					});
				}
				
				Utils.createHelpButton(composite, "JOE_M_JobAssistentTypeForms_Help.label", jobTypeShell);
			}
			
			java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			
			jobTypeShell.setBounds((screen.width - jobTypeShell.getBounds().width) /2, 
					(screen.height - jobTypeShell.getBounds().height) /2, 
					jobTypeShell.getBounds().width, 
					jobTypeShell.getBounds().height);
			jobTypeShell.open();
			jobTypeShell.layout();
			jobTypeShell.pack();		
		} catch (Exception e) {
			try {
				new sos.scheduler.editor.app.ErrorLog(SOSJOEMessageCodes.JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			} catch(Exception ee) {
				//tu nichts
			}
			System.err.println(SOSJOEMessageCodes.JOE_E_0002.params("JobAssistentTypeForms.showTypeForms()" ) + e.getMessage());
		}
	}
	
	
	
	private void setToolTipText() {
//		
	}
	
	private void close() {
		int cont = MainWindow.message(jobTypeShell, SOSJOEMessageCodes.JOE_M_JobAssistent_CancelWizard.label(), SWT.ICON_WARNING | SWT.OK |SWT.CANCEL );
		if(cont == SWT.OK)
			jobTypeShell.dispose();	
	}
}
