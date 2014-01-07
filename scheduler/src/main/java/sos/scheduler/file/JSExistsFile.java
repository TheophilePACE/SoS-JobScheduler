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

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sos.i18n.annotation.I18NResourceBundle;

/**
 * \class 		JSExistsFile - Workerclass for "check wether a file exist"
 *
 * \brief AdapterClass of JSExistFile for the SOSJobScheduler
 *
 * This Class JSExistsFile is the worker-class.
 *

 *
 * see \see C:\Users\KB\Documents\xmltest\JSExistFile.xml for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20110820120954 
 * \endverbatim
 */
@I18NResourceBundle(baseName = "com_sos_scheduler_messages", defaultLocale = "en")
public class JSExistsFile extends JSFileOperationBase {
	@SuppressWarnings("hiding")
	private final String	conClassName	= "JSExistsFile";														
	@SuppressWarnings("hiding")
	private static Logger	logger			= Logger.getLogger(JSExistsFile.class);
	private final String	conSVNVersion	= "$Id: JSExistsFile.java 17792 2012-08-06 19:16:28Z kb $";

	/**
	 * 
	 * \brief JSExistsFile
	 *
	 * \details
	 *
	 */
	public JSExistsFile() {
		super();
	}

	/**
	 * 
	 * \brief Execute - Start the Execution of JSExistsFile
	 * 
	 * \details
	 * 
	 * For more details see
	 * 
	 * \see JobSchedulerAdapterClass 
	 * \see JSExistsFileMain
	 * 
	 * \return JSExistsFile
	 *
	 * @return
	 */
	public boolean Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; 

		logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName));

		try {
			logger.info(conSVNVersion);
			initialize();
			Options().file.CheckMandatory();
			Options().file_spec.setRegExpFlags(Pattern.CASE_INSENSITIVE);

			flgOperationWasSuccessful = existsFile(Options().file, Options().file_spec, //
					Options().min_file_age, Options().max_file_age, Options().min_file_size, Options().max_file_size, //
					Options().skip_first_files, //
					Options().skip_last_files, -1, -1);

			flgOperationWasSuccessful = createResultListParam(flgOperationWasSuccessful);
			return flgOperationWasSuccessful;

//			if (flgOperationWasSuccessful == true) {
//				flgOperationWasSuccessful = checkSteadyStateOfFiles();
//			}
//			return setReturnResult(flgOperationWasSuccessful);

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error(Messages.getMsg("JSJ-I-107", conMethodName), e);
		}
		finally {
			if(flgOperationWasSuccessful) {
				logger.debug(Messages.getMsg("JSJ-I-111", conMethodName));
			}
		}

		return flgOperationWasSuccessful;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; 
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

} // class JSExistsFile
