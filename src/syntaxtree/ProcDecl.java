package syntaxtree;

import java.util.List;

public class ProcDecl extends Decl{
    String name;
    List<ParamfieldDecl> pdl; // Optional
    String type;              // Optional
    List<Decl> dl;            // Optional
    List<Stmt> sl;

    public ProcDecl(String name, List<ParamfieldDecl> pdl, String type, List<Decl> dl, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.type = type;
        this.dl = dl;
        this.sl = sl;
    }

    public String printAst() {
        /* (PROC_DECL (NAME("x") 
                       [List<ParamfieldDecl>] 
                       [TYPE(INT)] 
                       [List<Decl>] 
                       List<Stmt>))
        */
        StringBuilder sb = new StringBuilder();
        sb.append("(PROC_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");

        // Optional list of paramfield decls
        if (pdl != null) {
            sb.append("\n");
            for (ParamfieldDecl pf : pdl) {
                sb.append("\t" + pf.printAst());
                sb.append("\n");
            }
        } else {
            sb.append("NULL");
        }

        // Optional type
        if (type != null) {
            sb.append(" TYPE(" + this.type + ") ");
        } else{
            sb.append(" NULL ");
        }

        // Optional decl list
        if (dl != null) {
            sb.append("\n");
            for (Decl d : dl) {
                sb.append("\t" + d.printAst());
                sb.append("\n");
            }
        } else{
            sb.append("NULL");
        }

        sb.append("\n");
        for (Stmt s : sl) {
            sb.append("\t" + s.printAst());
            sb.append("\n");
        }

        sb.append("))");
        return sb.toString();
    }
}
