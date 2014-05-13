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
package com.sos.jitl.mail.smtp;

import java.util.HashMap;

import org.apache.log4j.Logger;

import sos.net.mail.options.SOSSmtpMailOptions;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;

/**
 * \class 		JSSmtpMailOptions - SMTP Mail Options
 *
 * \brief
 * An Options as a container for the Options super class.
 * The Option class will hold all the things, which would be otherwise overwritten at a re-creation
 * of the super-class.
 *
 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerSmtpMail.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by JobDocu2OptionsClass.xslt from http://www.sos-berlin.com at 20111124184709
 * \endverbatim
 */
@JSOptionClass(name = "JSSmtpMailOptions", description = "Launch and observe any given job or job chain")
public class JSSmtpMailOptions extends SOSSmtpMailOptions {

	private static final long	serialVersionUID		= 6441074884525254517L;
	@SuppressWarnings("unused")
	private final String		conClassName			= "JSSmtpMailOptions";							//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger		logger					= Logger.getLogger(JSSmtpMailOptions.class);
	@SuppressWarnings("unused")
	private static final String	conSVNVersion			= "$Id$";

	// TODO �ber Prefix OnError_, OnSuccess_, OnJobStart_ adressieren

	public JSSmtpMailOptions	objMailOnError			= null;
	public JSSmtpMailOptions	objMailOnSuccess		= null;
	public JSSmtpMailOptions	objMailOnJobStart		= null;

	private String				strAlternativePrefix	= "";

	public enum enuMailClasses {
		MailDefault, MailOnError, MailOnSuccess, MailOnJobStart;
	}

	/**
	* constructors
	*/

	public JSSmtpMailOptions() {
	} // public JSSmtpMailOptions

	public JSSmtpMailOptions(final JSListener pobjListener) {
		this();
	} // public JSSmtpMailOptions

	public SOSSmtpMailOptions getOptions(final enuMailClasses penuMailClass) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getOptions";

		SOSSmtpMailOptions objO = objMailOnError;
		switch (penuMailClass) {
			case MailOnError:
				break;

			case MailOnJobStart:
				objO = objMailOnJobStart;
				break;

			case MailOnSuccess:
				objO = objMailOnSuccess;
				break;

			default:
				objO = this;
				break;
		}

		return objO;
	} // private JSSmtpMailOptions getOptions

	public JSSmtpMailOptions(final HashMap<String, String> JSSettings) throws Exception {
		super(JSSettings);
		objMailOnError = new JSSmtpMailOptions(JSSettings, "MailOnError_");
		objMailOnSuccess = new JSSmtpMailOptions(JSSettings, "MailOnSuccess_");
		// ....
		objMailOnJobStart = new JSSmtpMailOptions(JSSettings, "MailOnJobStart_");

	} // public JSSmtpMailOptions (HashMap JSSettings)

	public boolean MailOnJobStart() {
		boolean flgR = false;
		if (objMailOnJobStart == null) {
			objMailOnJobStart = new JSSmtpMailOptions(Settings(), "MailOnJobStart_");
			mergeDefaultSettings(objMailOnJobStart);
		}
		flgR = objMailOnJobStart.to.isDirty();
		return flgR;
	}

	public boolean MailOnError() {
		boolean flgR = false;
		if (objMailOnError == null) {
			objMailOnError = new JSSmtpMailOptions(Settings(), "MailOnError_");
			mergeDefaultSettings(objMailOnError);
		}

		flgR = objMailOnError.to.isDirty();
		return flgR;
	}

	public boolean MailOnSuccess() {
		boolean flgR = false;
		if (objMailOnSuccess == null) {
			objMailOnSuccess = new JSSmtpMailOptions(Settings(), "MailOnSuccess_");
			mergeDefaultSettings(objMailOnSuccess);
		}
		flgR = objMailOnSuccess.to.isDirty();
		return flgR;
	}

	private void mergeDefaultSettings (final JSSmtpMailOptions pobjOpt) {
		// TODO mark Options as default and assign by reflection (extend baseclass) 
		if (pobjOpt.host.isNotDirty()) {
			pobjOpt.host.Value(host.Value());
		}
		if (pobjOpt.port.isNotDirty()) {
			pobjOpt.port.Value(port.Value());
		}
		if (pobjOpt.smtp_user.isNotDirty()) {
			pobjOpt.smtp_user.Value(smtp_user.Value());
		}
		if (pobjOpt.smtp_password.isNotDirty()) {
			pobjOpt.smtp_password.Value(smtp_password.Value());
		}
	}
//	@Override
//	public void setAllOptions(final HashMap<String, String> JSSettings) throws Exception {
//		@SuppressWarnings("unused")
//		final String conMethodName = conClassName + "::setAllOptions";
//		setAllCommonOptions(JSSettings);
//		super.setAllOptions(JSSettings);
//		objMailOnError = new JSSmtpMailOptions(JSSettings, "MailOnError_");
//		objMailOnSuccess = new JSSmtpMailOptions(JSSettings, "MailOnSuccess_");
//		// ....
//		objMailOnJobStart = new JSSmtpMailOptions(JSSettings, "MailOnJobStart_");
//
//	} // public void setAllOptions}

	public JSSmtpMailOptions(final HashMap<String, String> JSSettings, final String pstrPrefix)  {
		strAlternativePrefix = pstrPrefix;
		try {
			super.setAllOptions(JSSettings, strAlternativePrefix);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.trace(this.dirtyString());
	} // public JSSmtpMailOptions (HashMap JSSettings)

	/**
	 * \brief CheckMandatory - pr�ft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgel�st, wenn eine mandatory-Option keinen Wert hat
	 */
	@Override
	// JSSmtpMailOptionsSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()
}
