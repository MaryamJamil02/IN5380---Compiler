package syntaxtree;

import java.util.List;

import bytecode.instructions.*;
import bytecode.*;
import semantics.*;

public class WhileStmt extends Stmt {
    Exp cond;
    List<Stmt> stmts; // May be empty

    public WhileStmt(Exp cond, List<Stmt> stmts) {
        this.cond = cond;
        this.stmts = stmts;
    }

    @Override
    public String printAst() {
        // (WHILE_STMT (WHILE(EXP) DO(List<Stmt>)))
        StringBuilder sb = new StringBuilder();
        sb.append("(WHILE_STMT (");

        sb.append("WHILE " + cond.printAst());

        sb.append("\n\t\t\tDO (");
        if (!stmts.isEmpty()) {
            sb.append("\n");
            for (Stmt s : stmts) {
                sb.append("\t\t\t\t" + s.printAst() + "\n");
            }
        } else {
            sb.append(" ");
        }
        sb.append("\t\t\t)))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        String condType = cond.typeCheck(st);
        if (condType != "bool") {
            throw new TypeException("Condition in an While statement must be of type bool, found: " + condType);
        }

        if (!stmts.isEmpty()) {
            SymbolTable newSt = st.copy(); // local scope
            for (Stmt s : stmts) {
                s.typeCheck(newSt);
            }
        }

        return "void";
    }

    @Override
    public void generateCode(CodeProcedure codeProcedure) {
        int start = codeProcedure.addInstruction(new NOP());

        cond.generateCode(codeProcedure);

        int exit = codeProcedure.addInstruction(new JMPFALSE(0)); // placeholder

        // Loop body
        for (Stmt s : stmts) {
            s.generateCode(codeProcedure);
        }

        // Jump back to start
        codeProcedure.addInstruction(new JMP(start));

        // Fix exit label
        int offset = codeProcedure.addInstruction(new NOP());

        codeProcedure.replaceInstruction(exit, new JMPFALSE(offset));
    }
}
