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

public class WebserviceListener {

	private SchedulerDom _dom           = null;

	private Element           _service       = null;

	private String[]           _chains       = new String[0];

	private ISchedulerUpdate   _main          = null;

	

	public WebserviceListener(SchedulerDom dom, Element webService, ISchedulerUpdate main) {

		_dom = dom;
		_service = webService;
		_main = main;

	}

	
	public boolean getDebug() {
		return Utils.isAttributeValue("debug", _service);
	}


	public String getForwardXSLT() {
		return Utils.getAttributeValue("forward_xslt_stylesheet", _service);
	}


	public String getRequestXSLT() {
		return Utils.getAttributeValue("request_xslt_stylesheet", _service);
	}


	public String getResponseXSLT() {
		return Utils.getAttributeValue("response_xslt_stylesheet", _service);
	}


	public String getJobChain() {
		return Utils.getAttributeValue("job_chain", _service);
	}


	public String getName() {
		return Utils.getAttributeValue("name", _service);
	}


	public String getTimeout() {
		return Utils.getAttributeValue("timeout", _service);
	}


	public String getURL() {
		return Utils.getAttributeValue("url_path", _service);
	}




	public void applyService(boolean debug, String chain, String name, String forward, String request, String response,
			String timeout, String url) {
		
		Utils.setAttribute("debug", debug, _service, _dom);
		Utils.setAttribute("job_chain", chain, _service, _dom);
		Utils.setAttribute("name", name, _service, _dom);
		Utils.setAttribute("forward_xslt_stylesheet", forward, _service, _dom);
		Utils.setAttribute("request_xslt_stylesheet", request, _service, _dom);
		Utils.setAttribute("response_xslt_stylesheet", response, _service, _dom);
		Utils.setAttribute("timeout", timeout, _service, _dom);
		Utils.setAttribute("url_path", url, _service, _dom);

		_main.updateTreeItem("Web Service: " +name);
		_dom.setChanged(true);
	}




	public String[] getJobChains() {
		if(_service == null || 
				_service.getParentElement() == null ||
				_service.getParentElement().getParentElement() == null	
		)
			return new String[0];
		
		Element _config = _service.getParentElement().getParentElement(); 
		Element element = _config.getChild("job_chains");
		if (element != null) {
			List chains = element.getChildren("job_chain");
			_chains = new String[chains.size()];
			int index = 0;
			Iterator it = chains.iterator();
			while (it.hasNext()) {
				String name = ((Element) it.next()).getAttributeValue("name");
				_chains[index++] = name != null ? name : "";
			}
		} else
			_chains = new String[0];

		return _chains;
	}


	public int getChainIndex(String jobChain) {
		for (int i = 0; i < _chains.length; i++) {
			if (_chains[i].equals(jobChain))
				return i;
		}
		return -1;
	}


	public void fillParams(Table table) {
		table.removeAll();
		if (_service != null) {
			Element params = _service.getChild("params");
			if (params != null) {
				for (Iterator it = params.getChildren("param").iterator(); it.hasNext();) {
					Element e = (Element) it.next();
					TableItem param = new TableItem(table, SWT.NONE);
					param.setText(0, Utils.getAttributeValue("name", e));
					param.setText(1, Utils.getAttributeValue("value", e));
				}
			}
		}
	}


	public boolean isValid(String name) {
		
		List _list = _service.getParentElement().getChildren("web_service");
		if (_list != null) {
			for (Iterator it = _list.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				if (Utils.getAttributeValue("name", e).equals(name))
					return false;
			}
		}
		return true;
	}
	
}
