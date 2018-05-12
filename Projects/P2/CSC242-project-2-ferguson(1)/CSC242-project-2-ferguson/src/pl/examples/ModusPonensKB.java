package pl.examples;

import pl.core.*;
import pl.prover.Solver_;

import static pl.prover.Prover_.ttEntails;

public class ModusPonensKB extends KB {
	
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		Symbol q = intern("Q");
		add(p);

		Sentence a = new Implication(p, q);
		add(a);

        System.out.println("Results from ttEntails");
		System.out.print("Whether P, P implies Q entails Q: ");
		System.out.println(ttEntails(this, q)); //part 1

        System.out.println("Results from walkSat");
        System.out.print("Whether P, P implies Q entails Q: ");
        add(new Negation(q));
        if (Solver_.walkSat(this, 0.5, 10000) == null) System.out.println("true");
        else System.out.println("false");//part 2
	}
	
	public static void main(String[] argv) {
		new ModusPonensKB().dump();
	}

}
