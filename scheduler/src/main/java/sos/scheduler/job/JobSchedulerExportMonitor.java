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
package sos.scheduler.job;


import java.io.File;

import sos.spooler.Monitor_impl;
import sos.spooler.Order;
import sos.util.SOSSchedulerLogger;

/**
 * <p>ProcessOrderMonitor implementiert ein Monitor-Script, das pro Auftrag vor bzw. nach dessen Verarbeitung gestartet wird.
 * Das Script wird f�r Standard Job-Klassen verwendet, an die Auftragsparameter aus der XML-Konfiguration �bergeben werden.</p>
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05 
 */

public class JobSchedulerExportMonitor extends Monitor_impl {


    /**
     * Initialisierung vor Verarbeitung eines Auftrags
     * @see sos.spooler.Monitor_impl#spooler_process_before()
     */
   /*public boolean spooler_process_before() {
        
        try { 
            return true;
        } catch (Exception e) {
            spooler_log.warn("error occurred in spooler_process_before(222): " + e.getMessage());
            return false;
        }
    }*/

    
    /**
     * Cleanup nach Verarbeitung eines Auftrags
     * @see sos.spooler.Monitor_impl#spooler_process_after()
     */

    public boolean spooler_process_after(boolean rc) {
        
    	File exportFile = null;
    	File moveFile = null;
    	
    	
        try { 
        	
        	spooler_log.info("calling JobSchedulerExportMonitor.spooler_process_after");// to map order configuration to this job
        	sos.spooler.Variable_set set = spooler_task.order().params();
        	
        	if (spooler_task.params() != null) set.merge(spooler_task.params());
            if (spooler_job.order_queue() != null) set.merge(spooler_task.order().params());
        	
            String fname = set.value("filename");
            if(fname != null && fname.length() > 0) {
            	exportFile = new File(fname);
                if(!exportFile.exists()) {
                	spooler_log.warn("missing [file=" + exportFile.getCanonicalPath());
                	return rc;
                }            	
            } else {
            	spooler_log.warn("missing parameter [filename]");
            	return rc;
            }
            
           
            String iname = set.value("move_path");
            
            if(iname  != null && iname.length() > 0) {
            	moveFile = new File(iname.endsWith("\\") || iname.endsWith("/") ? iname : iname.concat("/") + exportFile.getName());
            	if(!exportFile.renameTo(moveFile)) {
            		spooler_log.warn(exportFile.getCanonicalPath() + " could not move to " + moveFile.getCanonicalPath());
            		return rc;
            	}
            	spooler_log.info(exportFile.getCanonicalPath() + " moved to " + moveFile.getCanonicalPath());
            }
         	
        	//System.out.println(exportFile.getCanonicalPath() + " moved to " + importFile.getCanonicalPath());
        	        
            return rc;
        } catch (Exception e) {
            spooler_log.warn("error occurred in spooler_process_after(): " + e.getMessage());
            return false;
        } finally {            
        }
    }

}
