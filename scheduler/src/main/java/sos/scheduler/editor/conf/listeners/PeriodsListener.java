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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;

public class PeriodsListener {


	private    SchedulerDom          _dom        = null;

	private    Element               _parent     = null;

	private    List                  _list       = null;

	private    int                   _period     = -1;

	private    List                  _listOfAt   = null;

	private    ISchedulerUpdate      _main       = null;


	public PeriodsListener(SchedulerDom dom, Element parent) {
		this(dom, parent, null);		
	}

	public PeriodsListener(SchedulerDom dom, Element parent, ISchedulerUpdate main) {

		_dom    = dom;
		_parent = parent; 
		_main   = main;

		if(_parent.getName().equals("at")) {
			_list = getDateChildren();
		} else {
			_list = parent.getChildren("period");
		}

		_listOfAt = getAtChildren();

	}


	private List getDateChildren() {
		List retval = new ArrayList();
		String date = Utils.getAttributeValue("at",_parent).substring(0, 10);
		if(_parent.getParentElement() == null) {
			return retval;
		}
		List parentDates = _parent.getParentElement().getChildren("date");
		for(int i=0; i < parentDates.size(); i++) {
			Element d = (Element)parentDates.get(i);
			if(Utils.getAttributeValue("date", d).equals(date)){
				retval = d.getChildren("period"); 
				return retval;
			}
		}

		return retval;
	}


	private List getAtChildren() {

		ArrayList retval = new ArrayList();

		if(_parent != null && _parent.getParentElement() != null) {
			List l = null;
			String date = "";
			l = _parent.getParentElement().getChildren("at");
			if(_parent.getName().equals("at")) {    				
				date = Utils.getAttributeValue("at",_parent).substring(0, 10);
			}else {    				
				date = Utils.getAttributeValue("date",_parent);
			}

			for (int i=0; i <l.size(); i++) {
				Element at = (Element)l.get(i);
				if(Utils.getAttributeValue("at", at).startsWith(date)){
					retval.add(at);
				}
			}
		}

		return retval;
	}

	public boolean isOnOrder() {

		Element job = _parent;

		if(job.getParentElement()==null)
			return false;		

		//m�gliche Vaterknoten herausfinden
		while (job != null 
				&& !job.getName().equals("job") 
				&& !job.getName().equals("add_order")
				&& !job.getName().equals("order")
				&& !job.getName().equals("schedule")
				&& !job.getName().equals("config"))
			job = job.getParentElement();

		if(job == null )
			job = _parent;
		
		return		
		Utils.isAttributeValue("order", job) && job.getName().equals("job") || 
		Utils.isAttributeValue("id", job)&& job.getName().equals("add_order");

	}


	public void fillTable(Table table) {
		table.removeAll();

		if (_list != null) {
			for (Iterator it = _list.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				TableItem item = new TableItem(table, SWT.NONE);
				if(Utils.getAttributeValue("single_start", e).length() > 0)
					item.setText(0, "");
				else
					item.setText(0, Utils.isAttributeValue("let_run", e) ? "Yes" : "No");
				item.setText(1, Utils.getAttributeValue("begin", e));
				item.setText(2, Utils.getAttributeValue("end", e));
				item.setText(3, Utils.getAttributeValue("repeat", e));
				item.setText(4, Utils.getAttributeValue("single_start", e));
				item.setText(5, Utils.getAttributeValue("absolute_repeat", e));
				item.setData(e);
				String whenHoliday = Utils.getAttributeValue("when_holiday", e).length() == 0 ? "suppress execution" : Utils.getAttributeValue("when_holiday", e);
				item.setText(6, whenHoliday);


			}
		}        
		ArrayList remList = new ArrayList();
		for(int i =0; i < _listOfAt.size(); i++) {
			Element at = (Element)_listOfAt.get(i);
			String sAT = Utils.getAttributeValue("at", at);

			if(sAT != null && sAT.length()> 0) {
				String date = sAT.substring(0, 10);				
				String time = sAT.substring((sAT.indexOf(" ") > -1 ? sAT.indexOf(" ") + 1 : sAT.length()));
				if(time.length()==0) {
					//at ohne Zeitangabe werden gel�scht
					remList.add(at);
				} else {					
					if(date.equalsIgnoreCase(Utils.getAttributeValue("date", _parent)) ||
							Utils.getAttributeValue("at", _parent).startsWith(date)){
						TableItem item = new TableItem(table, SWT.NONE);    		
						item.setText(0, "No");
						item.setText(1, "");
						item.setText(2, "");
						item.setText(3, "");                         
						item.setText(4, time);
						item.setData(at);
						
					}
				}
			}
		}
		_listOfAt.removeAll(remList);

	} 

