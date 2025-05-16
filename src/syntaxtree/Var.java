package syntaxtree;

import semantics.*;
import java.util.List;

import bytecode.*;
import bytecode.instructions.*;

public class Var extends VarExp {
    Exp exp; // Optional, then name must be a record
    String name;

    String type; // Set during type check

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
    public String typeCheck(SymbolTable st) throws TypeException {
        if (exp == null) {
            // Regular variable: Look up name in the current variable scope
            VarDecl var = st.lookupV(name);
            if (var == null) {
                throw new TypeException("Unbound variable " + name);
            }
            this.type = var.type;
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
                    if (f.name.equals(name)) {
                        this.type = f.type;
                        return f.type;
                    }
                }
            }

            // Did not find field
            throw new TypeException("Field " + name + " not found in record type " + expType);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        if (exp == null) {
            int varIndex = codeProcedure.variableNumber(name);
            codeProcedure.addInstruction(new LOADLOCAL(varIndex));
        } else {
            String structName = exp.getType();

            // Genereate code to push record object on stack
            exp.generateCode(codeProcedure);

            int structIndex = codeProcedure.structNumber(structName);
            int fieldIndex = codeProcedure.fieldNumber(structName, name);

            codeProcedure.addInstruction(new GETFIELD(fieldIndex, structIndex));
        }
    }

    @Override
    public void generateCodeStore(CodeProcedure codeProcedure) {
        if (exp == null) {
            int varIndex = codeProcedure.variableNumber(name);
            codeProcedure.addInstruction(new STORELOCAL(varIndex));
        } else {
            String structName = exp.getType();

            int tmpIndex = codeProcedure.variableNumber("_tmp_");
            codeProcedure.addInstruction(new STORELOCAL(tmpIndex));

            exp.generateCode(codeProcedure);
            codeProcedure.addInstruction(new LOADLOCAL(tmpIndex));

            int structIndex = codeProcedure.structNumber(structName);
            int fieldIndex = codeProcedure.fieldNumber(structName, name);

            codeProcedure.addInstruction(new PUTFIELD(fieldIndex, structIndex));
        }
    }
}
