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

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * This job checks for file existence of a file or several files of a directory.
 * It can be used standalone or as an order driven job.
 *
 * @author Uwe Risse <uwe.risse@sos-berlin.com>
 * @since  2006-12-08
 * @version $Id: JobSchedulerCanWrite.java 18220 2012-10-18 07:46:10Z kb $
*/
@I18NResourceBundle(baseName = "com.sos.scheduler.messages", defaultLocale = "en")
public class JobSchedulerCanWrite extends JobSchedulerFileOperationBase {
	private final String	conSVNVersion	= "$Id: JobSchedulerCanWrite.java 18220 2012-10-18 07:46:10Z kb $";
	private static final String conClassName = "JobSchedulerCanWrite";

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() {
		try {
			initialize(conSVNVersion);
			CheckMandatoryFile();
			//
			flgOperationWasSuccessful = SOSFileOperations.canWrite(file, fileSpec, isCaseInsensitive, objSOSLogger);
			//
			return setReturnResult(flgOperationWasSuccessful);
		}
		catch (Exception e) {
			try {
				e.printStackTrace(System.err);
				String strM = JSJ_F_0010.params( conClassName, e.getLocalizedMessage());
				logger.fatal(strM);
				throw new JobSchedulerException(strM, e);
			}
			catch (Exception x) {
			}
			return signalFailure();
		}
	}
}
