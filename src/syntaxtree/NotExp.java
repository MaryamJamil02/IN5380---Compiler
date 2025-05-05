package syntaxtree;

import semantics.*;

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

    @Override
    public String typeCheck(SymbolTable st) {
        String expType = e.typeCheck(st);
        if (!expType.equals("bool")) {
            throw new Exception("Not-operator requires bool, found: " + expType);
        }
        return "bool";
    }
}
