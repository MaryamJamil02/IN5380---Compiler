package syntaxtree;

import semantics.*;

public class VarDecl extends Decl {
    String name;
    String type; // Optional
    Exp exp;     // Optional
    
    public VarDecl (String name, String type, Exp exp) {
        this.name = name;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public String printAst() { 
        // (VAR_DECL (NAME("x") TYPE(INT) NULL))
        StringBuilder sb = new StringBuilder();
        sb.append("(VAR_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");

        if (type != null) {
            sb.append("TYPE(" + this.type + ") ");
        } else {
            sb.append("NULL ");
        }

        if (exp != null) {
            sb.append(exp.printAst());
        } else {
            sb.append("NULL");
        }

        sb.append("))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) {

        if (type != null) {
            // "var" NAME ":" TYPE [ ":=" EXP ]
            if (exp != null) {
                String expT = exp.typeCheck(st);
                if (!type.equals(expT)) {
                    throw new TypeException("Variable type (" + type + ") and expression type (" + expT + ") don't match.");
                }
            }
            return type;
        }
        else {
            //  | "var" NAME ":=" EXP
            return exp.typeCheck(st);
        }
    }    
}