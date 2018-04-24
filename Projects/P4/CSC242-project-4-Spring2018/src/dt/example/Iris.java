package dt.example;

import dt.core.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

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
        Set<Example> examples = problem.readExamplesFromCSVFile(new File("dt/example/" + args[0]));
        for (Example e : examples) {
            System.out.println(e);
        }
        DecisionTree tree = new DecisionTreeLearner(problem).learn(examples);
        tree.dump();
        tree.test(examples);
    }
}
