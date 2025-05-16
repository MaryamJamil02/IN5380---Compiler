package semantics;

import java.util.HashMap;
import syntaxtree.*;

public class SymbolTable {
    HashMap<String, VarDecl> variables; // name: (type, value)
    HashMap<String, ProcDecl> procedures; // name: ProcDecl
    HashMap<String, RecDecl> records; // name: RecDecl

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

    public void addV(String name, VarDecl type) {
        // if (variables.get(name) != null) throw new TypeException("Variable " + name +
        // " is already defined.");
        variables.put(name, type);
    }

    public void addP(String name, ProcDecl p) {
        // if (procedures.get(name) != null) throw new TypeException("Procedure " + name
        // + " is already defined.");
        procedures.put(name, p);
    }

    public void addR(String name, RecDecl p) {
        // if (records.get(name) != null) throw new TypeException("Struct " + name + "
        // is already defined.");
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
