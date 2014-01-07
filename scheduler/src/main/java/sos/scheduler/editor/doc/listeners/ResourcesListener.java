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

import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class ResourcesListener {
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _resources;

    private Element          _memory;

    private Element          _space;

    private String[]         _units = { "KB", "MB", "GB", "TB" };


    public ResourcesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;

        _resources = _parent.getChild("resources", _dom.getNamespace());
        if (_resources != null) {
            _memory = _resources.getChild("memory", _dom.getNamespace());
            _space = _resources.getChild("space", _dom.getNamespace());
        }
    }


    public static Element getResourcesElement(DocumentationDom dom, Element parent) {
        Element res = parent.getChild("resources", dom.getNamespace());
        if (res == null) {
            res = new Element("resources", dom.getNamespace());
            parent.addContent(res);
        }
        return res;
    }


    public static void checkResources(DocumentationDom dom, Element parent) {
        Element res = parent.getChild("resources", dom.getNamespace());
        if (res != null && res.getChildren().size() == 0)
            res.detach();
    }


    public void setMemory(boolean enabled) {
        if (_resources == null)
            _resources = getResourcesElement(_dom, _parent);
        _memory = _resources.getChild("memory", _dom.getNamespace());

        if (enabled && _memory == null) {
            _memory = new Element("memory", _dom.getNamespace());
            _resources.addContent(_memory);
            _dom.setChanged(true);
        } else if (!enabled && _memory != null) {
            _memory.detach();
            _dom.setChanged(true);
            checkResources(_dom, _parent);
        }
    }


    public String getMemory() {
        return Utils.getAttributeValue("min", _memory);
    }


    public void setMemory(String memory) {
        Utils.setAttribute("min", memory, _memory, _dom);
    }


    public String[] getUnits() {
        return _units;
    }


    public String getMemoryUnit() {
        String unit = Utils.getAttributeValue("unit", _memory);
        if (unit.equals("")) {
            unit = _units[1];
            Utils.setAttribute("unit", unit, _memory);
        }
        return unit;
    }


    public void setMemoryUnit(String unit) {
        Utils.setAttribute("unit", unit, _memory, _dom);
    }


    public Element getMemoryElement() {
        return _memory;
    }


    public boolean isMemory() {
        return _memory != null;
    }


    public void setSpace(boolean enabled) {
        if (_resources == null)
            _resources = getResourcesElement(_dom, _parent);
        _space = _resources.getChild("space", _dom.getNamespace());

        if (enabled && _space == null) {
            _space = new Element("space", _dom.getNamespace());
            _resources.addContent(_space);
            _dom.setChanged(true);
        } else if (!enabled && _space != null) {
            _space.detach();
            _dom.setChanged(true);
            checkResources(_dom, _parent);
        }
    }


    public String getSpace() {
        return Utils.getAttributeValue("min", _space);
    }


    public void setSpace(String space) {
        Utils.setAttribute("min", space, _space, _dom);
    }


    public String getSpaceUnit() {
        String unit = Utils.getAttributeValue("unit", _space);
        if (unit.equals("")) {
            unit = _units[1];
            Utils.setAttribute("unit", unit, _space);
        }
        return unit;
    }


    public void setSpaceUnit(String unit) {
        Utils.setAttribute("unit", unit, _space, _dom);
    }


    public Element getSpaceElement() {
        return _space;
    }


    public boolean isSpace() {
        return _space != null;
    }
}
