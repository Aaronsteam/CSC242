
# Spaces not tabs for recipes (note space at end of next line)
.RECIPEPREFIX= 

JAVA = java
JAVAC = javac
JAVADOC = javadoc

build all:
	test -d bin || mkdir bin
	cd src; $(JAVAC) -d ../bin `find . -name '*.java'`

doc:
	$(JAVADOC) -d doc `find src -name '*.java'`

#
# dt
#
run-dt-willwait:
	$(JAVA) -cp bin dt.example.WillWaitProblem src/dt/example/WillWait-data.txt

#
# lc
#
run-lc-perceptron-earthquake-clean:
	$(JAVA) -cp bin lc.example.PerceptronClassifierTest src/lc/example/earthquake-clean.data.txt 10000 0.95

run-lc-logistic-earthquake-clean:
	$(JAVA) -cp bin lc.example.LogisticClassifierTest src/lc/example/earthquake-clean.data.txt 10000 0.95

run-lc-perceptron-earthquake-noisy:
	$(JAVA) -cp bin lc.example.PerceptronClassifierTest src/lc/example/earthquake-noisy.data.txt 10000 0.95

run-lc-logistic-earthquake-noisy:
	$(JAVA) -cp bin lc.example.LogisticClassifierTest src/lc/example/earthquake-noisy.data.txt 10000 0.95

run-lc-perceptron-earthquake-noisy-decaying:
	$(JAVA) -cp bin lc.example.PerceptronClassifierTest src/lc/example/earthquake-noisy.data.txt 10000 0

run-lc-logistic-earthquake-noisy-decaying:
	$(JAVA) -cp bin lc.example.LogisticClassifierTest src/lc/example/earthquake-noisy.data.txt 10000 0

#
# nn
#
run-nn-majority:
	$(JAVA) -cp bin nn.example.MajorityPerceptronNN 100 5

run-nn-majority-1000:
	$(JAVA) -cp bin nn.example.MajorityPerceptronNN 1000 50

run-nn-majority-5000:
	$(JAVA) -cp bin nn.example.MajorityPerceptronNN 5000 500
