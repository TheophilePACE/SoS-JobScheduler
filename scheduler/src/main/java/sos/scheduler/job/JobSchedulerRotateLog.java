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

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import sos.spooler.Job_impl;
// import sos.util.SOSDate;
import sos.util.SOSFile;
import sos.util.SOSGZip;

/**
 * compress and rotate scheduler log files
 * @author andreas.pueschel@sos-berlin.com 
 */
public class JobSchedulerRotateLog extends Job_impl {

    public boolean spooler_process() {

    	/** give a path for files to remove */
    	String filePath = spooler.log_dir(); 
    	
    	/** give the number of days, defaults to 14 days */
    	long fileAge = 14;
    	
    	/** number of days, for file age of files that should be deleted **/
    	long deleteFileAge = 0;
    	
    	/** give a regular expression as file specification */
    	String fileSpec = "^(scheduler)([0-9\\-]+)";
    	if (spooler.id() != null && spooler.id().length() > 0) fileSpec +=  "(\\." + spooler.id() + ")";
    	fileSpec += "(\\.log)$";
    	
    	String deleteFileSpec = "^(scheduler)([0-9\\-]+)";
    	if (spooler.id() != null && spooler.id().length() > 0) deleteFileSpec +=  "(\\." + spooler.id() + ")";
    	deleteFileSpec += "(\\.log)(\\.gz)?$";
    	

		if (spooler_task.params().var("file_path") != null
		&& spooler_task.params().var("file_path").length() > 0) {
		    filePath = spooler_task.params().var("file_path");
			spooler_log.info(".. job parameter [file_path]: " + filePath);
		}
    	
		if (spooler_task.params().var("file_specification") != null
		&& spooler_task.params().var("file_specification").length() > 0) {
			fileSpec = spooler_task.params().var("file_specification");
			spooler_log.info(".. job parameter [file_specification]: " + fileSpec);
		}
			  	
		if (spooler_task.params().var("file_age") != null
		&& spooler_task.params().var("file_age").length() > 0) {
			fileAge = Long.parseLong(spooler_task.params().var("file_age"));
			spooler_log.info(".. job parameter [file_age]: " + fileAge);
		}
		
		if (spooler_task.params().var("delete_file_age") != null
				&& spooler_task.params().var("delete_file_age").length() > 0) {
					deleteFileAge = Long.parseLong(spooler_task.params().var("delete_file_age"));
					spooler_log.info(".. job parameter [delete_file_age]: " + deleteFileAge);
		}
		
		if (spooler_task.params().var("delete_file_specification") != null
				&& spooler_task.params().var("delete_file_specification").length() > 0) {
					deleteFileSpec = spooler_task.params().var("delete_file_specification");
					spooler_log.info(".. job parameter [delete_file_specification]: " + deleteFileSpec);
		}
    	
		
    	try {
    		int counter = 0;
    		int deleteCounter = 0;

    		if (!filePath.endsWith("/")) filePath += "/";
    		if ( deleteFileAge > 0){
    			Vector deleteFilelist = SOSFile.getFilelist(filePath, deleteFileSpec, 0);
    			Iterator deleteIterator = deleteFilelist.iterator();
    			while(deleteIterator.hasNext()) {
    				File tempFile = (File)deleteIterator.next();
    				long interval = System.currentTimeMillis()-tempFile.lastModified();
    				if (tempFile.canWrite()  && interval>(deleteFileAge*24*3600*1000) ) {
    					try {    						
    							tempFile.delete();
    							spooler_log.debug1( ".. log file [" + tempFile.getName() + 
    							"] deleted.");  						
    							deleteCounter++;
    					} 
    					catch (Exception e) {
    						throw (new Exception("an error occurred compressing log file [" + tempFile.getPath() + "] to gzip file: " + e.getMessage()));
    					}
    				}
    			}
    		}
    		
    		Vector filelist = SOSFile.getFilelist(spooler.log_dir(), fileSpec, 0);
    		Iterator iterator = filelist.iterator();
    		while(iterator.hasNext()) {
    			File tempFile = (File)iterator.next();
    			long interval = System.currentTimeMillis()-tempFile.lastModified();
    			if (tempFile.canWrite() && interval > (fileAge*24*3600*1000) ) {
    				try {    					
    					counter++;
    					String gzipFilename = filePath + tempFile.getName().concat(".gz");
    					File gzipFile = new File(gzipFilename);
    					SOSGZip.compressFile(tempFile, gzipFile);
    					gzipFile.setLastModified(tempFile.lastModified());
    					tempFile.delete();

    					spooler_log.debug1( ".. log file [" + tempFile.getName() + 
    							"] compressed to: " + gzipFilename);

    				} 
    		  		catch (Exception e) {
    			  		throw (new Exception("an error occurred compressing log file [" + tempFile.getPath() + "] to gzip file: " + e.getMessage()));
    		  		}
    			}
    		}
    		
    		if (counter > 0) spooler_log.info(counter + " log files compressed");
    		if (deleteCounter > 0) spooler_log.info(deleteCounter + " log files deleted");
    		

    	} catch (Exception e) {
    		spooler_log.warn("an error occurred cleaning up log files: " + e.getMessage());
    	}
    	
    	try {
    	    spooler.log().start_new_file();
    	    
    	} catch (Exception e) {
    		spooler_log.warn("an error occurred rotating log file: " + e.getMessage());
    		return false;
    	}
    	
    	return false;
    }

}
