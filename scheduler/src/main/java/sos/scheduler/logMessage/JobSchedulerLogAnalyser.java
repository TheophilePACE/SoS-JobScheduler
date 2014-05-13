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
package sos.scheduler.logMessage;

import java.io.File; 
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;



import sos.connection.SOSConnection;
import sos.scheduler.job.JobSchedulerJob;
import sos.settings.SOSProfileSettings;
 
import sos.spooler.Order;
import sos.spooler.Variable_set;


import sos.util.SOSArguments;
import sos.util.SOSClassUtil;
import sos.util.SOSDate;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;
import sos.util.SOSString;



/**
 * @author uwe.risse@sos-berlin.com
 * @author andreas.pueschel@sos-berlin.com
 * @author mueruevet.oeksuez@sos-berlin.com
 *
 * 
 * see job documentation in the package jobdoc for details
 */
public class JobSchedulerLogAnalyser extends JobSchedulerJob {
   
 	
	/** optional name for this job, defaults to scheduler job name */
	protected   String       monitorJob                  = "JobSchedulerMonitorMessageJob";
	
	/** optional message type: currently "status" and "reset" supported */
	protected   String       messageType                 = "status";
	
  
	/** sos.util.SOSString Object */
	private     SOSString    sosString                   = new SOSString();
	
	/** optional nur der Job-Name soll �berpr�ft werden*/
	private     String       monitorJobname              = "";
	
	/** optional nur der Jobkette soll �berpr�ft werden*/
	private     String       monitorJobChainname         = "";

	
	/** Parameter maximum_lifetime gibt die Lebensdauer einer Messages an. Die Messages kann in
	 * 
	 *  maximum_lifetime=hh24
	 *  maximum_lifetime=hh24:mi
	 *  maximum_lifetime=hh24:mi:ss
	 *  
	 *  angegeben werden.
	 */
	private     String       maxLifeTime                 = null;

	
	
	/** optional, default = false, Loggen von Fehlern und Warnungen */
	private     boolean      logMessages                 = false;
	
 
		
	private     String       logFileName                 = "";  
	private     String       dbProperty                  = "";
	private     String       dbClass                     = "";
	
	private     Order  	     order                       = null;
	private     SOSLogger    sosLogger                   = null;
	private     Vector       messages                    = null;

