package syntaxtree;

import semantics.*;
import java.util.List;

import bytecode.CodeFile;
import bytecode.CodeProcedure;

import java.util.ArrayList;

public class CallExp extends Exp {
    CallStmt cs;
    String type; // Set in type check

    public CallExp(CallStmt cs) {
        this.cs = cs;
    }

    @Override
    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append(cs.printAst());
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        List<String> eTypes = new ArrayList<>();
        if (cs.exps != null) {
            for (Exp e : cs.exps) {
                eTypes.add(e.typeCheck(st));
            }
        }

        // Check if the procedure exists
        ProcDecl procedure = st.lookupP(cs.name); // The actual procedure
        if (procedure == null) {
            throw new TypeException("Procedure " + cs.name + " is unknown.");
        }

        // The number and types of the parameters of a procedure must coincide
        List<ParamfieldDecl> parameters = procedure.pdl != null ? procedure.pdl : new ArrayList<>();

        int expsSize = cs.exps != null ? cs.exps.size() : 0;
        if (parameters.size() != expsSize) {
            throw new TypeException("Procedure " + cs.name + " was called with too many/too few arguments.");
        }
        for (int i = 0; i < expsSize; i++) {
            String expected = parameters.get(i).type;
            String actual = eTypes.get(i);

            // if expected is float and
            if (!expected.equals(actual) && !(expected.equals("float") && actual.equals("int"))) {
                throw new TypeException("Expected parameter type " + expected + ", but found " + actual);
            }
        }

        this.type = procedure.type;
        if (type == null) {
            throw new TypeException("Function call must have return type when used in an expression.");
        }
        return getType();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        cs.generateCode(codeProcedure);
    }

}
