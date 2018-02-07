package com.company;

public class State {
    public String[] board = new String[9];
    String activePlayer;
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
}

