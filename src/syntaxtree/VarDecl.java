package syntaxtree;

import semantics.*;
import bytecode.*;
import bytecode.type.*;
import bytecode.instructions.*;

public class VarDecl extends Decl {
    // String name;
    String type; // Optional
    Exp exp; // Optional

    public VarDecl(String name, String type, Exp exp) {
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
    public String typeCheck(SymbolTable st) throws TypeException {
        if (st.lookupV(name) != null) {
            throw new TypeException("Illegal double declaration: Variable " + name + " already exists.");
        }
        st.addV(name, this);

        if (type != null) {
            // "var" NAME ":" TYPE [ ":=" EXP ]
            if (exp != null) {
                String expT = exp.typeCheck(st);
                if (!type.equals(expT)) {
                    throw new TypeException(
                            "Variable type (" + type + ") and expression type (" + expT + ") don't match.");
                }
            }
        } else {
            // "var" NAME ":=" EXP
            type = exp.typeCheck(st);
        }

        return type;
    }

    // GLobal variable
    @Override
    public void generateCode(CodeFile codefile) {

        codefile.addVariable(name);
        codefile.updateVariable(name, new RuntimeType(type, codefile.structNumber(type)).type);
    }

    // Local variable (inside procDecl)
    public void generateCode(CodeProcedure codeProcedure) {

        codeProcedure.addLocalVariable(name, new RuntimeType(type, codeProcedure.structNumber(type)).type);

        if (exp != null) {
            exp.generateCode(codeProcedure);
            int index = codeProcedure.variableNumber(name);
            codeProcedure.addInstruction(new STORELOCAL(index));
        }
    }
}