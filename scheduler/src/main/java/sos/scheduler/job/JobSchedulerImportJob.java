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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import sos.connection.SOSConnection;
import sos.scheduler.job.JobSchedulerJob;
import sos.spooler.Variable_set;
import sos.util.SOSClassUtil;
// import sos.util.SOSImport;
import sos.marshalling.SOSImport;
import sos.util.SOSStandardLogger;
import sos.util.SOSString;

/**
 * 
 * 6. Import durch Job
 * 
 * 
 * @author mueruevet.oeksuez@sos-berlin.com
 *
 */

public class JobSchedulerImportJob extends JobSchedulerJob { 
	
	
	/** order parameters */
	private Variable_set             parameters                        = null;
	
	/** databse connection and settings*/
	private SOSConnection            sosConnection                     = null;
	
	/** databse connection and settings*/
	private SOSConnection            sosUpdateStateConnection          = null;
	
	private String                   sosSettingsFile                   = null;
	
	private String                   sosSettingsUpdateStateFile        = null;
	
	private SOSString                sosString                         = null;
	
	private Iterator                 listOfFiles                       = null;
	
	private String                   inputPath                         = "";
	
	private String                   regEx                             = "";
	
//	beinhaltet Tabellennamen, die umbenannt werden sollen: key=alte Tabellenname, value=neue Tabellenname
	private HashMap                  mappingTablenames                 = null;
	
	public boolean spooler_init() {
		
		
		boolean rc               = super.spooler_init();
		
		try {
			this.setLogger(new sos.util.SOSSchedulerLogger(spooler_log));
			String filename = "";
			if(rc) {
				sosString = new SOSString();
				this.setParameters(spooler.create_variable_set()); 
				
				
				
				if (spooler_task.params() != null) this.getParameters().merge(spooler_task.params());
				if (spooler_job.order_queue() != null) this.getParameters().merge(spooler_task.order().params());
				
				
				if (this.getParameters().value("sos_settings_file") != null && this.getParameters().value("sos_settings_file").length() > 0) {
					sosSettingsFile = this.getParameters().value("sos_settings_file");
					getLogger().debug1(".. parameter [sos_settings_file]: " +sosSettingsFile );
				} 
				
				if (this.getParameters().value("sos_setting_file_status_update") != null && this.getParameters().value("sos_setting_file_status_update").length() > 0) {
					sosSettingsUpdateStateFile = this.getParameters().value("sos_setting_file_status_update");
					getLogger().debug1(".. parameter [sos_setting_file_status_update]: " +sosSettingsUpdateStateFile );
				} 
				
				if (this.getParameters().value("input_path") != null && this.getParameters().value("input_path").length() > 0) {
					inputPath = this.getParameters().value("input_path");
					getLogger().debug1(".. parameter [sos_setting_file_status_update]: " +inputPath );
				} 
				
				if (this.getParameters().value("reg_ex") != null && this.getParameters().value("reg_ex").length() > 0) {
					regEx = this.getParameters().value("reg_ex");
					getLogger().debug1(".. parameter [reg_ex]: " +regEx );
				} 
				
				if (this.getParameters().value("filename") != null && this.getParameters().value("filename").length() > 0) {
					filename = this.getParameters().value("filename");
					getLogger().debug1(".. parameter [filename]: " +filename );					
				}
				
				String mappingFilename = null;
				if (this.getParameters().value("mapping_table_names") != null && this.getParameters().value("mapping_table_names").length() > 0) {
					mappingFilename = this.getParameters().value("mapping_table_names");
					getLogger().debug1(".. parameter [mapping_table_names]: " +mappingFilename );					
				}
				if(sosString.parseToString(mappingFilename).length() > 0) {
					mappingTablenames = getMappingFilename(mappingFilename);
				}
				
				//DB Einstellungen aus der sos_settings_file (=jobparameter) oder aus der factory.ini
				sosConnection = getConnections(sosSettingsFile);				
				sosConnection.setAutoCommit(false);				
				//DB Einstellungen aus der sos_setting_file_status_update (=jobparameter) oder aus der factory.ini
				sosUpdateStateConnection = getConnections(sosSettingsUpdateStateFile);
				
				//only for Standalone Job
				ArrayList list     = new ArrayList(); //Hilfsvariable
				Vector    filelist = null;
				if(spooler_task.job().order_queue() == null) {
					
					//Verzeichnis�berwachung
					if (sosString.parseToString(spooler_task.changed_directories()).length() > 0 &&
							sosString.parseToString(spooler_task.trigger_files()).length() > 0);
					String[] split = spooler_task.trigger_files().split(";");	
					
					for (int i = 0; i < split.length; i++) {
						if(sosString.parseToString(split[i]).length() >0)
							list.add(split[i]);
					}					
					
					//Angabe einer zu �berwachende Verzeichnis
					if(sosString.parseToString(inputPath).length() > 0) {
						filelist = sos.util.SOSFile.getFilelist(inputPath,regEx, java.util.regex.Pattern.CASE_INSENSITIVE);
						Iterator it = filelist.iterator();
						while (it.hasNext()) {
							list.add(it.next());	
						}
					}
					
					//Angabe einer festen dateiname: Parameter filename
					if(sosString.parseToString(filename).length() > 0) {
						list.add(filename);
					}
					
					listOfFiles = list.iterator();
					if(!listOfFiles.hasNext())
						return false;
				}
			}
		} catch (Exception e) {
			if (this.getLogger() != null) try { this.getLogger().error(e.getMessage()); } catch (Exception ex) {}
			return false;
		}
		return rc;
		
	}
	
	
	
