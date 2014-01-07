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

import sos.scheduler.editor.doc.DocumentationDom;

public class SettingsListener {
    DocumentationDom _dom;

    Element          _parent;

    Element          _settings;


    public SettingsListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
        _settings = _parent.getChild("settings", _dom.getNamespace());
    }


    public void setSettings() {
        if (_settings == null) {
            _settings = new Element("settings", _dom.getNamespace());
            _parent.addContent(_settings);
        }
    }


    public void checkSettings() {
        if (_settings != null) {
            boolean remove = true;
            if (_settings.getChild("note", _dom.getNamespace()) != null)
                remove = false;
            if (_settings.getChild("profile", _dom.getNamespace()) != null)
                remove = false;
            if (_settings.getChild("connection", _dom.getNamespace()) != null)
                remove = false;

            if (remove) {
                _settings.detach();
                _settings = null;
            }
        }
    }


    public Element getSettingsElement() {
        setSettings();
        return _settings;
    }
}
