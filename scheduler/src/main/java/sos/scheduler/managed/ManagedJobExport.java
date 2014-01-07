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
package sos.scheduler.managed;

import java.util.Properties;

import sos.connection.SOSConnection;
import sos.settings.SOSProfileSettings;
import sos.util.SOSArguments;
import sos.marshalling.SOSExport;
import sos.util.SOSLogger;
import sos.util.SOSSchedulerLogger;
import sos.util.SOSStandardLogger;

/**
 * ManagedJobExport exports jobdefinitions from the
 * SCHEDULER_MANAGED_JOBS (as well as SCHEDULER_MANAGED_JOB_TYPES und SETTINGS)
 * tables
 * @author Andreas Liebert 
 */
public class ManagedJobExport{
	
	private static SOSConnection conn;

	private static SOSStandardLogger sosLogger = null;
	
	public static void main(String[] args) {
		if(args.length==0 || args[0].equals("-?") || args[0].equals("-h")){
			showUsage();
			System.exit(0);
		}
		try {
			SOSArguments arguments = new SOSArguments(args);
			
			String xmlFile="";
			String logFile="";
			int logLevel=0;
			String settingsFile="";
			int jobID=0;
			try {
				xmlFile = arguments.as_string("-file=","job_export.xml");
				logLevel = arguments.as_int("-v=",SOSStandardLogger.INFO);
				logFile = arguments.as_string("-log=","");
				jobID = arguments.as_int("-job=");
				settingsFile = arguments.as_string("-settings=","../config/factory.ini");
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				showUsage();
				System.exit(0);
			}
			if (logFile.length()>0)	sosLogger = new SOSStandardLogger(logFile, logLevel);
			else sosLogger = new SOSStandardLogger(logLevel);
			
			getDBConnection(settingsFile);
						
			conn.connect();
			
			arguments.check_all_used();
			export(xmlFile,jobID);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * @param settingsFile
	 * @throws Exception
	 */
	protected static SOSConnection getDBConnection(String settingsFile) throws Exception {
		SOSProfileSettings settings = new SOSProfileSettings(settingsFile);
		Properties props = settings.getSection("spooler");
		
		if (props.isEmpty()) 
		    throw new Exception("no settings found in section [spooler] of configuration file: " + settingsFile);

		if (props.getProperty("db") == null || props.getProperty("db").length() == 0) 
		    throw new Exception("no settings found for entry [db] in section [spooler] of configuration file: " + settingsFile);
		
		if (props.getProperty("db_class") == null || props.getProperty("db_class").length() == 0) 
		    throw new Exception("no settings found for entry [db_class] in section [spooler] of configuration file: " + settingsFile);
		
		
		if (sosLogger != null) sosLogger.debug6("connecting to database.. .");

		String dbProperty = props.getProperty("db").replaceAll("jdbc:", "-url=jdbc:");
		dbProperty = dbProperty.substring(dbProperty.indexOf('-'));
		
		SOSArguments dbArguments = new SOSArguments(dbProperty);

		conn =  SOSConnection.createInstance(  
		     					props.getProperty("db_class"),
		     					dbArguments.as_string("-class=", ""),
		     					dbArguments.as_string("-url=", ""),
		     					dbArguments.as_string("-user=", ""),
		     					dbArguments.as_string("-password=", ""),
		     					sosLogger);
		
		return conn;
	}

	public static void showUsage(){
		System.out.println("usage:ManagedJobExport ");
		System.out.println("Argumente:");
		System.out.println("     -job=            ID des zu kopierenden jobs");
		System.out.println("     -v=              Loglevel (optional)");
		System.out.println("     -log=            LogDatei (optional)");
		System.out.println("     -settings=       factory.ini Datei (default:../config/factory.ini)");
		System.out.println("     -file=           Exportdatei (default:job_export.xml)");
	}
	
	private static void export(String xmlFile, int jobID) throws Exception{
		String selManagedJobs = "SELECT * FROM "+JobSchedulerManagedObject.getTableManagedJobs()+" WHERE \"ID\"="+jobID;
		String selJobTypes = "SELECT * FROM "+JobSchedulerManagedObject.getTableManagedJobTypes()+" WHERE \"TYPE\"='?'";
		String selSettings = "SELECT * FROM "+JobSchedulerManagedObject.getTableSettings()+" WHERE \"APPLICATION\" IN ('job_type/local/?', 'job_type/global/?', 'job_type/mixed/?')";
		
				
		SOSExport export = new SOSExport(conn,xmlFile, "DOCUMENT", sosLogger);
		int job = export.query(JobSchedulerManagedObject.getTableManagedJobs(),"ID",selManagedJobs);
		int jobTypes = export.query(JobSchedulerManagedObject.getTableManagedJobTypes(),"TYPE", selJobTypes,"JOB_TYPE", job);
		int settings = export.query(JobSchedulerManagedObject.getTableSettings(), "APPLICATION,SECTION,NAME",selSettings,"TYPE,TYPE,TYPE",jobTypes);
		
		
		
		export.doExport();
		
		
	}
	/**
	 * @return Returns the sosLogger.
	 */
	public static SOSStandardLogger getSosLogger() {
		return sosLogger;
	}
	/**
	 * @param sosLogger The sosLogger to set.
	 */
	public static void setSosLogger(SOSStandardLogger sosLogger) {
		ManagedJobExport.sosLogger = sosLogger;
	}
}
