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
 * JobSchedulerManagedMailJob.java
 * Created on 02.08.2007
 * 
 */
package sos.scheduler.managed;

import java.io.File;
import java.util.Properties;

import sos.net.SOSMail;
import sos.net.SOSMailAttachment;
import sos.settings.SOSProfileSettings;
import sos.settings.SOSSettings;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.util.SOSFile;


/**
 * https://blogs.oracle.com/apanicker/entry/java_code_for_smtp_server
 * for SSL/TLS and SMTP
 * @author KB
 *
 */
public class JobSchedulerManagedMailJob extends JobSchedulerManagedJob {

	@Override
	public boolean spooler_process() {

		Order order = null;
		orderPayload = null;
		String orderId = "(none)";

		String host = spooler_log.mail().smtp();
		boolean hostChanged = false;
		int port = 25;
		boolean portChanged = false;
		String queueDir = spooler_log.mail().queue_dir();
		boolean queueDirChanged = false;

		String from = spooler_log.mail().from();
		boolean fromChanged = false;

		String fromName = "";
		String replyTo = "";

		String to = "";
		String cc = "";
		String bcc = "";
		String subject = "";
		String body = "";
		String contentType = "";
		String encoding = "";

		String attachmentCharset = "";
		String attachmentContentType = "";
		String attachmentEncoding = "";
		boolean cleanupAttachment = false;
		String[] attachments = {};

		String smtpUser = "";
		String smtpPass = "";

		try {

			try {
				super.prepareParams();
			}
			catch (Exception e) {
				throw new Exception("error occurred preparing order: " + e.getMessage());
			}

			if (doSendMail()) {

				try {
					if (this.getParameters().value("to") != null && this.getParameters().value("to").length() > 0) {
						to = this.getParameters().value("to");
					}
					else {
						throw new Exception("no value was specified for mandatory parameter [to]");
					}

					if (this.getParameters().value("subject") != null && this.getParameters().value("subject").length() > 0) {
						subject = this.getParameters().value("subject");
					}
					else {
						throw new Exception("no value was specified for mandatory parameter [subject]");
					}

					if (this.getParameters().value("host") != null && this.getParameters().value("host").length() > 0) {
						host = this.getParameters().value("host");
						hostChanged = true;
					}

					if (this.getParameters().value("port") != null && this.getParameters().value("port").length() > 0) {
						try {
							port = Integer.parseInt(this.getParameters().value("port"));
							portChanged = true;
						}
						catch (Exception e) {
							throw new Exception("illegal, non-numeric value [" + this.getParameters().value("port") + "] for parameter [port]: "
									+ e.getMessage());
						}
					}

					if (this.getParameters().value("smtp_user") != null && this.getParameters().value("smtp_user").length() > 0) {
						smtpUser = this.getParameters().value("smtp_user");
					}

					if (this.getParameters().value("smtp_password") != null && this.getParameters().value("smtp_password").length() > 0) {
						smtpPass = this.getParameters().value("smtp_password");
					}

					if (this.getParameters().value("queue_directory") != null && this.getParameters().value("queue_directory").length() > 0) {
						queueDir = this.getParameters().value("queue_directory");
						queueDirChanged = true;
					}

					if (this.getParameters().value("from") != null && this.getParameters().value("from").length() > 0) {
						from = this.getParameters().value("from");
						fromChanged = true;
					}

					if (this.getParameters().value("cc") != null && this.getParameters().value("cc").length() > 0) {
						cc = this.getParameters().value("cc");
					}

					if (this.getParameters().value("bcc") != null && this.getParameters().value("bcc").length() > 0) {
						bcc = this.getParameters().value("bcc");
					}

					if (this.getParameters().value("from_name") != null && this.getParameters().value("from_name").length() > 0) {
						fromName = this.getParameters().value("from_name");
					}

					if (this.getParameters().value("reply_to") != null && this.getParameters().value("reply_to").length() > 0) {
						replyTo = this.getParameters().value("reply_to");
					}

					if (this.getParameters().value("body") != null && this.getParameters().value("body").length() > 0) {
						body = this.getParameters().value("body");
					}

					if (this.getParameters().value("content_type") != null && this.getParameters().value("content_type").length() > 0) {
						contentType = this.getParameters().value("content_type");
					}

					if (this.getParameters().value("encoding") != null && this.getParameters().value("encoding").length() > 0) {
						encoding = this.getParameters().value("encoding");
					}

					if (this.getParameters().value("attachment_charset") != null && this.getParameters().value("attachment_charset").length() > 0) {
						attachmentCharset = this.getParameters().value("attachment_charset");
					}

					if (this.getParameters().value("attachment_content_type") != null && this.getParameters().value("attachment_content_type").length() > 0) {
						attachmentContentType = this.getParameters().value("attachment_content_type");
					}

					if (this.getParameters().value("attachment_encoding") != null && this.getParameters().value("attachment_encoding").length() > 0) {
						attachmentEncoding = this.getParameters().value("attachment_encoding");
					}

					if (this.getParameters().value("attachment") != null && this.getParameters().value("attachment").length() > 0) {
						attachments = this.getParameters().value("attachment").split(";");
					}

					if (this.getParameters().value("cleanup_attachment") != null && this.getParameters().value("cleanup_attachment").length() > 0) {
						if (this.getParameters().value("cleanup_attachment").equals("1")
								|| this.getParameters().value("cleanup_attachment").equalsIgnoreCase("true")
								|| this.getParameters().value("cleanup_attachment").equalsIgnoreCase("yes")) {
							cleanupAttachment = true;
						}
					}

				}
				catch (Exception e) {
					throw new Exception("error occurred checking parameters: " + e.getMessage());
				}

				try { // to process order
					SOSMail sosMail;
					Properties mailSection = null;
					if (this.getConnectionSettings() != null) {
						try {
							mailSection = this.getConnectionSettings().getSection("email", "mail_server");
							if (mailSection.size() < 1)
								mailSection = null;
						}
						catch (Exception e) {
							getLogger().debug6("No database settings found, using defaults from factory.ini");
						}
					}
					if (mailSection != null) {
						// use defaults from database settings
						sosMail = new SOSMail(getConnectionSettings());
						if (hostChanged)
							sosMail.setHost(host);
						if (queueDirChanged)
							sosMail.setQueueDir(queueDir);
						if (fromChanged)
							sosMail.setFrom(from);
					}
					else {
						// use defaults from Scheduler configuration
						sosMail = new SOSMail(host);
						sosMail.setQueueDir(queueDir);
						sosMail.setFrom(from);
						SOSSettings smtpSettings = new SOSProfileSettings(spooler.ini_path());
						Properties smtpProperties = smtpSettings.getSection("smtp");
						if (!smtpProperties.isEmpty()) {
							if (smtpProperties.getProperty("mail.smtp.user") != null && smtpProperties.getProperty("mail.smtp.user").length() > 0) {
								sosMail.setUser(smtpProperties.getProperty("mail.smtp.user"));
							}
							if (smtpProperties.getProperty("mail.smtp.password") != null && smtpProperties.getProperty("mail.smtp.password").length() > 0) {
								sosMail.setPassword(smtpProperties.getProperty("mail.smtp.password"));
							}
						}
					}

					if (portChanged)
						sosMail.setPort(Integer.toString(port));
					if (smtpUser.length() > 0)
						sosMail.setUser(smtpUser);
					if (smtpPass.length() > 0)
						sosMail.setPassword(smtpPass);

					// set values only if these are set by params, else use defaults from SOSMail
					if (contentType.length() > 0)
						sosMail.setContentType(contentType);
					if (encoding.length() > 0)
						sosMail.setEncoding(encoding);

					if (attachmentCharset.length() > 0)
						sosMail.setAttachmentCharset(attachmentCharset);
					if (attachmentEncoding.length() > 0)
						sosMail.setAttachmentEncoding(attachmentEncoding);
					if (attachmentContentType.length() > 0)
						sosMail.setAttachmentContentType(attachmentContentType);

					if (fromName.length() > 0)
						sosMail.setFromName(fromName);

					String recipientsTo[] = to.split(";|,");
					for (int i = 0; i < recipientsTo.length; i++) {
						if (i == 0)
							sosMail.setReplyTo(recipientsTo[i].trim());
						sosMail.addRecipient(recipientsTo[i].trim());
					}
					if (replyTo.length() > 0)
						sosMail.setReplyTo(replyTo);
					sosMail.addCC(cc);
					sosMail.addBCC(bcc);

					sosMail.setSubject(subject);
					sosMail.setBody(body);

					for (String attachment2 : attachments) {
						File attachmentFile = new File(attachment2);
						SOSMailAttachment attachment = new SOSMailAttachment(sosMail, attachmentFile);
						attachment.setCharset(sosMail.getAttachmentCharset());
						attachment.setEncoding(sosMail.getAttachmentEncoding());
						attachment.setContentType(sosMail.getAttachmentContentType());
						sosMail.addAttachment(attachment);
					}

					sosMail.setSOSLogger(this.getLogger());

					this.getLogger().info("sending mail: \n" + sosMail.dumpMessageAsString());

					if (!sosMail.send()) {
						this.getLogger().warn(
								"mail server is unavailable, mail for recipient [" + to + "] is queued in local directory [" + sosMail.getQueueDir() + "]:"
										+ sosMail.getLastError());
					}

					if (cleanupAttachment) {

						for (String attachment : attachments) {
							File attachmentFile = new File(attachment);
							if (attachmentFile.exists() && attachmentFile.canWrite()) {
								SOSFile.deleteFile(attachmentFile);
							}
						}
					}

					sosMail.clearRecipients();

				}
				catch (Exception e) {
					throw new Exception(e.getMessage());
				}

			}
			return spooler_task.job().order_queue() != null ? true : false;

		}
		catch (Exception e) {
			spooler_log.warn("error occurred processing order [" + orderId + "]: " + e.getMessage());
			spooler_task.end();
			return false;
		}
	}

	/**
	 * This function may be overwritten by other classes
	 * which may check with other parameters if a mail
	 * should be sent or not
	 * @return true if mail should be sent
	 */
	protected boolean doSendMail() {
		return true;
	}

	@Override
	public final Variable_set getParameters() {
		return orderPayload;
	}
}
