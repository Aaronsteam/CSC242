package lc.example;

import java.io.IOException;
import java.util.List;

import lc.core.Example;
import lc.core.LearningRateSchedule;
import lc.core.PerceptronClassifier;
import lc.display.ClassifierDisplay;

public class PerceptronClassifierTest {

	/**
	 * Train a PerceptronClassifier on a file of examples and
	 * print its accuracy after each training step.
	 */
	public static void main(String[] argv) throws IOException {
		if (argv.length < 3) {
			System.out.println("usage: java PerceptronClassifierTest data-filename nsteps alpha");
			System.out.println("       specify alpha=0 to use decaying learning rate schedule (AIMA p725)");
			System.exit(-1);
		}
		String filename = "lc/example/" + argv[0];
		int nsteps = Integer.parseInt(argv[1]);
		double alpha = Double.parseDouble(argv[2]);
		System.out.println("filename: " + filename);
		System.out.println("nsteps: " + nsteps);
		System.out.println("alpha: " + alpha);
		
		//ClassifierDisplay display = new ClassifierDisplay("PerceptronClassifier: " + filename);

        List<Example> allExamples = Data.readFromFile(filename);
        List<Example> someExamples = allExamples.subList(0, (int)(allExamples.size() *0.2));
		int ninputs = allExamples.get(0).inputs.length;
		PerceptronClassifier classifier = new PerceptronClassifier(ninputs) {
			public void trainingReport(List<Example> examples, int stepnum, int nsteps) {
				double accuracy = accuracy(examples);
				System.out.println(stepnum + "\t" + accuracy);
				//display.addPoint(stepnum/(double)nsteps, accuracy);
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
