package syntaxtree;
import java.util.List;

public class Program {

    List<Decl> decls;
    String name;

    public Program(String name, List<Decl> decls) {
        this.decls = decls;
        this.name = name;
    }

    public String printAst(){  // "pretty" printing 
        StringBuilder sb = new StringBuilder();
        sb.append("(PROGRAM ");
        sb.append("(NAME ");
        sb.append(this.name);
        sb.append(")\n");
        for (Decl decl : decls) {
            sb.append("\t" + decl.printAst());
            sb.append("\n");
        }
        sb.append(")");
        return sb.toString();
        
    }
}
