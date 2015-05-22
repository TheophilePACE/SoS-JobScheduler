grammar TWS_SCHEDULES ;

options {
  language = Java;
  output=AST ;
}

  
@header { 
package org.jobscheduler.converter.tws.jobstream;
import org.jobscheduler.converter.tws.* ;
import java.util.LinkedList ;
import java.util.HashMap ;
import org.jobscheduler.converter.tws.scheduler.* ;
}
   


@lexer::header {
package org.jobscheduler.converter.tws.jobstream; 
}



schedules returns [HashMap<String,TWSJobStream> streams] : 
{
$streams = new HashMap<String,TWSJobStream>() ;
}
(scheduleDefinition {$streams.put($scheduleDefinition.stream.id,$scheduleDefinition.stream);})* 

;


scheduleDefinition returns [TWSJobStream stream]:


  'SCHEDULE' sdId=ID 
  {
  $stream = new TWSJobStream($sdId.text) ;
  Launcher.createApplicationDirectory($sdId.text);
  JobChain jobChain = new JobChain() ;
  String tz ="";
  } 
 
 ('VALIDFROM' DATE_LITERAL)?
 
  (timeZoneDefinition {tz = $timeZoneDefinition.timeZone ;})?
  
 ('DESCRIPTION' STRING_LITERAL)?
 
 ('DRAFT')?
 
 (varTableDefinition {Launcher.MarshallVariable($sdId.text,$varTableDefinition.varTable);})?
 
 freeDaysDefinition?
 
 (onStatement {
 Launcher.logger.info($sdId.text);
 $stream.onStatements.add($onStatement.onStatement) ;
 $onStatement.onStatement.timeZone = tz ;
 })*
 
 (exceptStatement
 {
 System.out.println("im here") ;
 for(TWSOnStatement on : $stream.onStatements){
  System.out.println("merging runtimes") ;
 Launcher.mergeRunTimes(on.runTime,$exceptStatement.runTime) ;
 }
 
 }
 
 
 )*
 
 (launchDefinition{$stream.globalAt = $launchDefinition.beginTime;})?
 
 (untilDefinition{$stream.globalUntil = $untilDefinition.endTime ;})?
 
 deadlineDefinition?
 
 'CARRYFORWARD'?
 
 matchingStatement*
 
 (followsStatement {$stream.prevChains.add($followsStatement.jobId);})*
 
 ('KEYSCHED')?
 
 ('LIMIT'  NUMBER)?
 
 needsStatement*
 
 (opensStatement {$stream.opens.add($opensStatement.fileName) ;})*
 
 priorityDefinition?
  
 ':'
 (jobStatement
 {
 Job job = Launcher.getJobById($sdId.text,$jobStatement.jobId) ;
 
 
 job.setRunTime($jobStatement.runTime) ;
 if ($jobStatement.priority != null){
 job.getPriority().add($jobStatement.priority) ;
 }
 $stream.addJobStatement($jobStatement.statement) ; 
 //Launcher.putJobIntoDir($sdId.text,$jobStatement.jobId) ;
 }
 
)*
{
$stream.normalizeNames() ;
}
'END'
 
 ;
 
 
 priorityDefinition returns[String priority]:
 'PRIORITY' (prio=NUMBER {$priority= $prio.text;}|'HI'{$priority="255"; }|'GO' {$priority="255";})
 ;
 
 
 varTableDefinition returns [String varTable] :
 'VARTABLE' v=ID {$varTable=$v.text;}
 ;
 
 freeDaysDefinition :
 'FREEDAYS' ID ('-SA')? ('-SU')?
 ;
 
 onStatement returns [TWSOnStatement onStatement]:
 
 {
 $onStatement = new TWSOnStatement() ;
 }
 
 
 'ON' ('RUNCYCLE' id1=ID {$onStatement.id = $id1.text ;  })?
 
 ('VALIDFROM' DATE_LITERAL)? ('VALIDTO' DATE_LITERAL)?
 
 ('DESCRIPTION' STRING_LITERAL)?
 
 ('VARTABLE' ID)?
 
 (calendarDefinition[$onStatement.runTime,true] )*
 
 fdOptions?
 
 ('('
 (launchDefinition{$onStatement.onAt = $launchDefinition.beginTime ;})?
 
 (untilDefinition {$onStatement.onUntil= $untilDefinition.endTime;})?
 
 deadlineDefinition?
 
 ')')?
 ;
 
 
 exceptStatement returns [RunTime runTime] :
 
 {
 $runTime = new RunTime()  ;
 }
  
 'EXCEPT' ('RUNCYCLE' ID)?
 
 ('VALIDFROM' DATE_LITERAL)? ('VALIDTO' DATE_LITERAL)?
 
 ('DESCRIPTION' STRING_LITERAL)?
 
 ('VARTABLE' ID)?
 
 calendarDefinition[$runTime,false]* //en attendant mieu
 
 fdOptions ?
 
 ('('
 
 launchDefinition?
  
 ')')?
 
 ;
 
 matchingStatement : 
 'MATCHING' 
 followsOrMatchingOptions?
 ;
 
 followsStatement returns [String jobId]: 
 'FOLLOWS' id=ID {$jobId = $id.text;}
 followsOrMatchingOptions ?
 ;
 
 followsOrMatchingOptions :
 'SAMEDAY'
 |'PREVIOUS'
 |('RELATIVE' 'FROM' ('+'|'-')? NUMBER 'TO' ('+'|'-')? NUMBER)
 |('FROM' ('+'|'-')? NUMBER (('+'|'-')? NUMBER day)? 'TO' ('+'|'-')? NUMBER (('+'|'-')? NUMBER day)? )
 ;
 
 
 
 calendarDefinition  [RunTime runTime,boolean on] : 
 ID //include a calendar file
 |ical=STRING_LITERAL {Launcher.recognizeICalendar(Launcher.getString($ical.text),$runTime,$on);}
 |'REQUEST' //Manual launch
 |groupDate[$runTime] //explicit calendar definition
 ;

 jobStatement returns [String jobId,RunTime runTime,String priority
 ,TWSJobStatement statement
 ] :
 {
 $runTime = new RunTime() ;
 
 } 
 id1=ID {$jobId=$id1.text ;
 $statement = new TWSJobStatement($id1.text) ;
 }
 (
 launchDefinition
 {
 $runTime.setBegin($launchDefinition.beginTime);
 }
 | untilDefinition  { $runTime.setEnd($untilDefinition.endTime) ;}
 | deadlineDefinition 
 | ('EVERY' every=NUMBER {$statement.every = $every.text;})
 | followsStatement {$statement.addFollow($followsStatement.jobId) ;}
 |'CONFIRMED'
 |'CRITICAL'
 |'KEYJOB'
 |needsStatement
 |priorityDefinition {$priority=$priorityDefinition.priority;}
 |opensStatement
 )*
 ;

 fdOptions :
 'FDIGNORE'
 |'FDPREV' 
 |'FDNEXT'
 ;
 
 
 groupDate [RunTime runTime] :
 {
 System.out.println("I'm Here group date") ;
 LinkedList<String> dates = new LinkedList<String>() ;}
 
 d1= DATE_LITERAL {dates.add(Launcher.normalizeDate($d1.text)) ;} (',' d2=DATE_LITERAL {dates.add(Launcher.normalizeDate($d2.text)) ;})*
 {
 for(String sDate : dates){
 RunTime.Date date = new RunTime.Date() ;
 date.setDate(sDate) ; 
 $runTime.getDate().add(date) ;
 
 }
 
 }
 
 
 ;
 
 
 needsStatement  :
 
 'NEEDS' ((NUMBER)? ID)
 ;
 
 
 opensStatement returns[String fileName]: 
 'OPENS' {} fn= fileName qualifier* {$fileName =  $fn.path ; }
 
 ;
 
 qualifier :  //A REVOIR  

  '(' ('\'')?(
  ('-d' '%p')
  |(('/')?ID)
  | ('-e' '%p') 
  | ('-f' '%p') 
  | ('-r' '%p') 
  | ('-s' '%p') 
  | ('-w' '%p')
  | ('-a')
  | ('-o')
  |('ls' |'`ls'| '%p' | '|' | 'wc' | '-w`'  | (('-gt'|'-eq'|'-ge') NUMBER))* //commande spécifique à air france
 
  )
  ('\'')?
  ')'
  ;
 
 
 launchDefinition returns [String beginTime] :
 
 (atStatement{$beginTime= $atStatement.atTime;})
 |schedtimeStatement 
 
 ;
 
 atStatement returns [String atTime]:
  ('AT' time=NUMBER timeZoneDefinition? ('+' NUMBER day)?)
  {
  $atTime = Launcher.normalizeTime($time.text) ;
  }
 ;
 
 schedtimeStatement :
 ('SCHEDTIME' NUMBER timeZoneDefinition? ('+' NUMBER day)?)
 ;
 
 
 untilDefinition  returns [String endTime]: //provisoirement
 'UNTIL' time=NUMBER  timeZoneDefinition? {$endTime = Launcher.normalizeTime($time.text) ;} ('+' NUMBER day {$endTime = null ;})? ('ONUNTIL' action)?
  
  
  
  ;
  
  deadlineDefinition :
  'DEADLINE' NUMBER  timeZoneDefinition? ('+' NUMBER day)?
  ;
 
 day :
 'DAY' 
 |'DAYS'
 ;
 