	public boolean spooler_init() {


		
	   
	   try {
		 sosLogger = new SOSSchedulerLogger(spooler_log);

     	this.setJobSettings(new SOSProfileSettings(spooler.ini_path()));
        this.setJobProperties(this.getJobSettings().getSection("spooler"));
        if (this.getJobProperties().isEmpty()) 
            throw new Exception("no settings found in section [spooler] of configuration file: " + spooler.ini_path());
        if (this.getJobProperties().getProperty("db") == null || this.getJobProperties().getProperty("db").length() == 0) 
            throw new Exception("no settings found for entry [db] in section [spooler] of configuration file: " + spooler.ini_path());
        if (this.getJobProperties().getProperty("db_class") == null || this.getJobProperties().getProperty("db_class").length() == 0) 
            throw new Exception("no settings found for entry [db_class] in section [spooler] of configuration file: " + spooler.ini_path());
                        
        if (this.getLogger() != null) sosLogger.debug6("connecting to database.. .");

         
	       if (spooler_task.params().var("db") != null && spooler_task.params().var("db").length() > 0) {
	          this.setDbProperty(spooler_task.params().var("db"));
	          spooler_log.debug1(".. job parameter [db]: " + this.getDbProperty());
	      }
	       if (spooler_task.params().var("db_class") != null && spooler_task.params().var("db_class").length() > 0) {
	          this.setDbClass(spooler_task.params().var("db_class"));
	          spooler_log.debug1(".. job parameter [db_class]: " + this.getDbClass());
	      }

        SOSArguments arguments = new SOSArguments(this.getDbProperty());

        SOSConnection conn;
        
        if (sosLogger!=null){
            conn = getSchedulerConnection(this.getJobSettings(),sosLogger);
        }else{
            conn = getSchedulerConnection(this.getJobSettings());
        }
        
        if (spooler_task.params().var("db") != null && spooler_task.params().var("db").length() > 0) {
           if (sosLogger!=null){
            	conn = SOSConnection.createInstance(  
        		    dbClass,
                	arguments.as_string("-class=", ""),
                	arguments.as_string("-url=", ""),
                	arguments.as_string("-user=", ""),
                	arguments.as_string("-password=", ""),
                	sosLogger );
           } else{
        	    conn = SOSConnection.createInstance(  
					dbClass,
					arguments.as_string("-class=", ""),
					arguments.as_string("-url=", ""),
					arguments.as_string("-user=", ""),
					arguments.as_string("-password=", ""));
        }
        }
        if (spooler_job!=null && getJobSettings()!=null) setJobProperties(getJobSettings().getSection("job " + spooler_job.name()));            
        if (spooler_task != null) this.setJobId(spooler_task.id());            
        if (spooler_job != null) this.setJobName(spooler_job.name());
		if (spooler_job != null) this.setJobTitle(spooler_job.title());
		
        this.setConnection(conn);      
        this.getConnection().connect();
  
	   return true;
	   } catch (Exception e) {
			e.printStackTrace();
			return false;
		  }
	}
	/**
	 * check scheduler log file for warnings and errors
	 * 
	 */	
	public void readLogMessages() {
		
	    messages = new Vector();

		SchedulerMessage message = null;
		
		//this.messageVariables = new Vector();
	 	
		try {
			
			long logFilepointer = 0;
			String logFilename = "";
			String logLine = "";
			File logFile = null;
			File prevLogFile = null;
			RandomAccessFile checkFile = null;
			RandomAccessFile checkPrevFile = null;
			
			 
				
			
			if (this.getMessageType().equalsIgnoreCase("reset")) {
				spooler_log.info("message stack has been reset");
				this.getConnection().execute("update SCHEDULER_MESSAGES set \"STATUS\" = -1 where \"STATUS\">=0");
				this.getConnection().commit();
			}
			
			/* Liste der Meldungen f�llen
			 * Alte Meldungen dabei nicht aufnehmen
			 * Zu ignorierende Meldungen nicht aufnehmen

			 */
		  
            
			messages = actualizeOldMessages(messages, logFile );
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  			Date lastLog_date=null;
             try{
               String lastLog = this.getConnection().getSingleValue("select max(\"LOGTIME\") as youngest from SCHEDULER_MESSAGES");
               lastLog_date =  df.parse(lastLog);
            }catch (Exception e){
               lastLog_date = df.parse("2000-01-01 00:00:00");;
            }
            GregorianCalendar gclast = new GregorianCalendar(
            	  lastLog_date.getYear()+1900,
            	  lastLog_date.getMonth(),
            	  lastLog_date.getDate(),
            	  lastLog_date.getHours(),
            	  lastLog_date.getMinutes(),
            	  lastLog_date.getSeconds());
    		
			try { // to process possibly partially checked previous log files
				String logFilepointerVariable = spooler.var(this.getMonitorJob() + ".filepointer");
				String logFilenameVariable    = spooler.var(this.getMonitorJob() + ".filename");
				
				
				if (logFilepointerVariable != null && logFilepointerVariable.length() > 0) 
					logFilepointer = Long.parseLong(logFilepointerVariable);
				if (logFilenameVariable != null && logFilenameVariable.length() > 0) logFilename = logFilenameVariable;
				
				// on previous execution of this job a different log file may have been partially checked
				if (logFilename != null && logFilename.length() > 0 && !logFilename.equals(new File(spooler.log().filename()).getCanonicalPath() )) {					
					prevLogFile = new File(logFilename);
					if (!prevLogFile.exists()) {
						spooler_log.info("log file from previous job execution not found: " + prevLogFile.getCanonicalPath() );
					}
					
					checkPrevFile = new RandomAccessFile(prevLogFile, "r");
					if (logFilepointer > 0) {
						checkPrevFile.seek(logFilepointer);
						spooler_log.debug3("starting to check previous log file [" + prevLogFile.getCanonicalPath() + "] from position: " + logFilepointer);
					}
					//alte Fehlermeldung merken
					while((logLine = checkPrevFile.readLine()) != null) {
						if (logLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[WARN\\] +.*" )) {
							message = new SchedulerMessage(logFile, "WARN",logLine,spooler.id(), this.getConnection(),sosLogger);
							spooler_log.debug9("..old WARN =" + logLine);
							if (!messages.contains(message))
							   messages.add(message);
						} else if (logLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[ERROR\\] +.*" )) {
							message = new SchedulerMessage(logFile, "ERROR",logLine,spooler.id(), this.getConnection(), sosLogger);
							spooler_log.debug9("..old ERROR =" + logLine);
							if (!messages.contains(message))
							   messages.add(message);
						}
					}
					
					spooler_log.debug3("previous log file [" + prevLogFile.getCanonicalPath() + "] processed");
					logFilepointer = 0;
					logFilename = "";
				}
			} catch (Exception e) { // ignore all errors when processing previous log files (log files may have been gzipped or removed)
				spooler_log.info("previous log file [" + ((prevLogFile != null) ? prevLogFile.getCanonicalPath() : "") + "] processed with errors: " + e.getMessage());
				logFilepointer = 0;
				logFilename = "";
			} finally {
				if (checkPrevFile != null ) try { checkPrevFile.close(); } catch (Exception ex) {} // gracefully ignore this error
			}
			
			
			try {
				
				if(sosString.parseToString(getLogFilename()).length() > 0) {
					logFile = new File(sosString.parseToString(getLogFilename()));
				} else {				
					logFile = new File(spooler.log().filename());
				}
				if (!logFile.exists()) {
					throw new Exception("log file not found: " + logFile.getCanonicalPath() );
				}
				
				checkFile = new RandomAccessFile(logFile, "r");
				if (logFilepointer > 0) {
					checkFile.seek(logFilepointer);
					spooler_log.debug3("starting to check current log file [" + logFile.getCanonicalPath() + "] from position: " + logFilepointer);
				}
				
				ArrayList listOfMonitoringJobs = new ArrayList();
				if(sosString.parseToString(getMonitorJobname()).length()> 0)  {
					String[] split = getMonitorJobname().split(";");
					for (int i = 0; i < split.length; i++){
						if(sosString.parseToString(split[i]).length()>0)
							listOfMonitoringJobs.add(split[i]);
					}
					spooler_log.debug("..monitoring job: " + getMonitorJobname());
				}
				
				ArrayList listOfMonitoringJobChains = new ArrayList();
				if(sosString.parseToString(getMonitorJobChainname()).length()> 0)  {
					String[] split = getMonitorJobChainname().split(";");
					for (int i = 0; i < split.length; i++) {
						if(sosString.parseToString(split[i]).length()>0)
							listOfMonitoringJobChains.add(split[i]);
					}
					spooler_log.debug("..monitoring jobchain: " + getMonitorJobChainname());
				}
				
				
				String currLogJobName = "";
				String currOrderName = "";
				
				int countOfLines = 0;
				
				HashMap jobnames  = new HashMap(); 
				HashMap tasksList  = new HashMap();
				
				while((logLine = checkFile.readLine()) != null) {
				
				   if (logLine.indexOf("[debug") > -1) {
					  continue;
				   }
					if(sosString.parseToString(getMonitorJobname()).length() > 0 &&
							!isMonitorJobname(logLine, listOfMonitoringJobs)){
						countOfLines++;
						continue;
					}
					
					if(sosString.parseToString(getMonitorJobChainname()).length() > 0 &&
							!isMonitorJobChainname(logLine, listOfMonitoringJobChains)){
						countOfLines++;
						continue;
					}
					
				   SchedulerMessage m = new SchedulerMessage(logFile, logLine,this.getConnection(),sosLogger);
				   m.scheduler_id = spooler.id();
  	    		   Date actlog_date =  df.parse(m.logtime);

				   
				   if (lastLog_date.after(actlog_date)  ) {
					  sosLogger.debug6("Message to old. Ignored: " + m.logFile);
					  continue;
				   }

 					if(logLine.indexOf("SCHEDULER-930  Task ") > -1) {
						ArrayList stateList = new ArrayList();
						stateList.add(m);						
					
						if(logLine.toLowerCase().indexOf("cause: order") > -1) {
							int task = m.getTask();
							tasksList.put(String.valueOf(task), m);
						}
						
					    removeJobFromMessages(m,gclast);
						jobnames.put(m.getJob_name(), stateList);
						spooler_log.debug6("..monitoring from [job=" + m.getJob_name() + "]");
					}
	  
					if (logLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[WARN\\] +.*" )  ) {
						int task = m.getTask();	
                        removeJobChainFromMessages(m,gclast);
						
						if (tasksList.get(String.valueOf(task)) != null) {
 						    m.job_name = ((SchedulerMessage)tasksList.get(String.valueOf(task))).getJob_name();
						}
						currLogJobName = m.getJob_name();
						ArrayList stateList = (ArrayList)jobnames.get(currLogJobName);
						if(stateList == null){
							stateList = new ArrayList();
                            removeJobFromMessages(m,gclast);
							jobnames.put(currLogJobName, stateList);
						}
						m.severity = "WARN";
						stateList.add(m);
					} else if (logLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[ERROR\\] +.*" )) {

						int task = m.getTask();	

                        removeJobChainFromMessages(m,gclast);


						if (tasksList.get(String.valueOf(task)) != null) {
							m.job_name = ((SchedulerMessage)tasksList.get(String.valueOf(task))).getJob_name();
						}
						currLogJobName = m.getJob_name();
						ArrayList stateList = (ArrayList)jobnames.get(currLogJobName);
						if(stateList == null){
							stateList = new ArrayList();
							removeJobFromMessages(m,gclast);
							jobnames.put(currLogJobName, stateList);
						}
						m.severity = "ERROR";
						stateList.add(m);					
					}
				}
				
				if(countOfLines > 0) {						
					spooler_log.debug(".." + countOfLines + " line skip, cause no monitoring job");
					countOfLines = 0;
				}
				
				
				Iterator itKeys = jobnames.keySet().iterator();
				while(itKeys.hasNext()) {

				    currLogJobName = sosString.parseToString(itKeys.next());
				    ArrayList stateList = (ArrayList)jobnames.get(currLogJobName);
					currOrderName = "";
					for (int i = 0; i < stateList.size(); i++) {		
					    SchedulerMessage actMessage = (SchedulerMessage)stateList.get(i);
						String msgLine = actMessage.getLog();
						if(msgLine.indexOf("SCHEDULER-930  Task ") > -1 && 
								msgLine.toLowerCase().indexOf("cause: order") > -1) {							
							int pos1 = msgLine.toLowerCase().indexOf("cause: order") + "cause: order".length() +1;
							if(pos1 > -1)
							    currOrderName = actMessage.getOrder_id();
//						currOrderName = msgLine.substring(pos1);
						} 
						if(actMessage.severity.equalsIgnoreCase("WARN") || msgLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[WARN\\] +.*")){
							message = new SchedulerMessage(logFile, "WARN",msgLine,spooler.id(), this.getConnection(), sosLogger);	
							if(sosString.parseToString(currOrderName).length() > 0) {
								message.order_id = currOrderName;
							}
							spooler_log.debug9("..new WARN= " + msgLine);
							if (!messages.contains(message))messages.add(message);
						} else if(actMessage.severity.equalsIgnoreCase("ERROR") || msgLine.matches( "^\\d{4}-\\d{1,2}-\\d{1,2} +\\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,3})? +\\[ERROR\\] +.*")){
							message = new SchedulerMessage(logFile, "ERROR",msgLine,spooler.id(), this.getConnection(), sosLogger);	
													
							if(sosString.parseToString(currOrderName).length() > 0) {
								message.order_id = currOrderName;
							}
							spooler_log.debug9("..new ERROR= " + msgLine);
							if (!messages.contains(message))
							   messages.add(message);
						}
					}
				}
				

				Vector curMsg = new Vector();
				Iterator logMsg = messages.iterator();
				SchedulerMessage m = null;                
				while (logMsg.hasNext()) {
					m = (SchedulerMessage)logMsg.next();
					if(m.message_id >= 0) {
						curMsg.add(m);                		
					}
				}
				messages = curMsg;
				logMsg = messages.iterator();

				if(this.isLogMessages() || spooler_log.level() <= -3) {
					spooler_log.info("The following warnings and errors are reported:");
					while (logMsg.hasNext()) {
						m = (SchedulerMessage)logMsg.next();                	                							
						spooler_log.info(transformString(m));
					}
				}

				spooler.set_var(this.getMonitorJob() + ".filename",    logFile.getCanonicalPath());
				spooler.set_var(this.getMonitorJob() + ".filepointer", Long.toString(checkFile.getFilePointer()));
				spooler_log.debug3("current log file [" + logFile.getCanonicalPath() + "] processed to position: " + Long.toString(checkFile.getFilePointer()));
				
				 
			} catch (Exception e) {            	
				throw new Exception(e);
			} finally {
				if (checkFile != null ) try { checkFile.close(); } catch (Exception ex) {} // gracefully ignore this error
			}
			
		} catch (Exception e){
			spooler_log.info("getLogMessages(): failed to check messages in log file [" + spooler.log().filename() + "]: " + e.getMessage());
 		}
	}
	 
