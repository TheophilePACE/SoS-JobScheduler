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
import sos.scheduler.editor.conf.SchedulerDom;

public class JobLockUseListener {


	private        SchedulerDom      _dom               = null;

	private        Element           _job               = null;

	private        List              _lockUseList       = null;

	private        Element           _lockUse           = null;


	public JobLockUseListener(SchedulerDom dom, Element job) {

		_dom = dom;
		_job = job;
		_lockUseList = _job.getChildren("lock.use");

	}

	public void fillLockUse(Table table) {

		table.removeAll();
		Iterator it = _lockUseList.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, Utils.getAttributeValue("lock", e));
			item.setText(1, Utils.getAttributeValue("exclusive", e));
		}

	}


	public void newLockUse() {
		_lockUse = new Element("lock.use");    	
	}


	public void selectLockUse(int index) {
		if (index >= 0 && index < _lockUseList.size())
			_lockUse = (Element) _lockUseList.get(index);
	}


	public void applyLockUse(String lockUse, boolean exclusive) {
		if (_lockUse == null)newLockUse();
		Utils.setAttribute("lock", lockUse, _lockUse, _dom);
		Utils.setAttribute("exclusive", exclusive, _lockUse, _dom);
		if (!_lockUseList.contains(_lockUse))
			_lockUseList.add(_lockUse);
		_dom.setChanged(true);
		_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_job), SchedulerDom.MODIFY);
	}


	public void deleteLockUse(int index) {
		if (index >= 0 && index < _lockUseList.size()) {
			_lockUseList.remove(index);
			_lockUse = null;
			_dom.setChanged(true);
			_dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_job), SchedulerDom.MODIFY);
		}
	}


	public String getLockUse() {
		return Utils.getAttributeValue("lock", _lockUse);
	}


	public boolean getExclusive() {
		if (Utils.getAttributeValue("exclusive", _lockUse) == null || Utils.getAttributeValue("exclusive", _lockUse).length()==0) {
			return true;
		}else {
			return (Utils.getAttributeValue("exclusive", _lockUse).equals("yes"));
		}
	}

	public boolean isValidLock(String lock) {
		if (lock.equals("")) return false;
		if (_lockUseList != null) {
			for (Iterator it = _lockUseList.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				if (Utils.getAttributeValue("lock", e).equals(lock))
					return false;
			}
		}
		return true;
	}

	public String[] getLocks() {
		String[] names = null;
		if(_dom.isLifeElement()) { 
			names = new String[0];
			return names;
		}
		Element locks = _dom.getRoot().getChild("config").getChild("locks");
		if (locks != null) {
			List list = locks.getChildren("lock");
			names = new String[list.size()];
			int i = 0;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o instanceof Element) {
					String name = ((Element) o).getAttributeValue("name");
					if (name == null)
						name = "";
					names[i++] = name;
				}
			}
		}
		return names;
	}

	public Element getJob() {
		return _job;
	}
}
