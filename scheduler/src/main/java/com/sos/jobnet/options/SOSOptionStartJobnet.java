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

import com.sos.JSHelper.Options.JSOptionsClass;
import com.sos.JSHelper.Options.SOSOptionString;
import com.sos.jobnet.classes.JobNetException;

public class SOSOptionStartJobnet extends SOSOptionString {

	public SOSOptionStartJobnet(JSOptionsClass pPobjParent, String pPstrKey, String pPstrDescription, String pPstrValue, String pPstrDefaultValue, boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
	}

	public boolean isStart() {
		return (this.Value().equalsIgnoreCase(StartModes.start.name()));
	}

	public boolean isAlways() {
		return (this.Value().equalsIgnoreCase(StartModes.start_independent.name()));
	}

    public StartModes asEnum() {
    	return StartModes.valueOf(Value());
    }

    public void Value(String value) {
        String testValue = value.toLowerCase();
        try {
            StartModes t = StartModes.valueOf(testValue);
        } catch (Exception e) {
            throw new JobNetException("Value " + testValue + " is not valid for " + strKey + " - valid values are " + StartModes.asString() + ".");
        }
        super.Value(testValue);
    }

}
