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
import sos.scheduler.editor.conf.SchedulerDom;

public class HttpDirectoriesListener {


	private      SchedulerDom    _dom            = null;

	private      Element         _config         = null;

	private      Element         _http_server    = null;

	private      List            _httpDirectory  = new ArrayList();


	public HttpDirectoriesListener(SchedulerDom dom, Element config) {

		_dom = dom;
		_config = config;
		initDirectories();

	}

	private void initDirectories() {

		_http_server = _config.getChild("http_server");
		if (_http_server != null) {
			_httpDirectory = _http_server.getChildren("http_directory");
		}

	}

	public void fillHttpDirectoryTable(Table table) {

		table.removeAll();   
		for (Iterator it = _httpDirectory.iterator(); it.hasNext();) {
			Element http_directory = (Element) it.next();
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, Utils.getAttributeValue("url_path", http_directory));
			item.setText(1, Utils.getAttributeValue("path", http_directory));
		}
	}

	public void applyHttpDirectory(TableItem[] httpDirectories) {

		if (_http_server != null) _http_server.removeChildren("http_directory");

		if (httpDirectories.length > 0) {
			if (_http_server == null && _config.getAttribute("http_server") == null) {
				_http_server = new Element("http_server");
				_config.addContent(_http_server);
			}

			for (int i = 0; i < httpDirectories.length; i++) {
				Element directory = new Element("http_directory");
				Utils.setAttribute("url_path", httpDirectories[i].getText(0), directory);
				Utils.setAttribute("path", httpDirectories[i].getText(1), directory);
				if (httpDirectories[i].getText(0) != "") {
					_http_server.addContent(directory);
				}
			}
			_dom.setChanged(true);
		}
	}
	public void fillHttpDirectory(Table table) {
		table.removeAll();
		if (_http_server != null) {
			for (Iterator it = _http_server.getChildren("http_directory").iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				TableItem directory = new TableItem(table, SWT.NONE);
				directory.setText(0, Utils.getAttributeValue("url_path", e));
				directory.setText(1, Utils.getAttributeValue("path", e));
			}
		}
	}


}
