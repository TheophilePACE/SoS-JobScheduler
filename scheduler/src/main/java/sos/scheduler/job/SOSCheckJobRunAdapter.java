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

import sos.net.ssh.SOSSSHJob2;
import sos.net.ssh.SOSSSHJobOptions;

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
 * *** get by Name ***
 * 
 * \see
 * 
 * \code 
 * .... code goes here ... 
 * \endcode
 * 
 * \author Klaus Buettner - http://www.sos-berlin.com 
* @version $Id: SOSCheckJobRunAdapter.java 15153 2011-09-14 11:59:34Z kb $1.1.0.20100506
 * 
 * This Source-Code was created by JETTemplate
 * SOSJobSchedulerAdapterClass.javajet, Version 1.0 from 2009-12-26, written by
 * kb
 */

public class SOSCheckJobRunAdapter extends JobSchedulerJobAdapter {

	private final String	conClassName	= "SOSSSHJob2JSAdapter";

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
			logger.error(StackTrace2String(e));
			return false;
		}
		finally {
		} // finally
		
		return (spooler_task.job().order_queue() != null);

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit";
//		System.out.println("--> super.exit()");

		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		SOSSSHJob2 objR = new SOSSSHJob2();
		SOSSSHJobOptions objO = objR.Options();
		objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
		objR.setJSJobUtilites(this);
		objO.CheckMandatory();
		objR.Execute();
	} // doProcessing

	private void doInitialize() throws Exception {
	} // doInitialize


} // SOSSSHJob2JSAdapter
