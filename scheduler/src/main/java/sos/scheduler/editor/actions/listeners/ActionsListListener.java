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
package sos.scheduler.editor.actions.listeners;


import org.jdom.Element;
import sos.scheduler.editor.actions.ActionsDom;
import sos.scheduler.editor.app.Utils;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

public class ActionsListListener {
   
	private ActionsDom           _dom                 = null;

    private Element              _actions             = null;

   
    public ActionsListListener(ActionsDom dom, Element actions) {
        _dom = dom;
        _actions = actions;
    }
   
    public String getActions() {
        return Utils.getAttributeValue("name", _actions);
    }
    
    public void newAction(String name) {
    	if(_actions == null)
    		_actions = _dom.getRoot();
    	
    	Element action = new Element("action");
    	Utils.setAttribute("name", name, action);
    	
    	_actions.addContent(action);
    	_dom.setChanged(true);
    }


    public void fillActions(Table table) {  
    	
    	if(_actions == null)
    		_actions = _dom.getRoot();
    	
        if(table != null) {
        	table.removeAll();
        	
        	java.util.List l = _actions.getChildren("action");
        	for(int i = 0; i < l.size(); i++) {
        		Element action = (Element)l.get(i);
        		TableItem item = new TableItem(table, SWT.NONE);        		
        		item.setText(Utils.getAttributeValue("name", action));
        		item.setData(action);
        		
        	}
        }
    }

    public void removeAction(Table table) {
    	if(table.getSelectionCount() > 0) {
    		TableItem item = table.getSelection()[0];
    		Element e = (Element) item.getData();
    		e.detach();
    		fillActions(table);
    		_dom.setChanged(true);
    	}
    }
   

}
