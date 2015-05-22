grammar TWS_JOBS;

options {
  language = Java;
  output = AST;
}
 

@header {
 
package org.jobscheduler.converter.tws.job;
import  org.jobscheduler.converter.tws.scheduler.* ;
import  org.jobscheduler.converter.tws.* ;
import  org.jobscheduler.converter.tws.scheduler.Job.Description ;
import  org.jobscheduler.converter.tws.scheduler.JobChain.JobChainNode ;
import java.util.HashMap ;
}
   

 
@lexer::header {
package org.jobscheduler.converter.tws.job; 
}

jobs[boolean test] returns [HashMap<String,Job> jobs,HashMap<String,JobChainNode> states ]:
 {
 $jobs = new HashMap<String,Job>() ;
 $states = new HashMap<String,JobChainNode>() ;
 }
'$JOBS'

(jobDefinition[$test]

{
$jobs.put($jobDefinition.fullId, $jobDefinition.job) ;
Launcher.states.put($jobDefinition.fullId, $jobDefinition.state) ;
//Launcher.logger.info("putting "+$jobDefinition.fullId) ;
Launcher.recoveries.put($jobDefinition.fullId, $jobDefinition.recovery) ;
}

)*
{
Launcher.logger.info(Launcher.states.size()+" states collected") ;
}
;

jobDefinition [boolean test]returns [String fullId,Job job,JobChainNode state,Recovery recovery] : 

{
$job = new Job() ;
$state = new JobChainNode() ;
$job.setOrder("yes") ;
}

jobId=ID 
{
//a modifier 
$fullId = $jobId.text ;
$job.setName( $jobId.text.split("#")[1]);
$state.setJob($jobId.text.split("#")[1]) ;
$state.setState($jobId.text.split("#")[1]) ;  
//Launcher.logger.info("Parsing of the Job " + $jobId.text);
}

//command {$job.setScript($command.script);}
{Script script = new Script();
script.setLanguage("shell") ;
}
(

('DOCOMMAND' command=STRING_LITERAL
{
if(!test){
script.getContent().add(Launcher.getString($command.text));
}else{

script.getContent().add("sleep 5 #"+Launcher.getString($command.text));
}
}
)

|('SCRIPTNAME' path=STRING_LITERAL
{
if(!test){
script.getContent().add(Launcher.getString($path.text));
}else{
script.getContent().add("sleep 5  #"+Launcher.getString($path.text));
}
}
))
{
$job.setScript(script) ;
}

'STREAMLOGON' (ID |STRING_LITERAL)

 (description {$job.setDescription($description.description);})?
 
 tasktype?
 
 'INTERACTIVE'?
 
 successCondition? 
 
 (recoveryOptions{$recoveryOptions.recovery.state= $jobId.text ;
 
 $recovery = $recoveryOptions.recovery ;
 })?
;

recoveryOptions returns [Recovery recovery]:

 {
 $recovery = new Recovery() ;
 }
'RECOVERY'
  (('STOP' {$recovery.option = "stop" ;})
  
  |('RERUN' {$recovery.option = "rerun";})
  |('CONTINUE' {$recovery.option = "continue";})
  )
  ('AFTER' rec=ID {$recovery.after = $rec.text ;} )? // ambiguité : si spécifié avec un stop, un rerun ou un continue ,Override?
  
  {
  $recovery.setRecoveryType() ;
  }
  
  ('ABENDPROMPT' STRING_LITERAL)?
;


successCondition :

'RCCONDSUCC' STRING_LITERAL

;



tasktype :
'TASKTYPE' ID 
;

description returns [Description description] :
'DESCRIPTION' desc=STRING_LITERAL
{
$description = new Description() ;
$description.getContent().add(Launcher.getString($desc.text)) ;

}
;


//command returns [Script script]:
// {
// $script = new Script() ;
// } 
// 
// 
//(doCommand {$script.getContent().add($doCommand.code) ;})
//| 
//
//(scriptCommand 
//{
//
////System.out.println($scriptCommand.path) ;
//$script.getContent().add($scriptCommand.path) ;
//
//}
//
//
//)
//;
//
//doCommand returns [String code]:
//'DOCOMMAND' content=STRING_LITERAL
//{
//$code = $content.text ;
//}
//;
//
//scriptCommand returns [String path]:
//
//'SCRIPTNAME' content=STRING_LITERAL
//{
//$path = $content.text ;
//}
//;




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
    | 'A'..'Z'|'^'
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
    |'^'
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
FILE_NAME
  :
  ID?STRING_LITERAL
  ;

DATE_LITERAL
  :
  ('0'..'9') ('0'..'9') '/' ('0'..'9') ('0'..'9') '/' ('0'..'9') ('0'..'9') ('0'..'9') ('0'..'9')
  ;
