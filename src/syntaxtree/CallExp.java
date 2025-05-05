package syntaxtree;

import semantics.*;

public class CallExp extends Exp {
    CallStmt cs;

    public CallExp(CallStmt cs) {
        this.cs = cs;
    }

    @Override
    public String printAst() {
        StringBuilder sb = new StringBuilder();
        sb.append(cs.printAst());
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) {
        return cs.typeCheck(st);
    }
}
