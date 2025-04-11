package syntaxtree;

public class Literal extends Exp {
    enum Type { INT, FLOAT, BOOLEAN, STRING, NULL }
    Type type;

    float f;
    int i;
    boolean b;
    String s;

    public Literal(float f) {
        this.f = f;
        this.type = Type.FLOAT;
    }

    public Literal(int i) {
        this.i = i;
        this.type = Type.INT;
    }

    public Literal(boolean b) {
        this.b = b;
        this.type = Type.BOOLEAN;
    }

    public Literal(String s) {
        this.s = s;
        this.type = Type.STRING;
    }

    public Literal() {
        this.type = Type.NULL;
    }

    @Override
    public String printAst() {
        // (LITERAL (INT 1))
        StringBuilder sb = new StringBuilder();
        sb.append("(LITERAL (");

        switch (type) {
            case FLOAT: 
                sb.append("FLOAT " + f); break;
            case INT:
                sb.append("INT " + i); break;
            case BOOLEAN:
                sb.append("BOOLEAN " + b); break;
            case STRING:
                sb.append("STRING \"" + s + "\""); break;
            case NULL:
                sb.append("NULL"); break;
        }

        sb.append("))");
        return sb.toString();
    }

    @Override
    public void typeCheck() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeCheck'");
    }

    @Override
    public String getType() {
        switch (type) {
            case FLOAT: 
                return "float";
            case INT:
                return "int";
            case BOOLEAN:
                return "bool";
            case STRING:
                return "string";
            case NULL:
                return "null";
        }

        return null;
    }    
}
