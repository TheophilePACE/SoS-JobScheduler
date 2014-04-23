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

import org.eclipse.swt.widgets.Button;
import org.jdom.Element;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;
import java.util.HashMap;

public class PeriodListener {


	private    SchedulerDom      _dom                      = null;

	private    Element           _period                   = null;

	private    Element           _at                       = null;
	
	private    String[]          _whenHolidays             = {"previous non holiday", "next non holiday", "suppress execution", "ignore holiday"};
	  //suppress
      //ignore_holiday
      //previous_non_holiday
      //next_non_holiday
	private    HashMap          _realNameWhenHolidays     = null;


	public PeriodListener(SchedulerDom dom) {
		_dom = dom;
		initWhenHolidays();
	}

	
	private void initWhenHolidays() {
		_realNameWhenHolidays = new HashMap();
		
		_realNameWhenHolidays.put("previous non holiday","previous_non_holiday"); 
		_realNameWhenHolidays.put("next non holiday", "next_non_holiday"); 
		_realNameWhenHolidays.put("suppress execution", "suppress");
		_realNameWhenHolidays.put("ignore holiday", "ignore_holiday");		
		
		_realNameWhenHolidays.put("previous_non_holiday", "previous non holiday"); 
		_realNameWhenHolidays.put("next_non_holiday", "next non holiday"); 
		_realNameWhenHolidays.put("suppress", "suppress execution");
		_realNameWhenHolidays.put("ignore_holiday", "ignore holiday");
		
	}

	public void setPeriod(Element period) {
		if(period != null && period.getName().equals("at"))
			_at = period;

		_period = period;    	
	}


	public Element getPeriod() {
		return _period;
	}


	public String getBegin() {
		return Utils.getTime(getBeginHours(), getBeginMinutes(), getBeginSeconds(), false);
	}


	public String getEnd() {
		return Utils.getTime(getEndHours(), getEndMinutes(), getEndSeconds(), false);
	}


	public String getRepeat() {
		return Utils.getTime(getRepeatHours(), getRepeatMinutes(), getRepeatSeconds(), true);
	}


	public String getSingle() {
		return Utils.getTime(getSingleHours(), getSingleMinutes(), getSingleSeconds(), false);
	}


