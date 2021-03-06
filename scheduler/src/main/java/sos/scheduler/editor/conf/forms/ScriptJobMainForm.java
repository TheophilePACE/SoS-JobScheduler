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

//import org.eclipse.draw2d.*;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.CTabItem;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
 
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
 
import org.jdom.Element;

import sos.scheduler.editor.app.Messages;

import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.composites.JobMainComposite;
import sos.scheduler.editor.conf.container.JobDelayAfterError;
import sos.scheduler.editor.conf.container.JobDocumentation;
import sos.scheduler.editor.conf.container.JobEmailSettings;
import sos.scheduler.editor.conf.container.JobOptions;
import sos.scheduler.editor.conf.container.JobProcessFile;
import sos.scheduler.editor.conf.container.JobSetback;
import sos.scheduler.editor.conf.container.JobSourceViewer;
import sos.scheduler.editor.conf.container.JobStartWhenDirectoryChanged;
import sos.scheduler.editor.conf.listeners.JobOptionsListener;

public class ScriptJobMainForm extends ScriptForm{

    @SuppressWarnings("unused")
    private final String conSVNVersion = "$Id$";
    
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(ScriptJobMainForm.class);
    @SuppressWarnings("unused")
    private final String conClassName = "ScriptJobMainForm";
 
    private JobOptionsListener objDataOptionsProvider;
   
    private Composite tabItemOptionsComposite = null;
    private Composite tabItemDirChangedComposite = null;
    private Composite objTabControlComposite = null;
    private Composite tabItemEMailComposite = null;
    private Composite tabItemDocumentationComposite = null;
    private Composite tabItemOrderSetBackComposite = null;
    private Composite tabItemDelayAfterErrorComposite = null;
    private Composite tabItemProcessFileComposite = null;
    private Composite tabItemSourceViewerComposite = null;

    private JobMainComposite jobMainComposite=null;
 
    private CTabItem tabItemSourceViewer = null;
    private CTabItem tabItemEMail = null;
    private CTabItem tabItemDocumentation = null;
    private CTabItem tabItemOrderSetBack = null;
    private CTabItem tabItemDelayAfterError = null;
    private CTabItem tabItemProcessFile = null;
    private CTabItem tabItemOptions = null;
    private CTabItem tabItemDirChanged = null;

    private JobOptions objJobOptions = null;  
   
    private Group objMainOptionsGroup = null;

    public ScriptJobMainForm(Composite parent, int style, SchedulerDom dom, Element job, ISchedulerUpdate main) {
        super(parent, style, dom, job, main);
        
        showWaitCursor();
        objDataOptionsProvider = new JobOptionsListener(dom, job);
        initialize();
        restoreCursor();
    }
    
    public void initForm(){
        jobMainComposite.init();
    }
    
    protected String[] getScriptLanguages(){
        return objDataProvider._languagesJob;
    }
    
    
    protected String getPredefinedFunctionNames(){
        return "spooler_init;spooler_open;spooler_process;spooler_close;spooler_exit;spooler_on_error;spooler_on_success";
    }
    
