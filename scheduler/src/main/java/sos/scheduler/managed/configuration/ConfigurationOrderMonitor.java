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
package sos.scheduler.managed.configuration;

import java.io.File;

import sos.spooler.Order;
import sos.util.SOSSchedulerLogger;
import sos.xml.SOSXMLXPath;

/**
 * <p>ConfigurationOrderMonitor implements a Monitor script, which reads parameters for the order
 * (per job chain node) from an XML configuration (file)</p>

 *
 * * !!!!  Attention: this program is using the xml-paylod for making the changes persistent !!!!

 * @author andreas.pueschel@sos-berlin.com
 * @since 1.0 2006-10-05
 */

public class ConfigurationOrderMonitor extends ConfigurationBaseMonitor {

	/**
	 * Initialisierung vor Verarbeitung eines Auftrags
	 * @see sos.spooler.Monitor_impl#spooler_process_before()
	 */
	@Override
	public boolean spooler_process_before() {

		try { // to map order configuration to this job
			this.setLogger(new SOSSchedulerLogger(spooler_log));
			Order order = spooler_task.order();
			String liveFolder = "";
			String orderXML = order.xml();
			SOSXMLXPath xp = new SOSXMLXPath(new StringBuffer(orderXML));
			String jobChainPath = xp.selectSingleNodeValue("/order/@job_chain");

			if (order.params().value("configuration_path") != null && order.params().value("configuration_path").length() > 0) {
				this.setConfigurationPath(order.params().value("configuration_path"));
			}
			else
				if (spooler_task.params().value("configuration_path") != null && spooler_task.params().value("configuration_path").length() > 0) {
					this.setConfigurationPath(spooler_task.params().value("configuration_path"));
				}
				else {
					if (spooler_job.configuration_directory().length() > 0) {
						File fLiveBaseFolder = new File(spooler.configuration_directory());
						File sJobChainPath = new File(fLiveBaseFolder, jobChainPath + ".job_chain.xml");
						this.getLogger().debug7("Looking for job chain configuration file: " + sJobChainPath.getAbsolutePath());
						if (!sJobChainPath.exists()) {
							this.getLogger().debug2("Job Chain is probably configured in cache folder and not in live folder...");
							File fCacheBaseFolder = new File(fLiveBaseFolder.getParentFile(), "cache");
							sJobChainPath = new File(fCacheBaseFolder, jobChainPath);
						}
						liveFolder = sJobChainPath.getParent();

						this.setConfigurationPath(liveFolder);
					}
					else {
						this.setConfigurationPath(new File(spooler.ini_path()).getParent());
					}
					this.getLogger().debug2(".. parameter [configuration_path]: " + this.getConfigurationPath());
				}

			if (order.params().value("configuration_file") != null && order.params().value("configuration_file").length() > 0) {
				this.setConfigurationFilename(order.params().value("configuration_file"));
			}
			else
				if (spooler_task.params().value("configuration_file") != null && spooler_task.params().value("configuration_file").length() > 0) {
					this.setConfigurationFilename(spooler_task.params().value("configuration_file"));
				}
				else {
					if (spooler_job.order_queue() != null) {
						// this.setConfigurationFilename("scheduler_" + spooler_task.order().job_chain().name() + "_" + order.id() + ".config.xml");
						this.getLogger().debug2(".. parameter [configuration_file]: " + this.getConfigurationFilename());
						if (spooler_job.configuration_directory().length() > 0) {
							File confFile = new File(getConfigurationPath(), order.job_chain().name() + ".config.xml");
							File confOrderFile = new File(getConfigurationPath(), order.job_chain().name() + "," + order.id() + ".config.xml");
							if (confOrderFile.exists()) {
								this.setConfigurationFilename(confOrderFile.getAbsolutePath());
								this.getLogger().debug2(".. configuration file for this order exists. order_id:" + order.job_chain().name() + "/" + order.id());
							}
							else {
								this.setConfigurationFilename(confFile.getAbsolutePath());
								this.getLogger().debug2(".. configuration file for job chain:" + order.job_chain().name());
							}
						}
						else {
							this.setConfigurationFilename("scheduler_" + spooler_task.order().job_chain().name() + ".config.xml");
						}
						this.getLogger().debug2(".. parameter [configuration_file]: " + this.getConfigurationFilename());
					}
				}

			//Hier ein Pflaster analog zu ConfigurationBaseMonitor...
			File confFile = null;
			if (this.getConfigurationFilename().startsWith(".") || this.getConfigurationFilename().startsWith("/")
					|| this.getConfigurationFilename().startsWith("\\") || this.getConfigurationFilename().indexOf(":") > -1
					|| this.getConfigurationFilename() == null || this.getConfigurationFilename().length() == 0) {
				confFile = new File(this.getConfigurationFilename());
			}
			else {
				confFile = new File(this.getConfigurationPath(), this.getConfigurationFilename());
			}

			//Also Task-Parameters should be substituted.
			//Deleted because it has to many side effects.
			/*   Variable_set v = spooler.create_variable_set();
			   v.merge(spooler_task.params());
			   v.merge( spooler_task.order().params());
			   spooler_task.order().params().merge(v);
			   */

			if (confFile.exists()) {
				this.initConfiguration();
				this.prepareConfiguration();
			}
			else {
				if (spooler_task.order().xml_payload() != null) {
					spooler_log.info("Configuration File: " + confFile.getAbsolutePath() + " not found (Probably running on an agent).");
					spooler_log.info("Reading configuration from xml payload...");
					try {
						this.prepareConfiguration();
					}
					catch (Exception e) {
					}
				}
			}

			return true;
		}
		catch (Exception e) {
			spooler_log.warn("error occurred in spooler_process_before: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cleanup nach Verarbeitung eines Auftrags
	 * @throws Exception
	 * @see sos.spooler.Monitor_impl#spooler_process_after()
	 */

	@Override
	public boolean spooler_process_after(final boolean rc) throws Exception {
		this.cleanupConfiguration();
		return rc;
		/* Ist nicht mehr notwendig, da es on error setback am Knoten gibt. F�hrt dann zu doppelten setbacks.
		try { // to map order configuration to this job
		    this.setLogger(new SOSSchedulerLogger(spooler_log));

		    Order order = spooler_task.order();

		    if (rc == false) {
		        if (order.params() != null && order.params().value("setback") != null
		                && (  order.params().value("setback").equalsIgnoreCase("false")
		                   || order.params().value("setback").equalsIgnoreCase("no")
		                   || order.params().value("setback").equals("0")
		                )) {
		            // clear setback parameter for other jobs
		        	spooler_task.order().params().set_var("setback", "");
		        } else {
		            spooler_task.order().setback();
		        }
		    }

		    return rc;
		} catch (Exception e) {
		    spooler_log.warn("error occurred in spooler_process_after(): " + e.getMessage());
		    return false;
		} finally {
		    try { this.cleanupConfiguration(); } catch (Exception e) {}
		}
		*/
	}
}
