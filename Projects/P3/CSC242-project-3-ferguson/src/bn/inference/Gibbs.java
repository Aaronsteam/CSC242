package bn.inference;

import bn.core.*;
import bn.parser.MyParser;
import bn.parser.XMLBIFParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Gibbs {

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
        Gibbs i = new Gibbs();
        Distribution d = i.gibbsAsk(Rand, e, bn, n);

        System.out.println(d);
    }

    public Distribution gibbsAsk(RandomVariable X, Assignment e, BayesianNetwork bn, int n) {
        Domain d =  X.getDomain();
        Distribution N = new Distribution(d.size());
        //initially setting every entry in distribution to zero.
        for(int i = 0; i< N.size(); i++) {
            N.put(d.get(i),0);
        }
        List<RandomVariable> l = bn.getVariableList();
        Assignment x = e.copy(); //x is the current state of the network
        double product = 1;
        for(int j = 1; j <= n; j++)
            for(RandomVariable Z : l) {
            Distribution ZD = new Distribution();
            for(Object value : Z.getDomain()) {
                x.put()
            }
                for(RandomVariable Y : bn.getChildren(Z)) {
                    product *= bn.getProb(Y, e);
                }

                double p = Math.random();
                double cumulativeProb = 0.0;
                for(Object o : Z.getDomain()) {
                    x.set(Z, o);
                    cumulativeProb += (bn.getProb(Z, x)*product);
                    if(p <= cumulativeProb) {
                        break;
                    }
                }



                //x.set(Z, bn.getProb(Z, e) * product);

                N.put(x.get(X), N.get(x.get(X)) + 1);
            }
        N.normalize();
        return N;
    }

}
