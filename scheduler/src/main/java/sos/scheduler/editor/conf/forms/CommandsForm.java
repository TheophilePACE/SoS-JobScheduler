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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.CommandsListener;


public class CommandsForm extends SOSJOEMessageCodes implements IUpdateLanguage {

    private Text              tCommands;

    private CommandsListener  listener;

    //private SchedulerListener mainListener;

    private Group             commandsGroup = null;

    private Button            bSave         = null;



    public CommandsForm(Composite parent, int style, SchedulerDom dom, ISchedulerUpdate main) throws Exception {
        super(parent, style);
        listener = new CommandsListener(dom, main);
        initialize();
        setToolTipText();
        tCommands.setText(listener.readCommands());
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
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        commandsGroup = JOE_G_CommandsForm_Commands.Control(new Group(this, SWT.NONE));
//        commandsGroup.setText("Commands");
        commandsGroup.setLayout(gridLayout);
        createTable();

        tCommands = JOE_T_CommandsForm_Commands.Control(new Text(commandsGroup, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL));
        tCommands.addKeyListener(new KeyAdapter() {
        	public void keyPressed(final KeyEvent e) {
        		if(e.keyCode==97 && e.stateMask == SWT.CTRL){
        			tCommands.setSelection(0, tCommands.getText().length());
				}
        	}
        });
        tCommands.setFont(ResourceManager.getFont("Courier New", 8, SWT.NONE));
        final GridData gridData4_1 = new GridData(GridData.FILL, GridData.FILL, true, true);
        tCommands.setLayoutData(gridData4_1);
        
        GridData gridData = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.BEGINNING, false, false);
        bSave = JOE_B_CommandsForm_Apply.Control(new Button(commandsGroup, SWT.NONE));
//        bSave.setText("&Apply");
        bSave.setLayoutData(gridData);
        getShell().setDefaultButton(bSave);
        bSave.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                listener.saveCommands(tCommands.getText());
            }
        });
    }
    /**
     * This method initializes table
     */
    private void createTable() {
    }
    
    public void setToolTipText() {
//        bSave.setToolTipText(Messages.getTooltip("commands.btn_save"));
//        tCommands.setToolTipText(Messages.getTooltip("commands.commands"));
    }
} // @jve:decl-index=0:visual-constraint="10,10"
