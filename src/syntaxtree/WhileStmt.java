package syntaxtree;

import java.util.List;

public class WhileStmt extends Stmt {
    Exp cond;
    List<Stmt> stmts;   // May be empty

    public WhileStmt(Exp cond, List<Stmt> stmts) {
        this.cond = cond;
        this.stmts = stmts;
    }

    @Override
    public String printAst() {
        // (WHILE_STMT (WHILE(EXP) DO(List<Stmt>)))
        StringBuilder sb = new StringBuilder();
        sb.append("(WHILE_STMT (");

        sb.append("WHILE " + cond.printAst());

        sb.append("\n\t\t\tDO (");
        if (!stmts.isEmpty()) {
            sb.append("\n");
            for (Stmt s : stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
        } else {
            sb.append(" ");
        }
        sb.append("\t\t\t)))");
        return sb.toString();
    }

    @Override
    public void typeCheck(){
        String condType = cond.getType();

        if (condType != "bool") {
            throw new TypeException("Condition in an While statement must be of type bool");
            
        }
    }
}
