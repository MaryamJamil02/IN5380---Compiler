package syntaxtree;

public class NotExp extends Exp {
    Exp e;

    public NotExp(Exp e) {
        this.e = e;
    }

    @Override
    public String printAst() {
        // (NOT_EXP (!EXP))
        StringBuilder sb = new StringBuilder();
        sb.append("(NOT_EXP (" + "!" + e.printAst() + "))");
        return sb.toString();
    }
}
