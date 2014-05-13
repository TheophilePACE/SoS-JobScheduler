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
import org.jdom.JDOMException;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;

public class SecurityListener {
	
	
    private        SchedulerDom    _dom          = null;

    private        Element         _config       = null;

    private        Element         _security     = null;

    private        Element         _host         = null;

    private        List            _list         = null;

    private static String[]        _levels       = { "none", "info", "no_add", "all" };


    public SecurityListener(SchedulerDom dom, Element config) throws JDOMException {
    	    	
        _dom = dom;
        _config = config;

        _security = _config.getChild("security");
        if (_security != null)
            _list = _security.getChildren("allowed_host");
    }


    private void initSecurity() {
        if (_config.getChild("security") == null) {
            _security = new Element("security");
            _config.addContent(_security);
        } else {
            _security = _config.getChild("security");
        }
        _list = _security.getChildren("allowed_host");
    }


    public void fillTable(Table table) {
        table.removeAll();
        if (_list != null) {
            for (Iterator it = _list.iterator(); it.hasNext();) {
                Object o = it.next();
                if (o instanceof Element) {
                    Element e = (Element) o;
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(0, Utils.getAttributeValue("host", e));
                    item.setText(1, Utils.getAttributeValue("level", e));
                }
            }
        }
    }


    public void selectHost(int index) {
        if (_list != null && index >= 0 && index < _list.size())
            _host = (Element) _list.get(index);
        else
            _host = null;
    }


    public String getHost() {
        return Utils.getAttributeValue("host", _host);
    }


    public String getLevel() {
        return Utils.getAttributeValue("level", _host);
    }


    public boolean getIgnoreUnknownHosts() {
        return Utils.isAttributeValue("ignore_unknown_hosts", _security);
    }


    public void setIgnoreUnknownHosts(boolean ignore) {
        if (_list == null)
            initSecurity();

        Utils.setAttribute("ignore_unknown_hosts", ignore, _security, _dom);
    }


    public String[] getLevels() {
        return _levels;
    }


    public void newHost() {
        _host = new Element("allowed_host");
    }


    public void applyHost(String host, String level) {
        _host.setAttribute("host", host);
        _host.setAttribute("level", level);
        if (_list == null)
            initSecurity();
        if (!_list.contains(_host))
            _list.add(_host);
        _dom.setChanged(true);
    }


    public void removeHost(int index) {
        if (index >= 0 && index < _list.size()) {
            _list.remove(index);
            if (_list.size() == 0) {
                _config.removeChild("security");
                _security = null;
                _list = null;
            }
            _host = null;
            _dom.setChanged(true);
        }
    }


    public int getLevelIndex(String level) {
        for (int i = 0; i < _levels.length; i++) {
            if (level.equalsIgnoreCase(_levels[i]))
                return i;
        }
        return -1;
    }

}
