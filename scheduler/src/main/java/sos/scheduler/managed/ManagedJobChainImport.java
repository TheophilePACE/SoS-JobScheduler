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
 * ManagedJobChainImport.java
 * Created on 28.10.2005
 * 
 */
package sos.scheduler.managed;

import java.util.HashMap;

import org.apache.xml.serialize.IndentPrinter;

import sos.connection.SOSConnection;
import sos.settings.SOSConnectionSettings;
import sos.util.SOSArguments;
import sos.marshalling.SOSImport;
import sos.util.SOSStandardLogger;

/**
 * This class imports managed job chains from xml
 *
 * @author Andreas Liebert 
 */
public class ManagedJobChainImport extends SOSImport {
	private static SOSConnection conn;

	private static SOSStandardLogger sosLogger = null;
	
	private int workflow=-1;
	
	private boolean jobExists = false;
	
	private boolean modelExists = true;
	
	private String modelId="";
	
	public ManagedJobChainImport(SOSConnection conn, String file_name, String package_id, 
			String package_element, String package_value, SOSStandardLogger log){
		super(conn, file_name, package_id, package_element, package_value, log);
		
	}
	
	public static void showUsage(){
		System.out.println("usage:ManagedJobChainImport ");
		System.out.println("Argumente:");
		System.out.println("     -file=           Importdatei");
		System.out.println("     -v=              Loglevel (optional)");
		System.out.println("     -log=            LogDatei (optional)");
		System.out.println("     -settings=       factory.ini Datei (default:../config/factory.ini)");
		System.out.println("     -package=        zu importierende Pakete (package[+package[+...]] default: alle)");
		System.out.println("     -jobchain=       zu importierende jochains (jobchain[+jobchain[+...]] default: alle)");
	}
	
	public static void main(String[] args) {
		if(args.length==0 || args[0].equals("-?") || args[0].equals("/?") || args[0].equals("-h")){
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
			String packages="";
			String jobchains="";
			try {
				xmlFile = arguments.as_string("-file=");
				logLevel = arguments.as_int("-v=",SOSStandardLogger.INFO);
				logFile = arguments.as_string("-log=","");
				settingsFile = arguments.as_string("-settings=","../config/factory.ini");				
				jobchains = arguments.as_string("-jobchain=","");
				packages = arguments.as_string("-package=","");
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				showUsage();
				System.exit(0);
			}
			if (logFile.length()>0)	sosLogger = new SOSStandardLogger(logFile, logLevel);
			else sosLogger = new SOSStandardLogger(logLevel);
			if(packages.length()>0 && jobchains.length()>0){
				System.out.println("jobchain und package dürfen nicht zusammen angegeben werden.");
				showUsage();
				System.exit(0);
			}
			
			ManagedJobExport.setSosLogger(sosLogger);
			conn = ManagedJobExport.getDBConnection(settingsFile);
			conn.connect();

			conn.setAutoCommit(false);
			if (packages!=null && packages.length()>0) doMultipleImport(conn, xmlFile, "PACKAGE", packages);
			else if (jobchains!=null && jobchains.length()>0) doMultipleImport(conn, xmlFile, "NAME", jobchains);
			else{
			ManagedJobChainImport imp = new ManagedJobChainImport(conn, xmlFile, null, null, null, sosLogger);
						
			imp.setUpdate(false);
			imp.setHandler(JobSchedulerManagedObject.getTableManagedModels(),"key_handler_MANAGED_MODELS","","NAME");
			imp.setHandler(JobSchedulerManagedObject.getTableManagedJobs(),"key_handler_MANAGED_JOBS","rec_handler_MANAGED_JOBS",null);
			imp.setHandler(JobSchedulerManagedObject.getTableManagedOrders(),"key_handler_MANAGED_ORDERS",null,null);
			
						
			imp.doImport(conn, xmlFile);
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
	
	private static void doMultipleImport(SOSConnection conn, String xmlFile, String field, String identifiers) throws Exception{
		String[] identArray = identifiers.split("\\+");
		for(int i=0; i<identArray.length; i++){
			String identifier = identArray[i];
			ManagedJobChainImport imp = new ManagedJobChainImport(conn, xmlFile, JobSchedulerManagedObject.getTableManagedModels(),
					field, identifier, sosLogger);
			
			imp.setUpdate(false);
			imp.setHandler(JobSchedulerManagedObject.getTableManagedModels(),"key_handler_MANAGED_MODELS","","NAME");
			imp.setHandler(JobSchedulerManagedObject.getTableManagedJobs(),"key_handler_MANAGED_JOBS","rec_handler_MANAGED_JOBS",null);
			imp.setHandler(JobSchedulerManagedObject.getTableManagedOrders(),"key_handler_MANAGED_ORDERS",null,null);

			
			imp.doImport(conn, xmlFile);
			
		}
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
     * Key für MANAGED_MODELS
     * 
     */
    public HashMap key_handler_MANAGED_MODELS(HashMap keys) throws Exception {
        SOSConnectionSettings sosSettings = new SOSConnectionSettings(
				conn, JobSchedulerManagedObject.getTableSettings(), sosLogger);
		int key = sosSettings.getLockedSequence("scheduler",
				"counter", "scheduler_managed_models.id");
		keys.put("ID", String.valueOf(key));
    	modelId = String.valueOf(key);
        return keys; 
    }
    
    /***** 
     * Key für MANAGED_ORDERS
     * 
     */
    public HashMap key_handler_MANAGED_ORDERS(HashMap keys) throws Exception {
        SOSConnectionSettings sosSettings = new SOSConnectionSettings(
				conn, JobSchedulerManagedObject.getTableSettings(), sosLogger);
		int key = sosSettings.getLockedSequence("scheduler",
				"counter", "scheduler_managed_orders.id");
		keys.put("ID", String.valueOf(key));
    	
        return keys; 
    }
    
    /***** 
     * Record handler für MANAGED_JOBS
     * 
     */
    public HashMap rec_handler_MANAGED_JOBS(HashMap keys, HashMap record, String record_identifier) throws Exception{
    	record.put("MODEL", modelId);
        return record; 
    }
    
    
    
}
