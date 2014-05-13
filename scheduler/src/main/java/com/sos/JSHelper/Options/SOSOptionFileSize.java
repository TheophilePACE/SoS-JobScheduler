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

/**
* \class SOSOptionFileSize 
* 
* \brief SOSOptionFileSize - 
* 
* \details
*
*        <note language="en">
          <div xmlns="http://www.w3.org/1999/xhtml">
            Specifies the size of one or multiple files in bytes:
<br/>
Possible Values:<br/>
-1 (disabled)<br/>
number (Byte), e.g. 40<br/>
numberKB, e.g. 5KB (case insensitive)<br/>
numberMB, e.g. 20MB (case insensitive)<br/>
numberGB, e.g. 10GB (case insensitive)
          </div>
        </note>
*
*
*        <note language="de">
          <div xmlns="http://www.w3.org/1999/xhtml">
            Vereinbart die minimale Gr��e einer oder mehrerer Dateien in Bytes:
            unterschreitet die Gr��e einer der Dateien diesen Wert,
            dann gilt sie als nicht vorhanden.<br/>
<br/>
M�gliche Werte sind:<br/>
-1 (parameter wird ignoriert)<br/>
Zahl (Byte), z.B. 40<br/>
ZahlKB, z.B. 5KB (Gro�/Kleinschreibung wird ignoriert)<br/>
ZahlMB, z.B. 20MB (Gro�/Kleinschreibung wird ignoriert)<br/>
ZahlGB, z.B. 10GB (Gro�/Kleinschreibung wird ignoriert)
					</div>
        </note>

* \section SOSOptionFileSize.java_intro_sec Introduction
*
* \section SOSOptionFileSize.java_samples Some Samples
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
* @version $Id: SOSOptionFileSize.java 14985 2011-08-21 08:51:34Z kb $28.08.2010
* \see reference
*
* Created on 28.08.2010 22:44:23
 */

/**
 * @author KB
 *
 */
public class SOSOptionFileSize extends SOSOptionString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1449599231135132925L;
	@SuppressWarnings("unused")
	private final String	conClassName	= "SOSOptionFileSize";

	/**
	 * \brief SOSOptionFileSize
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
	public SOSOptionFileSize(JSOptionsClass pPobjParent, String pPstrKey, String pPstrDescription, String pPstrValue, String pPstrDefaultValue,
			boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param filesize   X Bytes, X KB, X MB, X GB   
	 * @return long value
	 * @throws Exception
	 */
	public  long getFileSize() throws Exception {

		long lngSize;
		String strFilesize = this.strValue;
		
		if (strFilesize == null || strFilesize.trim().length() == 0)
			return -1;

		if (strFilesize.matches("-1"))
			return -1;

		if (strFilesize.matches("[\\d]+")) {
			lngSize = Long.parseLong(strFilesize);
		}
		else {
			if (strFilesize.matches("^[\\d]+[kK][bB]$"))
				lngSize = Long.parseLong(strFilesize.substring(0, strFilesize.length() - 2)) * 1024;
			else
				if (strFilesize.matches("^[\\d]+[mM][bB]$"))
					lngSize = Long.parseLong(strFilesize.substring(0, strFilesize.length() - 2)) * 1024 * 1024;
				else
					if (strFilesize.matches("^[\\d]+[gG][bB]$"))
						lngSize = Long.parseLong(strFilesize.substring(0, strFilesize.length() - 2)) * 1024 * 1024 * 1024;
					else
						throw new Exception("The expression [" + strFilesize + "] is no valid file size definition");
		}

		return lngSize;
	}


}
