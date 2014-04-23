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


package com.sos.dailyschedule.job;

import java.io.File;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.dailyschedule.db.DailyScheduleAdjustment;

/**
 * \class 		CheckDailySchedule - Workerclass for "Checking a DailySchedule with runs in History"
 *
 * \brief AdapterClass of CheckDailySchedule for the SOSJobScheduler
 *
 * This Class CheckDailySchedule is the worker-class.
 *

 *
 * see \see C:\Dokumente und Einstellungen\Uwe Risse\Lokale Einstellungen\Temp\scheduler_editor-3456357879351999228.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Dokumente und Einstellungen\Uwe Risse\Eigene Dateien\sos-berlin.com\jobscheduler.1.3.9\scheduler_139\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20111212115623
 * \endverbatim
 */
public class CheckDailySchedule extends JSJobUtilitiesClass <CheckDailyScheduleOptions> {
	private final String					conClassName						= "CheckDailySchedule";  //$NON-NLS-1$
	private static Logger		logger			= Logger.getLogger(CheckDailySchedule.class);

//	protected CheckDailyScheduleOptions	objOptions			= null;
//    private JSJobUtilities      objJSJobUtilities   = this;


	/**
	 *
	 * \brief CheckDailySchedule
	 *
	 * \details
	 *
	 */
	public CheckDailySchedule() {
		super(new CheckDailyScheduleOptions());
	}

	/**
	 *
	 * \brief Options - returns the CheckDailyScheduleOptionClass
	 *
	 * \details
	 * The CheckDailyScheduleOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return CheckDailyScheduleOptions
	 *
	 */
	@Override
	public CheckDailyScheduleOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options";  //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new CheckDailyScheduleOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of CheckDailySchedule
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see CheckDailyScheduleMain
	 *
	 * \return CheckDailySchedule
	 *
	 * @return
	 */
	public CheckDailySchedule Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute";  //$NON-NLS-1$

		//logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName ) );

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());
			DailyScheduleAdjustment dailyScheduleAdjustment = new DailyScheduleAdjustment(new File(objOptions.configuration_file.Value()));
			dailyScheduleAdjustment.setOptions(objOptions);
 			dailyScheduleAdjustment.adjustWithHistory();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Exception (e);
			//logger.error(String.format(Messages.getMsg("JSJ-I-107"), conMethodName ), e);
		}
		finally {

		}

		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";  //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

}  // class CheckDailySchedule
