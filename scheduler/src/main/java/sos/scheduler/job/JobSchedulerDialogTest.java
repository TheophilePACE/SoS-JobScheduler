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

import sos.settings.SOSProfileSettings;
import sos.settings.SOSSettings;
import sos.spooler.Job_impl;
import sos.spooler.Order;
import sos.spooler.Variable_set;

import sos.stresstest.dialogtest.SOSDialogTest;
import sos.util.SOSSchedulerLogger;


/**
 * @author andreas.pueschel@sos-berlin.com
 *
 * process sos dialog test
 */
public class JobSchedulerDialogTest extends Job_impl {

    private String configFile = "./config/sos_dialogtest.ini";
    
    
	public boolean spooler_process() {
	    
	    boolean rc = false;
    	
	    try {
	    
	        // process job parameters
			if (spooler_task.params().var("config_file") != null
			&& spooler_task.params().var("config_file").length() > 0) {
				this.configFile = spooler_task.params().var("config_file").toString();
				spooler_log.debug3(".. job parameter [config_file]: " + this.configFile);
			}

			// order driven or job driven?
			if (spooler_job.order_queue() != null) {
			    rc = true;
			    Order order = spooler_task.order();
			    Variable_set payload = (sos.spooler.Variable_set) order.payload();
			    if (payload.var("config_file") != null && payload.var("config_file").length() > 0) {
			        this.configFile = payload.var("config_file");
					spooler_log.debug3(".. order parameter [config_file]: " + this.configFile);
			    }
			}
			
			spooler_log.info(".. parameter [config_file]: " + this.configFile);
			SOSSchedulerLogger logger = new SOSSchedulerLogger(spooler_log);
			
			SOSDialogTest dialogTest = new SOSDialogTest(logger);
	        /* if (dialogTest.initialize(this.configFile)) {
	            throw new Exception("error occurred initializing SOSDialogTest");
	        }*/
			//dialogTest.initialize(this.configFile);
			SOSSettings settings = new SOSProfileSettings(configFile);
			dialogTest.initialize(settings);
	        dialogTest.execute();
	        
	    } catch (Exception e) {
	        spooler_log.warn("error occurred in JobSchedulerDialogTest: " + e.getMessage());
	        rc = false;
	    }
		
		return rc;
	}

}