	public boolean spooler_process() {
		
		boolean rc          = true;
		long timeInSec = System.currentTimeMillis();
		//signalId wird ermittelt anhand der XML-Dateiname, die importiert werden soll.
		String signalId      = "-1";				
		String triggerfile  = "";
		try {
			
			
			if(spooler_task.job().order_queue() != null) {
				
				this.setParameters(spooler.create_variable_set()); 				
				if (spooler_task.params() != null) this.getParameters().merge(spooler_task.params());
				if (spooler_job.order_queue() != null) this.getParameters().merge(spooler_task.order().params());
				
				
				String filename = "";
				if (this.getParameters().value("filename") != null && this.getParameters().value("filename").length() > 0) {
					filename = this.getParameters().value("filename");
					getLogger().debug1(".. parameter [filename]: " +filename );
					//System.out.println(getParameters().value("filename"));
				}
				
				
				triggerfile = spooler_task.trigger_files();
				if(sosString.parseToString(triggerfile).length() > 0) {
					getLogger().debug3("order start cause trigger files: " + triggerfile);
				} else if(sosString.parseToString(filename).length() > 0){
					triggerfile = sosString.parseToString(getParameters().value("filename"));
					getLogger().debug3("order cause parameter [filename= " + triggerfile);
				}
			} else {
				
				triggerfile = sosString.parseToString(listOfFiles.next());
				if(triggerfile.length() == 0) {
					getLogger().debug("there is no file to import.");
					return false;
				}
			}
			
			if(triggerfile != null && triggerfile.length() > 0) {
				int iPos1 = triggerfile.lastIndexOf("_");
				int iPos2 = triggerfile.substring(0, iPos1).lastIndexOf("_");
				
				if(iPos2 > -1 && iPos1 > -1) {				
					signalId = triggerfile.substring(iPos2 + 1, iPos1);
					getLogger().debug7("signal_id is: " + signalId);
				}
				
				importfile(triggerfile);
				
				//AUftrag abgeschlossen					
				String upStr = "UPDATE "  + JobSchedulerSignalJob.TABLE_SCHEDULER_SIGNAL_OBECTS + " SET \"STATUS\" = 3 WHERE  \"SIGNAL_ID\" = " + signalId;     	       
				sosUpdateStateConnection.executeUpdate(upStr);
				sosUpdateStateConnection.commit();
				
				String time = Math.round((System.currentTimeMillis() - timeInSec) / 1000)+ "s";
				
				String stateText = "successfully import Database to XML-File " + triggerfile + " (" + time + ")";
				spooler_job.set_state_text(stateText); 
				
				getLogger().info(stateText);
				//System.out.println(stateText);
			}
			return ((spooler_job.order_queue() != null) ? rc : listOfFiles.hasNext());
			
		} catch (Exception e) {
			try {
				if(sosConnection != null) {
					sosConnection.rollback();//								
				}
				if(sosUpdateStateConnection != null) {
					sosUpdateStateConnection.rollback();
//					status auf 1002 setzen					
					String upStr = "UPDATE " + JobSchedulerSignalJob.TABLE_SCHEDULER_SIGNAL_OBECTS + 
					" SET \"STATUS\" = 1003 WHERE  \"SIGNAL_ID\" = " + signalId;     	       
					sosUpdateStateConnection.executeUpdate(upStr);
					sosUpdateStateConnection.commit();
					
				}
			} catch (Exception xe) {}
			String stateText = "could not import Database to XML-File " + triggerfile + " cause: " + e.getMessage();
			spooler_job.set_state_text(stateText); 
			try {
				getLogger().warn(stateText);					
				getLogger().error(stateText);
			} catch(Exception ea) {}
			//return false;
			return ((spooler_job.order_queue() != null) ? rc : listOfFiles.hasNext());
		}
	}
	
