package com.company;

import java.util.HashSet;
import java.util.Stack;

public class Node {
    private State state;
    private Node lmc, rs, parent;

    State getState() {
        return state;
    }
    void setState(State state) {
       this.state = state;
    }
    public Stack<Node> expand() {
        Node current = this.lmc;
        Stack<Node> expansion = new Stack<>();

        while(current != null) {
            expansion.add(current);
            current = current.rs;
        }

        return expansion;
    }


    public Solution getSolution() {
        return null;
    }

}
