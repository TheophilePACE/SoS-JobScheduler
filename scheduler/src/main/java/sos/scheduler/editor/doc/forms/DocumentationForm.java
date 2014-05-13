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

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jdom.Element;
import sos.scheduler.editor.app.IContainer;
import sos.scheduler.editor.app.IEditor;
import sos.scheduler.editor.app.IOUtils;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.TreeData;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;
import sos.scheduler.editor.doc.IDocumentationUpdate;
import sos.scheduler.editor.doc.NoteDialog;
import sos.scheduler.editor.doc.listeners.DocumentationListener;

// TODO doppelte einträge verhindern

public class DocumentationForm extends SOSJOEMessageCodes implements IEditor, IDocumentationUpdate {
	
    private DocumentationListener listener    = null;

    private DocumentationDom      dom         = null;

    private IContainer            container   = null;

    private SashForm              sashForm    = null;

    private Group                 group       = null;

    private Composite             docMainForm = null;

    private Tree                  docTree     = null;

    private TreeItem              selection   = null;


    public DocumentationForm(IContainer container, Composite parent, int style) {
        super(parent, style);
        this.container = container;

        // initialize();
        dom = new DocumentationDom();
        dom.setDataChangedListener(this);
        listener = new DocumentationListener(this, dom);
    }


    private void initialize() {
        createSashForm();
        setSize(new Point(724, 479));
        setLayout(new FillLayout());

    }


    /**
     * This method initializes sashForm
     */
    private void createSashForm() {
        sashForm = new SashForm(this, SWT.NONE);
        createGroup();
        createDocMainForm();
        sashForm.setWeights(new int[] { 66, 264 });
        Options.loadSash("documentation", sashForm);
    }


