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
package sos.scheduler.editor.conf.composites;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.ContextMenu;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IOUtils;
import sos.scheduler.editor.app.MergeAllXMLinDirectory;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.listeners.JobListener;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

public class JobMainComposite extends SOSJOEMessageCodes {
	@SuppressWarnings("unused")
	private final String	conSVNVersion		= "$Id$";

	private final int		intNoOfLabelColumns	= 2;
	@SuppressWarnings("unused")
	private static Logger	logger				= Logger.getLogger(JobMainComposite.class);
	@SuppressWarnings("unused")
	private final String	conClassName		= "JobMainForm";

	private JobListener		objDataProvider		= null;
	private Group			gMain				= null;

	private Text			tbxJobName			= null;
	private Label			lblJobTitlelabel1	= null;
	@SuppressWarnings("unused")
	private Label			lblProcessClass		= null;
	private Text			tbxJobTitle			= null;
	private Combo			cProcessClass		= null;
	private Button			butBrowse			= null;
	private boolean			init				= true;
	private Button			butShowProcessClass	= null;
	private Label			label				= null;
	private int				intComboBoxStyle	= SWT.NONE;
	private GridLayout		gridLayout			= null;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public JobMainComposite(Group parent, int style, JobListener objDataProvider_) {
		super(parent, style);
		objDataProvider = objDataProvider_;
		createGroup(parent);
	}

	private void createGroup(final Group parent) {

		gridLayout = new GridLayout();
		gridLayout.marginHeight = 1;
		gridLayout.numColumns = 6;

		gMain = JOE_G_JobMainComposite_MainOptions.Control(new Group(parent, SWT.NONE));
		gMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		gMain.setLayout(gridLayout);

		label = JOE_L_JobMainComposite_JobName.Control(new Label(gMain, SWT.NONE));
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.END, false, false, intNoOfLabelColumns, 1));

		tbxJobName = JOE_T_JobMainComposite_JobName.Control(new Text(gMain, SWT.BORDER));
		tbxJobName.addVerifyListener(new VerifyListener() {
			public void verifyText(final VerifyEvent e) {
				if (!init) {
					e.doit = Utils.checkElement(objDataProvider.getJobName(), objDataProvider.get_dom(), Editor.JOB, null);
				}
			}
		});
		tbxJobName.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 4, 1));
		tbxJobName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (init) {
					return;
				}
				checkName();
				objDataProvider.setJobName(tbxJobName.getText(), true);
				parent.setText(objDataProvider.getJobNameAndTitle());
			}
		});

		lblJobTitlelabel1 = JOE_L_JobMainComposite_JobTitle.Control(new Label(gMain, SWT.NONE));
		lblJobTitlelabel1.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, intNoOfLabelColumns, 1));

		tbxJobTitle = JOE_T_JobMainComposite_JobTitle.Control(new Text(gMain, SWT.BORDER));
		tbxJobTitle.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 4, 1));
		tbxJobTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (init)
					return;
				objDataProvider.setTitle(tbxJobTitle.getText());
			}
		});

		// tbxJobTitle.setItems(Options.getJobTitleList());

		lblProcessClass = JOE_L_JobMainComposite_ProcessClass.Control(new Label(gMain, SWT.NONE));

		// butShowProcessClass = JOE_goto.Control(new Button(gMain, SWT.ARROW | SWT.DOWN));
		butShowProcessClass = JOE_B_JobMainComposite_ShowProcessClass.Control(new Button(gMain, SWT.ARROW | SWT.DOWN));
		butShowProcessClass.setVisible(objDataProvider.get_dom() != null && !objDataProvider.get_dom().isLifeElement());
		butShowProcessClass.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				String strT = cProcessClass.getText();
				if (strT.length() > 0) {
					ContextMenu.goTo(strT, objDataProvider.get_dom(), Editor.PROCESS_CLASSES);
				}
			}
		});
		butShowProcessClass.setAlignment(SWT.RIGHT);
		butShowProcessClass.setVisible(true);

		cProcessClass = JOE_Cbo_JobMainComposite_ProcessClass.Control(new Combo(gMain, intComboBoxStyle));
		cProcessClass.setMenu(new ContextMenu(cProcessClass, objDataProvider.get_dom(), Editor.PROCESS_CLASSES).getMenu());

		cProcessClass.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {

				if (init) {
					return;
				}
				objDataProvider.setProcessClass(cProcessClass.getText());
				butShowProcessClass.setVisible(true);
			}
		});
		cProcessClass.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false, 3, 1));
		cProcessClass.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				if (init) {
					return;
				}
				objDataProvider.setProcessClass(cProcessClass.getText());
			}
		});

		cProcessClass.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.F1) {
					objDataProvider.openXMLAttributeDoc("job", "process_class");
				}
				if (event.keyCode == SWT.F10) {
					objDataProvider.openXMLDoc("job");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		});

		cProcessClass.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				String strT = cProcessClass.getText();
				if (strT.length() > 0) {
					ContextMenu.goTo(strT, objDataProvider.get_dom(), Editor.PROCESS_CLASSES);
				}
			}
		});

		butBrowse = JOE_B_JobMainComposite_BrowseProcessClass.Control(new Button(gMain, SWT.NONE));
		butBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				String name = IOUtils.getJobschedulerObjectPathName(MergeAllXMLinDirectory.MASK_PROCESS_CLASS);
				if (name != null && name.length() > 0)
					cProcessClass.setText(name);
			}
		});

	}

	public void init() {
		init = true;
		tbxJobName.setText(objDataProvider.getJobName());
		tbxJobTitle.setText(objDataProvider.getTitle());
		tbxJobName.setFocus();
		String process_class = objDataProvider.getProcessClass();
		cProcessClass.setItems(objDataProvider.getProcessClasses());
		cProcessClass.setText(process_class);
		init = false;
	}

	private void checkName() {
		if (Utils.existName(tbxJobName.getText(), objDataProvider.getJob(), "job")) {
			tbxJobName.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW));
		}
		else {
			tbxJobName.setBackground(null);
		}
	}

	@Override
	protected void checkSubclass() {
	}

	public Group getgMain() {
		return gMain;
	}

}
