package syntaxtree;

import semantics.SymbolTable;

public abstract class Decl {
    String name;
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st);
    // public abstract String getType();
}

// Subclasses: VarDecl, RecDecl, ProcDecl