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
package com.sos.scheduler.model.objects;

import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sos.scheduler.model.ISOSJsObjStartTimes;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.tools.RunTimeElement;
import com.sos.scheduler.model.tools.RunTimeElements;

public class JSObjDate extends RunTime.Date implements ISOSJsObjStartTimes {

	public JSObjDate (SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
	}

	public DateTime getDtDate() {
		DateTimeFormatter fmtDate = DateTimeFormat.forPattern("yyyy-MM-dd");
		return fmtDate.parseDateTime(getDate());
	}

	@Override
	public RunTimeElements getRunTimeElements(Interval timeRange) {
		RunTimeElements result = new RunTimeElements(timeRange);
		Iterator<Period> it = getPeriod().iterator();
		while(it.hasNext()) {
			Period p = it.next();
			JSObjPeriod period = new JSObjPeriod(objFactory);
			period.setObjectFieldsFrom(p);
			DateTime singleStart = period.getDtSingleStartOrNull(getDtDate());
			if (singleStart != null && timeRange.contains(singleStart)) {
				result.add( new RunTimeElement(singleStart,period.getWhenHoliday()) );
			}
		}
//		Collections.sort(result, DateTimeComparator.getInstance());
		return result;
	}

	/**
	 * The default behaviour of the JobScheduler Object Model is to provide an empty List of Periods. In some 
	 * cases we want to have exactly one default period.
	 * 
	 * (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.Weekdays.Day#getPeriod()
	 */
	@Override
    public List<Period> getPeriod() {
		List<Period> list = super.getPeriod();
		WhenHoliday h = (list != null && list.size() > 0) ? list.get(0).getWhenHoliday() : WhenHoliday.SUPPRESS;
        return (objFactory.useDefaultPeriod()) ? JSObjRunTime.getDefaultPeriod(objFactory,h) : list;
    }

}