	private Vector actualizeOldMessages(Vector messages, File logFile ) throws Exception {
    	Iterator resultset=null;
		HashMap rec=null;
		
		String selStr = " select * from SCHEDULER_MESSAGES";
	 

		ArrayList arrayList = new ArrayList();
		arrayList = this.getConnection().getArray(selStr);
		resultset = arrayList.iterator();
	 
		while (resultset.hasNext()) {
			rec = (HashMap) resultset.next();
			SchedulerMessage message = new SchedulerMessage(logFile, rec,this.getConnection(),sosLogger);
  	     
			if (!isValidMessages(message))   {
     		    message.status=-1;
          	    message.save();
			}
    	 
    	    messages.add(message);
          }
		return messages;
        
	} 
	
  	
	
	/**
	 * Extrahiert den Auftragsname (=job_chain) von der Logzeile
	 * 
	 * @param logLine
	 * @return String
	 * @throws Exception
	 */
	private String getCurrentOrdername(String logLine) throws Exception {
		String currLogOrderName = "";
		try {    					
			if(logLine.indexOf("SCHEDULER-930  Task ") > -1 && logLine.toLowerCase().indexOf("cause: order") == -1) {
				int sPos = logLine.toLowerCase().indexOf("cause: order") + 12;
				int ePos = logLine.substring(sPos).indexOf(":");
				currLogOrderName = logLine.substring(sPos, sPos + ePos);
			}
			return currLogOrderName ;
		} catch (Exception e) {
			spooler_log.info("error in " + SOSClassUtil.getMethodName() + ": " + e.getMessage());
			return currLogOrderName;
		}
	}
	
	 	
	
