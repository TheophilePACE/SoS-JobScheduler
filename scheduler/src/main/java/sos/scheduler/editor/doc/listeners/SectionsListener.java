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

public class SectionsListener {
    private DocumentationDom _dom;

    private Element          _parent;

    private Element          _section;

    private boolean          _newSection = false;


    public SectionsListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
    }


    public void fillSections(Table table) {
        table.removeAll();
        int index = 0;
        for (Iterator it = _parent.getChildren("section", _dom.getNamespace()).iterator(); it.hasNext();) {
            Element section = (Element) it.next();
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, Utils.getAttributeValue("name", section));
            item.setText(1, Utils.getAttributeValue("reference", section));
            item.setText(2, Utils.getAttributeValue("id", section));
            if (section.equals(_section))
                table.select(index);
            index++;
        }
    }


    public void setNewSection() {
        _section = new Element("section", _dom.getNamespace());
        _newSection = true;
    }


    public boolean selectSection(int index) {
        try {
            _section = (Element) _parent.getChildren("section", _dom.getNamespace()).get(index);
            _newSection = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getName() {
        return Utils.getAttributeValue("name", _section);
    }


    public String getID() {
        return Utils.getAttributeValue("id", _section);
    }


    public String getReference() {
        return Utils.getAttributeValue("reference", _section);
    }


    public String[] getReferences(String ownID) {
        return DocumentationListener.getIDs(_dom, ownID);
    }


    public void applySection(String name, String id, String reference) {
        Utils.setAttribute("name", name, _section);
        Utils.setAttribute("id", id, _section);
        Utils.setAttribute("reference", DocumentationListener.getID(reference), _section);
        if (_newSection)
            _parent.addContent(_section);
        _newSection = false;
        _dom.setChanged(true);
    }


    public void removeSection(int index) {
        if (selectSection(index)) {
            _section.detach();
            _section = null;
            _newSection = false;
            _dom.setChanged(true);
        }
    }

}
