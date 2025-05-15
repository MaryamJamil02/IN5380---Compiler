package syntaxtree;

import java.util.ArrayList;
import java.util.List;

import semantics.*;

public class ProcDecl extends Decl{
    // String name;
    List<ParamfieldDecl> pdl; // Optional
    String type;              // Optional
    List<Decl> dl;            // Optional
    List<Stmt> sl;

    public ProcDecl(String name, List<ParamfieldDecl> pdl, String type, List<Decl> dl, List<Stmt> sl) {
        this.name = name;
        this.pdl = pdl;
        this.type = type;
        this.dl = dl;
        this.sl = sl;
    }

    @Override
    public String printAst() {
        /* (PROC_DECL (NAME("x") 
                       [List<ParamfieldDecl>] 
                       [TYPE(INT)] 
                       [List<Decl>] 
                       List<Stmt>))
        */
        StringBuilder sb = new StringBuilder();
        sb.append("(PROC_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");

        // Optional list of paramfield decls
        if (pdl != null) {
            for (ParamfieldDecl pf : pdl) {
                sb.append("\n\t\t" + pf.printAst());
            }
        } else {
            sb.append("NULL");
        }

        // Optional type
        if (type != null) {
            sb.append(" TYPE(" + this.type + ") ");
        } else{
            sb.append(" NULL ");
        }

        // Optional decl list
        if (dl != null) {
            sb.append("\n");
            for (Decl d : dl) {
                sb.append("\t\t" + d.printAst());
                sb.append("\n");
            }
        } else{
            sb.append("NULL");
        }

        sb.append("\n");
        for (Stmt s : sl) {
            sb.append("\t\t" + s.printAst());
            sb.append("\n");
        }

        sb.append("\t))");
        return sb.toString();
    }


    public String typeCheck(SymbolTable st) throws TypeException{
        if (st.lookupP(name) != null) {
            throw new TypeException("Illegal double declaration: Procedure " + name + " already exists.");
        }
        st.addP(name, this);

        // Create local scope for procedure definition
        SymbolTable newSt = st.copy();

        // Formal parameters count as declarations local to the procedure body.

        // Optional list of paramfield decls
        List<String> pNames = new ArrayList<>();
        if (pdl != null) {
            for (ParamfieldDecl pf : pdl) {
                pf.typeCheck(newSt);

                // All formal parameters of one procedure must have distinct names
                if (pNames.contains(pf.name)) {
                    throw new TypeException("Found duplicate parameter names " + pf.name + " in procedure " + name);
                }
                pNames.add(pf.name);
                
                newSt.addV(pf.name, new VarDecl(pf.name, pf.type, null));
            }
        }

        // Optional decl list
        List<String> localNames = new ArrayList<>(pNames);
        if (dl != null) {
            for (Decl d : dl) {
                String dName = d.name;

                if (localNames.contains(dName)) {
                    throw new TypeException("Name collision: Local declaration '" + dName +
                                            "' conflicts with a parameter in procedure '" + name + "'");
                }
                localNames.add(dName);
                d.typeCheck(newSt);
            }
        }

        
        for (int i = 0; i < sl.size(); i++ ) {
            Stmt s = sl.get(i);
            String stmtT = s.typeCheck(newSt); // statement type

            boolean isLast = i == sl.size()-1;
            boolean isReturn = s instanceof ReturnStmt;

            // return must be the last statement in the procedure’s body.
            if (!isLast && isReturn) {
                throw new TypeException("Only last statement in procedure can be return.");
            } 
            
            if (isLast) {
                // If a procedure has declared a return type, its body is required to have a return statement
                if (type != null) {
                    if(!isReturn) {
                        throw new TypeException("Last statement must be a return.");
                    }
                
                    // Actual return type must correspond to the expected return type
                    if (!stmtT.equals(type)) {
                        throw new TypeException("Return type " + stmtT + " does not match procedure type " + type);
                    }
                } 
                
                // Can have a return statement without type if the return value is void
                else {
                    if (isReturn && !stmtT.equals("void")) {
                        throw new TypeException("Procedure must return void if no return type is declared, found: " + stmtT);
                    }
                }
            }
        }

        if (type != null) return type;
        return "void";
    }
}