	/**
	 * check scheduler log file for warnings and errors
	 */
	
	public boolean spooler_process() {
		
		try {
		
		   spooler_log.info("...spooler_process()");
          
       	   order = spooler_task.order();
		   getParameters();

			
			
			try { // to process messages
				int countMessages = 0;
				int countNewMessages = 0;
				int countInfos  = 0;
				int countNewInfos  = 0;
				int countWarnings = 0;
				int countErrors = 0;
				int countNewWarnings = 0;
				int countNewErrors = 0;
				
				if (sosString.parseToString(getMonitorJobname()).length() > 0 && 
						sosString.parseToString(getMonitorJobChainname()).length() > 0) {
					throw new Exception ("..parameter [monitor_job_chain_name] and [monitor_job_name] at same time is not allowed");
				}
				
			    readLogMessages();
				
				if (messages != null && !messages.isEmpty()) {
					String xml_payload = "";
					Iterator messageIterator = messages.iterator();

					while(messageIterator.hasNext()) {
					   
						try {
						   SchedulerMessage scheduler_message = (SchedulerMessage) messageIterator.next();
  						   if (scheduler_message == null || scheduler_message.getLog().equals("")) continue;
						   scheduler_message.insert();
						   countMessages++;
						   if (scheduler_message.getSeverity().toLowerCase().startsWith("error"))countErrors++;
						   if (scheduler_message.getSeverity().toLowerCase().startsWith("warn"))countWarnings++;
						   if (scheduler_message.isNew){
							   countNewMessages++;
							   if (scheduler_message.getSeverity().toLowerCase().startsWith("error"))countNewErrors++;
							   if (scheduler_message.getSeverity().toLowerCase().startsWith("warn"))countNewWarnings++;
						   }
						}catch (ClassCastException e){
						   spooler_log.info("message ignored . . .");
  						   
						}
	 					 
					}
					
					if (spooler_task.job().order_queue() != null) {
						order.set_xml_payload("<?xml version='1.0' encoding='iso-8859-1'?><xml_payload><messages errors='" + countErrors + "' warnings='" + countWarnings + "' infos='" + countInfos + "'>" + xml_payload + "</messages></xml_payload>");
					} else {
						spooler_task.params().set_var("errors", Integer.toString(countErrors));
						spooler_task.params().set_var("warnings", Integer.toString(countWarnings));
						spooler_task.params().set_var("infos", Integer.toString(countInfos));
						spooler_task.params().set_var("newErrors", Integer.toString(countNewErrors));
						spooler_task.params().set_var("newWarnings", Integer.toString(countNewWarnings));
						spooler_task.params().set_var("newInfos", Integer.toString(countNewInfos));
					}
				}
				
				spooler_log.info(countErrors + " errors (new:" + countNewErrors + "), " + countWarnings + " warnings (new: " + countNewWarnings + ") found");
				this.getConnection().execute("COMMIT");

			} catch (Exception e) {
				try{this.getConnection().execute("ROLLBACK");}catch(Exception ee){}
				throw new Exception("error occurred processing messages: " + e.getMessage());                
			}
			
			
 		
			// return value for classic and order driven processing
			return (spooler_task.job().order_queue() != null);
			
		} catch (Exception e) {
			try{this.getConnection().execute("ROLLBACK");}catch(Exception ee){}
			spooler_log.info("spooler_process(): " + e.getMessage());
			return false;
		}
	}
	
