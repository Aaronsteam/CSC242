package lc.example;
// Accuracy: Perceptron-0.84. Logistic-0.94
// Performance:
import java.io.IOException;
import java.util.List;

import lc.core.Example;
import lc.core.LearningRateSchedule;
import lc.core.LogisticClassifier;
import lc.display.ClassifierDisplay;

public class LogisticClassifierTest {
	
	/**
	 * Train a LogisticClassifier on a file of examples and
	 * print its (1.0-squared-error/sample) after each training step.
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java LogisticClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule (AIMA p725)");
			System.exit(-1);
		}
		String filename = "lc/example/" + argv[0];
		int nsteps = Integer.parseInt(argv[1]);
		double alpha = Double.parseDouble(argv[2]);
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		
		//ClassifierDisplay display = new ClassifierDisplay("LogisticClassifier: " + filename);
		List<Example> allExamples = Data.readFromFile(filename);
		List<Example> someExamples = allExamples.subList(0, (int)(allExamples.size()));
		int ninputs = someExamples.get(0).inputs.length;
		LogisticClassifier classifier = new LogisticClassifier(ninputs) {
			public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
				double oneMinusError = 1.0-squaredErrorPerSample(examples);
				System.out.println(stepnum + "\t" + oneMinusError);
				//display.addPoint(stepnum/(double)nsteps, oneMinusError);
			}
		};
		if (alpha > 0) {
			classifier.train(someExamples, nsteps, alpha);
		} else {
		    double start = System.nanoTime();
			classifier.train(someExamples, 100000, new LearningRateSchedule() {
				public double alpha(int t) { return 1000.0/(1000.0+t); }
			});
			double end = System.nanoTime();
			System.out.println("Time to train: " + (end-start) + "ns");
		}
	}

}
