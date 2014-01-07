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

import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;

public class RunTimeListener {


	private       SchedulerDom    _dom        = null;

	private       Element         _job        = null;

	private       Element         _runtime    = null;

	//private       ISchedulerUpdate _gui        = null;

	public RunTimeListener(SchedulerDom dom, Element job, ISchedulerUpdate gui) {
		_dom = dom;
		//_gui = gui;
		_job = job;
		_runtime = _job.getChild("run_time");
		checkRuntime();
	}


	public boolean isOnOrder() {
		/*if(_job.getName().equals("order"))
    		return true;
		 */
		return Utils.isAttributeValue("order", _job);
	}


	public String getComment() {
		return Utils.getAttributeValue("__comment__", _runtime);
	}


	public void setComment(String comment) {
		Utils.setAttribute("__comment__", comment, _runtime, _dom);
	}


	private void setRuntime() {
		if (_job.getChild("run_time") == null) {
			_runtime = new Element("run_time");
			_job.addContent(_runtime);
		}
	}


	public Element getRunTime() {
		if (_runtime == null)
			setRuntime();
		return _runtime;
	}

	public void setFunction(String function_name) {
		if (_runtime != null) {

			Utils.setAttribute("start_time_function",function_name, _runtime, _dom);
			//_gui.updateRunTime();
			//_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_job), SchedulerDom.MODIFY);
			if(_dom.isDirectory() || _dom.isLifeElement())
				_dom.setChangedForDirectory(_job, SchedulerDom.MODIFY);
		}
	}

	public String getFunction() {
		String s = _runtime.getAttributeValue("start_time_function");
		if (s==null)s="";
		return s;
	}

	public String[] getSchedules() {
		String[] retval = new String[0];
		if (!_dom.isLifeElement() && _dom.getRoot() != null) {
			if(_dom.getRoot().getChild("config") != null && _dom.getRoot().getChild("config").getChild("schedules")!= null) {
				java.util.List l = _dom.getRoot().getChild("config").getChild("schedules").getChildren("schedule");
				retval = new String[l.size()];
				for(int i = 0; i < l.size(); i++){
					Element e = (Element)l.get(i);
					retval[i] = Utils.getAttributeValue("name", e);
				}
			}
		}
		return retval;
	}

	public String getSchedule() {
		return Utils.getAttributeValue("schedule", _runtime);
	}


	public void setSchedule(String schedule) {
		if(_runtime != null) {
			Utils.setAttribute("schedule", schedule, _runtime, _dom);
			//_gui.updateRunTime();
			if(_dom.isDirectory() || _dom.isLifeElement())
				_dom.setChangedForDirectory(_job, SchedulerDom.MODIFY);
		}
	}

	/*
    Runtime darf Starttime nicht im Attribut haben.
    Der Starttime darf nur in Perioden definiert werden.
	   Wenn ein run_time Element bereits definitionen aus der Starttime hat (repeat_time und single_time und absolute_repeat),
    dann werden diese Attribute in every-Day gemoved.
	 */
	private void checkRuntime() {

		//repeat ins every_day moven
		String repeat = Utils.getAttributeValue("repeat", _runtime);
		if(repeat.length() > 0) {
			Element p = new Element("period");
			p.setAttribute("repeat", repeat);
			_runtime.addContent(p);
			_runtime.removeAttribute("repeat");
		}

		//single_start ins every_day moven
		String single_start = Utils.getAttributeValue("single_start", _runtime);
		if(single_start.length() > 0) {
			Element p = new Element("period");
			p.setAttribute("single_start", single_start);
			_runtime.addContent(p);
			_runtime.removeAttribute("single_start");
		}
	}

	public Element getParent() {
		return _job;
	}
}
