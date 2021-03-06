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
package sos.scheduler.editor.conf.container;

//import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

//import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.classes.FileNameSelector;
import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobIncludeFile extends FormBaseClass {
	@SuppressWarnings("unused")
	private final String	conClassName		= "JobIncludeFile";
	@SuppressWarnings("unused")
	private final String	conSVNVersion		= "$Id$";

	private Group			group				= null;
	private JobListener		objJobDataProvider	= null;
	private boolean			init				= true;
	private Button			bRemove				= null;
	private Text			tbxFile2Include		= null;
	private Button			bAdd				= null;
	private Label			label				= null;
	private Table			tableIncludes		= null;
	private Button			butIsLiveFile		= null;

	public JobIncludeFile(Composite pParentComposite, JobListener pobjJobDataProvider, JobIncludeFile that) {
		super(pParentComposite, pobjJobDataProvider);
		objJobDataProvider = pobjJobDataProvider;
		init = true;
		createGroup();
		init = false;
		getValues(that);
	}

	private void getValues(JobIncludeFile that) {
		if (that == null) {
			return;
		}

		this.tbxFile2Include.setText(that.tbxFile2Include.getText());
		for (int i = 0; i < that.tableIncludes.getItemCount(); i++) {
			TableItem t = new TableItem(this.tableIncludes, SWT.None);
			t.setText(that.tableIncludes.getItems()[i].getText());
		}
		;
	}

	private void createGroup() {
		int intNumColumns = 3;

		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.marginWidth = 0;
		gridLayout1.marginHeight = 0;
		gridLayout1.numColumns = intNumColumns;
		group = SOSJOEMessageCodes.JOE_G_JobIncludeFile_IncludeFiles.Control(new Group(objParent, SWT.NONE));
		group.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, true, intNumColumns, 1));
		group.setLayout(gridLayout1);
		setResizableV(group);

		butIsLiveFile = SOSJOEMessageCodes.JOE_B_ParameterForm_LifeFile.Control(new Button(group, SWT.CHECK));

		final FileNameSelector fleFile2Include = new FileNameSelector(group, SWT.BORDER);
		fleFile2Include.setDataProvider(objJobDataProvider);
		tbxFile2Include = fleFile2Include;

		// tbxFile2Include.addMouseListener(new MouseListener() {
		//
		// @Override
		// public void mouseDoubleClick(MouseEvent arg0) {
		// String strT = fleFile2Include.getFileName();
		// if (strT.trim().length() > 0) {
		// applyFile2Include();
		// }
		// }
		//
		// @Override
		// public void mouseDown(MouseEvent arg0) {
		// }
		//
		// @Override
		// public void mouseUp(MouseEvent arg0) {
		// }
		// });

		tbxFile2Include.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent arg0) {
				String strT = fleFile2Include.getFileName();
				if (strT.trim().length() > 0) {
					applyFile2Include();
				}
			}
		});

		butIsLiveFile.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (init) {
					return;
				}
				fleFile2Include.flgIsFileFromLiveFolder = butIsLiveFile.getSelection();
			}
		});

		bAdd = SOSJOEMessageCodes.JOE_B_JobIncludeFile_Add.Control(new Button(group, SWT.NONE));
		bAdd.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		bAdd.setEnabled(false);
		bAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				applyFile2Include();
			}
		});

		label = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
		// label.setText("Label");
		label.setLayoutData(new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, false, false, 3, 1));

		tableIncludes = SOSJOEMessageCodes.JOE_Tbl_JobIncludeFile_Includes.Control(new Table(group, SWT.FULL_SELECTION | SWT.BORDER));
		tableIncludes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (tableIncludes.getSelectionCount() > 0) {
					tbxFile2Include.setText(tableIncludes.getSelection()[0].getText(0));
					tbxFile2Include.setEnabled(true);
					butIsLiveFile.setSelection(tableIncludes.getSelection()[0].getText(1) != null
							&& tableIncludes.getSelection()[0].getText(1).equals("live_file"));
					bRemove.setEnabled(tableIncludes.getSelectionCount() > 0);
					bAdd.setEnabled(false);
				}
			}
		});
		tableIncludes.setLinesVisible(true);
		tableIncludes.setHeaderVisible(true);
		final GridData gridData_2 = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 2);
		gridData_2.heightHint = 4;
		gridData_2.minimumHeight = 20;
		tableIncludes.setLayoutData(gridData_2);

		final TableColumn newColumnTableColumn = SOSJOEMessageCodes.JOE_TCl_JobIncludeFile_Name.Control(new TableColumn(tableIncludes, SWT.NONE));
		newColumnTableColumn.setWidth(272);

		final TableColumn newColumnTableColumn_1 = SOSJOEMessageCodes.JOE_TCl_JobIncludeFile_FileLiveFile.Control(new TableColumn(tableIncludes, SWT.NONE));
		newColumnTableColumn_1.setWidth(81);

		final Button butNew = SOSJOEMessageCodes.JOE_B_JobIncludeFile_New.Control(new Button(group, SWT.NONE));
		butNew.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				tableIncludes.deselectAll();
				tbxFile2Include.setText("");
				tbxFile2Include.setEnabled(true);
				butIsLiveFile.setSelection(false);
				butIsLiveFile.setEnabled(true);
				tbxFile2Include.setFocus();
			}
		});
		butNew.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

		tbxFile2Include.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				bAdd.setEnabled(objJobDataProvider.isNotEmpty(tbxFile2Include.getText()));
			}
		});
		tbxFile2Include.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR && objJobDataProvider.isNotEmpty(tbxFile2Include.getText())) {
					objJobDataProvider.addInclude(tableIncludes, tbxFile2Include.getText(), butIsLiveFile.getSelection());
					objJobDataProvider.fillTable(tableIncludes);
					tbxFile2Include.setText("");
				}
			}
		});
		bRemove = SOSJOEMessageCodes.JOE_B_JobIncludeFile_Remove.Control(new Button(group, SWT.NONE));
		bRemove.setEnabled(false);
		bRemove.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
		bRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableIncludes.getSelectionCount() > 0) {
					int index = tableIncludes.getSelectionIndex();
					objJobDataProvider.removeInclude(index);
					objJobDataProvider.fillTable(tableIncludes);
					if (index >= tableIncludes.getItemCount())
						index--;
					// if (tableIncludes.getItemCount() > 0)
					// tableIncludes.setSelection(index);
					tableIncludes.deselectAll();
					tbxFile2Include.setText("");
					tbxFile2Include.setEnabled(false);
				}
			}
		});
	}

	private void applyFile2Include() {
		String strFileName = tbxFile2Include.getText();
		boolean flgIsLiveFile = butIsLiveFile.getSelection();
		// File objF = null;
		// if (flgIsLiveFile == true) {
		// objF = new File(Options.getSchedulerHotFolder(), strFileName);
		// }
		// else {
		// objF = new File(strFileName);
		// }
		// if (objF.exists() == false || objF.canRead() == false) {
		// MainWindow.ErrMsg(String.format("File '%1$s' not found or is not readable", strFileName));
		// tbxFile2Include.setText("");
		// } else {
		objJobDataProvider.addInclude(tableIncludes, strFileName, flgIsLiveFile);
		tbxFile2Include.setText("");
		tbxFile2Include.setEnabled(false);
		butIsLiveFile.setEnabled(false);
		tableIncludes.deselectAll();
		// }
		tbxFile2Include.setFocus();
	}

	public Table getTableIncludes() {
		return tableIncludes;
	}
}
