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
package com.sos.jitl.mail.smtp;

import org.apache.log4j.Logger;

import sos.scheduler.job.JobSchedulerJobAdapter;

// Super-Class for JobScheduler Java-API-Jobs
/**
 * \class 		JSMailClientJSAdapterClass - JobScheduler Adapter for "sending eMails via SMTP"
 *
 * \brief AdapterClass of JSMailClient for the SOSJobScheduler
 *
 * This Class JSMailClientJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JSMailClient.
 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-4778075809216214864.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\latestscheduler\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20130109134235
 * \endverbatim
 */
public class JSSmtpMailClientBaseClass extends JobSchedulerJobAdapter {
	@SuppressWarnings("unused")
	private final String		conClassName					= this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String	conSVNVersion					= "$Id$";
	@SuppressWarnings("unused")
	private final Logger		logger							= Logger.getLogger(this.getClass());
	protected final boolean		continue_with_spooler_process	= true;
	protected final boolean		continue_with_task				= true;

	protected JSSmtpMailClient	objR							= null;
	protected JSSmtpMailOptions	objO							= null;

	protected void CreateOptions(final String pstrEntryPointName) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";

		initializeLog4jAppenderClass();

		objR = new JSSmtpMailClient();
		objO = objR.Options();

		objR.setJSJobUtilites(this);
		objR.setJSCommands(this);
		String strStepName = this.getCurrentNodeName();
		//		if (pstrEntryPointName.length() > 0) {
		//			strStepName = pstrEntryPointName + "@" + strStepName;
		//			logger.debug("Options-Prefix is " + strStepName);
		//		}
		objO.CurrentNodeName(strStepName);

		objO.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
	} // doProcessing

	protected void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";
		CreateOptions("");
		objR.Execute();
	} // doProcessing

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

}
