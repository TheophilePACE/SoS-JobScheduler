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
package sos.scheduler.job;

import static com.sos.scheduler.messages.JSMessages.JFO_E_0016;
import static com.sos.scheduler.messages.JSMessages.JFO_I_0014;
import static com.sos.scheduler.messages.JSMessages.JFO_I_0015;
import static com.sos.scheduler.messages.JSMessages.JFO_I_0019;
import static com.sos.scheduler.messages.JSMessages.JFO_I_0020;
import static com.sos.scheduler.messages.JSMessages.JSJ_F_0010;

import java.io.File;
import java.util.Vector;

import sos.scheduler.file.JobSchedulerFileOperationBase;
import sos.util.SOSFile;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;
// "JFO_I_0014";  // File deleted: %1$s"
// "JFO_I_0020";
// "JFO_I_0019";
// "JFO_I_0015";   // %1$d %2$s files deleted
// "JFO_E_0016";

/**
 * @author andreas.pueschel@sos-berlin.com
 *
 *
 * cleanup temporary files created by other jobs
 * Paramters:
 * file_path Path for the files to remove
 * file_specification Regular expression as file specification
 * file_age Minimum file-age either milliseconds or hh:mm[:ss]
 * $Id: JobSchedulerCleanupFiles.java 18220 2012-10-18 07:46:10Z kb $
 */
@I18NResourceBundle(baseName = "com_sos_scheduler_messages", defaultLocale = "en")
public class JobSchedulerCleanupFiles extends JobSchedulerFileOperationBase {
	private final String		conSVNVersion	= "$Id: JobSchedulerCleanupFiles.java 18220 2012-10-18 07:46:10Z kb $";
	private final static String	conClassName	= "JobSchedulerCleanupFiles";

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() {

		initialize(conSVNVersion);

		try {
			if (isEmpty(filePath)) {
				filePath = conPropertyJAVA_IO_TMPDIR;  // JS-788
			}
			String[] filePaths = filePath.split(";");
			String tmpFileSpec = getParamValue(new String[] {conParameterFILE_SPEC, conParameterFILE_SPECIFICATION}, EMPTY_STRING);
			if (isEmpty(tmpFileSpec)) {
				fileSpec = "^(sos.*)";
			}
			if (lngFileAge <= 0) {
				lngFileAge = calculateFileAge(getParamValue(conParameterFILE_AGE, "24:00"));
			}

			String[] fileSpecs = fileSpec.split(";");
			boolean flgPathAndSpecHasSameNumberOfItems = filePaths.length == fileSpecs.length;
			fileSpec = fileSpecs[0];
			for (int i = 0; i < filePaths.length; i++) {
				int counter = 0;
				filePath = filePaths[i];
				if (filePath.trim().equalsIgnoreCase(conPropertyJAVA_IO_TMPDIR)) {
					filePath = System.getProperty(conPropertyJAVA_IO_TMPDIR);
				}
				if (flgPathAndSpecHasSameNumberOfItems == true) {
					fileSpec = fileSpecs[i];
				}

				logger.debug(JFO_I_0019.params(filePath));  // Looking for files in: %1$s
				Vector<File> filelist = SOSFile.getFolderlist(filePath, fileSpec, 0);
				if (filelist.size() == 0) {
					logger.info(JFO_I_0020.params(fileSpec));  // .. No files matched.
				}

				if (warningFileLimit > 0 && filelist.size() >= warningFileLimit) {  // '%1$s' files were found in directory '%2$s'. That is more than specified with param '%4$s' = '%3$d'.
					logger.error(JFO_E_0016.params(filelist.size(), filePath, warningFileLimit, conParameterWARNING_FILE_LIMIT));
				}

				for (File tempFile : filelist) {
					long interval = System.currentTimeMillis() - tempFile.lastModified();
					if (interval > lngFileAge) {
						counter += SOSFile.deleteFile(tempFile);
						logger.info(JFO_I_0014.params(tempFile.getAbsolutePath()));
					}
				} // for
				if (counter > 0) {
					String strT = filePath;
					logger.info(JFO_I_0015.params(counter, strT));
				}
			} // for

		}
		catch (Exception e) {
			throw new JobSchedulerException(JSJ_F_0010.params(conClassName, e.getLocalizedMessage()), e);
		}

		// return false;
		return signalSuccess();
	}
/*!
 * \var JSJ_I_0012
 * \brief "File deleted: %1$s"
 */

}
