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
 * Created on 28.02.2011
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package sos.scheduler.InstallationService.batchInstallationModel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import sos.scheduler.InstallationService.JSBatchInstaller;
import sos.scheduler.InstallationService.batchInstallationModel.installations.Installation;
import sos.spooler.Job_chain;
import sos.spooler.Order;
import sos.spooler.Spooler;

public class JSBatchInstallerExecuter {

	private Order				order				= null;
	private JSBatchInstaller	jsBatchInstaller	= null;
	private File				localDir;
	private File				installationDefinitionFile;
	private String				installationJobChain;
	private static Logger		logger				= Logger.getLogger(JSBatchInstallerExecuter.class);

	private boolean				update;												//Alle ausf�hren auf filterInstallHost:filterInstallPort 
	private String				filterInstallHost	= "";
	private int					filterInstallPort	= 0;

	private void init() {
		localDir = new File(jsBatchInstaller.Options().getlocal_dir().Value());
		installationDefinitionFile = new File(jsBatchInstaller.Options().getinstallation_definition_file().Value());
		 
		installationJobChain = jsBatchInstaller.Options().getinstallation_job_chain().Value();
		update = jsBatchInstaller.Options().getupdate().isTrue(); //Alle ausf�hren auf filterInstallHost:filterInstallPort 
		filterInstallHost = jsBatchInstaller.Options().getfilter_install_host().Value();
		filterInstallPort = jsBatchInstaller.Options().getfilter_install_port().value();
	}

	private boolean filterNotSetOrFilterMatch(String value, String filter) { 
		logger.debug("Testing filter:" + value + "=" + filter);
		return (value.equals(filter) || filter == null || filter.trim().equals(""));
	}
	
	private boolean filterNotSetOrFilterMatch(int value, int filter) { 
		logger.debug("Testing filter:" + value + "=" + filter);
		return (value == filter || filter == 0);
	}	
	
	private boolean checkFilter(JSinstallation jsInstallation) {
		boolean filterMatch = filterNotSetOrFilterMatch(jsInstallation.getHost(),filterInstallHost) &&
				              filterNotSetOrFilterMatch(jsInstallation.getSchedulerPort(),filterInstallPort); 
		logger.debug("FilterMatch: " + filterMatch);

		boolean installationNotExecuted = jsInstallation.getLastRun() == null || jsInstallation.getLastRun().equals("");
		logger.debug("installationNotExecuted: " + installationNotExecuted + "(lastRun=" + jsInstallation.getLastRun() +")");

		if (filterMatch && (installationNotExecuted || update)){
			return true;
		}else {
			if (!filterMatch) {
				logger.info("Installation will not execute because filter does not match");
			}
			if (!installationNotExecuted) {
				logger.info("Installation will not execute because already was executed");
			}
			return false;
		}
	}

	private void updateLastRun(File installationsDefinitionFile) throws Exception {
		JSInstallations jsInstallationsUpdateFile = new JSInstallations(installationsDefinitionFile);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
		String now = dateFormat.format(new Date());

		while (!jsInstallationsUpdateFile.eof()) {
			Installation installationUpdate = jsInstallationsUpdateFile.nextInstallation();
			if (installationUpdate.getLastRun() == null || installationUpdate.getLastRun().equals("") || update) {
			   installationUpdate.setLastRun(now);
			}
		}
		jsInstallationsUpdateFile.writeFile(installationsDefinitionFile);

	}

	public void performInstallation(JSBatchInstaller jsBatchInstaller_) throws Exception {
		this.jsBatchInstaller = jsBatchInstaller_;
		init();
		installationDefinitionFile = new File(jsBatchInstaller.Options().getinstallation_definition_file().Value());
		JSInstallations jsInstallations = new JSInstallations(installationDefinitionFile);

		while (!jsInstallations.eof()) {
			JSinstallation installation = jsInstallations.next();
			if (checkFilter(installation)) {
				createOrder(installation);
			}
			else {
				installation.doReplacing();
				logger.info(String.format("Skip creation of order for scheduler id %1$s", installation.getSchedulerId()));
			}
		}

		updateLastRun(installationDefinitionFile);
	}

