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
package com.sos.jobnet.options;

import com.sos.JSHelper.Options.JSOptionValueList;
import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.jobnet.classes.JobNetException;

public class SOSOptionStartType extends JSOptionValueList {

	private static final long serialVersionUID = 8517927910957781607L;

	public enum Type {
		tcp, udp, direct;

        public static String asString() {
            StringBuffer result = new StringBuffer();
            result.append("[");
            for(Type t : values()) {
                if(result.length() > 1) result.append(", ");
                result.append(t.name());
            }
            result.append("]");
            return result.toString();
        }
	}

	public SOSOptionStartType(JSOptionsClass pPobjParent, String pPstrKey, String pPstrDescription, String pPstrValue, String pPstrDefaultValue, boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
	}

	public SOSOptionStartType(JSOptionsClass pobjParent, String pstrIndexedKey, String pstrDescription, String pstrDefaultValue, boolean pflgIsMandatory) {
		super(pobjParent, pstrIndexedKey, pstrDescription, pstrDefaultValue, pflgIsMandatory);
	}

	public boolean isTcp() {
		return (this.Value().equalsIgnoreCase(Type.tcp.name()));
	}

	public boolean isUdp() {
		return (this.Value().equalsIgnoreCase(Type.udp.name()));
	}

	public boolean isDirect() {
		return (this.Value().equalsIgnoreCase(Type.direct.name()));
	}

    public void Value(String value) {
        String testValue = value.toLowerCase();
        try {
            SOSOptionStartType.Type t = SOSOptionStartType.Type.valueOf(testValue);
        } catch (Exception e) {
            throw new JobNetException("Value " + testValue + " is not valid for " + strKey + " - valid values are " + Type.asString() + ".");
        }
        super.Value(testValue);
    }

}
