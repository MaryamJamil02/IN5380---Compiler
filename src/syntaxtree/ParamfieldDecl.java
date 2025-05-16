package syntaxtree;

import bytecode.*;
import semantics.*;

public class ParamfieldDecl {
    String name;
    String type;

    public ParamfieldDecl(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String printAst() {
        // (PARAMFIELD_DECL (NAME("x") TYPE(INT)))
        StringBuilder sb = new StringBuilder();
        sb.append("(PARAMFIELD_DECL (");
        sb.append("NAME(\"" + this.name + "\") ");
        sb.append("TYPE(" + this.type + ")");
        sb.append("))");
        return sb.toString();
    }

    public String typeCheck(SymbolTable st) {
        return type;
    }

    public void generateCode(CodeProcedure codeProcedure) {
        int structRef = codeProcedure.structNumber(type);
        codeProcedure.addParameter(name, new RuntimeType(type, structRef).type);
    }

    public void generateCode(CodeStruct codeStruct) {
        codeStruct.addVariable(name, new RuntimeType(type, 0).type);
    }
}