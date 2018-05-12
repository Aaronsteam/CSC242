package bn.inference;

import bn.core.*;
import bn.parser.BIFParser;
import bn.parser.MyParser;
import bn.parser.XMLBIFParser;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class LikelihoodWeighting {


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        BayesianNetwork bn;
        XMLBIFParser parser = new XMLBIFParser();
        Assignment e = new Assignment();
        int n = Integer.parseInt(args[0]);
        String filename = args[1];

        int dotIndex = filename.indexOf('.');
        String fileType = filename.substring(dotIndex+1, filename.length());
        if(fileType.equalsIgnoreCase("xml")) {
            XMLBIFParser xbParser = new XMLBIFParser();
            bn = xbParser.readNetworkFromFile(filename);
        } else if(fileType.equalsIgnoreCase("bif")) {
            BIFParser bParser = new BIFParser(new FileInputStream(new File(filename)));
            bn = bParser.parseNetwork();
        } else {
            bn = null;
            throw new IOException("File Format Not Supported");
        }

        RandomVariable Rand = bn.getVariableByName((String) args[2]);
        int count = 3;
        while(count < args.length){
            e.put(bn.getVariableByName(args[count]), args[count+1]);
            count += 2;
        }
        LikelihoodWeighting i = new LikelihoodWeighting();

        long startTime = System.nanoTime();
        Distribution d = i.likelihoodWeighting(Rand, e, bn, n);
        long endTime = System.nanoTime();

        System.out.println(d);

        System.out.println("Duration: " + (endTime - startTime) + " nanoseconds");
    }

    private Distribution likelihoodWeighting(RandomVariable X, Assignment e, BayesianNetwork bn, int n) {

        Domain XDomain = X.getDomain();

        Distribution W = new Distribution(XDomain.size());

        //initially setting every entry in distribution to zero.
        for (Object aXDomain : XDomain) {
            W.put(aXDomain, 0);
        }
        for(int j = 1; j <= n; j++) {
            List<Object> l = weightedSample(bn, e);  //no such element exception
            Assignment x = (Assignment) l.get(0);
            if(x == null) System.out.println("x is null");
            double w = (double) l.get(1);
            W.put(x.get(X), W.get(x.get(X)) + w);
        }
        W.normalize();
        return W;
}

    private List<Object> weightedSample(BayesianNetwork bn, Assignment e) {
        List<Object> l = new ArrayList<>();
        double w = 1;
        Assignment x = e.copy();

        for(RandomVariable Xi : bn.getVariableListTopologicallySorted()) {

            if(e.containsKey(Xi)) {

                w *= bn.getProb(Xi, x);
            }
            else {

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
        }
        l.add(0, x);
        l.add(1, w);
        return l;
    }
}
