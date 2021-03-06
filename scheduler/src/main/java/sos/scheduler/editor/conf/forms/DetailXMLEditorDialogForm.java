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
/*
 * Created on 06.03.2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.editor.conf.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.ErrorLog;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.ResourceManager;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.conf.listeners.DetailXMLEditorListener;
import sos.scheduler.editor.conf.listeners.JobChainConfigurationListener;
import sos.scheduler.editor.conf.DetailDom;

public class DetailXMLEditorDialogForm {
	
	private String                  xmlFilename     = null;
	
	private Text                    txtXML          = null; 
	
	private DetailXMLEditorListener listener        = null;
	
	private Button                  butApply        = null;
	
	private Shell                   shell           = null; 
	
	private String                  state           = null;
	 
	private String                  jobChainname    = null;
	
	//private String[]                listOfOrderIds  = null;
	
	private String                  orderId         = null;    
	
	private DetailDom               dom             = null;
		
	/** wer hat ihn aufgerufen*/
	private int                     type            = -1;
	
	/** falls type = Editor.Details*/
	private Composite        parent                 = null;
	
	/** falls type = Editor.Details*/
	private JobChainConfigurationListener confListener = null;
	
	/** falls type = Editor.Details*/
	private Tree             tree                   = null;
	
	private boolean                  isLifeElement   = false;
	
	private String                   path            = null;
	
	/*public DetailXMLEditorDialogForm(String xmlFilename_, 
	 
			                         String jobChainname_, 
			                         String state_, 
			                         String[] listOfOrderIds_, 
			                         String orderId_, 
			                         int type_, 
			                         boolean isLifeElement_,
			                         String path_) {
		*/
	public DetailXMLEditorDialogForm(String xmlFilename_, 
            String jobChainname_, 
            String state_,            
            String orderId_, 
            int type_, 
            boolean isLifeElement_,
            String path_) {

		jobChainname = jobChainname_;
		state = state_;
		xmlFilename = xmlFilename_;
		//listOfOrderIds = listOfOrderIds_;
		orderId = orderId_;
		type = type_;
		isLifeElement = isLifeElement_;
		path = path_;
	}
	
	public DetailXMLEditorDialogForm(DetailDom dom_, int type_, boolean isLifeElement_, String path_) {
		dom = dom_;
		xmlFilename = dom.getFilename();
		type = type_;
		isLifeElement = isLifeElement_;
		path = path_;
	}
	
	public void showXMLEditor() {
		
		shell = new Shell(MainWindow.getSShell(), SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.BORDER);
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(final ShellEvent e) {
				close();
				e.doit = shell.isDisposed();
			}
		});
		
		shell.setImage(ResourceManager.getImageFromResource("/sos/scheduler/editor/editor.png"));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		gridLayout.marginLeft = 5;
		gridLayout.marginBottom = 5;
		shell.setLayout(gridLayout);
		shell.setSize(693, 743);		
		
		shell.setText(SOSJOEMessageCodes.JOE_M_0009.params(xmlFilename));
		
		java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();		
		shell.setBounds((screen.width - shell.getBounds().width) /2, 
				(screen.height - shell.getBounds().height) /2, 
				shell.getBounds().width, 
				shell.getBounds().height);
		
		
		
		{
			final Group jobGroup = SOSJOEMessageCodes.JOE_G_DetailXMLEditorDialogForm_JobGroup.Control(new Group(shell, SWT.NONE));
			final GridLayout gridLayout_1 = new GridLayout();
			gridLayout_1.numColumns = 2;
			jobGroup.setLayout(gridLayout_1);
//			jobGroup.setText("XML");
			final GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
			gridData.minimumWidth = 10;
			gridData.minimumHeight = 10;
			gridData.widthHint = 663;
			gridData.heightHint = 685;
			jobGroup.setLayoutData(gridData);
			
			
			txtXML = SOSJOEMessageCodes.JOE_T_DetailXMLEditorDialogForm_XML.Control(new Text(jobGroup, SWT.V_SCROLL | SWT.MULTI | SWT.WRAP | SWT.H_SCROLL));
			txtXML.addModifyListener(new ModifyListener() {
				public void modifyText(final ModifyEvent e) {
					butApply.setEnabled(true);
				}
			});
			final GridData gridData_2 = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1, 2);
			gridData_2.heightHint = 672;
			gridData_2.widthHint = 553;
			txtXML.setLayoutData(gridData_2);
			txtXML.setEnabled(true);
			txtXML.setEditable(true);

			butApply = SOSJOEMessageCodes.JOE_B_DetailXMLEditorDialogForm_Apply.Control(new Button(jobGroup, SWT.NONE));
			butApply.setEnabled(false);
			butApply.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
						listener.saveXML(txtXML.getText());
						if(type == Editor.DETAILS) {
							confListener.treeFillMain(tree, parent);
							shell.setFocus();
						}
						butApply.setEnabled(false);
						shell.close();
				}
			});
			final GridData gridData_1 = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
			gridData_1.widthHint = 62;
			butApply.setLayoutData(gridData_1);
