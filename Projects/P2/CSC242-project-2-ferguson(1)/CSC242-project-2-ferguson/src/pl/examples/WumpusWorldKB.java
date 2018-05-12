package pl.examples;

import pl.core.*;
import pl.prover.Prover_;
import pl.prover.Solver_;

public class WumpusWorldKB extends KB {
	
	public WumpusWorldKB() {
		super();
		Symbol p11 = intern("P1,1");
		Symbol p12 = intern("P1,2");
		Symbol p21 = intern("P2,1");
		Symbol p22 = intern("P2,2");
		Symbol p31 = intern("P3,1");
		Symbol b11 = intern("B1,1");
		Symbol b21 = intern("B2,1");

		Sentence s1 = new Biconditional(b11, new Disjunction(p12, p21));
		Sentence s2 = new Biconditional(b21, new Disjunction(p12, new Disjunction(p22, p31)));
		add(new Negation(p11));
		add(s1);
		add(s2);

		add(new Negation(b11));
		add(b21);
		System.out.println("Checking with ttEntails");
		System.out.print("Whether p12 is entailed: ");
		System.out.println(Prover_.ttEntails(this, p12)); // part 1

        Sentence negatedConclusion = new Negation(p12);
        add(negatedConclusion);
        System.out.println("Checking with walkSat: ");
        System.out.print("Whether p12 is entailed: ");
		if(Solver_.walkSat(this, 0.5, 100) == null) System.out.println("true");
		else System.out.println("false"); // part 2
	}

	public static void main(String[] argv) {
		new WumpusWorldKB().dump();
	}

}
