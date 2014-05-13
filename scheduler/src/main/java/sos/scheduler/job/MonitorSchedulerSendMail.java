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
/*
 * MonitorSchedulerSendMail.java
 * Created on 22.08.2007
 *
 */
package sos.scheduler.job;

import sos.scheduler.misc.SchedulerMailer;
import sos.spooler.Variable_set;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * This monitor sends an email after process using the parameters of
 * {@link SchedulerMailer}<br>
 * Additional parameters:<br>
 * monitor_mail_on_success - send email if job returned true (default true)
 * monitor_mail_on_error   - send email if job returned false (default true)
 * @author Andreas Liebert
 */
public class MonitorSchedulerSendMail extends JobSchedulerJobAdapter {

	@SuppressWarnings("unused")
	private final String				conSVNVersion		= "$Id: MonitorSchedulerSendMail.java 18378 2012-11-09 15:39:03Z kb $";

	@Override
	public boolean spooler_process_after(final boolean result) throws Exception {
		try {
			Variable_set params = spooler.create_variable_set();
			params.merge(spooler_task.params());
			if (spooler_job.order_queue() != null) {
				params.merge(spooler_task.order().params());
			}

			boolean mailOnSuccess = true;
			boolean mailOnError = true;

			if (params.value("monitor_mail_on_success") != null) {
				String mos = params.value("monitor_mail_on_success");
				if (mos.equalsIgnoreCase("no") || mos.equalsIgnoreCase("false") || mos.equalsIgnoreCase("0"))
					mailOnSuccess = false;
			}

			if (params.value("monitor_mail_on_error") != null) {
				String moe = params.value("monitor_mail_on_error");
				if (moe.equalsIgnoreCase("no") || moe.equalsIgnoreCase("false") || moe.equalsIgnoreCase("0"))
					mailOnError = false;
			}

			if (mailOnSuccess && result || mailOnError && !result) {
				SchedulerMailer mailer = new SchedulerMailer(this);
				mailer.getSosMail().send();
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException("Error occured processing mail: " + e, e);
		}
		return result;
	}

}
