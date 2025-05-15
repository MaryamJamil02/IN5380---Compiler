package syntaxtree;

import semantics.*;

public class AssignStmt extends Stmt {
    VarExp v;  // Either Var or DerefVar
    Exp e;

    public AssignStmt (VarExp v, Exp e) {
        this.v = v;
        this.e = e;
    }

    @Override
    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("(ASSIGN_STMT ");
        sb.append(v.printAst() + " ");
        sb.append(e.printAst());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        String varType = v.typeCheck(st);
        String expType = e.typeCheck(st);

        if (!isAssignmentCompatible(varType, expType)){
            throw new TypeException("Cannot assign " + expType + " to " + varType);
        }
        return varType;
    }

    private boolean isAssignmentCompatible(String v, String e) {
        if (v.equals(e) || (v.equals("float") && e.equals("int"))) return true;
        return false;
    }
}