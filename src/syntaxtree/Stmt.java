package syntaxtree;

public abstract class Stmt {
    public abstract String printAst();
    public abstract void typeCheck();
    public abstract String getType();
}

// Subclasses:
// AssignStmt as;
// IfStmt is;
// WhileStmt ws;
// ReturnStmt rs;
// CallStmt cs;