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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jdom.Element;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;


public class ScheduleListener { 
	
	
	private    SchedulerDom     _dom                   = null;
	
	private    ISchedulerUpdate _main                  = null;
	
	private    Element          _schedule              = null;
	
    private    Date validFrom;
    private    Date validTo;
	
	
	public ScheduleListener(SchedulerDom dom, ISchedulerUpdate update, Element schedule) {
		
		_dom = dom;
		_main = update;
		_schedule = schedule;
		
	}

	public String getName() {
		return Utils.getAttributeValue("name", _schedule);
	}

	public void setName(String _name) {
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.DELETE);
		Utils.setAttribute("name", _name, _schedule);
		
		_main.updateTreeItem(_name);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);	
		
		
	}

	public Element getSchedule() {
		return _schedule;
	}
	
	public void setTitle(String title) {		
		Utils.setAttribute("title", title, _schedule);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);	
		
	}
	
	public String getTitle() {
		return Utils.getAttributeValue("title", _schedule);
	}

	
	public void setValidFrom(String validFrom) {
		
		Utils.setAttribute("valid_from", validFrom, _schedule);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);	
		
	}
	
	public String getValidFrom() throws ParseException  {
		String sDate = Utils.getAttributeValue("valid_from", _schedule);
        return sDate;
   }
	
	   
	private void clearValid() {
	    Utils.setAttribute("valid_from", null, _schedule);
	    Utils.setAttribute("valid_to", null, _schedule);
       _dom.setChanged(true);
       _dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);   
	}
	
	public void setValidTo(String validTo) throws ParseException {
	    
		Utils.setAttribute("valid_to", validTo, _schedule);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);	
		
	}
	
	public String getValidTo() throws ParseException {
        String sDate = Utils.getAttributeValue("valid_to", _schedule);
        return sDate;	
    }

	
	public void setSubstitut(String substitute) {		
	    if (substitute.trim().equals("")){
	        clearValid();
	    }
		Utils.setAttribute("substitute", substitute, _schedule);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("schedule", Utils.getAttributeValue("name", _schedule), SchedulerDom.MODIFY);	
		
	}
	
	public String getSubstitute() {
		return Utils.getAttributeValue("substitute", _schedule);
	}

	public String[] getAllSchedules() {
		
		java.util.List s = null;
		 
		if(_dom.isLifeElement() || _schedule == null) {
			return null;
		}
				
		/*Element schedules = _schedule.getParentElement();
		if(schedules == null)
			return null;
		*/
		
		String currSchedulename = Utils.getAttributeValue("name",_schedule);
		Element schedules = null;
		if(_schedule.getParentElement() != null)
			schedules = (Element)_schedule.getParentElement().clone();
		else 
			return null;
		
		s = schedules.getChildren("schedule");		
		
		java.util.ArrayList list = new java.util.ArrayList();
		list.add("");
		//int index = 0;
		for(int i = 0; i < s.size(); i++) {
			if(s.get(i) instanceof Element) {
				Element e = (Element)s.get(i);
				if(!Utils.getAttributeValue("name", e).equals(currSchedulename)) {
					list.add(Utils.getAttributeValue("name", e));
					//index++;
				}
			}
		}
				
		String[] str = new String[list.size()];
 		for(int i = 0; i < list.size(); i++) {
			str[i] = (String)list.get(i);
		}
		//convert in String[]
		
		
		/*String[] str = new String[s.size()-1];
		int index = 0;
		for(int i = 0; i < s.size(); i++) {
			if(s.get(i) instanceof Element) {
				Element e = (Element)s.get(i);
				if(!Utils.getAttributeValue("name", e).equals(currSchedulename)) {
					str[index] = Utils.getAttributeValue("name", e);
					index++;
				}
			}
		}
			
				*/
		return str;
		
			
		
	}
}