	public String getBeginHours() {
		return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("begin"), -999));
	}

		public void setPeriodTime(int maxHour, Button bApply, String node, String hours, String minutes, String seconds) {
			maxHour=24;

		if (_period != null ){
			if (node.equals("single_start") && _period.getName().equals("at")) {
				Utils.setAttribute("at", Utils.getAttributeValue("at", _period).substring(0, 10) + " " + Utils.getTime(maxHour, hours, minutes, seconds, false), _period, _dom);
			} else if (!node.equals("single_start") && _period.getName().equals("at") && Utils.getAttributeValue("at", _period).length() < 11) {    			
				//getDatePeriod();
				if(_period.getParentElement() != null) {
					java.util.List rt = _period.getParentElement().getChildren("date");
					String sDate = Utils.getAttributeValue("at", _period).substring(0, 10);
					for(int i = 0; i < rt.size(); i++) {    	    			
						Element el = (Element)rt.get(i);

						if(Utils.getAttributeValue("date", el).equals(sDate)){
							removeAtElement(Utils.getAttributeValue("at", _period));
							_period = new Element("period");
							el.addContent(_period);
							Utils.setAttribute(node, Utils.getTime(maxHour, hours, minutes, seconds, false), _period, _dom);		
							_dom.setChanged(true);
							if(el.getParentElement() != null) {
								//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",el.getParentElement()), SchedulerDom.MODIFY);								
								Element parent = Utils.getRunTimeParentElement(el);
								String name = parent.getName().equals("order") || parent.getName().equals("order") ? 
										       Utils.getAttributeValue("job_chain",parent)+","+Utils.getAttributeValue("id",parent) :
										    	   Utils.getAttributeValue("name",parent);
										       
								_dom.setChangedForDirectory(parent.getName(), name, SchedulerDom.MODIFY);
							}
								
							break;    	    				
						}
					}
				}

			} else {
				//Utils.setAttribute(node, Utils.getTime(maxHour, hours, minutes, seconds, false), _period, _dom);
				
				Utils.setAttribute(node, Utils.getTime(maxHour, hours, minutes, seconds, false), _period);
				Element parent = Utils.getRunTimeParentElement(_period);
				String name = parent.getName().equals("order") || parent.getName().equals("add_order") ? 
						       Utils.getAttributeValue("job_chain",parent)+","+Utils.getAttributeValue("id",parent) :
						    	   Utils.getAttributeValue("name",parent);
			    _dom.setChanged(true);
				_dom.setChangedForDirectory(parent.getName(), name, SchedulerDom.MODIFY);
			}
			if (bApply != null) {
				bApply.setEnabled(true);
			}
		}     
	}



	public void setRepeatSeconds(Button bApply, String seconds) {
		Utils.setAttribute("repeat", seconds, _period, _dom);
		if (bApply != null) {
			bApply.setEnabled(true);
		}
	}


	public String getBeginMinutes() {
		return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("begin"), -999));
	}


	public String getBeginSeconds() {
		return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("begin"), -999));
	}


	public String getEndHours() {
		return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("end"), -999));
	}




	public String getEndMinutes() {
		return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("end"), -999));
	}


	public String getEndSeconds() {
		return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("end"), -999));
	}


	public String getRepeatHours() {
		return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("repeat"), -999));
	}


	public String getRepeatMinutes() {
		return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("repeat"), -999));
	}


	public String getRepeatSeconds() {
		return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("repeat"), -999));
	}


	public String getAbsoluteRepeatHours() {
		return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("absolute_repeat"), -999));
	}

	public String getAbsoluteRepeatMinutes() {
		return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("absolute_repeat"), -999));
	}

	public String getAbsoluteRepeatSeconds() {
		return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("absolute_repeat"), -999));
	}


	public String getSingleHours() {
		if(_period!=null && _period.getName().equals("at")) 
			return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("at") != null ? _period.getAttributeValue("at").split(" ")[1] : "0", -999));
		else
			return Utils.getIntegerAsString(Utils.getHours(_period.getAttributeValue("single_start"), -999));
	}


	public String getSingleMinutes() {
		if(_period!=null && _period.getName().equals("at"))
			return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("at") != null? _period.getAttributeValue("at").split(" ")[1]:"0", -999));
		else
			return Utils.getIntegerAsString(Utils.getMinutes(_period.getAttributeValue("single_start"), -999));
	}


	public String getSingleSeconds() {
		if(_period!=null && _period.getName().equals("at"))
			return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("at") != null ? _period.getAttributeValue("at").split(" ")[1] :"0", -999));
		else
			return Utils.getIntegerAsString(Utils.getSeconds(_period.getAttributeValue("single_start"), -999));
	}


	public boolean getLetRun() {
		String letrun = _period.getAttributeValue("let_run");
		return letrun == null ? false : letrun.equalsIgnoreCase("yes");
	}


	public void setLetRun(boolean letrun) {

		Utils.setAttribute("let_run", letrun ? "yes" : "no", "no", _period);
		_dom.setChanged(true);
	}


	public boolean getRunOnce() {
		return Utils.isAttributeValue("once", _period);
	}


	public void setRunOnce(boolean once) {
		Utils.setAttribute("once", once, false, _period);
		_dom.setChanged(true);
	}

	public void setAtElement(Element at) {    	    	
		_period=null;
		_at = at;	
	}

	public Element getAtElement() {
		return _at;
	}

	private void removeAtElement(String date) {
		if(_period.getName().equals("at") && _period.getParentElement() != null) {
			java.util.List rt = _period.getParentElement().getChildren("at");
			for(int i = 0; i < rt.size(); i++) {
				Element el = (Element)rt.get(i);
				if(Utils.getAttributeValue("at", el).equals(date)) {
					rt.remove(i);
					return;
				}

			}
		}

	}
	
	public String[] getWhenHolidays() {
		return _whenHolidays;
	}

	public String getWhenHoliday() {
		if(Utils.getAttributeValue("when_holiday", _period).length() == 0)	
            return "suppress execution";
		
		if(_realNameWhenHolidays.get(Utils.getAttributeValue("when_holiday", _period)) == null)
			return Utils.getAttributeValue("when_holiday", _period);
		else
			return _realNameWhenHolidays.get(Utils.getAttributeValue("when_holiday", _period)).toString();
	}

	public void setWhenHoliday(String whenHoliday, Button bApply) {
		//Utils.getAttributeValue("job_chain",_order)+","+Utils.getAttributeValue("id",_order)
		Element parent = Utils.getRunTimeParentElement(_period);
		String name = parent.getName().equals("order") || parent.getName().equals("order") ? 
				       Utils.getAttributeValue("job_chain",parent)+","+Utils.getAttributeValue("id",parent) :
				    	   Utils.getAttributeValue("name",parent);
		//_dom.setChangedForDirectory("job", name, SchedulerDom.MODIFY);
				       _dom.setChangedForDirectory(parent.getName(), name, SchedulerDom.MODIFY);
		
		if(whenHoliday == null || whenHoliday.length() == 0) {			
			Utils.setAttribute("when_holiday", "suppress", "suppress", _period);
			return;
		}
		
		if(_realNameWhenHolidays.get(whenHoliday) == null || _realNameWhenHolidays.get(whenHoliday).toString().length() == 0) {
			Utils.setAttribute("when_holiday", whenHoliday, "suppress", _period);
		}
			
		Utils.setAttribute("when_holiday", _realNameWhenHolidays.get(whenHoliday).toString(), "suppress", _period);  
		
		if(bApply != null)
			bApply.setEnabled(true);
		else 
			_dom.setChanged(true);
	}


	public SchedulerDom get_dom() {
		return _dom;
	}
	

	public void clearNONSingleStartAttributes() {
		 
		if(_period != null) {
			_period.removeAttribute("absolute_repeat");
			_period.removeAttribute("begin");
			_period.removeAttribute("end");
			_period.removeAttribute("repeat");
			
		}
	}
	
	public void clearSingleStartAttributes() {
		 
		if(_period != null) {
			_period.removeAttribute("single_start");			
		}
	}
	
}

