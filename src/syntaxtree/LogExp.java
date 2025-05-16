package syntaxtree;

import bytecode.*;
import bytecode.instructions.*;

import semantics.*;

public class LogExp extends Exp {
    Exp e1;
    String op;
    Exp e2;

    public LogExp(Exp e1, String op, Exp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    @Override
    public String printAst() {
        // (LOG_EXP (EXP + EXP))
        StringBuilder sb = new StringBuilder();
        sb.append("(LOG_EXP (");
        sb.append(e1.printAst() + " " + op + " " + e2.printAst() + "))");
        return sb.toString();
    }

    public String typeCheck(SymbolTable st) throws TypeException {
        // LOG_OP -> "&&" | "||"

        String e1Type = e1.typeCheck(st);
        ;
        String e2Type = e2.typeCheck(st);

        if (!e1Type.equals("bool") || !e2Type.equals("bool")) {
            throw new TypeException("Cannot perform '" + op + "' on types " + e1Type + " and " + e2Type);
        }

        return getType();
    }

    @Override
    public String getType() {
        return "bool";
    }

    @Override
    public void generateCode(CodeProcedure cp) {
        e1.generateCode(cp);
        e2.generateCode(cp);

        switch (op) {
            case "&&":
                cp.addInstruction(new AND());
                break;
            case "||":
                cp.addInstruction(new OR());
                break;
            default:
                throw new RuntimeException("Unknown logical op: " + op);
        }
    }
}
