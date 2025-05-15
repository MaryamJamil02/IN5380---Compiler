package syntaxtree;

import semantics.*;

public class Literal extends Exp {
    enum Type { INT, FLOAT, BOOL, STRING, NULL }
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
        this.type = Type.BOOL;
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
            case BOOL:
                sb.append("BOOL " + b); break;
            case STRING:
                sb.append("STRING \"" + s + "\""); break;
            case NULL:
                sb.append("NULL"); break;
        }

        sb.append("))");
        return sb.toString();
    }

    @Override
    public String typeCheck(SymbolTable st) throws TypeException {
        // literals are always well-typed

        switch (type) {
            case FLOAT: 
                return "float";
            case INT:
                return "int";
            case BOOL:
                return "bool";
            case STRING:
                return "string";
            case NULL:
                return "null";
            default:
                throw new TypeException("This should never happen...");
        }
    } 
}
