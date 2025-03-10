package syntaxtree;

import java.util.List;

public class ProcDecl {

    String name;
    List<ParamFieldDecl> pdl;
    String type;
    List<Decl> dl;
    List<Stmt> sl;


    public ProcDecl(String name, List<ParamFieldDecl> pdl, String type, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.type = type;
        this.sl = sl;
    }


    public ProcDecl(String name, List<ParamFieldDecl> pdl, List<Decl> dl, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.dl = dl;
        this.sl = sl;
    }


    public ProcDecl(String name, List<ParamFieldDecl> pdl, String type, List<Decl> dl, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.type = type;
        this.dl = dl;
        this.sl = sl;
    }


    public ProcDecl(String name, List<ParamFieldDecl> pdl, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.sl = sl;
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("PROC_DECL");
        sb.append("(NAME (" + this.name + ")");
        for (ParamFieldDecl pf : pdl) {
            sb.append("\t" + pf.printAst());
            sb.append("\n");
        }

        if (type != null) {
            sb.append("TYPE (" + this.type + ")");
        }

        else{
            sb.append("(NULL)");
        }

        if (dl != null) {
            for (Decl d : dl) {
                sb.append("\t" + d.printAst());
                sb.append("\n");
            }
        }

        else{
            sb.append("(NULL)");
        }

        for (Stmt s : sl) {
            sb.append("\t" + s.printAst());
            sb.append("\n");
        }

        sb.append(")");
        return sb.toString();
    }
    
}
