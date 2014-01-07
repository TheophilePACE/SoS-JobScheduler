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

public class ProfilesListener {
    private DocumentationDom   _dom;

    private SettingsListener   _settings;

    private Element            _profile;

    private boolean            _newProfile;

    public final static String defaultName = "default";


    public ProfilesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _settings = new SettingsListener(dom, parent);
    }


    public void checkSettings() {
        _settings.checkSettings();
    }


    public void fillProfiles(Table table) {
        table.removeAll();
        int index = 0;
        for (Iterator it = _settings.getSettingsElement().getChildren("profile", _dom.getNamespace()).iterator(); it
                .hasNext();) {
            Element profile = (Element) it.next();
            TableItem item = new TableItem(table, SWT.NONE);
            String name = Utils.getAttributeValue("name", profile);
            item.setText(name.length() > 0 ? name : defaultName);
            if (profile.equals(_profile))
                table.select(index);
            index++;
        }
    }


    public void setNewProfile() {
        _profile = new Element("profile", _dom.getNamespace());
        _newProfile = true;
    }


    public boolean selectProfile(int index) {
        try {
            _profile = (Element) _settings.getSettingsElement().getChildren("profile", _dom.getNamespace()).get(index);
            _newProfile = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getName() {
        String name = Utils.getAttributeValue("name", _profile);
        return name.length() > 0 ? name : defaultName;
    }


    public Element getProfileElement() {
        return _profile;
    }


    public void applyProfile(String name) {
        Utils.setAttribute("name", name, _profile);
        if (_newProfile)
            _settings.getSettingsElement().addContent(_profile);
        _newProfile = false;
        _dom.setChanged(true);
    }


    public boolean removeProfile(int index) {
        if (selectProfile(index)) {
            _profile.detach();
            _profile = null;
            _dom.setChanged(true);
            return true;
        } else
            return false;
    }


    public boolean isNewProfile() {
        return _newProfile;
    }
}
