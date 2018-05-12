package pl.examples;

import pl.core.*;
import pl.prover.Solver_;

import static pl.prover.Prover_.ttEntails;

public class MoreLiarsAndTruthKB extends KB {



    public MoreLiarsAndTruthKB() {
        super();
        Symbol a = intern("Amy");
        Symbol b = intern("Bob");
        Symbol c = intern("Cal");
        Symbol d = intern("Dee");
        Symbol e = intern("Eli");
        Symbol f = intern("Fay");
        Symbol g = intern("Gli");
        Symbol h = intern("Hal");
        Symbol i = intern("Ida");
        Symbol j = intern("Jay");
        Symbol k = intern("Kay");
        Symbol l = intern("Lee");

        add(new Implication(a, new Conjunction(h, i)));
        add(new Implication(b, new Conjunction(a, l)));
        add(new Implication(c, new Conjunction(b, g)));
        add(new Implication(d, new Conjunction(e, l)));
        add(new Implication(e, new Conjunction(c, h)));
        add(new Implication(f, new Conjunction(d, i)));
        add(new Implication(g, new Conjunction(new Negation(e), new Negation(j))));
        add(new Implication(h, new Conjunction(new Negation(f), new Negation(k))));
        add(new Implication(i, new Conjunction(new Negation(g), new Negation(k))));
        add(new Implication(j, new Conjunction(new Negation(a), new Negation(c))));
        add(new Implication(k, new Conjunction(new Negation(d), new Negation(f))));
        add(new Implication(l, new Conjunction(new Negation(b), new Negation(j))));



        System.out.println("Results from ttEntails");
        for(Symbol s : this.symbols()) {
            System.out.print("Whether " + s.getName() + " is truthful: ");
            System.out.println(ttEntails(this, s));
        }



        System.out.println("Results from walkSat");
        Sentence negConcl;
        for(Symbol s : this.symbols()) {
            negConcl = new Negation(s);
            add(negConcl);
            System.out.print("Whether " + s.getName() + " is truthful: ");
            if(Solver_.walkSat(this, 0.5, 100) == null) System.out.println("true");
            else System.out.println("false");
            remove(negConcl);
        }

    }

    public static void main(String[] argv) { new MoreLiarsAndTruthKB().dump(); }
}
