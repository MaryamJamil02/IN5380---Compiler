package syntaxtree;

public abstract class VarExp extends Exp {
    public abstract String printAst();
}

// Subclasses: Var, DerefVar, RefVar