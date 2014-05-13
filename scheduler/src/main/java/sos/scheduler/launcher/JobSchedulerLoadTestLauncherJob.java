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
package sos.scheduler.launcher;


import java.util.HashMap;

import sos.scheduler.launcher.JobSchedulerLoadTestLauncher;
import sos.spooler.Job_impl;
import sos.spooler.Variable_set;
import sos.util.SOSClassUtil;
import sos.util.SOSSchedulerLogger;
import sos.util.SOSString;


/**
 * 
 * Klasse JobSchedulerLoadTestLauncherJob
 * Diese Job kann die Ausf�hrung paralleler Jobs skalieren und beliebig viele Jobs k�nnen parallel laufen.
 * Der Job kann beliebig viele individuelle Jobs startet und kann mit dem Namen eines anderen Jobs oder
 * Auftrags parametrisiert werden, die gestartet werden soll.
 * Die Anzahl zu startender Jobs in konfigurierbaren Zeitabst�nden sowie die Erh�hung der Anzahl zu startenden Jobs beim Erreichen
 * jedes Intervalls sind konfigurierbar.
 *
 *
 * @author M�r�vet �ks�z
 * @version 1.0
 * 
 * email: mueruevet.oeksuez@sos-berlin.com
 * 
 * resourcen: * sos.mail.jar, sos.util.jar, sos.xml.jar, xercesImpl.jar, xml-apis.jar, xalan.jar
 * 
 *  ********************************************************************************************
 *  
 *   * 2. Parameter
 *
 * 2.1 Allgemeine Parameter f�r den Launcher
 *
 * a) <param name="scheduler_launcher_host" value="localhost"/>
 *
 * Der Host des Job Schedulers, der den Job ausf�hrt, Default: localhost
 *
 * b) <param name="scheduler_launcher_port" value="4444"/>
 *
 * Der Port des Job Schedulers, der den Job ausf�hrt, Default: 4444
 *
 * c) <param name="scheduler_launcher_protocol" value="tcp|udp"/>
 *
 * Das Protokoll zum Versenden via JobSchedulerCommand, Default: tcp
 *
 * c) <param name="scheduler_launcher_min_starts" value="5"/>
 *
 * Die minimale Anzahl von Jobs oder Auftr�gen, die gleichzeitig gestartet wird. Default=1
 *
 * d) <param name="scheduler_launcher_max_starts" value="100"/>
 *
 * Die maximale Anzahl von Jobs oder Auftr�gen, die gleichzeitig gestartet werden.
 *
 *  e) <param name="scheduler_launcher_start_increment" value="+10|*2"/>
 *
 *  Die Anzahl zu startender Job oder Auftr�ge wird pro Start um diese Zahl erh�ht. Ein Wert 3 ist gleichbedeutend mit +3, d.h. die Zahl wird um 3 erh�ht.
 *  Ein Wert *2 bedeutet, dass sich die Anzahl verdoppelt. Das Inkrement gilt nicht beim ersten Start. Die mit dem Parameter max_starts gesetzte Anzahl darf nicht �berschritten werden. Wird sie �berschritten, dann werden alle weiteren Starts mit dem Wert von max_starts ausgef�hrt.
 *
 *  f) alle Parameter, deren Namen nicht mit scheduler_launcher_ beginnen, werden an den Job oder Auftrag durchgereicht.
 *
 *  2.2 Spezielle Parameter f�r Jobs, siehe Beispiel
 *  <param name="scheduler_launcher_job" value="job_name"/>
 *  Wenn dieser Parameter �bergeben wurde, dann handelt es sich um einen Job. Der hier �bergebene 
 *  Job-Name ist der Wert des Attributs job= in <start_job job="..."/>
 *
 *  In diesem Fall werden ausgewertet:
 *
 *  a) mandatory
 *  keine
 *
 *  b) optional, d.h. bitte keine eigenen Defaults, sondern im Fall des Fehlens einfach nicht �bergeben
 *  <param name="scheduler_launcher_job_after" value="..."/>
 *  <param name="scheduler_launcher_job_at" value="..."/>
 *  <param name="scheduler_launcher_job_web_service" value="..."/>
 *
 *  2.3 Spezielle Parameter f�r Auftr�ge, siehe Beispiel
 *  <param name="scheduler_launcher_order" value="order_id"/>
 *  Wenn dieser Parameter �bergeben wurde, dann handelt es sich um einen Auftrag. Die hier �bergebene Auftragskennung ist der Wert des Attributs id= in <add_order id="..."/>
 *
 *  In diesem Fall werden ausgewertet:
 *
 *  a) mandatory
 *  <param name="scheduler_launcher_order_job_chain" value="..."/>
 *
 *  b) optional, d.h. bitte keine eigenen Defaults, sondern im Fall des Fehlens einfach nicht �bergeben
 *  <param name="scheduler_launcher_order_replace" value="yes|no"/>
 *  <param name="scheduler_launcher_order_state" value="..."/>
 *  <param name="scheduler_launcher_order_title" value="..."/>
 *  <param name="scheduler_launcher_order_at" value="..."/>
 *  <param name="scheduler_launcher_order_priority" value="..."/>
 *  <param name="scheduler_launcher_order_web_service" value="..."/>
 *  <param name="scheduler_launcher_interval" value="..."/>
 *  
 *
 *  2.4 Alle anderen Parameter werden durchgereicht. Genauer: alle Parameter, die nicht mit
 *  scheduler_launcher_ beginnen, werden den Jobs �bergeben, die gestartet werden sollen.
 * 
 */