action :
'CANC'
|'SUPPR'
|'CONT'
;
 
COMMENT
  :
  '#' .*
  (
    '\n'
    | '\r'
  )
  
  {
   $channel = HIDDEN;
  }
  ;

timeZoneDefinition returns [String timeZone]:
('tz' ID) 
| ('TIMEZONE' (fus1=ID {$timeZone =$fus1.text ;}('/'fus2=ID{$timeZone = $timeZone+"/"+$fus2.text ;})?))

{

if($timeZone.toUpperCase().equals("ECT")){
$timeZone ="Europe/Paris"  ;
}
}

;



  STRING_LITERAL
  :
  '"'
  (
    ~(
      '"'
      | '\r'
      | '\n'
     )
     |('\\"')
  )*
  '"'
  ;
  


WS
  :
  (' '|'\n'
    | '\t'
    | '\r'
    | '\\')+ 
        {
         $channel = HIDDEN;
        }
  ;


ID
  :
 (
    'a'..'z'
    | 'A'..'Z'
  )
  (
    'a'..'z'
    | 'A'..'Z'
    | '0'..'9'
    | '-'
    | '_'
    | '@'
    | '.'
    | '#'
    |'\''
    |'%'
  )*
  ;

//NEW_LINE
//  :
//  (
//    '\n'
//    | '\t'
//    | '\r'
//    | '\\'
//  )+
//  
//  {
//   $channel = HIDDEN;
//  }
//  ;

NUMBER
  :
  ('0'..'9')+
  ;

//HASH_ID : ID'#' ;

//SPECIFIC_ID: HASH_ID? ID
//  ;

//QUALIFIER_LITERAL :
//STRING*
//;
fileName returns [String path]
  :
  ID?sl=STRING_LITERAL {$path=$sl.text;}
  ;

DATE_LITERAL
  :
  ('0'..'9') ('0'..'9') '/' ('0'..'9') ('0'..'9') '/' ('0'..'9') ('0'..'9') ('0'..'9') ('0'..'9')
  ;
 