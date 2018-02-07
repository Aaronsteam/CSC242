package com.company;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Stack;

public class Node {
    private State state;
    private LinkedList<Node> children;
    private int minimaxVal;


    public void setMinimaxVal(int x) {
        minimaxVal = x;
    }

    public int getMinimaxVal(){
        return minimaxVal;
    }
    public Node() {
    }

    public void addChild(Node n) {
        children.add(n);
    }

    State getState() {
        return state;
    }
    void setState(State state) {
       this.state = state;
    }
    public LinkedList<Node> expand() {
        return children;
    }


    public Solution getSolution() {
        return null;
    }

    public LinkedList<Node> getChildren(){
        return children;
    }



}
