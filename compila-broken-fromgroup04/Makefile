# # Since there are problems with the ant built process, let's try a Makefile approach.



lex-simple: 
	java -jar lib/JFlex.jar  -d src-gen2/parser  src/grammars/compila.lex

cup-simple: lex-simple
	java -jar lib/java-cup-11a.jar -destdir src-gen2/parser/ src/grammars/compila.cup

build-simple: cup-simple
	javac -d build/classes -cp ./build/classes/:lib/java-cup-11a.jar src-gen2/parser/parser.java


run-simple: build-simple
	java -cp build/classes/:lib/java-cup-11a.jar parser.parser "src/tests/dummyprograms/simpledummy.cmp"



# # [echo] Targets                             
# # [echo] clean                		- Cleans up the project
# # [echo] build                		- Build compiler
# # [echo] run                  		- Run compiler


# JAVA=java
# JAVAC=javac
# JFLEXCLASS=jflex
# CUP=$(JAVA) java_cup.Main <

# all: test

# test: output.txt
# 	@(diff output.txt output.good && echo "Test OK!") || echo "Test failed!"

# output.txt: Main.class test.txt
# 	$(JAVA) Main test.txt > output.txt

# Main.class: Main.java Lexer.java parser.java

# %.class: %.java
# 	$(JAVAC) $^

# Lexer.java: lcalc.flex
# 	$(JFLEX) lcalc.flex

# parser.java: ycalc.cup
# 	$(CUP) ycalc.cup

# clean:
# 	rm -f parser.java Lexer.java sym.java output.txt *.class *~





# # says that it belongs to the Iota package).

# #also, I assume that your lexer input is named Iota\Lexer
# # and that you want the output to be named Iota\Lexer.java
# # you should make sure that the first thing in the "user code" section
# # of Iota\Lexer (see the JLex user manual) contains the line:
# #     package Iota;
# # otherwise the resulting Iota\Lexer.java file will refuse to compile

# #also, I assume that your CUP input is named Iota\CUPParser.cup
# # and that you want the output to be named Iota\CUPParser.java and
# # Iota\CUPSymbols.java for the parser file and for the symbols
# # file (see CUP documentation about the "-symbols" option).  There
# # should be a package declaration line like the following in the
# # Iota\CUPParser.cup file:
# #     package Iota;


# #directory where to find the JDK
# #JDK_PATH=g:\jdk1.2.2\bin

# #path to the java_cup jar file
# CUP_CLASSPATH = lib/java_cup-11a.jar
# #path to the jlex jar file
# JLEX_CLASSPATH = lib/JFLex.jar
# # the following line is the path that contains an 'Iota' directory
# #   that contains your classes from the Iota package (this is the
# #   package you're writing)
# IOTA_CLASSPATH = .

# # this is the additional paths to add
# CLASSPATH = ${CUP_CLASSPATH};${JLEX_CLASSPATH};${IOTA_CLASSPATH}
# # how to invoke java
# JAVA = ${JDK_PATH}\java -cp ${CLASSPATH}
# # how to invoke the java compiler
# JAVAC = javac -classpath ${CLASSPATH}
# # how to invoke jlex
# JLEX = ${JAVA} JLex.Main
# # how to invoke cup
# CUP = ${JAVA} java_cup.Main 

# # phony target to rebuild the compiler, conditionally reconstructing
# #  the auto-generated lexer and parser files, if necessery

# all:			Iota/Lexer.java Iota/CUPParser.java
# 	${JAVAC} ${JFLAGS} Iota/Compiler.java

# Iota/Lexer.java:	Iota/Lexer
# 	${JLEX} Iota/Lexer

# Iota/CUPParser.java:	Iota/CUPParser.cup
# 	${CUP} -package Iota -parser Iota/CUPParser \
# 		-symbols Iota/CUPSymbols -expect 6 -nopositions < Iota/CUPParser.cup


# clean:
# 	rm -rf Iota/*.class

# immaculate:		clean
# 	rm -f Iota/Lexer.lex.java Iota/CUPParser.java Iota/CUPConstant.java




# JFLEXFILE = ./src/grammars/compila.lex  # destdir = src-gen/
# CUPFILE   = ./src/grammars/compila.cup  # destdir="src-gen" interface="true" 

# generate:
# 	echo "generate"



