package syntaxtree;

import semantics.*;
import bytecode.*;

public abstract class VarExp extends Exp {
    public abstract String printAst();

    public abstract String typeCheck(SymbolTable st) throws TypeException;

    public abstract void generateCode(CodeProcedure codeProcedure); // local variable

    public abstract void generateCodeStore(CodeProcedure codeProcedure); // storing local variable

    public abstract String getType();
}

// Subclasses: Var, DerefVar, RefVar