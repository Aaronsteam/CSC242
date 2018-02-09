package com.company;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
public class Main {


    private static Solution treeSearch(Problem p) {
        Set<Node> frontier = new Set<>(p.getInitState());
        while(true) {
            if (frontier.isEmpty()) {
                return null;
            }

            Node node = frontier.selectOne(p);
            if (p.isGoalState(node.getState())) {
                return node.getSolution();
            }

            for (Node n : node.expand()) {
                    frontier.add(n);
                }
        }
    }
 /*   static Solution graphSearch(Problem p) {
        Set<Node> frontier = new Set<Node>(p.getInitState());
        Set<Node> explored = new Set<>();
        while(true){
            if (frontier.isEmpty()) {
                return null;
            }
        }
        Node node = frontier.selectOne();
        if (p.isGoalState(node.getState())) {
            return node.getSolution();
        }
        explored.add(node);

        for (Node n : node.expand()) {
            if(!explored.contains(n)) {
                frontier.add(n);
            }

        }
    }*/
       static Action minimax_decision(State s, Problem p) {
        int max = -1000000;
        HashSet<Action> appAct = p.applActions(s);
        Action maxAction = new Action("placeholder"); //9 placeholder action
        if(appAct == null) return new Action("no actions");
        for(Action a : appAct) {
            if(min_val(p.result(s,a),p)>max) {
                maxAction = a;
                max = min_val(p.result(s,a),p);
            }
        }
        return maxAction;
    }

    static private int max_val(State s, Problem p) {
        if(p.isGoalState(s)) {
            return p.utility(s);
        }
        int v = -1000000;

        for(Action a: p.applActions(s)) {
            v = Math.max(v, min_val(p.result(s,a),p));
        }
        return v;
    }

    static private int min_val(State s, Problem p) {
        if(p.isGoalState(s)) {
            return p.utility(s);
        }
        int v = 1000000;
        for(Action a: p.applActions(s)) {
            v = Math.min(v, max_val(p.result(s,a),p));
        }
        return v;
    }

    public static int minimax(Node s, Problem p) {

           if(p.isGoalState(s.getState())) {
               s.setMinimaxVal(p.utility(s.getState()));
               return p.utility(s.getState());
           }
           int result = 0;
           if(s.getState().activePlayer.equalsIgnoreCase("X")) {
               int max = -1000000;
               for(Node child: s.getChildren()) {
                   result = minimax(child,p);
                   if(result > max) {
                       result = max;
                   }
               }

           }
        if(s.getState().activePlayer.equalsIgnoreCase("X")) {
            int min = 1000000;
            for(Node child: s.getChildren()) {
                result = minimax(s,p);
                if(result < min) {
                    result = min;
                }
            }


        }

        s.setMinimaxVal(p.utility(s.getState()));
        return p.utility(s.getState());
    }
    public static void makeTree(Node current, Problem p) {
        //first make the tree
        //if(p.applActions(current.getState()) == null){
          //  System.out.println("Cool");
        //}
        if(p.applActions(current.getState()) != null) {
            for (Action a : p.applActions(current.getState())) {
                State result = p.result(current.getState(),a);
                Node childNode = new Node();
                childNode.setState(result);
                current.addChild(childNode);

                //result.print3Board(); this prints every possible child of every node.

                makeTree(childNode, p);
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem tictactoe = new Problem();
        tictactoe.populateActions(); //adding actions to tictactoe


        Node root = new Node(); //root of the tree
        Node copy = root.copy(); //trying to copy by value NOT reference

        root.setState(tictactoe.getInitState()); //sets the root.state to where root.state.board[i] = "e" for all 0 <= i < 9
        Node current = root;

        makeTree(root,tictactoe); // pass in the root to make the tree. How do I access the root?

        LinkedList<Node> children = current.getChildren();
        System.out.println("root is: ");
        LinkedList<Node> copyChildren = copy.getChildren();
        root.getState().print3Board();
        String res;

      //  while(!res.equalsIgnoreCase("n") || !res.equalsIgnoreCase("y")) {
            System.out.println("Welcome to the classic tic tac toe game!");
            System.out.println("Would you like to make the first move? (X always goes first) y/n");
            res = scanner.next();
            String move;
            if(res.equalsIgnoreCase("y")) {
                while(true) {
                    System.out.print("current is after y: ");
                    current.getState().print3Board();
                    if(tictactoe.isGoalState(current.getState())) break;
                    //System.out.print("Never come here");



                    System.out.println("Enter your move: ");
                    move = scanner.next();
                    State result = tictactoe.result(current.getState(), new Action(move));

                    for(Node child : current.getChildren()) { //getChildren() is empty
                        if(child.getState() == result)
                            current = child;
                    }

                    //current.getState().print3Board();

                    int maxUtil = -1000000;
                    for(Node n : current.getChildren()) {
                        System.out.println("Checking minimmax");
                        int currentMinimaxVal = n.getMinimaxVal();
                        if(currentMinimaxVal > maxUtil) {
                            maxUtil = currentMinimaxVal;
                            current = n;
                        }
                    }
                    current.getState().print3Board();

                }

            } else if(res.equalsIgnoreCase("n")) {
                while(true) {
                    if(tictactoe.isGoalState(current.getState())) break;
                    int maxUtil = -1000000;
                    for(Node n : current.getChildren()) {
                        int currentMinimaxVal = n.getMinimaxVal();
                        if(currentMinimaxVal > maxUtil) {
                            maxUtil = currentMinimaxVal;
                            current = n;
                        }
                    }
                    current.getState().print3Board();

                    System.out.println("Enter your move: ");
                    move = scanner.next();
                    State result = tictactoe.result(current.getState(), new Action(move));

                    for(Node child : current.getChildren()) {
                        if(child.getState() == result)
                            current = child;
                    }
                }

            } else {
                System.out.println("wrong input");
            }
     //   }

        if(tictactoe.utility(current.getState()) > 0) System.out.println("X won!");
        else if(tictactoe.utility(current.getState()) < 0) System.out.println("O won!");
        else System.out.println("It's a draw!");


/*
        Action afirst = minimax_decision(tictactoe.getInitState(), tictactoe);
        System.out.println(afirst.getPosition());
        //System.out.println(minimax_decision(tictactoe.result(tictactoe.getInitState(), minimax_decision(tictactoe.getInitState(), tictactoe))).getPosition());
        System.out.println(minimax_decision(tictactoe.result(sfirst, afirst),tictactoe).getPosition());
        //tictactoe.result(tictactoe.getInitState(), minimax_decision(tictactoe.getInitState(), tictactoe)).print3Board();
        //Solution sol = treeSearch(tictactoe);

*/



        State win = new State();
        win.board[0] = "X";
        win.board[1] = "X";
        win.board[2] = "O";
        win.board[3] = "O";
        win.board[4] = "O";
        win.board[5] = "e";
        win.board[6] = "e";
        win.board[7] = "e";
        win.board[8] = "e";
        }
        }
