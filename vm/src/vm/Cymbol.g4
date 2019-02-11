grammar Cymbol;

prog:   funDecl+//函数
;

funDecl
    :   funName=ID '('parameterList')' funBlock=block
    ;

parameterList : (ID (',' ID)*)? ;

block:  '{' stmtList '}' ;   // possibly empty statement block

stmtList
    :   stmt*
    ;

stmt
    : RETURN expr ';'
    | RETURN stat ';'
	| ifStmt
    | expr ';'
    | whileStmt ';'
    | block
    | stat ';'
    //	| 'do' stmt WHILE '(' expr ')'
    //	| 'for' '(' ini=expr ';' tes=expr ';' inc=expr ')'
    ;

ifStmt
    : IF '(' expr ')' case1=stmt 'else' case2=stmt
	| IF '(' expr ')' case1=stmt
    ;

whileStmt
    : 'while' '('expr')' stmt
    ;
stat
	:	ID '=' expr          #Asn
	|   ID '(' exprList ')'  #Call
	;

expr
//    |   expr '[' expr ']'       # Index
    :   op=SUB expr                # Negate
    |   op=NOT expr                # Not
    |   expr op=(GT|LT) expr                # GtLt
    |   expr op=(MUL|DIV) expr           # MulDiv
    |   expr op=(ADD|SUB) expr     # AddSub
    |   expr op='==' expr          # Equal
    |   ID                      # Var
    |   NUMBER                     # Number
    ;

exprList : (expr (',' expr)*)? ;   // arg list


MUL:'*';
DIV:'/';
ADD:'+';
SUB:'-';
NOT:'!';
IF : 'if' ;
GT : '>';
LT : '<';
RETURN : 'return' ;

ID  :  ('a'..'z' |'A'..'Z' )('a'..'z' |'A'..'Z'|'0'..'9')* ;
NUMBER :   [0-9]+ ;
WS  :   [ \t\n\r]+ -> skip ;
SL_COMMENT
    :   '//' .*? '\n' -> skip
    ;
