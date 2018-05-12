package pl.core;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;

import java.util.HashMap;
import java.util.Set;


public class Model_ implements Model {

    HashMap<Symbol, Boolean> map = new HashMap<>();


        /**
         * Set the value assigned to the given PropositionSymbol in this
         * Model to the given boolean VALUE.
         */
        public void set(Symbol sym, boolean value) {
            map.put(sym, value);
        }

        /**
         * Returns the boolean value associated with the given PropositionalSymbol
         * in this Model.
         */
        public boolean get(Symbol sym) {
            return map.get(sym);
        }

        /**
         * Return true if this Model satisfies (makes true) the given KB.
         */

        public boolean satisfies(KB kb) {

            Set<Clause> allClauses = CNFConverter.convert(kb);

            for(Clause c : allClauses) {
                if(!c.isSatisfiedBy(this)) return false;
            }
            return true;
        }

        /**
         * Return true if this Model satisfies (makes true) the given Sentence.
         */
        public boolean satisfies(Sentence sentence) {
            Set<Clause> allClauses = CNFConverter.convert(sentence);
            for(Clause c : allClauses) {
                if(!c.isSatisfiedBy(this)) return false;
            }
            return true;
        }

        /**
         * Print the assignments in this Model to System.out.
         */
        public void dump() {
            System.out.println(map);
        }

        public void flipSymbol(Clause clause, int index) {

            //flipping the value in model randomly
            if(get(clause.get(index).getContent())) {
                set(clause.get(index).getContent(), false);
            } else {
                set(clause.get(index).getContent(), true);
            }
        }
}
