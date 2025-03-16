package syntaxtree;

import java.util.List;

public class CallStmt extends Stmt {
    String name;
    List<Exp> exps; // Optional

    public CallStmt(String name, List<Exp> exps) {
        this.name = name;
        this.exps = exps;
    }

    @Override
    public String printAst() {
        // (CALL_STMT (NAME("x") [ARGS(List<Exp>)]))

        StringBuilder sb = new StringBuilder();
        sb.append("(CALL_STMT (");
        sb.append("NAME(\"" + name + "\") ");

        if (!exps.isEmpty()) {
            sb.append("ARGS(");
            for (Exp e : exps) {
                sb.append("\t" + e.printAst() + "\n");
            }
            sb.append(")");
        }

        sb.append("))");
        return sb.toString();
    }    
}
