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
/**
 *
 */
package com.sos.VirtualFileSystem.DataElements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sos.JSHelper.DataElements.JSDataElementDate;
import com.sos.JSHelper.DataElements.JSDateFormat;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Logging.Log4JHelper;
import com.sos.JSHelper.io.Files.JSFile;
import com.sos.VirtualFileSystem.DataElements.SOSFileListEntry.enuTransferStatus;
import com.sos.VirtualFileSystem.FTP.SOSFTPOptions;
import com.sos.VirtualFileSystem.Factory.VFSFactory;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.common.SOSVfsConstants;
import com.sos.VirtualFileSystem.common.SOSVfsMessageCodes;
import com.sos.i18n.annotation.I18NResourceBundle;
import com.sos.scheduler.model.SchedulerObjectFactory;

/**
 * @author KB
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSFileList extends SOSVfsMessageCodes {

	@SuppressWarnings("unused")
	private final String					conSVNVersion					= "$Id: SOSFileList.java 19109 2013-02-13 12:12:06Z oh $";

	private static String					conClassName					= "SOSFileList";
	private static Logger					logger							= Logger.getLogger(SOSFileList.class);
	private static Logger					objJadeReportLogger				= Logger.getLogger(VFSFactory.getLoggerName());
	@SuppressWarnings("unused")
	private static Log4JHelper				objLogger						= null;

	private SOSFTPOptions					objOptions						= null;

	private Vector<SOSFileListEntry>		objFileListEntries				= new Vector<SOSFileListEntry>();
	long									lngSuccessfulTransfers			= 0;
	long									lngFailedTransfers				= 0;
	long									lngSkipedTransfers				= 0;
	long lngNoOfTransferHistoryRecordsSent = 0;
	long lngNoOfHistoryFileRecordsWritten = 0;
	long lngNoOfRecordsInResultSetFile = 0;

	@SuppressWarnings("unused")
	private SOSFileList						objParent						= null;

	private ISOSVFSHandler					objVFS							= null;
	public ISOSVfsFileTransfer				objDataTargetClient				= null;
	public ISOSVfsFileTransfer				objDataSourceClient				= null;

	private final JSDataElementDate			dteTransactionStart				= new JSDataElementDate(Now(), JSDateFormat.dfTIMESTAMPS);
	private final JSDataElementDate			dteTransactionEnd				= new JSDataElementDate(Now(), JSDateFormat.dfTIMESTAMPS);

	public SchedulerObjectFactory			objFactory						= null;
	private final HashMap<String, String>	objSubFolders					= new HashMap<String, String>();

	public int								lngNoOfZeroByteSizeFiles		= 0;
	private boolean							flgHistoryFileAlreadyWritten	= false;
	private boolean							flgResultSetFileAlreadyCreated	= false;

	public void VFSHandler(final ISOSVFSHandler pobjVFS) {
		objVFS = pobjVFS;
	}

	public ISOSVFSHandler VFSHandler() {
		return objVFS;
	}

	public HashMap<String, String> getSubFolderList() {
		return objSubFolders;
	}

	public boolean add2SubFolders(final String pstrSubFolderName) {
		boolean flgR = false;
		String strT = getSubFolderList().get(pstrSubFolderName);
		if (strT == null) {
			flgR = true;
			getSubFolderList().put(pstrSubFolderName, "");
		}
		return flgR;
	}

	public void setParent(final SOSFileList pobjParent) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setParent";

		objParent = pobjParent;
	} // private void setParent

	/**
	 *
	 */
	@SuppressWarnings("deprecation")
	public SOSFileList() {
		super(SOSVfsConstants.strBundleBaseName);
	}

	public SOSFileList(final ISOSVFSHandler pobjVFS) {
		this();
		this.VFSHandler(pobjVFS);
	}

	public SOSFTPOptions Options() {
		return objOptions;
	}

	public void Options(final SOSFTPOptions pobjOptions) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options";

		objOptions = pobjOptions;

	} // private void Options

	public long count() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::count";

		return objFileListEntries.size();
	} // private long count

	private void CountStatus() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::CountStatus";

		lngSuccessfulTransfers = 0;
		lngFailedTransfers = 0;
		lngSkipedTransfers = 0;

		for (SOSFileListEntry objEntry : objFileListEntries) {
			switch (objEntry.getTransferStatus()) {
				case transferred:
					lngSuccessfulTransfers++;
					break;

				case renamed:
					lngSuccessfulTransfers++;
					break;

				case deleted:
					lngSuccessfulTransfers++;
					break;

				case transfer_has_errors:
					lngFailedTransfers++;
					break;

				case transfer_skipped:
					lngSkipedTransfers++;
					break;

				default:
					break;
			}
		}
	} // private void CountStatus

	/**
	 *
	 * \brief SuccessfulTransfers
	 *
	 * \details
	 *
	 * \return long
	 *
	 * @return
	 */
	public long SuccessfulTransfers() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::count";
		this.CountStatus();
		return lngSuccessfulTransfers;
	} // private long SuccessfulTransfers

	/**
	 *
	 * \brief FailedTransfers
	 *
	 * \details
	 *
	 * \return long
	 *
	 * @return
	 */
	public long FailedTransfers() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::count";
		CountStatus();
		return lngFailedTransfers;
	} // private long SuccessfulTransfers

	/**
	 *
	 * \brief SOSFileList
	 *
	 * \details
	 *
	 * @param pstrFileList
	 */
	public SOSFileList(final String[] pstrFileList) {

		this();
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::";
		for (String strFileName : pstrFileList) {
			this.add(strFileName);
		}
	} // private SOSFileList

	public Vector<SOSFileListEntry> List() {
		if (objFileListEntries == null) {
			objFileListEntries = new Vector<SOSFileListEntry>();
		}
		return objFileListEntries;
	} // public Vector <SOSFileListEntry> List ()

	/**
	 *
	 * \brief SOSFileList
	 *
	 * \details
	 *
	 * @param pvecFileList
	 */
	public SOSFileList(final Vector<File> pvecFileList) {
		this();

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::";

		for (File fleFileName : pvecFileList) {
			this.add(fleFileName.getAbsolutePath());
		}
	} // private SOSFileList

	/**
	 *
	 * \brief addAll
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param pobjFileList
	 */
	public void addAll(final SOSFileList pobjFileList) {

		for (SOSFileListEntry objFile : pobjFileList.List()) {
			this.add(objFile.SourceFileName());
		}
	}

	/**
	 *
	 * \brief add
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param String[] pstrA
	 */
	public void add(final String[] pstrA, final String pstrFolderName) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::add";

		if (pstrA == null) {
			return;
		}
		File fleDir = new File(pstrFolderName);
		String strDir = fleDir.getPath();
		for (String strFileName : pstrA) {
			File fleT = new File(strFileName);
			String strP = fleT.getParent();
			if (strP == null) {
				strFileName = strDir + "/" + strFileName;
			}
			this.add(strFileName);
		}

	} // private SOSFileListEntry add

	public void addFileNames(final Vector<String> pstrA) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::add";

		for (String strFileName : pstrA) {
			this.add(strFileName);
		}

	} // private SOSFileListEntry add

	public void addFiles(final Vector<File> pfleA) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::add";

		for (File fleFileName : pfleA) {
			this.add(fleFileName.getAbsolutePath());
		}

	} // private SOSFileListEntry add

	/**
	 *
	 * \brief add
	 *
	 * \details
	 *
	 * \return SOSFileListEntry
	 *
	 * @param pstrLocalFileName
	 * @return
	 */
	public SOSFileListEntry add(final String pstrLocalFileName) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::add";

		if (objFileListEntries == null) {
			objFileListEntries = new Vector<SOSFileListEntry>();
		}

		SOSFileListEntry objEntry = this.Find(pstrLocalFileName);
		if (objEntry == null) {
			objEntry = new SOSFileListEntry(pstrLocalFileName);
			objEntry.VfsHandler((ISOSVfsFileTransfer) objVFS);
			objEntry.setParent(this);
			objFileListEntries.add(objEntry);
			objEntry.Options(objOptions);
			objEntry.setStatus(SOSFileListEntry.enuTransferStatus.waiting4transfer);
		}

		return objEntry;
	} // private SOSFileListEntry add

	public SOSFileListEntry Find(final String pstrLocalFileName) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Find";

		for (SOSFileListEntry objEntry : objFileListEntries) {
			if (pstrLocalFileName.equalsIgnoreCase(objEntry.SourceFileName())) {
				return objEntry;
			}
		}

		return null;
	} // private SOSFileListEntry Find

	// TODO Diese Methoden m�ssen in die SOSFileListEntry-Klasse, da sie nicht unmittelbar etwas mit FTP zu tun haben (Die FileListKlasse
	// wird auch noch an anderen Stellen verwendet als nur in dieser Klasse).
	//	public void sendMails() throws Exception {
	//		// if (zeroByteCount > 0 && fileZeroByteNotificationTo != null && fileZeroByteNotificationTo.length() > 0) {
	//		// sendMail(fileZeroByteNotificationTo, fileZeroByteNotificationCC, fileZeroByteNotificationBCC, fileZeroByteNotificationSubject,
	//		// fileZeroByteNotificationBody);
	//		// }
	//		//
	//		// if (count > 0 && fileNotificationTo != null && fileNotificationTo.length() > 0) {
	//		// sendMail(fileNotificationTo, fileNotificationCC, fileNotificationBCC, fileNotificationSubject, fileNotificationBody);
	//		// }
	//	}

	public int getZeroByteCount() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::getZeroByteCount";

		return lngNoOfZeroByteSizeFiles;

	} // private int getZeroByteCount

	/**
	 * Erst wenn alle Dateien erfolgreich transferieriert wurden, dann sollen die lokalen Dateien gel�scht werden.
	 * Parameter = objOptions.transactional.value() = yes und remove_files=yes
	 * @throws Exception
	 */
	public void DeleteSourceFiles() throws Exception {
		if (objOptions.remove_files.isTrue()) {
			boolean flgFilesDeleted = false;

			for (SOSFileListEntry objListItem : objFileListEntries) {
				if (objListItem.getTransferStatus() == enuTransferStatus.transferred) {
					if (flgFilesDeleted == false) {
						flgFilesDeleted = true;
						logger.debug(SOSVfs_D_208.get());
					}
					objListItem.DeleteSourceFile();
				}
			}
		}
	}

	public void createHashFileEentries (final String pstrHashTypeName) {
		Vector <String> objV = new Vector <String> ();
		for (SOSFileListEntry objEntry : objFileListEntries) {
			objV.add(objEntry.SourceFileName());
		}

		for (String string : objV) {
			this.add(string + "." + pstrHashTypeName).flgIsHashFile = true;
		}
	}
	/**
	 *
	 *
	 * \brief SetFile
	 *
	 * \details
	 * The content of the ResultSet File is a list of all Source-File Names.
	 *
	 * \return void
	 *
	 */
	public void CreateResultSetFile() {
		if (flgResultSetFileAlreadyCreated == true) {
			return;
		}
		flgResultSetFileAlreadyCreated = true;

		try {
			if (objOptions.CreateResultSet.isTrue()) {
				if (objOptions.ResultSetFileName.isDirty()) {
					JSFile objResultSetFile = new JSFile(objOptions.ResultSetFileName.Value());
					for (SOSFileListEntry objListItem : objFileListEntries) {
						String strSourceFileName = objListItem.getSourceFilename();
						objResultSetFile.WriteLine(strSourceFileName);
						lngNoOfRecordsInResultSetFile++;
					}

					objResultSetFile.close();
					logger.info(String.format("ResultSet File '%1$s' written with %2$d records", objResultSetFile.getAbsoluteFile(), lngNoOfRecordsInResultSetFile));

					objResultSetFile = new JSFile(objOptions.ResultSetFileName.Value() + ".csv");
					for (SOSFileListEntry objListItem : objFileListEntries) {
						String strLine = objListItem.toCsv();
						objResultSetFile.WriteLine(strLine);
					}

					System.out.println("*** ResultSet End ***");
					logger.info(String.format("ResultSet to StdOut and to csv-File '%1$s' written", objResultSetFile.getAbsoluteFile()));
				}

				System.out.println("*** ResultSet Start ***");
				for (SOSFileListEntry objListItem : objFileListEntries) {
					String strLine = objListItem.toCsv();
					System.out.println(strLine);
				}

				System.out.println("*** ResultSet End ***");
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException("Problems occured creating ResultSetFile", e);
		}
	}

	/**
	 * Erst bei Erfolgreichen transferieren aller Dateien, wird der atomic suffix umbennant
	 * Bedingung: Parameter objOptions.transactional.value() = yes
	 * @throws Exception
	 */

	public void renameAtomicTransferFiles() throws Exception {

		final String conMethodName = conClassName + "::renameAtomicTransferFiles";

		try {
			if (objOptions.isAtomicTransfer() && objOptions.transactional.isTrue()) {
				logger.debug(SOSVfs_D_209.get());

				for (SOSFileListEntry objListItem : objFileListEntries) {
					String strTargetTransferName = objListItem.TargetTransferName();
					String strToFilename = MakeFullPathName(objOptions.TargetDir.Value(), objListItem.TargetFileName());
					if (strToFilename.equalsIgnoreCase(strTargetTransferName) == false) { // SOSFTP-142
						ISOSVirtualFile objF = null;
						if (objOptions.overwrite_files.isTrue() && objListItem.FileExists() == true) {
							objF = objDataTargetClient.getFileHandle(strToFilename);
							objF.delete();
						}
						objF = objDataTargetClient.getFileHandle(MakeFullPathName(objOptions.TargetDir.Value(), strTargetTransferName));
						objF.rename(strToFilename);
						// if cumulative_files is true then this loop has to much entries
						if (objOptions.CumulateFiles.isTrue()) {
							break;
						}
					}
				}
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException(SOSVfs_E_210.params(conMethodName, e.getLocalizedMessage()), e);
		}
	}

	/**
	 *
	 * \brief EndTransaction
	 *
	 * \details
	 * This routine has to be called anyway. It belongs not to transactional mode.
	 *
	 * \return void
	 *
	 */
	public void EndTransaction() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::EndTransaction";

		this.checkSecurityHashFiles();
		dteTransactionEnd.setParsePattern(JSDateFormat.dfTIMESTAMPS);
		dteTransactionEnd.Value(Now());
		this.sendTransferHistory();
		this.WriteHistory();
		CreateResultSetFile();

	} // private void EndTransaction

	private void checkSecurityHashFiles () {

		if (objOptions.CheckSecurityHash.isTrue()) {
			for (SOSFileListEntry objItem : objFileListEntries) {
				if (objItem.flgIsHashFile == false) {
					String strTargetFileName = objItem.getTargetFileNameAndPath();
					ISOSVirtualFile objF = objItem.getDataTargetClient().getFileHandle(strTargetFileName + "." + objOptions.SecurityHashType.Value());
					try {
						if (objF.FileExists() == false) {
							logger.info(String.format("Hash file '%1$s' not found", strTargetFileName));
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					String strHash = objF.File2String();
					strHash = strHash.replaceAll("\\n", "");
					strHash = strHash.replaceAll("\\r", "");
					String strH = objItem.SecurityHash();
					if (strHash.equals(strH) == false) {
						logger.info(String.format("Security Hash violation. File %1$s, hash read '%2$s', hash calculated '%3$s'", strTargetFileName, strHash, strH));
					}
				}
			}
		}

	}
	/**
	 *
	 * \brief StartTransaction
	 *
	 * \details
	 *
	 * \return void
	 *
	 */
	public void StartTransaction() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::StartTransaction";

		dteTransactionStart.setParsePattern(JSDateFormat.dfTIMESTAMPS);
		dteTransactionStart.Value(Now());

	} // private void StartTransaction

	/**
	 *
	 * \brief sendTransferHistory
	 *
	 * \details
	 * Send the transfer history for all transferred files to the background service.
	 *
	 * \return void
	 *
	 */
	public void sendTransferHistory() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::sendTransferHistory";

		for (SOSFileListEntry objEntry : objFileListEntries) {
			if (objEntry.sendTransferHistory()) {
				lngNoOfTransferHistoryRecordsSent++;
			}
		}

		// TODO I18N
		logger.debug(String.format("%1$d transfer history records sent to background service", lngNoOfTransferHistoryRecordsSent));
	} // private void sendTransferHistory

	/**
	 *
	 * \brief Rollback
	 *
	 * \details
	 *
	 * \return void
	 *
	 */
	public void Rollback() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Rollback";
		logger.info(SOSVfs_I_211.get());
		objJadeReportLogger.info(SOSVfs_I_211.get());
		// TODO check out, on which stage the process was aborted. Example: 1) source2target, 2)
		// renameAtomic, 3) DeleteSource, 4) deleteOnly, 5) renameOnly

		// TODO l�schen der Dateien mit Atomic-Prefix und -Suffix auf dem Target
		if (objOptions.isAtomicTransfer()) {
			for (SOSFileListEntry objListItem : objFileListEntries) {
				String strAtomicFileName = objListItem.getAtomicFileName();
				if (isNotEmpty(strAtomicFileName)) {
					objDataTargetClient.getFileHandle(strAtomicFileName).delete();
					String strT = SOSVfs_D_212.params(strAtomicFileName);
					logger.debug(strT);
					objJadeReportLogger.info(strT);
					objListItem.setAtomicFileName(EMPTY_STRING);
					objListItem.setStatus(enuTransferStatus.setBack);
				}
			}
		}

		if (objOptions.transactional.isTrue()) {

		}
		this.EndTransaction();

		// TODO rules, decisions and coding

	} // private void Rollback

	/**
	 * Felder der Historiendatei
	 */
	private final String					historyFields							= "guid;mandator;transfer_timestamp;pid;ppid;operation;localhost;localhost_ip;local_user;remote_host;remote_host_ip;remote_user;protocol;port;local_dir;remote_dir;local_filename;remote_filename;file_size;md5;status;last_error_message;log_filename";

	/**
	 * neue Felder der Historiendatei. Der Aufbau ist wie folgt: historyFields;<history_entry_>;newHistoryFields
	 */
	private final String					newHistoryFields						= "jump_host;jump_host_ip;jump_port;jump_protocol;jump_user;modification_timestamp";


	public void WriteHistory() {

		if (flgHistoryFileAlreadyWritten == true) {
			return;
		}
		flgHistoryFileAlreadyWritten = true;

		dteTransactionEnd.FormatString(JSDateFormat.dfTIMESTAMPS);
		dteTransactionStart.FormatString(JSDateFormat.dfTIMESTAMPS);
		long lngDuration = dteTransactionEnd.getDateObject().getTime() - dteTransactionStart.getDateObject().getTime();
		String strWhat2Do = objOptions.operation.Value();
		String strT = SOSVfs_D_213.params(dteTransactionStart.FormattedValue(), dteTransactionEnd.FormattedValue(), lngDuration, strWhat2Do);
		logger.debug(strT);
		objJadeReportLogger.info(strT);

		String strHistoryFileName = objOptions.HistoryFileName.Value();
		if (objOptions.HistoryFileName.isDirty()) {
			JSFile objResultSetFile = new JSFile(strHistoryFileName);
			if (objResultSetFile.exists() == false) {
				try {
					objResultSetFile.WriteLine(historyFields + ";" + newHistoryFields);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (SOSFileListEntry objListItem : objFileListEntries) {

				// TODO  CSV or XML or Hibernate?

				String strLine = objListItem.toCsv();
				try {
					objResultSetFile.WriteLine(strLine);
					lngNoOfHistoryFileRecordsWritten++;
				}
				catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
			try {
				objResultSetFile.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (SOSFileListEntry objEntry : objFileListEntries) {
			if (objEntry.TargetFileName().isEmpty() == false) {
				strT = objEntry.toString();
				logger.trace(strT);
				objJadeReportLogger.info(strT);
			}
		}

		// TODO I18N
		logger.debug(String.format("%1$d records written to history file '%2$s'.", lngNoOfHistoryFileRecordsWritten, strHistoryFileName));
	}
}
