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

import org.apache.log4j.Logger;

import com.sos.JSHelper.Exceptions.JobSchedulerException;

/**
* \class SOSOptionJadeOperation
*
* \brief SOSOptionJadeOperation -
*
* \details
*
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
* \version $Id$
* \see reference
*
* Created on 09.09.2011 15:25:28
 */

/**
 * @author KB
 *
 */
public class SOSOptionJadeOperation extends SOSOptionStringValueList {

	private static final long	serialVersionUID	= 1786255103960193423L;
	private final String		conClassName		= "SOSOptionJadeOperation";
	@SuppressWarnings("unused")
	private static final String	conSVNVersion		= "$Id$";
	private static final Logger	logger				= Logger.getLogger(SOSOptionJadeOperation.class);

	private enuJadeOperations	enuTT				= enuJadeOperations.undefined;


	public static enum enuJadeOperations {
		send, /* old style: from localhost to target-host */
		receive, /* old style: from remote-host to localhost */
		copy, move, delete, undefined, rename, zip, getlist // get the list of filenames only
		, sendusingdmz
		, receiveusingdmz
		, copytointernet
		, copyfrominternet
		, remove  /* alias for "delete"
		/* */;

		public String Text() {
			String strT = this.name();
			return strT;
		}
	}

	/**
	 * \brief SOSOptionJadeOperation
	 *
	 * \details
	 *
	 * @param pobjParent
	 * @param pstrKey
	 * @param pstrDescription
	 * @param pstrValue
	 * @param pstrDefaultValue
	 * @param pflgIsMandatory
	 */
	public SOSOptionJadeOperation(final JSOptionsClass pobjParent, final String pstrKey, final String pstrDescription, final String pstrValue, final String pstrDefaultValue,
			final boolean pflgIsMandatory) {
		super(pobjParent, pstrKey, pstrDescription, pstrValue, pstrDefaultValue, pflgIsMandatory);

		String strT = "";
		for (enuJadeOperations enuT : enuJadeOperations.values()) {
			strT += enuT.Text() + ";";
		}
		this.createValueList(strT.substring(0, strT.length()-1));
	}

	public void Value(final enuJadeOperations penuOperation) {
		enuTT = penuOperation;
		if (enuTT == enuJadeOperations.remove) {
			enuTT = enuJadeOperations.delete;
		}
		super.Value(enuTT.Text());
	}

	public enuJadeOperations value() {
		for (enuJadeOperations enuT : enuJadeOperations.values()) {
			if (enuT.name().equalsIgnoreCase(strValue)) {
				enuTT = enuT;
				break;
			}
		}
		return enuTT;
	}

	@Override
	public void Value(final String pstrValue) {

		boolean flgOperationIsValid = false;
		if (pstrValue != null) {
			for (enuJadeOperations enuT  : enuJadeOperations.values()) {
				if (enuT.name().equalsIgnoreCase(pstrValue)) {
					this.Value(enuT);
					flgOperationIsValid = true;
					super.Value(enuTT.Text());
					return;
				}
			}

			if (flgOperationIsValid == false) {
				throw new JobSchedulerException(String.format("unknown or invalid value for parameter '%1$s' specified: '%2$s'", this.getShortKey(), pstrValue));
			}
		}
		super.Value(pstrValue);
	}

	public boolean isOperationSend() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationSend";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.send || enuT == enuJadeOperations.sendusingdmz;
	} // private boolean isOperationSend

	public boolean isOperationSendUsingDMZ() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationSendUsingDMZ";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.sendusingdmz;
	} // private boolean isOperationSendUsingDMZ

	public boolean isOperationCopyFromInternet() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationCopyFromInternet";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.copyfrominternet;
	} // private boolean isOperationCopyFromInternet

	public boolean isOperationCopyToInternet() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationCopyToInternet";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.copytointernet;
	} // private boolean isOperationCopyToInternet

	public boolean isOperationReceiveUsingDMZ() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationReceiveUsingDMZ";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.receiveusingdmz;
	} // private boolean isOperationReceiveUsingDMZ


	public boolean isOperationReceive() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationReceive";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.receive || enuT == enuJadeOperations.receiveusingdmz;
	} // private boolean isOperationReceive

	public boolean isOperationCopy() {

		@SuppressWarnings("unused")
		final String conMethodName = conClassName + ":: isOperationReceive";

		enuJadeOperations enuT = this.value();

		return enuT == enuJadeOperations.copy;
	} // private boolean isOperationReceive

}
