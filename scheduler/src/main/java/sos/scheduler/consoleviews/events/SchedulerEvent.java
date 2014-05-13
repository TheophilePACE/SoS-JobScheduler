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
/*
 * Created on 30.09.2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.consoleviews.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class SchedulerEvent {
   
   private String event_name;
   protected String event_title;
   protected String event_class;
   protected String event_id;
   protected String job_name;
   protected String job_chain;
   protected String order_id;
   protected String exit_code;
   protected String created;
   protected String expires;
   protected String remote_scheduler_host;
   protected String remote_scheduler_port;
   protected String scheduler_id;
   protected String logic="";
   protected String comment="";
    
   public String getEvent_title() {
      return event_title;
   }

   public String getEvent_class() {
      return event_class;
   }

   public String getEvent_id() {
      return event_id;
   }

   public String getJob_name() {
      return job_name;
   }

   public String getJob_chain() {
      return job_chain;
   }

   public String getOrder_id() {
      return order_id;
   }

   public String getExit_code() {
      return exit_code;
   }

   public String getCreated() {
      return created;
   }

   public String getExpires() {
      return expires;
   }

   public String getRemote_scheduler_host() {
      return remote_scheduler_host;
   }

   public String getRemote_scheduler_port() {
      return remote_scheduler_port;
   }

   public String getScheduler_id() {
      return scheduler_id;
   }

   public String getLogic() {
      return logic;
   }

   private HashMap properties() {
	  HashMap attr=new	HashMap();
	  //Id und class m�ssen bei der Pr�fung auf Ungleichheit unbedingt einflie�en
	  //if (event_class.equals(""))event_class = "*nodef";
	  //if (event_id.equals(""))event_id = "*nodef";
	  attr.put("event_title",event_title);
	  attr.put("event_class",event_class);
	  attr.put("event_id",event_id);
	  attr.put("job_name",job_name);
	  attr.put("job_chain",job_chain);
	  attr.put("order_id",order_id);
	  attr.put("exit_code",exit_code);
	  //attr.put("created",created);
	  //attr.put("expires",expires);
	  attr.put("remote_scheduler_host",remote_scheduler_host);
	  attr.put("remote_scheduler_port",remote_scheduler_port);
	  attr.put("scheduler_id",scheduler_id);
	  //attr.put("comment",comment);
      return attr;
   }
   
   public boolean isEqual(SchedulerEvent eActive) {
	 boolean erg=true;
 	 Iterator iProperties = properties().keySet().iterator();
	 while (iProperties.hasNext()) {
		String trigger =  iProperties.next().toString();
		if (!properties().get(trigger).equals("")) {
 		   if (eActive.properties().get(trigger) != null) {
 			 if (!trigger.equalsIgnoreCase("expires") && ! trigger.equalsIgnoreCase("created") && !(eActive.properties().get(trigger).equals(properties().get(trigger)))) {
	  	      erg = false;
		    }
		  }
		}
	 }
	 return erg;
   }
   
   // True if this is in list of active events
   public boolean isIn(LinkedHashSet listOfActiveEvents) {
	  boolean erg = false;
	  Iterator i = listOfActiveEvents.iterator();
	 
	  while (i.hasNext() && !erg) {
        if (this.isEqual((SchedulerEvent)i.next())) {
            erg = true;
        }
	  }
 	  return erg;
   }

   public void setEvent_name(String event_name) {
      this.event_name = event_name;
   }

   public void setLogic(String logic) {
      this.logic = logic;
   }

   public String getComment() {
      return comment;
   }

   public String getEvent_name() {
	  if (this.event_name.equals("")) {
		 if (this.event_class.equals("")) {
		    return this.event_id;
		 }else {
		    return this.event_class + "." + this.event_id;
		 }
	  }else {
         return event_name;
	  }
   }
}
