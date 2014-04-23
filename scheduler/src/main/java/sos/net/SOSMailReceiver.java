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
package sos.net;

import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import sos.util.NullBufferedWriter;
import sos.util.SOSLogger;
import sos.util.SOSStandardLogger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * Mail Receiver (POP3, IMAP)
 *
 * @version $Id: SOSMailReceiver.java 18712 2013-01-02 21:27:15Z kb $
 */

public class SOSMailReceiver {

	private final String				conSVNVersion		= "$Id: SOSMailReceiver.java 18712 2013-01-02 21:27:15Z kb $";

	/** */
	private Store					store;

	public int						READ_ONLY				= Folder.READ_ONLY;
	public int						READ_WRITE				= Folder.READ_WRITE;
	private String					folderName				= "INBOX";
	private Folder					folder					= null;
	private String					protocol				= "POP3";
	//Vector<SOSMailAttachment> sosMailAttachmentList = new Vector<SOSMailAttachment>();
//	Vector<?>							sosMailAttachmentList	= new Vector();
	SOSLogger						logger					= null;

	protected SOSMailAuthenticator	authenticator			= null;
	private final String			host;
	private final String			port;
	private final String			user;
	private Session					session;
	private final String			password;
	protected int					timeout					= 5000;

	/**
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @throws Exception
	 */
	public SOSMailReceiver(final String host, final String port, final String user, final String password) throws Exception {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		createSession();
		setStubLogger();
		logger.debug(conSVNVersion);
	}

	/**
	 *
	 * @throws Exception
	 */
	private void setStubLogger() throws Exception {
		if (logger == null)
			logger = new SOSStandardLogger(new NullBufferedWriter(new OutputStreamWriter(System.out)), SOSStandardLogger.DEBUG1);
		return;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public Session createSession() throws Exception {
		Properties props = System.getProperties();
		props.put("mail.host", host);
		props.put("mail.port", port);
		if (!user.equals("") && !password.equals("")) {
			authenticator = new SOSMailAuthenticator(user, password);
			session = Session.getInstance(props, authenticator);
		}
		else {
			authenticator = new SOSMailAuthenticator(user, password);
			session = Session.getInstance(props, authenticator);
		}
		return session;
	} // createSession

	/**
	 *
	 *
	 * @param pstrMailProtocolName
	 *            the protocol to be used: "imap" for IMAP and pop3 for POP3
	 *
	 * @throws MessagingException
	 */
	public void connect(final String pstrMailProtocolName) throws Exception {
		// set timeout
		String strMailProtocolName = pstrMailProtocolName.toLowerCase();
		session.getProperties().put("mail." + strMailProtocolName + ".timeout", String.valueOf(timeout));
		store = session.getStore(strMailProtocolName);
		store.connect(host, user, password);
		logger.debug5("..connected to host [" + host + ":" + port + "] successfully done.");
	} // connect

	/**
	 *
	 * @param folderName1
	 * @param mode
	 *            The open mode of this folder. The open mode is
	 *            Folder.READ_ONLY, Folder.READ_WRITE, or -1 if not known.
	 * @return folder
	 * @throws Exception
	 */
	public Folder openFolder(final String folderName1, final int mode) throws Exception {

		folder = store.getFolder(folderName1);

		if (folder == null)
			throw new JobSchedulerException("An error occured opening [" + folderName1 + "]");

		folder.open(mode);
		folderName = folderName1;
		return folder;
	}// openFolder

	/**
	 * opens the default folder
	 *
	 * @param mode
	 *            The open mode of this folder. The open mode is
	 *            Folder.READ_ONLY, Folder.READ_WRITE, or -1 if not known.
	 * @return folder
	 * @throws Exception
	 */
	public Folder openFolder(final int mode) throws Exception {

		folder = store.getDefaultFolder();

		if (folder == null) {
			throw new JobSchedulerException("An error occured opening default folder");
		}

//		folder = folder.getFolder(folderName);
		folderName = folder.getName();
		folder.open(mode);
		return folder;
	}// openFolder

	/**
	 * @param expunge
	 *            expunges all deleted messages if this flag is true.
	 * @throws MessagingException
	 */
	public void closeFolder(final boolean expunge) throws MessagingException {
		if (folder != null && folder.isOpen()) {
			folder.close(expunge);
			folder = null;
		}
	}// closeFolder

	/**
	 *
	 * @throws MessagingException
	 */
	public void disconnect() throws MessagingException {
		if (store != null) {
			store.close();
			store = null;
		} // if
	}// disconnect

	/**
	 * @return Returns the protocol.
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            The protocol to set.
	 */
	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return Returns the folderName.
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName
	 *            The folderName to set.
	 */
	public void setFolderName(final String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return Returns the logger.
	 */
	public SOSLogger getLogger() {
		return logger;
	}

	/**
	 * @param logger The logger to set.
	 */
	public void setLogger(final SOSLogger logger) {
		this.logger = logger;
	}

	/**
	 * @return Returns the session.
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session The session to set.
	 */
	public void setSession(final Session session) {
		this.session = session;
	}

	/**
	 * @return Returns the timeout.
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout The timeout to set.
	 */
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}
}
