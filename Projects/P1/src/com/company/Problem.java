package com.company;

import java.util.HashSet;

public class Problem {
    private HashSet<State> states = new HashSet<>();
    private HashSet<Action> actions = new HashSet<>();



    public void populateActions(){
        Action[] a = new Action[9];
        for(int i = 0; i<9; i++) {
            a[i] = new Action(String.valueOf(i) + 1);
            this.addAction(a[i]);
        }

    }
    private boolean isValid(int position, State state) {
        return !(state.board[position].equalsIgnoreCase("x") || state.board[position].equalsIgnoreCase("o"));

    }
    public HashSet<Action> applActions(State state) {
        if(isGoalState(state)) return null; //at the goal states, there are no applicable actions.
        HashSet<Action> toReturn = new HashSet<>();
        Action z;
        for(int i=0;i<9;i++) {
                if(isValid(i,state)) {
//                    System.out.println("Here");
                z = new Action(String.valueOf(i+1));
                toReturn.add(z);
            }

        }
        System.out.println(toReturn.size());
        return toReturn;
    }

    public State result(State state, Action action) {
       // System.out.println("Action val: " + action.getPosition());
        State temp = state.copy();
        if(Integer.parseInt(action.getPosition()) == 0) return temp;
        temp.board[Integer.parseInt(action.getPosition())-1] = temp.activePlayer;
        System.out.println("Active player is " + temp.activePlayer);
        if(temp.activePlayer.equalsIgnoreCase("X")){
            temp.activePlayer = "O";
        } else if(temp.activePlayer.equalsIgnoreCase("O")){
            temp.activePlayer = "X";
        }
        return temp;
    }

    public double cost(State init, Action action, State fin) {
        //
        return 1;
    }

    Problem() {
        initState = new State();
        initState.activePlayer = "X";
        for (int i = 0; i < 9; i++) {
            initState.board[i] = "e"; //e for empty;
        }
    }
    public boolean isGoalState(State state) {

    if(utility(state) == 0) {//meaning no one has won in the state
        for (int i = 0; i < 9; i++) {
            if (state.board[i].equals("e")) {
//                System.out.println("Here");
                return false;
            }
        }
    }
        //System.out.println("T");
        //state.print3Board();
        return true;
    }

    public int utility(State s) {
        //X is maximizing, O is minimizing
        if ((!s.board[0].equals("e")) && s.board[0].equalsIgnoreCase(s.board[1]) && s.board[1].equalsIgnoreCase(s.board[2])) {
            if(s.board[0].equalsIgnoreCase("x")) {
                return 1;
            } else {
                return -1;
            }
        }
            if ((!s.board[3].equals("e")) && s.board[3].equalsIgnoreCase(s.board[4]) && s.board[4].equalsIgnoreCase(s.board[5])) {
            if(s.board[0].equalsIgnoreCase("x")) {
                return 1;
            } else {
                return -1;
            }
        }

            if ((!s.board[6].equals("e")) && s.board[6].equalsIgnoreCase(s.board[7]) && s.board[7].equalsIgnoreCase(s.board[8])) {
             if(s.board[6].equalsIgnoreCase("x")) {
                return 1;
            } else {
                return -1;
            }
            }
            if ((!s.board[0].equals("e")) && s.board[0].equalsIgnoreCase(s.board[4]) && s.board[4].equalsIgnoreCase(s.board[8])) {
              if(s.board[0].equalsIgnoreCase("x")) {
                return 1;
            } else {
                return -1;
            }
            }
            if ((!s.board[2].equals("e")) && s.board[2].equalsIgnoreCase(s.board[4]) && s.board[4].equalsIgnoreCase(s.board[6])) {
              if(s.board[2].equalsIgnoreCase("x")) {
                return 1;
            } else {
                return -1;
            }
        }
            return 0;
    }


    private State initState;
    private HashSet<State> goalStates;

    public State getInitState() {
        return initState;
    }

    public void setInitState(State state) {
        initState = state;
    }
    public HashSet<State> getGoalStates() {
        return goalStates;
    }

    public void setgoalStates(HashSet<State> states) {
        goalStates = states;
    }


    public void addAction(Action a) {
        actions.add(a);
    }

}
