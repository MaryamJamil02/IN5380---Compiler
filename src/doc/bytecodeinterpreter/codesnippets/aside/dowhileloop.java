int start = test.addInstruction(new NOP());  // jump target = loop start
                                             // statements for loop body....
test.addInstruction(new LOADLOCAL(test.variableNumber("i")));
test.addInstruction(new PUSHINT(new Integer(2)));
test.addInstruction(new LT());               // boolean value now on the stack
test.addInstruction(new JMPTRUE(start));     // jump back if true		    
