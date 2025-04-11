package syntaxtree;

public abstract class Decl {
    public abstract String printAst();
    public abstract void typeCheck();
    public abstract String getType();
}

// Subclasses: VarDecl, RecDecl, ProcDecl