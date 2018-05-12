package pl.prover;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.core.*;
import pl.util.ArraySet;

import java.util.Random;
import java.util.Set;

public class Solver_ {

    public static Model walkSat(KB kb, double p, int max_flips) {
        Random rand = new Random();
        Model_ model = new Model_();
        for(Symbol sym : kb.symbols()) { //random assignment all symbols
            model.set(sym, rand.nextBoolean());
        }

        for(int i = 1; i <= max_flips; i++) {
            if(model.satisfies(kb)) return model;
            Clause clause = getFalseClause(kb, model); //get a random clause that is false in the model
            if(Math.random() < p) { // with the probability p do the following
                int index = rand.nextInt(clause.size()); // randomly generate an index for a symbol
                model.flipSymbol(clause, index);
            } else {
                //flip whichever symbol in clause maximizes the number of satisfied clauses
                int maxSatisfied = 0;
                int count;
                int clauseSize = clause.size();

                for(int j = 0; j < clauseSize; j++) {

                    count = 0;
                    model.flipSymbol(clause,j); //flip symbol

                    for(Sentence s : kb.sentences()) {
                        if(model.satisfies(s)) count++;
                    }

                    if(count > maxSatisfied) {
                        maxSatisfied = count;
                    } else {
                        model.flipSymbol(clause,j); //unflip if it didn't maximize the number of satisfied clauses.
                    }

                }
            }
        }
        return null;
    }

    private static Clause getFalseClause(KB kb, Model_ model) {
        Random rand = new Random();
        int randIndex;
        while(true) {
            randIndex = rand.nextInt(kb.sentences().size());
            Sentence test = kb.sentences().get(randIndex);
            for(Clause c: CNFConverter.convert(test)) {
                if(!c.isSatisfiedBy(model)) return c;

            }
        }
    }
}
