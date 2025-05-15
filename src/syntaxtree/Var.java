package syntaxtree;

import semantics.*;
import java.util.List;


public class Var extends VarExp {
    Exp exp;    // Optional
    String name;

    public Var(String name) {
        this.exp = null;
        this.name = name;
    }

    public Var(Exp exp, String name) {
        this.exp = exp;
        this.name = name;
    }

    @Override
    public String printAst() {
        // (VAR ([EXP] NAME("x")))
        StringBuilder sb = new StringBuilder();
        sb.append("(VAR (");
        if (exp != null) {
            sb.append(exp.printAst() + " ");
        }
        sb.append("NAME(\"" + name + "\")))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException{
        if (exp == null) {
            // Regular variable: Look up name in the current variable scope
            VarDecl var = st.lookupV(name);
            if (var == null) {
                throw new TypeException("Unbound variable " + name);
            }
            return var.type;
        } 
        
        else {

            // exp.name, where exp must be a record-type
            String expType = exp.typeCheck(st);
            RecDecl record = st.lookupR(expType);

            if (record == null) {
                throw new TypeException("Found no record named " + expType);
            }

            // Search for field 'name' in record definition
            List<ParamfieldDecl> fields = record.paramfieldDecls;
            if (fields != null) {
                for (ParamfieldDecl f : fields) {
                    if (f.name.equals(name)) return f.type;
                }
            }

            // Did not find field
            throw new TypeException("Field " + name + " not found in record type " + expType);
        }
    }
}
