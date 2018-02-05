package com.company;

import java.util.HashSet;

public class Problem {
    private HashSet<State> states = new HashSet<>();
    private HashSet<Action> actions = new HashSet<>();



    private boolean isValid(int position, State state) {
        return !(state.board[position].equalsIgnoreCase("x") || state.board[position].equalsIgnoreCase("o"));

    }
    public HashSet<Action> applActions(State state) {
        HashSet<Action> toReturn = new HashSet<>();
        Action z;
        for(int i=0;i<9;i++) {
                if(isValid(i,state)) {
                z = new Action(String.valueOf(i));
                toReturn.add(z);
            }

        }
        return toReturn;
    }

    public State result(State state, Action action) {

        state.board[Integer.parseInt(action.getPosition())] = state.activePlayer;
        if(state.activePlayer.equalsIgnoreCase("X")){
            state.activePlayer = "O";
        } else if(state.activePlayer.equalsIgnoreCase("O")){
            state.activePlayer = "X";
        }
        return state;
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
    boolean isGoalState(State state) {

        for(int i = 0; i<9;i++) {
            if(state.board[i].equals("e") && utility(state) == 0) {
                return false;
            }
        }
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
