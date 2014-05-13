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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import sos.connection.SOSConnection;
import sos.settings.SOSProfileSettings;
import sos.scheduler.command.SOSSchedulerCommand;
import sos.spooler.Job_impl;
import sos.util.SOSArguments;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;

/**
 * trigger sanity check in remote schedulers
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2005-03-22 
 */
public class JobSchedulerTriggerSanityCheck extends Job_impl {

    /** Protokollierung */
    private SOSSchedulerLogger sosLogger = null;
    
    /** Verbindung zur Datenbank */
    private SOSConnection sosConnection = null;

    /** Verbindung zur Datenbank */
    private Connection connection = null;
    
    /** Einstellungen des Jobs */
    protected SOSProfileSettings jobSettings = null;
    
    /** Job-Einstellungen */
    private Properties jobProperties = null;
  
    
    /** Pr�fungskategorien */
    private Properties checkCategories = null;

    /** Liste der zu �berpr�fenden Scheduler */
    private Vector checkSchedulers = null;

    /** Job-Name des Sanity Checks */
    private String jobName = "scheduler_check_sanity";
    
   

    public boolean spooler_init() {
    	
        ArrayList references = null;
        
        try {
            this.setLogger(new SOSSchedulerLogger(this.spooler.log()));

            try { // to initialize database connection
                this.setJobSettings(new SOSProfileSettings(spooler.ini_path()));
                this.setJobProperties(this.jobSettings.getSection("spooler"));

                if (this.getJobProperties().isEmpty()) 
                    throw new Exception("no settings found in section [spooler] of configuration file: " + spooler.ini_path());

                if (this.getJobProperties().getProperty("db") == null || this.getJobProperties().getProperty("db").length() == 0) 
                    throw new Exception("no settings found for entry [db] in section [spooler] of configuration file: " + spooler.ini_path());
                
                if (this.getJobProperties().getProperty("db_class") == null || this.getJobProperties().getProperty("db_class").length() == 0) 
                    throw new Exception("no settings found for entry [db_class] in section [spooler] of configuration file: " + spooler.ini_path());
                
                
                if (this.getLogger() != null) sosLogger.debug6("connecting to database.. .");

                String dbProperty = this.getJobProperties().getProperty("db").replaceAll("jdbc:", "-url=jdbc:");
                dbProperty = dbProperty.substring(dbProperty.indexOf('-'));
                
                SOSArguments arguments = new SOSArguments(dbProperty);

	            this.setConnection( SOSConnection.createInstance(  
	                    							this.getJobProperties().getProperty("db_class"),
	                    							arguments.as_string("-class=", ""),
	                    							arguments.as_string("-url=", ""),
	                    							arguments.as_string("-user=", ""),
	                    							arguments.as_string("-password=", ""),
	                    							(SOSLogger)new SOSSchedulerLogger(this.spooler_log) )
	                    			);
	            this.getConnection().connect();
                
	            if (this.getLogger() != null) this.getLogger().debug6("..successfully connected to " + arguments.as_string("-url=", ""));
            }
            catch (Exception e) {
                throw (new Exception("connect to database failed: " + e.getMessage()));
            }

            
            try { // to retrieve the list of schedulers for sanity check 
                // TODO Liste der Scheduler aus XML-Kommando <show_state what="client"/> generieren (noch nicht verf�gbar)
                this.checkSchedulers = new Vector();
                HashMap schedulerEntry = new HashMap();
                schedulerEntry.put("host", "localhost");
                schedulerEntry.put("port", "4444");
                this.checkSchedulers.add(schedulerEntry);
            }
            catch (Exception e) {
                throw (new Exception("retrieval of schedulers for sanity check failed: " + e.getMessage()));
            }
            
            
            try { // to check parameter values
                if (spooler_task.params().var("check_scheduler_host") != null
                        && spooler_task.params().var("check_scheduler_host").length() > 0) {
                    if (spooler_task.params().var("check_scheduler_host").equalsIgnoreCase("true") 
                            || spooler_task.params().var("check_disk_space").equalsIgnoreCase("yes")
                            || spooler_task.params().var("check_disk_space").equalsIgnoreCase("1")) {
                        this.checkCategories.put("check_disk_space", "yes");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_disk_space]: yes");
                    } else {
                        this.checkCategories.put("check_disk_space", "no");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_disk_space]: no");
                    }
                }	
      
                if (spooler_task.params().var("check_disk_space") != null
                        && spooler_task.params().var("check_disk_space").length() > 0) {
                    if (spooler_task.params().var("check_disk_space").equalsIgnoreCase("true") 
                            || spooler_task.params().var("check_disk_space").equalsIgnoreCase("yes")
                            || spooler_task.params().var("check_disk_space").equalsIgnoreCase("1")) {
                        this.checkCategories.put("check_disk_space", "yes");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_disk_space]: yes");
                    } else {
                        this.checkCategories.put("check_disk_space", "no");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_disk_space]: no");
                    }
                }	
      
                if (spooler_task.params().var("check_memory_size") != null
                        && spooler_task.params().var("check_memory_size").length() > 0) {
                    if (spooler_task.params().var("check_memory_size").equalsIgnoreCase("true") 
                            || spooler_task.params().var("check_memory_size").equalsIgnoreCase("yes")
                            || spooler_task.params().var("check_memory_size").equalsIgnoreCase("1")) {
                        this.checkCategories.put("check_memory_size", "yes");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_memory_size]: yes");
                    } else {
                        this.checkCategories.put("check_memory_size", "no");
                        if (this.getLogger() != null) this.getLogger().info(".. job parameter [check_memory_size]: no");
                    }
                }	

            }
            catch (Exception e) {
                throw (new Exception("parameter check failed: " + e.getMessage()));
            }
            
            return true;
        }    
        catch (Exception e) {
            if (this.getLogger() != null) try { this.getLogger().error(e.getMessage()); } catch (Exception ex) {}
            return false;
        }
		    
    }

    
    public boolean spooler_process() {

        String jobContent = "";
        
        try {
            this.setLogger(new SOSSchedulerLogger(this.spooler.log()));

            try { // to start sanity check job for the given schedulers in the given categories
                jobContent = "<start_job job=\"" + this.jobName + "\"><params>";
                
                Iterator categoryIterator = checkCategories.keySet().iterator();
                while(categoryIterator.hasNext()) {
                    String category = (String)categoryIterator.next();
                    if (category != null && category.length() > 0) {
                        if (checkCategories.containsKey(category))
                            jobContent += "<param name=\"" + category + "\" value=\"" + checkCategories.get(category).toString() + "\"/>";
                    }
                }
                
                jobContent += "</params></start_job>";
                
                Iterator schedulerIterator = this.checkSchedulers.iterator();
                while(schedulerIterator.hasNext()) {
                    HashMap checkScheduler = (HashMap)schedulerIterator.next();
                    SOSSchedulerCommand command = new SOSSchedulerCommand(checkScheduler.get("host").toString(), 
                                                       Integer.parseInt(checkScheduler.get("port").toString()), "udp");
                    command.connect();
                    command.sendRequest(jobContent);
                    command.disconnect();
                }
                
                // TODO wait some seconds and retrieve sanity check results from database, send alert message
                
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
    	} catch (Exception e) {
    	    spooler_log.error("error occurred starting sanity check for remote scheduler: " + e.getMessage());
    	}
      
      return false;
    }

    
    /**
     * @return Returns the jobProperties.
     */
    public Properties getJobProperties() {
        return this.jobProperties;
    }
    
    /**
     * @param jobProperties The jobProperties to set.
     */
    public void setJobProperties(Properties jobProperties) {
        this.jobProperties = jobProperties;
    }
    
    /**
     * @return Returns the jobSettings.
     */
    public SOSProfileSettings getJobSettings() {
        return this.jobSettings;
    }
    
    /**
     * @param jobSettings The jobSettings to set.
     */
    public void setJobSettings(SOSProfileSettings jobSettings) {
        this.jobSettings = jobSettings;
    }
    
    /**
     * @return Returns the sosConnection.
     */
    public SOSConnection getConnection() {
        return this.sosConnection;
    }
    
    /**
     * @param sosConnection The sosConnection to set.
     */
    public void setConnection(SOSConnection sosConnection) {
        this.sosConnection = sosConnection;
    }
        
    /**
     * @return Returns the sosLogger.
     */
    public SOSSchedulerLogger getLogger() {
        return sosLogger;
    }
    
    /**
     * @param sosLogger The sosLogger to set.
     */
    public void setLogger(SOSSchedulerLogger sosLogger) {
        this.sosLogger = sosLogger;
    }

}
