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

import java.util.HashMap;

import org.apache.log4j.Logger;

import sos.net.ssh.SOSSSHJob2;
import sos.net.ssh.SOSSSHJobOptions;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * \class SOSSSHJob2JSAdapter
 *
 * \brief
 * AdapterClass of SOSSSHJob2JSAdapter for the SOSJobScheduler
 *
 * Start a Script, a command or a script-file using SSH on a remote server.
 *
 * This Class SOSSSHJob2JSAdapter works as an adapter-class between the SOS
 * JobScheduler and the worker-class SOSSSHJob2.
 *
 * \see
 *
 * \code
 * .... code goes here ...
 * \endcode
 *
 * \author Klaus Buettner - http://www.sos-berlin.com
* @version $Id: SOSSSHJob2JSAdapter.java 20725 2013-07-18 18:23:05Z kb $1.1.0.20100506
 *
 */

public class SOSSSHJob2JSAdapter extends JobSchedulerJobAdapter {

	private final String conClassName = this.getClass().getSimpleName();
	@SuppressWarnings("hiding")
	private final Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private final String	conSVNVersion	= "$Id: SOSSSHJob2JSAdapter.java 20725 2013-07-18 18:23:05Z kb $";

	public void init() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";

		doInitialize();
	}

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init";

		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_process";

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			logger.fatal(StackTrace2String(e));
			throw new JobSchedulerException(e);
		}
		finally {
		} // finally

		return signalSuccess();

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit";

		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		SOSSSHJob2 objR = new SOSSSHJob2();
		SOSSSHJobOptions objO = objR.Options();
		objO.CurrentNodeName(this.getCurrentNodeName());
		//		objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
		HashMap<String, String> hsmParameters1 = getSchedulerParameterAsProperties(getJobOrOrderParameters());
		// see http://www.sos-berlin.com/otrs/index.pl?Action=AgentTicketZoom&TicketID=267&ArticleID=1275
		objO.setAllOptions(objO.DeletePrefix(hsmParameters1, "ssh_"));

		objR.setJSJobUtilites(this);
		// Allow <script> tag of job as command parameter
		// http://www.sos-berlin.com/jira/browse/JITL-48
		if (objO.commandSpecified() == false) {
			String strS = getJobScript();
			if (isNotEmpty(strS)) {
				objO.command_script.Value(strS);
				logger.debug("setting 'command_script' value from script tag of job: \n" + strS);
			}
		}

		objO.CheckMandatory();
		objR.Execute();
	} // doProcessing

	private void doInitialize() throws Exception {
	} // doInitialize

} // SOSSSHJob2JSAdapter