public class JobSchedulerLoadTestLauncherJob extends Job_impl {
	
	 private SOSString sosString          = null;
	 private SOSSchedulerLogger sosLogger = null;

     public boolean spooler_init() {
	        
	        try {
                sosString = new SOSString();	            
	            //this.getParameters();
	            sosLogger = new SOSSchedulerLogger(spooler_log);	           	            
	            return true;
	            
	        } catch (Exception e) {
	            spooler_log.error("error occurred initializing job: " + e.getMessage());
	            return false;
	        }
	    }
	    
	    
	    
	    /**
	     * Job Scheduler API implementation.
	     * 
	     * Method is executed once per job or repeatedly per order.
	     * 
	     * @return boolean
	     */
	    public boolean spooler_process() {
	    	        
	        Variable_set parameters = null;
	        HashMap allParams       = null;
	        JobSchedulerLoadTestLauncher launcher = null;
	        try {
	        	sosLogger = new SOSSchedulerLogger(spooler_log);
	        	sosLogger.debug3(".. calling " + SOSClassUtil.getMethodName());
	            parameters = spooler_task.params();
	            
	            if (spooler_job.order_queue() != null) {
	                parameters.merge(spooler_task.order().params());	                         
	            }
	            
	            allParams = this.getParameters();	      
	            
	            launcher = new JobSchedulerLoadTestLauncher(sosLogger);	            
	            launcher.setParameters(allParams);
	            launcher.process();
	            
	            spooler_job.set_state_text(launcher.getStateText()); 
	            
	            return (spooler_job.order_queue() != null);
	            
	        } catch (Exception e) {
	            spooler_log.error("error occurred in execution: " + e.getMessage());
	            return false;
	        }
	        
	    }

	    /**
	     * Liest alle Job-Parameter aus und schreibt diese in ein HahpMap Objekt.
	     * 
	     * @return
	     * @throws Exception
	     */
	  	public HashMap getParameters() throws Exception {
	  		Variable_set parameters = null;
	  		String[] names = null;
	  		HashMap allParam = new HashMap();
	        try { 	        	
	        	sosLogger.debug3(".. calling " + SOSClassUtil.getMethodName());
	        	parameters = spooler_task.params();
	        	if (parameters.count() > 0) {
	        		names = parameters.names().split(";");
	        		for(int i = 0; i < parameters.count(); i++) {
	        			if (sosString.parseToString(parameters.var(names[i])).length() > 0) {
	        				allParam.put(names[i], parameters.var(names[i]));
	        			}
	        		}	
	        	}
	        	 return allParam;	        		        				        		          
	            
	        } catch (Exception e) {
	            throw new Exception("error occurred processing task parameters: " + e.getMessage());
	        }
	    }	    	    	    

}