	public void spooler_exit() {
		
		super.spooler_exit();
		try {
			if(sosConnection != null) {
				sosConnection.disconnect();
			}
			if(sosUpdateStateConnection != null) {
				sosUpdateStateConnection.disconnect();
			}
		}
		catch (Exception e) {
			try {
				getLogger().warn("spooler_exit(): disconnect failed: " + e.toString());
			} catch(Exception es) {}
		}
	}
	
	public void importfile(String triggerfile) throws Exception{
		
		
		SOSStandardLogger sosLogger = null;
		
		try {    					
			//sosLogger = new SOSStandardLogger(getLogger().getFileName(), getLogger().getLogLevel());
			sosLogger = new SOSStandardLogger(spooler.log().filename(), getLogger().getLogLevel());
			
			SOSImport imp = new SOSImport(sosConnection, triggerfile, null, null, null, sosLogger);
			
			if(mappingTablenames != null && !mappingTablenames.isEmpty())
				imp.setMappingTablenames(mappingTablenames);
			
			imp.doImport();
			
			sosConnection.commit();
			
			
		} catch (Exception e) {
			spooler_log.warn("error in " + SOSClassUtil.getClassName() + ". Could not import file : " + triggerfile+ " cause:" + e.getMessage());
			throw new Exception ("error in " + SOSClassUtil.getClassName() + " :" + e.getMessage());
		} 
	}
	
	/**
	 * @return Returns the parameters.
	 */
	public Variable_set getParameters() {
		return parameters;
	}
	
	
	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(Variable_set parameters) {
		this.parameters = parameters;
	}
	
	
	
	
	private SOSConnection getConnections(String settingsfile) throws Exception{
		SOSConnection conn = null;
		if(sosString.parseToString(settingsfile).length() > 0) {			
			conn = getConnectionFromINIFile(settingsfile);
		} 
		
		if (conn == null) {
			conn = getConnection();
		}
		
		return conn;
	}
	
	private SOSConnection getConnectionFromINIFile(String settingsfile) throws Exception{
		SOSConnection conn = null;
		try {
			
			spooler_log.debug3("DB Connecting.. .");
			conn = SOSConnection.createInstance(settingsfile, new sos.util.SOSSchedulerLogger(spooler_log));
			
			conn.connect();
			spooler_log.debug3("DB Connected");
		} catch (Exception e) {
			throw (new Exception("error in " + SOSClassUtil.getMethodName()
					+ ": connect to database failed: " + e.toString()));
		}
		return conn;
	}
	
	private HashMap getMappingFilename(String mapFile) throws Exception {
		
		HashMap retVal = new HashMap();
		try {
			
			javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		    	
			javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document dom = db.parse(mapFile);
			org.w3c.dom.Element docEle = dom.getDocumentElement();
			
			org.w3c.dom.NodeList nl = docEle.getElementsByTagName("tablename");
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {					
					org.w3c.dom.Element el = (org.w3c.dom.Element)nl.item(i);
					retVal.put(el.getAttribute("from"), el.getAttribute("to"));
				}
			}

			
			return retVal;	
		} catch (Exception e) {
			throw new Exception ("..error in " + SOSClassUtil.getClassName() + " :" + e.getMessage());
		}
		
	}
	
}
