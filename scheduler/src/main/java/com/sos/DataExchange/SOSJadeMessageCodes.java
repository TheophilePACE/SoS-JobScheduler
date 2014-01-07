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
package com.sos.DataExchange;

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "SOSDataExchange", defaultLocale = "en")
public class SOSJadeMessageCodes extends JSToolBox {
	public final String			conSVNVersion					= "$Id: SOSDataExchangeEngine.java 18719 2013-01-02 21:34:59Z kb $";

	public static final SOSMsgJade SOSJADE_I_0104 = new SOSMsgJade("SOSJADE_I_0104");	// "zero byte file(s) found";
	public static final SOSMsgJade SOSJADE_I_0100 = new SOSMsgJade("SOSJADE_I_0100");	// "one file transferred";
	public static final SOSMsgJade SOSJADE_I_0101 = new SOSMsgJade("SOSJADE_I_0101");	// "%1$d files transferred";
	public static final SOSMsgJade SOSJADE_I_0102 = new SOSMsgJade("SOSJADE_I_0102");	// "%1$d file(s) skipped due to zero byte constraint";
	public static final SOSMsgJade SOSJADE_I_0115 = new SOSMsgJade("SOSJADE_I_0115");	// "Operation 'getList' is specified. no transfer will be done.";
	public static final SOSMsgJade SOSJADE_E_0101 = new SOSMsgJade("SOSJADE_E_0101");	// "data transfer ended with error '%1$s'";
	public static final SOSMsgJade SOSJADE_E_0200 = new SOSMsgJade("SOSJADE_E_0200");	// "Problems creating/connecting DataSourceClient";
	public static final SOSMsgJade SOSJADE_D_0200 = new SOSMsgJade("SOSJADE_D_0200");	// "source directory/file: '%1$s' file regexp: '%2$s'";
	
	
	public static final SOSMsgJade SOSJADE_T_0010 = new SOSMsgJade("SOSJADE_T_0010");	// 
	public static final SOSMsgJade SOSJADE_T_0011 = new SOSMsgJade("SOSJADE_T_0011");	// 
	public static final SOSMsgJade SOSJADE_T_0012 = new SOSMsgJade("SOSJADE_T_0012");	// 
	public static final SOSMsgJade SOSJADE_T_0013 = new SOSMsgJade("SOSJADE_T_0013");	// 
	
	public static final SOSMsgJade 	SOSJADE_E_0100					= new SOSMsgJade("SOSJADE_E_0100");													// "No file name found which match the regular expression criteria '1$s'";

	/*!
	 * \var TRANSACTION_ABORTED
	 * \brief could not complete transaction, cause: %1$s
	 */
	public static final SOSMsgJade	TRANSACTION_ABORTED				= new SOSMsgJade("SOSDataExchangeEngine.TRANSACTION_ABORTED");
	/*!
	 * \var EXCEPTION_RAISED
	 * \brief %1$s: Exception raised, cause: %2$s
	 */

	public static final SOSMsgJade		EXCEPTION_RAISED				=  new SOSMsgJade("EXCEPTION_RAISED");

	protected SOSJadeMessageCodes() {
		super();
	}
}
