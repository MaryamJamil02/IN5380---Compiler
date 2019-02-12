CodeFile codeFile = new CodeFile();
String filename = "example.bin";

// Building the bytecode with instructions like
codeFile.addProcedure("Main");
CodeProcedure main = new CodeProcedure("Main",
				       VoidType.TYPE,
				       codeFile);
main.addInstruction (new RETURN());
codeFile.updateProcedure(main);

// ... and more ....


byte [] bytecode = codeFile.getBytecode();
DataOutputStream stream = new DataOutputStream(
					       new FileOuputStream(filename));
stream.write(bytecode);
stream.close();
