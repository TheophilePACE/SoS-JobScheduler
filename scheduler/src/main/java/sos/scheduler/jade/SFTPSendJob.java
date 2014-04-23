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

import com.sos.JSHelper.Basics.JSVersionInfo;
import com.sos.JSHelper.Options.SOSOptionAuthenticationMethod.enuAuthenticationMethods;
import com.sos.JSHelper.Options.SOSOptionJadeOperation;
import com.sos.JSHelper.Options.SOSOptionPortNumber;
import com.sos.JSHelper.Options.SOSOptionTransferType.enuTransferTypes;
import com.sos.VirtualFileSystem.Options.SOSConnection2Options;
import com.sos.i18n.annotation.I18NResourceBundle;

/** 
 * \file SFTPSendJob.java
 * \class 		SFTPSendJob
 *
 * \brief 
 * AdapterClass of SOSDEx for the SOSJobScheduler
 *
 * This Class SFTPSendJob works as an adapter-class between the SOS
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
public class SFTPSendJob extends Jade4JessyBaseClass {
	private final String	conClassName	= "SFTPSendJob";
	private final String	conSVNVersion	= "$Id: SFTPSendJob.java 17648 2012-07-23 12:34:16Z kb $";

	protected void setSpecialOptions() {
		objO.operation.Value(SOSOptionJadeOperation.enuJadeOperations.send);

		objO.protocol.Value(enuTransferTypes.sftp);
		objO.port.value(SOSOptionPortNumber.getStandardSFTPPort());
//		objO.ssh_auth_method.Value(enuAuthenticationMethods.publicKey);

		SOSConnection2Options objConn = objO.getConnectionOptions();
		if (objConn != null) {
			objConn.Target().protocol.Value(enuTransferTypes.sftp);
//			objConn.Target().port.value(SOSOptionPortNumber.getStandardSFTPPort());
//			objConn.Target().ssh_auth_method.Value(enuAuthenticationMethods.publicKey);
		}
	}

	protected void showVersionInfo() {
		logger.debug(JSVersionInfo.getVersionString());
		logger.debug(conSVNVersion);
	}
}
