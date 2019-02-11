grammar Soul;

prog
    : describtion*
    ;

describtion
    : '@' ID block
    ;
block
    : '{' stmtlist '}'
    ;
stmtlist
    : stmt*
    ;
stmt
    : field 'rely' field ';'    #FIELD_RELY
    | field 'in' range ';'      #FIELD_RANGE
    | ID (',' ID)* ';'          #IDS
    | field block               #FIELD_BLOCK
    ;
range
    : NUMBER '~' NUMBER
    ;
field
    : ID'.'ID
    ;

ID
    :  ('a'..'z' | 'A'..'Z' | '_')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')*
    ;
NUMBER
    :  [0-9]+
    |  [0-9]+'.'[0-9]+
    ;
WS  :   [ \t\n\r]+ -> skip ;
SL_COMMENT
    :   '//' .*? '\n' -> skip
    ;