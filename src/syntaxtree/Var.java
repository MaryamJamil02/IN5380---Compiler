package syntaxtree;

import semantics.*;

public class Var extends VarExp {
    Exp base;    // Optional
    String name;

    public Var(String name) {
        this.base = null;
        this.name = name;
    }

    public Var(Exp base, String name) {
        this.base = base;
        this.name = name;
    }

    @Override
    public String printAst() {
        // (VAR ([EXP] NAME("x")))
        StringBuilder sb = new StringBuilder();
        sb.append("(VAR (");
        if (base != null) {
            sb.append(base.printAst() + " ");
        }
        sb.append("NAME(\"" + name + "\")))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) {
        if (base == null) {
            // Regular variable
            st.lookup(name);
        } else {
            String baseType = base.typeCheck(st);

            if (baseType != "record") {
                throw new TypeException("");
            }
            st.lookupField(baseType, name);
        }
    }
}
