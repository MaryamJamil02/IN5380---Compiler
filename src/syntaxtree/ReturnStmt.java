package syntaxtree;

import semantics.*;

public class ReturnStmt extends Stmt{
    Exp e;

    public ReturnStmt () {

    }
    public ReturnStmt(Exp e) {
        this.e = e;
    }

    @Override
    public String printAst() {
        // (RETURN_STMT ([EXP]))
        StringBuilder sb = new StringBuilder();
        sb.append("(RETURN_STMT ");
        if (e != null) {
            sb.append(e.printAst());
        } else {
            sb.append("()");
        }
        sb.append(")");
        return sb.toString();
    }


    @Override
    public String typeCheck(SymbolTable st) {
        if (e != null) {
            return e.typeCheck(st);
        }
        return "void";
    }
}
