package syntaxtree;

import bytecode.*;
import bytecode.CodeProcedure;
import semantics.*;

public class RefVar extends VarExp {
    VarExp var; // Either Var or RefVar

    String type; // Set during type checking

    public RefVar(Var var) {
        this.var = var;
    }

    public RefVar(RefVar var) {
        this.var = var;
    }

    @Override
    public String printAst() {
        // (REF_VAR (VAR/REF_VAR))
        return "(REF_VAR " + var.printAst() + ")";
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        String type = var.typeCheck(st);
        this.type = "ref(" + type + ")";
        return getType();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        var.generateCode(codeProcedure);
    }

    @Override
    public void generateCodeStore(CodeProcedure codeProcedure) {
        var.generateCodeStore(codeProcedure);
    }
}
