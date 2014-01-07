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
package sos.scheduler.file;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Annotations.JSOptionClass;
import com.sos.JSHelper.Exceptions.JSExceptionMandatoryOptionMissing;
import com.sos.JSHelper.Listener.JSListener;

/**
 * \class 		JSExistsFileOptions - check wether a file exist
 *
 * \brief 
 * An Options as a container for the Options super class. 
 * The Option class will hold all the things, which would be otherwise overwritten at a re-creation
 * of the super-class.
 *
 *

 *
 * see \see C:\Users\KB\Documents\xmltest\JSExistFile.xml for (more) details.
 * 
 * \verbatim ;
 * mechanicaly created by JobDocu2OptionsClass.xslt from http://www.sos-berlin.com at 20110820120923 
 * \endverbatim
 */
@JSOptionClass(name = "JSExistsFileOptions", description = "check wether a file exist")
public class JSExistsFileOptions extends JSExistsFileOptionsSuperClass {
	private static final long	serialVersionUID	= 1731085195875420660L;
	@SuppressWarnings("unused")//$NON-NLS-1$
	private final String		conClassName		= "JSExistsFileOptions";						//$NON-NLS-1$
	@SuppressWarnings("unused")
	private static Logger		logger				= Logger.getLogger(JSExistsFileOptions.class);

	/**
	* constructors
	*/

	public JSExistsFileOptions() {
	} // public JSExistsFileOptions

	public JSExistsFileOptions(JSListener pobjListener) {
		this();
		this.registerMessageListener(pobjListener);
	} // public JSExistsFileOptions

	//

	public JSExistsFileOptions(HashMap<String, String> JSSettings) throws Exception {
		super(JSSettings);
	} // public JSExistsFileOptions (HashMap JSSettings)

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
	// JSExistFileOptionsSuperClass
	public void CheckMandatory() {
		try {
			super.CheckMandatory();
		}
		catch (Exception e) {
			throw new JSExceptionMandatoryOptionMissing(e.toString());
		}
	} // public void CheckMandatory ()
}
