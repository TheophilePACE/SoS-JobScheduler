package org.jobscheduler.converter.tws.icalendar;

import java.util.LinkedList;

import org.jobscheduler.converter.tws.scheduler.*;
import org.jobscheduler.converter.tws.scheduler.RunTime.Date;





public class Calendar {
	
	
	
	public static void addDay(Weekdays wd, String d){
		org.jobscheduler.converter.tws.scheduler.Weekdays.Day day = new org.jobscheduler.converter.tws.scheduler.Weekdays.Day() ;
		day.getDay().add(d) ;
		wd.getDay().add(day) ;
		
	}
	
	public static String normalizeDate(String date){
		String[] elements = date.split("/") ;
		return elements[2]+"-"+elements[0]+"-"+elements[1] ;
	}
	
	public static String normalizeTime(String time){
		return time.substring(0,2)+":"+time.substring(2,4) ;
		
	}
	
	public static LinkedList<Date> getDates(LinkedList<String> dates){
		LinkedList<Date> datesRturn  = new LinkedList<Date>() ;
		
		for (String stDate : dates){
			Date dDate = new Date() ;
			dDate.setDate(normalizeDate(stDate)) ;
			datesRturn.add(dDate) ;
		}
	
		return datesRturn ;
	}
	
	
	public static void getHolidays( LinkedList<Holiday>  holidays, LinkedList<String> dates){
		
		for (String date : dates){
			Holiday holiDay = new Holiday() ;
			holiDay.setDate(normalizeDate(date) ) ;
			
			holidays.add(holiDay);
		
		}
		
	}
	
	
	public static void setExcept(LinkedList<Order> orders, LinkedList<Holiday> holiDays){
		for(Order order : orders){
			order.getRunTime().getHolidays().getWeekdaysOrHolidayOrInclude().addAll(holiDays) ;
		}
	}
	
	public static void setUntil(LinkedList<Order> orders, String endTime){
		for(Order order : orders){
			order.getRunTime().setEnd(endTime) ;
		}
	}
	

}
