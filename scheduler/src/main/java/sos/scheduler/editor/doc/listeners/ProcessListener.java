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
import org.jdom.Content;
import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class ProcessListener {
    DocumentationDom _dom;

    Element          _job;

    Element          _process;


    public ProcessListener(DocumentationDom dom, Element job) {
        _dom = dom;
        _job = job;
    }


    public boolean isProcess() {
        boolean script = _job.getChild("script", _dom.getNamespace()) != null;
        _process = _job.getChild("process", _dom.getNamespace());
        if (!script && _process == null)
            setProcess();
        return _process != null;
    }


    public void setProcess() {
        _job.removeChild("script", _dom.getNamespace());
        _process = new Element("process", _dom.getNamespace());
        _job.addContent(_process);
        _dom.setChanged(true);
    }


    public String getFile() {
        return Utils.getAttributeValue("file", _process);
    }


    public void setFile(String file) {
        Utils.setAttribute("file", file, _process, _dom);
    }


    public String getParam() {
        return Utils.getAttributeValue("param", _process);
    }


    public void setParam(String param) {
        Utils.setAttribute("param", param, _process, _dom);
    }


    public String getLog() {
        return Utils.getAttributeValue("log", _process);
    }


    public void setLog(String log) {
        Utils.setAttribute("log", log, _process, _dom);
    }


    public void applyParam(String name, String value) {
        Element env = _process.getChild("environment", _dom.getNamespace());
        if (env == null) {
            env = new Element("environment", _dom.getNamespace());
            _process.addContent(env);
        }

        Element var = getVariable(name, env);
        if (var == null) {
            var = new Element("variable", _dom.getNamespace());
            env.addContent(var);
        }

        var.setAttribute("name", name);
        var.setAttribute("value", value);
        _dom.setChanged(true);
    }


    private Element getVariable(String name, Element env) {
        for (Iterator it = env.getContent().iterator(); it.hasNext();) {
            Content o = (Content) it.next();
            if (o instanceof Element && Utils.getAttributeValue("name", (Element) o).equals(name))
                return (Element) o;
        }
        return null;
    }


    public void removeVariable(String name) {
        Element env = _process.getChild("environment", _dom.getNamespace());
        if (env == null)
            return;

        Element var = getVariable(name, env);
        if (var != null)
            var.detach();
        if (env.getChildren().size() == 0)
            env.detach();

        _dom.setChanged(true);
    }


    public void fillTable(Table table) {
        table.removeAll();
        Element env = _process.getChild("environment", _dom.getNamespace());
        if (env != null) {
            for (Iterator it = env.getContent().iterator(); it.hasNext();) {
                Content o = (Content) it.next();
                if (o instanceof Element) {
                    Element var = (Element) o;
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(0, Utils.getAttributeValue("name", var));
                    item.setText(1, Utils.getAttributeValue("value", var));
                }
            }
        }
    }
}
