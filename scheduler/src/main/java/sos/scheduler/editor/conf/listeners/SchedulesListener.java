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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;


public class SchedulesListener { 

	private    SchedulerDom         _dom          = null;

	private    ISchedulerUpdate     _main         = null;

	private    Element              _config       = null;

	private    Element              _schedules    = null;

	private    List                 _list         = null;


	public SchedulesListener(SchedulerDom dom, ISchedulerUpdate update) {

		_dom = dom;
		_main = update;
		if(_dom.isLifeElement()) {

		} else {
			_config = _dom.getRoot().getChild("config");
			_schedules = _config.getChild("schedules");

			if (_schedules != null)
				_list = _schedules.getChildren("schedule");
		}

	}


	private void initSchedules() {
		if (_config.getChild("schedules") == null) {
			Element _schedules = new Element("schedules");
			_config.addContent(_schedules);
			_list = _schedules.getChildren("schedule");
		} else {
			_schedules = _config.getChild("schedules");
			_list = _schedules.getChildren("schedule");
		}
	}


	public void fillTable(Table table) {		
		table.removeAll();  
		if (_list != null) {
			for (Iterator it = _list.iterator(); it.hasNext();) {
				Object o = it.next();
				if (o instanceof Element) {
					Element e = (Element) o;
					TableItem item = new TableItem(table, SWT.NONE);
					item.setData(e);
					String name = Utils.getAttributeValue("name", e);

					item.setText(0, name);

				}
			}
		}
	}


	public void newScheduler(Table table) {
		Element schedule = new Element("schedule");
		String name = "schedule" + (table.getItemCount() + 1);
		schedule.setAttribute("name", name);				
		if (_list == null)
			initSchedules();
		_list.add(schedule);
				
		fillTable(table);
		table.setSelection(table.getItemCount() - 1);
		_main.updateSchedules();
		_main.expandItem(name);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", schedule), SchedulerDom.NEW);

	}



	public boolean deleteSchedule(Table table) {
		int index = table.getSelectionIndex();
		if (index >= 0) {
			TableItem item = table.getItem(index);
			Element e = (Element) item.getData();			
			e.detach();
			_dom.setChanged(true);
			_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", e) ,SchedulerDom.DELETE);
			table.remove(index);			
			_main.updateSchedules();
			if(_list==null)
				initSchedules();
			if (_list.size() == 0) {
				_config.removeChild("schedules");
				_schedules = null;
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


}
