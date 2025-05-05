package syntaxtree;

import semantics.*;

public abstract class Stmt {
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st);
    // public abstract String getType();
}

// Subclasses:
// AssignStmt as;
// IfStmt is;
// WhileStmt ws;
// ReturnStmt rs;
// CallStmt cs;