package semantics;

import java.util.HashMap;
import syntaxtree.*;

public class SymbolTable {
    HashMap<String, VarDecl> variables;     // name: (type, value)
    HashMap<String, ProcDecl> procedures;  // name: ProcDecl
    HashMap<String, RecDecl> records;      // name: RecDecl

    // private class Pair<String, Object> {
    //     public final String type;
    //     public final Object value;

    //     public Pair(String type, Object value) {
    //         this.type = type;
    //         this.value = value;
    //     }
    // }

    public SymbolTable() {
        this.variables = new HashMap<>();
        this.procedures = new HashMap<>();
        this.records = new HashMap<>();
    }
    public SymbolTable(HashMap<String, VarDecl> variables, 
                       HashMap<String, ProcDecl> procedures,
                       HashMap<String, RecDecl> records) {
        this.variables = variables;
        this.procedures = procedures;
        this.records = records;
    }

    public VarDecl lookupV(String name) {
        return variables.get(name);
    }

    public ProcDecl lookupP(String name) {
        return procedures.get(name);
    }

    public RecDecl lookupR(String name) {
        return records.get(name);
    }

    public void addV(String name, Object type) {
        variables.put(name, type);
    }

     public void addP(String name, ProcDecl p) {
        procedures.put(name, p);
    }

     public void addR(String name, RecDecl p) {
        records.put(name, p);
    }

    public SymbolTable copy() {
        HashMap<String, VarDecl> clonedV = new HashMap<>();
        clonedV.putAll(variables);

        HashMap<String, ProcDecl> clonedP = new HashMap<>();
        clonedP.putAll(procedures);

        HashMap<String, RecDecl> clonedR = new HashMap<>();
        clonedR.putAll(records);

        SymbolTable newSt = new SymbolTable(clonedV, clonedP, clonedR);
        return newSt;
    }
}

