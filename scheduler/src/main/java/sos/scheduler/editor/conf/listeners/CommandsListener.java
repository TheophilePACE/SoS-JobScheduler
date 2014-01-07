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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import sos.scheduler.editor.conf.ISchedulerUpdate;
import sos.scheduler.editor.conf.SchedulerDom;

public class CommandsListener {

	private SchedulerDom     _dom;

	private Element          _config;

	private ISchedulerUpdate _main;

	private Element          _commands;


	public CommandsListener(SchedulerDom dom, ISchedulerUpdate update) {
		_dom = dom;
		_config = _dom.getRoot().getChild("config");
		_commands = _config.getChild("commands");
		_main = update;
	}


	public String readCommands() throws Exception {
		String xml = "";
		if (_commands != null) {
			try {
				//Iterator it = _commands.getChildren().iterator();
				java.util.List l = _commands.getChildren();
				for(int i = 0; i < l.size(); i++) {
					Element e = (Element) l.get(i);
					String s = _dom.getXML(e);
					xml += s.substring(45);
				}
				/* while (it.hasNext()) {
                    Element e = (Element) it.next();
                    String s = _dom.getXML(e);
                    xml += s.substring(45);
                }*/
			} catch (JDOMException ex) {
				try {
					new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , ex);
				} catch(Exception ee) {
					//tu nichts
				}
				throw new Exception("error in " + sos.util.SOSClassUtil.getMethodName() + " : " + ex.getMessage());

			}
		}
		return xml;
	}


	public Element getCommands() {
		return _commands;
	}


	public void saveCommands(String commands) {
		_config.removeChild("commands");
		ByteArrayInputStream bai;
		try {
			String s = "<commands>" + commands + "</commands>";
			bai = new ByteArrayInputStream(s.getBytes("UTF-8"));
			SAXBuilder builder = new SAXBuilder(false);
			Document doc;

			doc = builder.build(bai);
			Element r = doc.getRootElement();
			r.detach();
			_config.addContent(r);
			_dom.setChanged(true);
			_main.updateOrders();
		} catch (JDOMException e1) {
			try {
				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , e1);
			} catch(Exception ee) {
				//tu nichts
			}

			e1.printStackTrace();
		} catch (IOException e1) {
			try {
				new sos.scheduler.editor.app.ErrorLog("error in " + sos.util.SOSClassUtil.getMethodName() , e1);
			} catch(Exception ee) {
				//tu nichts
			}

			e1.printStackTrace();
		}

	}

}
