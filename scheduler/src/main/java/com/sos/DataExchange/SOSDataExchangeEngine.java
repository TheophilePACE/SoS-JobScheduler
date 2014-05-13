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
package com.sos.DataExchange;

import static com.sos.DataExchange.SOSJadeMessageCodes.EXCEPTION_RAISED;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_D_0200;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_E_0100;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_E_0101;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_E_0200;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_I_0100;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_I_0101;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_I_0102;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_I_0104;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_I_0115;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_T_0010;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_T_0012;
import static com.sos.DataExchange.SOSJadeMessageCodes.SOSJADE_T_0013;
import static com.sos.DataExchange.SOSJadeMessageCodes.TRANSACTION_ABORTED;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sos.net.SOSMail;
import sos.net.mail.options.SOSSmtpMailOptions;
import sos.net.mail.options.SOSSmtpMailOptions.enuMailClasses;

import com.sos.JSHelper.Basics.JSVersionInfo;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionJadeOperation.enuJadeOperations;
import com.sos.JSHelper.concurrent.SOSThreadPoolExecutor;
import com.sos.JSHelper.io.Files.JSFile;
import com.sos.VirtualFileSystem.DataElements.SOSFileList;
import com.sos.VirtualFileSystem.DataElements.SOSFileListEntry;
import com.sos.VirtualFileSystem.FTP.SOSFTPOptions;
import com.sos.VirtualFileSystem.Factory.VFSFactory;
import com.sos.VirtualFileSystem.Interfaces.ISOSVFSHandler;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.VirtualFileSystem.Options.SOSConnection2OptionsAlternate;

public class SOSDataExchangeEngine extends JadeBaseEngine implements Runnable {
	private static final String	conKeyWordLAST_ERROR			= "last_error";
	private static final String	conKeywordSTATE					= "state";
	private static final String	conKeywordSUCCESSFUL_TRANSFERS	= "successful_transfers";
	private static final String	conKeywordFAILED_TRANSFERS		= "failed_transfers";
	private static final String	conKeywordSTATUS				= "status";
	private final String		conClassName					= "SOSDataExchangeEngine";
	public final String			conSVNVersion					= "$Id: SOSDataExchangeEngine.java 20719 2013-07-18 18:17:48Z kb $";

	private final Logger		logger							= Logger.getLogger(SOSDataExchangeEngine.class);
	final String				strLoggerName					= "JadeReportLog";
	private final Logger		objJadeReportLogger				= Logger.getLogger(strLoggerName);

	private ISOSVFSHandler		objVfs4Target					= null;
	private ISOSVFSHandler		objVfs4Source					= null;
	public ISOSVfsFileTransfer	objDataTargetClient				= null;
	public ISOSVfsFileTransfer	objDataSourceClient				= null;
	private SOSFileList			objSourceFileList				= null;
	/** notification by e-mail in case of transfer of empty files. */
	// private boolean sameConnection = false;
	@SuppressWarnings("unused")
	private final boolean		testmode						= false;

	public SOSDataExchangeEngine() throws Exception {
		init();
	}

	private void init() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";

