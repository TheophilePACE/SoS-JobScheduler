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

public class ClusterListener {
	
    public final static int            NONE              = 0;

    private             SchedulerDom   _dom              = null;

    private             Element        _parent           = null;
    
    private             Element        _cluster          = null;
    

    public ClusterListener(SchedulerDom dom, Element parent) {
    	
        _dom = dom;
        _parent = parent;
        //setCluster();
        _cluster = _parent.getChild("cluster");
        
    }

    private void setCluster() {

    	_cluster = _parent.getChild("cluster");
    	if (_cluster == null) {
    		_cluster =   new Element("cluster");
    		_parent.addContent(_cluster);
    	}

    }

    private void setAttributeValue(String element, String value) {
    	
        _cluster.setAttribute(element, value);
        _dom.setChanged(true);
        
    }


    public String getHeartbeatTimeout() {
    	if(_cluster == null)
    		return "";
      return Utils.getAttributeValue("heart_beat_timeout", _cluster);
    }
   
    public String getHeartbeatOwnTimeout() {
    	if(_cluster == null)
    		return "";
      return Utils.getAttributeValue("heart_beat_own_timeout", _cluster);
    }

    public String  getHeartbeatWarnTimeout() {
    	if(_cluster == null)
    		return "";
      return Utils.getAttributeValue("heart_beat_warn_timeout", _cluster);
    }


    public void setHeartbeatWarnTimeout(String heart_beat_warn_timeout) {  
    	setCluster();
    	_cluster.removeAttribute("heart_beat_warn_timeout");
      if (!heart_beat_warn_timeout.equals(""))setAttributeValue("heart_beat_warn_timeout", heart_beat_warn_timeout);
    }

    public void setHeartbeatOwnTimeout(String heart_beat_own_timeout) {
    	setCluster();
    	_cluster.removeAttribute("heart_beat_own_timeout");
    	if (!heart_beat_own_timeout.equals(""))setAttributeValue("heart_beat_own_timeout", heart_beat_own_timeout);
    }
    
    public void setHeartbeatTimeout(String heart_beat_timeout) {
    	setCluster();
    	_cluster.removeAttribute("heart_beat_timeout");
    	if (!heart_beat_timeout.equals(""))setAttributeValue("heart_beat_timeout", heart_beat_timeout);
    }
    
    

 
}
