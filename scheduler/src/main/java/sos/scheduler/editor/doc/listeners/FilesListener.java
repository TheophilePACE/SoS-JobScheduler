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

public class FilesListener {
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _resources;

    private Element          _file;

    private String[]         _platforms = { "all", "win32", "win64", "linux32", "linux64", "solaris32", "solaris64",
            "hpux64"                   };

    private String[]         _types     = { "java", "binary", "other" };

    private boolean          _isNewFile = false;


    public FilesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
        _resources = _parent.getChild("resources", _dom.getNamespace());
    }


    public String[] getPlatforms() {
        return _platforms;
    }


    public String[] getTypes() {
        return _types;
    }


    public void fillFiles(Table table) {
        table.removeAll();
        int index = 0;
        if (_resources != null) {
            for (Iterator it = _resources.getChildren("file", _dom.getNamespace()).iterator(); it.hasNext();) {
                Element file = (Element) it.next();
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, Utils.getAttributeValue("file", file));
                item.setText(1, Utils.getAttributeValue("os", file));
                item.setText(2, Utils.getAttributeValue("type", file));
                item.setText(3, Utils.getAttributeValue("id", file));
                if (file.equals(_file))
                    table.select(index);
                index++;
            }
        }
    }


    public boolean selectFile(int index) {
        if (_resources != null) {
            try {
                _file = (Element) _resources.getChildren("file", _dom.getNamespace()).get(index);
                _isNewFile = false;
                return true;
            } catch (Exception e) {
                return false;
            }
        } else
            return false;
    }


    public void setNewFile() {
        _file = new Element("file", _dom.getNamespace());
        Utils.setAttribute("os", _platforms[0], _file);
        Utils.setAttribute("type", _types[0], _file);
        _isNewFile = true;
    }


    public String getFile() {
        return Utils.getAttributeValue("file", _file);
    }


    public String getID() {
        return Utils.getAttributeValue("id", _file);
    }


    public String getOS() {
        return Utils.getAttributeValue("os", _file);
    }


    public String getType() {
        return Utils.getAttributeValue("type", _file);
    }


    public void applyFile(String file, String id, String os, String type) {
        if (_resources == null)
            _resources = ResourcesListener.getResourcesElement(_dom, _parent);

        Utils.setAttribute("file", file, _file);
        Utils.setAttribute("id", id, _file);
        Utils.setAttribute("os", os, _file);
        Utils.setAttribute("type", type, _file);

        if (_isNewFile)
            _resources.addContent(_file);

        _isNewFile = false;
        _dom.setChanged(true);
    }


    public void removeFile(int index) {
        if (_resources != null && selectFile(index)) {
            _file.detach();
            _file = null;
            _dom.setChanged(true);
        }

        ResourcesListener.checkResources(_dom, _parent);
    }


    public Element getFileElement() {
        return _file;
    }


    public boolean isNewFile() {
        return _isNewFile;
    }
}
