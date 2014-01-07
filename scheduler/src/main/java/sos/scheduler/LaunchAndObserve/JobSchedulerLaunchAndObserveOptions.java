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

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;

/**
 * \class 		JobSchedulerLaunchAndObserveOptions - Launch and observe any given job or job chain
 *
 * \brief 
 * An Options as a container for the Options super class. 
 * The Option class will hold all the things, which would be otherwise overwritten at a re-creation
 * of the super-class.
 *
 *

 *
 * see \see J:\E\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerLaunchAndObserve.xml for (more) details.
 * 
 * \verbatim ;
 * mechanicaly created by JobDocu2OptionsClass.xslt from http://www.sos-berlin.com at 20111124184709 
 * \endverbatim
 */
@JSOptionClass(name = "JobSchedulerLaunchAndObserveOptions", description = "Launch and observe any given job or job chain")
public class JobSchedulerLaunchAndObserveOptions extends JobSchedulerLaunchAndObserveOptionsSuperClass {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2227906861871375733L;
	@SuppressWarnings("unused")//$NON-NLS-1$
	private final String		conClassName		= "JobSchedulerLaunchAndObserveOptions";						//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger		logger				= Logger.getLogger(JobSchedulerLaunchAndObserveOptions.class);

	private SOSSmtpMailOptions	objMailOnRestartOptions	= null;
	private SOSSmtpMailOptions	objMailOnKillOptions	= null;


	/**
	* constructors
	*/

	public JobSchedulerLaunchAndObserveOptions() {
	} // public JobSchedulerLaunchAndObserveOptions

	public JobSchedulerLaunchAndObserveOptions(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public JobSchedulerLaunchAndObserveOptions

	//

	public JobSchedulerLaunchAndObserveOptions(HashMap<String, String> JSSettings) throws Exception {
		super(JSSettings);
		setChildClasses(JSSettings);
	} // public JobSchedulerLaunchAndObserveOptions (HashMap JSSettings)

	@Override
	public void setChildClasses(HashMap<String, String> pobjJSSettings) throws Exception {
//		objMailOnRestartOptions = new SOSSmtpMailOptions(pobjJSSettings);
//		objMailOnKillOptions = new SOSSmtpMailOptions(pobjJSSettings);
//
//		logger.debug(String.format("set par ameter for prefix '%1$s'", "MailOnRestart_"));
//		objMailOnRestartOptions.setAllOptions(pobjJSSettings, "MailOnRestart_");
//		logger.debug(String.format("set parameter for prefix '%1$s'", "MailOnKill_"));
//		objMailOnKillOptions.setAllOptions(pobjJSSettings, "MailOnKill_");
	}

	public ISOSSmtpMailOptions getMailOnRestartOptions() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getMailOnRestartOptions";

		if (objMailOnRestartOptions == null) {
			objMailOnRestartOptions = new SOSSmtpMailOptions();
		}

		return objMailOnRestartOptions;
	} // private ISOSSmtpMailOptions getMailOnRestartOptions

	public ISOSSmtpMailOptions getMailOnKillOptions() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getMailOnKillOptions";
		
		if (objMailOnKillOptions == null) {
			objMailOnKillOptions = new SOSSmtpMailOptions();
		}
		return objMailOnKillOptions;
	} // private ISOSSmtpMailOptions getMailOnRestartOptions

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
	// JobSchedulerLaunchAndObserveOptionsSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()
}
