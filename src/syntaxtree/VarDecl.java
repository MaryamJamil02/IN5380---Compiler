package syntaxtree;

public class VarDecl {
    String name;
    String type;
    Exp exp;
    
    public VarDecl (String name, String type, Exp exp) {
        this.name = name;
        this.type = type;
        this.exp = exp;
    }

    public VarDecl (String name, String type) {
        this.name = name;
        this.type = type;

    }

    public VarDecl (String name, Exp exp) {
        this.name = name;
        this.exp = exp;

    }

    

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("VAR_DECL ");
        sb.append("(NAME (" + this.name + ")");

        if (type != null) {
            sb.append("TYPE (" + this.type + ")");
        }

        else{
            sb.append("(NULL)");
        }

        if (exp != null) {
            sb.append(exp.printAst());
        }

        else{
            sb.append("NULL");
        }

        sb.append(")");

        return sb.toString();





    }
}
