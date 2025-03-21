package syntaxtree;

import java.util.List;

public class RecDecl extends Decl {
    String name;
    List<ParamfieldDecl> paramfieldDecls; // Optional

    public RecDecl (String name, List<ParamfieldDecl> paramfieldDecls) {
        this.name = name;
        this.paramfieldDecls = paramfieldDecls;
    }

    @Override
    public String printAst() {
        // (REC_DECL (NAME("x") [List<ParamfieldDecl>]))
        StringBuilder sb = new StringBuilder();
        sb.append("(REC_DECL (");
        sb.append("NAME(\"" +  name + "\") ");
        
        if (paramfieldDecls != null){
            sb.append("\n");
            for (ParamfieldDecl p : paramfieldDecls) {
                sb.append("\t\t" + p.printAst() + "\n");
            }
        } else {
            sb.append("NULL");
        }

        sb.append("\t))");
        return sb.toString();
    }
}