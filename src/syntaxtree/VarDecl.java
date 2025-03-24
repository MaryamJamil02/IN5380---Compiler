package syntaxtree;

public class VarDecl extends Decl {
    String name;
    String type; // Optional
    Exp exp;     // Optional
    
    public VarDecl (String name, String type, Exp exp) {
        this.name = name;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public String printAst() { 
        // (VAR_DECL (NAME("x") TYPE(INT) NULL))
        StringBuilder sb = new StringBuilder();
        sb.append("(VAR_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");

        if (type != null) {
            sb.append("TYPE(" + this.type + ") ");
        } else {
            sb.append("NULL ");
        }

        if (exp != null) {
            sb.append(exp.printAst());
        } else {
            sb.append("NULL");
        }

        sb.append("))");
        return sb.toString();
    }
}
