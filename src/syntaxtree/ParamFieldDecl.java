package syntaxtree;

public class ParamFieldDecl {
    String name;
    String type;

    public ParamFieldDecl(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("PARAM_FIELD_DECL");
        sb.append("(NAME (" + this.name + ")");
        sb.append("TYPE (" + this.type + ")");
        sb.append(")");

        return sb.toString();
    }

}
