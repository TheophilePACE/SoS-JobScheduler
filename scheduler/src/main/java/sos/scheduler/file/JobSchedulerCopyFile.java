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
package sos.scheduler.file;
import static com.sos.scheduler.messages.JSMessages.JSJ_F_0010;
import static com.sos.scheduler.messages.JSMessages.JSJ_F_0011;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * This job copies a file or several files of a directory.
 * It can be used standalone or as an order driven job.
 *
 * @author Florian Schreiber <fs@sos-berlin.com>
 * @since  2006-11-01
 * @version $Id: JobSchedulerCopyFile.java 18220 2012-10-18 07:46:10Z kb $
*/
@I18NResourceBundle(baseName = "com_sos_scheduler_messages", defaultLocale = "en")
public class JobSchedulerCopyFile extends JobSchedulerFileOperationBase {
	private final String	conSVNVersion	= "$Id: JobSchedulerCopyFile.java 18220 2012-10-18 07:46:10Z kb $";
	private final String	conClassName	= this.getClass().getName();

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}
	@Override
	public boolean spooler_process() {
		try {
			initialize(conSVNVersion);
			CheckMandatorySource();
			// CheckMandatoryTarget(); // is not mandatory

			String[] fileSource = source.split(";");
			String[] fileTarget = null;
			if (isNotNull(target)) {
				fileTarget = target.split(";");
				if (fileSource.length != fileTarget.length) {
					String strM = JSJ_F_0011.params(fileSource.length, fileTarget.length);
					logger.fatal(strM);
					throw new JobSchedulerException(strM);
				}
			}

			String[] fileSpecs = fileSpec.split(";");
			boolean flgPathAndSpecHasSameNumberOfItems = fileSource.length == fileSpecs.length;
			fileSpec = fileSpecs[0];
			for (int i = 0; i < fileSource.length; i++) {
				String strSource = fileSource[i];
				String strTarget = null;
				if (isNotNull(target)) {
					strTarget = fileTarget[i];
				}
				if (isNotEmpty(fileSpec)) {
					if (flgPathAndSpecHasSameNumberOfItems == true) {
						fileSpec = fileSpecs[i];
					}
				}
				doFileOperation(strSource, strTarget);
			}

			flgOperationWasSuccessful = intNoOfHitsInResultSet > 0;
			return setReturnResult(flgOperationWasSuccessful);
		}
		catch (Exception e) {
			try {
				String strM = JSJ_F_0010.params(conClassName, e.getLocalizedMessage());
				logger.fatal(strM + "\n" + StackTrace2String(e));
				throw new JobSchedulerException(strM, e);
			}
			catch (Exception x) {
			}
			return signalFailure();
		}
	}

	private void doFileOperation(final String strSource, final String strTarget) throws Exception {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doFileOperation";

		intNoOfHitsInResultSet += SOSFileOperations.copyFileCnt(strSource, strTarget, fileSpec, flags, isCaseInsensitive, replacing, replacement, minFileAge,
				maxFileAge, minFileSize, maxFileSize, skipFirstFiles, skipLastFiles, objSOSLogger);
		saveResultList();

	} // private void doFileOperation

//	@I18NMessages(value = { @I18NMessage("number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort."), //
//			@I18NMessage(value = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort.", //
//			locale = "en_UK", //
//			explanation = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort." //
//			), //
//			@I18NMessage(value = "Die Anzahl Einzel-Elemente in der Quelle '%1$d' ist nicht identisch mit der Anzahl '%2$d' im Ziel. Abort.", //
//			locale = "de", //
//			explanation = "Die Anzahl der durch Semikolon getrennten Elemente in source und target mu� identisch sein." //
//			), //
//			@I18NMessage(value = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort.", locale = "es", //
//			explanation = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort." //
//			), //
//			@I18NMessage(value = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort.", locale = "fr", //
//			explanation = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort." //
//			), //
//			@I18NMessage(value = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort.", locale = "it", //
//			explanation = "number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort." //
//			) //
//	}, msgnum = "JSJ_F_0011", msgurl = "JSJ_F_0011")
///*!
// * \var JSJ_F_0011
// * \brief number of items in source '%1$d' is not equal to number of items in target '%2$d'. Abort.
// */
//	public static final String	JSJ_F_0011	= "JSJ_F_0011";

}
