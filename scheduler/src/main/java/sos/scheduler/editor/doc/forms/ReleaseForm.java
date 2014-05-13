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

import java.text.ParseException;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jdom.Element;

import com.sos.dialog.components.SOSDateTime;

import sos.scheduler.editor.app.IUnsaved;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.listeners.ReleaseListener;

public class ReleaseForm extends SOSJOEMessageCodes implements IUnsaved, IUpdateLanguage {

    private ReleaseListener listener = null;
    private DocumentationDom dom = null;
    private Group group = null;
    private Label label = null;
    private Label label1 = null;
    private Text tTitle = null;
    private Text tID = null;
    @SuppressWarnings("unused")
	private Label label2 = null;
    private Label label3 = null;
    private SOSDateTime created = null;
    private SOSDateTime modified = null;
    private NoteForm fNote = null;
    private NoteForm fChanges = null;

    // private Text txtNotes = null;

    // private Text txtChanges = null;

    public ReleaseForm(Composite parent, int style, DocumentationDom dom, Element release) {
        super(parent, style);

        this.dom = dom;
        listener = new ReleaseListener(dom, release);

        initialize();
        setToolTipText();

        // listener.fillReleases(tReleases);

    }

    private void initialize() {
        createGroup();
        setSize(new Point(801, 462));
        setLayout(new FillLayout());

        try {
            setReleaseStatus(false);
            created.setDate(listener.getCreated());
            modified.setDate(listener.getModified());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        fNote.setParams(dom, listener.getRelease(), "note", true, true);
        fNote.setTitle(JOE_B_DBResources_Notes.label());

        fChanges.setParams(dom, listener.getRelease(), "changes", true, true);
        fChanges.setTitle(JOE_M_ReleaseForm_Changes.label());

        tID.setText(listener.getID());
        tTitle.setText(listener.getTitle());

    }

    /**
     * This method initializes group
     */
    private void createGroup() {
        GridLayout gridLayout = new GridLayout(4, false);
        
        group = JOE_G_ReleaseForm_Releases.Control(new Group(this, SWT.NONE));
        group.setLayout(gridLayout); // Generated
        
        label1 = JOE_L_ReleaseForm_ID.Control(new Label(group, SWT.NONE));
        label1.setLayoutData(new GridData());
        
        GridData gridData3 = new GridData(GridData.FILL, GridData.CENTER, true, false);
        
        tID = JOE_T_ReleaseForm_ID.Control(new Text(group, SWT.BORDER));
        tID.setLayoutData(gridData3); // Generated
        tID.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                Utils.setBackground(tID, true);
                listener.setId(tID.getText());
            }
        });
        
        label2 = JOE_L_ReleaseForm_Created.Control(new Label(group, SWT.NONE));
        
        createCreated();
        
        label = JOE_L_ReleaseForm_Title.Control(new Label(group, SWT.NONE));
        label.setLayoutData(new GridData());
        
        GridData gridData21 = new GridData(GridData.FILL, GridData.CENTER, true, false);
        
        tTitle = JOE_T_ReleaseForm_Title.Control(new Text(group, SWT.BORDER));
        tTitle.setLayoutData(gridData21); // Generated
        tTitle.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                Utils.setBackground(tTitle, false);
                listener.setTitle(tTitle.getText());
            }
        });
        
        label3 = JOE_L_ReleaseForm_Modified.Control(new Label(group, SWT.NONE));
        label3.setLayoutData(new GridData());
        
        createModified();
        // Label filler = new Label(group, SWT.NONE);
        createComposite();
        createGroup1();
    }

    /**
     * This method initializes group1
     */
    private void createGroup1() {
        GridData gridData5 = new GridData(GridData.FILL, GridData.FILL, true, true, 4, 2);
        gridData5.widthHint = 486;
        
//        GridLayout gridLayout1 = new GridLayout(5, false);

        final Composite composite = new Composite(group, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 4, 1));
        final GridLayout gridLayout = new GridLayout();
        composite.setLayout(gridLayout);

        fNote = new NoteForm(composite, SWT.NONE);
        fNote.setTitle(JOE_B_DBResources_Notes.label());
        fNote.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

        /*
         * txtNotes = new Text(composite, SWT.BORDER);
         * txtNotes.setEditable(false); txtNotes.setLayoutData(new
         * GridData(GridData.FILL, GridData.FILL, true, true)); bNotes = new
         * Button(composite, SWT.NONE); bNotes.setLayoutData(new
         * GridData(GridData.FILL, GridData.BEGINNING, false, false));
         * bNotes.setText("Note..."); // Generated
         * bNotes.addSelectionListener(new
         * org.eclipse.swt.events.SelectionAdapter() { public void
         * widgetSelected(org.eclipse.swt.events.SelectionEvent e) { String tip
         * = Messages.getTooltip("doc.note.text.releases");
         * //DocumentationForm.openNoteDialog(dom, listener.getRelease(),
         * "note", tip, true, false,"Release Note", txtNotes);
         * DocumentationForm.openNoteDialog(dom, listener.getRelease(), "note",
         * tip, true, false,"Release Note"); } });
         */
        /*
         * txtChanges = new Text(composite, SWT.BORDER);
         * txtChanges.setEditable(false); txtChanges.setLayoutData(new
         * GridData(GridData.FILL, GridData.FILL, true, true));
         */
        fChanges = new NoteForm(composite, SWT.NONE);
        fChanges.setTitle(JOE_M_ReleaseForm_Changes.label());
        fChanges.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

        /*
         * bChanges = new Button(composite, SWT.NONE);
         * bChanges.setLayoutData(new GridData(GridData.BEGINNING,
         * GridData.BEGINNING, false, false)); bChanges.setText("Changes...");
         * // Generated bChanges.addSelectionListener(new
         * org.eclipse.swt.events.SelectionAdapter() { public void
         * widgetSelected(org.eclipse.swt.events.SelectionEvent e) { String tip
         * = Messages.getTooltip("doc.note.text.changes");
         * DocumentationForm.openNoteDialog(dom, listener.getRelease(),
         * "changes", tip, true, false ,"Changes"); } });
         */
        // group1 = new Group(group, SWT.NONE);
        // group1.setText("Authors"); // Generated
        // group1.setLayoutData(gridData5); // Generated
        // group1.setLayout(gridLayout1); // Generated
    }

    /**
     * This method initializes composite
     */
    private void createComposite() {
    }

    /**
     * This method initializes created
     */
    private void createCreated() {
        GridData gridData6 = new GridData(GridData.FILL, GridData.CENTER, false, false);

        created = JOE_ReleaseForm_Created.Control(new SOSDateTime(group, SWT.NONE));
        created.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                listener.setCreated(created.getISODate());
            }
        });
        created.setLayoutData(gridData6); // Generated
    }

    /**
     * This method initializes modified
     */
    private void createModified() {
        GridData gridData13 = new GridData(GridData.FILL, GridData.CENTER, false, false);
        
        modified = JOE_ReleaseForm_Modified.Control(new SOSDateTime(group, SWT.NONE));
        modified.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                listener.setModified(modified.getISODate());
            }
        });
        modified.setLayoutData(gridData13); // Generated
    }

    public void apply() {
        applyRelease();
    }

    public boolean isUnsaved() {
        return false;
    }

    public void setToolTipText() {
        // bNotes.setToolTipText(Messages.getTooltip("doc.releases.notes"));
        // bChanges.setToolTipText(Messages.getTooltip("doc.releases.changes"));

    }

    private void setReleaseStatus(boolean enabled) throws ParseException {
        // bApply.setEnabled(false);
        // bNotes.setEnabled(enabled);
        // bChanges.setEnabled(enabled);

        if (enabled) {
            tTitle.setText(listener.getTitle());

            if (listener.getCreated().equals(""))
                created.setDate(new Date());
            else
                created.setDate(listener.getCreated());
            if (listener.getModified().equals(""))
                modified.setDate(new Date());
            else
                modified.setDate(listener.getModified());

            tID.setText(listener.getID());
            tID.setFocus();
        }
    }

    private void applyRelease() {
        // bApply.setEnabled(false);
        // tID.setText("");
        // tTitle.setText("");
        listener.applyRelease(tTitle.getText(), tID.getText(), created.getISODate(), modified.getISODate());

    }

} // @jve:decl-index=0:visual-constraint="10,10"
