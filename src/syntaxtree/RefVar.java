package syntaxtree;

import semantics.*;

public class RefVar extends VarExp{
    VarExp var; // Either Var or RefVar

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
        return "ref(" + type + ")";
    }
}
