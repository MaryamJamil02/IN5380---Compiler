package syntaxtree;

public abstract class VarExp extends Exp {
    public abstract String printAst();
    public abstract String typeCheck();
    // public abstract String getType();
}

// Subclasses: Var, DerefVar, RefVar