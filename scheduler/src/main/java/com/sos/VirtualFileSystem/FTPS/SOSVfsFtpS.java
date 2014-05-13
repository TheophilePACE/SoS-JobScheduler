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
package com.sos.VirtualFileSystem.FTPS;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.FTP.SOSFtpClientLogger;
import com.sos.VirtualFileSystem.FTP.SOSVfsFtpBaseClass;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsFtpS extends SOSVfsFtpBaseClass  {
	@SuppressWarnings("unused")
	private final String conClassName = this.getClass().getSimpleName();
	@SuppressWarnings("unused")
	private static final String conSVNVersion = "$Id: SOSVfsFtpS.java 20723 2013-07-18 18:19:56Z kb $";
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	private FTPSClient						objFTPClient			= null;

	/**
	 *
	 * \brief SOSVfsFtpS
	 *
	 * \details
	 *
	 */
	public SOSVfsFtpS() {
	}

	public FTPSClient getClient() {
		return Client();
	}

	@Override
	protected FTPSClient Client() {
		if (objFTPClient == null) {
			try {
				String strProtocol = objConnection2Options.FtpS_protocol.Value();
				objFTPClient = new FTPSClient(strProtocol);
			}
			catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JobSchedulerException("can not create FTPS-Client");
			}
			FTPClientConfig conf = new FTPClientConfig();
//			conf.setServerLanguageCode("fr");
//			objFTPClient.configure(conf);
			/**
			 * This listener is to write all commands and response from commands to system.out
			 *
			 */
			objProtocolCommandListener = new SOSFtpClientLogger(HostID(""));
			// TODO create a hidden debug-option to activate this listener
			if (objConnection2Options != null) {
				if (objConnection2Options.ProtocolCommandListener.isTrue()) {
					objFTPClient.addProtocolCommandListener(objProtocolCommandListener);
				}
			}

			String strAddFTPProtocol = System.getenv("AddFTPProtocol");
			if (strAddFTPProtocol != null && strAddFTPProtocol.equalsIgnoreCase("true")) {
				objFTPClient.addProtocolCommandListener(objProtocolCommandListener);
			}

		}
		return objFTPClient;
	}

	@Override
	public void connect(final String phost, final int pport) {
		try {
			if (isConnected() == false) {
				super.connect(phost, pport);

				/**
				 * PBSZ (protection buffer size) command, as detailed in [RFC-2228],
				 * is compulsory prior to any PROT command.
				 */
				Client().execPBSZ(0);
				LogReply();
				Client().execPROT("P");  // Secure Data channel, see http://www.faqs.org/rfcs/rfc2228.html
				LogReply();
				Client().enterLocalPassiveMode();
				LogReply();

			}
			else {
				logger.warn(SOSVfs_D_0102.params(host, port));
			}
		}
		catch (Exception e) {
			String strM = HostID("connect returns an exception");
			e.printStackTrace(System.err);
			logger.error(strM, e);
			// throw new RuntimeException(strM, e);
		}
	}

}
