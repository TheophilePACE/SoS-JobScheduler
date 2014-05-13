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
package com.sos.jitl.eventing;

import static com.sos.scheduler.messages.JSMessages.JSJ_F_107;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_110;
import static com.sos.scheduler.messages.JSMessages.JSJ_I_111;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sos.scheduler.command.SOSSchedulerCommand;
import sos.xml.SOSXMLXPath;

import com.sos.JSHelper.Basics.JSJobUtilitiesClass;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.JSHelper.Options.SOSOptionTime;

/**
 * \class 		JSEventsClient - Workerclass for "Submit and Delete Events"
 *
 * \brief AdapterClass of JSEventsClient for the SOSJobScheduler
 *
 * This Class JSEventsClient is the worker-class.
 *

 *
 * see \see C:\Users\KB\AppData\Local\Temp\scheduler_editor-4778075809216214864.html for (more) details.
 *
 * \verbatim ;
 * mechanicaly created by C:\ProgramData\sos-berlin.com\jobscheduler\latestscheduler\config\JOETemplates\java\xsl\JSJobDoc2JSWorkerClass.xsl from http://www.sos-berlin.com at 20130109134235
 * \endverbatim
 */
@SuppressWarnings("deprecation")
public class JSEventsClient extends JSJobUtilitiesClass<JSEventsClientOptions> {
	private final String					conClassName	= this.getClass().getSimpleName();
	private final Logger					logger			= Logger.getLogger(this.getClass());
	private final String					conSVNVersion	= "$Id: JSEventsClient.java 18220 2012-10-18 07:46:10Z kb $";

	private final HashMap<String, String>	eventParameters	= new HashMap<String, String>();

	/**
	 *
	 * \brief JSEventsClient
	 *
	 * \details
	 *
	 */
	public JSEventsClient() {
		super();
		this.Options();
	}

	/**
	 *
	 * \brief Options - returns the JSEventsClientOptionClass
	 *
	 * \details
	 * The JSEventsClientOptionClass is used as a Container for all Options (Settings) which are
	 * needed.
	 *
	 * \return JSEventsClientOptions
	 *
	 */
	@Override
	public JSEventsClientOptions Options() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Options"; //$NON-NLS-1$

