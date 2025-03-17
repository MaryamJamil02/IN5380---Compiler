package syntaxtree;

public class NewExp extends Exp {
    String name;

    public NewExp(String name) {
        this.name = name;
    }

    public String printAst() {
        // (NEW_EXP(NAME("x")))
        StringBuilder sb = new StringBuilder();
        sb.append("(NEW_EXP (");
        sb.append("NAME(\"" + this.name + "\")");
        sb.append("))");
        return sb.toString();
    }    
}
