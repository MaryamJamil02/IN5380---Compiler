package syntaxtree;

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
}