	public void removePeriod(Element el) {
		if(_list.contains(el)){
			_list.remove(el);
			_period = -1;	
		} else if(_listOfAt.contains(el)){

			el.getParentElement().getChildren("at").remove(el);
			_listOfAt.remove(el);

		}
		_dom.setChanged(true);

		_dom.setChangedForDirectory(_parent, SchedulerDom.MODIFY);
		if(_main != null) {
			_main.updateFont();
		}
	}

	public void removePeriod(int index) {

		if (index >= 0 && index < _list.size()) {
			_list.remove(index);
			_period = -1;			
		} else {
			if(_parent.getName().equals("at") || _list.size()==0 && _listOfAt.size() > 0) {
				int i = _list.size() - (index); 
				if(i < 0) 
					i = i*(-1);
				Element el = (Element)(_listOfAt.get(i));	

				if(_list.size()==0 && _listOfAt.size()==1) {
					//das letzte at Element wird gel�scht. Das Datum steht auf der Treeview
					String[] date = Utils.getAttributeValue("at", el).substring(0, 10).split("-");

					DateListener dateListener = new DateListener(_dom, _parent.getParentElement(), 1); 
					if(!dateListener.exists(Utils.str2int(date[0]), Utils.str2int(date[1]), Utils.str2int(date[2]))) {    					
						_parent = dateListener.addDate(Utils.str2int(date[0]), Utils.str2int(date[1]), Utils.str2int(date[2]));
						_list = _parent.getChildren("period");
					}
				}

				if(i + 1 < _listOfAt.size())
					_parent =(Element)_listOfAt.get(i + 1);

				el.getParentElement().getChildren("at").remove(_listOfAt.get(i));


				_listOfAt.remove(i);				

			} else {
				System.out.println("Bad period index for removal!");
			}
		}    	

		_dom.setChanged(true);
		_dom.setChangedForDirectory(_parent, SchedulerDom.MODIFY);
		if(_main != null) {
			_main.updateFont();
		}

	}


	public Element getNewPeriod() {
		_period = -1;
		Element e = new Element("period");
		e.setAttribute("end","24:00");
		return e;
	}


	public Element getPeriod(int index) {
		if (index >= 0 && index < _list.size()) {
			_period = index;
			//return (Element) ((Element) _list.get(index)).clone();
			return (Element) _list.get(index);
		} else {			
			if(_parent.getName().equals("at") || _listOfAt.size() > 0) {
				int i = _list.size() - (index); 
				if(i < 0) 
					i = i*(-1);
				Element el = (Element)(_listOfAt.get(i));
				return el;
			} 
			System.out.println("Bad period index for selection!");
			return null;

		}
	}


	public void applyPeriod(Element period) {
		if(!period.getName().equals("at")) {			
			if (_period == -1) {  
				if(!_list.contains(period))
					_list.add(period);
				
			} else {				
				if(!_list.contains(period))
					_list.set(_period, period);

			}
		}
		_dom.setChanged(true);
		//Element job = getJobElement(_parent);
		_dom.setChangedForDirectory(_parent, SchedulerDom.MODIFY);
		if(_main != null) {
			_main.updateFont();
		}
	}

	public List get_list() {
		return _list;
	}

	public Element getAtElement(String at) {
		String date = "";
		if(_parent.getName().equalsIgnoreCase("at")){
			date = Utils.getAttributeValue("at", _parent);
			date = date.substring(0, date.indexOf(" "));
		} else {
			date = Utils.getAttributeValue("date", _parent);
		}

		for (int i =0; i < _listOfAt.size(); i++) {
			Element eat = (Element)(_listOfAt.get(i));
			String[] seat = Utils.getAttributeValue("at", eat).split(" ");
			String sDate = seat[0];
			String time = seat[1];
			if(sDate.equalsIgnoreCase(date) && time.equalsIgnoreCase(at)) {
				return eat;
			}
		}
		return null;

	}


	/**
	 * �berpr�ft, ob ein das Period Element absolute_repeat oder repeat Attribute har.
	 * Es ist nur eins erlaubt.
	 * @return
	 */
	public boolean hasRepeatTimes() {		
		if(_parent != null )  {
			java.util.List l = _parent.getChildren("period");
			for(int i = 0; i < l.size(); i++) {
				Element p = (Element)l.get(i);
				if(Utils.getAttributeValue("repeat", p).length() > 0 || Utils.getAttributeValue("absolute_repeat", p).length() > 0) {
					return true;
				}
			}			
		}
		return false;
	}

	/**
	 * �berpr�ft, ob Element p ein repeat bzw. absolute_repeat Attribut har
	 * @param p
	 * @return
	 */
	public boolean isRepeatElement(Element p) {		
		
		if(Utils.getAttributeValue("repeat", p).trim().length() > 0 || 
		   Utils.getAttributeValue("absolute_repeat", p).trim().length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	
}
