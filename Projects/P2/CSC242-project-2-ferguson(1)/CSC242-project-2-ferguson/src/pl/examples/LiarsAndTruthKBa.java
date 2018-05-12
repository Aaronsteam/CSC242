package pl.examples;
import pl.core.*;
import pl.prover.Solver_;

import static pl.prover.Prover_.ttEntails;

public class LiarsAndTruthKBa extends KB {


    public LiarsAndTruthKBa() {
        super();
        Symbol amy = intern("Amy");
        Symbol cal = intern("Cal");
        Symbol bob = intern("Bob");

        add(new Implication(amy, new Conjunction(cal, amy)));
        add(new Implication(bob, new Negation(cal)));
        add(new Implication(cal, new Conjunction(bob, new Negation(amy))));


        System.out.println("Results from ttEntails");
        System.out.print("Whether Amy is truthful: ");
        System.out.println(ttEntails(this, amy)); //part 1
        System.out.print("Whether Bob is truthful: ");
        System.out.println(ttEntails(this, bob));
        System.out.print("Whether Cal is truthful: ");
        System.out.println(ttEntails(this,cal));

        System.out.println("Results from walkSat");

        Sentence negConcl;
        negConcl = new Negation(amy);
        add(negConcl);
        System.out.print("Whether Amy is truthful: ");
        if(Solver_.walkSat(this, 0.5, 100) ==  null) System.out.println("true");
        else System.out.println("false");
        remove(negConcl);

        negConcl = new Negation(bob);
        add(negConcl);
        System.out.print("Whether Bob is truthful: ");
        if(Solver_.walkSat(this, 0.5, 100) ==  null) System.out.println("true");
        else System.out.println("false");
        remove(negConcl);

        negConcl = new Negation(cal);
        add(negConcl);
        System.out.print("Whether Cal is truthful: ");
        if(Solver_.walkSat(this, 0.5, 100) ==  null) System.out.println("true");
        else System.out.println("false");
        remove(negConcl);
    }

    public static void main(String[] argv) { new LiarsAndTruthKBa().dump(); }

}
