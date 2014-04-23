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
import org.eclipse.swt.custom.CTabFolder;

import org.eclipse.swt.custom.CTabItem;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
 
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
 
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.classes.LanguageSelector;

import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.container.JobIncludeFile;
import sos.scheduler.editor.conf.container.JobJavaAPI;
import sos.scheduler.editor.conf.container.JobScript;
import sos.scheduler.editor.conf.listeners.JobListener;

public abstract class ScriptForm extends SOSJOEMessageCodes implements IUpdateLanguage {

    @SuppressWarnings("unused")
    private final String conSVNVersion = "$Id: ScriptForm.java 18346 2012-11-02 14:07:36Z ur $";
    private int intNoOfLabelColumns = 2;

    private static Logger logger = Logger.getLogger(ScriptJobMainForm.class);
    @SuppressWarnings("unused")
    private final String conClassName = "ScriptForm";

    protected JobListener objDataProvider = null;
 
    private Cursor  objLastCursor   = null;
    private boolean init = true;
 
    protected ISchedulerUpdate update;
    protected Element job;
  
    protected LanguageSelector languageSelector = null;

    private Composite tabItemJavaAPIComposite = null;
    private Composite tabItemIncludedFilesComposite = null;
    private Composite objTabControlComposite = null;
    private Composite tabItemScriptComposite = null;

  
    private CTabItem tabItemScript = null;
    private CTabItem tabItemJavaAPI = null;
    private CTabItem tabItemIncludedFiles = null;
    protected CTabFolder tabFolder = null;

    protected JobScript objJobScript = null;
    private JobJavaAPI objJobJAPI = null;
    private JobIncludeFile objJobIncludeFile = null;
    private SchedulerDom dom;
    
    protected Group objMainOptionsGroup = null;

    protected abstract void initForm();
    protected abstract  void createGroup();
    protected abstract String getPredefinedFunctionNames();
    protected abstract String[] getScriptLanguages();
    
    public ScriptForm(Composite parent, int style, SchedulerDom dom_, Element job_, ISchedulerUpdate main) {
        super(parent, style);
        job=job_;
        dom=dom_;
        // final ToolTipHandler tooltip = new ToolTipHandler(parent.getShell());

        update = main;
        dom.setInit(true);
        objDataProvider = new JobListener(dom, job, main);
        objDataProvider._languages = objDataProvider._languagesJob;
        dom.setInit(false);
    }

    public void apply() {
        
    }

    
    public boolean isUnsaved() {
         return false;
    }
    
    protected void showWaitCursor() {
        if (!getShell().isDisposed()) {
            objLastCursor = getShell().getCursor();
        }
        getShell().setCursor(new Cursor(getShell().getDisplay(), SWT.CURSOR_WAIT));
    }

    protected void restoreCursor() {
        if (!getShell().isDisposed())
            if (objLastCursor == null) {
                getShell().setCursor(new Cursor(getShell().getDisplay(), SWT.CURSOR_ARROW));
            }
            else {
                getShell().setCursor(objLastCursor);
            }
    }


    protected void initialize() {
        dom.setInit(true);

        init = true;
        this.setLayout(new GridLayout());

        

        createGroup();
        fillForm();
        setSize(new org.eclipse.swt.graphics.Point(723, 566));
        initForm();

        init = false;
        dom.setInit(true);

    }
 
