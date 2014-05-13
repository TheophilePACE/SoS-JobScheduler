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
package sos.scheduler.editor.doc.listeners;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class ParamsListener {
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _params;

    private Element          _param;

    private boolean          _newParam;


    public ParamsListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
        _params = _parent.getChild("params", _dom.getNamespace());
    }


    private void setParams() {
        if (_params == null) {
            _params = new Element("params", _dom.getNamespace());
            _parent.addContent(_params);
        }
    }


    public void checkParams() {
        if (_params != null && getID().length() == 0 && getReference().length() == 0
                && _params.getChildren().size() == 0) {
            _params.detach();
            _params = null;
        }
    }


    public String getID() {
        return Utils.getAttributeValue("id", _params);
    }


    public void setID(String id) {
        setParams();
        Utils.setAttribute("id", id, _params, _dom);
        checkParams();
    }


    public String[] getReferences(String ownID) {
        return DocumentationListener.getIDs(_dom, ownID);
    }


    public String getReference() {
        return Utils.getAttributeValue("reference", _params);
    }


    public void setReference(String reference) {
        setParams();
        Utils.setAttribute("reference", DocumentationListener.getID(reference), _params, _dom);
        checkParams();
    }


    public Element getParamsElement() {
        setParams();
        return _params;
    }


    public void fillParams(Table table) {
        table.removeAll();
        if (_params != null) {
            int index = 0;
            for (Iterator it = _params.getChildren("param", _dom.getNamespace()).iterator(); it.hasNext();) {
                Element param = (Element) it.next();
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, Utils.getAttributeValue("name", param));
                item.setText(1, Utils.getAttributeValue("default_value", param));
                item.setText(2, Utils.getBooleanValue("required", param) ? "yes" : "no");
                item.setText(3, Utils.getAttributeValue("reference", param));
                item.setText(4, Utils.getAttributeValue("id", param));
                if (param.equals(_param))
                    table.select(index);
                index++;
            }
        }
    }


    public void setNewParam() {
        _param = new Element("param", _dom.getNamespace());
        _newParam = true;
    }


    public boolean selectParam(int index) {
        try {
            _param = (Element) _params.getChildren("param", _dom.getNamespace()).get(index);
            _newParam = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getParamName() {
        return Utils.getAttributeValue("name", _param);
    }


    public String getParamDefaultValue() {
        return Utils.getAttributeValue("default_value", _param);
    }


    public String getParamID() {
        return Utils.getAttributeValue("id", _param);
    }


    public boolean getParamRequired() {
        return Utils.getBooleanValue("required", _param);
    }


    public String getParamReference() {
        return Utils.getAttributeValue("reference", _param);
    }


    public Element getParamElement() {
        return _param;
    }


    public void applyParam(String name, String defaultValue, String id, String reference, boolean required) {
        setParams();
        Utils.setAttribute("name", name, _param);
        Utils.setAttribute("default_value", defaultValue, _param);
        Utils.setAttribute("id", id, _param);
        Utils.setAttribute("reference", DocumentationListener.getID(reference), _param);
        Utils.setBoolean("required", required, _param);
        
        if(!_params.getContent().contains(_param))
        	_params.addContent(_param);
        
        _newParam = false;
        checkParams();
        _dom.setChanged(true);
    }


    public void removeParam(int index) {
        if (selectParam(index)) {
            _param.detach();
            _param = null;
            _dom.setChanged(true);
            checkParams();
        }
    }


    public boolean isNewParam() {
        return _newParam;
    }
}
