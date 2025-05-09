package syntaxtree;

import java.util.List;
import semantics.*;

public class Program {

    List<Decl> decls; // Optional
    String name;

    public Program(String name, List<Decl> decls) {
        this.decls = decls;
        this.name = name;
    }

    public String printAst(){  // "pretty" printing 
        StringBuilder sb = new StringBuilder();
        sb.append("(PROGRAM ");
        sb.append("(NAME " + this.name + ")");

        if (decls != null) {
            sb.append("\n");
            for (Decl decl : decls) {
                sb.append("\t" + decl.printAst() + "\n");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public String typeCheck(SymbolTable st){
        // Each program must have a procedure named main.
        boolean hasMain = false;

        if (decls != null) {
            for (Decl decl : decls) {
                decl.typeCheck(st);
                if (decl.name.equals("main")) hasMain = true;
            }
        }

        if (!hasMain) {
            throw new TypeException("Found no procedure named main.");
        }

        return "void";
    }
}
