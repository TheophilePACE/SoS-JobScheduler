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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.jdom.Element;

import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.listeners.DaysListener;
import sos.scheduler.editor.conf.listeners.SpecificWeekdaysListener;

public class SpecificWeekdaysForm extends SOSJOEMessageCodes implements IUpdateLanguage {
	
	
    private SpecificWeekdaysListener     listener        = null;

    private ISchedulerUpdate             _main           = null;      

    private Group                        group           = null;
 
    private Combo                        cWeekdays       = null;

    private Button                       bAdd            = null;

    private List                         lUsedDays       = null;

    private Button                       bRemove         = null;

    private Label                        label2          = null;
    
    private Combo                        cWeekdayNumber  = null;


    public SpecificWeekdaysForm(Composite parent, int style, SchedulerDom dom, Element job, ISchedulerUpdate main, int type) {
        super(parent, style);
     
        listener = new SpecificWeekdaysListener(dom, job);
        _main = main;

        initialize();
        setToolTipText();
        lUsedDays.setItems(listener.getDays());
        
        this.group.setEnabled(Utils.isElementEnabled("job", dom, job));
     
    }


    private void initialize() {
        this.setLayout(new FillLayout());
        createGroup();
        setSize(new org.eclipse.swt.graphics.Point(443, 312));
    }


    /**
     * This method initializes group
     */
    private void createGroup() {
        GridData gridData5 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.CENTER, false, false, 3, 1);
        gridData5.heightHint = 10;
        GridData gridData3 = new org.eclipse.swt.layout.GridData();
        gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        GridData gridData = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        
        group = JOE_G_SpecificWeekdaysForm_Monthdays.Control(new Group(this, SWT.NONE));
        group.setLayout(gridLayout);
 

        createCombo();
        
        GridData gridData2 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.CENTER, false, false);
        gridData2.widthHint = 90;
        bAdd = JOE_B_SpecificWeekdaysForm_AddWeekday.Control(new Button(group, SWT.NONE));
        bAdd.setLayoutData(gridData2);
        getShell().setDefaultButton(bAdd);
        bAdd.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                listener.addDay(cWeekdays.getText() ,cWeekdayNumber.getText());
                _main.updateSpecificWeekdays();
                _main.updateFont();
                String s = cWeekdayNumber.getText() + "." + cWeekdays.getText();
                if (lUsedDays.indexOf(s) == -1) lUsedDays.add(s);
                bRemove.setEnabled(lUsedDays.getSelectionCount() > 0);
            }
        });

        label2 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
//      label2.setText("Label");
        label2.setLayoutData(gridData5);
        
        lUsedDays = JOE_Lst_SpecificWeekdaysForm_UsedDays.Control(new List(group, SWT.BORDER));
        lUsedDays.setLayoutData(gridData);
        lUsedDays.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                bRemove.setEnabled(lUsedDays.getSelectionCount() > 0);
            }
        });
        
        bRemove = JOE_B_SpecificWeekdaysForm_RemoveWeekday.Control(new Button(group, SWT.NONE));
        bRemove.setEnabled(false);
        bRemove.setLayoutData(gridData3);
        bRemove.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                listener.deleteDay(lUsedDays.getItem(lUsedDays.getSelectionIndex()));
                _main.updateFont();
                lUsedDays.remove(lUsedDays.getSelectionIndex());
                _main.updateSpecificWeekdays();
                bRemove.setEnabled(lUsedDays.getSelectionCount() > 0);
            }
        });
    }


    /**
     * This method initializes combo
     */
    private void createCombo() {
        GridData gridData4 = new org.eclipse.swt.layout.GridData(GridData.FILL, GridData.CENTER, false, false);
        gridData4.widthHint = 300;

        cWeekdayNumber = JOE_Cbo_SpecificWeekdaysForm_Daynames.Control(new Combo(group, SWT.NONE));
        cWeekdayNumber.setItems(SpecificWeekdaysListener._daynames);
        cWeekdayNumber.setVisibleItemCount(8);
        cWeekdayNumber.select(0);
        cWeekdayNumber.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
        
//        String[] strWeekdays = JOE_M_SpecificWeekdaysForm_Weekdays.label().split(";");
        String[] strWeekdays = DaysListener.getWeekdays();
        cWeekdays = JOE_Cbo_SpecificWeekdaysForm_Weekdays.Control(new Combo(group, SWT.READ_ONLY));
        cWeekdays.setItems(strWeekdays);
        cWeekdays.setText(strWeekdays[0]);
        cWeekdays.setVisibleItemCount(7);
        cWeekdays.setLayoutData(gridData4);
    }




    public void setToolTipText() {
//
    }

} // @jve:decl-index=0:visual-constraint="10,10"
