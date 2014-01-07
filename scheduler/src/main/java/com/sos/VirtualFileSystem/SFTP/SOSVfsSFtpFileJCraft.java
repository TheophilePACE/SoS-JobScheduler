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
package com.sos.VirtualFileSystem.SFTP;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.ChannelSftp;
import com.sos.VirtualFileSystem.common.SOSVfsTransferFileBaseClass;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * @author KB
 *
 */
@I18NResourceBundle(baseName = "SOSVirtualFileSystem", defaultLocale = "en")
public class SOSVfsSFtpFileJCraft extends SOSVfsTransferFileBaseClass {
	/**
	 *
	 * \brief SOSVfsSFtpFileJCraft
	 *
	 * \details
	 *
	 * @param pstrFileName
	 */
	public SOSVfsSFtpFileJCraft(final String pstrFileName) {
		super(pstrFileName);
	}

	/**
	 *
	 * \brief read
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param bteBuffer
	 * @return
	 */
	@Override
	public int read(final byte[] bteBuffer) {
		try {
			InputStream is = this.getFileInputStream();
			if (is == null) {
				throw new Exception(SOSVfs_E_177.get());
			}
			return is.read(bteBuffer);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_173.params("read", fileName));
			return 0;
		}
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

				int intTransferMode = ChannelSftp.OVERWRITE;
				if (flgModeAppend) {
					intTransferMode = ChannelSftp.APPEND;
				}
				else if (flgModeRestart ){
					intTransferMode = ChannelSftp.RESUME;
				}

				SOSVfsSFtpJCraft objJ = (SOSVfsSFtpJCraft) objVFSHandler;
				objInputStream = objJ.getClient().get(fileName, intTransferMode);
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
	 *
	 * \brief read
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param bteBuffer
	 * @param intOffset
	 * @param intLength
	 * @return
	 */
	@Override
	public int read(final byte[] bteBuffer, final int intOffset, final int intLength) {
		try {
			InputStream is = this.getFileInputStream();
			if (is == null) {
				throw new Exception(SOSVfs_E_177.get());
			}
			return is.read(bteBuffer, intOffset, intLength);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_173.params("read", fileName));
			return 0;
		}
	}

	/**
	 *
	 * \brief write
	 *
	 * \details
	 *
	 * \return
	 *
	 * @param bteBuffer
	 * @param intOffset
	 * @param intLength
	 */
	@Override
	public void write(final byte[] bteBuffer, final int intOffset, final int intLength) {
		try {

			OutputStream os = this.getFileOutputStream();
			if (os == null) {

				throw new Exception(SOSVfs_E_147.get());
			}
			os.write(bteBuffer, intOffset, intLength);
		}
		catch (Exception e) {
			RaiseException(e, SOSVfs_E_173.params("write", fileName));
		}
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
				fileName = super.AdjustRelativePathName(fileName);
				int intTransferMode = ChannelSftp.OVERWRITE;
				if (flgModeAppend) {
					intTransferMode = ChannelSftp.APPEND;
				}
				else if (flgModeRestart ){
					intTransferMode = ChannelSftp.RESUME;
				}

				SOSVfsSFtpJCraft objJ = (SOSVfsSFtpJCraft) objVFSHandler;
//				objOutputStream = objJ.getClient().put(fileName, intTransferMode);
				objOutputStream = objJ.getClient().put(fileName);

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

	@Override
	public long getModificationDateTime() {

		String dateTime = null;
		long mt = 0;

//		try {
//			SftpATTRS objAttr = objVFSHandler.  (fileName);
//			if (objAttr != null) {
//				mt = objAttr.getMTime();
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				dateTime = df.format(new Date(mt));
//			}
//		}
//		catch (SftpException e) {
//			// e.printStackTrace();
//		}
		return mt;
	}

	@Override
	public long setModificationDateTime(final long pdteDateTime) {

		// TODO Auto-generated method stub
		return 0;
	}

}
