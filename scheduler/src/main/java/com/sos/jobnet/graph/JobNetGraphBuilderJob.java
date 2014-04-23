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

import com.sos.JSHelper.Basics.JSToolBox;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.io.Files.JSFile;
import com.sos.jobnet.classes.JobNetConstants;
import com.sos.jobnet.db.EventsDBLayer;
import com.sos.localization.Messages;

public class JobNetGraphBuilderJob implements Runnable {
	
	private final Logger logger = Logger.getLogger(JobNetGraphBuilderJob.class);
	private static final Messages msg = new JSToolBox(JobNetConstants.strBundleBaseName).getMessageObject();
	
	private final String uuid;
	private final JSFile hibernateConnectionConfigFile;
	private final EventsDBLayer eventsDBLayer;
	private final String eventIdJobnetStarted;
	private final String eventIdJobnetEnded;
	private final JobNetGraphBuilder builder;
	private final int checkInterval;
	
	private boolean jobnetStarted = false;

	public JobNetGraphBuilderJob(JobNetGraphBuilderOptions options) {
	    this.uuid = options.uuid_jobnet_identifier.Value();
		this.hibernateConnectionConfigFile 	= options.hibernate_connection_config_file.JSFile();
		this.eventsDBLayer = new EventsDBLayer(hibernateConnectionConfigFile);
		this.eventIdJobnetStarted = JobNetConstants.JOBNET_STARTED_PREFIX + uuid;
		this.eventIdJobnetEnded = JobNetConstants.JOBNET_ENDED_PREFIX + uuid;
		this.builder = new JobNetGraphBuilder(options);
		this.checkInterval = options.check_interval.getTimeAsSeconds();
		
		String conMethodName = JobNetGraphBuilderJob.class.getSimpleName();
		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "UUID", uuid));
		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "hibernateConnectionConfigFile", hibernateConnectionConfigFile.getAbsolutePath()));
		logger.debug(msg.getMsg(JobNetConstants.JOBNET_D_0001, conMethodName, "checkInterval", checkInterval));
	}

	@Override
	public void run() {

		// configure the job with a setback to wait until the jobnet is started
		jobnetStarted = eventsDBLayer.isEventThrown(eventIdJobnetStarted);
		if (jobnetStarted) {

			boolean jobnetEnded = false;
			try {
				builder.addToIndexFile(uuid + JobNetGraphBuilder.HTML_EXTENSION);
				do {
					builder.run();			// creates the jobnet graph
					builder.createHtmlFile();
					if(!builder.isJobnetPresentInDB())
						throw new JobSchedulerException("A jobnet with id " + uuid + " is not present in database.");
					jobnetEnded = eventsDBLayer.isEventThrown(eventIdJobnetEnded);
					if (jobnetEnded) {
						logger.info("Jobnet is ended - graphbuilder will be ended, too.");
						break;
					}
					Thread.sleep(checkInterval*1000);
				}
				while (!jobnetEnded);
				builder.removeFromIndexFile(uuid + JobNetGraphBuilder.HTML_EXTENSION);
			} catch (InterruptedException e) {
				String msg = "Error while sleeping";
				logger.error(msg,e);
				throw new JobSchedulerException(msg,e);
			}
		} else {
			String msg = "Jobnet with id " + uuid + " is not started yet";
			logger.warn(msg);
		}

	}

	public boolean isJobnetStarted() {
		return jobnetStarted;
	}
}
