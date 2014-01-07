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
package sos.scheduler.reports;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Basics.JSVersionInfo;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		JSReportAllParameters - Workerclass for "Report all Parameters"
 *
 * \brief AdapterClass of JSReportAllParameters for the SOSJobScheduler
 *
 * This Class JSReportAllParameters is the worker-class.
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JSReportAllParameters.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\sos.scheduler.xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20110516150411
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com.sos.scheduler.messages", defaultLocale = "en")
public class JSReportAllParameters extends JSToolBox implements JSJobUtilities {
	private final String					conSVNVersion		= "$Id: JSReportAllParameters.java 19333 2013-03-02 09:49:40Z kb $";
	private final String					conClassName		= this.getClass().getName();

	private static Logger					logger				= Logger.getLogger(JSReportAllParameters.class);

	protected JSReportAllParametersOptions	objOptions			= null;
	private JSJobUtilities					objJSJobUtilities	= this;

	/**
	 *
	 * \brief JSReportAllParameters
	 *
	 * \details
	 *
	 */
	public JSReportAllParameters() {
		super("com_sos_scheduler_messages");

	}

	/**
	 *
	 * \brief Options - returns the JSReportAllParametersOptionClass
	 *
	 * \details
	 * The JSReportAllParametersOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JSReportAllParametersOptions
	 *
	 */
	public JSReportAllParametersOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JSReportAllParametersOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Options - set the JSReportAllParametersOptionClass
	 *
	 * \details
	 * The JSReportAllParametersOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JSReportAllParametersOptions
	 *
	 */
	public JSReportAllParametersOptions Options(final JSReportAllParametersOptions pobjOptions) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		objOptions = pobjOptions;
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JSReportAllParameters
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JSReportAllParametersMain
	 *
	 * \return JSReportAllParameters
	 *
	 * @return
	 */
	public JSReportAllParameters Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		try {
			logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName));
			logger.info(conSVNVersion);
			logger.info(JSVersionInfo.getVersionString());
			// Options().CheckMandatory();
			logger.debug(Options().toString());
			HashMap<String, String> objSettings = Options().Settings();
			for (final Object element : objSettings.entrySet()) {
				final Map.Entry mapItem = (Map.Entry) element;
				final String strMapKey = mapItem.getKey().toString();
				String strTemp = "";
				if (mapItem.getValue() != null) {
					strTemp = mapItem.getValue().toString();
					if (strMapKey.contains("password")) {
						strTemp = "***";
					}
				}
				logger.info("Key = " + strMapKey + " --> " + strTemp);
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException(Messages.getMsg("JSJ-I-107", conMethodName), e);
		}
		finally {
			logger.debug(Messages.getMsg("JSJ-I-111", conMethodName));
		}

		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public String myReplaceAll(final String pstrSourceString, final String pstrReplaceWhat, final String pstrReplaceWith) {

		String newReplacement = pstrReplaceWith.replaceAll("\\$", "\\\\\\$");
		return pstrSourceString.replaceAll("(?m)" + pstrReplaceWhat, newReplacement);
	}

	/**
	 *
	 * \brief replaceSchedulerVars
	 *
	 * \details
	 * Dummy-Method to make sure, that there is always a valid Instance for the JSJobUtilities.
	 * \return
	 *
	 * @param isWindows
	 * @param pstrString2Modify
	 * @return
	 */
	@Override
	public String replaceSchedulerVars(final boolean isWindows, final String pstrString2Modify) {
		logger.debug("replaceSchedulerVars as Dummy-call executed. No Instance of JobUtilites specified.");
		return pstrString2Modify;
	}

	/**
	 *
	 * \brief setJSParam
	 *
	 * \details
	 * Dummy-Method to make shure, that there is always a valid Instance for the JSJobUtilities.
	 * \return
	 *
	 * @param pstrKey
	 * @param pstrValue
	 */
	@Override
	public void setJSParam(final String pstrKey, final String pstrValue) {

	}

	@Override
	public void setJSParam(final String pstrKey, final StringBuffer pstrValue) {

	}

	/**
	 *
	 * \brief setJSJobUtilites
	 *
	 * \details
	 * The JobUtilities are a set of methods used by the SSH-Job or can be used be other, similar, job-
	 * implementations.
	 *
	 * \return void
	 *
	 * @param pobjJSJobUtilities
	 */
	@Override
	public void setJSJobUtilites(final JSJobUtilities pobjJSJobUtilities) {

		if (pobjJSJobUtilities == null) {
			objJSJobUtilities = this;
		}
		else {
			objJSJobUtilities = pobjJSJobUtilities;
		}
		logger.debug("objJSJobUtilities = " + objJSJobUtilities.getClass().getName());
	}

	@Override
	public String getCurrentNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStateText(final String pstrStateText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCC(final int pintCC) {
		// TODO Auto-generated method stub
		
	}

} // class JSReportAllParameters
