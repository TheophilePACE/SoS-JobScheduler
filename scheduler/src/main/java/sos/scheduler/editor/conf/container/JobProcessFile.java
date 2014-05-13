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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import sos.scheduler.editor.app.SOSJOEMessageCodes;

import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobProcessFile extends FormBaseClass {

    @SuppressWarnings("unused")
    private final String conClassName       = "JobProcessFile";
    @SuppressWarnings("unused")
    private final String conSVNVersion      = "$Id: JobProcessFile.java 18346 2012-11-02 14:07:36Z ur $";

    private boolean      init               = true;
    private JobListener  objJobDataProvider = null;
    private Text         tExecuteFile       = null;
    private Text         tParameter         = null;
    private Text         tLogFile           = null;
    private Button       bIgnoreSignal      = null;
    private Button       bIgnoreError       = null;

    public JobProcessFile(Composite pParentComposite, JobListener pobjJobDataProvider) {
        super(pParentComposite, pobjJobDataProvider);
        objJobDataProvider = pobjJobDataProvider;

        init = true;
        createGroup();
        initForm();
        init = false;
    }

    public void apply() {
        // if (isUnsaved())
        // addParam();
    }

    public boolean isUnsaved() {
        return false;
    }

    public void refreshContent() {
    }

    private void initForm() {
        tExecuteFile.setText(objJobDataProvider.getFile());
        tLogFile.setText(objJobDataProvider.getLogFile());
        tParameter.setText(objJobDataProvider.getParam());
        bIgnoreError.setSelection(objJobDataProvider.isIgnoreError());
        bIgnoreSignal.setSelection(objJobDataProvider.isIgnoreSignal());
        tExecuteFile.setFocus();
    }

    private void createGroup() {
        objParent.setLayout(new GridLayout());

        GridData gridData61 = new org.eclipse.swt.layout.GridData();
        gridData61.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        gridData61.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        GridData gridData41 = new org.eclipse.swt.layout.GridData();
        gridData41.grabExcessHorizontalSpace = false;
        gridData41.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        gridData41.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        gridData41.horizontalSpan = 2;
        GridData gridData21 = new org.eclipse.swt.layout.GridData();
        gridData21.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        gridData21.grabExcessHorizontalSpace = false;
        gridData21.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
        GridData gridData12 = new org.eclipse.swt.layout.GridData();
        gridData12.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData12.grabExcessHorizontalSpace = true;
        gridData12.horizontalSpan = 3;
        gridData12.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        GridData gridData3 = new org.eclipse.swt.layout.GridData();
        gridData3.horizontalSpan = 3;
        gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        GridData gridData2 = new org.eclipse.swt.layout.GridData();
        gridData2.horizontalSpan = 3;
        gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        
        Group gExecutable = SOSJOEMessageCodes.JOE_G_JobProcessFile_RunExecutable.Control(new Group(objParent, SWT.NONE));
        gExecutable.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        gExecutable.setLayout(gridLayout);
        
        @SuppressWarnings("unused")
		Label label1 = SOSJOEMessageCodes.JOE_L_JobProcessFile_File.Control(new Label(gExecutable, SWT.NONE));
        
        tExecuteFile = SOSJOEMessageCodes.JOE_T_JobProcessFile_File.Control(new Text(gExecutable, SWT.BORDER));
        tExecuteFile.setLayoutData(gridData12);
        tExecuteFile.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                if (!init) {
                    objJobDataProvider.setFile(tExecuteFile.getText());
                }
            }
        });
        
        @SuppressWarnings("unused")
		Label label3 = SOSJOEMessageCodes.JOE_L_JobProcessFile_Parameter.Control(new Label(gExecutable, SWT.NONE));

        tParameter = SOSJOEMessageCodes.JOE_T_JobProcessFile_Parameter.Control(new Text(gExecutable, SWT.BORDER));
        tParameter.setLayoutData(gridData2);
        tParameter.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                if (!init)
                    objJobDataProvider.setParam(tParameter.getText());
            }
        });
        
        @SuppressWarnings("unused")
		Label label4 = SOSJOEMessageCodes.JOE_L_JobProcessFile_LogFile.Control(new Label(gExecutable, SWT.NONE));

        tLogFile = SOSJOEMessageCodes.JOE_T_JobProcessFile_LogFile.Control(new Text(gExecutable, SWT.BORDER));
        tLogFile.setLayoutData(gridData3);
        tLogFile.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                if (!init)
                    objJobDataProvider.setLogFile(tLogFile.getText());
            }
        });
        
        Label label5 = SOSJOEMessageCodes.JOE_L_JobProcessFile_Ignore.Control(new Label(gExecutable, SWT.NONE));
        label5.setLayoutData(gridData61);
        
        bIgnoreSignal = SOSJOEMessageCodes.JOE_B_JobProcessFile_IgnoreSignal.Control(new Button(gExecutable, SWT.CHECK));
        bIgnoreSignal.setLayoutData(gridData21);
        bIgnoreSignal.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (!init)
                    objJobDataProvider.setIgnoreSignal(bIgnoreSignal.getSelection());
            }
        });
        
        bIgnoreError = SOSJOEMessageCodes.JOE_B_JobProcessFile_IgnoreError.Control(new Button(gExecutable, SWT.CHECK));
        bIgnoreError.setLayoutData(gridData41);
        bIgnoreError.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (!init)
                    objJobDataProvider.setIgnoreError(bIgnoreError.getSelection());
            }
        });

        boolean enabled = true;
        tExecuteFile.setEnabled(enabled);
        tLogFile.setEnabled(enabled);
        tParameter.setEnabled(enabled);
        bIgnoreError.setEnabled(enabled);
        bIgnoreSignal.setEnabled(enabled);

        objParent.layout();
    }

}
