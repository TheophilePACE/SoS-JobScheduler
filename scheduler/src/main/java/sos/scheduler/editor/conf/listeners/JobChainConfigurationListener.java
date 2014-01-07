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
package sos.scheduler.editor.conf.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import sos.scheduler.editor.app.Editor;
import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.forms.JobChainConfigurationForm;
import sos.scheduler.editor.conf.forms.DetailForm;
import org.jdom.Document;
import sos.scheduler.editor.conf.DetailDom;


public class JobChainConfigurationListener{

	private DetailDom  _dom;

	private JobChainConfigurationForm _gui            = null;

	private String                    filename        = null;

	private String                    jobChainname    = null; 

	public JobChainConfigurationListener(JobChainConfigurationForm gui, DetailDom dom) {
		_gui = gui;
		_dom = dom;
	} 


	public void treeFillMain(Tree tree, Composite c, String jobChainname_) {
		jobChainname = jobChainname_;
		treeFillMain(tree, c);
	}

	public void treeFillMain(Tree tree, Composite c) {
		tree.removeAll();

		if(_dom.getDoc() != null) {
			Element jobChain = _dom.getRoot().getChild("job_chain");

			if(jobChain == null) {
				jobChain = _dom.getRoot().getChild("application");
			}

			jobChainname = Utils.getAttributeValue("name", jobChain);
		}
		TreeItem item = new TreeItem(tree, SWT.NONE);

		treeFillState(item);
		item.setText((jobChainname != null? jobChainname:""));
		item.setExpanded(true);

		tree.setSelection(new TreeItem[] { tree.getItem(0) });
		treeSelection(tree, c);

	}

	public void setFilename(String filename_) {
		filename = filename_;
	}

	public void treeFillState(TreeItem parent) {
		parent.removeAll();
		ArrayList listOfState = getAllDetailState(); 

		for (int i =0; i < listOfState.size(); i++) {
			TreeItem item = new TreeItem(parent, SWT.NONE);
			item.setData(listOfState.get(i));
			item.setText("State: " + listOfState.get(i).toString());        	
		}

		parent.setExpanded(true);
	}

	private ArrayList getAllDetailState() {
		ArrayList list = new ArrayList();


		Element root        = null;					
		Element order       = null;  
		//Element params_     = null;
		List process     = null;

		try {
			SAXBuilder builder = new SAXBuilder();
			filename = _dom.getFilename();
			if(filename == null || filename.length() == 0)
				return list;

			Document doc = builder.build( new File( filename ) );

			root = doc.getRootElement();

			Element application = root.getChild("job_chain");
			if(application == null) {
				application = root.getChild("application");
			}

			jobChainname = Utils.getAttributeValue("name", application);

			if(application != null)
				order =   application.getChild("order");


			if(order != null) {
				process = order.getChildren("process");		
			}

			for (int i = 0; i < process.size(); i++) {
				Element p = (Element)process.get(i);
				list.add(Utils.getAttributeValue("state", p));
			}

		} catch(Exception e) {
			try {
				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
			} catch(Exception ee) {
				//tu nichts
			}

			System.err.println("..error im JobChainConfigurationListener.getAllDetailState(): " + e.getMessage());
		}

		return list;
	}

	public void treeExpandJob(TreeItem parent, String job) {

		if (parent.getText().equals("Jobs")) {

			for (int i = 0; i < parent.getItemCount(); i++)
				if (parent.getItem(i).getText().equals(job)) {
					parent.getItem(i).setExpanded(true);
				}

		}

	}

	public boolean treeSelection(Tree tree, Composite c) {
		try {
			if (tree.getSelectionCount() > 0) {

				// dispose the old form
				Control[] children = c.getChildren();
				for (int i = 0; i < children.length; i++) {
					if (!Utils.applyFormChanges(children[i]))
						return false;
					children[i].dispose();
				}

				TreeItem item = tree.getSelection()[0];
				_dom.setInit(true);
				DetailForm df = null;
				try {
					if(jobChainname == null) {
						
						df = new DetailForm(c, SWT.NONE, Editor.DETAILS, _dom, _gui, false, null);
						df.setLayout(new org.eclipse.swt.layout.FillLayout());
						
					} else {                
						Composite composite = new Composite(c.getShell(), SWT.NONE);						
						composite.setLayout(new org.eclipse.swt.layout.FillLayout());
						df = new DetailForm(composite, SWT.NONE, jobChainname, item.getData() != null && !(item.getData() instanceof sos.scheduler.editor.app.TreeData)? item.getData().toString(): null, null, Editor.DETAILS, _dom, _gui, false, null);						
						df.setLayout(new org.eclipse.swt.layout.FillLayout());
					}
					df.setTree(tree);
					df.setJobChainConfigurationListener(this);

					df.open();

					if(df.hasErrors()) {
						df.dispose();

						_gui.close();
						return false;
					}

				} catch (Exception e) {	

					try {
						new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
					} catch(Exception ee) {
						//tu nichts
					}

					MainWindow.message(e.getMessage(), SWT.ICON_ERROR);	
					df.dispose();

					_gui.close();
					return false;
				}

			}

			c.layout();

		} catch (Exception e) {
			c.layout();
			try {
				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName(), e);
			} catch(Exception ee) {
				//tu nichts
			}

			e.printStackTrace();
			MainWindow.message(e.getMessage(), SWT.ICON_ERROR);
		}
		_dom.setInit(false);
		return true;
	}

	public void setJobChainname(String jobChainname) {
		this.jobChainname = jobChainname;		
	}


	public DetailDom getDom() {
		return _dom;
	}


	public void setDom(DetailDom _dom) {
		this._dom = _dom;
	}

	public void deleteState(String state, TreeItem parent) {
		parent.removeAll();        
		Element jobChain = _dom.getRoot().getChild("job_chain");
		if (jobChain != null) {
			//Iterator it = jobChain.getChildren().iterator();
			Element order = jobChain.getChild("order");
			List  pList = order.getChildren("process");
			for(int i = 0; i< pList.size(); i++) {            
				Object o = pList.get(i);
				if (o instanceof Element) {
					Element element = (Element) o;
					TreeItem item = new TreeItem(parent, SWT.NONE);                   
					item.setText("State: " + Utils.getAttributeValue("state", element));
					item.setData(Utils.getAttributeValue("state", element));

				}
			}
		}
		parent.setExpanded(true);
	}
}
