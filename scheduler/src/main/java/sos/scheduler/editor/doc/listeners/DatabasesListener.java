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
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class DatabasesListener {
    private static final String _defaultName = "[unknown]";

    private DocumentationDom    _dom;

    private Element             _parent;

    private Element             _resources;

    private Element             _database;

    private Element             _resource;

    private boolean             _newDatabase;

    private boolean             _newResource;

    private String[]            _types       = { "table", "view", "trigger", "procedure", "function", "java", "index",
            "other"                         };


    public DatabasesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
        _resources = _parent.getChild("resources", _dom.getNamespace());
    }
 

    public void fillDatabases(Table table) {
        table.removeAll();
        int index = 0;
        if (_resources != null) {
            for (Iterator it = _resources.getChildren("database", _dom.getNamespace()).iterator(); it.hasNext();) {
                Element database = (Element) it.next();
                TableItem item = new TableItem(table, SWT.NONE);
                String name = Utils.getAttributeValue("name", database);
                item.setText(0, !name.equals("") ? name : _defaultName);
                item.setText(1, Utils.getBooleanValue("required", database) ? "yes" : "no");
                if (database.equals(_database))
                    table.select(index);
                index++;
            }
        }
    }


    public void setNewDatabase() {
        _database = new Element("database", _dom.getNamespace());
        Utils.setAttribute("required", false, _database);
        _newDatabase = true;
    }


    public boolean setDatabase(int index) {
        if (_resources != null) {
            List dbs = _resources.getChildren("database", _dom.getNamespace());
            try {
                _database = (Element) dbs.get(index);
                _newDatabase = false;
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            return false;
    }


    public String getDBName() {
        return Utils.getAttributeValue("name", _database);
    }


    public boolean isRequired() {
        return Utils.getBooleanValue("required", _database);
    }


    public void applyDatabase(String dbName, boolean required) {
        if (_resources == null)
            _resources = ResourcesListener.getResourcesElement(_dom, _parent);

        Utils.setAttribute("name", dbName, _database);
        Utils.setBoolean("required", required, _database);

        if (_newDatabase)
            _resources.addContent(_database);

        _newDatabase = false;
        _dom.setChanged(true);
    }


    public void removeDatabase(int index) {
        if (setDatabase(index)) {
            _database.detach();
            _database = null;
            _dom.setChanged(true);
            ResourcesListener.checkResources(_dom, _parent);
        }
    }


    public void fillResources(Table table) {
        table.removeAll();
        int index = 0;
        if (_database != null) {
            for (Iterator it = _database.getChildren("resource", _dom.getNamespace()).iterator(); it.hasNext();) {
                Element resource = (Element) it.next();
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, Utils.getAttributeValue("name", resource));
                item.setText(1, Utils.getAttributeValue("type", resource));
                if (resource.equals(_resource))
                    table.select(index);
                index++;
            }
        }
    }


    public void setNewResource() {
        _resource = new Element("resource", _dom.getNamespace());
        Utils.setAttribute("type", _types[0], _resource);
        _newResource = true;
    }


    public boolean setResource(int index) {
        if (_database != null) {
            List dbs = _database.getChildren("resource", _dom.getNamespace());
            try {
                _resource = (Element) dbs.get(index);
                _newResource = false;
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            return false;
    }


    public Element getResource() {
        return _resource;
    }


    public String getName() {
        return Utils.getAttributeValue("name", _resource);
    }


    public String getType() {
        return Utils.getAttributeValue("type", _resource);
    }


    public void applyResource(String name, String type) {
        Utils.setAttribute("name", name, _resource);
        Utils.setAttribute("type", type, _resource);

        if (_newResource)
            _database.addContent(_resource);
        else
            _dom.setChanged(true);

        _newResource = false;
    }


    public void removeResource(int index) {
        if (setResource(index)) {
            _resource.detach();
            _resource = null;
            _dom.setChanged(true);
        }
    }


    public String[] getTypes() {
        return _types;
    }


    public boolean isNewResource() {
        return _newResource;
    }


    public boolean isNewDatabase() {
        return _newDatabase;
    }
}