	private void getParameters() throws Exception {
		Variable_set orderData = null;

		try { // to fetch parameters from the job and from orders that have precedence to job parameters

           // start processing job parameters
           if (spooler_task.params().var("job") != null && spooler_task.params().var("job").length() > 0) {
               this.setMonitorJob(spooler_task.params().var("job"));
               spooler_log.debug1(".. job parameter [job]: " + this.getMonitorJob());
           } else if (this.getMonitorJob() == null || this.getMonitorJob().length() == 0){
               this.setMonitorJob(spooler_job.name());
           }
           
           if (spooler_task.params().var("type") != null && spooler_task.params().var("type").length() > 0) {
               this.setMessageType(spooler_task.params().var("type"));
               spooler_log.debug1(".. job parameter [type]: " + this.getMessageType());
           }
        
           
           if (sosString.parseToString(spooler_task.params().var("monitor_job_name")).length() > 0) {
               this.setMonitorJobname(spooler_task.params().var("monitor_job_name"));
               spooler_log.debug1(".. job parameter [monitor_job_name]: " + this.getMonitorJobname());
           } else {
               this.setMonitorJobname("");
           }
           
           if (sosString.parseToString(spooler_task.params().var("monitor_job_chain_name")).length() > 0) {
               this.setMonitorJobChainname(spooler_task.params().var("monitor_job_chain_name"));
               spooler_log.debug1(".. job parameter [monitor_job_chain_name]: " + this.getMonitorJobChainname());
           } else {
               this.setMonitorJobChainname("");
           }
           
           
           if (sosString.parseToString(spooler_task.params().var("log_messages")).length() > 0) {
               this.setLogMessages(sosString.parseToBoolean(spooler_task.params().var("log_messages")));
               spooler_log.debug1(".. job parameter [log_messages]: " + this.isLogMessages());
           }
           
       
           if (sosString.parseToString(spooler_task.params().var("maximum_lifetime")).length() > 0) {
              this.setMaxLifeTime(sosString.parseToString(spooler_task.params().var("maximum_lifetime")));
              spooler_log.debug1(".. job parameter [maximum_lifetime]: " + this.getMaxLifeTime());
          } else {
              this.setMaxLifeTime("");
          }
           
     
           
           if (sosString.parseToString(spooler_task.params().var("log_file")).length() > 0) {
               this.setLogFilename(sosString.parseToString(spooler_task.params().var("log_file")));
               spooler_log.debug1(".. job parameter [log_file]: " + this.getLogFilename());
           } else {
               this.setLogFilename("");
           }
           

           // now fetch the order parameters
           if (spooler_task.job().order_queue() != null) {
				
 				if (order != null) {
					orderData = order.params();
					
					if ( orderData.var("type") != null && orderData.var("type").toString().length() > 0) {
						this.setMessageType(orderData.var("type").toString());
						spooler_log.debug1(".. order parameter [type]: " + this.getMessageType());
					}
					
		 							
					if(orderData != null && sosString.parseToString(orderData.var("monitor_job_name")).length() > 0) {
						this.setMonitorJobname(sosString.parseToString(orderData.var("monitor_job_name")));
						spooler_log.debug1(".. order parameter [monitor_job_name]: " + this.getMonitorJobname() );
					} else {
						this.setMonitorJobname("");
					}
					
					if(orderData != null && sosString.parseToString(orderData.var("monitor_job_chain_name")).length() > 0) {
						this.setMonitorJobChainname(sosString.parseToString(orderData.var("monitor_job_chain_name")));
						spooler_log.debug1(".. order parameter [monitor_job_chain_name]: " + this.getMonitorJobChainname() );
					} else {
						this.setMonitorJobChainname("");
					}
					
					if(orderData != null && sosString.parseToString(orderData.var("log_messages")).length() > 0) {
						this.setLogMessages(sosString.parseToBoolean(orderData.var("log_messages")));
						spooler_log.debug1(".. order parameter [log_messages]: " + this.isLogMessages() );
					}
					
 		
 
					
					if(orderData != null && sosString.parseToString(orderData.var("log_file")).length() > 0) {
						setLogFilename(sosString.parseToString(orderData.var("log_file")));
						spooler_log.debug1(".. order parameter [log_file]: " + this.getLogFilename() );
					} else {
						setLogFilename("");
					}
					
				}
				
 
			}
		} catch (Exception e) {
			throw new Exception("error occurred processing parameters: " + e.toString());
		}
	}

	
	
	
	/**
	 * @return Returns the monitorJob.
	 */
	private String getMonitorJob() {
		return monitorJob;
	}
	
	
	/**
	 * @param monitorJob The monitorJob to set.
	 */
	private void setMonitorJob(String monitorJob) {
		this.monitorJob = monitorJob;
	}
	
 	
	/**
	 * @return Returns the messageType.
	 */
	private String getMessageType() {
		return messageType;
	}
	
	
	/**
	 * @param messageType The messageType to set.
	 */
	private void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
	private String getMonitorJobChainname() {
		return monitorJobChainname;
	}
	
	
	private void setMonitorJobChainname(String monitorJobChainname) {
		this.monitorJobChainname = monitorJobChainname;
	}
	
	
	private String getMonitorJobname() {
		return monitorJobname;
	}
	
	
	private void setMonitorJobname(String monitorJobname) {
		this.monitorJobname = monitorJobname;
	}
	
	
	private void setLogMessages(boolean logMessages) {
		this.logMessages = logMessages;
	}
	
