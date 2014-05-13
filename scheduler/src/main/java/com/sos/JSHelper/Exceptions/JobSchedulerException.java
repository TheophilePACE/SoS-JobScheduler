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
package com.sos.JSHelper.Exceptions;

import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.i18n.annotation.I18NResourceBundle;
import com.sos.localization.SOSMsg;

/**
 * <p>
 * Base-Class for Dataswitch-Exceptions
 * </p>
 */
@I18NResourceBundle(baseName = "com.sos.JSHelper.messages", defaultLocale = "en")
public class JobSchedulerException extends RuntimeException {
	@SuppressWarnings("unused")
	private final String		conSVNVersion				= "$Id: JobSchedulerException.java 20984 2013-09-04 08:53:21Z ur $";

	public static boolean	gflgStackTracePrinted		= false;

	private final String		conClassName				= "JobSchedulerException";

	public final static int		UNDEFINED					= -1;
	public final static int		SUCCESS						= 0;
	public final static int		WARNING						= 1;
	public final static int		ERROR						= 2;
	public final static int		PENDING						= 3;
	public final static int		SKIPPROCESSING				= 4;

	public final static String	CategoryIDocProcessing		= "IDocs";

	public final static String	CategoryDatabase			= "Database";
	public final static String	CategoryFileTransfer		= "FileTransfer";
	public final static String	CategoryFileHandling		= "FileHandling";
	public final static String	CategoryJobStart			= "JobStart";
	public final static String	CategoryOptions				= "Options";
	public final static String	CategoryIDocs				= "IDocs";

	public final static String	TypeSQL						= "SQL";
	public final static String	TypeTL						= "TrafficLight"; 
	public final static String	TypeSplitIDocs				= "Splitter";
	public final static String	TypeOptionMissing			= "OptionValueMissing";
	public final static String	TypeExport					= "Export";
	public final static String	TypeImport					= "Import";

	public final static int		conErrorNumberJobStopped	= 123;
	public final static int		conErrorNumberFtpError		= 77;

	/** int Status: Get the status of this Exception */
	private int					intStatus					= SUCCESS;
	private static final long	serialVersionUID			= 1L;
	private Exception			nestedException;
	public boolean				flgStackTracePrinted		= false;

	/** String Message: The Message assigned to this Exception */
	protected String			strMessage					= null;

	private int					intErrorNumber				= 0;
	protected SOSMsg			objSOSMsg					= null;

	public static String		LastErrorMessage			= "";

	public JobSchedulerException() {
		super("*** JobSchedulerException ***");
	}

	/**
	 *
	 * \brief ErrorNumber
	 *
	 * \details
	 *
	 * \return void
	 *
	 * @param pintErrorNumber
	 */
	public void ErrorNumber(final int pintErrorNumber) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::ErrorNumber";

