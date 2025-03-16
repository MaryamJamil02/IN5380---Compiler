package syntaxtree;

import java.util.List;

public class WhileStmt extends Stmt {
    Exp cond;
    List<Stmt> stmts;   // May be null

    public WhileStmt(Exp cond, List<Stmt> stmts) {
        this.cond = cond;
        this.stmts = stmts;
    }

    @Override
    public String printAst() {
        // (WHILE_STMT (WHILE(EXP) DO(List<Stmt>)))
        StringBuilder sb = new StringBuilder();
        sb.append("(WHILE_STMT (");

        sb.append("WHILE" + cond.printAst() + " ");

        sb.append("DO(");
        if (stmts != null) {
            sb.append("\n");
            for (Stmt s : stmts) {
                sb.append("\t" + s.printAst() + "\n");
            }
        } else {
            sb.append(" ");
        }
        sb.append(")))");
        return sb.toString();
    }
}
