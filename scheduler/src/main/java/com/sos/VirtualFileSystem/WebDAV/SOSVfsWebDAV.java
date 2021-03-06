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
package com.sos.VirtualFileSystem.WebDAV;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;
import org.apache.webdav.lib.WebdavResource;

import sos.util.SOSString;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.Interfaces.ISOSAuthenticationOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnection;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsAlternate;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsSuperClass;
import com.sos.VirtualFileSystem.common.SOSVfsTransferBaseClass;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * @ressources webdavclient4j-core-0.92.jar
 *
 * @author Robert Ehrlich
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsWebDAV extends SOSVfsTransferBaseClass {

	private final String	conClassName		= "SOSVfsWebDAV";

	private final Logger	logger		= Logger.getLogger(SOSVfsWebDAV.class);

	private HttpURL			rootUrl		= null;
	private WebdavResource	davClient	= null;

	private String password = null;
	private String proxyHost = null;
	private int proxyPort = 0;
	private String proxyUser = null;
	private String proxyPassword = null;


	/**
	 *
	 * \brief SOSVfsWebDAV
	 *
	 * \details
	 *
	 */
	public SOSVfsWebDAV() {
		super();
	}

	/**
	 *
	 * \brief Connect
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public ISOSConnection Connect() {
		@SuppressWarnings("unused")
		SOSConnection2OptionsAlternate pConnection2OptionsAlternate = null;
		this.Connect(pConnection2OptionsAlternate);
		return this;

	}

	/**
	 *
	 * \brief Connect
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjConnectionOptions
	 * @return
	 */
	@Override
	public ISOSConnection Connect(final SOSConnection2OptionsAlternate pConnection2OptionsAlternate) {
		connection2OptionsAlternate = pConnection2OptionsAlternate;

		if (connection2OptionsAlternate == null) {
			RaiseException(SOSVfs_E_190.params("connection2OptionsAlternate"));
		}

		this.connect(connection2OptionsAlternate.host.Value(), connection2OptionsAlternate.port.value());
		return this;
	}

	/**
	 *
	 * \brief Authenticate
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pAuthenticationOptions
	 * @return
	 */
	@Override
	public ISOSConnection Authenticate(final ISOSAuthenticationOptions pAuthenticationOptions) {
		authenticationOptions = pAuthenticationOptions;
		try {
			proxyHost = connection2OptionsAlternate.proxy_host.Value();
			proxyPort = connection2OptionsAlternate.proxy_port.value();
			proxyUser = connection2OptionsAlternate.proxy_user.Value();
			proxyPassword = connection2OptionsAlternate.proxy_password.Value();

			this.doAuthenticate(authenticationOptions);
		}
		catch (Exception ex) {
			Exception exx = ex;

			this.disconnect();

			if (connection2OptionsAlternate != null) {
				SOSConnection2OptionsSuperClass optionsAlternatives = connection2OptionsAlternate.Alternatives();
				if (!optionsAlternatives.host.IsEmpty() && !optionsAlternatives.user.IsEmpty()) {
					logINFO(SOSVfs_I_170.params(connection2OptionsAlternate.Alternatives().host.Value()));
					try {

						host = optionsAlternatives.host.Value();
						port = optionsAlternatives.port.value();

						proxyHost = optionsAlternatives.proxy_host.Value();
						proxyPort = optionsAlternatives.proxy_port.value();
						proxyUser = optionsAlternatives.proxy_user.Value();
						proxyPassword = optionsAlternatives.proxy_password.Value();

						this.doAuthenticate(optionsAlternatives);
						exx = null;
					}
					catch (Exception e) {
						exx = e;
					}
				}
			}

			if (exx != null) {
				RaiseException(exx, SOSVfs_E_168.get());
			}
		}

		return this;
	}

	/**
	 *
	 * \brief login
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pUserName
	 * @param pPassword
	 */
	@Override
	public void login(final String pUserName, final String pPassword) {

		try {

			userName = pUserName;
			password = pPassword;

			logger.debug(SOSVfs_D_132.params(userName));

			HttpURL httpUrl = this.setRootHttpURL(userName, pPassword, host, port);
			davClient = getWebdavResource(httpUrl);

			if (!davClient.exists()) {
				throw new Exception("not connected " + davClient.getStatusMessage());
			}

			reply = "OK";
			logger.debug(SOSVfs_D_133.params(userName));
			this.LogReply();
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("authentication"));
		}

	} // private boolean login

	/**
	 *
	 * \brief disconnect
	 *
	 * \details
	 *
	 * \return
	 *
	 */
	@Override
	public void disconnect() {
		reply = "disconnect OK";

		if (davClient != null) {
			try {
				davClient.close();
				davClient = null;
			}
			catch (Exception ex) {
				reply = "disconnect: " + ex;
			}
		}

		this.logINFO(reply);
	}

	/**
	 *
	 * \brief isConnected
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public boolean isConnected() {
		return davClient != null;
	}

	/**
	 * Creates a new subdirectory on the FTP server in the current directory .
	 * @param path The pathname of the directory to create.
	 * @exception JobSchedulerException
	 */
	@Override
	public void mkdir(final String path) {
		try {
			reply = "";
			String strPath = "";
			if (path.startsWith("/")) {
				strPath = "/";
			}
//			String strPath = davClient.getPath();
			String[] pathArray = path.split("/");
			for (String strSubFolder : pathArray) {
				if (strSubFolder.trim().length() > 0) {
					strPath += strSubFolder + "/";
					strPath = this.normalizePath(strPath);

					logger.debug(HostID(SOSVfs_D_179.params("mkdir", strPath)));

					if (this.fileExists(strPath)) {
						continue;
					}

					if (davClient.mkcolMethod(strPath)) {
						reply = "mkdir OK";
						logger.debug(HostID(SOSVfs_D_181.params("mkdir", strPath, getReplyString())));
					}
					else {
						throw new Exception(davClient.getStatusMessage());
					}
				}
			}

			// DoCD(strCurrentDir);
			logINFO(HostID(SOSVfs_D_181.params("mkdir", path, getReplyString())));
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_134.params("[mkdir]"));
		}
	}

	/**
	 * Removes a directory on the FTP server (if empty).
	 * @param path The pathname of the directory to remove.
	 * @exception JobSchedulerException
	 */
	@Override
	public void rmdir(String path) {
		try {
			reply = "rmdir OK";

			path = this.normalizePath(path + "/");

			if (davClient.deleteMethod(path)) {
				reply = "rmdir OK";
				logger.debug(HostID(SOSVfs_D_181.params("rmdir", path, getReplyString())));
			}
			else {
				throw new JobSchedulerException(davClient.getStatusMessage());
			}

			logger.info(HostID(SOSVfs_D_181.params("rmdir", path, getReplyString())));
		}
		catch (Exception e) {
			reply = e.toString();
			throw new JobSchedulerException(SOSVfs_E_134.params("[rmdir]"), e);
		}
	}

	/**
	 * Checks if file is a directory
	 *
	 * @param path
	 * @return true, if filename is a directory
	 */
	@Override
	public boolean isDirectory(final String path) {
		WebdavResource res = null;
		try {

			res = this.getResource(path);
			return res.isCollection();
		}
		catch (Exception e) {
		}
		finally {
			try {
				if (res != null) {
					res.close();
				}
			}
			catch (Exception ex) {
			}
		}
		return false;
	}

	/**
	 *
	 * \brief listNames
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Override
	public String[] listNames(String path) throws IOException {
		WebdavResource res = null;
		try {
			if (path.length() == 0) {
				path = "/";
			}
			if (!this.fileExists(path)) {
				return null;
			}

			if (!this.isDirectory(path)) {
				reply = "ls OK";
				return new String[] { path };
			}

			res = this.getResource(path);

			WebdavResource[] lsResult = res.listWebdavResources();
			String[] result = new String[lsResult.length];
			for (int i = 0; i < lsResult.length; i++) {
				WebdavResource entry = lsResult[i];
				result[i] = entry.getPath();
			}
			reply = "ls OK";
			return result;
		}
		catch (Exception e) {
			reply = e.toString();
			return null;
		}
		finally {
			if (res != null) {
				try {
					res.close();
				}
				catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * return the size of remote-file on the remote machine on success, otherwise -1
	 * @param path the file on remote machine
	 * @return the size of remote-file on remote machine
	 */
	@Override
	public long size(final String path) throws Exception {
		long size = -1;
		WebdavResource res = null;
		try {
			res = this.getResource(path);
			if (res.exists()) {
				size = res.getGetContentLength();
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Exception(SOSVfs_E_161.params("checking size", e));
		}
		finally {
			if (res != null) {
				try {
					res.close();
				}
				catch (Exception ex) {
				}
			}
		}

		return size;
	}

	/**
	 *
	 * \brief getFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param remoteFile
	 * @param localFile
	 * @param append
	 * @return
	 */
	@Override
	public long getFile(String remoteFile, final String localFile, final boolean append) {
		String sourceLocation = this.resolvePathname(remoteFile);
		File transferFile = null;
		long remoteFileSize = -1;
		File file = null;
		WebdavResource res = null;
		try {
			remoteFile = this.normalizePath(remoteFile);

			//remoteFileSize = this.size(remoteFile);

			// TODO append muss noch ber�cksichtigt werden
			file = new File(localFile);

			res = this.getResource(remoteFile);
			if (!res.exists()) {
				throw new Exception("remoteFile not found");
			}

			remoteFileSize = res.getGetContentLength();

			if (res.getMethod(sourceLocation, file)) {
				transferFile = new File(localFile);

				if (!append) {
					if (remoteFileSize > 0 && remoteFileSize != transferFile.length()) {
						throw new JobSchedulerException(SOSVfs_E_162.params(remoteFileSize, transferFile.length()));
					}
				}

				remoteFileSize = transferFile.length();
				reply = "get OK";
				logINFO(HostID(SOSVfs_I_182.params("getFile", sourceLocation, localFile, getReplyString())));
			}
			else {
				throw new Exception(res.getStatusMessage());
			}
		}
		catch (Exception ex) {
			reply = ex.toString();
			RaiseException(ex, SOSVfs_E_184.params("getFile", sourceLocation, localFile));
		}
		finally {
			try {
				if (res != null) {
					res.close();
				}
			}
			catch (Exception e) {
			}
		}

		return remoteFileSize;
	}

	/**
	 * Stores a file on the server using the given name.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @return file size
	 *
	 * @exception Exception
	 * @see #put( String, String )
	 */
	@Override
	// ISOSVfsFileTransfer
	public long putFile(final String localFile, String remoteFile) {
		long size = 0;
		try {
			remoteFile = this.normalizePath(remoteFile);

			if (davClient.putMethod(remoteFile, new File(localFile))) {
				reply = "put OK";
				logINFO(HostID(SOSVfs_I_183.params("putFile", localFile, remoteFile, getReplyString())));
				return this.size(remoteFile);
			}
			else {
				throw new Exception(davClient.getStatusMessage());
			}
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_185.params("putFile()", localFile, remoteFile));
		}

		return size;
	}

	/**
	 * Deletes a file on the FTP server.
	 * @param The path of the file to be deleted.
	 * @return True if successfully completed, false if not.
	 * @throws RunTime error occurs while either sending a
	 * command to the server or receiving a reply from the server.
	 */
	@Override
	public void delete(String path) {
		try {
			path = this.normalizePath(path);

			if (this.isDirectory(path)) {
				throw new JobSchedulerException(SOSVfs_E_186.params(path));
			}

			if (!davClient.deleteMethod(path)) {
				throw new Exception(davClient.getStatusMessage());
			}
		}
		catch (Exception ex) {
			reply = ex.toString();
			RaiseException(ex, SOSVfs_E_187.params("delete", path));
		}

		reply = "rm OK";
		logINFO(HostID(SOSVfs_D_181.params("delete", path, getReplyString())));
	}

	/**
	 *
	 * \brief rename
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param from
	 * @param to
	 */
	@Override
	public void rename(String from, String to) {
		from = this.resolvePathname(from);
		to = this.resolvePathname(to);
		try {
			from = this.normalizePath(from);
			to = this.normalizePath(to);

			if (!davClient.moveMethod(from, to)) {
				throw new Exception(davClient.getStatusMessage());
			}
		}
		catch (Exception e) {
			reply = e.toString();
			RaiseException(e, SOSVfs_E_188.params("rename", from, to));
		}

		reply = "mv OK";
		logINFO(HostID(SOSVfs_I_189.params(from, to, getReplyString())));
	}

	/**
	 *
	 * \brief ExecuteCommand
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param strCmd
	 */
	@Override
	public void ExecuteCommand(final String cmd) {
		logger.debug("not implemented yet");
	}

	/**
	 *
	 * \brief getInputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 */
	@Override  // SOSVfsTransferBaseClass
	public InputStream getInputStream(String path) {
		try {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}

			return davClient.getMethodData(path);
		}
		catch (Exception ex) {
			RaiseException(ex, SOSVfs_E_193.params("getInputStream()", path));
			return null;
		}
	}

	/**
	 *
	 * \brief getOutputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 */
	@Override  // SOSTransferBaseClass
	public OutputStream getOutputStream(final String path) {
		WebdavResource res = null;
//		Das muss in den WebDavFile ausgelagert werden. Andernfalls ist ein paralleles
//		�bertragen nicht m�glich
		try {
			res = this.getResource(path, false);
//			res.lockMethod(path, owner, timeout, lockType, depth):
//			res.unlockMethod(path, owner);
			return new SOSVfsWebDAVOutputStream(res);
		}
		catch (Exception ex) {
			RaiseException(ex, SOSVfs_E_193.params("getOutputStream()", path));
			return null;
		}
		finally {
			try {
				if (res != null) {
					res.close();
				}
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 *
	 * \brief changeWorkingDirectory
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 */
	@Override
	public boolean changeWorkingDirectory(String path) {
		try {
			String origPath = davClient.getPath();

			path = this.normalizePath(path+"/");

			davClient.setPath(path);
			if (davClient.exists()) {
				reply = "cwd OK";
				logger.debug(SOSVfs_D_194.params(path, getReplyString()));
			}
			else {
				davClient.setPath(origPath);
				reply = "cwd failed";
				logger.debug(SOSVfs_D_194.params(path, getReplyString()));
				return false;
			}
		}
		catch (Exception ex) {
			RaiseException(ex, SOSVfs_E_193.params("cwd", path));
		}
		return true;
	}

	/**
	 *
	 * \brief getFileHandle
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param fileName
	 * @return
	 */
	@Override
	public ISOSVirtualFile getFileHandle(String fileName) {
		fileName = adjustFileSeparator(fileName);
		ISOSVirtualFile file = new SOSVfsWebDAVFile(fileName);
		OutputStream os = getOutputStream(fileName);
		file.setHandler(this);

		logger.debug(SOSVfs_D_196.params(fileName));

		return file;
	}

	/**
	 *
	 * \brief getModificationTime
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param path
	 * @return
	 */
	@Override
	public String getModificationTime(final String path) {
		WebdavResource res = null;
		String dateTime = null;
		try {
			res = this.getResource(path);
			if (res.exists()) {
				long lm = res.getGetLastModified();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateTime = df.format(new Date(lm));
			}
		}
		catch (Exception ex) {

		}
		finally {
			if (res != null) {
				try {
					res.close();
				}
				catch (Exception e) {
				}
			}
		}

		return dateTime;
	}

	/**
	 *
	 * \brief fileExists
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param filename
	 * @return
	 */
	@Override
	protected boolean fileExists(final String filename) {

		WebdavResource res = null;
		try {
			res = this.getResource(filename);

			return res.exists();
		}
		catch (Exception e) {
			return false;
		}
		finally {
			try {
				if (res != null) {
					res.close();
				}
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 *
	 * \brief getCurrentPath
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	protected String getCurrentPath() {
		String path = null;

		try {
			path = davClient.getPath();
			logger.debug(HostID(SOSVfs_D_195.params(path)));
			LogReply();
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("getCurrentPath"));
		}

		return path;
	}

	/**
	 *
	 * \brief getResource
	 *
	 * \details
	 *
	 * \return WebdavResource
	 *
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private WebdavResource getResource(final String path) throws Exception {
		return getResource(path, true);
	}

	private WebdavResource getWebdavResource(final HttpURL url) throws Exception {
		if(SOSString.isEmpty(proxyHost)) {
			return new WebdavResource(url);
		}
		else {
			WebdavResource r = new WebdavResource(url,proxyHost, proxyPort,new UsernamePasswordCredentials(proxyUser, proxyPassword));
			return r;
		}
	}


	private WebdavResource getResource(String path, final boolean flgTryWithTrailingSlash) throws Exception {
		path = this.normalizePath(path);
		WebdavResource res = getWebdavResource(getWebdavRessourceURL(path));
		//path of a folder must have a trailing slash otherwise it does not exist
		if(flgTryWithTrailingSlash == true && path.endsWith("/") == false && res.exists() == false) {
			//try with trailing slash
			WebdavResource res2 = null;
			try {
				res2 = getWebdavResource(getWebdavRessourceURL(path+"/"));
				if(res2.exists() == true) {
					res = res2;
				}
			}
			catch (Exception e) {}
			finally {
				try {
					if(res2 != null) {
						res2.close();
					}
				}
				catch (Exception e) {}
			}
		}
		return res;
	}

	/**
	 *
	 * \brief normalizePath
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @param path
	 * @return
	 */
	private String normalizePath(String path) {
		if(path.startsWith("/") == false) {
			path = "/" + path;
		}
		path = path.replaceAll("//+", "/");
		return path;
	}

	/**
	 *
	 * \brief normalizeRootHttpURL
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @param url
	 * @return String
	 */
	private String normalizeRootHttpURL(String url, final int port) {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::normalizeRootHttpURL";

		if(url.endsWith("/") == false) {
			url += "/";
		}

		// insert port into url
		if (connection2OptionsAlternate.auth_method.isURL()) {
			//--------------------- (schema://)(user:passw@)?(host)(:port)?(path) ----------
			url = url.replaceFirst("^(https?://)([^/@]+@)?([^/:]+)(:[^/]+)?(.*)$", "$1$2$3:"+port+"$5");
		}

		return url;
	} // private String normalizeRootHttpURL

	/**
	 *
	 * \brief setRootHttpURL
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param puser
	 * @param ppassword
	 * @param phost
	 * @param pport
	 * @return HttpURL
	 * @throws Exception
	 */
	private HttpURL setRootHttpURL(final String puser, final String ppassword, final String phost, final int pport) throws Exception {

		rootUrl = null;
		HttpURL httpUrl = null;
		String path = "/";
		String normalizedHost = normalizeRootHttpURL(phost, pport);

		if (connection2OptionsAlternate.auth_method.isURL()) {
			if (phost.toLowerCase().startsWith("https://")) {
				httpUrl = new HttpsURL(normalizedHost);
			}
			else {
				httpUrl = new HttpURL(normalizedHost);
			}

			String phostRootUrl = httpUrl.getScheme() + "://" + httpUrl.getAuthority() + "/";
			if (httpUrl.getScheme().equalsIgnoreCase("https")) {
				rootUrl = new HttpsURL(phostRootUrl);

				if(pport > 0) {
					Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory)new EasySSLProtocolSocketFactory(),pport));
				}
			}
			else {
				rootUrl = new HttpURL(phostRootUrl);
			}
		}
		else {
			httpUrl = new HttpURL(phost, pport, path);
			rootUrl = new HttpURL(phost, pport, path);
		}
		httpUrl.setUserinfo(puser, ppassword);
		return httpUrl;
	}

	private HttpURL getWebdavRessourceURL(String path) throws Exception {
		HttpURL url = null;

		if(rootUrl != null) {
			path = path.startsWith("/") ? path.substring(1) : path;
			if(rootUrl.getScheme().equalsIgnoreCase("https")) {
				url = new HttpsURL(rootUrl.getEscapedURI()+path);
			}
			else {
				url = new HttpURL(rootUrl.getEscapedURI()+path);
			}
		}
		url.setUserinfo(userName,password);

		return url;
	}

	/**
	 *
	 * \brief doAuthenticate
	 *
	 * \details
	 *
	 * \return ISOSConnection
	 *
	 * @param authenticationOptions
	 * @return
	 * @throws Exception
	 */
	private ISOSConnection doAuthenticate(final ISOSAuthenticationOptions pAuthenticationOptions) throws Exception {

		authenticationOptions = pAuthenticationOptions;

		userName = authenticationOptions.getUser().Value();
		password = authenticationOptions.getPassword().Value();

		logger.debug(SOSVfs_D_132.params(userName));

		HttpURL httpUrl = this.setRootHttpURL(userName, password, host, port);

		try {
			// eigentlich bereits bei new WebdavResource soll eine Exception ausgel�st werden,
			// wenn die Credentials bzw host etc falsch sind
			// hier aber aus irgend. Grund kommt keine Exception hoch
			if(SOSString.isEmpty(proxyHost)) {
				davClient = new WebdavResource(httpUrl);
			}
			else {
				logger.info("using proxy: host "+proxyHost+":"+proxyPort+" user = "+proxyUser);
				davClient = new WebdavResource(httpUrl,proxyHost, proxyPort,new UsernamePasswordCredentials(proxyUser, proxyPassword));
			}

			if (!davClient.exists()) {
				throw new JobSchedulerException("not connected " + davClient.getStatusMessage());
			}

		}
		catch (Exception ex) {
			throw new JobSchedulerException(SOSVfs_E_167.params(authenticationOptions.getAuth_method().Value(), authenticationOptions.getAuth_file().Value()), ex);
		}

		reply = "OK";
		logger.debug(SOSVfs_D_133.params(userName));
		this.LogReply();

		return this;
	}

	/**
	 *
	 * \brief connect
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param phost
	 * @param pport
	 */
	private void connect(final String phost, final int pport) {

		host = phost;
		port = pport;

		// TODO Meldung auch ohne Port oder Port mit ?? ausgeben.
		String msgPort = connection2OptionsAlternate.auth_method.isURL() ? "??" : "" + port;

		logger.debug(SOSVfs_D_0101.params(host, port));

		if (this.isConnected() == false) {

			this.LogReply();
		}
		else {
			logWARN(SOSVfs_D_0103.params(host, port));
		}
	}

	@Override
	public OutputStream getOutputStream() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

}
