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
package sos.scheduler.editor.conf.container;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IContainer;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.classes.TextArea;
import sos.scheduler.editor.classes.TextArea.enuSourceTypes;
import sos.scheduler.editor.conf.forms.JobAssistentImportJobParamsForm;
import sos.scheduler.editor.conf.forms.JobAssistentImportJobsForm;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobDocumentation extends FormBaseClass {
	@SuppressWarnings("unused")
	private final String	conSVNVersion		= "$Id: JobDocumentation.java 18346 2012-11-02 14:07:36Z ur $";

	private Group			group				= null;
	private Group			gMain				= null;
	private Group			gDescription		= null;
	private Text			tFileName			= null;
	private StyledText		tDescription		= null;
	private StyledText		tComment			= null;
	private Button			butShow				= null;
	private Button			butOpen				= null;
	private Button			butIsLiveFile		= null;
	private Button			butWizard			= null;
	private boolean			init				= true;
	private JobListener		objJobDataProvider	= null;
	private TextArea		txtAreaDescription	= null;

	public JobDocumentation(Composite pParentComposite, JobListener pobjDataProvider) {
		super(pParentComposite, pobjDataProvider);
		objJobDataProvider = (JobListener) pobjDataProvider;
		init = true;
		showWaitCursor();
		createGroup(pParentComposite);
		initForm();
		init = false;
		restoreCursor();
	}

	public void apply() {
		// if (isUnsaved())
		// addParam();
	}

	public boolean isUnsaved() {
		// return bApply.isEnabled();
		return false;
	}

	private void initForm() {

		this.tFileName.setText(objJobDataProvider.getInclude());
		this.butIsLiveFile.setSelection(objJobDataProvider.isLiveFile());
	}

	private void createGroup(Composite objParent) {
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 1;
		group = new Group(objParent, SWT.NONE);
		String strM = SOSJOEMessageCodes.JOE_M_JobAssistent_JobGroup.params(objJobDataProvider.getJobName())
				+ (objJobDataProvider.isDisabled() ? SOSJOEMessageCodes.JOE_M_JobCommand_Disabled.label() : "");
		group.setText(strM);
		group.setLayout(gridLayout2);

		group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		group.setLayout(gridLayout2);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gMain = SOSJOEMessageCodes.JOE_G_ConfigForm_Comment.Control(new Group(group, SWT.NONE));
		gMain.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		gMain.setLayout(gridLayout);

		TextArea txtAreaComment = new TextArea(gMain, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.H_SCROLL);
		txtAreaComment.setDataProvider(objJobDataProvider, enuSourceTypes.xmlComment);
		tComment = txtAreaComment;
		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.numColumns = 2;

		gDescription = SOSJOEMessageCodes.JOE_G_JobDocumentation_JobDescription.Control(new Group(group, SWT.NONE));
		gDescription.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		gDescription.setLayout(gridLayout3);

		GridData gridData12 = new GridData(SWT.FILL, GridData.CENTER, true, false);
		gridData12.horizontalIndent = -1;

		tFileName = SOSJOEMessageCodes.JOE_T_JobDocumentation_FileName.Control(new Text(gDescription, SWT.BORDER));
		tFileName.setLayoutData(gridData12);
		tFileName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (init)
					return;

				showWaitCursor();
				objJobDataProvider.setInclude(tFileName.getText(), butIsLiveFile.getSelection());
				if (tFileName.getText() != null && tFileName.getText().length() > 0) {
					butShow.setEnabled(true);
					if (tFileName.getText().endsWith(".xml"))
						butOpen.setEnabled(true);
					else
						butOpen.setEnabled(false);
				}
				else {
					butShow.setEnabled(false);
					butOpen.setEnabled(false);
				}
				restoreCursor();
			}
		});

		butIsLiveFile = SOSJOEMessageCodes.JOE_B_JobDocumentation_IsLiveFile.Control(new Button(gDescription, SWT.CHECK));
		butIsLiveFile.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (init)
					return;

				showWaitCursor();
				objJobDataProvider.setInclude(tFileName.getText(), butIsLiveFile.getSelection());

				if (tFileName.getText() != null && tFileName.getText().length() > 0) {
					butShow.setEnabled(true);
					if (tFileName.getText().endsWith(".xml"))
						butOpen.setEnabled(true);
					else
						butOpen.setEnabled(false);
				}
				else {
					butShow.setEnabled(false);
					butOpen.setEnabled(false);
				}
				restoreCursor();
			}
		});

		GridData gridData14 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 1, 3);
		gridData14.horizontalIndent = -1;

		txtAreaDescription = new TextArea(gDescription, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
		GridData gd_txtAreaDescription = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 3);
		gd_txtAreaDescription.heightHint = 91;
		txtAreaDescription.setLayoutData(gd_txtAreaDescription);
		tDescription = txtAreaDescription;
		txtAreaDescription.setDataProvider(objJobDataProvider, enuSourceTypes.JobDocu);
		tDescription.setToolTipText(SOSJOEMessageCodes.JOE_M_0050.label());

		tDescription.setText(objJobDataProvider.getDescription());

		butShow = SOSJOEMessageCodes.JOE_B_JobDocumentation_Show.Control(new Button(gDescription, SWT.NONE));
		butShow.setEnabled(false);
		butShow.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		butShow.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {

				try {
					showWaitCursor();
					if (tFileName.getText() != null && tFileName.getText().length() > 0) {
						String sData = "";// getData(tFileName.getText());

						Program prog = Program.findProgram("html");

						String strFileName = new File((sData).concat(tFileName.getText())).toURI().toURL().toString();
						if (prog != null)
							prog.execute(strFileName);
						else {
							Runtime.getRuntime().exec(Options.getBrowserExec(strFileName, Options.getLanguage()));
						}
					}
				}
				catch (Exception ex) {
					try {
						new sos.scheduler.editor.app.ErrorLog(SOSJOEMessageCodes.JOE_M_0011.params(sos.util.SOSClassUtil.getMethodName(), tFileName.getText(),
								ex));
					}
					catch (Exception ee) {
					}
					MainWindow.message(getShell(), SOSJOEMessageCodes.JOE_M_0011.params("widgetSelected()", tFileName.getText(), ex.getMessage()),
							SWT.ICON_WARNING | SWT.OK);
				}
				finally {
					restoreCursor();
				}
			}
		});
		butShow.setEnabled(tFileName.getText().trim().length() > 0);

		butOpen = SOSJOEMessageCodes.JOE_B_JobDocumentation_Open.Control(new Button(gDescription, SWT.NONE));
		butOpen.setEnabled(false);
		butOpen.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				String xmlPath = "";
				try {
					showWaitCursor();
					if (tFileName.getText() != null && tFileName.getText().length() > 0) {
						xmlPath = sos.scheduler.editor.app.Options.getSchedulerData();
						xmlPath = (xmlPath.endsWith("/") || xmlPath.endsWith("\\") ? xmlPath.concat(tFileName.getText()) : xmlPath.concat("/").concat(
								tFileName.getText()));

						IContainer con = getContainer();
						con.openDocumentation(xmlPath);
						con.setStatusInTitle();
					}
					else {
						MainWindow.message(SOSJOEMessageCodes.JOE_M_JobDocumentation_NoDoc.params(xmlPath), SWT.ICON_WARNING | SWT.OK);
					}
				}
				catch (Exception e1) {
				}
				finally {
					restoreCursor();
				}
			}
		});
		butOpen.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		butOpen.setEnabled(tFileName.getText().trim().length() > 0 && tFileName.getText().endsWith(".xml"));

		tComment.setToolTipText(SOSJOEMessageCodes.JOE_M_0051.label());

		butIsLiveFile.setSelection(objJobDataProvider.isLiveFile());
		tFileName.setText(objJobDataProvider.getInclude());

		butWizard = SOSJOEMessageCodes.JOE_B_ParameterForm_Wizard.Control(new Button(gDescription, SWT.NONE));
		butWizard.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				startWizzard(false);
			}
		});

		butWizard.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		tComment.setText(objJobDataProvider.getComment());
	}

	public void startWizzard(boolean onlyParams) {
		try {
			showWaitCursor();
			if (objJobDataProvider.getInclude() != null && objJobDataProvider.getInclude().trim().length() > 0) {
				// JobDokumentation ist bekannt -> d.h Parameter aus dieser
				// Jobdoku extrahieren
				JobAssistentImportJobParamsForm paramsForm = new JobAssistentImportJobParamsForm(objJobDataProvider.get_dom(), objJobDataProvider.get_main(),
						objJobDataProvider, onlyParams ? Editor.JOB : Editor.JOB_WIZARD);

				if (!onlyParams)
					paramsForm.setJobForm(this);
				paramsForm.showAllImportJobParams(objJobDataProvider.getInclude());
			}
			else {
				JobAssistentImportJobsForm importJobForms = new JobAssistentImportJobsForm(objJobDataProvider, Editor.JOB_WIZARD);

				if (!onlyParams) {
					importJobForms.setJobForm(this);
				}
				importJobForms.showAllImportJobs();

			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			restoreCursor();
		}
	}

	public String getData(String filename) {

		String data = ".";
		if ((objJobDataProvider.get_dom().isDirectory() || objJobDataProvider.get_dom().isLifeElement()) && butIsLiveFile.getSelection()) {
			if (filename.startsWith("/") || filename.startsWith("\\")) {
				data = Options.getSchedulerHotFolder();
			}
			else
				if (objJobDataProvider.get_dom().getFilename() != null) {
					data = new File(objJobDataProvider.get_dom().getFilename()).getParent();
				}
		}
		else {
			if (butIsLiveFile.getSelection())
				data = Options.getSchedulerHotFolder();
			else
				data = Options.getSchedulerData();
		}

		if (!(data.endsWith("\\") || data.endsWith("/")))
			data = data.concat("/");

		data = data.replaceAll("\\\\", "/");

		return data;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
