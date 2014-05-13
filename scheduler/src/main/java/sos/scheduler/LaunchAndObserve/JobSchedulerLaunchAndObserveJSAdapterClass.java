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
package sos.scheduler.LaunchAndObserve;

import java.util.HashMap;

import org.apache.log4j.Logger;

import sos.net.mail.options.ISOSSmtpMailOptions;
import sos.net.mail.options.SOSSmtpMailOptions;
import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Mail;
import sos.spooler.Spooler;

/**
 * \class 		JobSchedulerLaunchAndObserveJSAdapterClass - JobScheduler Adapter for "Launch and observe any given job or job chain"
 *
 * \brief AdapterClass of JobSchedulerLaunchAndObserve for the SOSJobScheduler
 *
 * This Class JobSchedulerLaunchAndObserveJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JobSchedulerLaunchAndObserve.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerLaunchAndObserve.xml for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20111124184800 
 * \endverbatim
 */
public class JobSchedulerLaunchAndObserveJSAdapterClass extends JobSchedulerJobAdapter {
	private final String	conClassName	= "JobSchedulerLaunchAndObserveJSAdapterClass";						//$NON-NLS-1$
	private static Logger	logger			= Logger.getLogger(JobSchedulerLaunchAndObserveJSAdapterClass.class);

	@SuppressWarnings("unused")
	private final String				conSVNVersion		= "$Id: JobSchedulerLaunchAndObserveJSAdapterClass.java 15749 2011-11-22 16:04:10Z kb $";

	public void init() {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			return false;
		}
		finally {
		} // finally
			// return value for classic and order driven processing
			// TODO create method in base-class for this functionality
		return (spooler_task.job().order_queue() != null);

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::doProcessing"; //$NON-NLS-1$

		JobSchedulerLaunchAndObserve objR = new JobSchedulerLaunchAndObserve();
		JobSchedulerLaunchAndObserveOptions objO = objR.Options();
		objO.CurrentNodeName(this.getCurrentNodeName());
		HashMap <String, String> objH = getSchedulerParameterAsProperties(getParameters());
		objO.setAllOptions(objH);
		
		Object objSp = getSpoolerObject();
		Spooler objSpooler = (Spooler) objSp;
		Mail objMail = objSpooler.log().mail();
		ISOSSmtpMailOptions	objMailOnRestartOptions	= objO.getMailOnRestartOptions();
		ISOSSmtpMailOptions	objMailOnKillOptions = objO.getMailOnRestartOptions();
		
		if(objO.getscheduler_host().IsEmpty()) {
			objH.put(objO.getscheduler_host().getShortKey(), objSpooler.hostname());
		}
		if(objO.getscheduler_port().value() == 0) {
			objH.put(objO.getscheduler_port().getShortKey(), ""+objSpooler.tcp_port());
		}
		
		if(objMailOnRestartOptions.gethost().IsEmpty()) {
			objH.put(objMailOnRestartOptions.gethost().getShortKey(), objMail.smtp());
		}
		if(objMailOnRestartOptions.getfrom().IsEmpty()) {
			objH.put(objMailOnRestartOptions.getfrom().getShortKey(), objMail.from());
		}
		if(objMailOnRestartOptions.getqueue_directory().IsEmpty()) {
			objH.put(objMailOnRestartOptions.getqueue_directory().getShortKey(), objMail.queue_dir());
		}
		
		if(objMailOnKillOptions.gethost().IsEmpty()) {
			objH.put(objMailOnRestartOptions.gethost().getShortKey(), objMail.smtp());
		}
		if(objMailOnKillOptions.getfrom().IsEmpty()) {
			objH.put(objMailOnRestartOptions.getfrom().getShortKey(), objMail.from());
		}
		if(objMailOnKillOptions.getqueue_directory().IsEmpty()) {
			objH.put(objMailOnRestartOptions.getqueue_directory().getShortKey(), objMail.queue_dir());
		}
		
		objO.setAllOptions(objH);
		SOSSmtpMailOptions objM = (SOSSmtpMailOptions) objO.getMailOnRestartOptions();
		objM.setAllOptions(objH);
		logger.debug(objM.toString());
		
		SOSSmtpMailOptions objK = (SOSSmtpMailOptions) objO.getMailOnKillOptions();
		objK.setAllOptions(objH);
		logger.debug(objK.toString());

		
		objO.CheckMandatory();
		objR.setJSJobUtilites(this);
		objR.Execute();
	} // doProcessing

}
