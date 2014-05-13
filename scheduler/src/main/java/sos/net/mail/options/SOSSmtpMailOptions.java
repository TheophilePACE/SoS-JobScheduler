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
package sos.net.mail.options;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;

/**
 * \class 		SOSSmtpMailOptions - SMTP Mail Options
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
@JSOptionClass(name = "SOSSmtpMailOptions", description = "Launch and observe any given job or job chain")
public class SOSSmtpMailOptions extends SOSSmtpMailOptionsSuperClass implements ISOSSmtpMailOptions {

	private static final long	serialVersionUID		= 6441074884525254517L;
	@SuppressWarnings("unused")
	private final String		conClassName			= "SOSSmtpMailOptions";						//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger		logger					= Logger.getLogger(SOSSmtpMailOptions.class);
	@SuppressWarnings("unused")
	private static final String	conSVNVersion			= "$Id$";

	// TODO �ber Prefix OnError_, OnSuccess_, OnEmptyFiles_ adressieren

	private SOSSmtpMailOptions	objMailOnError			= null;
	private SOSSmtpMailOptions	objMailOnSuccess		= null;
	private SOSSmtpMailOptions	objMailOnEmptyFiles		= null;

	private String				strAlternativePrefix	= "";

	public enum enuMailClasses {
		MailDefault, MailOnError, MailOnSuccess, MailOnEmptyFiles;
	}

	/**
	* constructors
	*/

	public SOSSmtpMailOptions() {
	} // public SOSSmtpMailOptions

	public SOSSmtpMailOptions(final JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public SOSSmtpMailOptions

	public SOSSmtpMailOptions getOptions(final enuMailClasses penuMailClass) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getOptions";

		SOSSmtpMailOptions objO = objMailOnError;
		switch (penuMailClass) {
			case MailOnError:
				break;

			case MailOnEmptyFiles:
				objO = objMailOnEmptyFiles;
				break;
			case MailOnSuccess:
				objO = objMailOnSuccess;
				break;

			default:
				objO = this;
				break;
		}

		return objO;
	} // private SOSSmtpMailOptions getOptions

	public SOSSmtpMailOptions(final HashMap<String, String> JSSettings) throws Exception {
		super(JSSettings);
		objMailOnError = new SOSSmtpMailOptions(JSSettings, "MailOnError_");
		objMailOnSuccess = new SOSSmtpMailOptions(JSSettings, "MailOnSuccess_");
		objMailOnEmptyFiles = new SOSSmtpMailOptions(JSSettings, "MailOnEmptyFiles_");

	} // public SOSSmtpMailOptions (HashMap JSSettings)

	public SOSSmtpMailOptions(final HashMap<String, String> JSSettings, final String pstrPrefix) throws Exception {
		strAlternativePrefix = pstrPrefix;
		setAllOptions(JSSettings, strAlternativePrefix);
		logger.trace(this.dirtyString());
	} // public SOSSmtpMailOptions (HashMap JSSettings)

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
	// SOSSmtpMailOptionsSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()
}
