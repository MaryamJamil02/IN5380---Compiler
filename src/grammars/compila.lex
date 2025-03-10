package parser;
import java_cup.runtime.*;
%%

%class Lexer
%unicode
%cup
%line
%column
%public
%{
 StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*

%%
<YYINITIAL>{
        {WhiteSpace}                    {  }
        "program"                       { return symbol(sym.PROGRAM); }
        "struct"                        { return symbol(sym.STRUCT); }
        "begin"                         { return symbol(sym.BEGIN); }
        "end"                           { return symbol(sym.END); }
        "("                             { return symbol(sym.LPAR); }
        ")"                             { return symbol(sym.RPAR); }
        ";"                             { return symbol(sym.SEMI); }
        {Identifier}                    { return symbol(sym.ID,yytext()); }
        "var"                           { return symbol(sym.VAR);}
        "colon"                         { return symbol(sym.COLON);}
        "float"                         { return symbol(sym.FLOAT);}
        "int"                           { return symbol(sym.INT);}
        "string"                        { return symbol(sym.STRING);}
        "bool"                          { return symbol(sym.BOOL);}
        "ref"                           { return symbol(sym.REF);}
        ":="                            { return symbol(sym.ASSIGN);}
        "in"                            { return symbol(sym.IN);}
        "{"                             { return symbol(sym.LBRACKET);}
        "}"                             { return symbol(sym.RBRACKET);}
        "not"                           { return symbol(sym.NOT);}
        "new"                           { return symbol(sym.NEW);}
        "deref"                         { return symbol(sym.DEREF);}
        "."                             { return symbol(sym.PERIOD);}
        "&&"                            { return symbol(sym.AND);}
        "||"                            { return symbol(sym.OR);}
        "<"                             { return symbol(sym.LESS);}
        "<="                            { return symbol(sym.LESSEQUAL);}
        ">"                             { return symbol(sym.GREATER);}
        ">="                            { return symbol(sym.GREATEREQUAL);}
        "="                             { return symbol(sym.EQUAL);}
        "<>"                            { return symbol(sym.ANGLEBRACKETS);}
        "+"                             { return symbol(sym.PLUS);}
        "-"                             { return symbol(sym.MINUS);}
        "*"                             { return symbol(sym.MULTIPLY);}
        "/"                             { return symbol(sym.SLASH);}
        "^"                             { return symbol(sym.EXPONENT);}
        "null"                          { return symbol(sym.NULL);}
        "true"                          { return symbol(sym.TRUE);}
        "false"                         { return symbol(sym.FALSE);}
        "if"                            { return symbol(sym.IF);}
        "then"                          { return symbol(sym.THEN);}
        "else"                          { return symbol(sym.ELSE);}
        "fi"                            { return symbol(sym.FI);}
        "while"                         { return symbol(sym.WHILE);}
        "do"                            { return symbol(sym.DO);}
        "od"                            { return symbol(sym.OD);}


}

.                           { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
