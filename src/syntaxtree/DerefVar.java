package syntaxtree;

public class DerefVar extends VarExp {
  // Either Var or DerefVar
  VarExp var;

  public DerefVar(Var var) {
    this.var = var;
  }

  public DerefVar(DerefVar var) {
    this.var = var;
  }

  @Override
  public String printAst() {
      // (DEREF_VAR (VAR/DEREF_VAR))
      StringBuilder sb = new StringBuilder();
      sb.append("(DEREF_VAR " + var.printAst() + ")");
      return sb.toString();
  }
}
