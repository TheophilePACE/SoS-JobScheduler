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
package sos.scheduler.editor.conf.listeners;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jdom.Element;
import org.jdom.JDOMException;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;

public class BaseListener {
	
	
    private         SchedulerDom      _dom       = null;

    private         Element           _config    = null;

    private         Element           _baseFile  = null;

    private         List              _list      = null;


    public BaseListener(SchedulerDom dom) throws JDOMException {
    	
        _dom = dom;
        _config = _dom.getRoot().getChild("config");
        _list = _config.getChildren("base");
        
    }


    public String getComment() {
        return Utils.getAttributeValue("__comment__", _baseFile);
    }


    public String[] getFiles() {
    	
        Iterator it = _list.iterator();
        String[] files = new String[_list.size()];
        int index = 0;
        while (it.hasNext()) {
            String file = ((Element) it.next()).getAttributeValue("file");
            if (file == null)
                file = "UNKNOWN FILE";
            files[index++] = file;
        }
        return files;
        
    }


    public void fillTable(Table table) {
    	
        table.removeAll();
        for (Iterator it = _list.iterator(); it.hasNext();) {
            Element e = (Element) it.next();
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, Utils.getAttributeValue("file", e));
            String comment = Utils.getAttributeValue("__comment__", e);
            if (comment.indexOf("\n") > -1)
                comment = comment.substring(0, comment.indexOf("\n") - 1) + "...";
            item.setText(1, comment);
        }
        
    }


    public void selectBaseFile(int index) {
    	
        if (index >= 0 && index < _list.size())
            _baseFile = (Element) _list.get(index);
        else
            _baseFile = null;
        
    }


    public void newBaseFile() {
        _baseFile = new Element("base");
    }


    public void applyBaseFile(String file, String comment) {
    	
        _baseFile.setAttribute("file", file);
        _baseFile.setAttribute("__comment__", comment);
        if (!_list.contains(_baseFile))
            _list.add(_baseFile);
        _dom.setChanged(true);
        
    }


    public void removeBaseFile(int index) {
    	
        if (index >= 0 && index < _list.size())
            _list.remove(index);
        _dom.setChanged(true);
        
    }


    public String getFile() {
        return Utils.getAttributeValue("file", _baseFile);
    }

}
