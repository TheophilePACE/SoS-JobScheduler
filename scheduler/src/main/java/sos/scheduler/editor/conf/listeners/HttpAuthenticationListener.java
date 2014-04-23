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
import sos.scheduler.editor.conf.SchedulerDom;

public class HttpAuthenticationListener {


	private     SchedulerDom    _dom              = null;

	private     Element         _config           = null;

	private     Element         _http_server      = null;

	private     List            _httpUsers        = new ArrayList();


	public HttpAuthenticationListener(SchedulerDom dom, Element config) {

		_dom = dom;
		_config = config;
		initAuthentication();

	}

	private void initAuthentication() {

		_http_server = _config.getChild("http_server");
		if (_http_server != null) {
			Element httpAuthentication = _http_server.getChild("http.authentication");
			if(httpAuthentication != null) {
				Element httpUsers = httpAuthentication.getChild("http.users");
				if(httpUsers != null) {
					_httpUsers = httpUsers.getChildren("http.user");  
				}
			}    	      
		}

	}

	public void fillAuthentication(Table table) {

		table.removeAll();   
		for (Iterator it = _httpUsers.iterator(); it.hasNext();) {
			Element http_user = (Element) it.next();
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, Utils.getAttributeValue("url_path", http_user));
			item.setText(1, Utils.getAttributeValue("path", http_user));
		}
	}

	public void applyHttpUser(TableItem[] httpUser) {
		Element auth = null;
		Element users = null;
		if (_http_server != null) {
			if(_http_server.getChild("http.authentication") == null) {
				auth = new Element("http.authentication");
				_http_server.addContent(auth);
			} else {
				auth = _http_server.getChild("http.authentication");
			}    		
			if(auth != null) {
				if(auth.getChild("http.users") != null) {
					users = auth.getChild("http.users");
				} else {
					users = new Element("http.users");
					auth.addContent(users);
				}    			
			}   		
			users.removeChildren("http.user");
		}


		if (httpUser.length > 0) {
			if (_http_server == null && _config.getAttribute("http_server") == null) {
				_http_server = new Element("http_server");
				_config.addContent(_http_server);
				auth = new Element("http.authentication");
				_http_server.addContent(auth);
				users = new Element("http.users");
				auth.addContent(users);
			}


			for (int i = 0; i < httpUser.length; i++) {
				Element user = new Element("http.user");
				Utils.setAttribute("name", httpUser[i].getText(0), user);
				Utils.setAttribute("password_md5", httpUser[i].getText(1), user);
				if (httpUser[i].getText(0) != "") {
					users.addContent(user);
				}
			}
			_dom.setChanged(true);
		} else {
			_config.removeChild(_http_server.getName());
			_dom.setChanged(true);
		}
	}


	public void fillHttpAuthenticationTable(Table table) {
		table.removeAll();
		if (_httpUsers != null) {
			for (Iterator it = _httpUsers.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				TableItem user = new TableItem(table, SWT.NONE);
				user.setText(0, Utils.getAttributeValue("name", e));
				user.setText(1, Utils.getAttributeValue("password_md5", e));
			}
		}
	}


}
