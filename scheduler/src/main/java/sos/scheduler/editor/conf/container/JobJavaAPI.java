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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.VerifyEvent;
//import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobJavaAPI extends FormBaseClass {

	@SuppressWarnings("unused")
	private final String	conClassName			= "JobJavaAPI";
	@SuppressWarnings("unused")
	private final String	conSVNVersion			= "$Id: JobJavaAPI.java 18346 2012-11-02 14:07:36Z ur $";

	private boolean init = true;
	private JobListener objJobDataProvider = null;
	private Text tClasspath = null;
	private Text txtJavaOptions = null;
	private Text tbxClassName = null; 

	public JobJavaAPI(Composite pParentComposite, JobListener pobjJobDataProvider,JobJavaAPI that) {
		super(pParentComposite, pobjJobDataProvider);
		objJobDataProvider = pobjJobDataProvider;
		init = true;
		createGroup();
		init = false;
		getValues(that);
	}

	public void apply() {
		// if (isUnsaved())
		// addParam();
	}

	public boolean isUnsaved() {
		return false;
	}

	public void refreshContent () {
	}
	
	private void getValues(JobJavaAPI that){
	    if (that == null){
	        return;
	    }
	    
        this.tClasspath.setText(that.tClasspath.getText());
        this.txtJavaOptions.setText(that.txtJavaOptions.getText());
        this.tbxClassName.setText(that.tbxClassName.getText());
 
	}
	
	private void createGroup() {
		showWaitCursor();

		Group gScript_2 = new Group(objParent, SWT.NONE);
		GridLayout lgridLayout = new GridLayout();
		lgridLayout.numColumns = 13;
		gScript_2.setLayout(lgridLayout);
		setResizableV(gScript_2);

		Label lblClassNameLabel = SOSJOEMessageCodes.JOE_L_JobJavaAPI_Classname.Control(new Label(gScript_2, SWT.NONE));
		GridData labelGridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1);
		lblClassNameLabel.setLayoutData(labelGridData);

		tbxClassName = SOSJOEMessageCodes.JOE_T_JobJavaAPI_Classname.Control(new Text(gScript_2, SWT.BORDER));
		tbxClassName.setEnabled(true);
		tbxClassName.setText(objJobDataProvider.getJavaClass());
//		tbxClassName.addVerifyListener(new VerifyListener() {
//			public void verifyText(final VerifyEvent e) {
				/*if (e.text.length() > 0 && objJobDataProvider.isJava() && objJobDataProvider.getSource().length() > 0) {
					MsgWarning("Please remove Script-Code first.");
					e.doit = false;
					return;
				}*/
//			}
//		});
		GridData gd_tClass = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd_tClass.horizontalSpan = 8;
		tbxClassName.setLayoutData(gd_tClass);
		tbxClassName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!init) {
					if (objJobDataProvider.isJava()) {
						objJobDataProvider.setJavaClass(tbxClassName.getText());
					}
				}
			}
		});

		Group gScript_1 = gScript_2;
		new Label(gScript_1, SWT.NONE);
		new Label(gScript_1, SWT.NONE);
		new Label(gScript_1, SWT.NONE);
		new Label(gScript_1, SWT.NONE);

		Label lblNewLabel_1 = SOSJOEMessageCodes.JOE_L_JobJavaAPI_Classpath.Control(new Label(gScript_2, SWT.NONE));
		lblNewLabel_1.setLayoutData(labelGridData);

  
		
		tClasspath = SOSJOEMessageCodes.JOE_T_JobJavaAPI_Classpath.Control(new Text(gScript_2, SWT.BORDER));
		tClasspath.setEnabled(true);
		tClasspath.setText(objJobDataProvider.getClasspath());
        GridData gd_tClasspath = new GridData(GridData.FILL, GridData.CENTER, true, false);
        gd_tClasspath.horizontalSpan = 8;
        tClasspath.setLayoutData(gd_tClasspath);
        tClasspath.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (!init) {
                    if (objJobDataProvider.isJava()) {
                        objJobDataProvider.setClasspath(tClasspath.getText());
                    }
                }
            }
        });
		 
		new Label(gScript_2, SWT.NONE);
		new Label(gScript_2, SWT.NONE);
		new Label(gScript_2, SWT.NONE);
		new Label(gScript_2, SWT.NONE);

		final Label java_optionsLabel = SOSJOEMessageCodes.JOE_L_JobJavaAPI_Options.Control(new Label(gScript_2, SWT.NONE));
		java_optionsLabel.setLayoutData(labelGridData);

		txtJavaOptions = SOSJOEMessageCodes.JOE_T_JobJavaAPI_Options.Control(new Text(gScript_2, SWT.BORDER));
		txtJavaOptions.setText(objJobDataProvider.getJavaOptions());
		txtJavaOptions.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				if (!init) {
				   objJobDataProvider.setJavaOptions(txtJavaOptions.getText());
				}
			}
		});
		txtJavaOptions.setLayoutData(gd_tClass);

		gScript_1.setVisible(true);
		gScript_1.redraw();
		restoreCursor();
	}

    public Text getTClasspath() {
        return tClasspath;
    }

    public Text getTbxClassName() {
        return tbxClassName;
    }
}
