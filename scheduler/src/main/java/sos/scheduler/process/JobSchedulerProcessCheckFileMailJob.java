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
/*
 * JobSchedulerProcessCheckFileMailJob.java
 * Created on 05.06.2007
 * 
 */
package sos.scheduler.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sos.util.SOSFile;
import sos.util.SOSLogger;

public class JobSchedulerProcessCheckFileMailJob extends
		JobSchedulerProcessSendMailJob {
	
	// regex for file to search for
	private String fileSpec;
	
    //	path for files
	private String filePath;
	
	// file content regex to search for in file
	private String fileContent;

	/* 
	 * @see sos.scheduler.process.JobSchedulerProcessSendMailJob#doSendMail()
	 */
	protected boolean doSendMail() {
		boolean found = false;
		fileSpec = "";
		fileContent = "";
		filePath ="";
		
		try {
    		if (this.getParameters().value("file_spec") != null && this.getParameters().value("file_spec").length() > 0) {
    			fileSpec = this.getParameters().value("file_spec");
    		}    		
    		if (this.getParameters().value("file_path") != null && this.getParameters().value("file_path").length() > 0) {
    			filePath = this.getParameters().value("file_path");
    		}
    		if (this.getParameters().value("file_content") != null && this.getParameters().value("file_content").length() > 0) {
    			fileContent = this.getParameters().value("file_content");
    		}
		} catch (Exception e) {
			try{
				this.getLogger().warn("error occurred checking parameters: " + e.getMessage());
			} catch(Exception ex){}
    	}
		if (fileSpec.length()==0 && fileContent.length()==0 && filePath.length()==0){
			try{
				getLogger().info("JobSchedulerProcessCheckFileMailJob is not configured, suppressing mail.");
			} catch (Exception e){}
			return false;
		}
		try{
			Vector fileList = new Vector();
			if (fileSpec.length()==0){
				if (spooler_job.order_queue() != null){
					File triggeredFile = new File(spooler_task.order().id());					
					fileList.add(triggeredFile);
				}
			} else{
				fileList = SOSFile.getFilelist(filePath, fileSpec,0);
			}
			getLogger().debug3("Lenght of filelist: "+fileList.size());
			Iterator iter = fileList.iterator();
			Pattern pattern = null;
			if (fileContent.length()>0) pattern = Pattern.compile(fileContent);
			
			while (iter.hasNext()){
				File curFile = (File) iter.next();
				if(pattern==null && curFile.exists()) {
					getLogger().info("Found file "+curFile.getAbsolutePath()+", sending mail.");
					found = true;
					break;
				}
				if(pattern!=null && curFile.exists()) {
					if (grep(curFile,pattern)){
						found = true;
						break;
					}
				}
			}
		}catch (Exception e) {
			try{
				this.getLogger().warn("error occurred checking file(s): " + e.getMessage());
			} catch(Exception ex){}
    	}
		return found;
	}

	private boolean grep(File file, Pattern regex) throws Exception{
		boolean found = false;
		try{
			getLogger().debug7("Grepping file "+file.getAbsolutePath()+" for regex: "+regex.pattern());
			BufferedReader in = new BufferedReader ( new FileReader (file) );
			String currentLine ="";
			while( (currentLine = in.readLine()) != null ) {
				Matcher matcher = regex.matcher(currentLine);
				//getLogger().debug9(currentLine);
				if (matcher.find()){
					String match = matcher.group();
					getLogger().debug1("Found String '"+match+"' in file "+file.getAbsolutePath()+", sending mail.");
					found = true;
					String body = "";
					if (this.getParameters().value("body") != null && this.getParameters().value("body").length() > 0) {
            			body = this.getParameters().value("body")+"\n";
            		}
					body+="'"+match+"' was found in file "+file.getAbsolutePath();
					this.getParameters().set_var("body",body);
					break;
				}
			}
			in.close();
		} catch(Exception e){
			throw new Exception ("Error occurde grepping file: "+e);
		}
		return found;
	}
}
