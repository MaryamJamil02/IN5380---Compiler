package syntaxtree;

public class CallExp extends Exp {
    CallStmt cs;

    public CallExp(CallStmt cs) {
        this.cs = cs;
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append(cs.printAst());
        return sb.toString();
    }    
}
