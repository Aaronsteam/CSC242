package pl.core;

public class Sentence_ implements Sentence{


    /**
     * Return true if this Sentence is satisfied by the given Model.
     */
    public boolean isSatisfiedBy(Model model) {
        return model.satisfies(this);
    }
}
