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

public class ReleasesListener {
	
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _release;

    private boolean          _newRelease = false;


    public ReleasesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
    }


    public String getTitle() {
        return Utils.getElement("title", _release, _dom.getNamespace());
    }


    public String getID() {
        return Utils.getAttributeValue("id", _release);
    }


    public String getCreated() {
        return Utils.getAttributeValue("created", _release);
    }


    public String getModified() {
        return Utils.getAttributeValue("modified", _release);
    }


    public void fillAuthors(Table table) {
        table.removeAll();

        if (_release != null) {
            for (Iterator it = _release.getChildren("author", _dom.getNamespace()).iterator(); it.hasNext();) {
                Element author = (Element) it.next();
                TableItem item = new TableItem(table, SWT.NONE);
                item.setText(0, Utils.getAttributeValue("name", author));
                item.setText(1, Utils.getAttributeValue("email", author));
            }
        }
        Utils.setBackground(table, true);
    }


    public void newRelease() {
        _release = new Element("release", _dom.getNamespace());
        _parent.addContent(_release);
        _newRelease = true;
        
    }


    public Element getRelease() {
        return _release;
    }


    /*public void removeRelease() {
        if (_release != null) {
            _release.detach();
            _release = null;
            _dom.setChanged(true);
        }
    }*/

    public void removeRelease(int index) {
        if (_parent != null) {        	
        	_parent.getChildren("release", _dom.getNamespace()).remove(index);
        	_dom.setChanged(true);
        }
    }

    public boolean selectRelease(int i) {
        try {
            _release = (Element) _parent.getChildren("release", _dom.getNamespace()).get(i);
            _newRelease = false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void applyRelease(String title, String id, String created, String modified, TableItem[] authors) {
        Utils.setAttribute("id", id, _release);
        Utils.setAttribute("created", created, _release);
        Utils.setAttribute("modified", modified, _release);
        Utils.setElement("title", title, false, _release, _dom.getNamespace(), _dom);

        /*
        _release.removeChildren("author", _dom.getNamespace());
        for (int i = 0; i < authors.length; i++) {
            Element author = new Element("author", _dom.getNamespace());
            Utils.setAttribute("name", authors[i].getText(0), author);
            Utils.setAttribute("email", authors[i].getText(1), author);
            _release.addContent(author);
        }

        if (_newRelease && !_parent.getContent().contains(_release)) {        	
        	_parent.addContent(_release);        	
        }
*/
        _newRelease = false;
        _dom.setChanged(true);
    }


    public boolean isNewRelease() {
        return _newRelease;
    }


    public void fillReleases(Table table) {
        table.removeAll();
        int index = 0;
        for (Iterator it = _parent.getChildren("release", _dom.getNamespace()).iterator(); it.hasNext();) {
            Element release = (Element) it.next();
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, Utils.getAttributeValue("id", release));
            item.setText(1, Utils.getElement("title", release, _dom.getNamespace()));
            item.setText(2, Utils.getAttributeValue("created", release));
            item.setText(3, Utils.getAttributeValue("modified", release));
            
            if (release.equals(_release))
                table.select(index);
            index++;
        }
        Utils.setBackground(table, true);
    }
}
