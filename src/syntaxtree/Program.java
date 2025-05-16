package syntaxtree;

import java.util.List;

import bytecode.*;
import bytecode.type.*;
import semantics.*;

public class Program {

    List<Decl> decls; // Optional
    String name;

    public Program(String name, List<Decl> decls) {
        this.decls = decls;
        this.name = name;
    }

    public String printAst() { // "pretty" printing
        StringBuilder sb = new StringBuilder();
        sb.append("(PROGRAM ");
        sb.append("(NAME " + this.name + ")");

        if (decls != null) {
            sb.append("\n");
            for (Decl decl : decls) {
                sb.append("\t" + decl.printAst() + "\n");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public String typeCheck(SymbolTable st) throws TypeException {
        // Each program must have a procedure named main.
        boolean hasMain = false;

        if (decls != null) {
            for (Decl decl : decls) {
                decl.typeCheck(st);
                if (decl instanceof ProcDecl) {
                    if (decl.name.equals("main"))
                        hasMain = true;
                }
            }
        }

        if (!hasMain) {
            throw new TypeException("Found no procedure named main.");
        }

        return "void";
    }

    public void generateCode(CodeFile codeFile) {

        addStandardLibrary(codeFile);

        if (decls != null) {
            for (Decl decl : decls) {
                decl.generateCode(codeFile);
            }
        }

        codeFile.setMain("main");
    }

    private void addStandardLibrary(CodeFile codeFile) {

        // Input procedures
        CodeProcedure readInt = new CodeProcedure("readint", IntType.TYPE, codeFile);
        codeFile.addProcedure("readint");
        codeFile.updateProcedure(readInt);

        CodeProcedure readfloat = new CodeProcedure("readfloat", FloatType.TYPE, codeFile);
        codeFile.addProcedure("readfloat");
        codeFile.updateProcedure(readfloat);

        CodeProcedure readchar = new CodeProcedure("readchar", IntType.TYPE, codeFile);
        codeFile.addProcedure("readchar");
        codeFile.updateProcedure(readchar);

        CodeProcedure readstring = new CodeProcedure("readstring", StringType.TYPE, codeFile);
        codeFile.addProcedure("readstring");
        codeFile.updateProcedure(readstring);

        CodeProcedure readline = new CodeProcedure("readline", StringType.TYPE, codeFile);
        codeFile.addProcedure("readline");
        codeFile.updateProcedure(readline);

        // Output procedures
        CodeProcedure printint = new CodeProcedure("printint", VoidType.TYPE, codeFile);
        printint.addParameter("i", IntType.TYPE);
        codeFile.addProcedure("printint");
        codeFile.updateProcedure(printint);

        CodeProcedure printfloat = new CodeProcedure("printfloat", VoidType.TYPE, codeFile);
        printint.addParameter("f", FloatType.TYPE);
        codeFile.addProcedure("printfloat");
        codeFile.updateProcedure(printfloat);

        CodeProcedure printstr = new CodeProcedure("printstr", VoidType.TYPE, codeFile);
        printint.addParameter("s", StringType.TYPE);
        codeFile.addProcedure("printstr");
        codeFile.updateProcedure(printstr);

        CodeProcedure printline = new CodeProcedure("printline", VoidType.TYPE, codeFile);
        printint.addParameter("s", StringType.TYPE);
        codeFile.addProcedure("printline");
        codeFile.updateProcedure(printline);
    }
}
