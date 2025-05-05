package syntaxtree;

import semantics.*;

public class ArithExp extends Exp {
    Exp e1;
    String op; 
    Exp e2;

    public ArithExp(Exp e1, String op, Exp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public String printAst() {
        // (ARITH_EXP (EXP + EXP))
        StringBuilder sb = new StringBuilder();
        sb.append("(ARITH_EXP (");
        sb.append(e1.printAst() + " " + op + " " + e2.printAst() + "))");
        return sb.toString();
    }


    public String typeCheck(SymbolTable st) {
        // ARITH_OP -> "+" | "-" | "*" | "/" | "^"

        String e1Type = e1.typeCheck(st);;
        String e2Type = e2.typeCheck(st);

        if (isCompatible(e1Type, e2Type)) {
            throw new Exception("Cannot perform '" + op + "'' on types " + e1Type + " and " + e2Type);
        }

        if (op.equals("^") || e1Type.equals("float") || e2Type.equals("float")) return "float";
        return "int";
    }

    private boolean isCompatible(String e1Type, String e2Type) {
        if (e1Type.equals("int") || e1Type.equals("float")) {
            if (e2Type.equals("int") || e2Type.equals("float")) {
                return true;
            }
        }
        return false;
    }


    public String getType(){
        return "";
    }
}
