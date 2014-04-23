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
package com.sos.jobnet.creator;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.options.JobNetIdCalculatorOptions;
import com.sos.jobnet.utils.NamedDurationFieldType;
import com.sos.jobnet.utils.TimeKeyCalculator;

public class JobNetIdCalculator  {

	private final Logger logger = Logger.getLogger(JobNetIdCalculator.class);
	
    private final String loadId;
    private final String loadIdFormat;
    private final String loadIdType;
    private final String rule;
    private final String targetFormat;
    private final List<String> targetFields;

    public JobNetIdCalculator(JobNetIdCalculatorOptions options, boolean isOrderJob) {
    	
    	try {
			options.CheckMandatory();
		} catch (Exception e) {
			String msg = "Error checking options for " + JobNetIdCalculator.class.getName();
			logger.error(msg,e);
			throw new JobSchedulerException(msg,e);
		}
    	
        this.loadIdFormat = options.getLoadIdDateFormat().Value();
        this.loadIdType = options.getLoadIdDurationType().Value();
        this.rule = options.getCalculationRule().Value();
        this.targetFormat = options.getResultDateFormat().Value();
        this.loadId = getFormattedDate(options.getLoadId().Value());

        String targetFieldsString = options.getResultParameterName().Value();
        this.targetFields = Arrays.asList(targetFieldsString.split(","));

        logger.info("Processing time key ...: " + loadId);
        logger.info("Expected format .......: " + loadIdFormat);
        logger.info("Type of calculation ...: " + loadIdType);
        logger.info("Calculation rule ......: " + rule);
        logger.info("Target format .........: " + targetFormat);
        logger.info("Target fields..........: " + targetFieldsString + " (" + (isOrderJob ? "order" : "job") + ")");
		
	}
    
    private String getFormattedDate(String input) {
    	String result = input;
    	if(result.toLowerCase().equals("*now")) {
    		result = DateTimeFormat.forPattern(loadIdFormat).print(new DateTime());
    	}
    	return result;
    }

	public String execute() {
		String result = "";
		try {
	        TimeKeyCalculator calc = new TimeKeyCalculator(loadId, DateTimeFormat.forPattern(loadIdFormat), NamedDurationFieldType.of(loadIdType));
	        result = calc.calculate(rule);
	        if(!targetFormat.isEmpty())
	        	result = calc.convert( DateTimeFormat.forPattern(targetFormat) );
	        logger.info("Result value ..........: " + result);
        } catch (Exception e) {
            throw new JobSchedulerException(e);
        }
		return result;
	}
	
	public List<String> getTargetField() {
		return targetFields;
	}

}
