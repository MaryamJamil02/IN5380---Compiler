package compiler;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import bytecode.CodeFile;

import syntaxtree.*;
import parser.*;
import semantics.*; // symboltable

// That's the version of Compiler.java for Oblig 2.
// It's extended compared to the version of
// oblig 1.  It's again mainly for INSPIRATION. It probably needs
// adaptation to a group's local situation and is not expected to always be usable
// without changes.

public class Compiler {
    private String inFilename = null;
    private String astFilename = null;
    private String binFilename = null;
    public String syntaxError;
    public String error;

    public Compiler(String inFilename, String astFilename, String binFilename){
        this.inFilename = inFilename;
        this.astFilename = astFilename;
        this.binFilename = binFilename;
    }
    public int compile() throws Exception {
        InputStream inputStream = null;
        inputStream = new FileInputStream(this.inFilename);
        Lexer lexer = new Lexer(inputStream);
        parser parser = new parser(lexer);
        Program program;
        try {
            program = (Program)parser.parse().value;
        } catch (Exception e) {
            // Do something here?
            throw e; // Or something.
        }

        SymbolTable st = new SymbolTable();
        addStandardLibrary(st);
        
        // Check semanics.
        try {
            program.typeCheck(st);
            System.out.println("> TYPE CHECKER SUCCEEDED!!!! YAYYY");
        } catch (TypeException e) {
            // Do something here?
            throw e;
        }

        if(false){ // If it is all ok:
            writeAST(program);
            // generateCode(program);
            return 0;
        } else if (false){ // If there is a SYNTAX ERROR (Should not get that for the tests):
            return 1;
        } else { // If there is a SEMANTIC ERROR (Should get that for the test with "_fail" in the name):
            return 2;
        }
    }
    private void writeAST(Program program) throws Exception {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.astFilename));
        bufferedWriter.write(program.printAst());
        bufferedWriter.close();
    }


    // public ProcDecl(String name, List<ParamfieldDecl> pdl, String type, List<Decl> dl, List<Stmt> sl) {
    public void addStandardLibrary(SymbolTable st) {
        ParamfieldDecl intParam =  new ParamfieldDecl("i", "int");
        ParamfieldDecl floatParam =  new ParamfieldDecl("f", "float");
        ParamfieldDecl stringParam =  new ParamfieldDecl("s", "string");


        st.addP("readint",    new ProcDecl("readint", null, "int", null,  new ArrayList<>()));
        st.addP("readfloat",  new ProcDecl("readfloat", null, "float", null,  new ArrayList<>()));
        st.addP("readchar",   new ProcDecl("readchar", null, "int", null, new ArrayList<>()));
        st.addP("readstring", new ProcDecl("readstring", null, "string", null, new ArrayList<>()));
        st.addP("readline",   new ProcDecl("readline", null, "string", null, new ArrayList<>()));

        st.addP("printint", new ProcDecl("printint", new ArrayList<>(List.of(intParam)), "int", null, new ArrayList<>()));
        st.addP("printfloat", new ProcDecl("printfloat", new ArrayList<>(List.of(floatParam)), "float", null, new ArrayList<>()));
        st.addP("printstr", new ProcDecl("printstr", new ArrayList<>(List.of(stringParam)), "string", null, new ArrayList<>()));
        st.addP("printline", new ProcDecl("printline", new ArrayList<>(List.of(stringParam)), "string", null, new ArrayList<>()));
    }



    private void generateCode(Program program) throws Exception {
        CodeFile codeFile = new CodeFile();
        program.generateCode(codeFile);

        byte[] bytecode = codeFile.getBytecode();
        DataOutputStream stream = new DataOutputStream(new FileOutputStream (this.binFilename));
        
        stream.write(bytecode);
        stream.close();
    }
    public static void main(String[] args) {
        Compiler compiler = new Compiler(args[0], args[1], args[2]);
        int result;
        try {
            result = compiler.compile();
            if(result == 1){
                System.out.println(compiler.syntaxError);
            } else if(result == 2){
                System.out.println(compiler.error);
            }
            System.exit(result);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            // If unknown error.
            System.exit(3);
        }
    }
    public static String indent(int indent){
        String result = "";
        for(int i=0;i<indent; i++){
            result+=" ";
        }
        return result;
    }
}
