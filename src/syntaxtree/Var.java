package syntaxtree;

public class Var extends VarExp {
  Exp e; // Optional
  String name;
  
  public Var(String name) {
    this.name = name;
  }

  public Var(Exp e, String name) {
    this.e = e;
    this.name = name;
  }

  @Override
  public String printAst() {
      // (VAR ([EXP] NAME("x")))
      StringBuilder sb = new StringBuilder();
      sb.append("(VAR (");
      if (e != null) {
        sb.append(e.printAst() + " ");
      }
      sb.append("NAME(\"" + name + "\")))");
      return sb.toString();
  }
}
