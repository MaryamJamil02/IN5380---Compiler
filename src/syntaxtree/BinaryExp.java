// package syntaxtree;

// import semantics.*;

// public class BinaryExp extends Exp {
// Exp e1;
// String op;
// Exp e2;

// public BinaryExp(Exp e1, String op, Exp e2) {
// this.e1 = e1;
// this.op = op;
// this.e2 = e2;
// }

// @Override
// public String printAst() {
// // (BINARY_EXP (EXP + EXP))
// StringBuilder sb = new StringBuilder();
// sb.append("(BINARY_EXP (");
// sb.append(e1.printAst() + " " + op + " " + e2.printAst() + "))");
// return sb.toString();
// }

// public String typeCheck(SymbolTable st) {

// String e1Type = e1.typeCheck(st);;
// String e2Type = e2.typeCheck(st);

// if (isCompatible(e1Type, e2Type)) {
// throw new Exception("Cannot perform '" + op + "'' on types " + e1Type + " and
// " + e2Type);
// }

// return getType();
// }

// private boolean isCompatible(String e1Type, String e2Type) {
// if (e1Type.equals("int") && e2Type.equals("float") && )
// // LOG_OP -> "&&" | "||"
// // REL_OP -> "<" | "<=" | ">" | ">=" | "=" | "<>"
// // ARITH_OP -> "+" | "-" | "*" | "/" | "^"
// }

// public String getType(){
// return "";
// }
// }