	private void createOrder(JSinstallation installation) throws Exception {
		Spooler spooler = (Spooler) jsBatchInstaller.getJSCommands().getSpoolerObject();

		File installationFile = installation.getInstallationFile(localDir);

		installation.doReplacing();
		logger.info(String.format("Start to create order for scheduler id %1$s", installation.getSchedulerId()));

		JSXMLInstallationFile jsXMLInstallationFile = new JSXMLInstallationFile();
		jsXMLInstallationFile.setValues(installation);

		jsXMLInstallationFile.writeFile(installationFile);

		logger.info("scheduler_id:" + installation.getSchedulerId());
		logger.info("host:" + installation.getHost());
		logger.info("install_path:" + installation.getInstallPath());
		logger.info("licence:" + installation.getLicence());
		logger.info("allowed_host:" + installation.getSchedulerAllowedHost());
		logger.info("scheduler_port:" + installation.getSchedulerPort());
		logger.info("userPathPanelElement:" + installation.getUserPathPanelElement());

		logger.info("----------------------------------------------");

		if (spooler == null) {
			logger.info("Creation of order is skipped because spooler object is NULL");
			return;
		}
		//spooler_job.set_state_text("Start Installation of " + installation.getHost() + ":" + installation.getServicePort());

		order = spooler.create_order();
		Job_chain jobchain = spooler.job_chain(installationJobChain);
		order.set_id(installation.getHost() + ":" + installation.getSchedulerPort());

		setParam("installation_file", installationFile.getName());

		setParam("ftp_user", installation.getFtp().getUser());
		setParam("ftp_local_dir", installation.getFtp().getLocalDir());
		setParam("ftp_host", installation.getHost());
		setParam("ftp_password", installation.getFtp().getPassword());
		setParam("ftp_remote_dir", installation.getFtp().getRemoteDir());

		setParam("TransferInstallationSetup/ftp_file_path", "scheduler_linux32_agent.jar");
		setParam("TransferInstallationFile/ftp_local_dir", installationFile.getParent());
		setParam("TransferInstallationFile/ftp_file_path", installationFile.getName());

		setParam("ShutdownScheduler/host", String.valueOf(installation.getHost()));
		setParam("ShutdownScheduler/port", String.valueOf(installation.getSsh().getPort()));
		setParam("ShutdownScheduler/user", installation.getSsh().getUser());
		setParam("ShutdownScheduler/auth_method", installation.getSsh().getAuthMethod());
		setParam("ShutdownScheduler/password", installation.getSsh().getPassword());
		setParam("ShutdownScheduler/sudo_password", installation.getSsh().getSudoPassword());
 		setParam("ShutdownScheduler/command",installation.getInstallPath() + "/" + installation.getSchedulerId() + "/bin/jobscheduler_agent.sh  stop");

		setParam("PerformInstall/host", String.valueOf(installation.getHost()));
		setParam("PerformInstall/port", String.valueOf(installation.getSsh().getPort()));
		setParam("PerformInstall/user", installation.getSsh().getUser());
		setParam("PerformInstall/auth_method", installation.getSsh().getAuthMethod());
		setParam("PerformInstall/password", installation.getSsh().getPassword());
		setParam("PerformInstall/sudo_password", installation.getSsh().getSudoPassword());
		setParam("PerformInstall/command", installation.getSsh().getCommand());

		jobchain.add_order(order);
	}

	private void setParam(final String pstrParamName, final String pstrParamValue) {
		logger.info("ParamName = " + pstrParamName + ", Value = " + pstrParamValue);
		order.params().set_var(pstrParamName, pstrParamValue);
	}

	

}
