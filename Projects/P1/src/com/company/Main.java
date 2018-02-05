package com.company;
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
    public static void main(String[] args) {
	// write your code here
        Problem tictactoe = new Problem();
        Action[] a = new Action[9];

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
        System.out.println(tictactoe.isGoalState(win));
        for(int i =0; i<9; i++) {
            a[i] = new Action(String.valueOf(i));
            tictactoe.addAction(a[i]);
        }



        Solution sol = treeSearch(tictactoe);
    }


}
