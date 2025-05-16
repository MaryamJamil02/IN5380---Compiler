package syntaxtree;

import bytecode.*;
import semantics.*;

public class DerefVar extends VarExp {
    VarExp var; // Either Var or DerefVar

    String type; // set type

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
    public String typeCheck(SymbolTable st) throws TypeException {
        String withRef = var.typeCheck(st);
        if (!withRef.startsWith("ref(")) {
            throw new TypeException("Cannot dereference something that's not a reference, " + withRef);
        }

        // Remove 'ref(' in front of type
        String withoutRef = withRef.substring(3); // "(type)"
        this.type = withoutRef.substring(1, withoutRef.length() - 1);
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
