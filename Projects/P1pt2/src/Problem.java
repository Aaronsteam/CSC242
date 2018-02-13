import java.util.HashSet;
import java.util.Scanner;
//call new state successor
public class Problem {
    private Scanner scanner = new Scanner(System.in);
    private State activeState;
    private int boardSize = 9;

    Problem() {
        activeState = new State();
        activeState = setEmpty();
    }

    private State setEmpty() {
        State s = new State();
        for(int board = 0; board < boardSize; board++) {
            for(int position = 0; position < boardSize; position++) {
                s.setBoard(board,position," ");
            }
        }
        return s;
    }

    private HashSet<Action> applicableActions(State s) {
        HashSet<Action> hApplicableActions = new HashSet<>();
        assert(s.getBoard()[0].length == boardSize);
        assert (s.getBoard().length == boardSize);

        //case where we are free to choose
        if(activeState.getActiveBoard() == 0) {
            for(int i = 0; i < boardSize; i++) {
                for(int j = 0; j < boardSize; j++) {
                    if(activeState.spotEmpty(s.getBoard()[i][j])) hApplicableActions.add(new Action(i+1, j+1));
                }
            }
        } else {
            //case where not free to choose
            for(int j = 0; j< boardSize; j++) {
                if(activeState.spotEmpty(s.getBoard()[activeState.getActiveBoard()-1][j])) {
                    hApplicableActions.add(new Action(activeState.getActiveBoard(), j+1));
                }
            }
        }
        return hApplicableActions;
    }

    public void startGame(){

        System.err.println("Welcome to the 9x9 game");
        System.err.println("Who do you want to play as (x/o)?");
        String response = scanner.nextLine();
        if(response.equalsIgnoreCase("x")){
            while(true) {
                humanMove();
                aiMove();
                activeState.displayBoard();
                if(isTerminalState(activeState)) break;
            }
        } else if(response.equalsIgnoreCase("o")){
            while(true) {
                aiMove();
                activeState.displayBoard();
                if(isTerminalState(activeState)) break;
                humanMove();

            }
        } else System.out.println("incorrect input");


        whoWon();

        System.err.println("Starting a new game...");

        System.err.println("Game over!");
    }


    private void whoWon() {
        if(isWon(activeState, "x")) System.err.println("X won!");
        else if (isWon(activeState, "o")) System.err.println("O won!");
        else System.err.println("It's a draw!");

    }
    private boolean isTerminalState(State s) { return (isDraw(s) || isWon(s, "x") || isWon(s, "o")); }

    private boolean isDraw(State s) {
        for(int board = 0; board < boardSize; board++) {
            for(int position = 0; board < boardSize; board++) {
                if(s.getBoard()[board][position].equalsIgnoreCase(" ")){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWon(State s, String player) {
        for(int board = 0; board < boardSize; board++) {
            if(checkHorizontal(s, board, player) || checkVertical(s, board, player) || checkDiagonal(s, board, player)) return true;
        }
        return false;
    }

    private boolean checkHorizontal(State s, int board, String player) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][1], s.getBoard()[board][2], player) ||
                areEqualNotEmpty(s.getBoard()[board][3], s.getBoard()[board][4], s.getBoard()[board][5], player) ||
                areEqualNotEmpty(s.getBoard()[board][6], s.getBoard()[board][7], s.getBoard()[board][8], player));

    }

    private boolean checkVertical(State s, int board, String player) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][3], s.getBoard()[board][6], player) ||
                areEqualNotEmpty(s.getBoard()[board][1], s.getBoard()[board][4], s.getBoard()[board][7], player) ||
                areEqualNotEmpty(s.getBoard()[board][2], s.getBoard()[board][5], s.getBoard()[board][8], player));
    }

    private boolean checkDiagonal(State s, int board, String player) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][4], s.getBoard()[board][8], player) ||
                areEqualNotEmpty(s.getBoard()[board][2], s.getBoard()[board][4], s.getBoard()[board][6], player));
    }

    //checks if the moves are equal and non empty
    private boolean areEqualNotEmpty(String a, String b, String c, String player) {
        return (a.equalsIgnoreCase(player) && b.equalsIgnoreCase(player) && c.equalsIgnoreCase(player) && !a.equalsIgnoreCase(" "));
    }

    private void humanMove() {
        int position;
        int board = activeState.getActiveBoard();
        assert(board >= 0 && board <= 9);

        if(board == 0) { //equals 0 means that we are free to choose
            System.err.println("Enter the board: ");
            board = scanner.nextInt();
        }

        do {
            System.err.println("Enter the position: ");
            position = scanner.nextInt();
        } while (!activeState.spotEmpty(activeState.getBoard()[board-1][position-1]));

        activeState = result(activeState, new Action(board, position));


        if(activeState.activeBoardHasFreeSpace(position)) activeState.setActiveBoard(position);
        else activeState.setActiveBoard(0);

    }

    private void aiMove() {
        /*before starting the search, our depth should be zero because the cutoff function
        forces either maxVal() or minVal() to return a value and stop recursion after depth 10.
        That's why we want to start from zero depth every time*/
        activeState.setDepth(0);
        Action decision = hMinimaxDecision(activeState);
        activeState = result(activeState, decision);

        if(activeState.activeBoardHasFreeSpace(decision.getPosition())) activeState.setActiveBoard(decision.getPosition());
        else activeState.setActiveBoard(0);


    }

    private State result(State s, Action a) {
        State sResult = s.copy();

        //adding the move on the board
        sResult.setBoard(a.getBoard()-1, a.getPosition()-1, s.getActivePlayer());

        //changing the active move (X or O)
        if(sResult.getActivePlayer().equalsIgnoreCase("X")) {
            sResult.setActivePlayer("O");
        } else if(sResult.getActivePlayer().equalsIgnoreCase("O")) {
            sResult.setActivePlayer("X");
        }

        //adding the cost of the previous state
        sResult.incrementCost();

        //incrementing depth for cutOff(State s) use
        sResult.incrementDepth();

        return sResult;
    }

    private Action hMinimaxDecision(State s){
        int max = Integer.MIN_VALUE;
        int minVal;
        Action aDecision = new Action(0,0);
        for(Action a: applicableActions(s)) {
            minVal = minVal(result(s,a));
            if(minVal > max) {
                max = minVal;
                aDecision = a;
            }
        }
        return aDecision;
    }

    private int maxVal(State s) {
        if(cutOff(s)) return eval(s);

        int v = Integer.MIN_VALUE;

        for(Action a: applicableActions(s)) {
            v = Math.max(v, minVal(result(s,a)));
        }
        return v;
    }

    private int minVal(State s) {
        if(cutOff(s)) return eval(s);

        int v = Integer.MAX_VALUE;

        for(Action a: applicableActions(s)) {
            v = Math.min(v, maxVal(result(s,a)));
        }
        return v;
    }
    private int eval(State s) { return s.getCost() + s.heuristic(); }

    private boolean cutOff(State s){ return s.getDepth() < 10; /*the depth limit is 10*/ }
}
