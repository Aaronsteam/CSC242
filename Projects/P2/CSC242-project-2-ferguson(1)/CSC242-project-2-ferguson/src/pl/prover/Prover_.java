package pl.prover;
import pl.core.*;
import pl.util.ArraySet;

public class Prover_ implements Prover {

    public static boolean ttEntails(KB kb, Sentence a) {
        ArraySet<Symbol> symbols = new ArraySet<>(kb.symbols());
        Model model = new Model_();
        return ttCheckAll(kb, a, symbols, model);
    }

    private static boolean ttCheckAll(KB kb, Sentence a, ArraySet<Symbol> symbols, Model model) {
        if(symbols.isEmpty()) {
            if(plTrue(kb, model)) return plTrue(a, model);
            else return true;
        } else {
            Symbol P = symbols.get(0); //first
            ArraySet<Symbol> rest =  symbols.subList(1, symbols.size()); //rest
            model.set(P, true);
            boolean arg1 = ttCheckAll(kb, a, rest, model);
            model.set(P, false);
            boolean arg2 = ttCheckAll(kb, a, rest, model);
            return (arg1 && arg2);
        }
    }

    private static boolean plTrue(KB kb, Model model){
        return model.satisfies(kb);

    }

    private static boolean plTrue(Sentence sentence, Model model) {
        return model.satisfies(sentence);
    }


    public boolean entails(KB kb, Sentence alpha) {
        return ttEntails(kb, alpha);
    }


//part 2 code
/*
    public static boolean plResolution(KB kb, Sentence s) {

        Set<Clause> clauses = convert(kb);
        clauses.addAll(convert(s));

        Set<Clause> new_ = new HashSet<>();

        while(true) {
            for(Clause c1 : clauses) {
                for(Clause c2 : clauses) {
                    Set<Clause> resolvents = plResolve(c1, c2);
                    if(resolvents.contains(null)) return false;
                    new_.addAll(resolvents);
                }
            }
            if(clauses.contains(new_)) return false;
            clauses.addAll(new_);
        }
    }

    static Set<Clause> plResolve(Clause c1, Clause c2) {
        return new ArraySet<>();
    }
*/


}


