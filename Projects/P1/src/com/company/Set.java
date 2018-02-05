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



    Action minimax_decision(Node n, Problem p) {
        int min =-1000000;
        Action minAction = new Action("9"); //9 placeholder action
        for(Action a : p.applActions(n.getState())) {
            if(min_val(p.result(n.getState(),a),p)>min) {
                minAction = a;
            }

        }
        return minAction;
    }

    private int max_val(State s, Problem p) {
        if(p.isGoalState(s)) {
            return p.utility(s);
        }
        int v = -1000000;

        for(Action a: p.applActions(s)) {
            v = Math.max(v, min_val(p.result(s,a),p));
        }
        return v;
    }

    private int min_val(State s, Problem p) {
        if(p.isGoalState(s)) {
            return p.utility(s);
        }
        int v = 1000000;
        for(Action a: p.applActions(s)) {
            v = Math.min(v, max_val(p.result(s,a),p));
        }
        return v;
    }

}
