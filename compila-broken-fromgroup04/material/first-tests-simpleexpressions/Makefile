

tree:
	javac -cp ./src-examples -d build/classes/ ./src-examples/exampletree/*.java





     # [echo] clean                		- Cleans up the project
     # [echo] compile-example-eval 		- Compile Example Eval
     # [echo] run-example-eval     		- Run Example Eval
     # [echo] compile-example-parse  		- Compile Example Parse
     # [echo] run-example-parse      		- Run Example Parse


lex-eval: tree
	java -jar ../../lib/JFlex.jar  -d src-gen/expression_eval  grammars/expression-eval.lex

cup-eval: lex-eval
	java -jar ../../lib/java-cup-11a.jar -parser Parser -destdir src-gen/expression_eval grammars/expression-eval.cup

build-eval: cup-eval
	javac -d build/classes -cp ./build/classes/:../../lib/java-cup-11a.jar src-gen/expression_eval/Parser.java


run-eval: build-eval
	java -cp build/classes/:../../lib/java-cup-11a.jar expression_eval.Parser "1+2;"


lex-parse:
	java -jar ../../../lib/JFlex.jar  expression-par.lex 

cup-parse:
	java -jar ../../../lib/java-cup-11a.jar expression-par.cup
