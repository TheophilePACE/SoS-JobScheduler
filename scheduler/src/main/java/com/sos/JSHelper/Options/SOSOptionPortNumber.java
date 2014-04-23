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
package com.sos.JSHelper.Options;
import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
* \class SOSOptionPortNumber
*
* \brief SOSOptionPortNumber -
*
* \details
*
Keyword         Decimal    Description                     References
-------         -------    -----------                     ----------
echo              7/tcp    Echo
echo              7/udp    Echo
ftp-data         20/tcp    File Transfer [Default Data]
ftp-data         20/udp    File Transfer [Default Data]
ftp              21/tcp    File Transfer [Control]
ftp              21/udp    File Transfer [Control]
ssh              22/tcp    SSH Remote Login Protocol
ssh              22/udp    SSH Remote Login Protocol
telnet           23/tcp    Telnet
telnet           23/udp    Telnet
smtp             25/tcp    Simple Mail Transfer
smtp             25/udp    Simple Mail Transfer
name             42/tcp    Host Name Server
name             42/udp    Host Name Server
nameserver       42/tcp    Host Name Server
nameserver       42/udp    Host Name Server
nicname          43/tcp    Who Is
nicname          43/udp    Who Is
domain           53/tcp    Domain Name Server
domain           53/udp    Domain Name Server
http             80/tcp    World Wide Web HTTP
http             80/udp    World Wide Web HTTP
www              80/tcp    World Wide Web HTTP
www              80/udp    World Wide Web HTTP
www-http         80/tcp    World Wide Web HTTP
www-http         80/udp    World Wide Web HTTP
npp              92/tcp    Network Printing Protocol
npp              92/udp    Network Printing Protocol
hostname        101/tcp    NIC Host Name Server
hostname        101/udp    NIC Host Name Server
pop3            110/tcp    Post Office Protocol - Version 3
pop3            110/udp    Post Office Protocol - Version 3
ident           113/tcp
auth            113/tcp    Authentication Service
auth            113/udp    Authentication Service
sftp            115/tcp    Simple File Transfer Protocol
sftp            115/udp    Simple File Transfer Protocol
ntp             123/tcp    Network Time Protocol
ntp             123/udp    Network Time Protocol
smb             137/udp    NetBIOS Name Service (Windows)
smb             138/upd    NetBIOS
smb             139/tcp    NetBIOS File and Printer Sharing (Windows)
imap            143/tcp    Internet Message Access Protocol
imap            143/udp    Internet Message Access Protocol
vmnet           175/tcp    VMNET
vmnet           175/udp    VMNET
ldap            389/tcp    Lightweight Directory Access Protocol
ldap            389/udp    Lightweight Directory Access Protocol
https           443/tcp    http protocol over TLS/SSL
https           443/udp    http protocol over TLS/SSL
smb             445/tcp    NetBIOS File and Printer Sharing (Windows)
syslog          514/udp
printer         515/tcp    spooler
printer         515/udp    spooler
whoami          565/tcp    whoami
whoami          565/udp    whoami
imap4-ssl       585/tcp    IMAP4+SSL (use 993 instead)
imap4-ssl       585/udp    IMAP4+SSL (use 993 instead)
ipp             631/tcp    IPP (Internet Printing Protocol)
ipp             631/udp    IPP (Internet Printing Protocol)
rsync           873/tcp    rsync
rsync           873/udp    rsync
ftps-data       989/tcp    ftp protocol, data, over TLS/SSL
ftps-data       989/udp    ftp protocol, data, over TLS/SSL
ftps            990/tcp    ftp protocol, control, over TLS/SSL
ftps            990/udp    ftp protocol, control, over TLS/SSL
telnets         992/tcp    telnet protocol over TLS/SSL
telnets         992/udp    telnet protocol over TLS/SSL
imaps           993/tcp    imap4 protocol over TLS/SSL
imaps           993/udp    imap4 protocol over TLS/SSL
pop3s           995/tcp    pop3 protocol over TLS/SSL (was spop3)
pop3s           995/udp    pop3 protocol over TLS/SSL (was spop3)

*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* @version $Id: SOSOptionPortNumber.java 20144 2013-05-16 13:04:30Z oh $16.05.2010
* \see reference
*
* Created on 16.05.2010 20:09:23
 */
/**
 * @author KB
 *
 */
public class SOSOptionPortNumber extends SOSOptionInteger {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 8291980761608522995L;

	@SuppressWarnings("unused")
	private final String		conClassName		= "SOSOptionPortNumber";

	public static final int		conPort4FTP			= 21;						// ftp
	public static final int		conPort4SSH			= 22;						// ssh / sftp
	public static final int		conPort4SFTP		= 22;						// ssh / sftp
	public static final int		conPort4Telnet		= 23;						// telnet
	public static final int		conPort4smtp		= 25;						// smtp
	public static final int		conPort4http		= 80;						// http
	public static final int		conPort4pop3		= 110;						// pop3
	public static final int		conPort4imap		= 143;						// imap
	public static final int		conPortWebDav		= 443;						// WebDAV
	public static final int		conPort4FTPS		= 990;						// ftp

	// SOSOptionPortNumber() {
	// //
	// }
	/**
	 * \brief SOSOptionPortNumber
	 *
	 * \details
	 *
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 */
	public SOSOptionPortNumber(final JSOptionsClass pPobjParent, final String pPstrKey, final String pPstrDescription, final String pPstrValue,
			final String pPstrDefaultValue, final boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
	}

	public static int getStandardSFTPPort() {
		return conPort4SFTP;
	}

	public static int getStandardFTPPort() {
		return conPort4FTP;
	}

	@Override
	public void Value(final String pstrPortNo) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Value";
		String strP = pstrPortNo;
		String pstrPortNumber = pstrPortNo;
		if (pstrPortNumber == null) {
			strP = "0";
		}

		try {
			if (isNotEmpty(strP)) {
				strP = StripQuotes(strP);
				int portNum = Integer.parseInt(strP);
				if (portNum >= 0 && portNum < 65535) {
					super.Value(strP);
				}
				else {
					throw new JobSchedulerException(String.format("invalid port number: %1$s", strP));
				}
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException(String.format("invalid port number: %1$s\n%2$s", strP, e.getLocalizedMessage()));
		}
	} // private void Value
}
