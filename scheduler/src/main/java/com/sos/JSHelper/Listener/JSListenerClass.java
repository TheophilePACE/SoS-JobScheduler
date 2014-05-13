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
package com.sos.JSHelper.Listener;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
 * Stellt f�r Klassen einen Message-Mechanismus zur Verf�gung.
 * Andere Objekte k�nnen sich an Objekte dieser Klasse registrieren, um
 * von deren T�tigkeit informiert zu werden (Listener). Diese Listener m�ssen
 * von der Klasse JSListener abgeleitet sein.
 * Zus�tzlich gibt die Methode SignalDebug die M�glichkeit in Abh�ngikeit eines
 * maximalen Debug-Levels, Debug-Meldungen bis zu dem maximalen Debug Level
 * ausgeben zu lassen oder eben zu unterdr�cken (im Produktivbetrieb).
 * Der maximal Debug-Level ist die statische Eigenschaft intMaxDebugLevel dieser Klasse.
 * Wenn sie nicht gesetzt ist, dann werden keine Debug-Meldungen ausgegeben.
 *
 */
public class JSListenerClass   implements JSListener {

	public final String			conClassName			= "JSListenerClass";

	protected JSListener			JSListener				= null;
	public static Integer		intMaxDebugLevel		= 0;
	public static boolean		bolLogDebugInformation	= true;
	public static final Integer	DEBUG_LEVEL1			= new Integer(1);
	public static final Integer	DEBUG_LEVEL2			= new Integer(2);
	public static final Integer	DEBUG_LEVEL3			= new Integer(3);
	public static final Integer	DEBUG_LEVEL4			= new Integer(4);
	public static final Integer	DEBUG_LEVEL5			= new Integer(5);
	public static final Integer	DEBUG_LEVEL6			= new Integer(6);
	public static final Integer	DEBUG_LEVEL7			= new Integer(7);
	public static final Integer	DEBUG_LEVEL8			= new Integer(8);
	public static final Integer	DEBUG_LEVEL9			= new Integer(9);

	public JSListenerClass() {

	}

	/**
	 *
	 * \brief message
	 *
	 * \details

	 *
	 * @param pstrMsg
	 */

	public void message(final String pstrMsg) {
		String strT = pstrMsg;
		strT = getTime() + " " + strT;
		if (JSListener != null) {
			JSListener.message(strT);
		}
		else {
//			Logger objLog = Logger.getRootLogger();
//			if (objLog==null)
				System.out.println(strT);
//			else
//				objLog.info(objLog.getName() + ": " + pstrMsg);
		}
	}

	/**
	 *
	 * \brief Listener
	 *
	 * \details
	 *
	 * \return JSListener
	 *
	 * @return
	 */
	public JSListener Listener() {

		@SuppressWarnings("unused")
		final String	conMethodName	= conClassName + "::Listener";

		return JSListener;
	} // public JSListener Listener}
	/**
	 *
	 * \brief registerMessageListener
	 *
	 * \details
	 *
	 *
	 * @param l
	 */
	public void registerMessageListener(final JSListener l) {
		JSListener = l;
	}

	/*
		public void ShowStackTrace (Exception e) {

			StackTraceElement arrStack [] = e.getStackTrace();
			for (int i = 0; i <= arrStack.length; i++) {
				StackTraceElement objS = arrStack[i];
				message (objS.toString());
			}
		}
	*/
	/**
	 *
	 * \brief SignalAbort
	 *
	 * \details

	 * \return void
	 *
	 * @param strS
	 * @throws Exception
	 */

	public void SignalAbort(final String strS) throws Exception {
		String strT = " ###ProgramAbort### ";
		strT = strT + strS + strT;
		message(strT);
		final JobSchedulerException expE = new JobSchedulerException(strT);

		throw expE;
	}

	/**
	 *
	 * \brief SignalInfo
	 *
	 * \details

	 * \return void
	 *
	 * @param strS
	 */
	public void SignalInfo(final String strS) { // throws Exception {
		message(strS);
	}

	/**
	 *
	 * \brief SignalError
	 *
	 * \details

	 * \return void
	 *
	 * @param expE
	 * @param strS
	 * @throws Exception
	 */
	public void SignalError(final JobSchedulerException expE, final String strS) {
		String strT = " ### Error ### ";
		strT = strT + strS + strT;
		message(strT);
		if (expE != null) {
			expE.Message(strS);
			expE.Status(JobSchedulerException.ERROR);
			throw expE;
		}
	}

	/**
	 *
	 * \brief SignalError
	 *
	 * \details

	 * \return void
	 *
	 * @param strS
	 * @throws Exception
	 */
	public void SignalError(final String strS) {
		this.SignalError(new JobSchedulerException(strS), strS);
	}

	/**
	 *
	 * \brief SignalDebug
	 *
	 * \details

	 * \return void
	 *
	 * @param pstrDebugMessage
	 */
	public void SignalDebug(final String pstrDebugMessage) {
		this.SignalDebug(pstrDebugMessage, 5);
	}

	/**
	 *
	 * \brief SignalDebug
	 *
	 * \details

	 * \return void
	 *
	 * @param pstrMsg
	 * @param pintDebugLevel
	 */
	public void SignalDebug(final String pstrMsg, final Integer pintDebugLevel) {
		final String strT = pstrMsg;
		if (!JSListenerClass.bolLogDebugInformation) {
			return;
		}
		if (intMaxDebugLevel == null) {
			intMaxDebugLevel = JSListenerClass.DEBUG_LEVEL9;
		}
		if (pintDebugLevel.intValue() > JSListenerClass.intMaxDebugLevel.intValue()) {
			return;
		}
		//		strT = pstrMsg.replaceAll("\n", "\nDEBUG(" + pintDebugLevel + "):");
		message("DEBUG(" + pintDebugLevel + "):>>> " + strT + " <<<");
	}

	/**
	 *
	 * \brief getDateTimeFormatted
	 *
	 * \details

	 * \return String
	 *
	 * @param pstrEditMask
	 * @return
	 */
	public String getDateTimeFormatted(final String pstrEditMask) {
		final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(pstrEditMask);
		final java.util.Calendar now = java.util.Calendar.getInstance();
		return formatter.format(now.getTime()).toString();

	}

	/**
	 *
	 * \brief getISODate
	 *
	 * \details

	 * \return String
	 *
	 * @return
	 */
	public String getISODate() {
		return getDateTimeFormatted("yyyy-MM-dd'T'HH:mm:ss");
	}

	/**
	 *
	 * \brief getTime
	 *
	 * \details

	 * \return String
	 *
	 * @return
	 */
	public String getTime() {
		return getDateTimeFormatted("HH:mm:ss");
	}

}
