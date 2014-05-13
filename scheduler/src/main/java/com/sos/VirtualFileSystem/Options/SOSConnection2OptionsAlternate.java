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
package com.sos.VirtualFileSystem.Options;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Annotations.JSOptionDefinition;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.JSHelper.Options.SOSOptionBoolean;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		SOSConnection2OptionsAlternate - Options for a connection to an uri (server, site, e.g.)
 *
 * \brief
 * An Options as a container for the Options super class.
 * The Option class will hold all the things, which would be otherwise overwritten at a re-creation
 * of the super-class.
 *
 *

 *
 * see \see j:\e\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\SOSConnection2.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by JobDocu2OptionsClass.xslt from http://www.sos-berlin.com at 20100917112404
 * \endverbatim
 */
@JSOptionClass(name = "SOSConnection2OptionsAlternate", description = "Options for a connection to an uri (server, site, e.g.)")
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSConnection2OptionsAlternate extends SOSConnection2OptionsSuperClass {
	/**
	 *
	 */
	private static final long	serialVersionUID		= 5924032437179660014L;
	private final String		conClassName			= "SOSConnection2OptionsAlternate";						//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger		logger					= Logger.getLogger(SOSConnection2OptionsAlternate.class);
	private String				strAlternativePrefix	= "";

	/**
	 * \option IgnoreCertificateError
	 * \type SOSOptionBoolean
	 * \brief IgnoreCertificateError - Ignore a SSL Certificate Error
	 *
	 * \details
	 * Ignore a SSL Certificate Error
	 * see http://www.sos-berlin.com/jira/browse/SOSFTP-173
	 * \mandatory: true
	 *
	 * \created 11.07.2013 20:04:54 by KB
	 */
	@JSOptionDefinition(name = "IgnoreCertificateError", description = "Ignore a SSL Certificate Error", key = "IgnoreCertificateError", type = "SOSOptionBoolean", mandatory = true)
	public SOSOptionBoolean		IgnoreCertificateError	= new SOSOptionBoolean( // ...
																this, // ....
																conClassName + ".IgnoreCertificateError", // ...
																"Ignore a SSL Certificate Error", // ...
																"true", // ...
																"true", // ...
																true);

	public boolean getIgnoreCertificateError() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getIgnoreCertificateError";

		return IgnoreCertificateError.value();
	} // public String getIgnoreCertificateError

	public SOSConnection2OptionsAlternate setIgnoreCertificateError(final boolean pflgValue) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setIgnoreCertificateError";
		IgnoreCertificateError.value(pflgValue);
		return this;
	} // public SOSConnection2OptionsAlternate setIgnoreCertificateError

	/**
	 * \option AlternateOptionsUsed
	 * \type SOSOptionBoolean
	 * \brief AlternateOptionsUsed - Alternate Options used for connection and/or authentication
	 *
	 * \details
	 * Alternate Options used for connection and/or authentication
	 *
	 * \mandatory: false
	 *
	 * \created 24.08.2012 20:44:05 by KB
	 */
	@JSOptionDefinition(name = "AlternateOptionsUsed", description = "Alternate Options used for connection and/or authentication", key = "AlternateOptionsUsed", type = "SOSOptionBoolean", mandatory = false)
	public SOSOptionBoolean	AlternateOptionsUsed	= new SOSOptionBoolean( // ...
															this, // ....
															conClassName + ".AlternateOptionsUsed", // ...
															"Alternate Options used for connection and/or authentication", // ...
															"false", // ...
															"false", // ...
															false);

	public String getAlternateOptionsUsed() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getAlternateOptionsUsed";

		return AlternateOptionsUsed.Value();
	} // public String getAlternateOptionsUsed

	public SOSConnection2OptionsAlternate setAlternateOptionsUsed(final String pstrValue) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setAlternateOptionsUsed";
		AlternateOptionsUsed.Value(pstrValue);
		return this;
	} // public SOSConnection2OptionsAlternate setAlternateOptionsUsed

	private SOSConnection2OptionsAlternate	objAlternativeOptions	= null;
	private SOSConnection2OptionsAlternate	objProxyOptions			= null;
	private SOSConnection2OptionsAlternate	objJumpServerOptions	= null;

	/**
	* constructors
	*/
	public SOSConnection2OptionsAlternate() {
	} // public SOSConnection2OptionsAlternate

	public SOSConnection2OptionsAlternate(final String pstrPrefix) {
		strAlternativePrefix = pstrPrefix;
	} // public SOSConnection2OptionsAlternate

	public SOSConnection2OptionsAlternate(final JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public SOSConnection2OptionsAlternate

	public SOSConnection2OptionsAlternate(final HashMap<String, String> pobjJSSettings) throws Exception {
		super(pobjJSSettings);
		objAlternativeOptions.setAllOptions(pobjJSSettings, "alternative_" + strAlternativePrefix);
		this.addProcessedOptions(objAlternativeOptions.getProcessedOptions());
	} // public SOSConnection2OptionsAlternate (HashMap JSSettings)

	public SOSConnection2OptionsAlternate(final HashMap<String, String> pobjJSSettings, final String pstrPrefix) throws Exception {
		//		super(pobjJSSettings);   // wrong, because every options which has not the prefix will be set as well.
		strAlternativePrefix = pstrPrefix;
		setAllOptions(pobjJSSettings, strAlternativePrefix);
		objAlternativeOptions = new SOSConnection2OptionsAlternate();
		objProxyOptions = new SOSConnection2OptionsAlternate();
		objJumpServerOptions = new SOSConnection2OptionsAlternate();
		objAlternativeOptions.setAllOptions(pobjJSSettings, "alternative_" + strAlternativePrefix);
		objProxyOptions.setAllOptions(pobjJSSettings, "proxy_" + strAlternativePrefix);
		objJumpServerOptions.setAllOptions(pobjJSSettings, "jump_" + strAlternativePrefix);
		this.addProcessedOptions(objAlternativeOptions.getProcessedOptions());
	} // public SOSConnection2OptionsAlternate (HashMap JSSettings)

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
	// SOSConnection2OptionsAlternateSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()

	/**
	 * \brief AlternativeOptions
	 *
	 * \details
	 * getter
	 *
	 * @return the objAlternativeOptions
	 */
	public SOSConnection2OptionsAlternate Alternatives() {
		if (objAlternativeOptions == null) {
			objAlternativeOptions = new SOSConnection2OptionsAlternate("");
		}
		return objAlternativeOptions;
	}

	/**
	 * \brief AlternativeOptions -
	 *
	 * \details
	 * setter
	 *
	 * @param objAlternativeOptions the value for objAlternativeOptions to set
	 */
	public void AlternativeOptions(final SOSConnection2OptionsAlternate pobjAlternativeOptions) {
		objAlternativeOptions = pobjAlternativeOptions;
	}
}
