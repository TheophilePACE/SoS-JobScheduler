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

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;


import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import sos.scheduler.editor.app.ContextMenu;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
 
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.ErrorLog;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;

import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;
import sos.scheduler.editor.conf.composites.PreProcessingComposite;
 
public class ScriptFormPreProcessing extends ScriptForm implements IUpdateLanguage {

    @SuppressWarnings("unused")
    private final String conSVNVersion = "$Id$";

    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(ScriptJobMainForm.class);
    @SuppressWarnings("unused")
    private final String conClassName = "PreProcessingForm";
    private PreProcessingComposite preProcessingHeader=null;
    private HashMap <String,String> favorites = null;

    public ScriptFormPreProcessing(Composite parent, int style, SchedulerDom dom, Element job, ISchedulerUpdate main) {
        super(parent, style, dom, job, main);
        objDataProvider._languages = objDataProvider._languagesMonitor;
        initialize();
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

        preProcessingHeader = new PreProcessingComposite(objMainOptionsGroup, SWT.NONE,objDataProvider);
        preProcessingHeader.setLayout(gridLayout);
        preProcessingHeader.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        
        createLanguageSelector(preProcessingHeader.getgMain());
        if (objDataProvider.getLanguage() < 0){ 
          objDataProvider.setLanguage(0);
          languageSelector.selectLanguageItem(0);
        }
        createScriptTabForm(objMainOptionsGroup);

        getFavoriteNames();

        preProcessingHeader.getButFavorite().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                Options.setProperty("monitor_favorite_" + objDataProvider.getLanguage(objDataProvider.getLanguage()) + "_" + preProcessingHeader.getTxtName().getText(), getFavoriteValue());
                Options.saveProperties();
                preProcessingHeader.getCboFavorite().setItems(normalized(Options.getPropertiesWithPrefix("monitor_favorite_")));
            }
        });
        
        preProcessingHeader.getCboFavorite().addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(final SelectionEvent e) {
            getDataFromFavorite();
          }
    });
    }
    
    protected String getPredefinedFunctionNames(){
        return "spooler_task_before;spooler_task_after;spooler_process_before;spooler_process_after";
    }
    
    protected String[] getScriptLanguages(){
        return objDataProvider._languagesMonitor;
    }
    
    private void getFavoriteNames(){
        preProcessingHeader.getCboFavorite().setData("favorites", favorites);
        preProcessingHeader.getCboFavorite().setMenu(new ContextMenu(preProcessingHeader.getCboFavorite(), objDataProvider.getDom(), Editor.SCRIPT).getMenu());
     }

    
    private String getFavoriteValue() {
        if (objDataProvider.isJava()) {
            return this.getObjJobJAPI().getTbxClassName().getText();
        }
        else {
            return objDataProvider.getIncludesAsString();
        }
    }

    
    private String getPrefix() {
        if (favorites != null && preProcessingHeader.getCboFavorite().getText().length() > 0 && favorites.get(preProcessingHeader.getCboFavorite().getText()) != null)
            return "monitor_favorite_" + favorites.get(preProcessingHeader.getCboFavorite().getText()) + "_";
        if (objDataProvider.getLanguage() == 0)
            return "";
        return "monitor_favorite_" + objDataProvider.getLanguage(objDataProvider.getLanguage()) + "_";
    }

    private String[] normalized(String[] str) {
        String[] retVal = new String[] { "" };
        try {
            favorites = new HashMap<String,String>();
            if (str == null)
                return new String[0];

            String newstr = "";
            retVal = new String[str.length];
            for (int i = 0; i < str.length; i++) {
                String s = str[i];
                int idx = s.indexOf("_");
                if (idx > -1) {
                    String lan = s.substring(0, idx);
                    String name = s.substring(idx + 1);
                    if (name == null || lan == null)
                        System.out.println(name);
                    else
                        favorites.put(name, lan);
                    newstr = name + ";" + newstr;
                }
            }
            retVal = newstr.split(";");
            return retVal;
        }
        catch (Exception e) {
            System.out.println(e.toString());
            try {
                new ErrorLog(JOE_E_0002.params(sos.util.SOSClassUtil.getMethodName()), e);
            }
            catch (Exception ee) {
              
            }
            return retVal;
        }
    }

     
    private void getDataFromFavorite(){
        
        if (preProcessingHeader.getCboFavorite().getText().length() > 0) {
            if (Options.getProperty(getPrefix() + preProcessingHeader.getCboFavorite().getText()) != null) {
               
                if (this.getObjJobJAPI() != null && this.getObjJobJAPI().getTbxClassName().getText().length() > 0 ||  this.getObjJobIncludeFile() != null  && this.getObjJobIncludeFile().getTableIncludes().isEnabled() && this.getObjJobIncludeFile().getTableIncludes().getItemCount() > 0) {
                    int c = MainWindow.message(getShell(), JOE_M_ScriptFormPreProcessing_OverwriteMonitor.label(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
                    if (c != SWT.YES)
                        return;
                    else {
                        if (this.getObjJobJAPI() != null){
                            this.getObjJobJAPI().getTbxClassName().setText("");
                        }
                        if (this.getObjJobIncludeFile() != null){
                           this.getObjJobIncludeFile().getTableIncludes().clearAll();
                        }
                        objDataProvider.removeIncludes();
                    }
                }
              
                if (favorites != null && favorites.get(preProcessingHeader.getCboFavorite().getText()) != null && favorites.get(preProcessingHeader.getCboFavorite().getText()).toString().length() > 0) {
                  
                    preProcessingHeader.getTxtName().setText(preProcessingHeader.getCboFavorite().getText());
                    objDataProvider.setMonitorName(preProcessingHeader.getCboFavorite().getText());
                    objDataProvider.setLanguage(objDataProvider.languageAsInt(favorites.get(preProcessingHeader.getCboFavorite().getText()).toString()));
                    languageSelector.setText(objDataProvider.getLanguageAsString(objDataProvider.getLanguage()));
                    
                    if (objDataProvider.isJava()) {
                        this.getObjJobJAPI().getTbxClassName().setText(Options.getProperty(getPrefix() + preProcessingHeader.getCboFavorite().getText()));
                    }
                    else {
                        tabFolder.setSelection(this.getTabItemIncludedFiles());
                        String[] split = Options.getProperty(getPrefix() + preProcessingHeader.getCboFavorite().getText()).split(";");
                        for (int i = 0; i < split.length; i++) {
                            objDataProvider.addInclude(split[i]);
                        }
                    }

                    fillForm();
                }
            }
        }

    }


    @Override protected void initForm() {

        preProcessingHeader.init();
        if (normalized(Options.getPropertiesWithPrefix("monitor_favorite_")) != null
                && normalized(Options.getPropertiesWithPrefix("monitor_favorite_"))[0] != null) {
            preProcessingHeader.getCboFavorite().setItems(normalized(Options.getPropertiesWithPrefix("monitor_favorite_")));
        }
        
    }
}  
