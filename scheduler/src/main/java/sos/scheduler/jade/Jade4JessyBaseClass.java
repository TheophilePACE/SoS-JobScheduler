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
package sos.scheduler.jade;

import java.util.HashMap;

import sos.scheduler.job.JobSchedulerJobAdapter;

import com.sos.DataExchange.SOSDataExchangeEngine;
import com.sos.VirtualFileSystem.FTP.SOSFTPOptions;
import com.sos.i18n.annotation.I18NResourceBundle;

/** 
 * \file Jade4JessyBaseClass.java
 * \class 		Jade4JessyBaseClass
 *
 * \brief 
 * AdapterClass of SOSDEx for the SOSJobScheduler
 *
 * This Class Jade4JessyBaseClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class SOSDEx.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\SOSDEx.xml for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\sos.scheduler.xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20100930175652 
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com.sos.scheduler.messages", defaultLocale = "en")
abstract public class Jade4JessyBaseClass extends JobSchedulerJobAdapter {
	private final String			conClassName	= "Jade4JessyBaseClass";											//$NON-NLS-1$
	@SuppressWarnings("unused")
	private final String			conSVNVersion	= "$Id: Jade4JessyBaseClass.java 15334 2011-10-17 07:44:50Z oh $";

	protected SOSFTPOptions			objO			= null;
	protected SOSDataExchangeEngine	objR			= null;
	protected HashMap<String, String> hsmParameters = null;
	
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
			e.printStackTrace();
			logger.error(String.format("%1$s ended abnormal.", conClassName));
			logger.error(StackTrace2String(e));
			return signalFailure();
		}
		finally {
		} // finally
			// return value for classic and order driven processing
		return signalSuccess();

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")//$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		final String conMethodName = conClassName + "::doProcessing"; //$NON-NLS-1$

		showVersionInfo();
		
		objR = new SOSDataExchangeEngine();
		objO = objR.Options();
		objO.CurrentNodeName(getCurrentNodeName());
		hsmParameters = getSchedulerParameterAsProperties(getParameters());

		objO.setAllOptions(objO.DeletePrefix(hsmParameters, "ftp_"));
		objO.CheckMandatory();

		setSpecialOptions();

		int intLogLevel = spooler_log.level();
		if (intLogLevel < 0) {
			logger.debug(objO.toString());
		}

		logger.info(String.format("%1$s with operation %2$s started.", conMethodName, objO.operation.Value()));
		objR.setJSJobUtilites(this);
		objR.Execute();
		objR.Logout();
		logger.info(String.format("%1$s with operation %2$s ended.", conMethodName, objO.operation.Value()));
	} // doProcessing

	abstract protected void setSpecialOptions();
	abstract protected void showVersionInfo();
}
