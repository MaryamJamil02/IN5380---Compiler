package syntaxtree;

import bytecode.*;
import semantics.*;

public abstract class Decl {
    String name;

    public abstract String printAst();

    public abstract String typeCheck(SymbolTable st) throws TypeException;

    public abstract void generateCode(CodeFile codefile); // Global

    public abstract void generateCode(CodeProcedure codeProcedure); // Local (inside proc)
}

// Subclasses: VarDecl, RecDecl, ProcDecl