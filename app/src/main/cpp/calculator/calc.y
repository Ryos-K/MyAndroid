%{
#include <stdio.h>
%}

%parse-param { int *result }

%union {
    int num;
}

%token <num> NUMBER

%type <num> stmt expr term prim

%token ADD SUB MUL DIV MOD LP RP

%%
stmt
    : expr
    { *result = $1; }
    ;
expr
    : term
    | expr ADD term
    { $$ = $1 + $3; }
    | expr SUB term
    { $$ = $1 - $3; }
    ;

term
    : prim
    | term MUL prim
    { $$ = $1 * $3; }
    | term DIV prim
    { $$ = $1 / $3; }
    | term MOD prim
    { $$ = $1 % $3; }
    ;

prim
    : NUMBER
    | LP expr RP
    { $$ = $2; }
    ;


%%
int yyerror(char const *str)
{
    return 1;
}
