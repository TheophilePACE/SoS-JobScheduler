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
package com.sos.jobnet.creator.jobs;

import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.jobnet.creator.JobNetIdCalculator;
import com.sos.jobnet.options.JobNetIdCalculatorOptions;
import sos.scheduler.job.JobSchedulerJobAdapter;
import sos.spooler.Variable_set;

import java.util.List;

public class JobNetIdCalculatorJSAdapterClass extends JobSchedulerJobAdapter  {

    private final JobNetIdCalculatorOptions options = new JobNetIdCalculatorOptions();

    @Override
    public boolean spooler_process_before() {

        try {
            super.spooler_process_before();
            logJobInfo();
        } catch (Exception e) {
            throw new JobSchedulerException(e);
        }

        return true;
    }

    @Override
    public boolean spooler_process_after(boolean processResult) {

        try {
            processResult = super.spooler_process_after(processResult);
            logJobInfo();
        } catch (Exception e) {
            throw new JobSchedulerException(e);
        }

        return processResult;
    }

    @Override
    public boolean spooler_process() {

		final String conMethodName = this.getClass().getSimpleName() + "::spooler_process"; //$NON-NLS-1$

		try {

            // prepare the class
            super.spooler_process();
            options.setAllOptions(getAllParametersAsProperties());
            options.CheckMandatory();
            JobNetIdCalculator c = new JobNetIdCalculator(options, isOrderJob() );

            List<String> targetNames = c.getTargetField();
            String targetValue = c.execute();
            Variable_set p =(isOrderJob()) ? spooler_task.order().params() : spooler_task.params();
            
            for(String targetName : targetNames) {
            	spooler_log.info("Set parameter .........: " + targetName + "=" + targetValue);
            	p.set_value(targetName,targetValue);
            }

        } catch (Exception e) {
			String msgText = String.format(Messages.getMsg("JSJ-I-107"), conMethodName );
            spooler_log.error(msgText);
			throw new JobSchedulerException(msgText,e);
        }

        return isOrderJob();
    }

    public JobNetIdCalculatorOptions getOptions() {
        return options;
    }
    
    private void logJobInfo() {
        if (isOrderJob()) 
        	spooler_log.info("order id is " + spooler_task.order().id());
        else
        	spooler_log.info("job name is " + spooler_task.job().name() + " (id: " + spooler_task.id() + ")");
    }

}
