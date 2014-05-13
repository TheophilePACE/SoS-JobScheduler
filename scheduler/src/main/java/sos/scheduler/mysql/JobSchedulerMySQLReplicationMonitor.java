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
package sos.scheduler.mysql;


import java.util.HashMap;
import java.util.Properties;

import sos.connection.SOSConnection;
import sos.connection.SOSMySQLConnection;
import sos.settings.SOSProfileSettings;
import sos.spooler.Job_impl;
import sos.spooler.Log;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;
 
/**
 * Monitor MySQL Replication
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2005-01-29 
 */
public class JobSchedulerMySQLReplicationMonitor extends Job_impl {

    private Log sosLogger = null;
    
    private SOSProfileSettings sosSettings = null;
    
    private SOSConnection masterConnection;
    
    private Properties masterProperties;
    private HashMap masterResult;

    private int sosCounter = 0;


    public boolean spooler_init() {
        
        String settingsFilename 		  = null;
        boolean hasMasterSettingsFile 	  = false;
        String connectionSettingsFilename = null;

        try {
            this.sosLogger = spooler_log;
        }
        catch (Exception e) {
            spooler_log.error("error retrieving logger: " + e.getMessage());
            return false;
        }


        try { // to read master settings
            settingsFilename = spooler.ini_path();
            this.sosSettings = new SOSProfileSettings(settingsFilename);

            this.masterProperties = this.sosSettings.getSection("mysqld");
            if (this.masterProperties.isEmpty()) {
                this.masterProperties = this.sosSettings.getSection("job " + spooler_job.name());
                if (!this.masterProperties.isEmpty())
                    connectionSettingsFilename = this.masterProperties.getProperty("master_config");
                if (connectionSettingsFilename == null || connectionSettingsFilename.length() == 0) this.masterProperties.clear();
                hasMasterSettingsFile = true;
            }
            if (this.masterProperties.isEmpty()) {
                throw new Exception("no master settings found for section [mysqld] or [job " + spooler_job.name() + "] in settings file: " + settingsFilename);
            }
        }
        catch (Exception e) {
            sosLogger.error("error occurred reading master settings: " + e.getMessage());
            return false;
        }


        try { // to connect to master database
            if (!hasMasterSettingsFile) {
                this.masterConnection = new SOSMySQLConnection( this.masterProperties.getProperty("driver"),
                                                                this.masterProperties.getProperty("url"),
                                                                this.masterProperties.getProperty("user"),
                                                                this.masterProperties.getProperty("password") );
            } else {
                this.masterConnection = SOSConnection.createInstance(connectionSettingsFilename, (SOSLogger) new SOSSchedulerLogger(spooler_log));
                masterProperties.setProperty("url", this.masterConnection.getUrl());
            }
            this.masterConnection.connect();
        }
        catch (Exception e) {
            sosLogger.error ("connect to master database failed: " + e.getMessage());
            return false;
        }

      
        try { // to retrieve master status
            this.masterResult = this.masterConnection.getSingle("SHOW MASTER STATUS");
            if (this.masterResult == null || masterResult.isEmpty()) {
                sosLogger.warn("master returned no result for query: SHOW MASTER STATUS");
                return false;
            }

            sosLogger.info("\n");
            sosLogger.info("status for master: " + this.masterProperties.getProperty("url"));
            sosLogger.info("file: " + this.masterResult.get("file").toString());
            sosLogger.info("position: " + this.masterResult.get("position").toString());
            sosLogger.info("binlog_do_db: " + this.masterResult.get("binlog_do_db").toString());
            sosLogger.info("binlog_ignore_db: " + this.masterResult.get("binlog_ignore_db").toString());
        }
        catch (Exception e) {
            sosLogger.error("error retrieving status for database from master: " + e.getMessage());
            return false;
        }
      
        return true;
    }
    
    
    public boolean spooler_process() {
    	
        Properties slaveProperties 			= null;
        SOSConnection slaveConnection 		= null;
        HashMap result             			= null;
        boolean hasSlaveSettingsFile    	= false;
        String connectionSettingsFilename 	= null;

        try { // to read slave settings
            slaveProperties = this.sosSettings.getSection("mysqld" + (++this.sosCounter));
            if (slaveProperties.isEmpty()) {
                slaveProperties = this.sosSettings.getSection("job " + spooler_job.name());
                if (!slaveProperties.isEmpty())
                    connectionSettingsFilename = slaveProperties.getProperty("slave_config_" + this.sosCounter);
                if (connectionSettingsFilename == null || connectionSettingsFilename.length() == 0) return false;
                hasSlaveSettingsFile = true;
            }
            if (slaveProperties.isEmpty()) return false;
        }
        catch (Exception e) {
            sosLogger.error("error occurred reading settings for slave no. " + this.sosCounter + ": " + e.getMessage());
            return false;
        }


        try { // to connect to slave database
            if (!hasSlaveSettingsFile) {
                slaveConnection = new SOSMySQLConnection( masterProperties.getProperty("driver"),
                                                      slaveProperties.getProperty("url"),
                                                      slaveProperties.getProperty("user"),
                                                      slaveProperties.getProperty("password") );
            } else {
                slaveConnection = SOSConnection.createInstance(connectionSettingsFilename, (SOSLogger) new SOSSchedulerLogger(spooler_log));
                slaveProperties.setProperty("url", slaveConnection.getUrl());
            }
            slaveConnection.connect();
        }
        catch (Exception e) {
            sosLogger.error("connect to database failed for slave no. " + this.sosCounter + ": " + e.getMessage());
            return true; // proceed with next slave instance
        }


        try { // to retrieve slave status
            result = slaveConnection.getSingle("SHOW SLAVE STATUS");
            if (result == null || result.isEmpty()) {
                sosLogger.error("slave no. " + this.sosCounter + " returned no result for query: SHOW SLAVE STATUS");
                return true;
            }

            sosLogger.info("\n");
            sosLogger.info("status for slave no. " + sosCounter + ": " + slaveProperties.getProperty("url"));
            sosLogger.info("Slave_IO_State: " + result.get("slave_io_state").toString());
            sosLogger.info("Master_Host: " + result.get("master_host").toString());
            sosLogger.info("Master_User: " + result.get("master_user").toString());
            sosLogger.info("Connect_Retry: " + result.get("connect_retry").toString());
            sosLogger.info("Master_Log_File: " + result.get("master_log_file").toString());
            sosLogger.info("Read_Master_Log_Pos: " + result.get("read_master_log_pos").toString());
            sosLogger.info("Relay_Log_File: " + result.get("relay_log_file").toString());
            sosLogger.info("Relay_Log_Pos: " + result.get("relay_log_pos").toString());
            sosLogger.info("Relay_Master_Log_File: " + result.get("relay_master_log_file").toString());
            sosLogger.info("Slave_IO_Running: " + result.get("slave_io_running").toString());
            sosLogger.info("Slave_SQL_Running: " + result.get("slave_sql_running").toString());
            sosLogger.info("Replicate_Do_DB: " + result.get("replicate_do_db").toString());
            sosLogger.info("Replicate_Ignore_DB: " + result.get("replicate_ignore_db").toString());
            sosLogger.info("Replicate_Do_Table: " + result.get("replicate_do_table").toString());
            sosLogger.info("Replicate_Ignore_Table: " + result.get("replicate_ignore_table").toString());
            sosLogger.info("Replicate_Wild_Do_Table: " + result.get("replicate_wild_do_table").toString());
            sosLogger.info("Replicate_Wild_Ignore_Table: " + result.get("replicate_wild_ignore_table").toString());
            sosLogger.info("Last_Errno: " + result.get("last_errno").toString());
            sosLogger.info("Last_Error: " + result.get("last_error").toString());
            sosLogger.info("Skip_Counter: " + result.get("skip_counter").toString());
            sosLogger.info("Exec_Master_Log_Pos: " + result.get("exec_master_log_pos").toString());
            sosLogger.info("Relay_Log_Space: " + result.get("relay_log_space").toString());
            sosLogger.info("Until_Condition: " + result.get("until_condition").toString());
            sosLogger.info("Until_Log_File: " + result.get("until_log_file").toString());
            sosLogger.info("Until_Log_Pos: " + result.get("until_log_pos").toString());
            sosLogger.info("Master_SSL_Allowed: " + result.get("master_ssl_allowed").toString());
            sosLogger.info("Master_SSL_CA_File: " + result.get("master_ssl_ca_file").toString());
            sosLogger.info("Master_SSL_CA_Path: " + result.get("master_ssl_ca_path").toString());
            sosLogger.info("Master_SSL_Cert: " + result.get("master_ssl_cert").toString());
            sosLogger.info("Master_SSL_Cipher: " + result.get("master_ssl_cipher").toString());
            sosLogger.info("Master_SSL_Key: " + result.get("master_ssl_key").toString());
            sosLogger.info("Seconds_Behind_Master: " + result.get("seconds_behind_master").toString());
        }
        catch (Exception e) {
            sosLogger.error("error retrieving status from database for slave no. " + this.sosCounter + ": " + e.getMessage());
            return true;
        } 
        finally {
            if (slaveConnection != null) {
                try { slaveConnection.disconnect(); } catch (Exception ex) {}
                slaveConnection = null;
            }
        }


        try { // to check slave status
            if (!result.get("slave_io_running").toString().equalsIgnoreCase("yes"))  throw new Exception("slave does not process any IO [Slave_IO_Running: " + result.get("slave_io_running").toString() + "]");
            if (!result.get("slave_sql_running").toString().equalsIgnoreCase("yes")) throw new Exception("slave does not run any SQL statements [Slave_SQL_Running: " + result.get("slave_sql_running").toString() + "]");
          
            if (!result.get("master_log_file").toString().equals(masterResult.get("file").toString())) {
                sosLogger.warn("log files for slave [" + result.get("master_log_file").toString() + "] and master [" + masterResult.get("file").toString() + "] are different");
            } else {
                if (result.get("read_master_log_pos").toString().equals(masterResult.get("position").toString())) {
                    sosLogger.info("slave no. " + sosCounter + " is up to date");
                } else {
                    sosLogger.info("slave no. " + sosCounter + " log position [" + result.get("read_master_log_pos").toString() + "] is behind master [" + masterResult.get("position").toString() + "]");
                    sosLogger.info("slave no. " + sosCounter + " is " + result.get("seconds_behind_master").toString() + "s behind master");
                }
            }
        }
        catch (Exception e) {
            sosLogger.error("slave no. " + sosCounter + " reports error: " + e.getMessage() + ". Last reported error is: " + result.get("last_error").toString());
            return true;
        }

        return true;
    }

    
    public void spooler_exit() {
        
          try {
              if (this.masterConnection != null) { 
                  this.masterConnection.disconnect();
                  this.masterConnection = null;
              }
          }
          catch (Exception e) {} // ignore this error


          try {
              if (this.masterProperties.getProperty("delay_after_error") != null) {
                  String[] delays = this.masterProperties.getProperty("delay_after_error").toString().split(";");
                  if (delays.length > 0) spooler_job.clear_delay_after_error();
                  for(int i=0; i<delays.length; i++) {
                      String[] delay = delays[i].split(":");
                      spooler_job.set_delay_after_error(Integer.parseInt(delay[0]),  delay[1]);
                  }
              } else {
                  spooler_job.set_delay_after_error( 1,    60);
                  spooler_job.set_delay_after_error( 5,   600);
                  spooler_job.set_delay_after_error( 10, 1800);
                  spooler_job.set_delay_after_error( 20, 3600);
                  spooler_job.set_delay_after_error(100, "STOP");
              }
          }
          catch (Exception e) {} // ignore this error
      }
    
}
