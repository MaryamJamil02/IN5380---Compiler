package syntaxtree;

import semantics.*;

public abstract class Exp {
    public abstract String printAst();

    public abstract String typeCheck(SymbolTable st);

    // public abstract String getType();
}
