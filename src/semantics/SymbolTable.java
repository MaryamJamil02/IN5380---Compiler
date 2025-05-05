package semantics;

import java.util.HashMap;

public class SymbolTable {
    HashMap<String, Object> st;

    public SymbolTable() {
        this.st = new HashMap<>();
    }
    public SymbolTable(HashMap<String, Object> st) {
        this.st = st;
    }

    public Object lookup(String name) {
        return st.get(name);
    }

    public void add(String name, Object type) {
        st.put(name, type);
    }

    public SymbolTable copy() {
        HashMap<String, Object> cloned = new HashMap<>();
        cloned.putAll(st);
        SymbolTable newSt = new SymbolTable(cloned);
        return newSt;
    }
}
