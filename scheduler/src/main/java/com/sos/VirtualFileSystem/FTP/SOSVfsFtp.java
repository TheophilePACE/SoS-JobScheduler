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
package com.sos.VirtualFileSystem.FTP;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilities;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.DataElements.SOSFileList;
import com.sos.VirtualFileSystem.DataElements.SOSFileListEntry;
import com.sos.VirtualFileSystem.DataElements.SOSFolderName;
import com.sos.VirtualFileSystem.Interfaces.ISOSAuthenticationOptions;
import com.sos.VirtualFileSystem.Interfaces.ISOSConnection;
import com.sos.VirtualFileSystem.Interfaces.ISOSSession;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFileSystem;
import com.sos.i18n.annotation.I18NResourceBundle;

@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsFtp extends SOSVfsFtpBaseClass implements ISOSVfsFileTransfer, ISOSVFSHandler, ISOSVirtualFileSystem, ISOSConnection {
	private final String	conClassName	= "SOSVfsFtp";
	private final Logger	logger			= Logger.getLogger(SOSVfsFtp.class);

	// protected Msg Messages = new Msg(new BundleBaseName(this.getClass()
	// .getAnnotation(I18NResourceBundle.class)
	// .baseName()));

	//	private Vector<String>					vecDirectoryListing		= null;
	private FTPClient		objFTPClient	= null;

	@Deprecated
	//	private ISOSConnectionOptions			objConnectionOptions	= null;
	//	private SOSConnection2OptionsAlternate	objConnection2Options	= null;
	//	private String							strCurrentPath			= EMPTY_STRING;
	//	private String							strReply				= EMPTY_STRING;
	//	private ProtocolCommandListener			listener				= null;
	//	private String							host					= EMPTY_STRING;
	//	private int								port					= 0;
	//	private String							gstrUser				= EMPTY_STRING;
	//	private SOSOptionHostName				objHost					= null;
	//	private SOSOptionPortNumber				objPort					= null;
	/**
	 *
	 * \brief SOSVfsFtp
	 *
	 * \details
	 *
	 */
	public SOSVfsFtp() {
		super();
	}

	//	/**
	//	 * Creates a new subdirectory on the FTP server in the current directory .
	//	 * @param pathname The pathname of the directory to create.
	//	 * @return True if successfully completed, false if not.
	//	 * @throws java.lang.IOException
	//	 */
	//	public void mkdir(String pathname) {
	//		final String conMethodName = conClassName + "::mkdir";
	//
	//		try {
	//			Client().makeDirectory(pathname);
	//			logger.debug(HostID(String.format(Messages.getMsg(SOSVfs_E_0106), conMethodName, pathname, getReplyString())));
	//		}
	//		catch (IOException e) {
	//			String strM = HostID(String.format(Messages.getMsg(SOSVfs_E_0105), conMethodName));
	//			e.printStackTrace(System.err);
	//			throw new RuntimeException(strM, e);
	//		}
	//	}

	//	/**
	//	 * Removes a directory on the FTP server (if empty).
	//	 * @param pathname The pathname of the directory to remove.
	//	 * @return True if successfully completed, false if not.
	//	 * @throws java.lang.IOException
	//	 */
	//	public void rmdir(String pathname) throws IOException {
	//		@SuppressWarnings("unused")
	//		final String conMethodName = conClassName + "::rmdir";
	//
	//		Client().removeDirectory(pathname);
	//		logger.debug(HostID(String.format(Messages.getMsg(SOSVfs_E_0106), conMethodName, pathname, getReplyString())));
	//	}

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine
	 * @param pathname on remote machine
	 * @return a listing of the contents of a directory on the remote machine
	 *
	 * @exception Exception
	 * @see #dir()
	 */
	@Override
	public Vector<String> nList(final String pathname) {
		return getFilenames(pathname);
	} // nList

	private Vector<String> getFilenames(final String pathname) {
		return getFilenames(pathname, false);
	}

	private 			FTPFile[] listFiles = null;

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine (without subdirectory)
	 *
	 * @param pathname on remote machine
	 * @return a listing of the contents of a directory on the remote machine
	 * @throws IOException
	 *
	 * @exception Exception
	 * @see #dir()
	 */
	private Vector<String> getFilenames(final String pstrPathName, final boolean flgRecurseSubFolders) {

		String conMethodName = "getFilenames";
		String strCurrentDirectory = null;
		// TODO vecDirectoryListing = null; pr�fen, ob notwendig
		Vector<String> vecDirectoryListing = null;
		if (vecDirectoryListing == null) {
			vecDirectoryListing = new Vector<String>();
			String[] fileList = null;
			strCurrentDirectory = DoPWD();
			String lstrPathName = pstrPathName.trim();
			if (lstrPathName.length() <= 0) {
				lstrPathName = ".";
			}
			if (lstrPathName.equals(".")) {
				lstrPathName = strCurrentDirectory;
			}

			//			if (1 == 1) {
			//				try {
			//					fileList = listNames(lstrPathName);
			//				}
			//				catch (IOException e) {
			//					e.printStackTrace(System.err);
			//				}
			//			}

			try {
				listFiles = Client().listFiles(lstrPathName);
			}
			catch (IOException e) {
				RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
			}

			if (listFiles == null || listFiles.length <= 0) {
				return vecDirectoryListing;
			}

			for (FTPFile listFile : listFiles) {
				String strCurrentFile = listFile.getName();
				if (isNotHiddenFile(strCurrentFile) && strCurrentFile.trim().length() > 0) {
					//					DoCD(strCurrentFile); // is this file-entry a subfolder?
					//					boolean flgIsDirectory = isNegativeCommandCompletion() == false;
					boolean flgIsDirectory = listFile.isDirectory();
					if (flgIsDirectory == false) {
						if (lstrPathName.startsWith("/") == false) { // JIRA SOSFTP-124
							if (strCurrentFile.startsWith(strCurrentDirectory) == false) {
								strCurrentFile = addFileSeparator(strCurrentDirectory) + strCurrentFile;
							}
						}
						vecDirectoryListing.add(strCurrentFile);
					}
					else {
						if (flgIsDirectory && flgRecurseSubFolders == true) {
							DoCD(strCurrentDirectory);
							if (flgRecurseSubFolders) {
								logger.debug(String.format(""));
								Vector<String> vecNames = getFilenames(strCurrentFile, flgRecurseSubFolders);
								if (vecNames != null) {
									vecDirectoryListing.addAll(vecNames);
								}
							}
						}
					}
				}
			}
		}
		logger.debug("strCurrentDirectory = " + strCurrentDirectory);
		if (strCurrentDirectory != null) {
			DoCD(strCurrentDirectory);
			DoPWD();
		}
		return vecDirectoryListing;
	}// nList

	private int DoCD(final String strFolderName) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::DoCD";
		int x = 0;
		try {
			String strT = strFolderName.replaceAll("\\\\", "/");
			logger.debug(SOSVfs_D_127.params(strT));
			x = cd(strT);
			LogReply();
		}
		catch (IOException e) {
		}
		return x;
	} // private int DoCD

	//	private boolean LogReply() {
	//		@SuppressWarnings("unused")
	//		final String conMethodName = conClassName + "::LogReply";
	//		strReply = getReplyString();
	//		logger.debug(HostID(strReply));
	//		return true;
	//	} // private boolean LogReply

	@Override
	public boolean isNegativeCommandCompletion() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::isNegativeCommandCompletion";
		int x = Client().getReplyCode();
		return x > 300;
	} // private boolean isNegativeCommandCompletion

	private boolean isPositiveCommandCompletion() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::isPositiveCommandCompletion";
		int x = Client().getReplyCode();
		return x <= 300;
	} // private boolean isPositiveCommandCompletion

	@Override
	public boolean isNotHiddenFile(final String strFileName) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::isNotHiddenFile";
		if (strFileName.endsWith("..") == false && strFileName.endsWith(".") == false) {
			return true; // not a hidden file
		}
		return false; // it is a hidden-file
	} // private boolean isNotHiddenFile

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine
	 * @param pathname on remote machine
	 * @return a listing of the contents of a directory on the remote machine
	 *
	 * @exception Exception
	 * @see #dir()
	 */
	@Override
	public Vector<String> nList(final String pathname, final boolean flgRecurseSubFolder) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::nList";

		try {
			return getFilenames(pathname, flgRecurseSubFolder);
		}
		catch (Exception e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		return null; // useless, but required by syntax-check
	} // nList

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine
	 *
	 * @return a listing of the contents of a directory on the remote machine
	 *
	 * @exception Exception
	 * @see #nList( String )
	 * @see #dir()
	 * @see #dir( String )
	 */
	@Override
	public Vector<String> nList() throws Exception {
		return getFilenames();
	} // nList

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine (without subdirectory)
	 *
	 * @return a listing of the contents of a directory on the remote machine
	 *
	 * @exception Exception
	 * @see #nList( String )
	 * @see #dir()
	 * @see #dir( String )
	 */
	private Vector<String> getFilenames() throws Exception {
		return getFilenames("", false);
	} // getFilenames

	private Vector<String> getFilenames(final boolean flgRecurseSubFolders) throws Exception {
		return getFilenames("", flgRecurseSubFolders);
	} // getFilenames

	/**
	 * return a listing of the contents of a directory in short format on
	 * the remote machine
	 *
	 * @return a listing of the contents of a directory on the remote machine
	 *
	 * @exception Exception
	 * @see #nList( String )
	 * @see #dir()
	 * @see #dir( String )
	 */
	@Override
	public Vector<String> nList(final boolean recursive) throws Exception {
		return getFilenames(recursive);
	} // nList

	/**
	 * return a listing of the files in a directory in long format on
	 * the remote machine
	 * @param pathname on remote machine
	 * @return a listing of the contents of a directory on the remote machine
	 * @exception Exception
	 * @see #nList()
	 * @see #nList( String )
	 * @see #dir()
	 */
	@Override
	public SOSFileList dir(final String pathname) {
		Vector<String> strList = getFilenames(pathname);
		String[] strT = strList.toArray(new String[strList.size()]);
		SOSFileList objFileList = new SOSFileList(strT);
		return objFileList;
	}

	/**
	 * return a listing of a directory in long format on
	 * the remote machine
	 *
	 * @param pathname on remote machine
	 * @return a listing of the contents of a directory on the remote machine
	 * @exception Exception
	 * @see #nList()
	 * @see #nList( String )
	 * @see #dir()
	 */
	@Override
	public SOSFileList dir(final String pathname, final int flag) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::dir";

		SOSFileList fileList = new SOSFileList();
		FTPFile[] listFiles = null;
		try {
			listFiles = Client().listFiles(pathname);
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		for (FTPFile listFile : listFiles) {
			if (flag > 0 && listFile.isDirectory()) {
				fileList.addAll(this.dir(pathname + "/" + listFile.toString(), flag >= 1024 ? flag : flag + 1024));
			}
			else {
				if (flag >= 1024) {
					fileList.add(pathname + "/" + listFile.toString());
				}
				else {
					fileList.add(listFile.toString());
				}
			}
		}
		return fileList;
	}

	/**
	 * return a listing of the files of the current directory in long format on
	 * the remote machine
	 * @return a listing of the contents of the current directory on the remote machine
	 * @exception Exception
	 * @see #nList()
	 * @see #nList( String )
	 * @see #dir( String )
	 */
	@Override
	public SOSFileList dir() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::dir";

		try {
			return dir(".");
		}
		catch (Exception e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		return null;
	}

	/**
	 * @return The entire text from the last FTP response as a String.
	 */
	@Override
	public String getResponse() {
		return this.getReplyString();
	}

	/**
	 * return the size of remote-file on the remote machine on success, otherwise -1
	 * @param remoteFile the file on remote machine
	 * @return the size of remote-file on remote machine
	 */
	@Override
	public long size(final String remoteFile) throws Exception {

		//		Client().sendCommand("NOOP");
		//		LogReply();
		//		LogReply();
		//		LogReply();

		if (listFiles != null && 1 == 0) {
			for (FTPFile objFile : listFiles) {
				if (objFile.getName().equalsIgnoreCase(remoteFile)) {
					return objFile.getSize();
				}
			}
			return -1L;
		}
		else {
			Client().sendCommand("SIZE " + remoteFile);
			LogReply();
			if (Client().getReplyCode() == FTPReply.CODE_213)
				return Long.parseLong(trimResponseCode(this.getReplyString()));
			else
				return -1L; // file not found or size not available
		}
	}

	/**
	 * trim the response code at the beginning
	 * @param response
	 * @return the response string without response code
	 * @throws Exception
	 */
	private String trimResponseCode(final String response) throws Exception {
		if (response.length() < 5)
			return response;
		return response.substring(4).trim();
	}

	/**
	 * Retrieves a named file from the ftp server.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @exception Exception
	 * @see #getFile( String, String )
	 */
	@Override
	public void get(final String remoteFile, final String localFile) {
		final String conMethodName = conClassName + "::get";

		FileOutputStream out = null;
		boolean rc = false;
		try {
			out = new FileOutputStream(localFile);
			rc = Client().retrieveFile(remoteFile, out);
			if (rc == false) {
				RaiseException(HostID(SOSVfs_E_0105.params(conMethodName)));
			}
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		finally {
			closeObject(out);
		}
	} // get

	private void closeObject(OutputStream objO) {
		try {
			if (objO != null) {
				objO.flush();
				objO.close();
				objO = null;
			}
		}
		catch (Exception e) {
		}
	}

	private void closeInput(InputStream objO) {
		try {
			if (objO != null) {
				objO.close();
				objO = null;
			}
		}
		catch (IOException e) {
		}
	}

	/**
	 * Retrieves a named file from the ftp server.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @return  The total number of bytes retrieved.
	 * @see #get( String, String )
	 * @exception Exception
	 */
	@Override
	public long getFile(final String remoteFile, final String localFile) {
		final boolean flgAppendLocalFile = false;
		return this.getFile(remoteFile, localFile, flgAppendLocalFile);
	}

	/**
	 * Retrieves a named file from the ftp server.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @param append Appends the remote file to the local file.
	 * @return  The total number of bytes retrieved.
	 * @see #get( String, String )
	 * @exception Exception
	 */
	@Override
	public long getFile(final String remoteFile, final String localFile, final boolean append) {
		final String conMethodName = conClassName + "::getFile";

		InputStream in = null;
		OutputStream out = null;
		long totalBytes = 0;
		try {
			// TODO get filesize and report as a message
			in = Client().retrieveFileStream(remoteFile);
			if (in == null) {
				throw new JobSchedulerException(SOSVfs_E_143.params(getReplyString()));
			}
			if (isPositiveCommandCompletion() == false) {
				throw new JobSchedulerException(SOSVfs_E_144.params("getFile()", remoteFile, getReplyString()));
			}
			// TODO Buffersize must be an Option
			byte[] buffer = new byte[4096];
			out = new FileOutputStream(new File(localFile), append);
			// TODO get progress info
			int bytes_read = 0;
			synchronized (this) {
				while ((bytes_read = in.read(buffer)) != -1) {
					// TODO create progress message
					out.write(buffer, 0, bytes_read);
					out.flush();
					totalBytes += bytes_read;
				}
			}
			closeInput(in);
			closeObject(out);
			this.CompletePendingCommand();
			// TODO create completed Message
			if (totalBytes > 0)
				return totalBytes;
			else
				return -1L;
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		finally {
			closeInput(in);
			closeObject(out);
		}
		return totalBytes;
	}

	/**
	 * Stores a file on the server using the given name.
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @return True if successfully completed, false if not.
	 * @exception Exception
	 * @see #putFile( String, String )
	 */
	@Override
	public void put(final String localFile, final String remoteFile) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::put";

		FileInputStream in = null;
		boolean rc = false;
		try {
			in = new FileInputStream(localFile);
			// TODO get progress info
			rc = Client().storeFile(remoteFile, in);
			if (rc == false) {
				RaiseException(SOSVfs_E_154.params("put"));
			}
		}
		catch (Exception e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		finally {
			closeInput(in);
		}
	}

	/**
	 * Stores a file on the server using the given name.
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 * @return The total number of bytes written.
	 *
	 * @exception Exception
	 * @see #put( String, String )
	 */
	@Override
	// ISOSVfsFileTransfer
	public long putFile(final String localFile, final String remoteFile) throws Exception {
		OutputStream outputStream = Client().storeFileStream(remoteFile);
		if (isNegativeCommandCompletion()) {
			RaiseException(SOSVfs_E_144.params("storeFileStream()", remoteFile, getReplyString()));
		}
		long i = putFile(localFile, outputStream);
		logger.debug(SOSVfs_D_146.params(localFile, remoteFile));
		return i;
	} // putFile

	/**
	 * written to store a file on the server using the given name.
	 *
	 * @param localfile The name of the local file.
	 * @param an OutputStream through which data can be
	 * @return The total number of bytes written.
	 * @exception Exception
	 */
	@SuppressWarnings("null")
	@Override
	public long putFile(final String localFile, final OutputStream out) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::putFile";

		if (out == null)
			RaiseException("OutputStream null value.");
		FileInputStream in = null;
		long lngTotalBytesWritten = 0;
		try {
			// TODO Buffersize must be an Option
			byte[] buffer = new byte[4096];
			in = new FileInputStream(new File(localFile));
			// TODO get progress info
			int bytesWritten;
			synchronized (this) {
				while ((bytesWritten = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytesWritten);
					lngTotalBytesWritten += bytesWritten;
				}
			}
			closeInput(in);
			closeObject(out);
			this.CompletePendingCommand();
			// if (Client().completePendingCommand() == false) {
			// logout();
			// disconnect();
			// RaiseException("Filetransfer failed. completePendingCommand() returns false");
			// }
			// if (isNegativeCommandCompletion()) {
			// RaiseException("..error occurred in putFile() on the FTP server for file [" + localFile + "]: " + getReplyString());
			// }
			return lngTotalBytesWritten;
		}
		catch (Exception e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		finally {
			closeInput(in);
			closeObject(out);
		}
		return lngTotalBytesWritten;
	} // putFile

	//	private void RaiseException(final Exception e, final String pstrM) {
	//		if (flgStackTracePrinted == false) {
	//			e.printStackTrace(System.err);
	//			logger.error(pstrM);
	//			flgStackTracePrinted = true;
	//		}
	//		throw new JobSchedulerException(pstrM, e);
	//	}
	//
	//	private void RaiseException(final String pstrM) {
	//		logger.error(pstrM);
	//		throw new JobSchedulerException(pstrM);
	//	}

	public FTPClient getClient() {
		return Client();
	}

	/**
	 * append a local file to the remote one on the server
	 *
	 * @param localFile The name of the local file.
	 * @param remoteFile The name of the remote file.
	 *
	 * @return The total number of bytes appended.
	 *
	 * @exception Exception
	 * @see #put( String, String )
	 * @see #putFile( String, String )
	 */
	@Override
	public long appendFile(final String localFile, final String remoteFile) {
		final String conMethodName = conClassName + "::appendFile";

		long i = 0;
		try {
			i = putFile(localFile, Client().appendFileStream(remoteFile));
			logger.info(SOSVfs_I_155.params(i));
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		return i;
	} // appendFile

	/**
	 * Using ASCII mode for file transfers
	 * @return True if successfully completed, false if not.
	 * @throws IOException If an I/O error occurs while either sending a
	 * command to the server or receiving a reply from the server.
	 */
	@Override
	public void ascii() {
		final String conMethodName = conClassName + "::ascii";

		try {
			boolean flgResult = Client().setFileType(FTP.ASCII_FILE_TYPE);
			if (flgResult == false) {
				throw new JobSchedulerException(SOSVfs_E_149.params(getReplyString()));
			}
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
	}

	/**
	 * Using Binary mode for file transfers
	 * @return True if successfully completed, false if not.
	 * @throws IOException If an I/O error occurs while either sending a
	 * command to the server or receiving a reply from the server.
	 */
	@Override
	public void binary() {
		final String conMethodName = conClassName + "::binary";

		try {
			boolean flgResult = Client().setFileType(FTP.BINARY_FILE_TYPE);
			if (flgResult == false) {
				throw new JobSchedulerException(SOSVfs_E_149.params(getReplyString()));
			}
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
	}

	/**
	 *
	 * @param directory The new working directory.
	 * @return The reply code received from the server.
	 * @throws IOException If an I/O error occurs while either sending a
	 * command to the server or receiving a reply from the server.
	 */
	@Override
	public int cd(final String directory) throws IOException {
		return Client().cwd(directory);
	}

	@Override
	public boolean changeWorkingDirectory(final String pathname) {
		final String conMethodName = conClassName + "::changeWorkingDirectory";
		boolean flgR = true;
		try {
			String strT = pathname.replaceAll("\\\\", "/");
			Client().cwd(strT);
			logger.debug(SOSVfs_D_135.params(strT, getReplyString(), "[directory exists]"));
			flgR = objFTPReply.isSuccessCode();
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		return flgR;
	}

	@Override
	public void disconnect() {
		final String conMethodName = conClassName + "::disconnect";

		try {
			if (Client().isConnected()) {
				Client().disconnect();
			}
		}
		catch (IOException e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
	}

	@Override
	public String[] listNames(final String pathname) throws IOException {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::listNames";
		String strT = pathname.replaceAll("\\\\", "/");
		String strA[] = Client().listNames(strT);
		if (strA != null) {
			for (int i = 0; i < strA.length; i++) {
				strA[i] = strA[i].replaceAll("\\\\", "/");
			}
		}
		else {
			strA = new String[] {};
		}
		logger.debug(SOSVfs_D_137.params(Client().getReplyString(), Client().getReplyCode()));
		return strA;
	}

	@Override
	public void rename(final String from, final String to) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::rename";

		try {
			this.Client().rename(from, to);
		}
		catch (IOException e) {
			RaiseException(e, SOSVfs_E_134.params("rename()"));
		}
		logger.info(SOSVfs_I_150.params(from, to));
	}

	@Override
	public ISOSVFSHandler getHandler() {
		return this;
	}

	@Override
	public void ExecuteCommand(final String strCmd) throws Exception {
		final String conMethodName = conClassName + "::ExecuteCommand";

		objFTPClient.sendCommand(strCmd);
		logger.debug(HostID(SOSVfs_E_0106.params(conMethodName, strCmd, getReplyString())));
		objFTPClient.sendCommand("NOOP");
		getReplyString();
	}

	@Override
	public String createScriptFile(final String pstrContent) throws Exception {
		notImplemented();
		return null;
	}

	@Override
	public Integer getExitCode() {
		notImplemented();
		return null;
	}

	@Override
	public String getExitSignal() {
		notImplemented();
		return null;
	}
	@SuppressWarnings("unused")
	private final ISOSAuthenticationOptions	objAO	= null;

	@Override
	public void CloseConnection() throws Exception {
		if (Client().isConnected()) {
			Client().disconnect();
			LogReply();
			logger.debug(SOSVfs_I_0109.params(host, port));
		}
	}

	@Override
	protected final FTPClient Client() {
		if (objFTPClient == null) {
			objFTPClient = new FTPClient();
			FTPClientConfig conf = new FTPClientConfig();
			// conf.setServerLanguageCode("fr");
			// objFTPClient.configure(conf);
			/**
			 * This listener is to write all commands and response from commands to system.out
			 *
			 */
			objProtocolCommandListener = new SOSFtpClientLogger(HostID(""));
			if (objConnection2Options != null) {
				if (objConnection2Options.ProtocolCommandListener.isTrue()) {
					objFTPClient.addProtocolCommandListener(objProtocolCommandListener);
					logger.debug("ProtocolcommandListener added and activated");
				}
			}

			// TODO implement as an additional Option-setting
			String strAddFTPProtocol = System.getenv("AddFTPProtocol");
			if (strAddFTPProtocol != null && strAddFTPProtocol.equalsIgnoreCase("true")) {
				objFTPClient.addProtocolCommandListener(objProtocolCommandListener);
			}

		}
		return objFTPClient;
	}

	@Override
	public SOSFileListEntry getNewVirtualFile(final String pstrFileName) {
		SOSFileListEntry objF = new SOSFileListEntry(pstrFileName);
		objF.VfsHandler(this);
		return objF;
	}

	@Override
	public ISOSConnection getConnection() {
		return this;
	}

	@Override
	public ISOSSession getSession() {
		return null;
	}

	@Override
	public SOSFileList dir(final SOSFolderName pobjFolderName) {
		this.dir(pobjFolderName.Value());
		return null;
	}

	@Override
	public StringBuffer getStdErr() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getStdOut() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remoteIsWindowsShell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setJSJobUtilites(final JSJobUtilities pobjJSJobUtilities) {
		// TODO Auto-generated method stub
	}

	@Override
	public ISOSVirtualFile getFileHandle(final String pstrFilename) {
		final String conMethodName = conClassName + "::getFileHandle";
		String strT = pstrFilename.replaceAll("\\\\", "/");
		ISOSVirtualFile objFtpFile = new SOSVfsFtpFile(strT);
		logger.trace(SOSVfs_D_152.params(strT, conMethodName));
		objFtpFile.setHandler(this);
		return objFtpFile;
	}

	@Override
	public String[] getFilelist(final String folder, final String regexp, final int flag, final boolean flgRecurseSubFolder) {
		// TODO vecDirectoryListing = null; pr�fen, ob notwendig
		vecDirectoryListing = null;
		if (vecDirectoryListing == null) {
			vecDirectoryListing = nList(folder, flgRecurseSubFolder);
		}
		Vector<String> strB = new Vector<String>();
		Pattern pattern = Pattern.compile(regexp, 0);
		for (String strFile : vecDirectoryListing) {
			/**
			 * the file_spec has to be compared to the filename only ... excluding the path
			 */
			String strFileName = new File(strFile).getName();
			Matcher matcher = pattern.matcher(strFileName);
			if (matcher.find() == true) {
				strB.add(strFile);
			}
		}
		return strB.toArray(new String[strB.size()]);
	}

	@Override
	public void closeInput() {
	}

	@Override
	public void flush() {
	}

	@Override
	public int read(final byte[] bteBuffer) {
		return 0;
	}

	@Override
	public int read(final byte[] bteBuffer, final int intOffset, final int intLength) {
		return 0;
	}

	@Override
	public void write(final byte[] bteBuffer, final int intOffset, final int intLength) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::write";
	}

	@Override
	public void write(final byte[] bteBuffer) {
	}

	@Override
	public void openInputFile(final String pstrFileName) {
	}

	@Override
	public void openOutputFile(final String pstrFileName) {
	}

	@Override
	public Vector<ISOSVirtualFile> getFiles(final String string) {
		return null;
	}

	@Override
	public Vector<ISOSVirtualFile> getFiles() {
		return null;
	}

	@Override
	public void putFile(final ISOSVirtualFile objVirtualFile) {
		final String conMethodName = conClassName + "::putFile";

		String strName = objVirtualFile.getName();
		// strName = new File(strName).getAbsolutePath();
		// if (strName.startsWith("c:") == true) {
		// strName = strName.substring(3);
		// }
		ISOSVirtualFile objVF = this.getFileHandle(strName);
		OutputStream objOS = objVF.getFileOutputStream();
		InputStream objFI = objVirtualFile.getFileInputStream();
		// TODO Buffersize must be defined via Options-Class
		int lngBufferSize = 1024;
		byte[] buffer = new byte[lngBufferSize];
		int intBytesTransferred;
		long totalBytes = 0;
		try {
			synchronized (this) {
				while ((intBytesTransferred = objFI.read(buffer)) != -1) {
					objOS.write(buffer, 0, intBytesTransferred);
					totalBytes += intBytesTransferred;
				}
				objFI.close();
				objOS.flush();
				objOS.close();
			}
		}
		catch (Exception e) {
			RaiseException(e, HostID(SOSVfs_E_0105.params(conMethodName)));
		}
		finally {
		}
	}

}
