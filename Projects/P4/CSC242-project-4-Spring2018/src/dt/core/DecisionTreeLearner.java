package dt.core;

import java.util.*;


/**
 * Implementation of the decision-tree learning algorithm in AIMA Fig 18.5.
 * This is based on ID3 (AIMA p. 758).
 */
public class DecisionTreeLearner extends AbstractDecisionTreeLearner {
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public DecisionTreeLearner(Problem problem) {
		super(problem);
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.  
	 */
	@Override
	protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples) {
        // Must be implemented by you; the following two methods may be useful
        if (examples.isEmpty()) return new DecisionTree(pluralityValue(parent_examples));
            // if all examples have the same classification
        else if (uniqueOutputValue(examples) != null) {
            System.out.println("printing unique output value: " + uniqueOutputValue(examples));
            return new DecisionTree(uniqueOutputValue(examples));
        } else if (attributes.isEmpty()) {
            return new DecisionTree(pluralityValue(examples));
        } else {
            Variable A = argMaxAttr(attributes, examples);
            DecisionTree tree = new DecisionTree(A);
            for(String vk : A.getDomain()) {
                Set<Example> exs =  examplesWithValueForAttribute(examples, A, vk);
                DecisionTree subtree = learn(exs, subtract(attributes, A), examples);
                tree.children.add(subtree);
            }
            return tree;
        }
    }

    private List<Variable> subtract(List<Variable> s1, Variable s2) {
	    s1.remove(s2);
	    return s1;
    }

    private Variable argMaxAttr(List<Variable> attributes, Set<Example> examples) {
        double maxImp = 0;
        double currImp;
        Variable argMax = new Variable("null", new Domain());
        for (Variable a : attributes) {
            currImp = gain(a, examples);
            if(currImp > maxImp) {
                maxImp = currImp;
                argMax = a;

            }
        }
        return argMax;

    }

	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties
	 * randomly.
	 * I don't do the random part yet.
	 */
	@Override
	protected String pluralityValue(Set<Example> examples) {
	    // I love HashMaps. HashMaps to the rescue.
	    Map<String, Integer> countMap = new HashMap<>();

	    for(Example e : examples) {
	        if(countMap.containsKey(e.getOutputValue())) {
                countMap.put(e.getOutputValue(), countMap.get(e.getOutputValue()) + 1); //increment the value
            } else {
	            countMap.put(e.getOutputValue(), 1); // otherwise map the new key to 1
            }
        }
        return maxValEntry(countMap).getKey();
	}


	private Map.Entry<String, Integer> maxValEntry(Map<String, Integer> map) {
        Map.Entry<String, Integer> maxEntry = null;
	    for(Map.Entry<String, Integer> entry : map.entrySet()) {
	        if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
	            maxEntry = entry;
            }
        }
        return maxEntry;
    }

	/**
	 * Returns the single unique output value among the given examples
	 * if there is only one, otherwise null.
	 */
	@Override
	protected String uniqueOutputValue(Set<Example> examples) {
	    // fill in val with a random val
	    String val = examples.iterator().next().getOutputValue();
	    // then iterate over all the values and if different return null.
	    for(Example e : examples) {
	        // if a different val is found
	        if (!val.equals(e.getOutputValue())) {
	            return null;
            }

        }
	    // if never different then will never return null and come here to return val
		return val;
	}
	
	//
	// Utility methods required by the AbstractDecisionTreeLearner
	//

	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	@Override
	protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
	    Set<Example> s = new HashSet<>();
	    for(Example e : examples) {
	        if(e.getInputValue(a).equals(vk)) {
	            s.add(e);
            }
        }
        return s;
	}
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	@Override
	protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
		int result = 0;
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result += 1;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	@Override
	protected int countExamplesWithValueForOutput(Set<Example> examples, String vk) {
	    int count = 0;
	    for(Example e : examples) {
	        if (e.getOutputValue().equals(vk)) count++;
        }
        return count;
	}
}
