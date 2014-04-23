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
import org.jdom.Element;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.PayloadListener;

public class PayloadForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {
    private PayloadListener  listener      = null;

    private DocumentationDom dom           = null;

    private Element          parentElement = null;

    private Group            group         = null;

    private ParamsForm       fParams       = null;

    private Button           bNotes        = null;

    private Button           bDocNotes     = null;


    public PayloadForm(Composite parent, int style, DocumentationDom dom, Element parentElement) {
        super(parent, style);

        this.dom = dom;
        this.parentElement = parentElement;
        initialize();
    }


    private void initialize() {
        createGroup();
        setSize(new Point(651, 431));
        setLayout(new FillLayout());

        listener = new PayloadListener(dom, parentElement, fParams);
        fParams.setParams(dom, listener.getPayloadElement());
        setToolTipText();
    }


    /**
     * This method initializes group
     */
    private void createGroup() {
        GridLayout gridLayout = new GridLayout(2, false);
        
        group = JOE_G_PayloadForm_Payload.Control(new Group(this, SWT.NONE));
        group.setLayout(gridLayout);
        
        bNotes = JOE_B_PayloadForm_PayloadNote.Control(new Button(group, SWT.NONE));
        bNotes.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//                String tip = Messages.getTooltip("doc.note.text.payload");
            	String tip = "";
//                DocumentationForm.openNoteDialog(dom, listener.getPayloadElement(), "note", tip, true,"Payload Note");
            	DocumentationForm.openNoteDialog(dom, listener.getPayloadElement(), "note", tip, true, JOE_B_PayloadForm_PayloadNote.label());
            }
        });
        
        bDocNotes = JOE_B_PayloadForm_DocNote.Control(new Button(group, SWT.NONE));
        bDocNotes.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//                String tip = Messages.getTooltip("doc.note.text.payload.document");
            	String tip = "";
//                DocumentationForm.openNoteDialog(dom, listener.getDocumentationElement(), "note", tip, true,"Payload Document Note");
                DocumentationForm.openNoteDialog(dom, listener.getDocumentationElement(), "note", tip, true, JOE_B_PayloadForm_DocNote.label());
            }
        });
        createFParams();
    }


    /**
     * This method initializes fParams
     */
    private void createFParams() {
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
        
        fParams = new ParamsForm(group, SWT.NONE);
        fParams.setLayoutData(gridData); // Generated
    }


    public void setToolTipText() {
//    	
    }


    public void apply() {
        fParams.apply();
    }


    public boolean isUnsaved() {
        listener.checkPayload();
        return fParams.isUnsaved();
    }

} // @jve:decl-index=0:visual-constraint="10,10"
