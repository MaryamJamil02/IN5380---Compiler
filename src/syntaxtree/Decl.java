package syntaxtree;

public class Decl {
    VarDecl vd;
    RecDecl rd;
    ProcDecl pd;
    
    public Decl (VarDecl vd) {
        this.vd = vd;
    }

    public Decl (RecDecl rd) {
        this.rd = rd;
    }

    public Decl (ProcDecl vd) {
        this.pd = pd;
    }

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (vd != null){
            sb.append("\t" + vd.printAst());
        }
        
        else if (rd != null){
            sb.append("\t" + rd.printAst());
        }

        else if (pd != null){
            sb.append("\t" + pd.printAst());
        }

        sb.append(")");

        return sb.toString();

    }
    
}
