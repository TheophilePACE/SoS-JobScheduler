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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.jdom.Element;

import sos.scheduler.editor.app.MainWindow;
import sos.scheduler.editor.app.Messages;
import sos.scheduler.editor.doc.DocumentationDom;

public class IncludeFilesListener {
    DocumentationDom _dom;

    Element          _parent;

    boolean          _changes = false;


    public IncludeFilesListener(DocumentationDom dom, Element parent) {
        _dom = dom;
        _parent = parent;
    }


    public String[] getIncludes() {
        if (_parent != null) {
            List includeList = _parent.getChildren("include", _dom.getNamespace());
            ArrayList files = new ArrayList();
            for (Iterator it = includeList.iterator(); it.hasNext();) {
                Element include = (Element) it.next();
                String file = include.getAttributeValue("file");
                files.add(file == null ? "" : file);
            }
            return (String[]) files.toArray(new String[] {});
        } else
            return new String[0];
    }


    public void saveIncludes(String[] includes) {
        if (_changes) {
            _parent.removeChildren("include", _dom.getNamespace());
            for (int i = 0; i < includes.length; i++) {
                _parent.addContent(new Element("include", _dom.getNamespace()).setAttribute("file", includes[i]));
            }
            // _dom.setChanged(true);
        }
    }


    public boolean exists(String file, String[] items) {
        for (int i = 0; i < items.length; i++)
            if (file.equals(items[i])) {
                MainWindow.message(Messages.getString("include.fileExists"), SWT.ICON_INFORMATION);
                return true;
            }
        return false;
    }


    public void setChanges(boolean changes) {
        _changes = changes;
        _dom.setChanged(true);
    }
}
