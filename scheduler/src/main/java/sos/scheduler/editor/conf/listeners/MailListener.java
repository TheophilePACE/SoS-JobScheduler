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
package sos.scheduler.editor.conf.listeners;


import org.jdom.Element;

import sos.scheduler.editor.app.Utils;
import sos.scheduler.editor.conf.SchedulerDom;

public class MailListener {
	
    //public final static int            NONE              = 0;

    private             SchedulerDom   _dom              = null;

    private             Element        _parent           = null;
    
    private             Element        _settings          = null;
    

    public MailListener(SchedulerDom dom, Element parent) {
    	
        _dom = dom;
        _parent = parent;
        
        _settings = _parent.getChild("settings");
        
    }

    private void setMail() {

    	_settings = _parent.getChild("settings");
    	if (_settings == null) {
    		_settings =   new Element("settings");
    		_parent.addContent(_settings);
    	}

    }

    public void setValue(String name, String value) {
    	setValue(name, value, "");
    	/*setMail();
		Utils.setAttribute(name, value, _settings, _dom);
		if(_dom.isDirectory() || _dom.isLifeElement()) _dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
		*/
	}

    public void setValue(String name, String value, String default_) {
    	if(value == null || value.length() == 0) {
    		if(_settings != null) {
    			//return;
    			_settings.removeChild(name);
    			if(_settings.getContentSize() == 0 && _parent != null) {
    				_parent.removeContent(_settings);
    			}
    			_dom.setChanged(true);
    			if(_dom.isDirectory() || _dom.isLifeElement()) _dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
    			return;
    		}else{
    			return;
    		}
    	}
    	setMail();
		//Utils.setAttribute(name, value, default_, _settings, _dom);
    	
    	Element elem = null;
    	if(_settings.getChild(name) == null) {
    		elem = new Element(name);
    		_settings.addContent(elem);
    	}else 
    		elem = _settings.getChild(name);
    	
    	elem.setText(value);
    	
    	_dom.setChanged(true);
		if(_dom.isDirectory() || _dom.isLifeElement()) _dom.setChangedForDirectory("job", Utils.getAttributeValue("name",_parent), SchedulerDom.MODIFY);
		
	}
    
    
    public String getValue(String name) {
    	if(_settings == null)
    		return "";
    	Element elem = _settings.getChild(name);
    	if(elem == null)
    		return "";
    	
      //return Utils.getAttributeValue(name, _settings);
    	return elem.getTextNormalize();
    }

}
