package syntaxtree;

import bytecode.*;
import semantics.*;

public abstract class Decl {
    String name;
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st) throws TypeException;
    public abstract void generatedCode(CodeFile codefile);
}

// Subclasses: VarDecl, RecDecl, ProcDecl