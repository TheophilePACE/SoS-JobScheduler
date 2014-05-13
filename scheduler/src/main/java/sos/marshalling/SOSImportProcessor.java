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
package sos.marshalling;

import java.io.File;
import java.util.Vector;
import java.io.BufferedReader;

import sos.connection.SOSConnection;
import sos.util.SOSArguments;
import sos.util.SOSFile;
import sos.marshalling.SOSImport;
import java.util.Iterator;
import sos.util.SOSStandardLogger;
import sos.util.SOSLogger;

/**
 * Title: SOSImportProcessor<br>
 * Description: Kommandozeilentool zum Importieren von Daten im XML-Format<br>
 * 
 * Copyright: Copyright (c) 2005<br>
 * Company: SOS Berlin GmbH<br>
 * @author <a href="mailto:robert.ehrlich@sos-berlin.com">Robert Ehrlich</a>
 * Resource sos.connection.jar sos.util.jar xalan.jar xercesImpl.jar xml-apis.jar
 * @version $Id$
 */
public class SOSImportProcessor{

	private SOSConnection _sosConnection = null;
	private SOSStandardLogger _sosLogger = null;
	private boolean update = false;
	private String fileSpec = "^(.*)";
	
    private File _configFile 		= null; 
    private File _inputFile     	= null;
    private File _logFile 			= null;
    private int _logLevel           = 0;
    
    public SOSImportProcessor(String settingsFilename, SOSStandardLogger sosLogger) throws Exception {
        
        _configFile = new File(settingsFilename);
        _sosLogger = sosLogger;
        //this.init();
    }
    
    /**
     * Konstruktor
     * 
     * 
     * @param configFile			Datei, in der die Zugangsdaten zur Datenbank enthalten sind
     * @param logFile				Name der Protokolldatei
     * @param logLevel				Log Level f�r die Protokolldatei 
     * @param inputFile				Dateiname f�r Import
     * @throws Exception
     */
	public SOSImportProcessor(  File configFile, 
								File logFile,
								int logLevel,
								File inputFile
								) 
			throws Exception {

        if( configFile  == null )  throw new NullPointerException( "Import: Parameter config == null!" );
        if( inputFile  == null )  throw new NullPointerException( "Import: Parameter input == null!" );
 
        try {
        	_configFile 	= configFile;
        	_logFile        = logFile;
        	_logLevel       = logLevel;
        	_inputFile     	= inputFile;
        	
        	if (_configFile != null && _configFile.getName().length() > 0 && !_configFile.exists()) {
        		throw new Exception("configuration file not found: " + _configFile);
        	}
        	
        	if (_inputFile != null && _inputFile.getName().length() > 0 && !_inputFile.exists()) {
        		throw new Exception("input file not found: " + _inputFile);
        	}
        	
        	if(_logLevel !=0 && _logFile.toString().equals("")){
                throw new Exception("log file is not defined");
        	}
        	
        } catch (Exception e) {
        	throw new Exception("error in SOSImportProcessor: " + e.getMessage());
        }
	}
	
    /**
    * Konstruktor
    * 
    * wird aufgerufen, um die Programm USAGE anzuzeigen
    *  
    */
 	public SOSImportProcessor(){
        System.out.println("Syntax");
        System.out.println("Optionen :");
        System.out.println("        -config     Namen der Konfigurationsdatei f�r die DB Verbindung angeben.");
        System.out.println("                    Default : sos_settings.ini");
        System.out.println("        -input      Namen der Import XML-Datei angeben.");
        System.out.println("                    Default : sos_export.xml ");
        System.out.println("        -log        Namen der Log-Datei angeben.");
        System.out.println("                    Default : sos_import.log");
        System.out.println("        -log-level  Loglevel angeben.");
        System.out.println("                    Default : 0  keine Log-Datei schreiben");
        System.out.println("");
        System.out.println("");
        System.out.println("Beispiel 1 : Datei sos_import.xml in die Tabelle t1 importieren");
        System.out.println("         -config=config/sos_settings.ini -input=sos_import.xml -table=t1");
	}
	