		// logger is not yet initialized.
		//		logger.info(conClassName + " --- " + conSVNVersion);

	} // private void init

	/**
	 * @param settingsFile
	 * @param settingsSection
	 * @param logger
	 * @param arguments_
	 */
	public SOSDataExchangeEngine(final Properties pobjProperties) throws Exception {
		this.Options();
		// TODO Properties in die OptionsClasse weiterreichen
		// objOptions.setAllOptions(pobjProperties);
	}

	public SOSDataExchangeEngine(final SOSFTPOptions pobjOptions) throws Exception {
		super(pobjOptions);
		objOptions = pobjOptions;
		if (objOptions.settings.isDirty()) {
			objOptions.ReadSettingsFile();
		}
	}

	public SOSDataExchangeEngine(final HashMap<String, String> pobjJSSettings) throws Exception {
		this.Options();
		objOptions.setAllOptions(pobjJSSettings);
	}

	@SuppressWarnings("unused")
	private ISOSVFSHandler getVFS() throws Exception {
		if (objVfs4Target == null) {
			objVfs4Target = VFSFactory.getHandler(objOptions.getDataTargetType());
		}
		return objVfs4Target;
	}

	@Override
	public SOSFTPOptions Options() {
		if (objOptions == null) {
			objOptions = new SOSFTPOptions();
		}
		return objOptions;
	}

	@Override
	public void run() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Run";
		try {
			this.Execute();
		}
		catch (Exception e) {
			throw new JobSchedulerException(EXCEPTION_RAISED.get(e), e);
		}
	}

	public boolean Execute() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::execute";

		VFSFactory.setParentLogger(strLoggerName);
		if (objOptions.verbose.value() == 0) {
			Logger.getRootLogger().setLevel(Level.INFO);
		}
		else {
			Logger.getRootLogger().setLevel(Level.DEBUG);
		}

		String strV = conSVNVersion + " -- " + JSVersionInfo.getVersionString();
		logger.info(strV);

		objOptions.getTextProperties().put("version", strV);

		objOptions.log_filename.setLogger(objJadeReportLogger);

		if (objOptions.remote_dir.isDirty() == false) {
			objOptions.remote_dir.Value(objOptions.TargetDir.Value());
		}
		if (objOptions.local_dir.isDirty() == false) {
			objOptions.local_dir.Value(objOptions.SourceDir.Value());
		}

		if (objOptions.host.isDirty() == false) {
			objOptions.host.Value(objOptions.Target().host.Value());
		}
        logger.debug(objOptions.Target().host.Value());

		String strT = SOSJADE_T_0010.get(); // LogFile Header
		strT = objOptions.replaceVars(strT);
		objJadeReportLogger.info(strT + "");

		boolean flgOK = false;
		try {
			flgOK = this.transfer();
		}
		catch (Exception e) {
			throw new JobSchedulerException(e.getLocalizedMessage(), e);
		}
		finally {
			strT = SOSJadeMessageCodes.SOSJADE_T_0011.get(); // LogFile Footer
			setTextProperties();
			strT = objOptions.replaceVars(strT);
			objJadeReportLogger.info(strT);
			sendNotifications();
		}

		return flgOK;
	}

	private void sendNotifications() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::sendNotifications";

		// TODO Nagios anbinden
		// TODO Status �ber MQ senden
		// TODO Fehler an JIRA, Peregrine, etc. senden. Ticket aufmachen.
		SOSSmtpMailOptions objO = objOptions.getMailOptions();
		if (objOptions.mail_on_success.isTrue() && objSourceFileList.FailedTransfers() <= 0 || objO.FileNotificationTo.isDirty() == true) {
			doProcessMail(enuMailClasses.MailOnSuccess);
		}
		if (objOptions.mail_on_error.isTrue() && (objSourceFileList.FailedTransfers() > 0 || JobSchedulerException.LastErrorMessage.length() > 0)) {
			doProcessMail(enuMailClasses.MailOnError);
		}
		if (objOptions.mail_on_empty_files.isTrue() && objSourceFileList.getZeroByteCount() > 0) {
			doProcessMail(enuMailClasses.MailOnEmptyFiles);
		}
	}

	private void doProcessMail(final enuMailClasses penuMailClass) {

		SOSSmtpMailOptions objO = objOptions.getMailOptions();
		SOSSmtpMailOptions objMOS = objO.getOptions(penuMailClass);
		if (objMOS == null || objMOS.FileNotificationTo.isDirty() == false) {
			objMOS = objO;
		}
		processSendMail(objMOS);
	}

	private void processSendMail(final SOSSmtpMailOptions pobjO) {
		if (pobjO != null && pobjO.FileNotificationTo.isDirty() == true) {
			try {
				String strA = pobjO.attachment.Value();
				if (pobjO.attachment.isDirty()) {
					strA += ";";
				}
				if (objOptions.log_filename.isDirty() == true) {
					String strF = objOptions.log_filename.getHtmlLogFileName();
					if (strF.length() > 0) {
						strA += strF;
					}

					strF = objOptions.log_filename.Value();
					if (strF.length() > 0) {
						if (strA.length() > 0) {
							strA += ";";
						}
						strA += strF;
					}
					if (strA.length() > 0) {
						pobjO.attachment.Value(strA);
					}
				}

				if (pobjO.subject.isDirty() == false) {
					String strT = "JADE: ";
					pobjO.subject.Value(strT);
				}

				String strM = pobjO.subject.Value();
				pobjO.subject.Value(objOptions.replaceVars(strM));

				strM = pobjO.body.Value();
				pobjO.body.Value(objOptions.replaceVars(strM));

				strM += "\n" + "List of transferred Files:" + "\n";
				for (SOSFileListEntry objListItem : objSourceFileList.List()) {
					String strSourceFileName = objListItem.getSourceFilename();
					strM += strSourceFileName + "\n";
				}

				if (pobjO.from.isDirty() == false) {
					pobjO.from.Value("JADE@sos-berlin.com");
				}

				SOSMail objMail = new SOSMail(pobjO.host.Value());
				logger.debug(pobjO.dirtyString());
				objMail.sendMail(pobjO);

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	} // private void sendMail

	public ISOSVfsFileTransfer DataSourceClient() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::DataSourceClient";

		if (objDataSourceClient == null) {
			try {
				SOSConnection2OptionsAlternate objSourceConnect = objOptions.getConnectionOptions().Source();
				if (objSourceConnect.loadClassName.isDirty() == false) {
					objSourceConnect.loadClassName.Value(objOptions.getConnectionOptions().loadClassName.Value());
				}
				VFSFactory.setConnectionOptions(objSourceConnect);
				objVfs4Source = VFSFactory.getHandler(objOptions.getDataSourceType());
				objVfs4Source.Options(objOptions);
				try {
					objVfs4Source.Connect(objSourceConnect);
				}
				catch (Exception e) {  // Problem to connect, try alternate host
					// TODO respect alternate data-source type? alternate port etc. ?
					JobSchedulerException.LastErrorMessage = "";
					objVfs4Source.Connect(objOptions.getConnectionOptions().Source().Alternatives());
					objOptions.getConnectionOptions().Source().setAlternateOptionsUsed("true");
					// TODO get an instance of .Alternatives for Authentication ...
				}

				try {
					objVfs4Source.Authenticate(objSourceConnect);
				}
				catch (Exception e) { // SOSFTP-113: Problem to login, try alternate User
					// TODO respect alternate authentication, eg password and/or public key
					JobSchedulerException.LastErrorMessage = "";
					objVfs4Source.Authenticate(objOptions.getConnectionOptions().Source().Alternatives());
					objOptions.getConnectionOptions().Source().setAlternateOptionsUsed("true");
				}

				objVfs4Source.setSource();
				objVfs4Source.Options(objOptions);
			}
			catch (Exception e) {
				throw new JobSchedulerException(SOSJADE_E_0200.params(e), e);
			}
		}

		return objDataSourceClient;
	} // private ISOSVfsFileTransfer DataSourceClient

	/**
	 *
	 * \brief DataTargetClient
	 * Returns an instance of the TargetClient
	 *
	 * \details
	 *
	 * \return ISOSVfsFileTransfer
	 *
	 * @return
	 * @throws Exception
	 */
	public ISOSVfsFileTransfer DataTargetClient() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::DataTarget";
		if (objDataTargetClient == null) {
			objOptions.CheckMandatory();
			SOSConnection2OptionsAlternate objTargetConnectOptions = objOptions.getConnectionOptions().Target();
			if (objTargetConnectOptions.loadClassName.isDirty() == false) {
				objTargetConnectOptions.loadClassName.Value(objOptions.getConnectionOptions().loadClassName.Value());
			}

			VFSFactory.setConnectionOptions(objTargetConnectOptions);
			objVfs4Target = VFSFactory.getHandler(objOptions.getDataTargetType());
			objVfs4Target.Options(objOptions);

			try {
				objVfs4Target.Connect(objTargetConnectOptions);
			}
			catch (Exception e) {  // Problem to connect, try alternate host
				// TODO respect alternate data-source type? alternate port etc. ?
				JobSchedulerException.LastErrorMessage = "";
				objVfs4Target.Connect(objOptions.getConnectionOptions().Target().Alternatives());
				objOptions.getConnectionOptions().Target().setAlternateOptionsUsed("true");
				// TODO get an instance of .Alternatives for Authentication ...
			}

			try {
				objVfs4Target.Authenticate(objTargetConnectOptions);
			}
			catch (Exception e) {
				JobSchedulerException.LastErrorMessage = "";
				objVfs4Target.Authenticate(objOptions.getConnectionOptions().Target().Alternatives());
				objOptions.getConnectionOptions().Target().setAlternateOptionsUsed("true");
			}
			objDataTargetClient = (ISOSVfsFileTransfer) objVfs4Target;
			objVfs4Target.setTarget();
			objVfs4Target.Options(objOptions);
			objVfs4Target.doPostLoginOperations();
		}
		return objDataTargetClient;
	} // private ISOSVfsFileTransfer DataTarget

	/**
	 * Send files by  .... from source to a target
	 *
	 * @return boolean
	 * @throws Exception
	 */
	public boolean transfer() throws Exception {
		boolean flgReturnCode = false;
		try { // to connect, authenticate and execute commands
			logger.debug(Options().dirtyString());
			logger.debug("Source : " + Options().Source().dirtyString());
			logger.debug("Target : " + Options().Target().dirtyString());

			Options().CheckMandatory();
			setTextProperties();

			objSourceFileList = new SOSFileList(objVfs4Target);
			objSourceFileList.Options(objOptions);
			objSourceFileList.StartTransaction();
			DataSourceClient();
			if (objOptions.NeedTargetClient() == true) {
				DataTargetClient();
				objSourceFileList.objDataTargetClient = objDataTargetClient;
				if (objOptions.passive_mode.value()) {
					objDataTargetClient.passive();
				}
				objDataTargetClient.TransferMode(objOptions.transfer_mode);
				objDataTargetClient.ControlEncoding(objOptions.ControlEncoding.Value());
				if (objOptions.PreFtpCommands.IsNotEmpty()) {
					// TODO Command separator as option
					String[] strA = objOptions.PreFtpCommands.Value().split(";");
					for (String strCmd : strA) {
						strCmd = objOptions.replaceVars(strCmd);
						objDataTargetClient.getHandler().ExecuteCommand(strCmd);
					}
				}
				makeDirs();
			}

			try {
				objDataSourceClient = (ISOSVfsFileTransfer) objVfs4Source;
				objSourceFileList.objDataSourceClient = objDataSourceClient;
				if (objOptions.passive_mode.value()) {
					objDataSourceClient.passive();
				}

				objDataSourceClient.TransferMode(objOptions.transfer_mode);
				objDataSourceClient.ControlEncoding(objOptions.ControlEncoding.Value());

				long lngPollTimeOut = 0;
				String strPollTimeOut = "";

				if (objOptions.poll_timeout.isDirty()) {
					lngPollTimeOut = objOptions.poll_timeout.value() * 60; // convert to seconds, poll_timeout is defined in minutes
					strPollTimeOut = objOptions.poll_timeout.Value();
				}
				else {
					lngPollTimeOut = objOptions.PollingDuration.getTimeAsSeconds();
					strPollTimeOut = objOptions.PollingDuration.Value();
				}

				long lngCurrentPollingTime = 0;
				long lngPollInterval = objOptions.poll_interval.getTimeAsSeconds();
				long lngNoOfFilesFound = 0;
				String strSourceDir = objOptions.SourceDir.Value();
				String strRemoteDir = objOptions.TargetDir.Value();
				boolean flgRecurseFolders = objOptions.recursive.value();

				if (objOptions.OneOrMoreSingleFilesSpecified()) {
					objSourceFileList.add(getSingleFileNames(), strSourceDir);

					if (objOptions.isFilePollingEnabled() == true) {
						long lngCurrentNumberOfFilesFound = objSourceFileList.List().size();
						while (true) {
							lngNoOfFilesFound = 0;
							if (lngCurrentPollingTime > lngPollTimeOut) { // time exceeded ?
								logger.info(String.format("polling: time '%1$s' is over. polling terminated", strPollTimeOut));
								break;
							}

							for (SOSFileListEntry objItem : objSourceFileList.List()) {
								// long lngFileSize = objItem.getFileSize();
								if (objItem.SourceFileExists()) {
									lngNoOfFilesFound++;
									objItem.setParent(objSourceFileList); // to reinit the file-size
								}
							}
							if (lngNoOfFilesFound == lngCurrentNumberOfFilesFound) {
								break;
							}

							if (objOptions.poll_minfiles.value() > 0 && lngNoOfFilesFound >= objOptions.poll_minfiles.value()) {
								break;
							}

							String strM = String.format("file-polling: going to sleep for %1$d seconds. '%2$d' files found, waiting for '%3$d' files",
									lngPollInterval, lngNoOfFilesFound, lngCurrentNumberOfFilesFound);
							objJSJobUtilities.setStateText(strM);
							logger.info(String.format(strM));
							Thread.sleep(lngPollInterval * 1000); // wait some seconds
							lngCurrentPollingTime += lngPollInterval;
						}
					}
				}
				else {
					String strRegExp4FileNames = objOptions.file_spec.Value();
					objDataSourceClient.changeWorkingDirectory(strSourceDir);
					ISOSVirtualFile objLocFile = objDataSourceClient.getFileHandle(strSourceDir);
					String strM = "";
					if (objOptions.NeedTargetClient() == true) {
						strM = "source directory/file: " + strSourceDir + ", target directory: " + strRemoteDir + ", file regexp: " + strRegExp4FileNames;
					}
					else {
						strM = SOSJADE_D_0200.params(strSourceDir, strRegExp4FileNames);
					}
					logger.debug(strM);
					if (objLocFile.isDirectory() == true) {
						String[] strFileList = null;
						boolean flgDoBreak = false;
						strFileList = objDataSourceClient.getFilelist(strSourceDir, strRegExp4FileNames, 0, flgRecurseFolders);
						long lngCurrentNoOfFilesFound = strFileList.length;
						strM = String.format("%1$d files found for regexp '%2$s'.", lngCurrentNoOfFilesFound, strRegExp4FileNames);
						logger.info(strM);
						objJSJobUtilities.setStateText(strM);
						while (true) {
							if (objOptions.isFilePollingEnabled()) {
								if (lngCurrentPollingTime > lngPollTimeOut) { // time exceeded ?
									logger.info(String.format("file-polling: time '%1$s' is over. polling terminated", strPollTimeOut));
									break;
								}
								if (objOptions.poll_minfiles.isDirty() && lngCurrentNoOfFilesFound >= objOptions.poll_minfiles.value()) { // amount
									flgDoBreak = true;
								}

								lngNoOfFilesFound = lngCurrentNoOfFilesFound;
								strM = String.format("polling: going to sleep for %1$d seconds. regexp '%2$s'", lngPollInterval, strRegExp4FileNames);
								objJSJobUtilities.setStateText(strM);
								logger.info(String.format(strM));
								Thread.sleep(lngPollInterval * 1000); // wait some seconds
								lngCurrentPollingTime += lngPollInterval;

								strFileList = objDataSourceClient.getFilelist(strSourceDir, strRegExp4FileNames, 0, flgRecurseFolders);
								lngCurrentNoOfFilesFound = strFileList.length;
								strM = String.format("%1$d files found for regexp '%2$s'.", lngCurrentNoOfFilesFound, strRegExp4FileNames);
								logger.info(strM);

								if (lngNoOfFilesFound >= lngCurrentNoOfFilesFound && lngNoOfFilesFound != 0) { // no additional file found
									if (objOptions.WaitingForLateComers.value() == true) { // just wait a round for latecommers
										objOptions.WaitingForLateComers.setFalse();
										flgDoBreak = false; // no break
									}
									else {
										flgDoBreak = true;
									}
								}
								else {
									flgDoBreak = false;
								}

								if (flgDoBreak == true) {
									break;
								}
							}
							else {
								break;
							}
						} // while

						objJSJobUtilities.setStateText("");

						if (objOptions.MaxFiles.isDirty() == true && strFileList.length > objOptions.MaxFiles.value()) {
							for (int i = 0; i < objOptions.MaxFiles.value(); i++) {
								objSourceFileList.add(strFileList[i]);
							}
						}
						else {
							objSourceFileList.add(strFileList, strSourceDir);
						}
					}
					else { // not a directory, seems to be a filename
						objSourceFileList.add(strSourceDir);
					}
				}

				// TODO checkSteadyStateOfFiles in FileListEntry einbauen
				if (checkSteadyStateOfFiles() == false) {
				}

				if (objOptions.TransferZeroByteFiles() == false) {
					if (objOptions.TransferZeroByteFilesNo() == true) {
						if (objSourceFileList.List().size() > 0) {
							boolean flgTransferZeroByteFilesNo = false;
							for (SOSFileListEntry objEntry : objSourceFileList.List()) {
								if (objEntry.getFileSize() > 0) { // zero byte size file
									flgTransferZeroByteFilesNo = true;
								}
								else {
									objSourceFileList.lngNoOfZeroByteSizeFiles++;
								}
							}
							if (flgTransferZeroByteFilesNo == false) { // all files are zbs files
								throw new JobSchedulerException("All files have zero byte size, transfer aborted");
							}
						}
					}

					Vector<SOSFileListEntry> objClone = new Vector<SOSFileListEntry>();
					for (SOSFileListEntry objEntry : objSourceFileList.List()) { // just to avoid concurrent modification exception
						objClone.add(objEntry);
					}
					for (SOSFileListEntry objEntry : objClone) {
						if (objEntry.getFileSize() <= 0) { // zero byte size file
							if (objOptions.TransferZeroByteFilesStrict() == true) {
								throw new JobSchedulerException(String.format("zero byte size file detected: %1$s", objEntry.getSourceFilename()));
							}
							objEntry.setTransferSkipped();
							if (objOptions.remove_files.value()) {
								objEntry.DeleteSourceFile();
							}
							objSourceFileList.lngNoOfZeroByteSizeFiles++;
							objSourceFileList.List().remove(objEntry);
						}
						else {
							// TODO Datei (nicht mehr) da? Fehler ausl�sen, weil in Liste enthalten.
						}
					}
				} // (zeroByteFiles == false)

				try {
					if (objOptions.operation.value() == enuJadeOperations.getlist) {
						String strM = SOSJADE_I_0115.get();
						logger.info(strM);
						objJadeReportLogger.info(strM);
						objOptions.remove_files.value(false);
						objSourceFileList.CreateResultSetFile();
					}
					else {
						if (objOptions.CreateSecurityHashFile.isTrue() || objOptions.CheckSecurityHash.isTrue()) {
							objSourceFileList.createHashFileEentries(objOptions.SecurityHashType.Value());
						}

						sendFiles(objSourceFileList);
					}

					if (objOptions.PostTransferCommands.IsNotEmpty()) {
						// TODO Command separator as option
						String[] strA = objOptions.PostTransferCommands.Value().split(";");
						for (String strCmd : strA) {
							strCmd = objOptions.replaceVars(strCmd);
							objDataTargetClient.getHandler().ExecuteCommand(strCmd);
						}
					}

					objDataSourceClient.close();
					objSourceFileList.renameAtomicTransferFiles();
					objSourceFileList.DeleteSourceFiles();

					if (objOptions.TransactionMode.isTrue()) {
						objSourceFileList.EndTransaction();
					}

					if (objOptions.NeedTargetClient() == true) {
						objDataTargetClient.close();
					}
				}
				catch (JobSchedulerException e) {
					String strM = TRANSACTION_ABORTED.get(e);
					logger.error(strM, e);
					objJadeReportLogger.info(strM);
					objSourceFileList.Rollback();
					throw e;
				}
				finally {
				}

				flgReturnCode = printState(flgReturnCode);
				return flgReturnCode;
			}
			catch (Exception e) {
				//				setTextProperties();
				//				objOptions.getTextProperties().put(conKeywordSTATUS, SOSJADE_T_0013.get());
				flgReturnCode = false;
				//				String strM = SOSJADE_E_0101.get(e.getLocalizedMessage());
				//				objJadeReportLogger.info(strM, e);
				throw e;
			}
			finally {
				setTextProperties();
			}
		}
		catch (Exception e) {
			setTextProperties();
			objOptions.getTextProperties().put(conKeywordSTATUS, SOSJADE_T_0013.get());
			String strM = SOSJADE_E_0101.params(e.getLocalizedMessage());
			objJadeReportLogger.info(strM, e);
			throw new JobSchedulerException(strM, e);
		}
	}

	// TODO in die DataSource verlagern? Oder in die FileList? Multithreaded ausf�hren?
	public boolean checkSteadyStateOfFiles() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::checkSteadyStateOfFiles";

		boolean flgAllFilesAreSteady = true;

		if (objOptions.CheckSteadyStateOfFiles.isTrue() && objSourceFileList != null) {
			long lngCheckSteadyStateInterval = objOptions.CheckSteadyStateInterval.getTimeAsSeconds() * 1000;
			long lngSteadyCount = objOptions.CheckSteadyCount.value();

			String strM = "checking file(s) for steady state";
			logger.debug(strM);
			setStateText(strM);
			for (int i = 0; i < lngSteadyCount; i++) {
				flgAllFilesAreSteady = true;
				for (SOSFileListEntry objFile : objSourceFileList.List()) {
					if (objFile.isSteady() == false) {
						long lastFileLength = objDataSourceClient.getFileHandle(objFile.SourceFileName()).getFileSize();
						try {
							Thread.sleep(lngCheckSteadyStateInterval);
							logger.debug(String.format("waiting %1$d for steady check", lngCheckSteadyStateInterval));
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						long lngActFileLength = objDataSourceClient.getFileHandle(objFile.SourceFileName()).getFileSize();
						logger.debug(String.format("Last file length %1$d, actual file length %2$d", lastFileLength, lngActFileLength));
						if (lastFileLength != lngActFileLength) {
							flgAllFilesAreSteady = false;
							logger.info(String.format("File '%1$s' changed during checking steady state", objFile.SourceFileName()));
							objFile.setSteady(false);
						}
						else {
							objFile.setSteady(true);
							logger.info(String.format("File '%1$s' was not changed during checking steady state", objFile.SourceFileName()));
						}
						objFile.setParent(objSourceFileList); // this is changing the filesize info in the object
					}
				} // For

				if (flgAllFilesAreSteady == false) {
					try {
						Thread.sleep(lngCheckSteadyStateInterval);
						logger.debug(String.format("waiting %1$d for steady check", lngCheckSteadyStateInterval));
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					break;
				}
			}

			if (flgAllFilesAreSteady == false) {
				strM = "not all files are steady";
				logger.error(strM);
				for (SOSFileListEntry objFile : objSourceFileList.List()) {
					if (objFile.isSteady() == false) {
						logger.info(String.format("File '%1$s' is not steady", objFile.SourceFileName()));
					}
				}
				throw new JobSchedulerException(strM);
			}
		}
		return flgAllFilesAreSteady;
	} // private boolean checkSteadyStateOfFiles

	private void setTextProperties() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setTextProperties";

		// TODO das mu� beim JobScheduler-Job in die Parameter zur�ck, aber nur da
		// siehe hierzu das Interface ...

		if (objSourceFileList != null) {
			objOptions.getTextProperties().put(conKeywordSUCCESSFUL_TRANSFERS, String.valueOf(objSourceFileList.SuccessfulTransfers()));
			objOptions.getTextProperties().put(conKeywordFAILED_TRANSFERS, String.valueOf(objSourceFileList.FailedTransfers()));
		}
		// return the number of transferred files
		if (JobSchedulerException.LastErrorMessage.length() <= 0) {
			objOptions.getTextProperties().put(conKeywordSTATUS, SOSJADE_T_0012.get());
		}
		else {
			objOptions.getTextProperties().put(conKeywordSTATUS, SOSJADE_T_0013.get());
		}
		objOptions.getTextProperties().put(conKeyWordLAST_ERROR, JobSchedulerException.LastErrorMessage);
	} // private void setTextProperties

	/**
	 *
	 * \brief Logout
	 *
	 * \details
	 *
	 * \return void
	 *
	 */
	public void Logout() {
		try {
			doLogout(objDataTargetClient);
			doLogout(objDataSourceClient);
		}
		catch (Exception e) {
		}
	}

	private void doLogout(ISOSVfsFileTransfer pobjClient) throws Exception {
		if (pobjClient != null) {
			pobjClient.logout();
			pobjClient.disconnect();
			pobjClient.close();
			pobjClient = null;
		}
	}

	/**
	 * Verzeichnis generieren
	 * @throws Exception
	 */
	private void makeDirs() {
		makeDirs(objOptions.TargetDir.Value());
	} // private void makeDirs()

	private boolean makeDirs(final String pstrPath) {
		boolean cd = true;
		try {
			boolean flgMakeDirs = objOptions.makeDirs.value();
			String strTargetDir = pstrPath;
			if (flgMakeDirs) {
				if (objDataTargetClient.changeWorkingDirectory(strTargetDir)) {
					cd = true;
				}
				else {
					objDataTargetClient.mkdir(strTargetDir);
					cd = objDataTargetClient.changeWorkingDirectory(strTargetDir);
				}
			}
			else {
				if (strTargetDir != null && strTargetDir.length() > 0) {
					cd = objDataTargetClient.changeWorkingDirectory(strTargetDir);
				}
			}
			// TODO alternative_remote_dir, wozu und wie gehen wir damit um?
			if (cd == false && objOptions.alternative_remote_dir.IsNotEmpty()) {// alternative Parameter
				String alternativeRemoteDir = objOptions.alternative_remote_dir.Value();
				logger.debug("..try with alternative parameter [remoteDir=" + alternativeRemoteDir + "]");
				cd = objDataTargetClient.changeWorkingDirectory(alternativeRemoteDir);
				objOptions.TargetDir.Value(alternativeRemoteDir);
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException("..error in makeDirs: " + e, e);
		}
		return cd;
	}

	// TODO pr�fen, ob eine Verlagerung in SOSFileList die bessere L�sung ist. Stichwort: Multithreading
	private void sendFiles(final SOSFileList pobjFileList) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::sendFiles";
		int intMaxParallelTransfers = 0;
		if (objOptions.ConcurrentTransfer.isTrue()) {
			intMaxParallelTransfers = objOptions.MaxConcurrentTransfers.value();
		}
		//		if (intMaxParallelTransfers <= 0 || objOptions.SendTransferHistory.value() == true) {
		//			intMaxParallelTransfers = 1;
		//		}

		int intNoOfFiles2Transfer = pobjFileList.List().size();
		int intFileCount = 0;

		if (intMaxParallelTransfers <= 0 || objOptions.CumulateFiles.isTrue()) {
			for (SOSFileListEntry objSourceFile : pobjFileList.List()) {
				objSourceFile.Options(objOptions);
				intFileCount++;
				String strMsg = String.format("Start Transfer '%1$d' of '%2$d' files: %3$s", intFileCount, intNoOfFiles2Transfer,
						objSourceFile.getSourceFilename());
				objJSJobUtilities.setStateText(strMsg);
				objSourceFile.run();
			}
		}
		else {

			SOSThreadPoolExecutor objTPE = new SOSThreadPoolExecutor(intMaxParallelTransfers);

			for (SOSFileListEntry objSourceFile : pobjFileList.List()) {
				objSourceFile.Options(objOptions);
				objSourceFile.setDataSourceClient(null); // force a new connection
				objSourceFile.setDataTargetClient(null);
				intFileCount++;
				String strMsg = String.format("Start Transfer '%1$d' of '%2$d' files: %3$s", intFileCount, intNoOfFiles2Transfer,
						objSourceFile.getSourceFilename());
				objJSJobUtilities.setStateText(strMsg);
				objTPE.runTask(objSourceFile);
			}

			try {
				objTPE.shutDown();
				objTPE.objThreadPool.awaitTermination(1, TimeUnit.DAYS);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

			//			Vector<Thread> objThreads = new Vector<Thread>();
			//
			//			int intThreadsRunning = 0;
			//			for (SOSFileListEntry objListItem : pobjFileList.List()) {
			//				objListItem.Options(objOptions);
			//				objListItem.setDataSourceClient(null); // force a new connection
			//				objListItem.setDataTargetClient(null);
			//				Thread objT = new Thread(objListItem);
			//				objT.start();
			//				intFileCount++;
			//				String strMsg = String.format("Start Transfer '%1$d' of '%2$d' files: %3$s", intFileCount, intNoOfFiles2Transfer,
			//						objListItem.getSourceFilename());
			//				objJSJobUtilities.setStateText(strMsg);
			//				synchronized (objThreads) {
			//					objThreads.add(objT);
			//				}
			//				boolean flgThreadsActive = true;
			//				intThreadsRunning = 0;
			//				while (flgThreadsActive) {
			//					flgThreadsActive = false;
			//					synchronized (objThreads) {
			//						for (Thread thread : objThreads) {
			//							if (thread.isAlive()) {
			//								flgThreadsActive = true;
			//								intThreadsRunning++;
			//							}
			//						}
			//					}
			//					try {
			//						if (flgThreadsActive == true && intThreadsRunning >= intMaxParallelTransfers) {
			//							Thread.sleep(500); // 
			//						}
			//						if (intThreadsRunning < intMaxParallelTransfers) {
			//							break;
			//						}
			//					}
			//					catch (InterruptedException e) {
			//						throw new JobSchedulerException("thread processing interrupted", e);
			//					}
			//					catch (Exception e) {
			//						throw new JobSchedulerException("thread processing interrupted", e);
			//					}
			//				}
			//			}
		}

		if (objOptions.transactional.isFalse()) {
			pobjFileList.EndTransaction();
		}

		//		Vector<Thread> objThreads = new Vector<Thread>();
		//		int intNoOfFiles2Transfer = pobjFileList.List().size();
		//		int intFileCount = 0;
		//		try {
		//			/**
		//			 * if "sendTransferHistory" is activated due to not thread-safe xml-parser (used by JAXB)
		//			 * the parallel transfer is omitted.
		//			 */
		//			if (intMaxParallelTransfers <= 0 || objOptions.SendTransferHistory.value() == true) {
		//				for (SOSFileListEntry objSourceFile : pobjFileList.List()) {
		//					objSourceFile.Options(objOptions);
		//					intFileCount++;
		//					String strMsg = String.format("Start Transfer '%1$d' of '%2$d' files: %3$s", intFileCount, intNoOfFiles2Transfer,
		//							objSourceFile.getSourceFilename());
		//					objJSJobUtilities.setStateText(strMsg);
		//					objSourceFile.run();
		//				}
		//			}
		//			else {
		//				int intThreadsRunning = 0;
		//				for (SOSFileListEntry objListItem : pobjFileList.List()) {
		//					objListItem.Options(objOptions);
		//					objListItem.setDataSourceClient(null); // force a new connection
		//					objListItem.setDataTargetClient(null);
		//					Thread objT = new Thread(objListItem);
		//					objT.start();
		//					intFileCount++;
		//					String strMsg = String.format("Start Transfer '%1$d' of '%2$d' files: %3$s", intFileCount, intNoOfFiles2Transfer,
		//							objListItem.getSourceFilename());
		//					objJSJobUtilities.setStateText(strMsg);
		//					synchronized (objThreads) {
		//						objThreads.add(objT);
		//					}
		//					boolean flgThreadsActive = true;
		//					intThreadsRunning = 0;
		//					while (flgThreadsActive) {
		//						flgThreadsActive = false;
		//						synchronized (objThreads) {
		//							for (Thread thread : objThreads) {
		//								if (thread.isAlive()) {
		//									flgThreadsActive = true;
		//									intThreadsRunning++;
		//								}
		//							}
		//						}
		//						try {
		//							if (flgThreadsActive == true && intThreadsRunning >= intMaxParallelTransfers) {
		//								Thread.sleep(500); // TODO this should be an option
		//							}
		//							if (intThreadsRunning < intMaxParallelTransfers) {
		//								break;
		//							}
		//						}
		//						catch (InterruptedException e) {
		//							throw new JobSchedulerException("thread processing interrupted", e);
		//						}
		//						catch (Exception e) {
		//							throw new JobSchedulerException("thread processing interrupted", e);
		//						}
		//					}
		//				}
		//			}
		//		}
		//		catch (JobSchedulerException e) {
		//			throw e;
		//		}
	}

	/**
	 * Statt ein Verzeichnis k�nnen ein oder mehrere - mit semikolen getrennt - dateien zum transferieren angegeben werden
	 * @return
	 */
	private String[] getSingleFileNames() {
		final String conMethodName = conClassName + "::getSingleFileNames";
		Vector<String> filelist = new Vector<String>();

		if (objOptions.file_path.IsNotEmpty() == true) {
			String filePath = objOptions.file_path.Value();
			logger.debug(String.format("single file(s) specified : '%1$s'", filePath));
			try {
				String localDir = AddFileSeparator(objOptions.SourceDir.Value().trim());
				// TODO separator-char variable as Option
				String[] strSingleFileNameArray = filePath.split(";");
				for (String strSingleFileName : strSingleFileNameArray) {
					strSingleFileName = strSingleFileName.trim();
					if (strSingleFileName.length() > 0) {
						if (localDir.trim().length() > 0) {
							if (isAPathName(strSingleFileName) == false) {
								/**
								 * Problem with folders, when pgs run on Windows, but has to
								 * create a path for unix-systems.
								 */
								// strT = new File(localDir, strT).getAbsolutePath();
								strSingleFileName = localDir + strSingleFileName;
							}
						}
						filelist.add(strSingleFileName);
					}
				}
			}
			catch (Exception e) {
				String strM = String.format("error in  %1$s", conMethodName);
				logger.error(strM + e);
				throw new JobSchedulerException(strM, e);
			}
		}

		if (objOptions.FileListName.IsNotEmpty() == true) {
			String strFileListName = objOptions.FileListName.Value();
			JSFile objF = new JSFile(strFileListName);
			if (objF.exists() == true) {
				// TODO create method in JSFile: File2Array
				StringBuffer strRec = null;
				while ((strRec = objF.GetLine()) != null) {
					filelist.add(strRec.toString());
				}
				try {
					objF.close();
				}
				catch (IOException e) {
				}
			}
		}

		String[] strA = { "" };
		if (filelist.size() > 0) {
			strA = filelist.toArray(new String[filelist.size()]);
		}
		return strA;
	}

	protected boolean isAPathName(final String pstrFileAndPathName) {
		boolean flgOK = false;
		String lstrPathName = pstrFileAndPathName.replaceAll("\\\\", "/");
		if (lstrPathName.startsWith(".") || lstrPathName.startsWith("..")) { // relative to localdir
			flgOK = false;
		}
		else {
			if (lstrPathName.contains(":/") || lstrPathName.startsWith("/")) {
				flgOK = true;
			}
			else {
				// flgOK = (lstrPathName.contains("/") == true);
			}
		}
		return flgOK;
	}

	private String AddFileSeparator(final String pstrPathName) {
		String strT = pstrPathName;
		int intL = strT.length();
		if (intL > 0) {
			String strL = strT.substring(intL - 1, intL);
			if (strL.equals("\\") || strL.equals("/")) {
			}
			else {
				strT += "/";
			}
		}
		return strT;
	}

	/**
	 * Zustand ausgeben. Erst ab log_level 1
	 * @param rc
	 * @return
	 * @throws Exception
	 */
	private boolean printState(boolean rc) throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::printState";
		String state = "processing successful ended";

		try {
			int intNoOfFilesTransferred = (int) objSourceFileList.SuccessfulTransfers();
			String strMsg = SOSJADE_E_0100.params(objOptions.file_spec.Value());
			int zeroByteCount = objSourceFileList.getZeroByteCount();
			switch (intNoOfFilesTransferred) {
				case 0:
					if (zeroByteCount > 0 && objOptions.TransferZeroByteFilesRelaxed()) {
						state = strMsg;
					}
					else
						if (zeroByteCount > 0 && objOptions.TransferZeroByteFilesRelaxed()) {
							throw new JobSchedulerException(SOSJADE_I_0104.get());
						}
						else {
							if (objOptions.force_files.value() == true) {
								if (objSourceFileList.List().size() <= 0) {
									objJadeReportLogger.info(strMsg);
									throw new JobSchedulerException(strMsg);
								}
							}
							else {
								state = strMsg;
							}
						}
					rc = objOptions.force_files.value() == false ? true : !objOptions.TransferZeroByteFilesRelaxed();
					break;
				case 1:
					state = SOSJADE_I_0100.get();
					rc = true;
					break;
				default:
					state = SOSJADE_I_0101.params(intNoOfFilesTransferred);
					rc = true;
					break;
			}
			if (zeroByteCount > 0) {
				state += " " + SOSJADE_I_0102.params(zeroByteCount);
			}
			logger.debug(state);
			objJadeReportLogger.info(state);
			objOptions.getTextProperties().put(conKeywordSTATE, state);
			return rc;
		}
		catch (Exception e) {
			objJadeReportLogger.info(e.getLocalizedMessage());
			throw e;
		}
		finally {
			objOptions.getTextProperties().put(conKeywordSTATE, state);
		}
	}

	/**
	 * @return the state
	 */
	public String getState() {
		String state = (String) objOptions.getTextProperties().get(conKeywordSTATE);
		return state;
	}

	public SOSFileList getFileList() {
		return objSourceFileList;
	}
}
