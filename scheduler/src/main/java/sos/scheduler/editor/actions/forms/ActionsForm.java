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
package sos.scheduler.editor.actions.forms;

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

import sos.scheduler.editor.actions.IActionsUpdate;
import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.actions.listeners.ActionsListener;
import sos.scheduler.editor.app.IContainer;
import sos.scheduler.editor.app.IEditor;
import sos.scheduler.editor.app.IOUtils;
import sos.scheduler.editor.app.IUpdateLanguage;
import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.SOSJOEMessageCodes;
import sos.scheduler.editor.app.TreeData;
import sos.scheduler.editor.app.Utils;


public class ActionsForm extends SOSJOEMessageCodes implements IEditor, IActionsUpdate {	

	private ActionsListener listener    = null;

	private ActionsDom      dom         = null;

	private IContainer            container   = null;

	private SashForm              sashForm    = null;

	private Group                 group       = null;

	private Composite             docMainForm = null;

	private Tree                  tree     = null;

	private TreeItem              selection   = null;




	public ActionsForm(IContainer container, Composite parent, int style) {
		super(parent, style);
		this.container = container;

		// initialize();
		dom = new ActionsDom();
		dom.setDataChangedListener(this);
		listener = new ActionsListener(this, dom);
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
		Options.loadSash("test", sashForm);
	}


	/**
	 * This method initializes group
	 */
	private void createGroup() {
		group = JOE_G_ActionsForm_ActionsElements.Control(new Group(sashForm, SWT.V_SCROLL | SWT.H_SCROLL));
		group.setLayout(new FillLayout()); // Generated
		tree = new Tree(group, SWT.NONE);
		//tree.addListener(SWT.Selection, new Listener() {
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {            	
				if (tree.getSelectionCount() > 0) {
					if (selection == null)
						selection = tree.getItem(0);

					e.doit = listener.treeSelection(tree, docMainForm);

					if (!e.doit) {
						tree.setSelection(new TreeItem[] { selection });
					} else {
						selection = tree.getSelection()[0];
					}
				}
				return;
			}
		});
	}


	 //Der SelectionListener gibt	lediglich den auslösenden	Event aus
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
		Options.saveSash("test", sashForm.getWeights());

		return dom.isChanged();
	}


	public boolean open(Collection files) {
		boolean res = IOUtils.openFile(files, dom);
		if (res) {
			initialize();
			listener.fillTree(tree);
			tree.setSelection(new TreeItem[] { tree.getItem(0) });
			listener.treeSelection(tree, docMainForm);
		}

		return res;
	}

	public boolean open(String filename, Collection files) {
		boolean res = IOUtils.openFile(filename ,files, dom);
		if (res) {
			initialize();
			listener.fillTree(tree);
			tree.setSelection(new TreeItem[] { tree.getItem(0) });
			listener.treeSelection(tree, docMainForm);
		}

		return res;
	}

	public void openBlank() {
		initialize();
		listener.fillTree(tree);
		tree.setSelection(new TreeItem[] { tree.getItem(0) });
		listener.treeSelection(tree, docMainForm);
	}


	public boolean save() {
		//boolean res = IOUtils.saveFile(dom, false);
		boolean res = IOUtils.saveAction(dom, false);
		if (res)
			container.setNewFilename(null);
		
		Utils.setResetElement(dom.getRoot());
		
		return res;
	}


	public boolean saveAs() {
		String old = dom.getFilename();
		boolean res = IOUtils.saveAction(dom, true);
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
		if (tree.getSelectionCount() > 0) {
			TreeItem item = tree.getSelection()[0];
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


	public ActionsDom getDom() {
		return dom;
	}

	public void updateAction(String name) {
		if(tree.getSelectionCount() > 0)
			tree.getSelection()[0].setText(ActionsListener.ACTION_PREFIX + name);
	}

	public void updateActions() {
		listener.treeFillAction(tree.getTopItem());		
	}

	public void updateEvents(Element action) {
		TreeItem item = tree.getTopItem();
		for(int i = 0; i < item.getItemCount(); i++) {
			TreeItem it = item.getItem(i);
			if(it.getText().equals(ActionsListener.ACTION_PREFIX + Utils.getAttributeValue("name", action))) {
				it = it.getItem(0);//events Knoten
				listener.fillEventGroup(it, action);
				it.setExpanded(true);
				break;
			}
		}	
	}


	public void updateCommands() {

		if (tree.getSelectionCount() > 0) {
			TreeItem item = tree.getSelection()[0];
			TreeData data = (TreeData) item.getData();
			listener.treeFillCommands(tree.getSelection()[0], data.getElement(), true);
			item.setExpanded(true);
		}
	}

	public void updateTreeItem(String s) {
        if(tree.getSelectionCount() > 0) {
			TreeItem item = tree.getSelection()[0];			
			item.setText(s);		
		}
	}

	public void updateCommand() {
		if (tree.getSelectionCount() > 0) {
			TreeItem item = tree.getSelection()[0];
			TreeData data = (TreeData) item.getData();
			listener.treeFillCommand(item , data.getElement(), true);
			item.setExpanded(true);
			if(item.getItemCount() > 0)
				item.getItems()[item.getItemCount()-1].setExpanded(true);
			//listener.treeFillCommands(tree.getSelection()[0], data.getElement(), true);
			//hier einen neuen TreeItem aufbauen
		}
	}

	
	
	public Tree getTree() {
		return tree;
	}

	public void updateTree(String which) {		
		if(which.equals("main")) {
			//neu zeichnen und das erste Element markieren
			listener.fillTree(tree);
			tree.setSelection(new TreeItem[] { tree.getItem(0) });
		}
		listener.treeSelection(tree, docMainForm);

	}

} // @jve:decl-index=0:visual-constraint="10,10"
