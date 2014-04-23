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
package com.sos.jobnet.creator.jobs;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.creator.JobNetPlanRestarter;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnet.interfaces.IStartOrder;
import com.sos.jobnet.options.JobNetCreatorOptions;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Spooler;
/**
 * This job is for restart a given jobnet instance identified by its uuid.
 * 
 * @author ss
 */
public class JobNetPlanRestarterJSAdapterClass extends JobSchedulerJobAdapter implements IStartOrder  {
	
	private static Logger logger = Logger.getLogger(JobNetPlanRestarterJSAdapterClass.class);
	
	private JobNetCreatorOptions options;
	
	@SuppressWarnings("unused")
	private	final String conSVNVersion = "$Id: JobNetPlanRestarterJSAdapterClass.java 16850 2012-03-21 12:14:23Z ss $";
	
	private JobNetPlanRestarter restarter = null;

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {

		final String conMethodName = this.getClass().getSimpleName() + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();

			options = new JobNetCreatorOptions();
			options.setAllOptions(getAllParametersAsProperties());
			options.CheckMandatory();
			logger.debug(options.toString());
			
			restarter = new JobNetPlanRestarter(options);
			restarter.registerOrderStarter(this);
			restarter.run();
			logger.debug(String.format(Messages.getMsg("JSJ-I-111"), conMethodName ) );
		}
		catch (Exception e) {
			String msgText = String.format(Messages.getMsg("JSJ-I-107"), conMethodName );
            spooler_log.error(msgText);
			throw new JobSchedulerException(msgText,e);
		}
		return (spooler_task.job().order_queue() != null);

	}

	@Override
	public void spooler_exit() {
		super.spooler_exit();
	}
	
	public JobNetCreatorOptions getOptions() {
		return options;
	}

	@Override
	public void setCC(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startOrderImmediately(JobNetPlanDBItem item) {
		startOrderViaJobScheduler(spooler, item, true);
	}

	@Override
	public void startOrder(JobNetPlanDBItem item) {
		startOrderViaJobScheduler(spooler, item, true);
	}

	@Override
	public void startOrderViaJobScheduler(Spooler spooler, JobNetPlanDBItem bootDBItem, boolean startImmediately) {
		restarter.startOrderViaJobScheduler(spooler, bootDBItem, startImmediately);
	}

}

