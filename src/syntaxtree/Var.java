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

  @Override
  public void typeCheck() {
      if (e != null) {
      String expType = e.getType();

      if (expType != "record") {
        throw new TypeException("")
        
      }
        
    }

  }

  @Override
  public String getType() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getType'");
  }
}