//			butApply.setText("Apply");

			final Button butClose = SOSJOEMessageCodes.JOE_B_DetailXMLEditorDialogForm_Close.Control(new Button(jobGroup, SWT.NONE));
			butClose.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					close();					
				}
			});
			butClose.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
//			butClose.setText("Cancel");
			
		}
		if(type == Editor.JOB_CHAINS) {
			listener = new DetailXMLEditorListener(xmlFilename);
		} else {
			listener = new DetailXMLEditorListener(dom);
		}
		try {
        txtXML.setText(listener.readCommands());
		} catch (Exception e) {
			try {
				System.err.println(SOSJOEMessageCodes.JOE_E_0002.params("showXMLEditor", e.toString()));						
//    			new ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , e);
    			new ErrorLog(SOSJOEMessageCodes.JOE_E_0002.params("showXMLEditor", e.toString()));
    		} catch(Exception ee) {
    			//tu nichts
    		}
			
		}
        
		setToolTipText();
		shell.open();
		shell.layout();		
		butApply.setEnabled(false);
	}
	
	public void setToolTipText() {
//		butApply.setToolTipText(Messages.getTooltip("detail.xml_Editor.apply"));
//		txtXML.setToolTipText(Messages.getTooltip("detail.xml_Editor.xml"));
	}

	private boolean closeDialog() {
		int cont = -1;
		boolean retVal = false;
		if(butApply.isEnabled()) {
//			cont = MainWindow.message(shell, sos.scheduler.editor.app.Messages.getString("detailform.close"), SWT.ICON_WARNING | SWT.OK |SWT.CANCEL );
			cont = MainWindow.message(shell, SOSJOEMessageCodes.JOE_M_0008.label(), SWT.ICON_WARNING | SWT.OK |SWT.CANCEL );
			if(cont == SWT.OK) {						
				shell.dispose();
				retVal = true;
			}
			
		} else{
			retVal = true;
		}
		return retVal;
	}
	
	private void openDetailForm() {
		  		
			DetailDialogForm detail = new DetailDialogForm(jobChainname, state, orderId, isLifeElement, path);
			detail.showDetails();
			detail.getDialogForm().open(orderId);
		 
	}
	
	private void close() {
		if (closeDialog() ) {
			if(type == Editor.JOB_CHAINS)
				openDetailForm();
			else if(type == Editor.DETAILS) {
				confListener.treeSelection(tree, parent);
				dom.setChanged(true);
			}
			shell.dispose();
			
		}
		
	}
	
	public void setConfigurationData(JobChainConfigurationListener confListener_, Tree tree_, Composite parent_) {
		confListener = confListener_;
		tree = tree_;
		parent = parent_;
		
	}
	
}


