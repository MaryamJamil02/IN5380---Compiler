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
        "var"                           {return symbol(sym.VAR);}
        "colon"                         {return symbol(sym.COLON);}
        "float"                         {return symbol(sym.FLOAT);}
        "int"                           {return symbol(sym.INT);}
        "string"                        {return symbol(sym.STRING);}
        "bool"                          {return symbol(sym.BOOL);}
        "ref"                           {return symbol(sym.REF);}
        ":="                            {return symbol(sym.ASSIGN);}
        "in"                            {return symbol(sym.IN);}
        "{"                             {return symbol(sym.LBRACKET);}
        "}"                             {return symbol(sym.RBRACKET);}
        "not"                           {return symbol(sym.NOT);}
        "new"                           {return symbol(sym.NEW);}
}

.                           { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