		if (objOptions == null) {
			objOptions = new JSEventsClientOptions();
		}
		return objOptions;
	}

	/**
	 *
	 * \brief Execute - Start the Execution of JSEventsClient
	 *
	 * \details
	 *
	 * For more details see
	 *
	 * \see JobSchedulerAdapterClass
	 * \see JSEventsClientMain
	 *
	 * \return JSEventsClient
	 *
	 * @return
	 */
	public JSEventsClient Execute() throws Exception {
		final String conMethodName = conClassName + "::Execute";

		logger.debug(JSJ_I_110.get(conMethodName));
		logger.debug(conSVNVersion);

		try {
			Options().CheckMandatory();
			logger.debug(Options().dirtyString());

			try {
				if (objOptions.EventParameter.isDirty() == true) {
					eventParameters.put(objOptions.EventParameter.getShortKey(), objOptions.EventParameter.Value());
					String[] strEP = objOptions.EventParameter.Value().split(";");
					HashMap <String, String> objH = objOptions.Settings();
					for (String strParamName : strEP) {
						strParamName = strParamName.trim();
						String strValue = objH.get(strParamName);
						if (strValue != null) {
							eventParameters.put(strParamName, strValue.trim());
						}
					}
				}
				String action = objOptions.scheduler_event_action.Value();
				String strEventIDs = objOptions.id.Value();
				if (strEventIDs.length() > 0) {
					String strA[] = objOptions.id.Value().split(";");
					for (String strEventID : strA) {
						strEventID = strEventID.trim();
						String addOrder = createAddOrder(action, strEventID, eventParameters);
						submitToSupervisor(addOrder);
					}
				}
				// Check for del_events
				if (objOptions.del_events.isDirty()) {
					String strA[] = objOptions.del_events.Value().split(";");
					action = "remove";
					for (String strEventID : strA) {
						String addOrder = createAddOrder(action, strEventID.trim(), eventParameters);
						submitToSupervisor(addOrder);
					}
				}
			}
			catch (Exception e) {
				throw new JobSchedulerException("Error submitting event order: " + e, e);
			}
		}
		catch (Exception e) {
			String strM =  JSJ_F_107.get(conMethodName);
			throw new JobSchedulerException(strM, e);
		}
		finally {
			logger.debug(JSJ_I_111.get(conMethodName)) ;
		}

		return this;
	}

	private String createAddOrder(final String action, final String eventId, final Map<String, String> eventParameters1) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document addOrderDocument = docBuilder.newDocument();
			Element addOrderElement = addOrderDocument.createElement("add_order");
			addOrderDocument.appendChild(addOrderElement);
			addOrderElement.setAttribute("job_chain", objOptions.supervisor_job_chain.Value());
			Element paramsElement = addOrderDocument.createElement("params");
			addOrderElement.appendChild(paramsElement);
			addParam(paramsElement, "action", action);
			addParam(paramsElement, "event_id", eventId);
			addParam(paramsElement, "remote_scheduler_host", objOptions.scheduler_event_handler_host.Value());
			addParam(paramsElement, "remote_scheduler_port", objOptions.scheduler_event_handler_port.Value());
			addParam(paramsElement, "job_chain", objOptions.supervisor_job_chain.Value());
			String orderId = "";
			String jobName = "";
			addParam(paramsElement, "order_id", orderId);
			addParam(paramsElement, "job_name", jobName);
			addParam(paramsElement, "event_class", objOptions.EventClass.Value());
			addParam(paramsElement, "exit_code", objOptions.scheduler_event_exit_code.Value());
			addParam(paramsElement, "created", SOSOptionTime.getCurrentTimeAsString());
			addParam(paramsElement, "expires", objOptions.scheduler_event_expires.Value());

			@SuppressWarnings("rawtypes")
			Iterator keyIterator = eventParameters1.keySet().iterator();
			while (keyIterator.hasNext()) {
				String name = keyIterator.next().toString();
				String value = eventParameters1.get(name).toString();
				addParam(paramsElement, name, value);
			}
			StringWriter out = new StringWriter();
			OutputFormat of = new OutputFormat(addOrderDocument);
			of.setEncoding("iso-8859-1");
			XMLSerializer serializer = new XMLSerializer(out, of);
			serializer.serialize(addOrderDocument);
			String strOrdertxt = out.toString();
			return strOrdertxt;
		}
		catch (Exception e) {
			throw new JobSchedulerException("Error creating add_order xml: " + e, e);
		}
	}

	private void addParam(final Element paramsElement, final String name, final String value) {
		if (value != null && value.length() > 0) {
			Element paramElement = paramsElement.getOwnerDocument().createElement("param");
			paramElement.setAttribute("name", name);
			paramElement.setAttribute("value", value);
			paramsElement.appendChild(paramElement);
		}
	}

	private void submitToSupervisor(String xml) throws Exception {
		try {
			if (xml.indexOf("<?xml") == -1) {
				// TODO encoding as a parameter
				xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
			}
			logger.debug("Sending add_order to the EventService:\n" + xml);
			String answer = null;
			if (objOptions.scheduler_event_handler_host.isDirty() && objOptions.scheduler_event_handler_port.value() > 0) {
				SOSSchedulerCommand schedulerCommand = new SOSSchedulerCommand();
				schedulerCommand.setHost(objOptions.scheduler_event_handler_host.Value());
				schedulerCommand.setPort(objOptions.scheduler_event_handler_port.value());
				// TODO protocol as Parameter
				schedulerCommand.setProtocol("tcp");
				logger.debug(".. connecting to EventService " + schedulerCommand.getHost() + ":" + schedulerCommand.getPort());
				schedulerCommand.connect();
				schedulerCommand.sendRequest(xml);
				answer = schedulerCommand.getResponse();
				logger.debug(answer.replaceAll(Pattern.quote("\n"), ""));
			}
			else {
				logger.info("No supervisor configured, submitting event to this JobScheduler.");
				answer = objJSCommands.executeXML(xml);
			}

			if (answer != null && answer.length() > 0) {
				SOSXMLXPath xAnswer = new SOSXMLXPath(new StringBuffer(answer));
				String errorText = xAnswer.selectSingleNodeValue("//ERROR/@text");
				if (errorText != null && errorText.length() > 0) {
					throw new JobSchedulerException("EventService returned an error: " + errorText);
				}
			}
		}
		catch (Exception e) {
			throw new JobSchedulerException("Failed to submit event: " + e, e);
		}
	}

	public void init() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::init";
		doInitialize();
	}

	private void doInitialize() {
	} // doInitialize

} // class JSEventsClient
