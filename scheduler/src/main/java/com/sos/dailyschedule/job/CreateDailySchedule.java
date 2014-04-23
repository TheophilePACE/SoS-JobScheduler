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

import com.sos.JSHelper.Basics.IJSCommands;
import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.dailyschedule.db.Calendar2DB;

/**
 * \class 		CreateDailySchedule - Workerclass for "Creating a DaysSchedule depending on actual Runtimes"
 *
 * \brief AdapterClass of CreateDailySchedule for the SOSJobScheduler
 *
 * This Class CreateDailySchedule is the worker-class.
 *

 *
 * see \see C:\Dokumente und Einstellungen\Uwe Risse\Lokale Einstellungen\Temp\scheduler_editor-2235912449518755069.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Dokumente und Einstellungen\Uwe Risse\Eigene Dateien\sos-berlin.com\jobscheduler.1.3.9\scheduler_139\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20111027105329
 * \endverbatim
 */
public class CreateDailySchedule extends JSJobUtilitiesClass <CreateDailyScheduleOptions> implements IJSCommands {
	private final String					conClassName		= "CreateDailySchedule";						//$NON-NLS-1$
	private static Logger					logger				= Logger.getLogger(CreateDailySchedule.class);

//	protected CreateDailyScheduleOptions	objOptions			= null;
//	private JSJobUtilities					objJSJobUtilities	= this;

	/**
	 *
	 * \brief CreateDaysSchedule
	 *
	 * \details
	 *
	 */
	public CreateDailySchedule() {
		super(new CreateDailyScheduleOptions());
	}

	/**
	 *
	 * \brief Options - returns the CreateDaysScheduleOptionClass
	 *
	 * \details
	 * The CreateDaysScheduleOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return CreateDaysScheduleOptions
	 *
	 */
	@Override
	public CreateDailyScheduleOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new CreateDailyScheduleOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of CreateDaysSchedule
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see CreateDaysScheduleMain
	 *
	 * \return CreateDaysSchedule
	 *
	 * @return
	 */
	public CreateDailySchedule Execute() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			Calendar2DB calendar2Db = new Calendar2DB(new File(objOptions.getconfiguration_file().Value()));
			calendar2Db.setOptions(objOptions);
			calendar2Db.store();

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Exception (e);
		}

		finally {
		}

		return this;
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

} // class CreateDailySchedule
