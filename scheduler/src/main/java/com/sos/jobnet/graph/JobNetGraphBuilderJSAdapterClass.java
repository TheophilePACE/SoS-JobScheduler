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
package com.sos.jobnet.graph;

import org.apache.log4j.Logger;
import sos.scheduler.job.JobSchedulerJobAdapter;
import com.sos.JSHelper.Exceptions.JobSchedulerException;

public class JobNetGraphBuilderJSAdapterClass extends JobSchedulerJobAdapter  {
	
	private final String					conClassName						= "JobNetCreatorJSAdapterClass";  //$NON-NLS-1$

	private static Logger		logger			= Logger.getLogger(JobNetGraphBuilderJSAdapterClass.class);
	
	@SuppressWarnings("unused")
	private	final String		conSVNVersion			= "$Id: JobNetPlanCreatorJSAdapterClass.java 16850 2012-03-21 12:14:23Z ss $";

	public void init() {
		doInitialize();
	}

	private void doInitialize() {
	}

	@Override
	public boolean spooler_init() {
		return super.spooler_init();
	}

	@Override
	public boolean spooler_process() throws Exception {
		try {
			super.spooler_process();
			doProcessing();
		}
		catch (Exception e) {
			throw e;
		}
		return (spooler_task.job().order_queue() != null);
	}

	@Override
	public void spooler_exit() {
		super.spooler_exit();
	}

	private void doProcessing() throws Exception {
		
		final String conMethodName = conClassName + "::doProcessing"; //$NON-NLS-1$

		logger.debug(String.format(Messages.getMsg("JSJ-I-110"), conMethodName ) );

		try { 
			JobNetGraphBuilderOptions options = new JobNetGraphBuilderOptions();
			options.setAllOptions(getAllParametersAsProperties());
			options.CheckMandatory();
			logger.debug(options.toString());
			JobNetGraphBuilderJob builder = new JobNetGraphBuilderJob(options);
			builder.run();
			if (!builder.isJobnetStarted()) {
				throw new JobSchedulerException("Error creating the jobnet graph.");
			}
			logger.debug(String.format(Messages.getMsg("JSJ-I-111"), conMethodName ) );
 		} catch (Exception e) {
			String msgText = String.format(Messages.getMsg("JSJ-I-107"), conMethodName );
			logger.error(msgText);
			throw new JobSchedulerException(msgText,e);
		}

	}

}

