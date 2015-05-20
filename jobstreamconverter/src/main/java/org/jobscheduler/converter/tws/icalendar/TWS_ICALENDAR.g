grammar TWS_ICALENDAR;

options {
  language = Java;
  output = AST;
}

@header {
package af.jstech.migration.icalendar ;
import af.jstech.migration.jaxb.scheduler.* ;

}

@lexer::header { 
package af.jstech.migration.icalendar ; 
}

icalendar :
{
TWSICalendar calendar = new TWSICalendar() ;
}


'FREQ' '='  (('DAYLY'

{
Period period = new Period() ;
calendar.period = period ; 
}
)
|('WEEKLY'
{
Weekdays wds = new Weekdays() ;

}

)
|'MONTHLY'

|'YEARLY'

)
 
 (';''INTERVAL' '=' ('-')? NUMBER)?

 (';' ('BYFREEDAY'|'BYWORKDAY'|'BYDAY' '=' weekdayList|
'BYMONTHDAY' '=' monthdayList))?

 ;

 weekdayList returns [Weekdays weekDays]: 
 {
 $weekDays = new Weekdays() ;
 }

 //no constructor 
 ('SU' {Calendar.addDay($weekDays,"7");})? (',' 'MO' {Calendar.addDay($weekDays,"1");})? (',' 'TU' {Calendar.addDay($weekDays,"2");})? (',' 'WE' {Calendar.addDay($weekDays,"3");})? (',' 'TH' {Calendar.addDay($weekDays,"4");})? (',' 'FR' {Calendar.addDay($weekDays,"5");})?  (',' 'SA' {Calendar.addDay($weekDays,"6");})?
 ;
 
 monthdayList returns [Monthdays monthDays]:
 
 ('+' NUMBER {})? ('-' NUMBER {})? (NUMBER {})?
 ;
 
 
 
 COMMENT : '#' .* ('\n'|'\r') {$channel = HIDDEN;};

STRING_LITERAL
  : '"'
    { StringBuilder b = new StringBuilder(); }
    ( '"' '"'       { b.appendCodePoint('"');}
    | c=~('"'|'\r'|'\n')  { b.appendCodePoint(c);}
    )*
    '"'
    { setText(b.toString()); }
  ; 
  
  
 ID : ('A'..'Z'|'a'..'z'|'0'..'9')+ ;
 
 DAYS_NUMBER : '+'('0'..'9')+' ''DAY'('S')? ;
 
 NUMBER  : ('0'..'9')* ;
  DATE_LITERAL : ('0'..'9')('0'..'9')'/'('0'..'9')('0'..'9')'/'('0'..'9')('0'..'9')('0'..'9')('0'..'9') ;
 