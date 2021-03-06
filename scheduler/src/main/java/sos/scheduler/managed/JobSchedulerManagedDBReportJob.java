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
 * JobSchedulerManagedDBReportJob.java
 * Created on 20.06.2005
 * 
 */
package sos.scheduler.managed;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import sos.connection.SOSConnection;
import sos.spooler.Order;
import sos.xml.SOSXMLTransformer;

/**
 * Diese Klasse f�hrt Datanbank-Statements f�r Managed Orders aus und
 * verschickt einen Report im XML Format. Optional kann das xml mit Hilfe
 * eines Stylesheets in ein anderes Format gewandelt werden.<br>
 * Parameter:<br>
 * <ul>
 *   <li><strong>scheduler_order_report_stylesheet<strong>: Pfad zu einem Stylesheet, das
 * 		f�r die xslt Transformation verwendet werden soll</li>
 *   <li><strong>scheduler_order_report_mailto<strong>: email-Empf�nger des
 * 		 Reports.</li>
 *   <li><strong>scheduler_order_report_mailcc<strong>: email-cc-Empf�nger des
 * 		 Reports.</li>
 *   <li><strong>scheduler_order_report_mailbcc<strong>: email-bcc-Empf�nger des
 * 		 Reports.</li>
 *   <li><strong>scheduler_order_report_subject<strong>: email-subject des
 * 		 Reports.</li>
 *   <li><strong>scheduler_order_report_body<strong>: email-body des
 * 		 Reports.</li>
 *   <li><strong>scheduler_order_report_asbody<strong>: Bei 1 wird der Report nicht
 * 		als Attachment, sondern als body der email verschickt.</li>
 * 	 <li><strong>scheduler_order_report_filename<strong>: Der gegebene Dateiname wird 
 *      f�r den Namen des Attachments verwendet und f�r die permanent
 *      abgelegte Datei, falls ein Report-Pfad angegeben wird.</li>
 *   <li><strong>scheduler_order_report_path<strong>: Wird hier ein Pfad angegeben, so 
 *      wird der Report nicht nur per email verschickt, sondern auch hier abgelegt.</li>
 * </ul>
 * @author Andreas Liebert 
 */
public class JobSchedulerManagedDBReportJob extends JobSchedulerManagedDatabaseJob {

	private static final String	conParameterSCHEDULER_ORDER_REPORT_STYLESHEET	= "scheduler_order_report_stylesheet";
	private String			xml;
	private String			sql;

	private ManagedReporter	reporter;

	@Override
	public boolean spooler_init() {
		boolean rc = super.spooler_init();
		try {
			reporter = new ManagedReporter(this);
		}
		catch (Exception e) {
			try {
				getLogger().warn("Failed to initialize Job: " + e);
			}
			catch (Exception ex) {
			}
			rc = false;
		}
		return rc;
	}

	@Override
	protected void executeStatements(final SOSConnection conn, final String command) throws Exception {
		sql = command;

		reporter.setBody("Report for statement:\n[sql]");

		reporter.setSubject("Database Report [taskid]");
		reporter.addReplacement("\\[sql\\]", sql);

		try {
			conn.setColumnNamesCaseSensitivity(flgColumn_names_case_sensitivity);
			conn.executeStatements(command);
			// conn.commit();

			HashMap <String, String> results = conn.get();
			if (results == null) {
				getLogger().info("No results found for query: " + command);
				return;
			}
			reporter.setHasResult(!results.isEmpty());
			xml = "<?xml version=\"1.0\" ?>\n";
			xml += "<report>\n";
			xml += " <table>\n";
			xml += "  <columns>\n";

			Iterator keysIt = results.keySet().iterator();
			while (keysIt.hasNext()) {
				String strColumnName = keysIt.next().toString();
				if (flgAdjust_column_names == true) {
					strColumnName = normalize(strColumnName);
				}
				xml += "<column name=\"" + strColumnName + "\"/>\n";
			}
			xml += "  </columns>\n";
			xml += "  <rows>\n";

			while (results != null && !results.isEmpty()) {
				xml += "   <row>\n";
				String debug = writeFields(results);
				xml += debug;
				// getLogger().debug9(debug);
				xml += "   </row>\n";
				results = conn.get();
			}
			conn.closeQuery();
			try {
				conn.disconnect();
			}
			catch (Exception ex) {
			} // ignore Errors
			// conn.commit();
			xml += "  </rows>\n";
			xml += " </table>\n";
			xml += "</report>";
			getLogger().debug3("Xml generated.");
			reporter.addReplacement("\\[xml\\]", xml);

			File attach = reporter.getReportFile();

			if (getOrderPayload() != null && getOrderPayload().var(conParameterSCHEDULER_ORDER_REPORT_STYLESHEET) != null
					&& getOrderPayload().var(conParameterSCHEDULER_ORDER_REPORT_STYLESHEET).length() > 0) {
				debugParamter(getOrderPayload(), conParameterSCHEDULER_ORDER_REPORT_STYLESHEET);
				File stylesheet = new File(getOrderPayload().var(conParameterSCHEDULER_ORDER_REPORT_STYLESHEET));
				getLogger().debug9("Calling stylesheet.canRead() ");
				if (!stylesheet.canRead()) {
					throw new Exception("Could not read stylesheet: " + stylesheet.getAbsolutePath());
				}
				else {
					getLogger().debug3("Doing xslt transformation...");

					try {
						getLogger().debug6("attach:" + attach.getAbsolutePath());
						SOSXMLTransformer.transform(xml, stylesheet, attach);
					}
					catch (Exception e) {
						throw new Exception("Error occured during transformation: " + e);
					}
					getLogger().debug3("Xslt transformation done.");

				}
			}
			else {
				FileOutputStream fos = new FileOutputStream(attach);
				fos.write(xml.getBytes());
				fos.flush();
				fos.close();
			}

			reporter.report();
		}
		catch (Exception e) {
			Order order = spooler_task.order();
			getLogger().warn(
					"An error occured creating database report"
							+ (order != null ? "  [Job Chain: " + order.job_chain().name() + ", Order:" + order.id() + "]" : "") + ": " + e);
			spooler_task.end();
		}
	}

	private String normalize(final String text) {
		String target;
		try {
			target = text.replaceAll("[^A-Za-z_0-9]", "_");

		}
		catch (Exception e) {
			try {
				getLogger().warn("An error occured replacing characters in element name \"" + text + "\"");
			}
			catch (Exception ex) {
			}
			return text;
		}
		return target;
	}

	private String writeFields(final HashMap results) {
		Iterator keysIt = results.keySet().iterator();
		String rc = "";
		while (keysIt.hasNext()) {
			String key = keysIt.next().toString();
			String value = results.get(key).toString();
			if (value == null || value.length() == 0)
				rc += "    <" + normalize(key) + "/>";
			else
				rc += "    <" + normalize(key) + "><![CDATA[" + value + "]]></" + normalize(key) + ">\n";
		}
		return rc;
	}

}
