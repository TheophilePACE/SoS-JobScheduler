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
 * 
 */
package sos.scheduler.editor.conf.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.jdom.JDOMException;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.BaseListener;

public class BaseForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {

	private static final String	conImageICON_OPEN_GIF	= "/sos/scheduler/editor/icon_open.gif";
	private static final String	conImageICON_EDIT_GIF	= "/sos/scheduler/editor/icon_edit.gif";
	private final String		conClassName			= "BaseForm";
	final String				conMethodName			= conClassName + "::enclosing_method";
	@SuppressWarnings("unused")
	private final String		conSVNVersion			= "$Id: BaseForm.java 19102 2013-02-11 09:35:38Z ur $";

	private BaseListener		listener				= null;
	private Group				group					= null;
	@SuppressWarnings("unused")
	private Label				label1					= null;
	private Text				tFile					= null;
	private Button				bApply					= null;
	private Button				bNew					= null;
	private Button				bRemove					= null;
	private Label				label					= null;
	private Label				label2					= null;
	private Label				label3					= null;
	private Text				tComment				= null;
	private Table				table					= null;
	private Button				butOpen					= null;
	private Button				button					= null;
	private Button				butOpenFileDialog		= null;

	/**
	 * @param parent
	 * @param style
	 * @throws JDOMException
	 */
	public BaseForm(Composite parent, int style, SchedulerDom dom) throws JDOMException {
		super(parent, style);
		listener = new BaseListener(dom);

		initialize();
		setToolTipText();
		listener.fillTable(table);
	}

	public boolean isUnsaved() {
		return bApply.isEnabled();
	}

	public void apply() {
		if (isUnsaved())
			applyFile();
	}

	private void initialize() {
		this.setLayout(new FillLayout());
		createGroup();
		setSize(new org.eclipse.swt.graphics.Point(657, 329));
	}

	private void createGroup() {
		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = GridData.FILL;
		gridData21.verticalAlignment = GridData.CENTER;
		GridData gridData11 = new GridData(GridData.FILL, GridData.CENTER, false, false, 3, 1);
		gridData11.heightHint = 10;
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalSpan = 1;
		gridData2.verticalAlignment = GridData.BEGINNING;
		GridData gridData1 = new GridData(GridData.FILL, GridData.BEGINNING, false, false);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;

		group = JOE_G_BaseForm_BaseFiles.Control(new Group(this, SWT.NONE));
		group.setLayout(gridLayout);

		label1 = JOE_L_BaseForm_BaseFile.Control(new Label(group, SWT.NONE));
		
		tFile = JOE_T_BaseForm_BaseFile.Control(new Text(group, SWT.BORDER));

		bApply = JOE_B_BaseForm_Apply.Control(new Button(group, SWT.NONE));
		bApply.setLayoutData(gridData1);
		bApply.setEnabled(false);
		bApply.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				applyFile();
			}
		});
		
		GridData gridData8 = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		label3 = JOE_L_BaseForm_BaseComment.Control(new Label(group, SWT.NONE));
		label3.setLayoutData(gridData8);
		
		GridData gridData9 = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData9.heightHint = 80;
		
		tComment = JOE_T_BaseForm_BaseComment.Control(new Text(group, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL));
		tComment.setLayoutData(gridData9);
		tComment.setFont(ResourceManager.getFont("Courier New", 8, SWT.NONE));
		tComment.setEnabled(false);
		tComment.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == 97 && e.stateMask == SWT.CTRL) {
					tComment.setSelection(0, tComment.getText().length());
				}
			}
		});
		tComment.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				bApply.setEnabled(!tFile.getText().equals(""));
				button.setEnabled(!tFile.getText().equals(""));
			}
		});

		final Composite composite = JOE_Cmp_BaseForm_CommentOpen.Control(new Composite(group, SWT.NONE));
		composite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginWidth = 0;
		gridLayout_1.marginHeight = 0;
		gridLayout_1.horizontalSpacing = 0;
		composite.setLayout(gridLayout_1);

		button = JOE_B_BaseForm_Comment.Control(new Button(composite, SWT.NONE));
		final GridData gridData = new GridData(GridData.BEGINNING, GridData.CENTER, true, true);
		button.setLayoutData(gridData);
		button.setEnabled(false);
		button.setImage(ResourceManager.getImageFromResource(conImageICON_EDIT_GIF));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				String text = sos.scheduler.editor.app.Utils.showClipboard(tComment.getText(), getShell(), true, "");
				if (text != null)
					tComment.setText(text);
			}
		});

		butOpenFileDialog = JOE_B_BaseForm_OpenFileDialog.Control(new Button(composite, SWT.NONE));
		butOpenFileDialog.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, true, true));
		butOpenFileDialog.setEnabled(false);
		butOpenFileDialog.setImage(ResourceManager.getImageFromResource(conImageICON_OPEN_GIF));
		butOpenFileDialog.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				openFileDialog();
			}

		});

		label = JOE_Sep_BaseForm_S1.Control(new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_OUT));
		label.setLayoutData(gridData11);
		
		createTable();

		bNew = JOE_B_BaseForm_NewBaseFile.Control(new Button(group, SWT.NONE));
		bNew.setLayoutData(gridData3);
		bNew.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				listener.newBaseFile();
				setInput(true);
			}
		});
		getShell().setDefaultButton(bNew);

		butOpen = JOE_B_BaseForm_OpenBaseFile.Control(new Button(group, SWT.NONE));
		butOpen.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));
		butOpen.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				openBaseElement();

			}
		});

		label2 = JOE_Sep_BaseForm_S2.Control(new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL));
		label2.setLayoutData(gridData21);
		
		bRemove = JOE_B_BaseForm_RemoveBaseFile.Control(new Button(group, SWT.NONE));
		bRemove.setEnabled(false);
		bRemove.setLayoutData(gridData2);
		bRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionCount() > 0) {
					int index = table.getSelectionIndex();
					listener.removeBaseFile(index);
					table.remove(index);
					if (index >= table.getItemCount())
						index--;
					if (table.getItemCount() > 0) {
						table.select(index);
						listener.selectBaseFile(index);
						setInput(true);
					}
					else
						setInput(false);
				}
				bRemove.setEnabled(table.getSelectionCount() > 0);
			}
		});
		
		tFile.setEnabled(false);
		tFile.setLayoutData(gridData4);
		tFile.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				getShell().setDefaultButton(bApply);
				bApply.setEnabled(!tFile.getText().equals(""));
				button.setEnabled(!tFile.getText().equals(""));
			}
		});
		tFile.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR && !tFile.getText().equals(""))
					applyFile();
			}
		});
		
	}

	/**
	 * This method initializes table
	 */
	private void createTable() {
		GridData gridData = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 2, 4);
		table = JOE_Tbl_BaseForm_BaseTable.Control(new Table(group, SWT.BORDER | SWT.FULL_SELECTION));
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(final MouseEvent e) {
				openBaseElement();
			}
		});
		table.setHeaderVisible(true);
		table.setLayoutData(gridData);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionCount() > 0) {
					listener.selectBaseFile(table.getSelectionIndex());
					setInput(true);
				}
			}
		});
		TableColumn tableColumn = JOE_TCl_BaseForm_BaseFiles.Control(new TableColumn(table, SWT.NONE));
		tableColumn.setWidth(300);
