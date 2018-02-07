package com.company;

import java.util.Stack;

public class Set<E> extends Stack<Node> {

    Set(State state) {
        Node n = new Node();
        n.setState(state);
        this.add(n);
    }

    Node selectOne(Problem p) {
/*        Node n = this.pop(); //popping from the frontier
        if(n.getState().activePlayer.equalsIgnoreCase("X") ) {
            for(Node f : this) { //check all the nodes in the frontier

            }
        } else {

        }
        Action a = minimax_decision(n,p); //checing the minimax decision of that node.
        while() {
            if(n.getState() == )
        }
        return p.result(n.getState(),a);*/
        return null;
    }





}
