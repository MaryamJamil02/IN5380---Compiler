package syntaxtree;

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
    public String typeCheck(SymbolTable st){
        Object record = st.lookup(name);
        if (record == null) {
            throw new TypeException("Record type " + name + " is unknown.");
        }

        if (!(record instanceof RecDecl)) {
            throw new TypeException(name + " is called with 'new', but is not a record.");
        }

        return name;
    }
}
