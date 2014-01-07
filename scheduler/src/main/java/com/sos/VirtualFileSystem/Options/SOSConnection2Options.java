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
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;
import com.sos.VirtualFileSystem.common.SOSVfsMessageCodes;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		SOSConnection2Options - Options for a connection to an uri (server, site, e.g.)
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
@JSOptionClass(name = "SOSConnection2Options", description = "Options for a connection to an uri (server, site, e.g.)")
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSConnection2Options extends SOSConnection2OptionsSuperClass {
	/**
	 *
	 */
	private static final long				serialVersionUID		= 6485361196241983182L;
	@SuppressWarnings("unused")
	private final String					conClassName			= "SOSConnection2Options";							//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger					logger					= Logger.getLogger(SOSConnection2Options.class);

	private final String					strAlternativePrefix	= "";

	private SOSConnection2OptionsAlternate	objAlternativeOptions	= null;
	private SOSConnection2OptionsAlternate	objSourceOptions		= null;
	private SOSConnection2OptionsAlternate	objTargetOptions		= null;
	private SOSConnection2OptionsAlternate	objJumpServerOptions	= null;
	private SOSConnection2OptionsAlternate	objProxyServerOptions	= null;

	/**
	* constructors
	*/

	public SOSConnection2Options() {
	} // public SOSConnection2Options

	public SOSConnection2Options(final String strPrefix) {
	} // public SOSConnection2Options

	@Deprecated
	public SOSConnection2Options(final JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public SOSConnection2Options

	private void initChildOptions() {

		objAlternativeOptions = new SOSConnection2OptionsAlternate("");
		objSourceOptions = new SOSConnection2OptionsAlternate("");
		objTargetOptions = new SOSConnection2OptionsAlternate("");
		objJumpServerOptions = new SOSConnection2OptionsAlternate("");
		objProxyServerOptions = new SOSConnection2OptionsAlternate("");

	}

	public SOSConnection2Options(final HashMap<String, String> pobjJSSettings) throws Exception {
		super(pobjJSSettings);
		initChildOptions();
		logger.trace(SOSVfsMessageCodes.SOSVfs_T_267.params("alternative_"));
		objAlternativeOptions.setAllOptions(pobjJSSettings, strAlternativePrefix + "alternative_");
		this.addProcessedOptions(objAlternativeOptions.getProcessedOptions());
		objAlternativeOptions.setAllOptions(pobjJSSettings);
		this.addProcessedOptions(objAlternativeOptions.getProcessedOptions());

		logger.trace(SOSVfsMessageCodes.SOSVfs_T_267.params("source_"));
		objSourceOptions = new SOSConnection2OptionsAlternate(pobjJSSettings, "source_");
		this.addProcessedOptions(objSourceOptions.getProcessedOptions());
		logger.debug(SOSVfsMessageCodes.SOSVfs_T_268.params(objSourceOptions.dirtyString()));

		logger.trace(SOSVfsMessageCodes.SOSVfs_T_267.params("target_"));
		objTargetOptions = new SOSConnection2OptionsAlternate(pobjJSSettings, "target_");
		this.addProcessedOptions(objTargetOptions.getProcessedOptions());
		logger.debug(SOSVfsMessageCodes.SOSVfs_T_269.params(objTargetOptions.dirtyString()));

		//		logger.trace(String.format("set parameter for prefix '%1$s'", "jump_"));
		objJumpServerOptions = new SOSConnection2OptionsAlternate(pobjJSSettings, "jump_");
		this.addProcessedOptions(objJumpServerOptions.getProcessedOptions());

	} // public SOSConnection2Options (HashMap JSSettings)

	/**
	 * \brief CheckMandatory - prüft alle Muss-Optionen auf Werte
	 *
	 * \details
	 * @throws Exception
	 *
	 * @throws Exception
	 * - wird ausgelöst, wenn eine mandatory-Option keinen Wert hat
	 */
	@Override
	// SOSConnection2OptionsSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()

	/**
	 * \brief getobjAlternativeOptions
	 *
	 * \details
	 * getter
	 *
	 * @return the objAlternativeOptions
	 */
	public SOSConnection2OptionsSuperClass Alternatives() {
		return objAlternativeOptions;
	}

	/**
	 * \brief setobjAlternativeOptions -
	 *
	 * \details
	 * setter
	 *
	 * @param objAlternativeOptions the value for objAlternativeOptions to set
	 */
	public void Alternatives(final SOSConnection2OptionsAlternate pobjAlternativeOptions) {
		if (objAlternativeOptions == null) {
			objAlternativeOptions = new SOSConnection2OptionsAlternate("alternative_");
		}
		objAlternativeOptions = pobjAlternativeOptions;
	}

	/**
	 * \brief Source
	 *
	 * \details
	 * getter
	 *
	 * @return the Source
	 */
	public SOSConnection2OptionsAlternate Source() {
		if (objSourceOptions == null) {
			objSourceOptions = new SOSConnection2OptionsAlternate("source_");
		}
		return objSourceOptions;
	}

	/**
	 * \brief setobjSourceOptions -
	 *
	 * \details
	 * setter
	 *
	 * @param objSourceOptions the value for objSourceOptions to set
	 */
	public void Source(final SOSConnection2OptionsAlternate pobjSourceOptions) {
		objSourceOptions = pobjSourceOptions;
	}

	/**
	 * \brief getobjJumpServerOptions
	 *
	 * \details
	 * getter
	 *
	 * @return the objJumpServerOptions
	 */
	public SOSConnection2OptionsAlternate JumpServer() {
		if (objJumpServerOptions == null) {
			objJumpServerOptions = new SOSConnection2OptionsAlternate("");
		}
		return objJumpServerOptions;
	}

	/**
	 * \brief setobjJumpServerOptions -
	 *
	 * \details
	 * setter
	 *
	 * @param objJumpServerOptions the value for objJumpServerOptions to set
	 */
	public void JumpServer(final SOSConnection2OptionsAlternate pobjJumpServerOptions) {
		objJumpServerOptions = pobjJumpServerOptions;
	}

	/**
	 * \brief getobjTargetOptions
	 *
	 * \details
	 * getter
	 *
	 * @return the objTargetOptions
	 */
	public SOSConnection2OptionsAlternate Target() {
		if (objTargetOptions == null) {
			objTargetOptions = new SOSConnection2OptionsAlternate("");
		}
		return objTargetOptions;
	}

	/**
	 * \brief setobjTargetOptions -
	 *
	 * \details
	 * setter
	 *
	 * @param objTargetOptions the value for objTargetOptions to set
	 */
	public void Target(final SOSConnection2OptionsAlternate pobjTargetOptions) {
		objTargetOptions = pobjTargetOptions;
	}
}