	private boolean isLogMessages() {
		return logMessages;
	}
 
	public String getMaxLifeTime() {
		return maxLifeTime;
	}
	
	public void setMaxLifeTime(String maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}	
		
	private String transformString(SchedulerMessage h){
		String retVal        = "";
			
			 
    		if(h.order_id != null && !h.order_id.equals("")) {
				retVal = "[Timestamp: " + h.logtime + "] " +"["+ h.severity + "] " +
				"[Job Chain:" + h.job_chain + ", " +
		    	"Job: " + h.job_name + ":" + h.task + "] " + h.getLog();
			} else {
				retVal = "[Timestamp: " + h.logtime + "] ["+ h.severity + "] [Job:" + h.job_name + "] " + h.getLog();
			}
					
			return retVal;
		
	}
  
 	
	private boolean isMonitorJobname(String logLine, ArrayList listOfMonitoringJobs){
		try {
			return listOfMonitoringJobs.isEmpty() || listOfMonitoringJobs.contains(logLine.replaceFirst(".*\\(Job\\s+([^\\)]+).*","$1"));
		} catch (Exception e) {
			return true;
		}
	}
	
	private boolean isMonitorJobChainname(String logLine, ArrayList listOfMonitoringJobChains){
		try {
	       return listOfMonitoringJobChains.isEmpty() || listOfMonitoringJobChains.contains(getCurrentOrdername(logLine));
 
		} catch (Exception e) {
			return true;
		}
	}
		public String getLogFilename() {
		return logFileName;
	}


