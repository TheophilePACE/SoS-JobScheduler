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

public class SettingListener {
    private DocumentationDom      _dom;

    private Element               _parent;

    private Element               _setting;

    private boolean               _newSetting;

    private static final String[] _types = { "", "integer", "double", "float", "string", "boolean", "clob", "blob" };


    public SettingListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
    }


    public void fillSettings(Table table) {
        table.removeAll();
        int index = 0;
        for (Iterator it = _parent.getChildren("setting", _dom.getNamespace()).iterator(); it.hasNext();) {
            Element setting = (Element) it.next();
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, Utils.getAttributeValue("name", setting));
            item.setText(1, Utils.getAttributeValue("default_value", setting));
            item.setText(2, Utils.getAttributeValue("type", setting));
            item.setText(3, Utils.getBooleanValue("required", setting) ? "yes" : "no");
            item.setText(4, Utils.getAttributeValue("reference", setting));
            item.setText(5, Utils.getAttributeValue("id", setting));
            if (setting.equals(_setting))
                table.select(index);
            index++;
        }
    }


    public void setNewSetting() {
        _setting = new Element("setting", _dom.getNamespace());
        _newSetting = true;
    }


    public boolean selectSetting(int index) {
        try {
            _setting = (Element) _parent.getChildren("setting", _dom.getNamespace()).get(index);
            _newSetting = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getName() {
        return Utils.getAttributeValue("name", _setting);
    }


    public String getDefaultValue() {
        return Utils.getAttributeValue("default_value", _setting);
    }


    public String getID() {
        return Utils.getAttributeValue("id", _setting);
    }


    public boolean isRequired() {
        return Utils.getBooleanValue("required", _setting);
    }


    public String getReference() {
        return Utils.getAttributeValue("reference", _setting);
    }


    public String getType() {
        return Utils.getAttributeValue("type", _setting);
    }


    public String[] getTypes() {
        return _types;
    }


    public Element getSettingElement() {
        return _setting;
    }


    public boolean isNewSetting() {
        return _newSetting;
    }


    public String[] getReferences(String ownID) {
        return DocumentationListener.getIDs(_dom, ownID);
    }


    public void applySetting(String name, String defaultValue, String id, boolean required, String reference,
            String type) {
        Utils.setAttribute("name", name, _setting);
        Utils.setAttribute("default_value", defaultValue, _setting);
        Utils.setAttribute("id", id, _setting);
        Utils.setBoolean("required", required, _setting);
        Utils.setAttribute("reference", DocumentationListener.getID(reference), _setting);
        Utils.setAttribute("type", type, _setting);
        if (_newSetting)
            _parent.addContent(_setting);
        _newSetting = false;
        _dom.setChanged(true);
    }


    public boolean removeSetting(int index) {
        if (selectSetting(index)) {
            _setting.detach();
            _setting = null;
            _newSetting = false;
            _dom.setChanged(true);
            return true;
        } else
            return false;
    }
}
