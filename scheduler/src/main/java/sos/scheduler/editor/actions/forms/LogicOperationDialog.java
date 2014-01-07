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
package sos.scheduler.editor.actions.forms;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.ErrorLog;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.util.SOSString;

class LogicOperationDialog extends org.eclipse.swt.widgets.Dialog {

	private final Object		result			= null;

	private Text		txt				= null;

	private Text		txtExpression	= null;

	private Button		butCancel		= null;

	private Button		butApply		= null;

	private ArrayList	operator		= null;

	private final SOSString	sosString		= new SOSString();

	private final ArrayList	undo			= new ArrayList();

	private List		listOfIds		= null;

	private List		list			= null;

	private Button		restoreButton	= null;

	private Button		butClear		= null;

	public LogicOperationDialog(final int style) {
		super(new Shell(), style);
	}

	public Object open(final Text txt_, final ArrayList operator_) {

		txt = txt_;
		operator = operator_;
		Shell parent = getParent();
		final Shell newFolderShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		newFolderShell.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(final TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE) {
					close();
				}
			}
		});

		newFolderShell.setImage(sos.scheduler.editor.app.ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		gridLayout.marginTop = 10;
		gridLayout.numColumns = 2;
		newFolderShell.setLayout(gridLayout);
		newFolderShell.setText(SOSJOEMessageCodes.JOE_M_LogicOperationDialog_LogicalOperation.label());

		newFolderShell.pack();

		txtExpression = SOSJOEMessageCodes.JOE_T_LogicOperationDialog_Expression.Control(new Text(newFolderShell, SWT.MULTI | SWT.BORDER | SWT.WRAP));
		txtExpression.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == SWT.CR)
					doSomethings();
			}
		});
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1);
		gridData.heightHint = 104;
		txtExpression.setLayoutData(gridData);

		list = SOSJOEMessageCodes.JOE_Lst_LogicOperationDialog_Operators.Control(new List(newFolderShell, SWT.BORDER));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				if (list.getSelectionCount() > 0) {
					String sel = list.getSelection()[0];
					txtExpression.insert(sel);
				}
			}
		});
		final GridData gridData_1 = new GridData(GridData.FILL, GridData.FILL, true, true);
		list.setLayoutData(gridData_1);

		listOfIds = SOSJOEMessageCodes.JOE_Lst_LogicOperationDialog_Group.Control(new List(newFolderShell, SWT.BORDER));
		listOfIds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				if (listOfIds.getSelectionCount() > 0) {
					String sel = listOfIds.getSelection()[0] + " ";
					txtExpression.insert(sel);
				}
			}
		});
		final GridData gridData_2 = new GridData(GridData.FILL, GridData.FILL, true, true);
		listOfIds.setLayoutData(gridData_2);

		final Composite composite_1 = new Composite(newFolderShell, SWT.NONE);
		composite_1.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.verticalSpacing = 0;
		gridLayout_2.marginWidth = 0;
		gridLayout_2.horizontalSpacing = 0;
		composite_1.setLayout(gridLayout_2);

		butCancel = SOSJOEMessageCodes.JOE_B_LogicOperationDialog_Cancel.Control(new Button(composite_1, SWT.NONE));
		butCancel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		butCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				close();
			}
		});

		final Composite composite = new Composite(newFolderShell, SWT.NONE);
		final GridData gridData_3 = new GridData(GridData.END, GridData.FILL, true, false);
		composite.setLayoutData(gridData_3);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginHeight = 0;
		gridLayout_1.marginWidth = 0;
		gridLayout_1.numColumns = 3;
		composite.setLayout(gridLayout_1);

		restoreButton = SOSJOEMessageCodes.JOE_B_LogicOperationDialog_Restore.Control(new Button(composite, SWT.NONE));
		restoreButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false));
		restoreButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				try {
					if (undo.size() > 0) {
						undo.add(0, txtExpression.getText());
						txtExpression.setText(sosString.parseToString(undo.get(undo.size() - 1)));
						undo.remove(undo.size() - 1);
						txtExpression.setFocus();
					}
				}
				catch (Exception es) {
					System.out.print(e.toString());
					//tu nichts
				}
			}
		});

		butClear = SOSJOEMessageCodes.JOE_B_LogicOperationDialog_Clear.Control(new Button(composite, SWT.NONE));
		butClear.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		butClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				undo.add(txtExpression.getText());
				txtExpression.setText("");
				txtExpression.setFocus();
			}
		});

		butApply = SOSJOEMessageCodes.JOE_B_LogicOperationDialog_Apply.Control(new Button(composite, SWT.NONE));
		butApply.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		butApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				doSomethings();
			}
		});
		newFolderShell.open();

		//org.eclipse.swt.graphics.Rectangle rect = image.getBounds();
		newFolderShell.setSize(476, 406);

		init();

		org.eclipse.swt.widgets.Display display = parent.getDisplay();
		while (!newFolderShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		return result;

	}

	private void init() {
		try {

			txtExpression.setText(txt.getText());

			ArrayList _list = new ArrayList();
			_list.add("or ");
			_list.add("and ");
			_list.add("not ");
			_list.add("(<key1> or <key2>) and (<key3> or <key4>)");

			for (int i = 0; i < _list.size(); i++) {
				list.add(sosString.parseToString(_list.get(i)));
			}

			for (int i = 0; i < operator.size(); i++) {
				listOfIds.add(sosString.parseToString(operator.get(i)));
			}
			setToolTipText();

		}
		catch (Exception e) {
			try {
				new ErrorLog(SOSJOEMessageCodes.JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
			}
			catch (Exception ee) {
				//tu nichts
			}
		}
	}

	public static void main(final String[] args) {
		final Shell shell = new Shell();
		shell.pack();

		LogicOperationDialog logicOperationDialog = new LogicOperationDialog(SWT.NONE);
		Text text = new Text(shell, SWT.NONE);
		text.setText("1 or 2");
		ArrayList l = new ArrayList();
		l.add("group1");
		l.add("group2");
		logicOperationDialog.open(text, l);
	}

	private void close() {
		getParent().close();
	}

	public void doSomethings() {
		txt.setText(txtExpression.getText());
		close();
	}

	public void setToolTipText() {
		//
	}

}
