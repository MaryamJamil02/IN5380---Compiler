package syntaxtree;

import bytecode.*;
import bytecode.instructions.*;

import semantics.*;

public class NewExp extends Exp {
    String name;

    public NewExp(String name) {
        this.name = name;
    }

    @Override
    public String printAst() {
        // (NEW_EXP(NAME("x")))
        StringBuilder sb = new StringBuilder();
        sb.append("(NEW_EXP (");
        sb.append("NAME(\"" + this.name + "\")");
        sb.append("))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        if (st.lookupR(name) == null) {
            throw new TypeException("Record type " + name + " is unknown.");
        }

        return getType();
    }

    public String getType() {
        return name;
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        codeProcedure.addInstruction(new NEW(codeProcedure.structNumber(name)));
    }
}
