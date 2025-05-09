package syntaxtree;

import semantics.*;
import java.util.List;


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
            // Regular variable: Look up name in the current variable scope
            VarDecl var = st.lookupV(name);
            if (var == null) {
                throw new TypeException("Unbound variable " + name);
            }
            return var.type;
        } 
        
        else {
            // exp.name, where exp must be a record-type
            String baseType = base.typeCheck(st);
            RecDecl record = st.lookupR(baseType);

            if (record == null) {
                throw new TypeException("Found no record named " + name);
            }

            // Search for field 'name' in record definition
            List<ParamfieldDecl> fields = record.paramfieldDecls;
            if (fields != null) {
                for (ParamfieldDecl f : fields) {
                    if (f.name.equals(name)) {
                        return f.type;
                    }
                }
            }

            // Did not find field
            throw new TypeException("Field " + name + " not found in record type " + baseType);
        }
    }
}
