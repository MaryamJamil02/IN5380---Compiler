package syntaxtree;

public abstract class Exp {
    public abstract String printAst();

    public abstract void typeCheck();

    public abstract String getType();
}
