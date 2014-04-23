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
package sos.net.ssh;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "SOSDataExchange", defaultLocale = "en")
public class SOSNetMessages extends JSToolBox {
	public final String				conSVNVersion		= "$Id: SOSDataExchangeEngine.java 18719 2013-01-02 21:34:59Z kb $";
	public static final SOSMsgNet	SOS_SSH_E_0100		= new SOSMsgNet("SOS_SSH_E_0100");										// Es wurde weder ein Kommando noch eine Kommandodatei angegeben. Abbruch.
	public static final SOSMsgNet	SOS_SSH_D_0110		= new SOSMsgNet("SOS_SSH_D_0110");										// starte am remote-server das Kommando: '%1$s'.

	/*!
	 * \var TRANSACTION_ABORTED
	 * \brief could not complete transaction, cause: %1$s
	 */
	public static final SOSMsgNet	TRANSACTION_ABORTED	= new SOSMsgNet("SOSDataExchangeEngine.TRANSACTION_ABORTED");
	/*!
	 * \var EXCEPTION_RAISED
	 * \brief %1$s: Exception raised, cause: %2$s
	 */

	public static final SOSMsgNet	EXCEPTION_RAISED	= new SOSMsgNet("EXCEPTION_RAISED");

	protected SOSNetMessages() {
		super();
	}
}
