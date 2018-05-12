package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import bn.parser.MyParser;
import bn.parser.XMLBIFParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exact implements Inferencer {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        XMLBIFParser parser = new XMLBIFParser();
        Assignment e = new Assignment();
        MyParser p = new MyParser();

        BayesianNetwork bn = parser.readNetworkFromFile((String) args[1]);
        RandomVariable Rand = bn.getVariableByName((String) args[2]);
        int count = 3;
        while(count < args.length){
            e.put(bn.getVariableByName(args[count]), args[count+1]);
            count += 2;
        }
        Exact i = new Exact();

        long startTime = System.nanoTime();
        Distribution d = i.enumerationAsk(bn, Rand, e);
        long endTime = System.nanoTime();

        System.out.println(d);
        System.out.println("Duration: " + (endTime - startTime) + " nanoseconds");

    }


    public Distribution enumerationAsk(BayesianNetwork bn, RandomVariable X, Assignment e) {
        Distribution q = new Distribution(X);
        for(Object xi : X.getDomain()) {
            e.set(X, xi);
            q.put(xi, enumerateAll(bn, bn.getVariableList(), e));
        }
        q.normalize();
        return q;
    }

    public double enumerateAll(BayesianNetwork bn, List<RandomVariable> vars, Assignment e) {
        if(vars.isEmpty()) return 1.0;
        RandomVariable Y = vars.get(0);
        Assignment ey;
        double sum = 0.0;
        List<RandomVariable> copy = new ArrayList<RandomVariable>(vars);
        copy.remove(0);
        if(e.get(Y) != null) {
            ey = e.copy();
            return bn.getProb(Y,ey) * enumerateAll(bn, copy, ey); //change this;
        } else {

            for(Object y : Y.getDomain()) {
                ey = e.copy();
                ey.set(Y,y);
                sum += bn.getProb(Y, ey)* enumerateAll(bn, copy, ey);

            }
            return sum;
        }
    }
}
