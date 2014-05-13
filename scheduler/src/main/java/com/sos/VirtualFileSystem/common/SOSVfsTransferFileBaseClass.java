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
package com.sos.VirtualFileSystem.common;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.VirtualFileSystem.Interfaces.ISOSVfsFileTransfer;
import com.sos.VirtualFileSystem.Interfaces.ISOSVirtualFile;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * @author KB
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsTransferFileBaseClass extends SOSVfsCommonFile {
	private final Logger		logger		= Logger.getLogger(SOSVfsTransferFileBaseClass.class);
	protected String	fileName	= EMPTY_STRING;

	public SOSVfsTransferFileBaseClass() {
		super("SOSVirtualFileSystem");
	}

	public SOSVfsTransferFileBaseClass(final String pFileName) {
		this();
		String name = pFileName;
		if (objVFSHandler != null) {
			String currentDir = objVFSHandler.DoPWD();
			logDEBUG(SOSVfs_I_126.params(currentDir));
			if (name.startsWith("./") == true) {
				name = name.replace("./", currentDir + "/");
			}
		}
		fileName = adjustFileSeparator(name);
	}

	/**
	 * \brief FileExists
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean FileExists() {
		boolean flgResult = false;
		logDEBUG(SOSVfs_D_156.params(fileName));
		// TODO hier wird im aktuellen Verzeichnis gesucht. geht schief, wenn die datei im Subfolder ist
		// TODO Der Dateiname darf hier nur aus dem Namen der Datei bestehen. Ist die Datei in einem Subfolder, dann mu� der Subfolder
		// ebenfalls Namensbestandteil sein.
		// TODO im Moment kommt der Dateiname mal mit und mal ohne Pfadname hier an.
		// TODO Methoden bauen: GibDateiNameOhnePFad und GibDateiNameMitPfad
		if (1 == 1) {
			File fleF = new File(AdjustRelativePathName(fileName));
			String strP = fleF.getParent();
			if (strP == null) {
				strP = ".";
			}
			strP = ".";
			String strN = fleF.getName();
			if (objVFSHandler.getFileSize(fileName) >= 0) {
				flgResult = true;
			}
			/**
			 * inperformant. the approach with size is much more better and faster.
			 */
			// Vector<String> vecTargetFileNamesList = objVFSHandler.nList(strP);
			// flgResult = vecTargetFileNamesList.contains(strFileName);
			// if (flgResult == false) {
			// flgResult = vecTargetFileNamesList.contains(strN);
			// }
		}
		else {
			Vector<String> vecTargetFileNamesList = objVFSHandler.nList(".");
			String strCurrDir = objVFSHandler.DoPWD();
			logDEBUG(SOSVfs_I_126.params(strCurrDir));
			String strT = fileName;
			if (strT.startsWith(strCurrDir) == false) {
				strT = strCurrDir + "/" + fileName;
			}
			flgResult = vecTargetFileNamesList.contains(strT);
			if (flgResult == false) { // Evtl. Windows?
				flgResult = vecTargetFileNamesList.contains(strCurrDir + "\\" + fileName);
			}
		}
		logDEBUG(SOSVfs_D_157.params(flgResult, fileName));
		return flgResult;
	}

	/**
	 * \brief delete
	 *
	 * \details
	 *
	 * \return
	 *
	 */
	@Override
	public boolean delete() {
		try {
			objVFSHandler.delete(fileName);
		}
		catch (Exception e) {
			SOSVfs_E_158.get();
			RaiseException(e, SOSVfs_E_158.params("delete()", fileName));
		}
		return true;
	}

	/**
	 * \brief getFileAppendStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public OutputStream getFileAppendStream() {
		OutputStream objO = null;
		try {
			fileName = AdjustRelativePathName(fileName);
			objO = objVFSHandler.getAppendFileStream(fileName);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_158.params("getFileAppendStream()", fileName));
		}
		return objO;
	}

	/**
	 * \brief getFileInputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public InputStream getFileInputStream() {
		try {
			if (objInputStream == null) {
				fileName = AdjustRelativePathName(fileName);

				objInputStream = objVFSHandler.getInputStream(fileName);
				if (objInputStream == null) {
					objVFSHandler.openInputFile(fileName);
				}
			}
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_158.params("getFileInputStream()", fileName));
		}
		return objInputStream;
	}

	/**
	 * \brief getFileOutputStream
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public OutputStream getFileOutputStream() {
		try {
			if (objOutputStream == null) {
				fileName = AdjustRelativePathName(fileName);
//				int intTransferMode = ChannelSftp.OVERWRITE;
//				if (flgModeAppend) {
//					intTransferMode = ChannelSftp.APPEND;
//				}
//				else if (flgModeRestart ){
//					intTransferMode = ChannelSftp.RESUME;
//				}
//
//				SOSVfsSFtpJCraft objJ = (SOSVfsSFtpJCraft) objVFSHandler;
//				objOutputStream = objJ.getClient().put(fileName, intTransferMode);
//
				if (objOutputStream == null) {
					objVFSHandler.openOutputFile(fileName);
				}
			}
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_158.params("getFileOutputStream()", fileName));
		}
		return objOutputStream;
	}

	protected String AdjustRelativePathName(final String pstrPathName) {
		String strT = pstrPathName;

		if (pstrPathName.startsWith("./") || pstrPathName.startsWith(".\\")) {
			String strPath = objVFSHandler.DoPWD() + "/";
			strT = new File(pstrPathName).getName();
			strT = strT.replaceAll("\\\\", "/");
			strT = strPath + strT;
			logDEBUG(SOSVfs_D_159.params(pstrPathName, strT));
		}

		strT = strT.replaceAll("\\\\", "/");
		return strT;
	}

	/**
	 * \brief getFilePermissions
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer getFilePermissions() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * \brief getFileSize
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public long getFileSize() {
		@SuppressWarnings("unused")
		long lngFileSize = -1;
		try {
			lngFileSize = objVFSHandler.getFileSize(fileName);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("getFileSize()"));
		}
		return lngFileSize;
	}

	/**
	 * \brief getName
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return fileName;
	}

	/**
	 * \brief getParent
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public String getParentVfs() {
		return null;
	}

	/**
	 * \brief getParentFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public ISOSVirtualFile getParentVfsFile() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * \brief isDirectory
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isDirectory() {
		return objVFSHandler.isDirectory(fileName);
	}

	/**
	 * \brief isEmptyFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public boolean isEmptyFile() {
		return this.getFileSize() <= 0;
	}

	/**
	 * \brief notExists
	 *
	 * \details
	 *
	 * \return
	 *
	 * @return
	 */
	@Override
	public boolean notExists() {
		boolean flgResult = false;
		try {
			flgResult = this.FileExists() == false;
		}
		catch (Exception e) {
			e.printStackTrace();
			RaiseException(e, SOSVfs_E_134.params("notExists()"));
		}
		return flgResult;
	}

	/**
	 * \brief putFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param fleFile
	 * @throws Exception
	 */
	@Override
	public void putFile(final File fleFile) {
		notImplemented();
	}

	/**
	 * \brief putFile
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param strFileName
	 * @throws Exception
	 */
	@Override
	public void putFile(@SuppressWarnings("hiding") final String strFileName) {
		notImplemented();
	}

	/**
	 * \brief rename
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pstrNewFileName
	 */
	@Override
	public void rename(final String pstrNewFileName) {
		objVFSHandler.rename(fileName, pstrNewFileName);
	}

	/**
	 * \brief setFilePermissions
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pintNewPermission
	 * @throws Exception
	 */
	@Override
	public void setFilePermissions(final Integer pintNewPermission) {
		notImplemented();
	}

	/**
	 * \brief setHandler
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param pobjVFSHandler
	 */
	@Override
	public void setHandler(final ISOSVfsFileTransfer pobjVFSHandler) {
		// this.objVFSHandler = (SOSVfsFtp) pobjVFSHandler;
		objVFSHandler = pobjVFSHandler;
	}

	@Override
	public String getModificationTime() {
		String strT = "";
		try {
			strT = objVFSHandler.getModificationTime(fileName);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_134.params("getModificationTime()"));
		}
		return strT;
	}

	@Override
	public String MakeZIPFile(final String pstrZipFileNameExtension) {
		logINFO(SOSVfs_I_160.params("MakeZIPFile()"));
		return fileName;
	}

	@Override
	public void close() {

		this.closeInput();
		this.closeOutput();
	}

	@Override
	public void closeInput() {
		try {
			if (objInputStream != null) {
				objInputStream.close();
				objInputStream = null;
			}
		}
		catch (Exception ex) {
		}
	}

	@Override
	public void closeOutput() {
		try {
			if (objOutputStream != null) {
				objOutputStream.flush();
				objOutputStream.close();
				objOutputStream = null;
			}
		}
		catch (Exception ex) {
		}
	}

	@Override
	public void flush() {
		try {
			this.getFileOutputStream().flush();
		}
		catch (IOException e) {
			RaiseException(e, SOSVfs_E_134.params("flush()"));
		}
	}

	@Override
	public int read(final byte[] bteBuffer) {
		// wird �berschrieben
		return 0;
	}

	@Override
	public int read(final byte[] bteBuffer, final int intOffset, final int intLength) {
		// wird �berschrieben
		return 0;
	}

	@Override
	public void write(final byte[] bteBuffer, final int intOffset, final int intLength) {
		// wird �berschrieben
	}

	@Override
	public void write(final byte[] bteBuffer) {
		notImplemented();
		try {
			this.getFileOutputStream().write(bteBuffer);
		}
		catch (IOException e) {
			RaiseException(e, SOSVfs_E_134.params("write()"));
		}
	}

	@Override
	public void putFile(final ISOSVirtualFile pobjVirtualFile) throws Exception {
		// TODO Auto-generated method stub
		notImplemented();
	}

	/**
	 *
	 * \brief RaiseException
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param e
	 * @param msg
	 */
	protected void RaiseException(final Exception e, final String msg) {
		logger.error(msg);
		throw new JobSchedulerException(msg, e);
	}

	/**
	 *
	 * \brief RaiseException
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param msg
	 */
	protected void RaiseException(final String msg) {
		logger.error(msg);
		throw new JobSchedulerException(msg);
	}

	/**
	 *
	 * \brief getLogPrefix
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @return
	 */
	private String getLogPrefix() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
		String[] classNameArr = ste.getClassName().split("\\.");

		return "(" + classNameArr[classNameArr.length - 1] + "::" + ste.getMethodName() + ") ";
	}

	/**
	 *
	 * \brief logINFO
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param msg
	 */
	protected void logINFO(final Object msg) {
		logger.info(this.getLogPrefix() + msg);
	}

	/**
	 *
	 * \brief logDEBUG
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param msg
	 */
	protected void logDEBUG(final Object msg) {
		logger.debug(this.getLogPrefix() + msg);
	}

	/**
	 *
	 * \brief logWARN
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param msg
	 */
	protected void logWARN(final Object msg) {
		logger.warn(this.getLogPrefix() + msg);
	}

	/**
	 *
	 * \brief logERROR
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param msg
	 */
	protected void logERROR(final Object msg) {
		logger.error(this.getLogPrefix() + msg);
	}

	@Override
	public long setModificationDateTime(final long pdteDateTime) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getModificationDateTime() {
		// TODO Auto-generated method stub
		return 0;
	}
}
