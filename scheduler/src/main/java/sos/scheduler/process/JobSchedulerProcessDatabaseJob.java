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
package sos.scheduler.process;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import sos.connection.SOSConnection;
import sos.spooler.Order;
import sos.util.SOSSchedulerLogger;

/**
 * <p>JobSchedulerProcessDatabaseJob implements an order driven job that executes database statements.</p>
 * 
 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05 
 * @deprecated use sos.scheduler.managed.JobSchedulerManagedDatabaseJob
 */

public class JobSchedulerProcessDatabaseJob extends ProcessOrderJob {

    
    protected String command = "";
    
    
    /**
     * Verarbeitung
     * @see sos.spooler.Job_impl#spooler_process()
     */
    public boolean spooler_process() {

        Order order    = null;
        String orderId = "(none)";
        boolean rc     = true;
        
        try {

            try { 
                this.setLogger(new SOSSchedulerLogger(spooler_log));
                if (spooler_job.order_queue() != null) {
                    order = spooler_task.order();
                    orderId = order.id();
                
                    if (order.params().value("configuration_path") != null && order.params().value("configuration_path").length() > 0) {
                        this.setConfigurationPath(order.params().value("configuration_path"));
                    } else if (spooler_task.params().value("configuration_path") != null && spooler_task.params().value("configuration_path").length() > 0) {
                        this.setConfigurationPath(spooler_task.params().value("configuration_path"));
                    }

                    if (order.params().value("configuration_file") != null && order.params().value("configuration_file").length() > 0) {
                        this.setConfigurationFilename(order.params().value("configuration_file"));
                    } else if (spooler_task.params().value("configuration_file") != null && spooler_task.params().value("configuration_file").length() > 0) {
                        this.setConfigurationFilename(spooler_task.params().value("configuration_file"));
                    }

                    // load and assign configuration
                    this.initConfiguration();
                }

                // prepare parameters and attributes
                this.prepare();
                
                
                /* mandatory parameters */
                if (this.getParameters().value("db_class") == null || this.getParameters().value("db_class").length() == 0)
                    throw new Exception("no parameter [db_class] for database connection class was specified");
                if (this.getParameters().value("db_driver") == null || this.getParameters().value("db_driver").length() == 0)
                    throw new Exception("no parameter [db_driver] for database JDBC driver was specified");
                if (this.getParameters().value("db_url") == null || this.getParameters().value("db_url").length() == 0)
                    throw new Exception("no parameter [db_url] for database connection url was specified");
                if (this.getParameters().value("db_user") == null || this.getParameters().value("db_user").length() == 0)
                    throw new Exception("no parameter [db_user] for database connection user was specified");
                if (this.getParameters().value("db_password") == null)
                    throw new Exception("no parameter [db_password] for database connection password was specified");

                if (this.getParameters().value("command") != null && this.getParameters().value("command").length() > 0) {
                    this.setCommand(this.getParameters().value("command"));
                } else {
                    throw new Exception("no parameter [command] has been specified");
                }

            } catch (Exception e) {
                throw new Exception("error occurred preparing order: " + e.getMessage());
            }
            
            
            try { // to process order
                
                try { // to get the database connection
                    if (this.getConnection() != null)
                        try { this.getConnection().rollback(); this.getConnection().disconnect(); } catch (Exception ex) {} // gracefully ignore this error

                    this.getLogger().debug3("connecting to database ...");
                    this.setConnection(SOSConnection.createInstance( this.getParameters().value("db_class"), 
                                                                     this.getParameters().value("db_driver"),
                                                                     this.getParameters().value("db_url"),
                                                                     this.getParameters().value("db_user"),
                                                                     this.getParameters().value("db_password"),
                                                                     this.getLogger() ));
                    this.getConnection().connect();
                    this.getLogger().debug3("connected to database");

                } catch (Exception e) {
                  throw (new Exception("connect to database failed: " + e.getMessage()));
                }

                
                try { // to process the database command
                    String[] parameterNames = this.getParameters().names().split(";");
                    for(int i=0; i<parameterNames.length; i++) {
                        this.setCommand( this.getCommand().replaceAll("\\$\\{" + parameterNames[i] + "\\}" , this.getParameters().value(parameterNames[i])) );
                    }
                    
                    this.getLogger().info("executing database command: " + this.getCommand());
                    this.executeStatements(this.getConnection(), this.getCommand());

                    if (this.getConnection().getResultSet() != null) {
                    	String warning = "";
                    	HashMap result = null;
                    	while( !(result = this.getConnection().get()).isEmpty()) {
                    		warning = "execution terminated with warning:";
                    		Iterator resultIterator = result.keySet().iterator();
                    		while(resultIterator.hasNext()) {
                    			String key = (String) resultIterator.next();
                    			if (key == null || key.length() == 0) continue;
                    			warning += " " + key + "=" + (String) result.get(key).toString();
                    		}
                    	}
                        if (warning != null && warning.length() > 0) {
                    	  this.getLogger().warn(warning);
                        }
                    }
                    
                } catch (Exception e) {
                    throw (new Exception("database command failed: " + e.getMessage()));
                }

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
            return (spooler_task.job().order_queue() != null) ? rc : false;
            
        } catch (Exception e) {
            spooler_log.warn("error occurred processing order [" + orderId + "]: " + e.getMessage());
            return false;
        } finally {
            // cleanup parameters and attributes
            try { this.cleanup(); } catch (Exception e) {};
            if (this.getConnection() != null) try { this.getConnection().rollback(); this.getConnection().disconnect(); } catch (Exception e) {} // gracefully ignore this error
        }
    }


    /**
     * <p>Die übergebene SOSConnection ist bereits verbunden, sollte aber nach
     * Ausführung der Statements via commit/rollback abgeschlossen werden. 
     * Die Standardimplementierung der Funktion ist:</p>
     * <p><code>connection.executeStatements(command);</p>           
     * 
     * @param connection bereits verbundene SOSConnection
     * @param command Datenbankkommando 
     * @throws Exception
     * @see SOSConnection
     */
    protected void executeStatements(SOSConnection connection, String command) throws Exception {

    	Exception exception = null;
    	
    	try { 
    		connection.executeStatements(command);
        } catch (Exception e) { exception = e;} // gracefully ignore errors from dbms output processing
    		

        try {
            Vector output = connection.getOutput();
            if (output.size() > 0){
                this.getLogger().info("output from database server:");
                Iterator it = output.iterator();
                while (it.hasNext()){
                    String line = (String) it.next();
                    this.getLogger().info("  " + line);
                }
            } else {
                this.getLogger().debug9("no output from database server.");
            }
        } catch (Exception e) {} // gracefully ignore errors from dbms output processing
        
        
        if (exception != null) throw new Exception(exception);
    }

    
    /**
     * @return Returns the command.
     */
    public String getCommand() {
        return command;
    }


    /**
     * @param command The command to set.
     */
    public void setCommand(String command) {
        this.command = command;
    }
    
}
