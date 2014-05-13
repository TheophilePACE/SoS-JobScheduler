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
package sos.scheduler.editor.actions.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;
import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.actions.listeners.ActionsListListener;
import sos.scheduler.editor.app.ContextMenu;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;


public class ActionsListForm extends SOSJOEMessageCodes implements IUpdateLanguage {
    
	private ActionsListListener listener     = null;

    private Group       actionsGroup         = null;
    
    private Table       list                 = null; 
         
    private Button      butRemove            = null;
    
    private Button      butNew               = null;
    
    private ActionsForm gui                 = null;
    
    private ActionsDom  _dom                 = null;
 
    public ActionsListForm(Composite parent, int style, ActionsDom dom, Element actions, ActionsForm _gui) {
        super(parent, style);
        gui = _gui;
        _dom = dom;
        listener = new ActionsListListener(dom, actions);
        initialize();
        setToolTipText();
       
    }


    private void initialize() {
        createGroup();
        setSize(new Point(696, 462));
        setLayout(new FillLayout());
        listener.fillActions(list);

    }


    /**
     * This method initializes group
     */
    private void createGroup() {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2; // Generated
        actionsGroup = JOE_G_ActionsListForm_Actions.Control(new Group(this, SWT.NONE));
        actionsGroup.setLayout(gridLayout); // Generated

        list = JOE_Tbl_ActionsListForm_ActionsList.Control(new Table(actionsGroup, SWT.BORDER));
        list.addMouseListener(new MouseAdapter() {
        	public void mouseDoubleClick(final MouseEvent e) {
        		ContextMenu.goTo(list.getSelection()[0].getText(0), _dom, Editor.ACTIONS);
        	}
        });
        list.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(final SelectionEvent e) {        		
        		butRemove.setEnabled(list.getSelectionCount() > 0);
        	}
        });
        list.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 3));

        butNew = JOE_B_ActionsListForm_New.Control(new Button(actionsGroup, SWT.NONE));
        butNew.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(final SelectionEvent e) {
        		TableItem item = new TableItem(list, SWT.NONE);
        		item.setText(JOE_M_ActionsListForm_NewAction.params(list.getItemCount()));
//        		listener.newAction("New Action " + list.getItemCount());
        		listener.newAction(JOE_M_ActionsListForm_NewAction.params(list.getItemCount()));
        		listener.fillActions(list);
        		butRemove.setEnabled(false);
        		gui.updateActions();
        	}
        });
        butNew.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false, false));

        butRemove = JOE_B_ActionsListForm_Remove.Control(new Button(actionsGroup, SWT.NONE));
        butRemove.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(final SelectionEvent e) {
        		listener.removeAction(list);
        		
        		butRemove.setEnabled(false);
        		gui.updateActions();
        		
        	}
        });
        new Label(actionsGroup, SWT.NONE);
    }
 

    public void setToolTipText() {
//
    }
    

} // @jve:decl-index=0:visual-constraint="10,10"

