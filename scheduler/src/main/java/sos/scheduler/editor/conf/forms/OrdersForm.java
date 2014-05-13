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
package sos.scheduler.editor.conf.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.jdom.Element;

import sos.scheduler.editor.app.ContextMenu;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.OrdersListener;

//import sos.scheduler.editor.conf.listeners.SchedulerListener;

public class OrdersForm extends SOSJOEMessageCodes implements IUpdateLanguage {

	private OrdersListener	listener;

	// private SchedulerListener mainListener;

	private Group			ordersGroup		= null;
	private static Table	table			= null;
	private Button			bNewOrder		= null;
	private Button			bRemoveOrder	= null;
	private Label			label			= null;
	private SchedulerDom	_dom			= null;

	// public OrdersForm(Composite parent, int style, SchedulerDom dom, ISchedulerUpdate update, SchedulerListener mainListener) {
	public OrdersForm(Composite parent, int style, SchedulerDom dom, ISchedulerUpdate update) {
		super(parent, style);
		_dom = dom;
		// this.mainListener = mainListener;
		listener = new OrdersListener(dom, update);
		initialize();
		setToolTipText();
		listener.fillTable(table);

	}

	private void initialize() {
		this.setLayout(new FillLayout());
		createGroup();
		setSize(new org.eclipse.swt.graphics.Point(656, 400));
	}

	private void createGroup() {
		GridData gridData4 = new org.eclipse.swt.layout.GridData();
		gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData4.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		GridData gridData1 = new org.eclipse.swt.layout.GridData();
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		
		ordersGroup = JOE_G_OrdersForm_Orders.Control(new Group(this, SWT.NONE));
		ordersGroup.setLayout(gridLayout);
		
		createTable();
		
		bNewOrder = JOE_B_OrdersForm_NewOrder.Control(new Button(ordersGroup, SWT.NONE));
		bNewOrder.setLayoutData(gridData);
		getShell().setDefaultButton(bNewOrder);
		bNewOrder.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				listener.newCommands(table);
				bRemoveOrder.setEnabled(true);
			}
		});
		
		label = new Label(ordersGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
//		label.setText("Label");
		label.setLayoutData(gridData4);
		
		bRemoveOrder = JOE_B_OrdersForm_RemoveOrder.Control(new Button(ordersGroup, SWT.NONE));
		bRemoveOrder.setEnabled(false);
		bRemoveOrder.setLayoutData(gridData1);
		bRemoveOrder.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//				int c = MainWindow.message(getShell(), "Do you want to remove the order?", SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				int c = MainWindow.message(getShell(), JOE_M_OrdersForm_RemoveOrder.label(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				if (c != SWT.YES)
					return;
				bRemoveOrder.setEnabled(listener.deleteCommands(table));
			}
		});
	}

	/**
	 * This method initializes table
	 */
	private void createTable() {
		GridData gridData2 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 1, 3);
		gridData2.widthHint = 204;
		
		table = JOE_Tbl_OrdersForm_Table.Control(new Table(ordersGroup, SWT.BORDER | SWT.FULL_SELECTION));
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(final MouseEvent e) {
				if (table.getSelectionCount() > 0)
					ContextMenu.goTo(table.getSelection()[0].getText(0), _dom, Editor.ORDER);
			}
		});
		table.setHeaderVisible(true);
		table.setLayoutData(gridData2);
		table.setLinesVisible(true);
		
		table.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				boolean enabled = true;
				if (table.getSelectionIndex() > -1) {
					Element currElem = (Element) table.getSelection()[0].getData();
					if (currElem != null && !Utils.isElementEnabled("commands", _dom, currElem)) {
						enabled = false;
					}
					bRemoveOrder.setEnabled(enabled);
				}
			}
		});
		
		TableColumn tableColumn = JOE_TCl_OrdersForm_OrderNameID.Control(new TableColumn(table, SWT.NONE));
		tableColumn.setWidth(240);
		
	}

	public void setToolTipText() {
//
	}

	public static Table getTable() {
		return table;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
