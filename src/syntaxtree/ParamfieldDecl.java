package syntaxtree;

public class ParamfieldDecl {
    String name;
    String type;

    public ParamfieldDecl(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String printAst() {
        // (PARAMFIELD_DECL (NAME("x") TYPE(INT)))
        StringBuilder sb = new StringBuilder();
        sb.append("(PARAMFIELD_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");
        sb.append("TYPE(" + this.type + ")");
        sb.append("))");
        return sb.toString();
    }
}
