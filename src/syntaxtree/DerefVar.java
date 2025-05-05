package syntaxtree;

import semantics.*;

public class DerefVar extends VarExp {
    VarExp var; // Either Var or DerefVar

    public DerefVar(Var var) {
        this.var = var;
    }
    public DerefVar(DerefVar var) {
        this.var = var;
    }

    @Override
    public String printAst() {
        // (DEREF_VAR (VAR/DEREF_VAR))
        return "(DEREF_VAR " + var.printAst() + ")";
    }

    @Override
    public String typeCheck(SymbolTable st) {
        return var.typeCheck(st);
    }

    @Override
    public String getType() {
        // Remove 'REF(' in front of type 
        String withRef = var.getType();
        return withRef.substring(4, withRef.length()-1);
    }
}