//		tableColumn.setText(Messages.getLabel("BaseFile"));
		
		TableColumn tableColumn1 = JOE_TCl_BaseForm_BaseComment.Control(new TableColumn(table, SWT.NONE));
		table.setSortColumn(tableColumn1);
		tableColumn1.setWidth(300);
//		tableColumn1.setText(Messages.getLabel("Comment"));
	}

	private void applyFile() {
		listener.applyBaseFile(tFile.getText(), tComment.getText());
		listener.fillTable(table);
		setInput(false);
		getShell().setDefaultButton(bNew);
	}

	private void setInput(boolean enabled) {
		tFile.setEnabled(enabled);
		tComment.setEnabled(enabled);
		butOpenFileDialog.setEnabled(enabled);
		if (enabled) {
			tFile.setText(listener.getFile());
			tComment.setText(listener.getComment());
			tFile.setFocus();
		}
		else {
			tFile.setText("");
			tComment.setText("");
		}
		bApply.setEnabled(false);
		button.setEnabled(false);
		bRemove.setEnabled(table.getSelectionCount() > 0);
	}

	public void setToolTipText() {
//
	}

	// �ffnet das File Dialog um ein Basefile auszuw�hlen
	private void openFileDialog() {
		sos.scheduler.editor.app.IContainer con = MainWindow.getContainer();
		String currPath = "";
		String sep = System.getProperty("file.separator");
		if (con.getCurrentEditor().getFilename() != null && con.getCurrentEditor().getFilename().length() > 0) {
			currPath = new java.io.File(con.getCurrentEditor().getFilename()).getParent();
		}
		else {
			currPath = Options.getSchedulerData() + sep + "config";
		}

		currPath = currPath.replace("/".toCharArray()[0], sep.toCharArray()[0]);
		currPath = currPath.replace("\\".toCharArray()[0], sep.toCharArray()[0]);

		FileDialog fdialog = JOE_FD_BaseForm_OpenBaseFile.Control(new FileDialog(MainWindow.getSShell(), SWT.OPEN));
		fdialog.setFilterPath(currPath);
		fdialog.setFilterExtensions(new String[] { "*.xml", "*.sosdoc", "*.*" });
//		fdialog.setText(Messages.getLabel("OpenBaseFile"));

		String fname = fdialog.open();
		if (fname == null)
			return;

		// fname = fname.substring(currPath.length()+1);
		fname = fname.replaceAll("/", sep);
		// fname = fname.replaceAll("\\\\", sep);

		if (fname.toLowerCase().startsWith(currPath.toLowerCase()))
			fname = fname.substring(currPath.length() + 1);

		tFile.setText(fname);

		// path = fdialog.getFilterPath();
	}

	// �ffnet den BaseFile im seperaten Tabitem im Editor
	private void openBaseElement() {
		String currPath = "";
		String sep = System.getProperty("file.separator");
		if (tFile.getText() != null && tFile.getText().length() > 0) {

			sos.scheduler.editor.app.IContainer con = MainWindow.getContainer();

			if (con.getCurrentEditor().getFilename() != null && con.getCurrentEditor().getFilename().length() > 0) {
				currPath = new java.io.File(con.getCurrentEditor().getFilename()).getParent();
			}
			else {
				currPath = sos.scheduler.editor.app.Options.getSchedulerData() + sep + "config";
			}
			if (!(currPath.endsWith("/") || currPath.endsWith("\\")))
				currPath = currPath.concat(sep);

			if (tFile.getText().toLowerCase().startsWith(currPath.toLowerCase()))
				currPath = tFile.getText();
			else
				currPath = currPath.concat(tFile.getText());

			con.openScheduler(currPath);

			con.setStatusInTitle();

		}
		else {
			MainWindow.message("There is no Basefile defined.", SWT.ICON_WARNING | SWT.OK);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
