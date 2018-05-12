package pl.examples;
import pl.core.*;
import pl.prover.Solver_;

import static pl.prover.Prover_.ttEntails;

public class LiarsAndTruthKBb extends KB {


    public LiarsAndTruthKBb() {
        super();
        Symbol amy = intern("Amy");
        Symbol cal = intern("Cal");
        Symbol bob = intern("Bob");

        add(new Implication(amy, new Negation(cal)));
        add(new Implication(bob, new Conjunction(amy, cal)));
        add(new Implication(cal, bob));


        System.out.println("Results from ttEntails");
        System.out.print("Whether Amy is truthful: ");
        System.out.println(ttEntails(this, amy));
        System.out.print("Whether Bob is truthful: ");
        System.out.println(ttEntails(this, bob));
        System.out.print("Whether Cal is truthful: ");
        System.out.println(ttEntails(this,cal));

        System.out.println("Results from walkSat");

        Sentence negConcl;
        negConcl = new Negation(amy);
        add(negConcl);
        System.out.print("Whether Amy is truthful: ");
        System.out.println(Solver_.walkSat(this, 0.5, 100));
        remove(negConcl);

        negConcl = new Negation(bob);
        add(negConcl);
        System.out.print("Whether Bob is truthful: ");
        System.out.println(Solver_.walkSat(this, 0.5, 100));
        remove(negConcl);

        negConcl = new Negation(cal);
        add(negConcl);
        System.out.print("Whether Cal is truthful: ");
        System.out.println(Solver_.walkSat(this, 0.5, 100));
        remove(negConcl);

    }

    public static void main(String[] argv) { new LiarsAndTruthKBa().dump(); }

}