    protected void createGroup() {
        
        GridLayout gridLayoutMainOptionsGroup = new GridLayout();
        gridLayoutMainOptionsGroup.numColumns = 1;
        objMainOptionsGroup = new Group(this, SWT.NONE);
        objMainOptionsGroup.setText(objDataProvider.getJobNameAndTitle());

        objMainOptionsGroup.setLayout(gridLayoutMainOptionsGroup);
        objMainOptionsGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginHeight = 1;
        gridLayout.numColumns = 1;

        jobMainComposite = new JobMainComposite(objMainOptionsGroup, SWT.NONE,objDataProvider);
        jobMainComposite.setLayout(gridLayout);
        jobMainComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        createLanguageSelector(jobMainComposite.getgMain());
        createScriptTabForm(objMainOptionsGroup);
        createJobMainTabPages();
        languageSelector.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent arg0) {
                
                disposeJobMainTabPages();
                createJobMainTabPages();
                 
            }
        });

    }
 
    private void disposeJobMainTabPages() {
        
        if (tabItemSourceViewer != null) {
            tabItemSourceViewer.dispose();
        }
        
        if (tabItemEMail != null) {
            tabItemEMail.dispose();
        }

        if (tabItemDocumentation != null) {
            tabItemDocumentation.dispose();
        }
        if (tabItemOrderSetBack != null) {
            tabItemOrderSetBack.dispose();
        }
        if (tabItemDelayAfterError != null) {
            tabItemDelayAfterError.dispose();
        }
        if (tabItemProcessFile != null) {
            tabItemProcessFile.dispose();
        }

        if (tabItemOptions != null) {
            tabItemOptions.dispose();
        }
        if (tabItemDirChanged != null) {
            tabItemDirChanged.dispose();
        }
         
        if (tabItemDirChanged != null) {
            tabItemDirChanged.dispose();
        }

    }
     
    private void createJobMainTabPages() {
       tabItemSourceViewerComposite = null;
       tabItemOptionsComposite = null;
       tabItemDirChangedComposite = null;
       tabItemEMailComposite = null;
       tabItemDocumentationComposite = null;
       tabItemOrderSetBackComposite = null;
       tabItemDelayAfterErrorComposite = null;
       tabItemProcessFileComposite = null;
 
        objTabControlComposite = new Composite(tabFolder, SWT.NONE);
        objTabControlComposite.setLayout(new GridLayout());
        setResizableV(objTabControlComposite);
 
        tabItemProcessFile = JOE_TI_ScriptJobMainForm_ProcessFile.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemProcessFileComposite = new Composite(tabFolder, SWT.NONE);
        tabItemProcessFileComposite.setLayout(new GridLayout());
        setResizableV(tabItemProcessFileComposite);
        tabItemProcessFile.setControl(tabItemProcessFileComposite);
 
        tabItemOptions = JOE_TI_ScriptJobMainForm_Options.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemOptionsComposite = new Composite(tabFolder, SWT.NONE);
        tabItemOptionsComposite.setLayout(new GridLayout());
        setResizableV(tabItemOptionsComposite);
        tabItemOptions.setControl(tabItemOptionsComposite);

        tabItemEMail = JOE_TI_ScriptJobMainForm_EMail.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemEMailComposite = new Composite(tabFolder, SWT.NONE);
        tabItemEMailComposite.setLayout(new GridLayout());
        setResizableV(tabItemEMailComposite);
        tabItemEMail.setControl(tabItemEMailComposite);

        if (objDataProvider.isOrderJob()) {
            tabItemOrderSetBack = JOE_TI_ScriptJobMainForm_SetBack.Control(new CTabItem(tabFolder, SWT.NONE));
            tabItemOrderSetBackComposite = new Composite(tabFolder, SWT.NONE);
            tabItemOrderSetBackComposite.setLayout(new GridLayout());
            setResizableV(tabItemOrderSetBackComposite);
            tabItemOrderSetBack.setControl(tabItemOrderSetBackComposite);
        } else {
            tabItemDelayAfterError = JOE_TI_ScriptJobMainForm_OnError.Control(new CTabItem(tabFolder, SWT.NONE));
            tabItemDelayAfterErrorComposite = new Composite(tabFolder, SWT.NONE);
            tabItemDelayAfterErrorComposite.setLayout(new GridLayout());
            setResizableV(tabItemDelayAfterErrorComposite);
            tabItemDelayAfterError.setControl(tabItemDelayAfterErrorComposite);

            tabItemDirChanged = JOE_TI_ScriptJobMainForm_FileWatcher.Control(new CTabItem(tabFolder, SWT.NONE));
            tabItemDirChangedComposite = new Composite(tabFolder, SWT.NONE);
            tabItemDirChangedComposite.setLayout(new GridLayout());
            setResizableV(tabItemDirChangedComposite);
            tabItemDirChanged.setControl(tabItemDirChangedComposite);
        }

        tabItemDocumentation = JOE_TI_ScriptJobMainForm_Doc.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemDocumentationComposite = new Composite(tabFolder, SWT.NONE);
        tabItemDocumentationComposite.setLayout(new GridLayout());
        setResizableV(tabItemDocumentationComposite);
        tabItemDocumentation.setControl(tabItemDocumentationComposite);

        tabItemSourceViewer = JOE_TI_ScriptJobMainForm_XML.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemSourceViewerComposite = new Composite(tabFolder, SWT.NONE);
        tabItemSourceViewerComposite.setLayout(new GridLayout());
        setResizableV(tabItemSourceViewerComposite);
        tabItemSourceViewer.setControl(tabItemSourceViewerComposite);
        
        createSetbackTab(tabItemOrderSetBackComposite);
        createDelayAfterErrorTab(tabItemDelayAfterErrorComposite);
        createDirChangedTab(tabItemDirChangedComposite);

        createEmailSettingsTab(tabItemEMailComposite);
        createProcessFileTab(tabItemProcessFileComposite);
        createDocumentationTab(tabItemDocumentationComposite);
        createOptionsTab(tabItemOptionsComposite);
        createSourceViewerTab(tabItemSourceViewerComposite);
        
    }

   
    private void createSourceViewerTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobSourceViewer(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }
     

    private void createDocumentationTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }

        new JobDocumentation(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }

    private void createProcessFileTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobProcessFile(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }

    private void createOptionsTab(final Composite pParentComposite) {
        objJobOptions = new JobOptions(pParentComposite, objDataProvider);
        objJobOptions.getbOrderYes().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (objJobOptions.getbOrderYes().getSelection() == false){
                    return;
                }
                disposeJobMainTabPages();
                createJobMainTabPages();
                tabFolder.setSelection(tabItemOptions);
            }

        });
        objJobOptions.getbOrderNo().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (objJobOptions.getbOrderNo().getSelection() == false){
                    return;
                }
                disposeJobMainTabPages();
                createJobMainTabPages();
                tabFolder.setSelection(tabItemOptions);
            }

        });        

        pParentComposite.layout();
    }

    private void createSetbackTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobSetback(pParentComposite, objDataOptionsProvider);
        pParentComposite.layout();
    }

    private void createDelayAfterErrorTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobDelayAfterError(pParentComposite, objDataOptionsProvider);
        pParentComposite.layout();
    }

    private void createEmailSettingsTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobEmailSettings(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }

    private void createDirChangedTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        new JobStartWhenDirectoryChanged(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }

    

} // @jve:decl-index=0:visual-constraint="10,10"
