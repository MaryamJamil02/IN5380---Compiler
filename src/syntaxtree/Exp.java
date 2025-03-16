package syntaxtree;

public class Exp {
    public Exp(String name) {
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("(EXP (");
        sb.append("))");
        return sb.toString();
    }    
}
