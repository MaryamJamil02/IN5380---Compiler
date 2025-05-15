package syntaxtree;

import semantics.*;
import bytecode.*;

public abstract class Exp {
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st) throws TypeException;
    public abstract void generatedCode(CodeFile codefile);
}