    /**
     * This method initializes group
     */
    private void createGroup() {
        group = JOE_G_DocumentationForm_DocElements.Control(new Group(sashForm, SWT.V_SCROLL | SWT.H_SCROLL));
        group.setLayout(new FillLayout()); // Generated
        
        docTree = new Tree(group, SWT.NONE);
        docTree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (docTree.getSelectionCount() > 0) {
                    if (selection == null)
                        selection = docTree.getItem(0);

                    e.doit = listener.treeSelection(docTree, docMainForm);

                    if (!e.doit) {
                        docTree.setSelection(new TreeItem[] { selection });
                    } else {
                        selection = docTree.getSelection()[0];
                    }
                }
            }
        });
    }


    /**
     * This method initializes docMainForm
     */
    private void createDocMainForm() {
        docMainForm = new Composite(sashForm, SWT.NONE);
        docMainForm.setLayout(new FillLayout());
    }


    public boolean applyChanges() {
        Control[] c = docMainForm.getChildren();
        return c.length == 0 || Utils.applyFormChanges(c[0]);
    }


    public boolean close() {
        return applyChanges() && IOUtils.continueAnyway(dom);
    }


    public boolean hasChanges() {
        Options.saveSash("documentation", sashForm.getWeights());

        return dom.isChanged();
    }


    public boolean open(Collection files) {
        boolean res = IOUtils.openFile(files, dom);
        if (res) {
            initialize();
            listener.fillTree(docTree);
            docTree.setSelection(new TreeItem[] { docTree.getItem(0) });
            listener.treeSelection(docTree, docMainForm);
        }

        return res;
    }

    public boolean open(String filename, Collection files) {
        boolean res = IOUtils.openFile(filename ,files, dom);
        if (res) {
            initialize();
            listener.fillTree(docTree);
            docTree.setSelection(new TreeItem[] { docTree.getItem(0) });
            listener.treeSelection(docTree, docMainForm);
        }

        return res;
    }

    public void openBlank() {
        initialize();
        listener.fillTree(docTree);
        docTree.setSelection(new TreeItem[] { docTree.getItem(0) });
        listener.treeSelection(docTree, docMainForm);
    }


    public boolean save() {
        boolean res = IOUtils.saveFile(dom, false);
        if (res)
            container.setNewFilename(null);
        
        Utils.setResetElement(dom.getRoot());
        
        return res;
    }


    public boolean saveAs() {
        String old = dom.getFilename();
        boolean res = IOUtils.saveFile(dom, true);
        if (res)
            container.setNewFilename(old);
        return res;
    }


    public void updateLanguage() {
        if (docMainForm.getChildren().length > 0) {
            if (docMainForm.getChildren()[0] instanceof IUpdateLanguage) {
                ((IUpdateLanguage) docMainForm.getChildren()[0]).setToolTipText();
            }
        }
    }


    public String getHelpKey() {
        if (docTree.getSelectionCount() > 0) {
            TreeItem item = docTree.getSelection()[0];
            TreeData data = (TreeData) item.getData();
            if (data != null && data.getHelpKey() != null)
                return data.getHelpKey();
        }
        return null;
    }


    public String getFilename() {
        return dom.getFilename();
    }


    public void dataChanged() {
        container.setStatusInTitle();
    }


    public static void openNoteDialog(DocumentationDom dom, Element parentElement, String name, boolean optional,String title) {
        openNoteDialog(dom, parentElement, name, null, optional, true,title);
    }


    public static void openNoteDialog(DocumentationDom dom, Element parentElement, String name, boolean optional,
            boolean changeStatus,String title) {
        openNoteDialog(dom, parentElement, name, null, optional, changeStatus,title);
    }


    public static void openNoteDialog(DocumentationDom dom, Element parentElement, String name, String tooltip,
            boolean optional,String title) {
        openNoteDialog(dom, parentElement, name, tooltip, optional, true,title);
    }


    public static void openNoteDialog(DocumentationDom dom, Element parentElement, String name, String tooltip,
            boolean optional, boolean changeStatus, String title) {
        NoteDialog dialog = new NoteDialog(MainWindow.getSShell(),title);
        dialog.setText("Note Editor");
        dialog.setTooltip(tooltip);
        
        dialog.setParams(dom, parentElement, name, optional, changeStatus);
        dialog.open();
    }


    public static void openNoteDialog(DocumentationDom dom, Element parentElement, String name, String tooltip,
            boolean optional, boolean changeStatus, String title, org.eclipse.swt.widgets.Text txt) {
        NoteDialog dialog = new NoteDialog(MainWindow.getSShell(),title);
        dialog.setText("Note Editor");
        //dialog.setUpdateText(txt); //Textfeld soll beim verlassen des Dialogs aktualisert werden
        dialog.setTooltip(tooltip);
        
        dialog.setParams(dom, parentElement, name, optional, changeStatus);
        dialog.open();
    }

    
	public DocumentationDom getDom() {
		return dom;
	}

	public void updateReleases() {
		if(docTree.getSelectionCount() > 0) {
			TreeItem item = docTree.getSelection()[0];		
			TreeData data = (TreeData) item.getData();
			org.jdom.Element elem = data.getElement();
			listener.treeFillReleases(item, elem);
			//tree.getSelection()[0].getItems()[tree.getSelection()[0].getItemCount()-1].setExpanded(true);
		}
	}
	
	public void updateDatabaseResource() {
		if(docTree.getSelectionCount() > 0) {
			TreeItem item = docTree.getSelection()[0];		
			TreeData data = (TreeData) item.getData();
			org.jdom.Element elem = data.getElement();
			listener.treeFillDatabaseResources(item, elem.getChild("resources", elem.getNamespace()));
			//tree.getSelection()[0].getItems()[tree.getSelection()[0].getItemCount()-1].setExpanded(true);
		}
	}
	
	public void updateTree(String which) {		
		if(which.equals("main")) {
			//neu zeichnen und das erste Element markieren
			listener.fillTree(docTree);
			docTree.setSelection(new TreeItem[] { docTree.getItem(0) });
		}
		listener.treeSelection(docTree, docMainForm);

	}
} // @jve:decl-index=0:visual-constraint="10,10"
