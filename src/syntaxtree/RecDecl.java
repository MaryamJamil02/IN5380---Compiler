package syntaxtree;

import java.util.List;
import semantics.*;

public class RecDecl extends Decl {
    String name;
    List<ParamfieldDecl> paramfieldDecls; // Optional

    public RecDecl (String name, List<ParamfieldDecl> paramfieldDecls) {
        this.name = name;
        this.paramfieldDecls = paramfieldDecls;
    }

    @Override
    public String printAst() {
        // (REC_DECL (NAME("x") [List<ParamfieldDecl>]))
        StringBuilder sb = new StringBuilder();
        sb.append("(REC_DECL (");
        sb.append("NAME(\"" +  name + "\") ");
        
        if (paramfieldDecls != null){
            sb.append("\n");
            for (ParamfieldDecl p : paramfieldDecls) {
                sb.append("\t\t" + p.printAst() + "\n");
            }
        } else {
            sb.append("NULL");
        }

        sb.append("\t))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) {
        if (st.lookupR(name) != null) {
            throw new TypeException("Illegal double declaration: Record " + name + " already exists.");
        }
        st.addR(name, this);

        SymbolTable newSt = st.copy(); // local scope

        if (paramfieldDecls != null){
            for (ParamfieldDecl p : paramfieldDecls) {
                p.typeCheck(newSt);
            }
        }

        return "void";
    }
}