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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.classes.FormBaseClass;
import sos.scheduler.editor.classes.TextArea;
import sos.scheduler.editor.classes.TextArea.enuSourceTypes;
import sos.scheduler.editor.conf.listeners.JobListener;

public class JobScript extends FormBaseClass {

    @SuppressWarnings("unused")
    private final String conClassName       = "JobScript";
    @SuppressWarnings("unused")
    private final String conSVNVersion      = "$Id$";
    private Group        group              = null;
    private Combo        cboPrefunction     = null;
    @SuppressWarnings("unused")
	private boolean      init               = true;
    private JobListener  objJobDataProvider = null;
    private StyledText   tSource            = null;

    public JobScript(Composite pParentComposite, JobListener pobjJobDataProvider) {
        super(pParentComposite, pobjJobDataProvider);
        objJobDataProvider = pobjJobDataProvider;
        init = true;
        createGroup();
        init = false;
    }

    private void createGroup() {
        group = SOSJOEMessageCodes.JOE_G_JobScript_Executable.Control(new Group(objParent, SWT.NONE));
        final GridData gridData_5 = new GridData(GridData.FILL, GridData.FILL, true, true, 13, 20);
        gridData_5.heightHint = 500;
        gridData_5.minimumHeight = 100;
        group.setLayoutData(gridData_5);
        final GridLayout gridLayout_2 = new GridLayout();
        gridLayout_2.marginHeight = 0;
        gridLayout_2.numColumns = 4;
        group.setLayout(gridLayout_2);

        @SuppressWarnings("unused")
		Label lblPrefunction1 = SOSJOEMessageCodes.JOE_L_JobScript_PredefinedFunctions.Control(new Label(group, SWT.NONE));
        
        cboPrefunction = SOSJOEMessageCodes.JOE_Cbo_JobScript_PredefinedFunctions.Control(new Combo(group, intComboBoxStyle));
        cboPrefunction.setVisibleItemCount(7);
        cboPrefunction.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(final SelectionEvent e) {
                if (cboPrefunction.getText().length() > 0) {
                    String lan = "function_" + objJobDataProvider.getLanguage(objJobDataProvider.getLanguage()) + "_";
                    String sourceTemplate = Options.getProperty(lan.toLowerCase() + cboPrefunction.getText());
                    if (sourceTemplate != null) {
                        tSource.append(Options.getProperty(lan.toLowerCase() + cboPrefunction.getText()));
                        cboPrefunction.setText("");
                        tSource.setFocus();
                    }
                }
            }
        });
        cboPrefunction.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
        
        final TextArea txtPrePostProcessingScriptCode = new TextArea(group, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL);
        txtPrePostProcessingScriptCode.setDataProvider(objJobDataProvider, enuSourceTypes.ScriptSource);
        tSource = txtPrePostProcessingScriptCode.getControl();

        init = false;

        objParent.layout();
    }

    public StyledText gettSource() {
        return tSource;
    }

    public Combo getCboPrefunction() {
        return cboPrefunction;
    }

}
