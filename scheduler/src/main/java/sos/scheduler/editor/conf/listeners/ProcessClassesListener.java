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
package sos.scheduler.editor.conf.listeners;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;
import org.jdom.JDOMException;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;

public class ProcessClassesListener {


	private final static String              CATCHALL        = "<empty>";

	private              SchedulerDom        _dom            = null;

	private              Element             _config         = null;

	private              Element             _processClasses = null;

	private              List                _list           = null;

	private              Element             _class          = null;


	public ProcessClassesListener(SchedulerDom dom, Element config) throws JDOMException {

		_dom = dom;
		_config = config;
		_processClasses = _config.getChild("process_classes");

		if (_processClasses != null)
			_list = _processClasses.getChildren("process_class");

	}


	private void initClasses() {

		if (_config.getChild("process_classes") == null) {
			_processClasses = new Element("process_classes");
			_config.addContent(_processClasses);
		} else {
			_processClasses = _config.getChild("process_classes");
		}
		_list = _processClasses.getChildren("process_class");

	}


	public void fillTable(Table table) {

		table.removeAll();
		if (_list != null) {
			for (Iterator it = _list.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				TableItem item = new TableItem(table, SWT.NONE);
				String name = Utils.getAttributeValue("name", e);
				if (name.equals(""))
					name = CATCHALL;
				item.setText(0, name);
				item.setText(1, "" + Utils.getIntValue("max_processes", e));
				item.setText(2, Utils.getAttributeValue("remote_scheduler", e));
				if(!Utils.isElementEnabled("process_class", _dom, e)) {
					item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
				} 
			}
		}
	}


	public void selectProcessClass(int index) {
		if(_list==null)
			initClasses();
		if (_list != null && index >= 0 && index < _list.size())
			_class = (Element) _list.get(index);
		else
			_class = null;
	}

	public Element getProcessElement(int index) {
		selectProcessClass(index);
		return _class;
	}

	public String getProcessClass() {
		String name = Utils.getAttributeValue("name", _class);
		if (name.equals(CATCHALL))
			name = "";
		return name;
	}

	public String getRemoteHost() {
		String host = Utils.getAttributeValue("remote_scheduler", _class);
		try {
			host = host.substring(0,host.indexOf(":"));
		}catch (Exception e) {host = "";}
		return host.trim();
	}

	public String getRemotePort() {
		String port = Utils.getAttributeValue("remote_scheduler", _class);
		try {
			port = port.substring(port.indexOf(":")+1);
		}catch (Exception e) {port = "";}
		return port.trim();
	}

	public String getMaxProcesses() {
		return Utils.getAttributeValue("max_processes", _class);
	}


	public String getSpoolerID() {
		return Utils.getAttributeValue("spooler_id", _class);
	}

	public void setIgnoreProcessClasses(boolean ignore) {
		if(_processClasses == null) {
			Element config = _dom.getRoot().getChild("config");    		
			_processClasses = config.getChild("process_classes");
			if(_processClasses ==null) {
				_processClasses =  new Element("process_classes");
				config.addContent(_processClasses);
			}
		}

		Utils.setAttribute("ignore", ignore, false, _processClasses, _dom);
	}

	public boolean isIgnoreProcessClasses() {    	
		if(_processClasses  != null)
			return Utils.getAttributeValue("ignore", _processClasses).equals("yes") ? true : false;
		else 
			return false;
	}

	public boolean isReplace() {    	    	
		//default ist true daher auch gleich leerstring
		return Utils.getAttributeValue("replace", _class).equals("") || Utils.getBooleanValue("replace", _class);    	
	}


	public void newProcessClass() {
		_class = new Element("process_class");
	}


	public void applyProcessClass(String processClass, String host, String port, int maxProcesses) {
		
		_dom.setChanged(true);
		_dom.setChangedForDirectory("process_class", Utils.getAttributeValue("name", _class), SchedulerDom.DELETE);
		Utils.setAttribute("name", processClass, _class, _dom);
		Utils.setAttribute("max_processes", maxProcesses, _class, _dom);

		if(host.trim().concat(port.trim()).length() > 0) {
			Utils.setAttribute("remote_scheduler", host.trim()+":"+port.trim(), _class, _dom);
		}
		if (_list == null)
			initClasses();
		if (!_list.contains(_class)) {
			_list.add(_class);            
			_dom.setChangedForDirectory("process_class", processClass, SchedulerDom.NEW);   
		} else if (_dom.isLifeElement()) {
			_dom.setChangedForDirectory("process_class", processClass, SchedulerDom.NEW);        	
			_dom.getRoot().setAttribute("name", _class.getAttributeValue("name"));
		} else {
			_dom.setChangedForDirectory("process_class", processClass, SchedulerDom.MODIFY);
		}
		

	}


	public void removeProcessClass(int index) {
		if (index >= 0 && index < _list.size()) {
			String processClass = Utils.getAttributeValue("name", (Element)_list.get(index));
			_list.remove(index);
			if (_list.size() == 0 && !isIgnoreProcessClasses()) {
				_config.removeChild("process_classes");
				_processClasses = null;
				_list = null;
			}
			_class = null;
			_dom.setChanged(true);
			_dom.setChangedForDirectory("process_class", processClass, SchedulerDom.DELETE);
		}
	}


	public boolean isValidClass(String name) {
		if (_list != null) {
			for (Iterator it = _list.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				if (Utils.getAttributeValue("name", e).equals(name))
					return false;
			}
		}
		return true;
	}

}
