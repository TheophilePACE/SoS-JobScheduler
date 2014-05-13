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
package sos.scheduler.job;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.search.SubjectTerm;

import sos.net.SOSMailReceiver;
import sos.net.SOSMimeMessage;
import sos.scheduler.command.SOSSchedulerCommand;
import sos.spooler.Job_chain;
import sos.spooler.Job_impl;
import sos.spooler.Order;
import sos.spooler.Variable_set;
import sos.util.SOSSchedulerLogger;

/**
 * <p>Title: JobDocumentFactoryCheckFax</p>
 * <p>Description: Dieser Job �berpr�ft anhand einer Mailbox, ob der Faxserver
 *
 *
 * </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * @author Uwe Risse
 */
public class SOSMailReadInbox extends Job_impl {
	/** Settings Attribut: mailHost: Host des mail servers */
	private String				mailHost					= "";
	/** Settings Attribut: mailHost: Host des mail servers */
	private String				mailPort					= "110";
	/** Settings Attribut: mail_user: Benutzername des Email Accounts */
	private String				mailUser					= "";
	/** Settings Attribut: mailPassword: Passwort des Email Accounts */
	private String				mailPassword				= new String("");
	/** Settings Attribut: mailMessageFolder: Ordner, der gelesen werden soll */
	private String				mailMessageFolder			= "";
	/** Settings Attribut: mailSubjectFilter: Filter f�r Betreff */
	private String				mailSubjectFilter			= "";
	/** Settings Attribut: mailSubjectPattern: Regul�rer Ausdruck f�r Betreff */
	private String				mailSubjectPattern			= "";
	/** Settings Attribut: mailBodyPattern: Regul�rer Ausdruck f�r Body */
	private String				mailBodyPattern				= "";
	/** Settings Attribut: mailServerTimeout: Timeout */
	private int					mailServerTimeout			= 0;
	/** Settings Attribut: mailDumpDir: Verzeichnis f�r Email, die als Datei gespeichert werden */
	private String				mailDumpDir					= "";
	/** Settings Attribut: mailJobchain: Jobkette f�r add_order */
	private String				mailJobchain				= "";
	/** Settings Attribut: mailOrderId: Order-id f�r add_order */
	private String				mailOrderId					= "";
	/** Settings Attribut: mailOrderState: State f�r add_order */
	private String				mailOrderState				= "";
	/** Settings Attribut: mailOrderTitle: Title f�r add_order */
	private String				mailOrderTitle				= "";
	/** Settings Attribut: mailServerType: Imap oder Pop3 */
	private String				mailServerType				= "";
	/** Settings Attribut: mailBodyAsSchedulerCommand: Den Body als Kommand an einen Scheduler senden */
	private boolean				mailBodyAsSchedulerCommand	= false;
	/** Settings Attribut: mailAction:  delete|order|dump|command */
	private String				mailAction					= "";
	/** Settings Attribut: mailSchedulerHost:  Scheduler Host f�r Kommando aus Body und add_order*/
	private String				mailSchedulerHost			= "";
	/** Settings Attribut: mailSchedulerPort:  Scheduler Port f�r Kommando aus Body und add_order*/
	private int					mailSchedulerPort			= 0;
	/** Email nach der Ausf�hrung der Aktionen auf "gelesen" setzen */
	private boolean				mailSetSeen					= true;
	/** Nur Emails verarbeiten, die nicht gelesen sind. Wenn True ist mailSetSeen auch True */
	private boolean				mailUseSeen					= true;
	private SOSMailReceiver		receiver;
	private int					fromMail					= 1;
	private final int					nextMail					= 19;
	private Variable_set		params;
	private Pattern				subjectPattern;
	private Pattern				bodyPattern;
	private SOSSchedulerLogger	sosLogger					= null;

