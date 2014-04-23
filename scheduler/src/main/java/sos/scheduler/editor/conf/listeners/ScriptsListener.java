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

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;


public class ScriptsListener { 
	
	private SchedulerDom        _dom                = null;
	
	private ISchedulerUpdate    _main               = null;		
	
	private List                _list               = null;
	
	private Element             _parent             = null;
	
	private final static String EMPTY_MONITOR_NAME  = "<empty>";
	
	
	
	public ScriptsListener(SchedulerDom dom, ISchedulerUpdate update, Element parent) {
		_dom = dom;
		_main = update;
		_parent = parent;
		
		_list = parent.getChildren("monitor");
		
		
	}
	
	
	private void initScripts() {							
		_list = _parent.getChildren("monitor");		
	}
	
	
	public void fillTable(Table table) {		
		table.removeAll();  
		if (_list != null) {
			for(int i= 0; i < _list.size(); i++) {
				Element monitor = (Element)_list.get(i);
				TableItem item = new TableItem(table, SWT.NONE);
				item.setData(monitor);
				String name = Utils.getAttributeValue("name", monitor);
				if(!Utils.isElementEnabled("job", _dom, _parent)) {
					item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));						
				} else {
					item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
				}				
				item.setText(0, name.equals("") ? EMPTY_MONITOR_NAME : name);
				item.setText(1, Utils.getAttributeValue("ordering", monitor));
			}
			
		}
	}
	
	
	public void newScript(Table table, String name) {
		Element monitor = new Element("monitor");
		if(name != null && name.length() > 0){
			monitor.setAttribute("name", "monitor" + (table.getItemCount() + 1));
		}
		
		
		if (_list == null)
			initScripts();		
		_dom.setChanged(true);
		
		if(_dom.isLifeElement() || _dom.isDirectory())
			_dom.setChangedForDirectory("job", Utils.getAttributeValue("name", _parent), SchedulerDom.MODIFY);
		
		fillTable(table);
		table.setSelection(table.getItemCount() - 1);
		_main.updateScripts();
		_main.expandItem("Monitor: "+ "monitor" + (table.getItemCount()));  
	}
	
	
	public void save(Table table, String name, String ordering, String newName) {
        boolean found = false;
        Element e = null;
        TableItem item = null;
        if (_list != null) {
                int index = 0;
                
                for(int i = 0; i < _list.size(); i++) {                    
                    e = (Element) _list.get(i);
                    if (name.equals(Utils.getAttributeValue("name", e)) || 
                       (name.equals("<empty>") && Utils.getAttributeValue("name", e).equals(""))) {                    	
                    	if(newName != null) {
                    		//name ändern
                    		Utils.setAttribute("name", newName, e); 
                    		_main.updateScripts();
                    		name = newName;
                    	}
                    	found = true;
                    	Utils.setAttribute("ordering", ordering, e);
                    	
                    	_dom.setChanged(true);
                    	if(_dom.isLifeElement() || _dom.isDirectory())
                    		_dom.setChangedForDirectory(_parent.getName(), Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
                    	table.getItem(index).setText(new String[] { (name.equals("") ? EMPTY_MONITOR_NAME : name)  , ordering });
                    }
                    index++;
                }
        }

        if (!found) {
            e = new Element("monitor");
            if (name.equals(EMPTY_MONITOR_NAME)) {                
                e.removeAttribute("name");
                Utils.setAttribute("ordering", ordering, e);
            } else {              
                Utils.setAttribute("name", name, e);
                Utils.setAttribute("ordering", ordering, e);
            }

            _dom.setChanged(true);            
            
            if(_dom.isLifeElement() || _dom.isDirectory()) 
            	_dom.setChangedForDirectory(_parent.getName(), Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
            
            if (_list == null)
            	initScripts();
            if (_list != null)
            	_list.add(e);
            
            item = new TableItem(table, SWT.NONE);
            item.setText(new String[] { (name.equals("") ? EMPTY_MONITOR_NAME : name)  , ordering });
            item.setData(e);
            
            _main.updateScripts();
           
        }

    }
	
	public boolean delete(Table table) {
		int index = table.getSelectionIndex();
		if (index >= 0) {
			TableItem item = table.getItem(index);
			Element e = (Element) item.getData();
			e.detach();
			_dom.setChanged(true);
			 if(_dom.isLifeElement() || _dom.isDirectory()) 
	            	_dom.setChangedForDirectory(_parent.getName(), Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
			table.remove(index);
			_main.updateScripts();
			if(_list==null)
				initScripts();
			if (_list.size() == 0) {
				_list = null;
			}

			if (index >= table.getItemCount())
				index--;
			if (index >= 0) {
				table.setSelection(index);
				return true;
			}
		}
		return false;
	}


	public boolean existScriptname(String name) {
		if(name == null || name.length() == 0)
			return false;
		
		for (int i = 0; _list != null && i < _list.size(); i++) {
			Element currJob = (Element)_list.get(i);
			String jobName = Utils.getAttributeValue("name", currJob);
			if (jobName.equalsIgnoreCase(name)) {			
				return true;				
			} 
		}
		return false;
	}


	public Element getParent() {
		return _parent;
	}
	
}
