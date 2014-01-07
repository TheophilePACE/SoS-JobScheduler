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


package sos.scheduler.managed.db;

import java.util.HashMap;

import sos.scheduler.managed.db.JobSchedulerManagedDBReportJob;
import sos.scheduler.managed.db.JobSchedulerManagedDBReportJobOptions;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.scheduler.job.JobSchedulerJobAdapter;  // Super-Class for JobScheduler Java-API-Jobs
import org.apache.log4j.Logger;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.localization.*;
/**
 * \class 		JobSchedulerManagedDBReportJobJSAdapterClass - JobScheduler Adapter for "Launch Database Report"
 *
 * \brief AdapterClass of JobSchedulerManagedDBReportJob for the SOSJobScheduler
 *
 * This Class JobSchedulerManagedDBReportJobJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JobSchedulerManagedDBReportJob.
 *

 *
 * see \see R:\backup\sos\java\development\com.sos.scheduler\src\sos\scheduler\jobdoc\JobSchedulerManagedDBReportJob.xml for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\Users\KB\eclipse\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20120830214330 
 * \endverbatim
 */
public class JobSchedulerManagedDBReportJobJSAdapterClass extends JobSchedulerJobAdapter  {
	private final String					conClassName						= "JobSchedulerManagedDBReportJobJSAdapterClass";  //$NON-NLS-1$
	private static Logger		logger			= Logger.getLogger(JobSchedulerManagedDBReportJobJSAdapterClass.class);

	public void init() {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_init"; //$NON-NLS-1$
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			return false;
		}
		finally {
		} // finally
		// return value for classic and order driven processing
		// TODO create method in base-class for this functionality
		return (spooler_task.job().order_queue() != null);

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::doProcessing"; //$NON-NLS-1$

		JobSchedulerManagedDBReportJob objR = new JobSchedulerManagedDBReportJob();
		JobSchedulerManagedDBReportJobOptions objO = objR.Options();
		objO.setAllOptions(getSchedulerParameterAsProperties(getParameters()));
		objO.CheckMandatory(); 
        objR.setJSJobUtilites(this);		
		objR.Execute();
	} // doProcessing

}

