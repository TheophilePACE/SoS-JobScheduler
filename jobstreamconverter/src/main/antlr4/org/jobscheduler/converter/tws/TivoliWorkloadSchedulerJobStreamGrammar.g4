/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar TivoliWorkloadSchedulerJobStreamGrammar;

init : SCHEDULE+;

SCHEDULE : 'SCHEDULE' scheduleID NEWLINE 
    |   'ON' runCycle NEWLINE 
	|	'END'; 
  
scheduleID  : ID;
//runCycle : ID
//	|  '\"' frequency '\"'; 
//frequency : ID;  

ID : [a-zA-Z0-9#_/:;,\'.@()=\-\"\+\&+]+;
NEWLINE:'\r'? '\n' ; // return newlines to parser (is end-statement signal)
WS : [ \t\r\n]+ -> skip ; // Define whitespace rule, toss it out   
    
