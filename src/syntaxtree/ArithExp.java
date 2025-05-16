package syntaxtree;

import bytecode.*;
import bytecode.instructions.*;

import semantics.*;

public class ArithExp extends Exp {
    Exp e1;
    String op;
    Exp e2;

    String type; // set during type check

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

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        // ARITH_OP -> "+" | "-" | "*" | "/" | "^"

        String e1Type = e1.typeCheck(st);
        String e2Type = e2.typeCheck(st);

        if (!isCompatible(e1Type, e2Type)) {
            throw new TypeException("Cannot perform '" + op + "' on types " + e1Type + " and " + e2Type);
        }

        if (op.equals("^") || e1Type.equals("float") || e2Type.equals("float")) {
            this.type = "float";
        } else {
            this.type = "int";
        }
        return getType();
    }

    private boolean isCompatible(String e1Type, String e2Type) {
        if (e1Type.equals("int") || e1Type.equals("float")) {
            if (e2Type.equals("int") || e2Type.equals("float")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void generateCode(CodeProcedure cp) {
        e1.generateCode(cp);
        e2.generateCode(cp);

        switch (op) {
            case "+":
                cp.addInstruction(new ADD());
                break;
            case "-":
                cp.addInstruction(new SUB());
                break;
            case "*":
                cp.addInstruction(new MUL());
                break;
            case "/":
                cp.addInstruction(new DIV());
                break;
            case "^":
                cp.addInstruction(new EXP());
                break;
            default:
                throw new RuntimeException("Unknown arith op: " + op);
        }
    }
}