		intErrorNumber = pintErrorNumber;

	} // public void ErrorNumber

	/**
	 *
	 * \brief ErrorNumber
	 *
	 * \details
	 *
	 * \return int
	 *
	 * @return
	 */
	public int ErrorNumber() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::ErrorNumber";

		return intErrorNumber;
	} // public int ErrorNumber}

	/**
	 * Construtor with message.
	 *
	 * @param pstrMessage
	 *            the message of the exception
	 */
	public JobSchedulerException(final String pstrMessage) {
		super(pstrMessage);
		setMessage(pstrMessage);
		this.Status(JobSchedulerException.ERROR);
	}

	public JobSchedulerException(final SOSMsg pobjMsg) {
		super(pobjMsg.get());
		objSOSMsg = pobjMsg;
		setMessage(pobjMsg.get());

		this.Status(JobSchedulerException.ERROR);
	}

	/**
	 * Instantiate the exception with a nested exception
	 *
	 * @param pstrMessage
	 *            the message of the exception
	 * @param e
	 *            the exception to be wrapped
	 */
	public JobSchedulerException(final String pstrMessage, final Exception e) {
		super(pstrMessage);
		setMessage(pstrMessage + " (" + e.getLocalizedMessage() + ")");
		saveException(e);
	}

	private void saveException(final Exception e) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::saveExeption";

		nestedException = e;
		if (e instanceof JobSchedulerException) {
			JobSchedulerException objXE = (JobSchedulerException) e;
			if (objXE.flgStackTracePrinted == false) {
				e.printStackTrace(System.err);
				objXE.flgStackTracePrinted = true;
			}
		}
		else {
			if (gflgStackTracePrinted == false) {
				e.printStackTrace(System.err);
				gflgStackTracePrinted = true;
			}
		}

	} // private void saveExeption

	public JobSchedulerException(final SOSMsg pobjMsg, final Exception e) {
		super(pobjMsg.get());
		strMessage = pobjMsg.get();
		objSOSMsg = pobjMsg;
		setMessage(strMessage + " (" + e.getLocalizedMessage() + ")");
		saveException(e);
	}

	public JobSchedulerException(final Exception e) {
		super(e.getLocalizedMessage());
		setMessage(e.getLocalizedMessage());
		saveException(e);
	}

	private void setMessage(final String pstrMsg) {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::setMessage";

		strMessage = pstrMsg;
		LastErrorMessage = pstrMsg;

	} // private void setMessage

	/**
	 * @return the nested exception
	 */
	public Exception getNestedException() {
		return nestedException;
	}

	public JobSchedulerException setNestedException(final Exception e) {
		nestedException = e;
		return this;
	}

	/**
	 * Status - Get the status of this Exception
	 *
	 * Getter: Get the status of this Exception
	 *
	 * Example:
	 *
	 * @return Returns the Status.
	 */
	public int Status() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Status";
		if (intStatus == UNDEFINED) {
			intStatus = PENDING;
		}
		return intStatus;
	} // int Status()

	/**
	 *
	 * \brief StatusAction
	 *
	 * \details
	 *
	 * \return String
	 *
	 * @return
	 */
	public String StatusAction() {
		String strT;

		switch (intStatus) {
			case JobSchedulerException.ERROR:
				strT = "The Workflow/Task ended with Error-Status. ";
				break;

			case JobSchedulerException.PENDING:
				strT = "The Workflow/Task ended in Error.\n" + "The Workflow may not be completed.\n" + "further scheduling of this workflow is on hold";
				break;

			case JobSchedulerException.WARNING:
				strT = "The Workflow/Task ended with Error-Status. ";
				break;

			case JobSchedulerException.SUCCESS:
				strT = "SUCCESS";
				break;

			case JobSchedulerException.SKIPPROCESSING:
				strT = "SKIPPROCESSING";
				break;

			case JobSchedulerException.UNDEFINED:
				strT = "UNDEFINED";
				break;

			default:
				strT = "????";
				break;
		}

		return strT;

	}

	/**
	 * StatusText - returns the text-representation of the job-ending status
	 *
	 * Getter: StatusText
	 *
	 * Example:
	 *
	 * @return Returns the Job-status as text
	 */

	public String StatusText() {
		String strT;

		switch (intStatus) {
			case JobSchedulerException.ERROR:
				strT = "ERROR";
				break;

			case JobSchedulerException.PENDING:
				strT = "PENDING";
				break;

			case JobSchedulerException.WARNING:
				strT = "WARNING";
				break;

			case JobSchedulerException.SUCCESS:
				strT = "SUCCESS";
				break;

			case JobSchedulerException.SKIPPROCESSING:
				strT = "SKIPPROCESSING";
				break;

			case JobSchedulerException.UNDEFINED:
				strT = "UNDEFINED";
				break;

			default:
				strT = "????";
				break;
		}

		return strT;

	}

	/**
	 * Status - Get the status of this Exception
	 *
	 * Setter: Get the status of this Exception
	 *
	 * @param pobjStatus: The int Status to set.
	 */
	public JobSchedulerException Status(final int pintStatus) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Status";
		intStatus = pintStatus;
		return this;
	} // public void Status(int pobjStatus)

	/*
	 * ---------------------------------------------------------------------------
	 * <method type="smcw" version="1.0"> <name>Category</name> <title>The
	 * Catogory assigned to this Exception</title> <description> <para> The
	 * Catogory assigned to this Exception </para> <para> Initial-Wert (Default)
	 * ist "null" (ohne Anf�hrungszeichen). </para> <mandatory>true</mandatory>
	 * </description> <params> <param name="param1" type=" "
	 * ref="byref|byvalue|out" > <para> </para> </param> </params> <keywords>
	 * <keyword>Exception</keyword> </keywords> <categories>
	 * <category>Exception</category> </categories> </method>
	 * ----------------------------------------------------------------------------
	 */
	/** String Category: The Catogory assigned to this Exception */
	private String	strCategory	= "???";

	/**
	 * Category - The Category assigned to this Exception
	 *
	 * Getter: The Category assigned to this Exception
	 *
	 * Example:
	 *
	 * @return Returns the Category.
	 */
	public String Category() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Category";
		return strCategory;
	} // String Category()

	/**
	 * Category - The Category assigned to this Exception
	 *
	 * Setter: The Category assigned to this Exception
	 *
	 * @param pstrCategory: The String Category to set.
	 */
	public JobSchedulerException Category(final String pstrCategory) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Category";
		strCategory = pstrCategory;
		return this;
	} // public void Category(String pstrCategory)

	/*
	 * ---------------------------------------------------------------------------
	 * <method type="smcw" version="1.0"> <name>Typ</name> <title>The Type
	 * assigned to this Exception</title> <description> <para> The Type
	 * assigned to this Exception </para> <para> Initial-Wert (Default) ist
	 * "null" (ohne Anf�hrungszeichen). </para> <mandatory>true</mandatory>
	 * </description> <params> <param name="param1" type=" "
	 * ref="byref|byvalue|out" > <para> </para> </param> </params> <keywords>
	 * <keyword>Exception</keyword> </keywords> <categories>
	 * <category>Exception</category> </categories> </method>
	 * ----------------------------------------------------------------------------
	 */
	/** String Typ: The Type assigned to this Exception */
	private String	strTyp	= "???";

	/*
	 * ! Typ - The Type assigned to this Exception
	 *
	 * Getter: The Type assigned to this Exception
	 *
	 * Example:
	 *
	 * @return Returns the Typ.
	 */
	public String Typ() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Typ";
		return strTyp;
	} // String Typ()

	/**
	 * Typ - The Type assigned to this Exception
	 *
	 * Setter: The Type assigned to this Exception
	 *
	 * @param pstrTyp: The String Typ to set.
	 */
	public JobSchedulerException Typ(final String pstrTyp) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Typ";
		strTyp = pstrTyp;
		return this;
	} // public void Typ(String pstrTyp)

	/*
	 * ---------------------------------------------------------------------------
	 * <method type="smcw" version="1.0"> <name>Message</name> <title>The
	 * Message assigned to this Exception</title> <description> <para> The
	 * Message assigned to this Exception </para> <para> Initial-Wert (Default)
	 * ist "null" (ohne Anf�hrungszeichen). </para> <mandatory>true</mandatory>
	 * </description> <params> <param name="param1" type=" "
	 * ref="byref|byvalue|out" > <para> </para> </param> </params> <keywords>
	 * <keyword>Exception</keyword> </keywords> <categories>
	 * <category>Exception</category> </categories> </method>
	 * ----------------------------------------------------------------------------
	 */
	/**
	 * Message - The Message assigned to this Exception
	 *
	 * Getter: The Message assigned to this Exception
	 *
	 * Example:
	 *
	 * @return Returns the Message.
	 */
	public String Message() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Message";
		return strMessage;
	} // String Message()

	/**
	 * Message - The Message assigned to this Exception
	 *
	 * Setter: The Message assigned to this Exception
	 *
	 * @param pstrMessage: The String Message to set.
	 */
	public JobSchedulerException Message(final String pstrMessage) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::Message";
		strMessage = pstrMessage;
		return this;
	} // public void Message(String pstrMessage)

	/**
	 * ExceptionText - returns a full description of what happened
	 *
	 *
	 * @return description of the exception
	 */
	public String ExceptionText() {
		String strT = null;

		strT = eMailSubject() + "\n";
		strT += getText("Message ", strMessage);

		strT += getText("Category", strCategory);
		strT += getText("Type    ", strTyp);

		if (objOptions != null) {
			strT += "\nOptions active:";
			strT += objOptions.toString();
		}

		if (strAdditionalText != null) {
			strT += "\nAdditional Text:\n" + strAdditionalText + "\n";
		}

		strT += "\nfurther Actions:\n\n";
		strT += StatusAction() + "\n";

		if (nestedException != null) {
			strT += "\n" + nestedException.getMessage();
			strT += "\n" + nestedException.toString();
			strT += "\nStackTrace of nested Exception:\n" + StackTrace2String(nestedException) + "\n";
		}
		else {
			strT += "\nStackTrace :\n" + StackTrace2String(this) + "\n";
		}
		return strT;
	}

	protected String getText(final String conWhat, final String pstrS) {

		if (pstrS != null && pstrS.length() > 0) {
			return conWhat + ": " + pstrS + "\n";
		}
		else {
			return "";
		}
	}

	public String StackTrace2String(final Exception e) {

		String strT = null;
		final StackTraceElement arrStack[] = e.getStackTrace();
		for (final StackTraceElement objS : arrStack) {
			strT += objS.toString() + "\n";
		}

		return strT;
	} // void ShowStackTrace (Exception e)

	private JSOptionsClass	objOptions	= null;

	public JobSchedulerException Options(final JSOptionsClass pobjOptions) {
		objOptions = pobjOptions;
		return this;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * <method type="smcw" version="1.0"> <name>AdditionalText</name>
	 * <title>AdditionalText</title> <description> <para> AdditionalText
	 * </para> <para> Initial-Wert (Default) ist "null" (ohne
	 * Anf�hrungszeichen). </para> <mandatory>true</mandatory> </description>
	 * <params> <param name="param1" type=" " ref="byref|byvalue|out" > <para>
	 * </para> </param> </params> <keywords> <keyword>Exception</keyword>
	 * <keyword>JobStart</keyword> </keywords> <categories> <category>Exception</category>
	 * </categories> </method>
	 * ----------------------------------------------------------------------------
	 */
	/** String AdditionalText: AdditionalText */
	private String	strAdditionalText	= null;

	/*
	 * ! AdditionalText - AdditionalText
	 *
	 * Getter: AdditionalText
	 *
	 * Example:
	 *
	 * @return Returns the AdditionalText.
	 */
	public String AdditionalText() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::AdditionalText";
		return strAdditionalText;
	} // String AdditionalText()

	/*
	 * ! AdditionalText - AdditionalText
	 *
	 * Setter: AdditionalText
	 *
	 * @param pstrAdditionalText: The String AdditionalText to set.
	 */
	public JobSchedulerException AdditionalText(final String pstrAdditionalText) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::AdditionalText";
		strAdditionalText = pstrAdditionalText;
		return this;
	} // public void AdditionalText(String pstrAdditionalText)

	/**
	 * overrides the getMessage() and reports the nested exception, if it
	 * exists.
	 *
	 * @return the exception message.
	 */
	/*
	 * public String getMessage() {
	 *
	 * if (nestedException == null) { return super.getMessage(); } else { //
	 * return the nested exception if exists return super.getMessage() + ": " +
	 * nestedException.toString(); } }
	 */

	/* ---------------------------------------------------------------------------
	<method type="smcw" version="1.0">
	<name>eMailSubject</name>
	<title>eMailSubject</title>
	<description>
	<para>
	eMailSubject
	</para>
	<para>
	Initial-Wert (Default) ist "null" (ohne Anf�hrungszeichen).
	</para>
	<mandatory>true</mandatory>
	</description>
	<params>
		<param name="param1" type=" " ref="byref|byvalue|out" >
			<para>
			</para>
		</param>
	</params>
	<keywords>
		<keyword>Options</keyword>
		<keyword>ResetPending</keyword>
		<keyword>WorkflowModelID</keyword>
	</keywords>
	<categories>
	<category>Workflow</category>
	<category>Options</category>
	</categories>
	</method>
	---------------------------------------------------------------------------- */
	/** String streMailSubject: eMailSubject */
	private String	streMailSubject	= null;

	/**
	 * eMailSubject - eMailSubject
	 *
	 * Getter: eMailSubject
	 *
	 * Example:
	 *
	 * @return Returns the eMailSubject.
	 */
	public String eMailSubject() {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::eMailSubject";
		if (streMailSubject == null) {
			streMailSubject = "JS: " + StatusText() + " - " + strMessage;
		}
		else {
			streMailSubject = "JS: " + StatusText() + " - " + streMailSubject;
		}
		return streMailSubject;
	} // String eMailSubject()

	/**
	 * eMailSubject - eMailSubject
	 *
	 * Setter: eMailSubject
	 *
	 * @param pstreMailSubject: The String eMailSubject to set.
	 */
	public JobSchedulerException eMailSubject(final String pstreMailSubject) {
		@SuppressWarnings("unused")
		final String conMethodName = conClassName + "::eMailSubject";
		streMailSubject = pstreMailSubject;
		return this;
	} // public void eMailSubject(String pstreMailSubject)

}
