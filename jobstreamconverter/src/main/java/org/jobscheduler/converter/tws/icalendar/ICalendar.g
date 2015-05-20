grammar ICalendar;

options {
  language = Java;
  output = AST ;
}


@header {
package af.jstech.migration.icalendar ;
import java.util.LinkedList ;
import af.jstech.migration.jaxb.scheduler.* ;
import af.jstech.migration.jaxb.scheduler.Weekdays.Day ;

}

@lexer::header { 
package af.jstech.migration.icalendar ; 
}

 

calendar [RunTime runTime,boolean on]: 
(daily [runTime])
|(weekly{

$runTime.setWeekdays($weekly.wds);

}) 
|(monthDays{

$runTime.setMonthdays($monthDays.mds);


})
|(lasts {


$runTime.setUltimos($lasts.ultimos);


}
) 

|yearly

;


daily [RunTime runTime]    : 

'FREQ' '=' 'DAILY' (';')? ('INTERVAL' '=' NUMBER (';')?)? ('BYFREEDAY'|('BYWORKDAY'))?
;


weekly  returns[Weekdays wds] :
{
$wds = new Weekdays() ; 
}
'FREQ' '=' 'WEEKLY' (';')? ('INTERVAL' '=' NUMBER (';')?)? 'BYDAY' '=' ( weekDayList (',')? {$wds.getDay().addAll($weekDayList.days) ;})+
;







lasts returns [Ultimos ultimos]:
({
$ultimos = new Ultimos() ;
}
'FREQ' '=' 'MONTHLY' (';')? ('INTERVAL' '=' NUMBER (';')?)? 'BYMONTHDAY' '=' '-'num2=NUMBER 
{

Integer i = new Integer(Integer.parseInt($num2.text)-1 ) ; 
Ultimos.Day day = new Ultimos.Day() ;
day.getDay().add(i) ;
$ultimos.getDay().add(day) ;
})


;

monthDays returns [Monthdays mds] : 

(

{

$mds = new Monthdays() ;
}
'FREQ' '=' 'MONTHLY' (';')? ('INTERVAL' '=' NUMBER (';')?)? 'BYMONTHDAY' '=' (num2=NUMBER (',')?

{
Integer i = new Integer(Integer.parseInt($num2.text) ) ; 
Monthdays.Day day = new Monthdays.Day() ;
day.getDay().add(i) ;
$mds.getDayOrWeekday().add(day) ;
}
)+)
|
(
{
$mds = new Monthdays() ;
}
'FREQ' '=' 'MONTHLY' (';')? ('INTERVAL' '=' NUMBER (';')?)? 'BYDAY' '='( num3=NUMBER dayList1=weekDayList(',')?  
{for (Day day : $dayList1.days){
 Monthdays.Weekday wd = new Monthdays.Weekday() ;
 wd.getDay().add(day.getDay().get(0)) ; 
 wd.setWhich($num3.text) ;
 $mds.getDayOrWeekday().add(wd) ;
 }
 
 })+
{


 
}
)
|
({
$mds = new Monthdays() ;
}
'FREQ' '=' 'MONTHLY' (';')? ('INTERVAL' '=' NUMBER (';')?)? 'BYDAY' '=' ('-'num6=NUMBER dayList2=weekDayList(',')?{

 for (Day day : $weekDayList.days){
 Monthdays.Weekday wd = new Monthdays.Weekday() ;
 wd.getDay().add(day.getDay().get(0)) ; 
 wd.setWhich("-"+$num6.text) ;
 $mds.getDayOrWeekday().add(wd) ;
 }
 
})+



)

;
yearly : 
'FREQ' '=' 'YEARLY' ('INTERVAL' '=' NUMBER)?
;

weekDayList returns [LinkedList<Day> days]:
{
$days = new LinkedList<Day>() ;
}
(('MO'{Day day = new Day() ; day.getDay().add("1") ; $days.add(day) ;} ) 
| ('TU' {Day day = new Day() ; day.getDay().add("2") ; $days.add(day) ;} )
|('WE' {Day day = new Day() ; day.getDay().add("3") ; $days.add(day) ;})
| ('TH' {Day day = new Day() ; day.getDay().add("4") ; $days.add(day) ;})
| ('FR' {Day day = new Day() ; day.getDay().add("5") ;$days.add(day) ;})
| ('SA' {Day day = new Day() ; day.getDay().add("6") ;$days.add(day) ;})
|('SU' {Day day = new Day() ; day.getDay().add("7") ;$days.add(day) ;}))
;


 COMMENT : '#' .* ('\n'|'\r') {$channel = HIDDEN;};


  

 

 
 NUMBER  : ('0'..'9')+ ;
 