	@Override
	public boolean spooler_init() throws Exception {
		params = spooler_task.params();
		sosLogger = new SOSSchedulerLogger(spooler_log);
		mailHost = getParams("mail_host", "");
		sosLogger.debug3(".. current setting [mail_host]: " + mailHost);
		mailUser = getParams("mail_user");
		sosLogger.debug3(".. current setting [mail_user]: " + mailUser);
		mailPassword = getParams("mail_password", "");
		sosLogger.debug3(".. current setting [mail_password]: " + "**********" /* mailPassword */);
		mailPort = getParams("mail_port", mailPort);
		sosLogger.debug3(".. current setting [mail_port]: " + mailPort);
		mailMessageFolder = getParams("mail_message_folder", "INBOX");
		sosLogger.debug3(".. current setting [mail_message_folder]: " + mailMessageFolder);
		mailSubjectFilter = params.value("mail_subject_filter");
		sosLogger.debug3(".. current setting [mail_subject_filter]: " + mailSubjectFilter);
		mailSubjectPattern = getParams("mail_subject_pattern", "");
		sosLogger.debug3(".. current setting [mail_subject_pattern]: " + mailSubjectPattern);
		mailBodyPattern = getParams("mail_body_pattern", "");
		sosLogger.debug3(".. current setting [mail_body_pattern]: " + mailBodyPattern);
		mailBodyAsSchedulerCommand = getParams("mail_body_as_scheduler_command", "true").equalsIgnoreCase("true")
				|| getParams("mail_body_as_scheduler_command", "true").equalsIgnoreCase("1")
				|| getParams("mail_body_as_scheduler_command", "true").equalsIgnoreCase("yes");
		sosLogger.debug3(".. current setting [mail_body_as_scheduler_command]: " + mailBodyAsSchedulerCommand);
		mailJobchain = getParams("mail_jobchain", "");
		sosLogger.debug3(".. current setting [mail_jobchain]: " + mailJobchain);
		mailOrderId = getParams("mail_order_id", "");
		sosLogger.debug3(".. current setting [mail_order_id]: " + mailOrderId);
		mailOrderTitle = getParams("mail_order_title", "");
		sosLogger.debug3(".. current setting [mail_order_title]: " + mailOrderTitle);
		mailOrderState = getParams("mail_order_state", "");
		sosLogger.debug3(".. current setting [mail_order_state]: " + mailOrderState);
		mailDumpDir = params.value("mail_dump_dir");
		sosLogger.debug3(".. current setting [mail_dump_dir]: " + mailDumpDir);
		mailAction = params.value("mail_action");
		sosLogger.debug3(".. current setting [mail_action]: " + mailAction);
		mailSchedulerHost = getParams("mail_scheduler_host", spooler.hostname());
		sosLogger.debug3(".. current setting [mail_scheduler_host]: " + mailSchedulerHost);
		mailSchedulerPort = getInt(getParams("mail_scheduler_port", String.valueOf(spooler.tcp_port())), 0);
		sosLogger.debug3(".. current setting [mail_scheduler_port]: " + mailSchedulerPort);
		mailServerTimeout = getInt(getParams("mail_server_timeout"), 0);
		sosLogger.debug3(".. current setting [mail_server_timeout]: " + mailServerTimeout);
		mailServerType = getParams("mail_server_type", "POP3");
		sosLogger.debug3(".. current setting [mail_server_type]: " + mailServerType);
		mailUseSeen = getParams("mail_use_seen", "true").equalsIgnoreCase("true") || getParams("mail_use_seen", "true").equalsIgnoreCase("1")
				|| getParams("mail_use_seen", "true").equalsIgnoreCase("yes");
		mailSetSeen = mailUseSeen || getParams("mail_set_seen", "true").equalsIgnoreCase("true") || getParams("mail_set_seen", "true").equalsIgnoreCase("1")
				|| getParams("mail_set_seen", "true").equalsIgnoreCase("yes");
		sosLogger.debug3(".. current setting [mail_set_seen]: " + mailSetSeen);
		subjectPattern = Pattern.compile(mailSubjectPattern, 0);
		bodyPattern = Pattern.compile(mailBodyPattern, 0);
		return true;
	}

	@Override
	public boolean spooler_process() throws Exception {
		try {
			ArrayList<SOSMimeMessage> messages = findMessages();
			if (messages == null) {
				return false;
			}
			if (!messages.isEmpty()) {
				Iterator<SOSMimeMessage> messageIterator = messages.iterator();
				SOSMimeMessage newestMail = null;
				while (messageIterator.hasNext()) {
					SOSMimeMessage message = messageIterator.next();
					spooler_log.info(message.getSubject());
					Date messageDate = message.getSentDate();
					if (newestMail == null || messageDate.after(newestMail.getSentDate()))
						newestMail = message;
					performAction(message);
				}
			}
			if (receiver != null)
				receiver.closeFolder(true);
		}
		catch (Exception e) {
			String stateText = e.toString();
			sosLogger.warn("an error occurred while processing: " + stateText);
			spooler_job.set_state_text(stateText);
			sosLogger.info("Job " + spooler_job.name() + " step terminated with errors.");
			spooler_task.end();
		} // no error processing at job level
		return true;
	}

