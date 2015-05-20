grammar TWS_VARIABLES;

options {
  language = Java;
}


@header {
package org.jobscheduler.converter.tws.variables ;
import  org.jobscheduler.converter.tws.scheduler.*;
import java.util.LinkedList ;
import java.util.HashMap ;
}
 
@lexer::header {
package org.jobscheduler.converter.tws.variables ;


}
variables returns [HashMap<String,Params> variables]: 
  {variables = new HashMap<String,Params>() ;}
 (variable{variables.put($variable.key,$variable.params) ;})+
 
 ;




variable returns [Params params,String key]:   

{$params = new Params() ;}
'VARTABLE' id=ID {$key=$id.text ;}
('DESCRIPTION' STRING_LITERAL)?
'ISDEFAULT'?
'MEMBERS' 
(varDefinition {$params.getParamOrCopyParamsOrInclude().add($varDefinition.param) ;})*
'END' 
;

varDefinition returns [Param param] : 
{$param = new Param() ;}
id=ID val=STRING_LITERAL 
{$param.setName($id.text) ;
$param.setValue($val.text) ;
}

;


 STRING_LITERAL
  : '"'
    { StringBuilder b = new StringBuilder(); }
    ( c=~('"'|'\r'|'\n')  { b.appendCodePoint(c);})*
    '"'
    { setText(b.toString()); }
  ; 
 
 DATE_LITERAL : ('0'..'9')('0'..'9')'/'('0'..'9')('0'..'9')'/'('0'..'9')('0'..'9')('0'..'9')('0'..'9') ;
 
 
ID : ('A'..'Z'|'a'..'z'|'0'..'9'|'-'|'_'|'^')+ ; 
 WS : ( ' ' | '\t' | '\r' | '\n' )+ { $channel = HIDDEN; } ;
