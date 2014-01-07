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
package com.sos.jitl.splitter;

import static com.sos.scheduler.messages.JSMessages.JSJ_I_0010;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_0020;

import org.apache.log4j.Logger;

import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Job;
import sos.spooler.Order;
import sos.spooler.Variable_set;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jitl.sync.SyncNodeList;

// Super-Class for JobScheduler Java-API-Jobs

/**
 * \class 		JobChainSplitterJSAdapterClass - JobScheduler Adapter for "Start a parallel processing in a jobchain"
 *
 * \brief AdapterClass of JobChainSplitter for the SOSJobScheduler
 *
 * This Class JobChainSplitterJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JobChainSplitter.
 *

 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-121986169113382203.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\latestscheduler_4446\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20130315155436
 * \endverbatim
 */
public class JobChainSplitterJSAdapterClass extends JobSchedulerJobAdapter {
	private final String	conClassName	= "JobChainSplitterJSAdapterClass";
	@SuppressWarnings("hiding")
	private static Logger	logger			= Logger.getLogger(JobChainSplitterJSAdapterClass.class);
	private final String	conSVNVersion	= "$Id: JSEventsClient.java 18220 2012-10-18 07:46:10Z kb $";

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

	@Override
	public boolean spooler_init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_init";
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_process";

		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			throw new JobSchedulerException("Fatal Error:" + e.getMessage(), e);
		}
		finally {
		} // finally
		return signalSuccess();

	} // spooler_process

	@Override
	public void spooler_exit() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::spooler_exit";
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::doProcessing";
		boolean flgCreateSyncParameter = false;
		boolean flgOverwriteParameters = true;

		if (isOrderJob() == true) {
			logger.info(conSVNVersion);
			JobChainSplitterOptions objSplitterOptions = new JobChainSplitterOptions();

			// TODO make this class available as a monitor class as well
			objSplitterOptions.CurrentNodeName(this.getCurrentNodeName());
			objSplitterOptions.setAllOptions(getSchedulerParameterAsProperties(getJobOrOrderParameters()));
			logger.info(objSplitterOptions.dirtyString());
			objSplitterOptions.CheckMandatory();

//			Order objOrderCurrent = super.getOrder();
			Order objOrderCurrent = spooler_task.order();
			Variable_set objOrderParams = objOrderCurrent.params();
			String strSyncStateName = objSplitterOptions.SyncStateName.Value();
			if (strSyncStateName.length() <= 0) {
				/**
				 * If the SyncStateName is not specified the name of the next_state is used for the sync state
				 */
				strSyncStateName = objOrderCurrent.job_chain_node().next_state();
			}
			logger.debug(String.format("SyncStateName = '%1$s'", strSyncStateName));
			String strJobChainName = objOrderCurrent.job_chain().name();

					// <show_job_chain job_chain="name" what="source" max_orders="0" max_order_history="0"/>
					/**
					 * Answer:
					 *  ...
						<source>
							<job_chain orders_recoverable="yes" visible="yes" title="ParallelChain" max_orders="30">
								<job_chain_node state="JobChainStart" job="JobChainStart" next_state="A" error_state="A"/>
								<job_chain_node state="A" job="ParallelExample" next_state="split_B" error_state="!A"/>
								<job_chain_node state="split_B" job="JobChainSplitter1" next_state="sync_B" error_state="!split_B"/>
								<job_chain_node state="C" job="ParallelExample" next_state="C1" error_state="!C"/>
								<job_chain_node state="C1" job="ParallelExample" next_state="sync_B" error_state="!C1"/>
								<job_chain_node state="D" job="ParallelExample" next_state="sync_B" error_state="!D"/>
								<job_chain_node state="E" job="ParallelExample" next_state="sync_B" error_state="!E"/>
								<job_chain_node state="G" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="H" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="I" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="J" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="K" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="L" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="M" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="N" job="ParallelExample" next_state="sync_B" error_state="!finished"/>
								<job_chain_node state="sync_B" job="SyncJob2" next_state="F" error_state="!sync_B"/>
								<job_chain_node state="F" job="ParallelExample" next_state="JobChainEnd" error_state="!F"/>
								<job_chain_node state="JobChainEnd" job="JobChainEnd" next_state="#finished" error_state="!finished"/>
								<job_chain_node state="!A"/>
								<job_chain_node state="!split_B"/>
								<job_chain_node state="!C"/>
								<job_chain_node state="!D"/>
								<job_chain_node state="!E"/>
								<job_chain_node state="!sync_B"/>
								<job_chain_node state="!F"/>
								<job_chain_node state="!C1"/>
								<job_chain_node state="#finished"/>
								<job_chain_node state="!finished"/>
							</job_chain>
						</source>
						...
					 *
					 */
			// TODO resolve problem with upper-/lower-case
			for (String strCurrentState : objSplitterOptions.StateNames.getValueList()) {
				if (objOrderCurrent.job_chain().node(strCurrentState) == null) {
					throw new JobSchedulerException(String.format("State '%1$s' in chain '%2$s' not found but mandatory", strCurrentState, strJobChainName));
				}
			}

			int lngNoOfParallelSteps = objSplitterOptions.StateNames.getValueList().length;
			flgCreateSyncParameter = true;
			if (flgCreateSyncParameter == true) {
//				String strSyncParam = strSyncStateName + "/" + strJobChainName + ";" + strSyncStateName + SyncNodeList.CONST_PARAM_PART_REQUIRED_ORDERS;
				String strSyncParam = strJobChainName + SyncNodeList.CHAIN_ORDER_DELIMITER + strSyncStateName + SyncNodeList.CONST_PARAM_PART_REQUIRED_ORDERS;
				objOrderParams.set_var(strSyncParam, Integer.toString(lngNoOfParallelSteps + 1));
				// TODO use global constant
				objOrderParams.set_var("sync_session_id", strJobChainName + "_" + strSyncStateName + "_" + objOrderCurrent.id());
			}

			for (String strCurrentState : objSplitterOptions.StateNames.getValueList()) {
				Order objOrderClone = spooler.create_order();
				objOrderClone.set_state(strCurrentState);
				objOrderClone.set_title(objOrderCurrent.title() + ": " + strCurrentState);
				objOrderClone.set_end_state(strSyncStateName);
				objOrderClone.params().merge(objOrderParams);
				String strOrderCloneName = objOrderCurrent.id() + "_" + strCurrentState;
				objOrderClone.set_id(strOrderCloneName);
				// TODO Parameter start_at  "when_in_period"
				objOrderClone.set_at("now");
				objOrderCurrent.job_chain().add_or_replace_order(objOrderClone);
				logger.info(String.format("Order '%1$s' created and started", strOrderCloneName));
				logger.debug(objOrderClone.xml());
			}
		}
		else {
			throw new JobSchedulerException("This Job can run as an job in a jobchain only");
		}
	} // doProcessing

	protected String getNextStateNodeName() {
		final String conMethodName = conClassName + "::getNextStateNodeName";
		String lstrNextStateNodeName = "";
		if (spooler_task != null) {
			Order objCurrentOrder = spooler_task.order();
			if (isNotNull(objCurrentOrder)) {
				lstrNextStateNodeName = objCurrentOrder.job_chain_node().next_state();
				JSJ_I_0020.toLog(conMethodName, lstrNextStateNodeName);
			}
			else {
				Job objCurrentJob = getJob();
				lstrNextStateNodeName = objCurrentJob.name();
				JSJ_I_0010.toLog(conMethodName, lstrNextStateNodeName);
			}
		}
		return lstrNextStateNodeName;
	} // public String getNodeName


}
