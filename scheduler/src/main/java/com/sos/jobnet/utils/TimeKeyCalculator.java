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
package com.sos.jobnet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class is for simplified calculation of time keys.
 */
public class TimeKeyCalculator {

    public final static DateTimeFormatter FORMAT_YYYYMMDD = DateTimeFormat.forPattern("yyyyMMdd");
    public final static DateTimeFormatter FORMAT_YYYYMM = DateTimeFormat.forPattern("yyyyMM");
    public final static DurationFieldType TYPE_DAYS = DurationFieldType.days();
    public final static DurationFieldType TYPE_MONTHS = DurationFieldType.months();

    public final static Pattern FORMULA_ADD_MONTH = Pattern.compile("add_months\\s*\\(\\s*(\\d*)\\s*\\)");
    public final static Pattern FORMULA_SUB_MONTH = Pattern.compile("subtract_months\\s*\\(\\s*(\\d*)\\s*\\)");

    private final String timeKey;
    private final DateTimeFormatter format;
    private final DurationFieldType type;
    private final DateTime timeValue;

    private MutableDateTime calculatedTimeValue;

    public TimeKeyCalculator(String timeKey, DateTimeFormatter format, DurationFieldType type) {
        this.timeKey = timeKey;
        this.format = format;
        this.type = type;
        this.timeValue = format.parseDateTime(timeKey);
        this.calculatedTimeValue = timeValue.toMutableDateTime();
    }

    public TimeKeyCalculator(DateTime timeValue, DateTimeFormatter format, DurationFieldType type) {
        this.timeValue = timeValue;
        this.format = format;
        this.type = type;
        this.timeKey = timeValue.toString(format);
        this.calculatedTimeValue = timeValue.toMutableDateTime();
    }

    public String add(int operand) {
        calculatedTimeValue.add(type,operand);
        return format.print(calculatedTimeValue);
    }

    public String subtract(int operand) {
        return add(operand * -1);
    }

    public String increase() {
        return add(1);
    }

    public String decrease() {
        return subtract(1);
    }

//    public String calculate(String calc) {
//        return (calc.isEmpty()) ? timeKey : add(Integer.parseInt(calc));
//    }
    
    public String calculate(String calc) {
    	String result = timeKey;
    	if(!calc.isEmpty()) {
	    	String[] parts = calc.split("(?=[\\+|\\-])");
	    	for(String part : parts) {
	    		while (true) {
		    		Matcher m = FORMULA_ADD_MONTH.matcher(part);
		    		if(m.find()) {
			    		int operand = Integer.valueOf(m.group(1));
		    			calculatedTimeValue.addMonths(operand);
		    			break;
		    		}
		    		m = FORMULA_SUB_MONTH.matcher(part);
		    		if(m.find()) {
			    		int operand = Integer.valueOf(m.group(1)) * -1;
		    			calculatedTimeValue.addMonths(operand);
		    			break;
		    		}
		    		// a positive sign will not be parsed by OpenJDK, therefore it has to be replaced
		    		int operand = Integer.valueOf(part.replaceAll("\\s","").replace("+", ""));
		    		calculatedTimeValue.add(type,operand);
		    		break;
	    		}
	    	}
	    	result = format.print(calculatedTimeValue);
    	}
    	return result;
    }

    public String convert(DateTimeFormatter toFormat) {
    	return toFormat.print(calculatedTimeValue);
    }

}
