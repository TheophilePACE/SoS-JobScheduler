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
import com.sos.jobnet.creator.JobNetCreator;
import com.sos.jobnet.options.JobNetCreatorOptions;

import sos.scheduler.job.JobSchedulerJobAdapter;
/**
 * \class 		JobNetCreatorJSAdapterClass - JobScheduler Adapter for "creates the job net for a given bootstrap order"
 *
 * \brief AdapterClass of JobNetCreator for the SOSJobScheduler
 *
 * This Class JobNetCreatorJSAdapterClass works as an adapter-class between the SOS
 * JobScheduler and the worker-class JobNetCreator.
 *

 *
 * see \see C:\Users\schaedi\AppData\Local\Temp\scheduler_editor-3377211190692187569.html for more details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\scheduler-1.3.12.2032\config\JOETemplates\java\xsl\JSJobDoc2JSAdapterClass.xsl from http://www.sos-berlin.com at 20120228153423 
 * \endverbatim
 */
public class JobNetCreatorJSAdapterClass extends JobSchedulerJobAdapter  {

	private static Logger logger = Logger.getLogger(JobNetCreatorJSAdapterClass.class);
	
	@SuppressWarnings("unused")
	private	final String conSVNVersion = "$Id: JobNetCreatorJSAdapterClass.java 16850 2012-03-21 12:14:23Z ss $";
	
	private JobNetCreatorOptions options = new JobNetCreatorOptions();

	public void init() {
	}

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
			logger.debug(options.toString());
			JobNetCreator creator = new JobNetCreator(options);
			creator.run();
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

}

