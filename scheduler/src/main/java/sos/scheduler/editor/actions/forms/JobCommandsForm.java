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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.actions.listeners.JobCommandsListener;


public class JobCommandsForm extends SOSJOEMessageCodes implements IUpdateLanguage {

    private JobCommandsListener listener       = null;

    private Group               commandsGroup  = null;

    private Table               table          = null;

    private Button              bNewCommands   = null;

    private Button              bRemoveCommand = null;

    private Label               label          = null;
    
    private ActionsDom          _dom           = null;
    

                                     
  public JobCommandsForm(Composite parent, int style, ActionsDom dom, Element action,  ActionsForm gui) {
        super(parent, style);        
  
        listener = new JobCommandsListener(dom, action, gui);
        _dom = dom;
        initialize();
        setToolTipText();
        listener.fillTable(table);       
    }
  


    private void initialize() {
        this.setLayout(new FillLayout());
        createGroup();
        setSize(new org.eclipse.swt.graphics.Point(656, 400));
    }


    /**
     * This method initializes group
     */
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
        
        commandsGroup = JOE_G_ActionsJobCommandsForm_Commands.Control(new Group(this, SWT.NONE));
        commandsGroup.setLayout(gridLayout);
        
        createTable();
        
        bNewCommands = JOE_B_ActionsJobCommandsForm_NewCommand.Control(new Button(commandsGroup, SWT.NONE));
        bNewCommands.setLayoutData(gridData);
        getShell().setDefaultButton(bNewCommands);

        label = new Label(commandsGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
//        label.setText("Label");
        label.setLayoutData(gridData4);
        bNewCommands.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                listener.newCommands(table);
                bRemoveCommand.setEnabled(true);
            }
        });
        
        bRemoveCommand = JOE_B_ActionsJobCommandsForm_RemoveCommand.Control(new Button(commandsGroup, SWT.NONE));
        bRemoveCommand.setEnabled(false);
        bRemoveCommand.setLayoutData(gridData1);
        bRemoveCommand.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		if(table != null && table.getSelectionCount() > 0)  {
        			int cont = sos.scheduler.editor.app.MainWindow.message(getShell(), JOE_M_EventForm_RemoveCommand.label(), SWT.ICON_WARNING | SWT.OK |SWT.CANCEL );

        			if(cont == SWT.OK) {				        				
        				bRemoveCommand.setEnabled(listener.deleteCommands(table));        				
        			} 
        		}
        	}
        });
    }


    /**
     * This method initializes table
     */
    private void createTable() {
        GridData gridData2 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 1, 3);
        gridData2.widthHint = 204;
        
        table = JOE_Tbl_ActionsJobCommandsForm_Commands.Control(new Table(commandsGroup, SWT.BORDER | SWT.FULL_SELECTION));
        table.addMouseListener(new MouseAdapter() {
        	public void mouseDoubleClick(final MouseEvent e) {
        		ContextMenu.goTo(table.getSelection()[0].getText(0), _dom, Editor.ACTION_COMMANDS);
        	}
        });
        table.setHeaderVisible(true);
        table.setLayoutData(gridData2);
        table.setLinesVisible(true);
        
        TableColumn commandnameTableColumn = JOE_TCl_ActionsJobCommandsForm_Command.Control(new TableColumn(table, SWT.NONE));
        commandnameTableColumn.setWidth(151);

        final TableColumn newColumnTableColumn_1 = JOE_TCl_ActionsJobCommandsForm_Host.Control(new TableColumn(table, SWT.NONE));
        newColumnTableColumn_1.setWidth(100);
        
        table.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                bRemoveCommand.setEnabled(true);
            }
        });

        final TableColumn newColumnTableColumn = JOE_TCl_ActionsJobCommandsForm_Port.Control(new TableColumn(table, SWT.NONE));
        newColumnTableColumn.setWidth(100);

    }


    public void setToolTipText() {
//    	
    }

} // @jve:decl-index=0:visual-constraint="10,10"