    protected void setResizableV(Control objControl) {
        boolean flgGrapVerticalspace = true;
        objControl.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, flgGrapVerticalspace));
    }

    protected void disposeTabPages() {
       
        if (tabItemScript != null) {
            tabItemScript.dispose();
        }
        if (tabItemJavaAPI != null) {
            tabItemJavaAPI.dispose();
        }
        if (tabItemIncludedFiles != null) {
            tabItemIncludedFiles.dispose();
        }
    }

    protected void createScriptTabForm(Group pobjMainOptionsGroup) {

        tabFolder = new CTabFolder(pobjMainOptionsGroup, SWT.None); // SWT.Bottom
        tabFolder.setLayout(new GridLayout());
        setResizableV(tabFolder);
        tabFolder.setSimple(true);
        tabFolder.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                int intIndex = tabFolder.getSelectionIndex();
                Options.setLastTabItemIndex(intIndex);
//                logger.debug("Selected item index = " + tabFolder.getSelectionIndex());
                logger.debug(JOE_M_ScriptForm_ItemIndex.params(tabFolder.getSelectionIndex()));
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });

        createTabPages();
        tabFolder.setSelection(0);

    }

    private void createTabPages() {
       tabItemJavaAPIComposite = null;
       tabItemIncludedFilesComposite = null;
       tabItemScriptComposite = null;

        objTabControlComposite = new Composite(tabFolder, SWT.NONE);
        objTabControlComposite.setLayout(new GridLayout());
        setResizableV(objTabControlComposite);

        tabItemScript = JOE_ScriptForm_TabItemScript.Control(new CTabItem(tabFolder, SWT.NONE));

        tabItemScriptComposite = new Composite(tabFolder, SWT.NONE);
        tabItemScriptComposite.setLayout(new GridLayout());
        setResizableV(tabItemScriptComposite);
        tabItemScript.setControl(tabItemScriptComposite);


        if (objDataProvider.isJava()) {
            tabItemJavaAPI = JOE_ScriptForm_TabItemJavaAPI.Control(new CTabItem(tabFolder, SWT.NONE));

            tabItemJavaAPIComposite = new Composite(tabFolder, SWT.NONE);
            tabItemJavaAPIComposite.setLayout(new GridLayout());
            setResizableV(tabItemJavaAPIComposite);
            tabItemJavaAPI.setControl(tabItemJavaAPIComposite);
        }

        tabItemIncludedFiles = JOE_ScriptForm_TabItemIncludes.Control(new CTabItem(tabFolder, SWT.NONE));
        tabItemIncludedFilesComposite = new Composite(tabFolder, SWT.NONE);
        tabItemIncludedFilesComposite.setLayout(new GridLayout());
        setResizableV(tabItemIncludedFilesComposite);
        tabItemIncludedFiles.setControl(tabItemIncludedFilesComposite);

      

        createJavaAPITab(tabItemJavaAPIComposite);
        createTabItemIncludeFile(tabItemIncludedFilesComposite);
        createScriptTab(tabItemScriptComposite);
    }

   

    private void createScriptTab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        objJobScript = new JobScript(pParentComposite, objDataProvider);
        pParentComposite.layout();
    }


    private void createJavaAPITab(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        objJobJAPI = new JobJavaAPI(pParentComposite, objDataProvider, objJobJAPI);
        pParentComposite.layout();
    }


    private void createTabItemIncludeFile(final Composite pParentComposite) {
        if (pParentComposite == null) {
            return;
        }
        objJobIncludeFile = new JobIncludeFile(pParentComposite, objDataProvider, objJobIncludeFile);
        pParentComposite.layout();

    }


    protected void createLanguageSelector(Composite pobjComposite) {
        Label labelLanguageSelector = JOE_L_ScriptForm_Language.Control(new Label(pobjComposite, SWT.NONE));
        labelLanguageSelector.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, intNoOfLabelColumns, 1));

        languageSelector = new LanguageSelector(pobjComposite, SWT.NONE);
        languageSelector.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent arg0) {
                if (objDataProvider != null && init == false) {
                    String strT = languageSelector.getText();
                    objDataProvider.setLanguage(strT);
                    disposeTabPages();
                    createTabPages();
                    
                    
                    objJobScript.getCboPrefunction().setEnabled(false);
                    if (languageSelector.isJava() && objJobJAPI.getTbxClassName()  != null) {
                        objJobJAPI.getTbxClassName().setFocus();
                        objJobJAPI.getTClasspath().setText(objDataProvider.getClasspath());

                        if (!objJobJAPI.getTbxClassName().getText().equals("") && objDataProvider.getJavaClass().equals(""))
                            objDataProvider.setJavaClass(objJobJAPI.getTbxClassName().getText());
                        objJobJAPI.getTbxClassName().setText(objDataProvider.getJavaClass());
                        tabFolder.setSelection(tabItemJavaAPI);
                    } else {
                        String lan = "";
                        if (languageSelector.isScriptLanguage()) {
                           
                            lan = getPredefinedFunctionNames();
                           
                            objJobScript.getCboPrefunction().setItems(lan.split(";"));
                            objJobScript.getCboPrefunction().setEnabled(true);
                        }

                        if (languageSelector.isJava()){
                            tabFolder.setSelection(tabItemJavaAPI);
                        }else{
                            tabFolder.setSelection(tabItemScript);
                            objJobScript.gettSource().setFocus();
                        }
                    }
                }
            }
        });

        GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_combo.minimumWidth = 100;
        languageSelector.setLayoutData(gd_combo);
        languageSelector.select(0);
        languageSelector.setItems(getScriptLanguages());
      }

    protected void fillForm() {
        init = true;
        int language = objDataProvider.getLanguage();
        if (objJobScript.getCboPrefunction() != null) {
            objJobScript.getCboPrefunction().removeAll();
        }
       
        languageSelector.selectLanguageItem(language);

        if (language != JobListener.NONE) {
            objDataProvider.fillTable(objJobIncludeFile.getTableIncludes());
        }else{
            languageSelector.selectLanguageItem(0);
            objDataProvider.setLanguage(0);
        }

        String lan = "";
        if (!languageSelector.isShell() && !languageSelector.isJava()) {
             lan = this.getPredefinedFunctionNames();
             objJobScript.getCboPrefunction().setItems(lan.split(";"));
        }
        
       
        if (languageSelector.isJava()){
             tabFolder.setSelection(tabItemJavaAPI);
        }else{
            tabFolder.setSelection(tabItemScript);
            objJobScript.gettSource().setFocus();
        }

        init = false;
    }
 

    @Override
    public void setToolTipText() {
// 
    }
    public JobJavaAPI getObjJobJAPI() {
        return objJobJAPI;
    }
    public JobIncludeFile getObjJobIncludeFile() {
        return objJobIncludeFile;
    }
   
    public CTabItem getTabItemIncludedFiles() {
        return tabItemIncludedFiles;
    }

}  
