package syntaxtree;

public class RefVar extends VarExp{
  VarExp var; // Either Var or RefVar

  public RefVar(Var var) {
    this.var = var;
  }

  public RefVar(RefVar var) {
    this.var = var;
  }

  @Override
  public String printAst() {
      // (REF_VAR (VAR/REF_VAR))
      StringBuilder sb = new StringBuilder();
      sb.append("(REF_VAR " + var.printAst() + ")");
      return sb.toString();
  }
}
