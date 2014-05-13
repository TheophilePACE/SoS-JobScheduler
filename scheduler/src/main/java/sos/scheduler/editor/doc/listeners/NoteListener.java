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

import org.jdom.Element;

import sos.scheduler.editor.app.Options;
import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.doc.DocumentationDom;

public class NoteListener {
    private DocumentationDom _dom;
    private Element          _parent;
    private String           _name;
    private boolean          _optional;
    private String[]         _languages  = { "de", "en", "fr", "it", "es" };
    private String           _lang       = null;
    private boolean          _setChanged = true;

    public NoteListener(DocumentationDom dom, Element parent, String name, boolean optional, boolean changeStatus) {
        _dom = dom;
        _parent = parent;
        _name = name;
        _optional = optional;
        _setChanged = changeStatus;
        init();
    }


    private void init() {
        if (_lang == null) {
            if (_parent != null) {
                List list = _parent.getChildren(_name, _dom.getNamespace());
                if (list.size() > 0) {
                    Element item = (Element) list.get(0);
                    String lang = item.getAttributeValue("language");
                    _lang = lang == null ? _languages[0] : lang;
                } else
                    _lang = _languages[0];
            }
        }
    }


    public String[] getLanguages() {
    	String strL = Options.getTemplateLanguageList();
    	_languages = strL.split(";");
        return _languages;
    }


    public void setLang(String pstrLang) {
        _lang = pstrLang;
        Options.setTemplateLanguage(pstrLang);
    }


    public String getLang() {
        return _lang;
    }


    public void setNote(String note) {
        Element item = getElement();
        if (item == null && note.length() > 0) {
            // create new one
        	 
         	  if (_parent.getParent()==null) {
        	    Element r = _dom.getRoot();
        	    
        	    Element c = null;
        	    if(_parent.getName().equalsIgnoreCase("release"))
        	    	c = r.getChild("releases",_dom.getNamespace());
        	    else if(_parent.getName().equalsIgnoreCase("resource"))
        	    	c = r.getChild("database",_dom.getNamespace());
        	    else 
        	    	c = r.getChild("configuration",_dom.getNamespace());
        	    c.addContent(_parent);
        	  }
        	   
            item = new Element(_name, _dom.getNamespace());
            Utils.setAttribute("language", _lang, item);
            _parent.addContent(item);
        } else if (item != null && note.length() == 0) {
            // empty - remove it
            item.detach();
            if (_setChanged)
                _dom.setChanged(true);
            return;
        } else if (item == null)
            return;

         try {
        	 /*if(item.getChild("div", org.jdom.Namespace.getNamespace("http://www.w3.org/1999/xhtml")) != null) {
        	 Element div = item.getChild("div", org.jdom.Namespace.getNamespace("http://www.w3.org/1999/xhtml"));
        	 div.setText(note);
        	 } else {
        		 
        	 */
        		 note = "<div xmlns=\"http://www.w3.org/1999/xhtml\">\n" + note + "\n</div>";
        		 item.setContent(_dom.noteAsDom(note));
        		 //_dom.setChanged(true);
        	 //}
          } catch (Exception e) {
            e.printStackTrace();
          }


        if (_setChanged)
            _dom.setChanged(true);
    }


    public String getNote() {
        Element item = getElement();
        if (item == null)
            return "";

        try {
            return _dom.noteAsStr(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private Element getElement() {
        if (_parent != null) {
            List list = _parent.getChildren(_name, _dom.getNamespace());
            for (Iterator it = list.iterator(); it.hasNext();) {
                Element item = (Element) it.next();
                if (Utils.getAttributeValue("language", item).equals(_lang))
                    return item;
            }
        }
        return null;
    }


    public void createDefault() {
        if (_parent != null && !_optional) {
            if (_parent.getChildren(_name, _dom.getNamespace()).size() == 0) {
                Element item = new Element(_name, _dom.getNamespace());
                item.setAttribute("language", _lang);
                _parent.addContent(item);
            }
        }
    }

}
