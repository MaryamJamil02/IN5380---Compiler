package syntaxtree;

import java.util.ArrayList;
import java.util.List;

import semantics.*;

public class CallStmt extends Stmt {
    String name;
    List<Exp> exps; // Optional

    public CallStmt(String name, List<Exp> exps) {
        this.name = name;
        this.exps = exps;
    }

    public CallStmt(String name) {
        this.name = name;
    }

    @Override
    public String printAst() {
        // (CALL_STMT (NAME("x") [ARGS(List<Exp>)]))

        StringBuilder sb = new StringBuilder();
        sb.append("(CALL_STMT (");
        sb.append("NAME(\"" + name + "\") ");

        if (exps != null) {
            sb.append("ARGS(");
            for (Exp e : exps) {
                sb.append(e.printAst());
            }
            sb.append(")");
        }

        sb.append("))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) {
        List<String> eTypes = new ArrayList<>();
        if (exps != null) {
            for (Exp e : exps) {
                eTypes.add(e.typeCheck(st));
            }
        }

        Object procDecl = st.lookup(name);
        if (procDecl == null) {
            throw new TypeException("Procedure " + name + " is unknown.");
        }
        if (!(procDecl instanceof ProcDecl)) {
            throw new Exception(name + " is called, but is not a procedure.");
        }
        
        return "void";
    }
}
