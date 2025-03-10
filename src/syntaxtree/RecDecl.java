package syntaxtree;

import java.util.List;

public class RecDecl {
    String name;
    List<RecDecl> rdl;

    public RecDecl (String name, List<RecDecl> rdl) {
        this.name = name;
        this.rdl = rdl;
    }

    public RecDecl (String name) {
        this.name = name;
    }

    

    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append("REC_DECL");
        sb.append("(NAME (\"" +  name + "\" )");
        
        if (rdl != null){
            for (RecDecl r : rdl) {
                sb.append("\t" + r.printAst());
                sb.append("\n");
            }
        }

       else{
        sb.append("NULL");
       }

        sb.append(")");
        return sb.toString();
    }
}