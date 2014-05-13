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
package com.sos.jitl.sync;

import static com.sos.scheduler.messages.JSMessages.JSJ_F_107;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_110;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_111;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * \class 		JobSchedulerSynchronizeJobChains - Workerclass for "Synchronize Job Chains"
 *
 * \brief AdapterClass of JobSchedulerSynchronizeJobChains for the SOSJobScheduler
 *
 * This Class JobSchedulerSynchronizeJobChains is the worker-class.
 *
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\scheduler_ur\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20121217120436
 * \endverbatim
 */
public class JobSchedulerSynchronizeJobChains extends JSJobUtilitiesClass<JobSchedulerSynchronizeJobChainsOptions> {
	private final String								conClassName		= "JobSchedulerSynchronizeJobChains";
	private static Logger								logger				= Logger.getLogger(JobSchedulerSynchronizeJobChains.class);

	protected SyncNodeContainer							syncNodeContainer;
	protected HashMap<String, String>					SchedulerParameters	= new HashMap<String, String>();

	/**
	 *
	 * \brief JobSchedulerSynchronizeJobChains
	 *
	 * \details
	 *
	 */
	public JobSchedulerSynchronizeJobChains() {
		super(new JobSchedulerSynchronizeJobChainsOptions());
	}

	/**
	 *
	 * \brief Options - returns the JobSchedulerSynchronizeJobChainsOptionClass
	 *
	 * \details
	 * The JobSchedulerSynchronizeJobChainsOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JobSchedulerSynchronizeJobChainsOptions
	 *
	 */
	@Override
	public JobSchedulerSynchronizeJobChainsOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JobSchedulerSynchronizeJobChainsOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JobSchedulerSynchronizeJobChains
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JobSchedulerSynchronizeJobChainsMain
	 *
	 * \return JobSchedulerSynchronizeJobChains
	 *
	 * @return
	 */
	public JobSchedulerSynchronizeJobChains Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute"; //$NON-NLS-1$

		JSJ_I_110.toLog(conMethodName );

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			syncNodeContainer = new SyncNodeContainer();

			syncNodeContainer.setSyncId(Options().sync_session_id.Value());
			syncNodeContainer.setJobpath(Options().jobpath.Value());
			syncNodeContainer.getNodes(Options().jobchains_answer.Value());
			syncNodeContainer.getOrders(Options().orders_answer.Value());
			syncNodeContainer.setRequiredOrders(SchedulerParameters);

			if (syncNodeContainer.isReleased()) {
				logger.debug("Release all orders");
			}
			else {
				logger.debug("Suspending all orders");
			}

		}
		catch (Exception e) {
			throw new JobSchedulerException(JSJ_F_107.get(conMethodName) + ":" + e.getMessage(), e);
		}

		JSJ_I_111.toLog(conMethodName);
		return this;
	}

	public void init() throws RuntimeException, Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init"; //$NON-NLS-1$
		doInitialize();
	}

	private void doInitialize() throws RuntimeException, Exception {

	} // doInitialize

	public void setSchedulerParameters(final HashMap<String, String> schedulerParameters) {
		SchedulerParameters = schedulerParameters;
	}

} // class JobSchedulerSynchronizeJobChains
