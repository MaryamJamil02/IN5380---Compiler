package semantics;

import bytecode.type.*;
import bytecode.*;

public class RuntimeType {
    public CodeType type;

    public RuntimeType(String type, int structRef) {
        if (type == null) {
            this.type = VoidType.TYPE;
            return;
        }

        switch (type) {
            case "void":
                this.type = VoidType.TYPE;
                break;

            case "string":
                this.type = StringType.TYPE;
                break;

            case "int":
                this.type = IntType.TYPE;
                break;

            case "float":
                this.type = FloatType.TYPE;
                break;

            case "bool":
                this.type = BoolType.TYPE;
                break;

            default:
                // int structRef = codeProcedure.structNumber(type);
                this.type = new RefType(structRef);

                break;
        }
    }
}
