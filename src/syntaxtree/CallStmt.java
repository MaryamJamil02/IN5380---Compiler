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
    public String typeCheck(SymbolTable st) throws TypeException {
        List<String> eTypes = new ArrayList<>();
        if (exps != null) {
            for (Exp e : exps) {
                eTypes.add(e.typeCheck(st));
            }
        }

        // Check if the procedure exists
        ProcDecl procedure = st.lookupP(name);
        if (procedure == null) {
            throw new TypeException("Procedure " + name + " is unknown.");
        }

        // The number and types of the parameters of a procedure must coincide
        List<ParamfieldDecl> parameters = procedure.pdl != null ? procedure.pdl : new ArrayList<>();

        int expsSize = exps != null ? exps.size() : 0;
        if (parameters.size() != expsSize) {
            throw new TypeException("Procedure " + name + " was called with too many/too few arguments.");
        }
        for (int i = 0; i < expsSize; i++) {
            String expected = parameters.get(i).type;
            String actual   = eTypes.get(i);
            if (!expected.equals(actual) && !(expected.equals("float") && actual.equals("int"))) {
                throw new TypeException("Expected parameter type " +  expected + ", but found " + actual);
            }
        }

        String type = procedure.type;
        if (type != null) return type;
        return "void";
    }
}
