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
package sos.scheduler.editor.doc.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.IncludeFilesListener;

public class IncludeFilesForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {
    private IncludeFilesListener listener = null; // @jve:decl-index=0:

    private Group                group    = null;

    @SuppressWarnings("unused")
	private Label                label    = null;

    private Text                 tFile    = null;

    private Button               bAdd     = null;

    private Label                label1   = null;

    private Label                label51  = null;

    private List                 fileList = null;

    private Button               bRemove  = null;


    public IncludeFilesForm(Composite parent, int style) {
        super(parent, style);
        initialize();
        setToolTipText();
        bAdd.setEnabled(false);
        bRemove.setEnabled(false);
    }


    public void setParams(DocumentationDom dom, Element parent) {
        listener = new IncludeFilesListener(dom, parent);
        fileList.setItems(listener.getIncludes());
    }


    private void initialize() {
        createGroup();
        setSize(new Point(600, 365));
        setLayout(new FillLayout());
       
    }


    public void setSeparator(String separator) {
        label51.setText(separator);
    }


    /**
     * This method initializes group
     */
    private void createGroup() {
        GridData gridData4 = new GridData();
        gridData4.horizontalAlignment = GridData.FILL; // Generated
        gridData4.verticalAlignment = GridData.CENTER; // Generated
        GridData gridData3 = new GridData();
        gridData3.horizontalAlignment = GridData.FILL; // Generated
        gridData3.verticalAlignment = GridData.BEGINNING; // Generated
        GridData gridData2 = new GridData();
        gridData2.horizontalAlignment = GridData.FILL; // Generated
        gridData2.grabExcessHorizontalSpace = true; // Generated
        gridData2.grabExcessVerticalSpace = true; // Generated
        gridData2.verticalAlignment = GridData.FILL; // Generated
        GridData gridData1 = new GridData();
        gridData1.horizontalSpan = 3; // Generated
        gridData1.verticalAlignment = GridData.CENTER; // Generated
        gridData1.horizontalAlignment = GridData.FILL; // Generated
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL; // Generated
        gridData.grabExcessHorizontalSpace = true; // Generated
        gridData.verticalAlignment = GridData.CENTER; // Generated
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3; // Generated
        
        group = JOE_G_IncludeFilesForm_IncludeFiles.Control(new Group(this, SWT.NONE));
        group.setLayout(gridLayout); // Generated
        
        label = JOE_L_IncludeFilesForm_File.Control(new Label(group, SWT.NONE));
        
        tFile = JOE_T_IncludeFilesForm_File.Control(new Text(group, SWT.BORDER));
        tFile.setLayoutData(gridData); // Generated
        tFile.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                bAdd.setEnabled(tFile.getText().length() > 0);
                getShell().setDefaultButton(bAdd);
            }
        });
        
        bAdd = JOE_B_IncludeFilesForm_Add.Control(new Button(group, SWT.NONE));
        bAdd.setLayoutData(gridData4); // Generated
        bAdd.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                addFile();
            }
        });
        
        label1 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
        label1.setText("Label"); // Generated
        label1.setLayoutData(gridData1); // Generated
        
        label51 = JOE_L_IncludeFilesForm_Parameter.Control(new Label(group, SWT.NONE));
        label51.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        label51.setVisible(false); // Generated
        
        fileList = JOE_Lst_IncludeFilesForm_Files.Control(new List(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL));
        fileList.setLayoutData(gridData2); // Generated
        fileList.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                boolean selection = fileList.getSelectionIndex() >= 0;
                if (selection)
                    tFile.setText(fileList.getItem(fileList.getSelectionIndex()));
                bAdd.setEnabled(false);
                bRemove.setEnabled(selection);
            }
        });
        
        bRemove = JOE_B_IncludeFilesForm_Remove.Control(new Button(group, SWT.NONE));
        bRemove.setLayoutData(gridData3); // Generated
        bRemove.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                removeFile();
            }
        });
    }


    public void apply() {
        addFile();
        if (listener != null)
            listener.saveIncludes(fileList.getItems());
    }


    public boolean isUnsaved() {
        if (listener != null)
            listener.saveIncludes(fileList.getItems());
        return bAdd.isEnabled();
    }


    public void setToolTipText() {
//    	
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        tFile.setEnabled(enabled);
        bAdd.setEnabled(false);
        fileList.setEnabled(enabled);
        bRemove.setEnabled(false);
    }


    private void addFile() {
        if (tFile.getText().length() > 0 && !listener.exists(tFile.getText(), fileList.getItems())) {
            fileList.add(tFile.getText());
            tFile.setText("");
            bAdd.setEnabled(false);
            fileList.deselectAll();
            bRemove.setEnabled(false);
            listener.setChanges(true);
        }
    }


    private void removeFile() {
        if (fileList.getSelectionIndex() >= 0) {
            fileList.remove(fileList.getSelectionIndex());
            fileList.deselectAll();
            tFile.setText("");
            bRemove.setEnabled(false);
            listener.setChanges(true);
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
