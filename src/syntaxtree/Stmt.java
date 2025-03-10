package syntaxtree;

public class Stmt {
    AssignStmt as;
    IfStmt is;
    WhileStmt ws;
    ReturnStmt rs;
    CallStmt cs;

    public Stmt(AssignStmt as) {
        this.as = as;
    }

    public Stmt(IfStmt is) {
        this.is = is;
    }

    public Stmt(WhileStmt as) {
        this.ws = ws;
    }

    public Stmt(ReturnStmt rs) {
        this.rs = rs;
    }

    public Stmt(CallStmt cs) {
        this.cs = cs;
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (as != null){
            sb.append("\t" + as.printAst());
        }
        
        else if (is != null){
            sb.append("\t" + is.printAst());
        }

        else if (ws != null){
            sb.append("\t" + ws.printAst());
        }

        else if (rs != null){
            sb.append("\t" + rs.printAst());
        }

        else if (cs != null){
            sb.append("\t" + cs.printAst());
        }

        sb.append(")");

        return sb.toString();

    }

    
}
