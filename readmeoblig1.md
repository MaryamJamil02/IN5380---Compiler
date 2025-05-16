# Oblig 1 - How to install and run project

## Authors

Mailen Børmarken (mailenb@uio.no)
Maryam Jamil (maryamj@uio.no)

Open up a terminal window. Direct to the folder you want to download the project in and write these commands:

### To download the project/repo:

`git clone https://github.uio.no/compilerconstruction-inf5110/compila25-06.git`

### Then to go into the right directory:

`cd compila-06`

### For starting ant:

`ant`

### Building the project:

`ant build`

There are four different test runs:

### To run the full program file runme.cmp:

`ant run`

### To run the no-error test-files in semanticanalysis:

`ant test`

### To the error test-files in semanticanalysis:

`ant fail`

### To run all tests in semanticanalysis(error and no-error):

`ant test-all`

After the project has run successfully, there should be a file/files generated in this directory: src/tests/outputs.
