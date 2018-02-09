package com.company;

import java.util.HashSet;

public class State {
    public String[] board;
    String activePlayer;

    HashSet<String[][]> cool;

    public State(){
        cool.add(new String[] []);
        activePlayer = new String();
        board = new String[9];
        for (int i = 0; i < board.length; i++){
            board[i] = new String();
        }
    }

    public void print3Board() {
        for(int i=0 ;i <9;i++) {
            System.out.print(this.board[i] );
            if((i+1)%3 == 0) {
                System.out.println();
            } else {
                System.out.print("|");
            }
        }
    }
    public State copy(){
        State s = new State();
        s.activePlayer = this.activePlayer;

        for (int i = 0; i < this.board.length; i++) {
            s.board[i] = this.board[i];
        }
        return s;
    }
}

