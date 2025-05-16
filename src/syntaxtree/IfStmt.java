package syntaxtree;

import java.util.List;

import bytecode.*;
import bytecode.instructions.*;
import semantics.*;

public class IfStmt extends Stmt {
    Exp cond;
    List<Stmt> if_stmts; // May be null
    List<Stmt> else_stmts; // Optional

    public IfStmt(Exp cond, List<Stmt> if_stmts) {
        this.cond = cond;
        this.if_stmts = if_stmts;
    }

    public IfStmt(Exp cond, List<Stmt> if_stmts, List<Stmt> else_stmts) {
        this.cond = cond;
        this.if_stmts = if_stmts;
        this.else_stmts = else_stmts;
    }

    @Override
    public String printAst() {
        // (IF_STMT (COND(EXP) THEN(List<Stmt>) [ELSE (List<Stmt>)]))
        StringBuilder sb = new StringBuilder();
        sb.append("(IF_STMT (");

        sb.append("COND" + cond.printAst());

        sb.append("\n\t\t\tTHEN(");
        if (if_stmts != null) {
            sb.append("\n");
            for (Stmt s : if_stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
        } else {
            sb.append(" ");
        }
        sb.append("\t\t\t)");

        if (else_stmts != null) {
            sb.append("\n\t\t\tELSE(\n");
            for (Stmt s : else_stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
            sb.append("\t\t\t)");
        }

        sb.append("\t\t))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        String condType = cond.typeCheck(st);

        if (!condType.equals("bool")) {
            throw new TypeException("Condition in an if statement must be of type bool, found: " + condType);
        }

        if (if_stmts != null) {
            SymbolTable newSt = st.copy();
            for (Stmt s : if_stmts) {
                s.typeCheck(newSt);
            }
        }
        if (else_stmts != null) {
            SymbolTable newSt = st.copy();
            for (Stmt s : else_stmts) {
                s.typeCheck(newSt);
            }
        }

        return "void";
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        cond.generateCode(codeProcedure);

        int jmpToElseOrEnd = codeProcedure.addInstruction(new JMPFALSE(0)); // jump if cond is false

        // THEN block
        if (if_stmts != null) {
            for (Stmt stmt : if_stmts) {
                stmt.generateCode(codeProcedure);
            }
        }

        // If it has ELSE block
        if (else_stmts != null) {
            // Must jump past ELSE after THEN is done
            int jmpAfterElse = codeProcedure.addInstruction(new JMP(0)); // placeholder

            // ELSE block starts here
            int elseStart = codeProcedure.addInstruction(new NOP());
            for (Stmt stmt : else_stmts) {
                stmt.generateCode(codeProcedure);
            }
            int end = codeProcedure.addInstruction(new NOP()); // end label

            codeProcedure.replaceInstruction(jmpToElseOrEnd, new JMPFALSE(elseStart));
            codeProcedure.replaceInstruction(jmpAfterElse, new JMP(end));
        }

        // No ELSE block: just jump to end
        else {
            int end = codeProcedure.addInstruction(new NOP());
            codeProcedure.replaceInstruction(jmpToElseOrEnd, new JMPFALSE(end));
        }
    }
}
