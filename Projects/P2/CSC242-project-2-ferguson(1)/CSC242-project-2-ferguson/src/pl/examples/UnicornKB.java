package pl.examples;

import pl.cnf.*;
import pl.core.*;
import pl.prover.Prover_;
import pl.prover.Solver_;

public class UnicornKB extends KB {

    public UnicornKB() {
        super();
        Symbol mythical = intern("Mythical");
        Symbol mortal = intern("Mortal");
        Symbol mammal = intern("Mammal");
        Symbol horned = intern("Horned");
        Symbol magical = intern("Magical");

        add(new Implication(mythical, new Negation(mortal)));
        add(new Implication(new Negation(mythical), new Conjunction(mortal, mammal)));
        add(new Implication((new Disjunction(new Negation(mortal), mammal)), horned));
        add(new Implication(horned, magical));


        System.out.println("Proof using ttEntails: ");
        System.out.print("Whether unicorn is mythical: ");
        System.out.println(Prover_.ttEntails(this, mythical)); //part 1s
        System.out.print("Whether unicorn is magical: ");
        System.out.println(Prover_.ttEntails(this, magical)); //part 1
        System.out.print("Whether unicorn is horned: ");
        System.out.println(Prover_.ttEntails(this, horned)); // part 1


        System.out.println("Proof using WalkSat: ");
        Sentence negConcl = new Negation(mythical);
        add(negConcl); // adding mythical to the kb and then checking for satisfiability


        Model m1 = Solver_.walkSat(this, 0.5, 10000);
        System.out.print("Whether it is mythical: ");
        if(m1 != null) {
            System.out.println("false");

        } else System.out.println("true");
        remove(negConcl); //removed mythical from kb to check other entailments

        negConcl = new Negation(magical);
        add(negConcl); // adding magical to the kb and then checking for satisfiability

        Model m2 = Solver_.walkSat(this, 0.5, 10000);
        System.out.print("Whether it is magical: ");
        if(m2 != null) {
            System.out.println("false");

        } else System.out.println("true");
        remove(negConcl);

        negConcl = new Negation(horned);
        add(negConcl);
        Model m3 = Solver_.walkSat(this, 0.5, 10000);
        System.out.print("Whether it is horned: ");
        if(m3 != null) {
            System.out.println("false");

        } else System.out.println("true");
        remove(negConcl);
    }
    public static void main(String[] argv) { new UnicornKB().dump(); }
}



