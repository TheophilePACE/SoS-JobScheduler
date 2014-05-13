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
package com.sos.JSHelper.Options;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
* \class SOSOptionTime
*
* \brief SOSOptionTime -
*
* \details
*
*         <note language="en">
          <div xmlns="http://www.w3.org/1999/xhtml">
Specifies the minimum age of one or multiple files.
If the files is newer then it is classified as non-existing.<br/>
<br/>
Possible values:<br/>
0 (disabled)<br/>
seconds<br/>
hours:minutes<br/>
hours:minutes:seconds
          </div>
        </note>

        <note language="de">
          <div xmlns="http://www.w3.org/1999/xhtml">
Vereinbart das Mindestalter einer oder mehrerer Dateien.
Ist eine Datei j�nger, dann gilt sie als nicht vorhanden.<br/>
<br/>
M�gliche Werte sind:<br/>
0 (parameter wird ignoriert)<br/>
Sekunden<br/>
Stunden:Minuten<br/>
Stunden:Minuten:Sekunden
					</div>
        </note>

* \section SOSOptionTime.java_intro_sec Introduction
*
* \section SOSOptionTime.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* @version $Id: SOSOptionTime.java 18717 2013-01-02 21:34:03Z kb $28.08.2010
* \see reference
*
* Created on 28.08.2010 22:36:43
 */

/**
 * @author KB
 *
 */
public class SOSOptionTime extends SOSOptionInteger {

	private static final long	serialVersionUID	= 6687670638160800096L;
	@SuppressWarnings("unused")
	private final String		conClassName		= "SOSOptionTime";
	@SuppressWarnings("hiding")
	public final String			ControlType			= "timetext";

	public static String		dateTimeFormat		= new String("yyyy-MM-dd HH:mm:ss");

	/**
	 * \brief SOSOptionTime
	 *
	 * \details
	 *
	 * @param pPobjParent
	 * @param pPstrKey
	 * @param pPstrDescription
	 * @param pPstrValue
	 * @param pPstrDefaultValue
	 * @param pPflgIsMandatory
	 */
	public SOSOptionTime(final JSOptionsClass pPobjParent, final String pPstrKey, final String pPstrDescription, final String pPstrValue,
			final String pPstrDefaultValue, final boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
	}

	public static String getCurrentTimeAsString() {
		String strT = "n.a.";
		try {
			strT = getCurrentTimeAsString(dateTimeFormat);
		}
		catch (Exception e) {
		}
		return strT;
	}

	public static String getCurrentTimeAsString(final String dateTimeFormat) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
		formatter.setLenient(true);
		Calendar now = Calendar.getInstance();
		return formatter.format(now.getTime());
	}

	public static String getCurrentDateAsString() throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(true);
		Calendar now = Calendar.getInstance();
		return formatter.format(now.getTime());
	}

	/**
	 *
	 * \brief getTimeAsSeconds
	 *
	 * \details
	 *
	 * \return int
	 *
	 * @return time as seconds
	 */
	public int getTimeAsSeconds() {
		int intSeconds = 0;
		int[] intM = { 1, 60, 3600, 3600 * 24 };

		String[] strT = strValue.split(":");

		int j = 0;
		for (int i = strT.length - 1; i >= 0; i--) {
			intSeconds += new Integer(strT[i]) * intM[j++];
		}

		return intSeconds;
	} // public int getTimeAsSeconds()

	/**
	 *
	 * \brief calculateFileAge
	 *
	 * \details
	 *
	 * \return long
	 *
	 * @return age in milli-seconds
	 */
	public long calculateFileAge() {
		// TODO implement this method in JSFile
		long age = 0;
		if (isNotEmpty(strValue)) {
			if (strValue.indexOf(":") > -1) {
				String[] timeArray = strValue.split(":");
				long hours = Long.parseLong(timeArray[0]);
				long minutes = Long.parseLong(timeArray[1]);
				long seconds = 0;
				if (timeArray.length > 2) {
					seconds = Long.parseLong(timeArray[2]);
				}
				age = hours * 3600000 + minutes * 60000 + seconds * 1000;
			}
			else {
				age = Long.parseLong(strValue) * 1000;
			}
		}
		return age;
	}

	public String getTimeAsString(final long lngValue) {
		String strT = "";

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strT = df.format(lngValue);

		return strT;
	}
}
