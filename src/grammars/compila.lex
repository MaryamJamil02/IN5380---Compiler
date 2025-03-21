package parser;
import java_cup.runtime.*;
%%

%class Lexer
%unicode
%cup
%line
%column
%public
%state STRING
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
Comment = "//" ~{LineTerminator} | "(*" ~"*)"

Identifier = [A-Za-z]([A-Za-z0-9_]*[A-Za-z0-9])?

IntLiteral = 0 | [1-9][0-9]*
FloatLiteral = [0-9]+ \.[0-9]+

%%
<YYINITIAL>{
        {WhiteSpace}                    {  }
        {Comment}                       {  }

        "program"                       { return symbol(sym.PROGRAM); }
        "struct"                        { return symbol(sym.STRUCT); }
        "procedure"                     { return symbol(sym.PROCEDURE); }
        "begin"                         { return symbol(sym.BEGIN); }
        "end"                           { return symbol(sym.END); }
        "float"                         { return symbol(sym.FLOAT);}
        "int"                           { return symbol(sym.INT);}
        "string"                        { return symbol(sym.STRING);}
        "bool"                          { return symbol(sym.BOOL);}
        "ref"                           { return symbol(sym.REF);}
        "var"                           { return symbol(sym.VAR);}
        "in"                            { return symbol(sym.IN);}
        "not"                           { return symbol(sym.NOT);}
        "new"                           { return symbol(sym.NEW);}
        "deref"                         { return symbol(sym.DEREF);}
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
        "return"                        { return symbol(sym.RETURN); }

        {IntLiteral}                    { return symbol(sym.INT_LITERAL,Integer.parseInt(yytext())); }
        {FloatLiteral}                  { return symbol(sym.FLOAT_LITERAL,Float.parseFloat(yytext())); }
        {Identifier}                    { return symbol(sym.ID,yytext()); }

        "("                             { return symbol(sym.LPAR); }
        ")"                             { return symbol(sym.RPAR); }
        ";"                             { return symbol(sym.SEMI); }
        ":="                            { return symbol(sym.ASSIGN);}
        ":"                             { return symbol(sym.COLON);}
        "{"                             { return symbol(sym.LBRACKET);}
        "}"                             { return symbol(sym.RBRACKET);}
        "."                             { return symbol(sym.PERIOD);}
        ","                             { return symbol(sym.COMMA);}
        "&&"                            { return symbol(sym.AND);}
        "||"                            { return symbol(sym.OR);}
        "<="                            { return symbol(sym.LESSEQUAL);}
        ">="                            { return symbol(sym.GREATEREQUAL);}
        "<>"                            { return symbol(sym.NOTEQUAL);}
        "<"                             { return symbol(sym.LESS);}
        ">"                             { return symbol(sym.GREATER);}
        "="                             { return symbol(sym.EQUAL);}
        "+"                             { return symbol(sym.PLUS);}
        "-"                             { return symbol(sym.MINUS);}
        "*"                             { return symbol(sym.MULTIPLY);}
        "/"                             { return symbol(sym.SLASH);}
        "^"                             { return symbol(sym.EXPONENT);}

        "\""                            { string.setLength(0); yybegin(STRING); }
}

<STRING> {
    [^\"\n\r]*    { string.append(yytext()); }
    "\""          { yybegin(YYINITIAL); 
                    return symbol(sym.STRING_LITERAL,string.toString()); }
    {LineTerminator}  { throw new Error("Illegal newline in string at line " + yyline + ", column " + yycolumn + "."); }
}

.                    { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
