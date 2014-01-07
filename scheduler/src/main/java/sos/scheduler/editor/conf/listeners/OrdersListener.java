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
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;

public class OrdersListener {

	private SchedulerDom     _dom;

	private ISchedulerUpdate _main;

	private Element          _commands;

	private Element          _config;

	private List             _orders;

	private List             _orders2;


	public OrdersListener(SchedulerDom dom, ISchedulerUpdate update) {
		_dom = dom;
		_main = update;
		if(_dom.isLifeElement())
			return;
		_config = _dom.getRoot().getChild("config");        
		_commands = _config.getChild("commands");
		if (_commands != null) {        	
			_orders =  _commands.getChildren("add_order");
			_orders2 = _commands.getChildren("order");
		}
	}


	private void initCommands() {
		if (_config.getChild("commands") == null) {
			_commands = new Element("commands");
			_config.addContent(_commands);
		} else {
			_commands = _config.getChild("commands");
		}
		_orders = _commands.getChildren("add_order");
		_orders2 = _commands.getChildren("order");

	}


	public void fillTable(Table table) {
		table.removeAll();
		if (_orders != null) {
			for (Iterator it = _orders.iterator(); it.hasNext();) {        	
				Object o = it.next();        	
				if (o instanceof Element) {
					Element e = (Element) o;
					TableItem item = new TableItem(table, SWT.NONE);
					item.setData(e);
					String id = Utils.getAttributeValue("id", e);
					item.setText(0, id);
					if(!Utils.isElementEnabled("commands", _dom, e)) {
						item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
					}
				}
			}
			for (Iterator it = _orders2.iterator(); it.hasNext();) {        	
				Object o = it.next();        	
				if (o instanceof Element) {
					Element e = (Element) o;
					TableItem item = new TableItem(table, SWT.NONE);
					item.setData(e);
					String id = Utils.getAttributeValue("id", e);
					item.setText(0, id);
					if(!Utils.isElementEnabled("commands", _dom, e)) {
						item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
					}
				}
			}
		}
	}


	private boolean haveId(int id, Table table) {
		int count = table.getItemCount();
		for (int i = 0; i < count; i++) {
			TableItem item = table.getItem(i);
			String actId = item.getText();
			if (actId.trim().equals(String.valueOf(id)))
				return true;
		}
		return false;
	}


	public void newCommands(Table table) {

		boolean found = false;
		String id = "";
		int c = 1;
		while (!found) {
			if (!haveId(c, table)) {
				found = true;
				id = String.valueOf(c);
			}
			c++;
		}

		Element add_order = new Element("order");
		Element runtime = new Element("run_time");
		runtime.setAttribute("let_run", "no");
		add_order.setAttribute("id", id);

		if (_commands == null)
			initCommands();

		add_order.addContent(runtime); 
		_orders2.add(add_order);

		
		fillTable(table);
		table.setSelection(table.getItemCount() - 1);
		_main.updateOrders();
		_dom.setChanged(true);
		_dom.setChangedForDirectory("order", Utils.getAttributeValue("job_chain", add_order) + "," + id, SchedulerDom.NEW);
	}


	public boolean deleteCommands(Table table) {
		int index = table.getSelectionIndex();
		if (index >= 0) {
			TableItem item = table.getItem(index);
			Element e = (Element) item.getData();
			e.detach();
			_dom.setChanged(true);
			_dom.setChangedForDirectory("order", Utils.getAttributeValue("job_chain", e)+","+Utils.getAttributeValue("id", e), SchedulerDom.DELETE);
			table.remove(index);
			_main.updateOrders();

			if (index >= table.getItemCount())
				index--;
			if (index >= 0) {
				table.setSelection(index);
				return true;
			}
		}
		return false;
	}


	/** Lifert alle Order Id's */
	public String[] getOrderIds() {
		String[] listOfIds = null;
		if (_orders != null) {
			listOfIds = new String[_orders.size()];
			//for (Iterator it = _orders.iterator(); it.hasNext();) {
			for(int i = 0; i < _orders.size(); i++) {
				Object o = _orders.get(i);
				if (o instanceof Element) {
					Element e = (Element) o;
					String id = Utils.getAttributeValue("id", e);
					listOfIds[i] = id;
				}
			}
		}
		if (_orders2 != null) {
			listOfIds = new String[_orders2.size()];
			//for (Iterator it = _orders.iterator(); it.hasNext();) {
			for(int i = 0; i < _orders2.size(); i++) {
				Object o = _orders2.get(i);
				if (o instanceof Element) {
					Element e = (Element) o;
					String id = Utils.getAttributeValue("id", e);
					listOfIds[i] = id;
				}
			}
		}
		return listOfIds;
	}

}
