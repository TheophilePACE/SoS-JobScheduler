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
package com.sos.jitl.jasperreports;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import sos.connection.SOSConnection;
import sos.spooler.Variable_set;
import sos.spooler.Web_service_request;
import sos.spooler.Xslt_stylesheet;
import sos.util.SOSClassUtil;
import sos.util.SOSString;
import sos.xml.SOSXMLXPath;

/**
 *  Es werden n-Mal Reports rausgeschrieben.
 *  n -> Anzahl der Statement
 *  Der Parameter der JobSchedulerJasperReportJob wird mit der Resultat der Selekt Statement rausgeschrieben.
 *  
 *  @author mo
 *
 */
public class JobSchedulerJasperReportJobRepeat extends
		JobSchedulerJasperReportJob {
	
	
	/** sos.util.SOSString Objekt*/
	private SOSString sosString 						= new SOSString();
	
	private ArrayList result                            = null;
		
	/** Eine SQl Statement. Anzahl der Ergebnisse dieser SQL Stament ist der Anzahl der Report, die erzeugt werden*/
	private String  repeatStatement                     = null;  
	
	private String settingsFilename                     = null;
	
	private String outputFilename                       = null; 
	
	
	public boolean spooler_process() { 
		HashMap repeatParams   = null;
		try {
			getJobParam();
			getOrderParam();
			spooler_log.debug9("repeateStatement " + repeatStatement);
			if(sosString.parseToString(repeatStatement).length() == 0) {
				spooler_log.debug9("keine repeateStatement");
				return super.spooler_process();
			}
			
			if(new File(repeatStatement).isFile() && new File(repeatStatement).getName().endsWith(".sql")) {
				repeatStatement = sos.util.SOSFile.readFile(new File(repeatStatement));
			}
									
			SOSConnection conn = getCurrConnection();
			if(conn != null) {
				result = conn.getArray(repeatStatement);
				
				for(int i =0; result != null && i < result.size(); i++) {
					String originOutputFilename = outputFilename;
					repeatParams = (HashMap)result.get(i);
					java.util.Iterator it = repeatParams.keySet().iterator(); 
					String vals = "";
					while(it.hasNext()) {
						String key = sosString.parseToString(it.next());
						String value = sosString.parseToString(repeatParams.get(key));
						spooler_log.debug9("repeat Parameter " + key + "=" + value);
						if(key.length() > 0 && value.length() > 0) {
							spooler_log.debug9("params: " + spooler_task.params().names());
							spooler_task.params().set_var(key, value);
							vals= vals + value + "_";
						}
					}
										
					File newOF = new File(originOutputFilename);
					originOutputFilename = (newOF != null && newOF.getParent()!= null ? newOF.getParent() : "") + "/" + vals + newOF.getName();
					spooler_task.params().set_var("output_filename", originOutputFilename);
						super.spooler_process();
					
				}
			}
			return false;
		} catch (Exception e) {
			spooler_job.set_state_text(e.getMessage());           
			spooler_log.warn(e.getMessage());
			return false;
		}
	}
	
	public void getJobParam() throws Exception {
		try {
			if (sosString.parseToString(spooler_task.params().var("repeat_sql_statement")).length() > 0) {
				repeatStatement = sosString.parseToString(spooler_task.params().var("repeat_sql_statement"));
				spooler_log.debug1(".. job parameter [repeat_sql_statement]: " + repeatStatement);
			}
			if (sosString.parseToString(spooler_task.params().var("settings_filename")).length() > 0) {
				settingsFilename = spooler_task.params().var("settings_filename");
				spooler_log.debug1(".. job parameter [settings_filename]: " + settingsFilename);
			}
			
			if (sosString.parseToString(spooler_task.params().var("output_filename")).length() > 0) {
				outputFilename = spooler_task.params().var("output_filename");
				spooler_log.debug1(".. job parameter [output_filename]: " + outputFilename);
			}
		} catch (Exception e) {
			throw new Exception("..error occurred processing job parameters: " + e.getMessage());
		}
	}

	private void getOrderParam() throws Exception {		
		try { 
			if (spooler_task.job().order_queue() != null) {

				order = spooler_task.order();
				//order = createOrderPayload(order);

				// create order payload and xml payload from web service request
				if (order.web_service_operation_or_null() != null) {
					SOSXMLXPath xpath = null;
					Web_service_request request = order.web_service_operation().request();

					// should the request be previously transformed ...
					if (order.web_service().params().var("request_stylesheet") != null && order.web_service().params().var("request_stylesheet").length() > 0) {
						Xslt_stylesheet stylesheet = spooler.create_xslt_stylesheet();
						stylesheet.load_file(order.web_service().params().var("request_stylesheet"));
						String xml_document = stylesheet.apply_xml(request.string_content());
						spooler_log.debug3("content of request:\n" + request.string_content());
						spooler_log.debug3("content of request transformation:\n" + xml_document);

						xpath = new sos.xml.SOSXMLXPath(new java.lang.StringBuffer(xml_document));
						// add order parameters from transformed request
						Variable_set params = spooler.create_variable_set();
						if (xpath.selectSingleNodeValue("//param[@name[.='repeat_sql_statement']]/@value") != null) {
							params.set_var("repeat_sql_statement", xpath.selectSingleNodeValue("//param[@name[.='repeat_sql_statement']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='settings_filename']]/@value") != null) {
							params.set_var("settings_filename", xpath.selectSingleNodeValue("//param[@name[.='settings_filename']]/@value"));
						}
						if (xpath.selectSingleNodeValue("//param[@name[.='output_filename']]/@value") != null) {
							params.set_var("output_filename", xpath.selectSingleNodeValue("//param[@name[.='output_filename']]/@value"));
						}
						order.set_payload(params);
					} else {
						xpath = new sos.xml.SOSXMLXPath(new java.lang.StringBuffer(request.string_content()));
						// add order parameters from request
						Variable_set params = spooler.create_variable_set();
						if (xpath.selectSingleNodeValue("//param[name[text()='repeat_sql_statement']]/value") != null) {
							params.set_var("repeat_sql_statement", xpath.selectSingleNodeValue("//param[name[text()='repeat_sql_statement']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='settings_filename']]/value") != null) {
							params.set_var("settings_filename", xpath.selectSingleNodeValue("//param[name[text()='settings_filename']]/value"));
						}
						if (xpath.selectSingleNodeValue("//param[name[text()='output_filename']]/value") != null) {
							params.set_var("output_filename", xpath.selectSingleNodeValue("//param[name[text()='output_filename']]/value"));
						}
						order.set_payload(params);
					}


				}

				orderData = (Variable_set) order.payload();
				if ( orderData != null && orderData.var("repeat_sql_statement") != null && orderData.var("repeat_sql_statement").toString().length() > 0) {
					repeatStatement = orderData.var("repeat_sql_statement").toString();
					spooler_log.debug1(".. order parameter [repeat_sql_statement]: " +repeatStatement);
				}
				if ( orderData != null && orderData.var("settings_filename") != null && orderData.var("settings_filename").toString().length() > 0) {
					settingsFilename  = (orderData.var("settings_filename").toString());
					spooler_log.debug1(".. order parameter [settings_filename]: " + settingsFilename );
				}
				if ( orderData != null && orderData.var("output_filename") != null && orderData.var("output_filename").toString().length() > 0) {
					outputFilename= orderData.var("output_filename").toString();
					spooler_log.debug1(".. order parameter [output_filename]: " + outputFilename);
				}
				

			}
		} catch (Exception e) {
			throw new Exception("error occurred processing parameters: " + e.toString());
		}
	}
	
private SOSConnection getCurrConnection() throws Exception {
		
		SOSConnection conn = null;
		try {
			this.spooler_log.debug5("DB Connecting.. .");
			if(settingsFilename != null && settingsFilename.length() > 0) {
			conn = SOSConnection.createInstance(settingsFilename, new sos.util.SOSSchedulerLogger(spooler_log));
			conn.connect();
			} else {
				conn = getConnection();
			}
			spooler_log.debug5("DB Connected");
			return conn;
		} catch (Exception e) {
			throw new Exception("\n -> ..error occurred in "
					+ SOSClassUtil.getMethodName() + ": " + e);
		}
	}
	
}
