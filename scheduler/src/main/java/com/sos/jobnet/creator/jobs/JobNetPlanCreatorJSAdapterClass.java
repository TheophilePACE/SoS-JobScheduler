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
package com.sos.jobnet.creator.jobs;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.creator.JobNetPlanCreator;
import com.sos.jobnet.db.JobNetPlanDBItem;
import com.sos.jobnet.interfaces.IStartOrder;
import com.sos.jobnet.options.JobNetCreatorOptions;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Spooler;
/**
 * \class 		JobNetPlanCreatorJSAdapterClass - JobScheduler Adapter for "creates the job net instance for a given bootstrap order"
 */
public class JobNetPlanCreatorJSAdapterClass extends JobSchedulerJobAdapter implements IStartOrder  {
	
	private final String conClassName = "JobNetCreatorJSAdapterClass";

	private static Logger logger = Logger.getLogger(JobNetPlanCreatorJSAdapterClass.class);
	private JobNetCreatorOptions options = null;;
	
	@SuppressWarnings("unused")
	private	final String conSVNVersion = "$Id: JobNetPlanCreatorJSAdapterClass.java 16850 2012-03-21 12:14:23Z ss $";
	
	private JobNetPlanCreator creator = null;

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {

		final String conMethodName = conClassName + "::spooler_process"; //$NON-NLS-1$

		try {
			super.spooler_process();
			logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName ) );
			options = new JobNetCreatorOptions();
			options.setAllOptions(getAllParametersAsProperties());
			options.CheckMandatory();
			logger.info(options.toString());
			creator = new JobNetPlanCreator(options);
			creator.registerOrderStarter(this);
			creator.run();
			logger.debug(String.format(Messages.getMsg("JSJ-I-111"), conMethodName ) );
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new JobSchedulerException(e.getMessage(),e);
		}
		return (spooler_task.job().order_queue() != null);

	}

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused") //$NON-NLS-1$
		final String conMethodName = conClassName + "::spooler_exit"; //$NON-NLS-1$
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
		startOrderViaJobScheduler(spooler, item, false);
	}

	@Override
	public void startOrderViaJobScheduler(Spooler spooler, JobNetPlanDBItem bootDBItem, boolean startImmediately) {
		creator.startOrderViaJobScheduler(spooler,bootDBItem, startImmediately);
	}

}

