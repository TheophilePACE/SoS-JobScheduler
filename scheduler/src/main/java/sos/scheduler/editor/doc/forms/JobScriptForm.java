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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.jdom.Element;

import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.JobScriptListener;

public class JobScriptForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {
    private JobScriptListener listener   = null;

    private Button            cUseScript = null;

    private ScriptForm        scriptForm = null;

    private DocumentationDom  dom;

    private Element           job;


    public JobScriptForm(Composite parent, int style, DocumentationDom dom, Element job) {
        super(parent, style);
        initialize();
        setToolTipText();
        this.dom = dom;
        this.job = job;
        listener = new JobScriptListener(dom, job);
        cUseScript.setSelection(listener.isScript());
        if (listener.isScript())
            scriptForm.setParams(dom, job, Editor.DOC_SCRIPT);
        scriptForm.init(listener.isScript(), true);
    }


    private void initialize() {
        cUseScript = JOE_B_JobScriptForm_UseScript.Control(new Button(this, SWT.RADIO));
        cUseScript.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                if (cUseScript.getSelection() != listener.isScript()) {
                    if (cUseScript.getSelection())
                        listener.setScript();
                    scriptForm.setParams(dom, job, Editor.DOC_SCRIPT);
                    scriptForm.init(listener.isScript(), true);
                }
            }
        });
        createScriptForm();
        setSize(new Point(694, 407));
        setLayout(new GridLayout());
    }


    /**
     * This method initializes scriptForm
     */
    private void createScriptForm() {
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL; // Generated
        gridData.grabExcessHorizontalSpace = true; // Generated
        gridData.grabExcessVerticalSpace = true; // Generated
        gridData.verticalAlignment = GridData.FILL; // Generated
        scriptForm = new ScriptForm(this, SWT.NONE);
        scriptForm.setLayoutData(gridData); // Generated
    }


    public void setToolTipText() {
//        cUseScript.setToolTipText(Messages.getTooltip("doc.script.useScript"));
    }


    public void apply() {
        scriptForm.apply();
    }


    public boolean isUnsaved() {
        return scriptForm.isUnsaved();
    }

} // @jve:decl-index=0:visual-constraint="10,10"