	private void performAction(final SOSMimeMessage message) throws Exception {
		String action = "";
		StringTokenizer t = new StringTokenizer(mailAction, ",");
		while (t.hasMoreTokens()) {
			action = t.nextToken();
			spooler_log.debug3("Action " + action + " will be performed.");
			action = action.toLowerCase();
			if (action.equals("dump"))
				dumpMessage(message);
			if (action.equals("order"))
				startOrder(message, mailSchedulerHost, mailSchedulerPort, mailJobchain, mailOrderId, mailOrderState, mailOrderTitle);
			if (action.equals("command"))
				executeCommand(message, mailSchedulerHost, mailSchedulerPort);
			if (action.equals("delete"))
				deleteMessage(message);
			if (mailSetSeen)
				message.setFlag(Flags.Flag.SEEN, true);
		}
	}

	private int min(final int a, final int b) {
		if (a < b)
			return a;
		return b;
	}

	private ArrayList <SOSMimeMessage> findMessages() throws Exception {
		ArrayList <SOSMimeMessage> messages = new ArrayList <SOSMimeMessage> ();
		try {
			sosLogger.debug3("Connecting to Mailserver " + mailHost + ":" + mailPort + "(" + mailServerType + ")...");
			receiver = new SOSMailReceiver(mailHost, mailPort, mailUser, mailPassword);
			receiver.setLogger(sosLogger);
			receiver.connect(mailServerType);
			if (mailServerTimeout > 0) {
				receiver.setTimeout(mailServerTimeout);
			}
			sosLogger.debug3("reading " + mailMessageFolder);
			Folder folder = receiver.openFolder(mailMessageFolder, receiver.READ_WRITE);
			int max = folder.getMessageCount();
			if (fromMail > max) {
				sosLogger.debug3("all messages found.");
				return null;
			}
			SubjectTerm term = null;
			Message[] msgs = null;
			Message[] msgs2 = null;
			sosLogger.debug3(max - fromMail + 1 + " messages left.");
			term = new SubjectTerm(mailSubjectFilter);
			msgs = folder.getMessages(fromMail, min(max, fromMail + nextMail));
			sosLogger.debug3(msgs.length + " messages found.");
			if (!mailSubjectFilter.equals("")) {
				sosLogger.debug3("looking for " + mailSubjectFilter);
				msgs2 = folder.search(term, msgs);
				sosLogger.debug3(msgs2.length + " messages found with " + mailSubjectFilter);
			}
			else {
				msgs2 = msgs;
				sosLogger.debug3(msgs2.length + " messages found");
			}
			if (msgs2.length > 0) {
				for (Message element : msgs2) {
					if (mailUseSeen && element.isSet(Flags.Flag.SEEN)) {
						sosLogger.info("message skipped, already seen: " + element.getSubject());
						continue;
					}
					SOSMimeMessage message = new SOSMimeMessage(element, sosLogger);
					// skip mails that do not match the subject pattern
					if (!mailSubjectPattern.equals("")) {
						Matcher subjectMatcher = subjectPattern.matcher(message.getSubject());
						if (!subjectMatcher.find()) {
							sosLogger.info("message skipped, subject does not match [" + mailSubjectPattern + "]: " + message.getSubject());
							continue;
						}
					}
					// skip mails whose body does not match the download link pattern
					if (!mailBodyPattern.equals("")) {
						Matcher bodyMatcher = bodyPattern.matcher(message.getPlainTextBody());
						if (!bodyMatcher.find()) {
							sosLogger.info("message skipped, no match found for  [" + mailBodyPattern + "]: " + message.getPlainTextBody());
							continue;
						}
					}
					messages.add(message);
				}
			}
			fromMail = fromMail + nextMail + 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			sosLogger.error(e.toString());
			throw new Exception("Error occured querying mail server. " + e);
		}
		return messages;
	}

	private void executeCommand(final SOSMimeMessage message, final String host_, final int port_) throws Exception {
		if (mailSchedulerHost.equals(spooler.hostname()) && mailSchedulerPort == spooler.tcp_port()) {
			sosLogger.debug3("...host/port is this host and port. Using API");
			spooler.execute_xml(message.getPlainTextBody());
		}
		else {
			executeXml(host_, port_, message.getPlainTextBody());
		}
	}

