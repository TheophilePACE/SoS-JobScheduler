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
 * ManagedJobImport.java
 * Created on 13.10.2005
 * 
 */
package sos.scheduler.managed;

import java.util.HashMap;

import sos.connection.SOSConnection;
import sos.settings.SOSConnectionSettings;
import sos.settings.SOSSettings;
import sos.util.SOSArguments;
import sos.marshalling.SOSImport;
import sos.util.SOSLogger;
import sos.util.SOSStandardLogger;

/**
 * ManagedJobImport imports jobdefinitions into the
 * SCHEDULER_MANAGED_JOBS (as well as SCHEDULER_MANAGED_JOB_TYPES und SETTINGS)
 * tables
 * @author Andreas Liebert 
 */
public class ManagedJobImport extends SOSImport {
	
	
	private static SOSConnection conn;

	private static SOSStandardLogger sosLogger = null;
	
	private int workflow=-1;
	
	private boolean jobExists = false;
	
	private boolean modelExists = true;
	
	private String modelId="";
	
	public ManagedJobImport(SOSConnection conn, String file_name, String package_id, 
			String package_element, String package_value, SOSStandardLogger log){
		super(conn, file_name, package_id, package_element, package_value, log);
		
	}
	
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
			int template=0;
			int model=0;
			
			try {
				xmlFile = arguments.as_string("-file=");
				logLevel = arguments.as_int("-v=",SOSStandardLogger.INFO);
				logFile = arguments.as_string("-log=","");
				settingsFile = arguments.as_string("-settings=","../config/factory.ini");				
				model = arguments.as_int("-jobchain=",-1);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				showUsage();
				System.exit(0);
			}
			if (logFile.length()>0)	sosLogger = new SOSStandardLogger(logFile, logLevel);
			else sosLogger = new SOSStandardLogger(logLevel);
			
			ManagedJobExport.setSosLogger(sosLogger);
			conn = ManagedJobExport.getDBConnection(settingsFile);
			conn.connect();

			conn.setAutoCommit(false);
			
			//ManagedJobImport imp = new ManagedJobImport(conn, xmlFile, "SCHEDULER_MANAGED_JOBS", "job_name", "weblog_report", sosLogger);
			ManagedJobImport imp = new ManagedJobImport(conn, xmlFile, null, null, null, sosLogger);
			imp.setWorkflow(model);			
			imp.setUpdate(false);
			imp.setHandler(JobSchedulerManagedObject.getTableManagedJobs(),"key_handler_MANAGED_JOBS","rec_handler_MANAGED_JOBS",null);
			/*imp.setHandler(DocumentFactoryClass.getTableDocumentIncludes(),"key_handler_LF_LETTERS",null,null);
			imp.setHandler(DocumentFactoryClass.getTablePrints(),"key_handler_LF_LETTERS",null,null);
			imp.setHandler(DocumentFactoryClass.getTableMails(),"key_handler_LF_LETTERS",null,null);
			imp.setHandler(DocumentFactoryClass.getTableArchives(),"key_handler_LF_LETTERS",null,null);
			imp.setHandler(DocumentFactoryClass.getTableDocumentParameters(),"key_handler_LF_LETTERS",null,null);
			imp.setHandler(DocumentFactoryClass.getTableBlobs(),"key_handler_LF_BLOBS", null, null);*/
			
			imp.doImport(conn, xmlFile);
			if (imp.jobExists()) {
				conn.rollback();
				sosLogger.warn("Job already exists.");
			}else if(!imp.modelExists()){
				conn.rollback();
				sosLogger.warn("Jobchain doesn't exist. Please specify a jobchain using the -jobchain option.");
			}
			conn.commit();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	public static void showUsage(){
		System.out.println("usage:ManagedJobImport ");
		System.out.println("Argumente:");
		System.out.println("     -file=           Importdatei");
		System.out.println("     -v=              Loglevel (optional)");
		System.out.println("     -log=            LogDatei (optional)");
		System.out.println("     -settings=       factory.ini Datei (default:../config/factory.ini)");
		System.out.println("     -jobchain=       neue Jobkette (ID) für importierten Job (optional)");
	}
	
	/**
	 * @return Returns the workflow.
	 */
	public int getWorkflow() {
		return workflow;
	}
	/**
	 * @param workflow The workflow to set.
	 */
	public void setWorkflow(int workflow) {
		this.workflow = workflow;
	}
	

    
	/***** 
     * Key für MANAGED_JOBS
     * 
     */
    public HashMap key_handler_MANAGED_JOBS(HashMap keys) throws Exception {
        SOSConnectionSettings sosSettings = new SOSConnectionSettings(
				conn, JobSchedulerManagedObject.getTableSettings(), sosLogger);
		int key = sosSettings.getLockedSequence("scheduler",
				"counter", "scheduler_managed_jobs.id");
		keys.put("ID", String.valueOf(key));
    	
        return keys; 
    }
    
    /***** 
     * Record handler für MANAGED_JOBS
     * 
     */
    public HashMap rec_handler_MANAGED_JOBS(HashMap keys, HashMap record, String record_identifier) throws Exception{
    	String model = record.get("MODEL").toString();
        if(workflow > -1)
            model = "" + workflow;
        String test = conn.getSingleValue("SELECT \"ID\" FROM " + JobSchedulerManagedObject.getTableManagedJobs() + " WHERE \"MODEL\"=" + model + " AND \"JOB_NAME\"='" + record.get("JOB_NAME").toString() + "'");
        if(test != null && test.length() > 0)
            jobExists = true;
        String modelTest = conn.getSingleValue("SELECT \"ID\" FROM " + JobSchedulerManagedObject.getTableManagedModels() + " WHERE \"ID\"=" + model);
        if(modelTest == null || modelTest.length() == 0)
            modelExists = false;
        record.put("MODEL", model);
        return record;

    }
    
    
    
	/**
	 * @return Returns the jobExists.
	 */
	public boolean jobExists() {
		return jobExists;
	}
	/**
	 * @return Returns the modelExists.
	 */
	public boolean modelExists() {
		return modelExists;
	}
}
