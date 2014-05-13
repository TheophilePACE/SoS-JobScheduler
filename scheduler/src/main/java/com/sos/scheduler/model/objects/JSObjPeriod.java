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

import java.util.Date;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.sos.JSHelper.Exceptions.JobSchedulerException;
import com.sos.scheduler.model.ISOSJsObjStartTimes;
import com.sos.scheduler.model.SchedulerObjectFactory;
import com.sos.scheduler.model.tools.RunTimeElement;
import com.sos.scheduler.model.tools.RunTimeElements;

/**
 * \file JSObjPeriod.java
 * \brief 
 *  
 * \class JSObjPeriod
 * \brief 
 * 
 * \details
 *
 * \code
  \endcode
 *
 * \author oh
 * \version 1.0 - 09.02.2011 15:07:56
 *
 * \author ss
 * \version 1.1 - 03.03.2012 14:02:21
 * <div class="sos_branding">
 *   <p>� 2010 SOS GmbH - Berlin (<a style='color:silver' href='http://www.sos-berlin.com'>http://www.sos-berlin.com</a>)</p>
 * </div>
 */
public class JSObjPeriod extends Period implements ISOSJsObjStartTimes {

	@SuppressWarnings("unused")
	private static final Logger	logger = Logger.getLogger(JSObjPeriod.class);
	
	public JSObjPeriod (SchedulerObjectFactory schedulerObjectFactory) {
		super();
		objFactory = schedulerObjectFactory;
	}

	public JSObjPeriod (SchedulerObjectFactory schedulerObjectFactory, String xmlContent) {
		super();
		objFactory = schedulerObjectFactory;
		Period p = (Period)super.unMarshal(xmlContent);
		setObjectFieldsFrom(p);
	}
	
	public PeriodType getPeriodType() {
		if (hasSingleStart()) return PeriodType.SINGLE_START;
		if (hasStartStartInterval()) return PeriodType.START_START_INTERVAL;
		if (hasEndStartInterval()) return PeriodType.END_START_INTERVAL;
		throw new JobSchedulerException("the period type of '" + toXMLString() + "' is not valid.");
	}
	
	public boolean hasSingleStart() {
		return getSingleStart() != null;
	}
	
	public boolean hasStartStartInterval() {
		return getAbsoluteRepeat() != null;
	}
	
	public boolean hasEndStartInterval() {
		return getRepeat() != null;
	}
	
	/* (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.ISOSJsObjPeriod#getBegin()
	 */
	@Override
	public String getBegin() {
		return JSObjPeriod.normalizeTime(super.getBegin());
	}
	
	/* (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.ISOSJsObjPeriod#getEnd()
	 */
	@Override
	public String getEnd() {
		return JSObjPeriod.normalizeTime(super.getEnd());
	}
	
	/* (non-Javadoc)
	 * @see com.sos.scheduler.model.objects.ISOSJsObjPeriod#getSingleStart()
	 */
	@Override
	public String getSingleStart() {
		return JSObjPeriod.normalizeTime(super.getSingleStart());
	}
	
	public static String normalizeTime(String timeString) {
		return (timeString == null || timeString.equals("")) ? null : (timeString.length()<8) ? timeString + ":00" : timeString;
	}
	
	public boolean isInPeriod(Date timeStamp) {
		DateTime d = new DateTime(timeStamp);
		return isInPeriod(d);
	}
	
	public boolean isInPeriod(DateTime timeStamp) {
		if (hasSingleStart()) return true;
		Interval p = getPeriodOrNull(timeStamp);
		return p.contains(timeStamp);
	}
	
	public Interval getPeriodOrNull(DateTime date) {
		Interval result = null;
		if (getBegin()!=null && getEnd()!= null) {
			DateTime begin = getDate(date,getBegin());
			DateTime end = getDate(date,getEnd());
			result = new Interval(begin,end);
		}
		return result;
	}
	
	private static DateTime getDate(DateTime baseDate, String timeString) {
		DateTimeFormatter fmtDate = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmtDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String dateString = fmtDate.print(baseDate) + " ";
		return fmtDateTime.parseDateTime(dateString + timeString);
	}
	
	/**
	 * \brief get the single start as a DateTime object
	 * \detail
	 * To calculate the starttime it is necessary to call this method with a base date.
	 * @param date
	 * @return
	 */
	public DateTime getDtSingleStartOrNull(DateTime date) {
		DateTime result = null;
		if (hasSingleStart()) {
			result = getDate(date,getSingleStart());
		}
		return result;
	}
	
	public DateTime getDtBeginOrNull(DateTime date) {
		DateTime result = null;
		if (!hasSingleStart()) {
			result = getDate(date,getBegin());
		}
		return result;
	}
	
	public DateTime getDtEndOrNull(DateTime date) {
		DateTime result = null;
		if (!hasSingleStart()) {
			result = getDate(date,getEnd());
		}
		return result;
	}
	
	@Override
	public RunTimeElements getRunTimeElements(Interval timeRange) {
		RunTimeElements result = new RunTimeElements(timeRange);
		DateTime dt = getDtSingleStartOrNull(timeRange.getStart());
		if (dt != null) {
			result = new RunTimeElements(timeRange);
			if (!timeRange.contains(dt))
				dt = dt.plusDays(1);
			while (true) {
				if (!timeRange.contains(dt))
					break;
				result.add( new RunTimeElement(dt,getWhenHoliday()));
				dt = dt.plusDays(1);
			}
//			Collections.sort(result, DateTimeComparator.getInstance());
		}
		return result;
	}
	
	public DateTime getDtNextSingleStartOrNull() {
		RunTimeElements result = getRunTimeElements(IntervalConstants.NEXT_24H.getInterval());
		return (result.size() == 0) ? null : result.getStartTimes().get(0);
	}
	
}
