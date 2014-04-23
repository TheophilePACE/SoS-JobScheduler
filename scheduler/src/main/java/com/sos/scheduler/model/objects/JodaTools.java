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

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;

public class JodaTools {

	/**
	 * \brief calculate a date for a specific weekday based on a given date
	 * \detail
	 * @param date - the base date
	 * @param weekday - the weekday
	 * @return
	 */
	public static DateTime getNextWeekday(DateTime date, int weekday) {    
	    if (date.getDayOfWeek() > weekday) 
	        date = date.plusWeeks(1);
	    return date.withDayOfWeek(weekday);
	}

	/**
	 * \brief  calculate a date for a specific weekday based on a given date
	 * \detail
	 *
	 * @param date
	 * @param weekday
	 * @return
	 */
	public static DateTime getPreviousWeekday(DateTime date, int weekday) {    
	    if (date.getDayOfWeek() < weekday) 
	        date = date.minusWeeks(1);
	    return date.withDayOfWeek(weekday);
	}

	/**
	 * \brief calculate a date for a specific weekday based on a given date
	 * \detail
	 * @param date - the base date
	 * @param weekday - the weekday
	 * @return
	 */
	public static DateTime getWeekdayInMonth(DateTime date, int weekday, int which) {

		if (which < 0) {
			DateTime baseDate = getEndOfMonth(date);
			DateTime d = getPreviousWeekday(baseDate, weekday);
			int weeks = (which * -1) - 1;
			return d.minusWeeks(weeks);
		}
		
		DateTime baseDate = getStartOfMonth(date);
		DateTime d = getNextWeekday(baseDate, weekday);
		return d.plusWeeks(which - 1);
		
	}

	public static DateTime getWeekdayInIntervalOrNull(Interval interval, int weekday, int which) {
		DateTime currentDate = getStartOfMonth(interval.getStart());
		DateTime result = getWeekdayInMonth(currentDate, weekday, which);
		while (!interval.contains(result)) {
			currentDate = currentDate.plusMonths(1);
			result = getWeekdayInMonth(currentDate, weekday, which);
			if(!result.isBefore(interval.getEnd()))
				return null;
		}
		return result;
	}


	public static DateTime getDayInMonth(DateTime date, int day) {
		DateTime baseDate = (day > 1) ? date.minusMonths(1) : date;
		DateTime result = getEndOfMonth(baseDate).plusDays(day);
		return result.minusMillis(result.getMillisOfDay());
	}

	public static DateTime getDayInIntervalOrNull(Interval interval, int day) {
		DateTime currentDate = getStartOfMonth(interval.getStart());
		DateTime result = getDayInMonth(currentDate, day);
		while (!interval.contains(result)) {
			currentDate = currentDate.plusMonths(1);
			result = getDayInMonth(currentDate, day);
			if(!result.isBefore(interval.getEnd()))
				return null;
		}
		return result;
	}

	/**
	 * \brief converts a JobScheduler day String into a list of Joda Weekday constants 
	 * \detail
	 *
	 * @param jsDay
	 * @return
	 */
	public static List<Integer> getJodaWeekdays(String jsDay) {
		List<Integer> result = new ArrayList<Integer>();
		String[] arr = jsDay.split(" ");
		for (int i = 0; i < arr.length; i++) {
			result.add(getJodaWeekday(arr[i]));
		}
		return result;
	}
	
	/**
	 * \brief converts a JS weekday in a Joda weekday
	 * \detail
	 *
	 * @param jsWeekday
	 * @return
	 */
	public static int getJodaWeekday(String jsWeekday) {
		String d = jsWeekday.toLowerCase();
		if (d.length() > 2) d = d.substring(0,2);
		if (d.equals("1") || d.equals("mo")) return DateTimeConstants.MONDAY;
		if (d.equals("2") || d.equals("tu") || d.equals("di")) return DateTimeConstants.TUESDAY;
		if (d.equals("3") || d.equals("we") || d.equals("mi")) return DateTimeConstants.WEDNESDAY;
		if (d.equals("4") || d.equals("th") || d.equals("do")) return DateTimeConstants.THURSDAY;
		if (d.equals("5") || d.equals("fr")) return DateTimeConstants.FRIDAY;
		if (d.equals("6") || d.equals("sa")) return DateTimeConstants.SATURDAY;
		return DateTimeConstants.SUNDAY;
	}
	
	public static DateTime getStartOfMonth(DateTime base) {
		return base.minusMillis(base.getMillisOfDay()).minusDays(base.getDayOfMonth()-1);
	}
	
	public static DateTime getEndOfMonth(DateTime base) {
		return getStartOfMonth(base.plusMonths(1)).minusMillis(1);
	}
	
	public static DateTime getStartOfDay(DateTime base) {
		return base.minusMillis(base.getMillisOfDay());
	}
}
