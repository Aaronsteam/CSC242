package dt.example;

import dt.core.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Iris extends Problem{

    public Iris() {
        super();
        // Input variables

        this.inputs.add(new Variable("Sepal Length", new Domain("S", "MS", "ML", "L")));
        this.inputs.add(new Variable("Sepal Width", new Domain("S", "MS", "ML", "L")));
        this.inputs.add(new Variable("Petal Length", new Domain("S", "MS", "ML", "L")));
        this.inputs.add(new Variable("Petal Width", new Domain("S", "MS", "ML", "L")));

        // Output variable
        this.output = new Variable("Class", new Domain("Iris-setosa", "Iris-versicolor", "Iris-virginica"));
    }
    public static void main(String[] args) throws IOException {
        Problem problem = new Iris();
        problem.dump();
        Set<Example> allExamples = problem.readExamplesFromCSVFile(new File("dt/example/" + args[0]));
        List<Example> list = new ArrayList<>(allExamples);
        //half 150 entries in list.

        List<Example> newList = list.subList(0, 150);
        Set<Example> someExamples = new HashSet<>(newList);
        //p
        for (Example e : someExamples) {
            System.out.println(e);
        }
        long startTime = System.nanoTime();
        DecisionTree tree = new DecisionTreeLearner(problem).learn(someExamples);
        long estTime = System.nanoTime() - startTime;
        tree.dump();
        tree.test(allExamples);
        System.out.println("\nEst Time: " + estTime + "ns");
    }
}


