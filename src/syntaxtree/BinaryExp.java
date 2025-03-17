package syntaxtree;

public class BinaryExp extends Exp {
    Exp e1;
    String op; 
    Exp e2;

    public BinaryExp(Exp e1, String op, Exp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public String printAst() {
        // (BINARY_EXP (EXP + EXP))
        StringBuilder sb = new StringBuilder();
        sb.append("(BINARY_EXP (");
        sb.append(e1.printAst() + " " + op + " " + e2.printAst() + "))");
        return sb.toString();
    }    
}
