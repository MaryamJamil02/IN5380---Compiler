package syntaxtree;

import semantics.*;
import bytecode.*;

public abstract class VarExp extends Exp {
    public abstract String printAst();
    public abstract String typeCheck(SymbolTable st) throws TypeException;
    public abstract void generatedCode(CodeFile codefile);

}

// Subclasses: Var, DerefVar, RefVar