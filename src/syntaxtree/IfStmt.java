package syntaxtree;

import java.util.List;

public class IfStmt extends Stmt {
    Exp cond;
    List<Stmt> if_stmts;   // May be null
    List<Stmt> else_stmts; // Optional

    public IfStmt(Exp cond, List<Stmt> if_stmts) {
        this.cond = cond;
        this.if_stmts = if_stmts;
    }
    public IfStmt(Exp cond, List<Stmt> if_stmts, List<Stmt> else_stmts) {
        this.cond = cond;
        this.if_stmts = if_stmts;
        this.else_stmts = else_stmts;
    }

    @Override
    public String printAst() {
        // (IF_STMT (COND(EXP) THEN(List<Stmt>) [ELSE (List<Stmt>)]))
        StringBuilder sb = new StringBuilder();
        sb.append("(IF_STMT (");

        sb.append("COND" + cond.printAst());

        sb.append("\n\t\t\tTHEN(");
        if (if_stmts != null) {
            sb.append("\n");
            for (Stmt s : if_stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
        } else {
            sb.append(" ");
        }
        sb.append("\t\t\t)");

        if (else_stmts != null) {
            sb.append("\n\t\t\tELSE(\n");
            for (Stmt s : else_stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
            sb.append("\t\t\t)");
        }

        sb.append("\t\t))");
        return sb.toString();
    }
}