	private void dumpMessage(final SOSMimeMessage message) throws Exception {
		if (mailDumpDir.equals("")) {
			throw new Exception("No output directory specified.");
		}
		File messageFile = new File(mailDumpDir, message.getMessageId());
		sosLogger.debug3("saving message to file: " + messageFile.getAbsolutePath());
		// TODO implement Options "save_attachments" (default true)
		message.dumpMessageToFile(messageFile, true, false);
	}

	private void deleteMessage(final SOSMimeMessage message) throws Exception {
		sosLogger.debug3("deleting message : " + message.getSubject());
		message.deleteMessage();
	}

	private int getInt(final String s, final int d) throws Exception {
		int erg = d;
		try {
			erg = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			sosLogger.warn("Wrong value for " + s + ". Using default=" + d);
			erg = d;
		}
		return erg;
	}

	private String getParams(final String v, final String d) {
		String s = params.value(v);
		if (s.equals("")) {
			s = d;
		}
		return s;
	}

	private String getParams(final String v) throws Exception {
		String s = params.value(v);
		if (s.equals("")) {
			throw new Exception("missing value for parameter " + v);
		}
		return s;
	}

	private void startOrder(final SOSMimeMessage message, final String host_, final int port_, final String jobchain, final String id, final String state, final String title) throws Exception {
		Variable_set order_params = spooler.create_variable_set();
		sosLogger.debug3("....merge");
		order_params.merge(spooler_task.params());
		order_params.set_var("mail_from", message.getFrom());
		if (message.getFromName() != null) {
			order_params.set_var("mail_from_name", message.getFromName());
		}
		else {
			order_params.set_var("mail_from_name", "");
		}
		order_params.set_var("mail_message_id", message.getMessageId());
		order_params.set_var("mail_subject", message.getSubject());
		order_params.set_var("mail_body", message.getPlainTextBody());
		order_params.set_var("mail_send_at", message.getSentDateAsString());
		if (mailSchedulerHost.equals(spooler.hostname()) && mailSchedulerPort == spooler.tcp_port()) {
			sosLogger.debug3("...host/port is this host and port. Using API");
			Job_chain j = spooler.job_chain(jobchain);
			sosLogger.debug3("...jobchain " + jobchain + " created.");
			Order o = spooler.create_order();
			o.params().merge(order_params);
			// if (!id.equals(""))o.set_id(id);
			sosLogger.debug3("...order " + o.id() + " created.");
			if (!state.equals(""))
				o.set_state(state);
			if (!title.equals(""))
				o.set_title(title);
			j.add_order(o);
			sosLogger.debug3("...order added to " + jobchain);
		}
		else {
			startOrderXML(host_, port_, jobchain, id, state, title, order_params);
		}
	}

	private void startOrderXML(final String host_, final int port_, final String jobchain, String id, String state, String title, final Variable_set params_) throws Exception {
		sosLogger.debug3("Starting order " + id + " at " + jobchain + " with xml-command");
		if (host_.equals(""))
			throw new Exception("Missing host while starting order.");
		if (port_ == 0)
			throw new Exception("Missing port while starting order.");
		if (!id.equals(""))
			id = "id=" + id;
		if (!state.equals(""))
			state = "state=" + state;
		if (!title.equals(""))
			title = "state=" + title;
		String xml = "<add_order replace=yes " + id + " " + state + " job_chain=\"" + jobchain + "\">" + "<params>";
		if (params_ != null && params_.xml() != null && params_.xml().length() > 0) {
			String paramsXml = params_.xml();
			int begin = paramsXml.indexOf("<sos.spooler.variable_set>") + 26;
			int end = paramsXml.lastIndexOf("</sos.spooler.variable_set>");
			if (begin >= 26 && end >= 26)
				xml += paramsXml.substring(begin, end).replaceAll("variable", "param");
		}
		xml += "</params></add_order>";
		executeXml(host_, port_, xml);
	}

	private void executeXml(final String host_, final int port_, final String xml) throws Exception {
		SOSSchedulerCommand command;
		command = new SOSSchedulerCommand(host_, port_);
		command.setProtocol("udp");
		sosLogger.debug3("Trying connection to " + host_ + ":" + port_);
		command.connect();
		sosLogger.debug3("...connected");
		sosLogger.debug3("Sending add_order command:\n" + xml);
		command.sendRequest(xml);
	}
}
