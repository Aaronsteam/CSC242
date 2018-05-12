package bn.inference;

import bn.core.*;
import bn.parser.MyParser;
import bn.parser.XMLBIFParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Rejection {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        XMLBIFParser parser = new XMLBIFParser();
        Assignment e = new Assignment();
        MyParser p = new MyParser();
        int n = Integer.parseInt(args[0]);
        BayesianNetwork bn = parser.readNetworkFromFile((String) args[1]);
        RandomVariable Rand = bn.getVariableByName((String) args[2]);
        int count = 3;
        while(count < args.length){
            e.put(bn.getVariableByName(args[count]), args[count+1]);
            count += 2;
        }
        Rejection i = new Rejection();

        long startTime = System.nanoTime();
        Distribution d = i.rejectionSampling(Rand, e, bn, n);
        long endTime = System.nanoTime();


        System.out.println(d);
        System.out.println("Duration: " + (endTime - startTime) + " nanoseconds");
    }




    private Distribution rejectionSampling(RandomVariable X, Assignment e, BayesianNetwork bn, int n) {

        Distribution N = new Distribution(X.getDomain().size()); // this is what we'll return
        //initially setting every entry in distribution to zero.
        for(Object o : X.getDomain()) N.put(o,0);

        for(int j = 1; j <= n; j++) { // where n is the number of samples we'll take

            Assignment x = priorSample(bn); // we take a random sample, and assign it to x.

            if(isConsistent(x,e)) {
                N.put(x.get(X), N.get(x.get(X)) + 1);
            }
        }
        N.normalize();
        return N;
    }

    private Assignment priorSample(BayesianNetwork bn) {

        Assignment x = new Assignment(); //an event with n elements

        for(RandomVariable Xi : bn.getVariableListTopologicallySorted()) {
            double p = Math.random();
            double cumulativeProb = 0.0;
            for(Object o : Xi.getDomain()) {
                x.set(Xi, o);
                cumulativeProb += bn.getProb(Xi, x);
                if(p <= cumulativeProb) {

                    break;
                }
            }
        }
        //System.out.println("Sample generated: " + x);
        return x;
    }



    private static boolean isConsistent(Assignment a, Assignment b) {
        for(Map.Entry<RandomVariable, Object> Xi : b.entrySet()) { //for every random variable in assignment x
            if(!Xi.getValue().equals(a.get(Xi.getKey()))) { // if any of the assignments in a is unequal to that of b then return false.
                return false;
            }
        }
        return true;
    }
}
