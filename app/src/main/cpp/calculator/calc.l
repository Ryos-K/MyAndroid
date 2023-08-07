%{
#include <stdio.h>
#include "parser.h"
%}

%option noyywrap
%option header-file="lexer.h"

%%
"+"         return ADD;
"-"         return SUB;
"*"         return MUL;
"/"         return DIV;
"%"         return MOD;
"("         return LP;
")"         return RP;
[ \t]+      ;
[1-9][0-9]* {
    int val;
    sscanf(yytext, "%d", &val);
    yylval.num = val;
    return NUMBER;
}
%%