	public void setLogFilename(String logFilename) {
		this.logFileName = logFilename;
	}

	private boolean  isValidMessages(SchedulerMessage curContent) throws Exception {
		try {
			return !isOutdatedLifeTime(curContent);
		} catch (Exception e) {
			spooler_log.info("..error in " + SOSClassUtil.getMethodName() + " cause: " + e.getMessage());
			return true;
		}
	}
	
 
	
 
	private boolean isOutdatedLifeTime(SchedulerMessage message) throws Exception {
		String format = "yyyy-MM-dd HH:mm:ss";
		String curContent = message.getLog();
		java.util.Date msgTime = null;
		String msgsTime = "";					
		String[] splitTime = null; 
		Calendar cal = null;
		
		try {
			
			if (sosString.parseToString(curContent).length() == 0) {
				return false;
			}
			if(sosString.parseToString(getMaxLifeTime()).length() > 0) {
				
				if(curContent.toUpperCase().indexOf(".")> -1)
					msgsTime = curContent.substring(0, curContent.toUpperCase().indexOf("."));
				if(sosString.parseToString(msgsTime).length() > 0) {
					SOSDate.setDateTimeFormat(format);
					
					msgTime = SOSDate.getDate(msgsTime, format);
					
					cal = Calendar.getInstance();
					
					cal.setTime(msgTime);
					
					
					splitTime = getMaxLifeTime().split(":");    				 
					if(splitTime.length == 3) {
						cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
						
						cal.add(Calendar.MINUTE, Integer.parseInt(splitTime[1]));
						cal.add(Calendar.SECOND, Integer.parseInt(splitTime[2]));    					 
					} else if(splitTime.length == 2) {
						cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
						cal.add(Calendar.MINUTE, Integer.parseInt(splitTime[1]));					
					} else if(splitTime.length == 1) {
						cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[0]));
					}
					spooler_log.debug9("message time with add maximum_lifetime " + cal.getTime() +  "current Time: " + SOSDate.getCurrentTime());
					
					if(cal.getTime().before(SOSDate.getCurrentTime())) {						
						spooler_log.info("..the following warnings and errors were dropped due to lifetime constrains (" + getMaxLifeTime() + ") for [log_line=" + curContent + "]");					
						return true;
					} 
					
				}
			}
			return false;
		} catch (Exception e) {
			spooler_log.info("error in isOutdatedLifeTime: " + e.getMessage() + " [log line="+ curContent + "], [message_time="+ msgsTime+"]");
			return false;
		}
	}
		
	
	private void removeJobFromMessages(SchedulerMessage m,GregorianCalendar gclast) throws Exception {
//			remove old messages cause successfully process
		    this.getConnection().execute("update SCHEDULER_MESSAGES set \"STATUS\" = -3 where \"LOGTIME\" <= %timestamp_iso('"
				+ SOSDate.getISODateTimeAsString(gclast) + "') and \"STATUS\">=0 and \"JOB_NAME\" <> 'nil' and \"JOB_NAME\"='" + m.getJob_name() + "'");
		    Iterator msg = messages.iterator();
		    SchedulerMessage h = null;
			while (msg.hasNext()) {
				try{
				   h = (SchedulerMessage)msg.next();
  				   if(m != h && m.getJob_name().equalsIgnoreCase(h.job_name) && !m.getJob_name().equals("")) {	
				  	   h.clear();
				   }
				}catch (ClassCastException e){
				   if ( h != null) {
				     spooler_log.info("message " + h.getLog() + " ignored . . .");
				   }else {
					     spooler_log.info("message ignored . . .");
				   }
				}

			}
		
	}
	
	private void removeJobChainFromMessages(SchedulerMessage m, GregorianCalendar gclast) throws Exception {
	
//			remove old messages cause successfully process
		    this.getConnection().execute("update SCHEDULER_MESSAGES set \"STATUS\" = -2 where \"LOGTIME\" <= %timestamp_iso('"
				+ SOSDate.getISODateTimeAsString(gclast) + "') and \"STATUS\">=0 and \"JOB_CHAIN\" <> 'nil' and \"JOB_CHAIN\"='" + m.getJob_chain() + "' and " + "\"ORDER_ID\"='" + m.getOrder_id() + "'");
 		    Iterator msg = messages.iterator();
		    SchedulerMessage h = null;
			while (msg.hasNext()) {
				try{
				   h = (SchedulerMessage)msg.next();
  				   if( m != h && m.getJob_chain().equalsIgnoreCase(h.job_chain) && !m.getJob_chain().equals("") &&
  					   m.getOrder_id().equals(h.getOrder_id())) {	
				  	   h.clear();
				   }
				}catch (ClassCastException e){
				   if ( h != null) {
				      spooler_log.info("message " + h.getLog() + " ignored . . .");
				   }else {
					  spooler_log.info("message ignored . . .");
				   }
				}

			}
	
	}
	
   private String getDbProperty() {
      return dbProperty;
   }
   private  void setDbProperty(String dbProperty_) {
	  
    this.dbProperty = dbProperty_;
	dbProperty = dbProperty.replaceAll("jdbc:", "-url=jdbc:");
    dbProperty = dbProperty.substring(dbProperty.indexOf('-'));
    if (dbProperty.endsWith("-password=")) dbProperty=dbProperty.substring(0, dbProperty.length()-10);

   }
   private String getDbClass() {
      return dbClass;
   }
   private void setDbClass(String dbClass) {
      this.dbClass = dbClass;
   }
}
