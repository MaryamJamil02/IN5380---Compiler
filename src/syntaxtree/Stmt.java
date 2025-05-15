package syntaxtree;

import bytecode.*;
import semantics.*;

public abstract class Stmt {
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st) throws TypeException;
    public abstract void generateCode(CodeFile codeFile); 
    // public abstract String getType();
}

// Subclasses:
// AssignStmt as;
// IfStmt is;
// WhileStmt ws;
// ReturnStmt rs;
// CallStmt cs;