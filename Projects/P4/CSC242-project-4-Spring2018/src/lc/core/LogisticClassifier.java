package lc.core;

import math.util.VectorOps;

public class LogisticClassifier extends LinearClassifier {
	
	public LogisticClassifier(int ninputs) {
		super(ninputs);
	}
	
	/**
	 * A LogisticClassifier uses the logistic update rule
	 * (AIMA Eq. 18.8): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times h_w(x)(1-h_w(x)) \times x_i 
	 */
	public void update(double[] x, double y, double alpha) {
	    for(int i = 0; i < x.length; i++) {
	        double evalX = eval(x);
	        weights[i] = weights[i] + alpha * (y - evalX) * evalX * (1 - evalX) * x[i];
        }
	}
	
	/**
	 * A LogisticClassifier uses a 0/1 sigmoid threshold at z=0.
	 */
	public double threshold(double z) {
		return 1/(1 + Math.exp(-z));
	}

}