	/**
	 * Import ausf&uuml;hren
	 * 
	 * @throws Exception
	 */
    public void doImport() throws Exception {
        	
    	try {
    	    if( _logLevel == 0){
    	        _sosLogger = new SOSStandardLogger(SOSStandardLogger.DEBUG);
            }
    	    else{
    	        _sosLogger = new SOSStandardLogger( _logFile.toString(), _logLevel );
    	    }
    	    
        	_sosConnection   = SOSConnection.createInstance( _configFile.toString(), _sosLogger );
        	_sosConnection.connect();
        	
        	SOSImport imp = new SOSImport(_sosConnection,_inputFile.toString(), null, null, null, _sosLogger);
         	
        	imp.doImport();
     
        	_sosConnection.commit();
        	
        	System.out.println("");
        	System.out.println("Import erfolgreich beendet.");
        	
        } 
    	catch (Exception e) {
    	    _sosConnection.rollback();
        	throw new Exception("error in SOSImportProcessor.doImport: " + e.getMessage());
        }
    	finally {
    	    try {
    	        if(_sosConnection != null) {
					_sosConnection.disconnect();
				}
			} 
    	    catch(Exception e) {	}
		}

	}
	
	public void process( File inputFile ) throws Exception {
        
    	try {
    		  _sosConnection   = SOSConnection.createInstance( _configFile.toString(), _sosLogger );
        	_sosConnection.connect();
        	
    	    if (inputFile.isDirectory()) {
        	
    	        int counter = 0;

        		Vector filelist = SOSFile.getFilelist(inputFile.getAbsolutePath(), this.getFileSpec(), 0);
        		Iterator iterator = filelist.iterator();
        		while(iterator.hasNext()) {
        			this.process( (File)iterator.next() );
        		}
    	    } else {    	    	  
    	        SOSImport imp = new SOSImport(_sosConnection,inputFile.toString(), null, null, null, _sosLogger);
    	        imp.setUpdate(getUpdate());
         			imp.doImport();
     					_sosConnection.commit();
    	    }
    	} catch (Exception e) {
        	 _sosLogger.warn("an error occurred processing file [" + inputFile.getAbsolutePath() + "]: " + e);
        } finally {
    	    try { if (_sosConnection != null) _sosConnection.rollback(); } catch (Exception ex) {}
        }
    }
	
	/**
    * Programm ausf�hren<br>
	* 
	* @param args	Programmargumente<br><br>
	* 
	* @throws Exception
	*/
    public static void main( String[] args ) throws Exception {
        
      if (args.length < 2) {
    		System.out.println("Usage: SOSImportProcessor configuration-file  path  [file-specification] [update] [log-level]");
    	}
    	
    	int logLevel = 0;
    	if (args.length > 4) logLevel = Integer.parseInt(args[4]); 
    	
    	SOSImportProcessor processor = new SOSImportProcessor( args[0], 
                										 new SOSStandardLogger(logLevel) );

     File inputFile = new File(args[1]);
     if (args.length > 2) processor.setFileSpec(args[2]);
     if (args.length > 3) processor.setUpdate(args[3].equals("1"));
     processor.process(inputFile);  
     
        /*boolean isImport = true;
        
        if(args.length == 1){
            String argument = args[0].toLowerCase().trim();
            if(argument.equals("?") || argument.equals("help")){
                SOSImportProcessor processor = new SOSImportProcessor(); 
                isImport = false;
            }
        }
        
        if(isImport){
            SOSArguments arguments = new SOSArguments( args );

            SOSImportProcessor processor = new SOSImportProcessor        ( 
                new File(      arguments.as_string( "-config=",        "sos_settings.ini")),
                new File(      arguments.as_string( "-log=",           "sos_import.log") ),
				   			   arguments.as_int   ( "-log-level=",     0),
                new File(      arguments.as_string( "-input=",        "sos_export.xml"))
            );

            arguments.check_all_used();
            processor.doImport();
        }*/
        
    }
    
    public void setUpdate(boolean ovr){
    	this.update = ovr;
    }
    
    public boolean getUpdate(){
    	return this.update;
    }
    
    /**
     * @param fileSpec The fileSpec to set.
     */
    public void setFileSpec(String fileSpec) {
        this.fileSpec = fileSpec;
    }
    
    /**
     * @return Returns the fileSpec.
     */
    public String getFileSpec() {
        return fileSpec;
    }
}
