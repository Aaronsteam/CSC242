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
                s.setBoard(board,position,"e");
            }
        }
        return s;
    }

    private HashSet<Action> applicableActions(State s) {
        HashSet<Action> hApplicableActions = new HashSet<>();
        assert(s.getBoard()[0].length == 9);
        assert (s.getBoard().length ==9);

        for(int i = 0; i< boardSize; i++) {
            for(int j = 0; j< boardSize; j++) {
                if(spotEmpty(s.getBoard()[i][j])) {
                    hApplicableActions.add(new Action(i+1, j+1));
                }
            }
        }
        return hApplicableActions;
    }

    private static boolean spotEmpty(String spot) {
        assert(spot.equalsIgnoreCase("e") || spot.equalsIgnoreCase("x") || spot.equalsIgnoreCase("y"));
        return(spot.equalsIgnoreCase("e"));
    }

    public void startGame(){

        System.out.println("Welcome to the 9x9 game");
        System.out.println("Do you want to play first?");
        if(scanner.next().equalsIgnoreCase("y")){
            while(isTerminalState(activeState)) {
                humanMove();
                aiMove();
                activeState.displayBoard();
            }
        } else {
            while(isTerminalState(activeState)) {
                aiMove();
                humanMove();
                activeState.displayBoard();
            }
        }
    }

    private boolean isTerminalState(State s) {
        return (isDraw(s) || isWon(s));
    }

    private boolean isDraw(State s) {
        for(int board = 0; board < boardSize; board++) {
            for(int position = 0; board < boardSize; board++) {
                if(s.getBoard()[board][position].equalsIgnoreCase("e")){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWon(State s) {
        for(int board = 0; board < boardSize; board++) {
            if(checkHorizontal(s, board) || checkVertical(s, board) || checkDiagonal(s, board)) return true;
        }
        return false;
    }

    private boolean checkHorizontal(State s, int board) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][1], s.getBoard()[board][2]) ||
                areEqualNotEmpty(s.getBoard()[board][3], s.getBoard()[board][4], s.getBoard()[board][5]) ||
                areEqualNotEmpty(s.getBoard()[board][6], s.getBoard()[board][7], s.getBoard()[board][8]));

    }

    private boolean checkVertical(State s, int board) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][3], s.getBoard()[board][6]) ||
                areEqualNotEmpty(s.getBoard()[board][1], s.getBoard()[board][4], s.getBoard()[board][7]) ||
                areEqualNotEmpty(s.getBoard()[board][2], s.getBoard()[board][5], s.getBoard()[board][8]));
    }

    private boolean checkDiagonal(State s, int board) {
        return(areEqualNotEmpty(s.getBoard()[board][0], s.getBoard()[board][4], s.getBoard()[board][8]) ||
                areEqualNotEmpty(s.getBoard()[board][2], s.getBoard()[board][4], s.getBoard()[board][6]));
    }

    //checks if the moves are equal and non empty
    private boolean areEqualNotEmpty(String a, String b, String c) {
        return (a.equalsIgnoreCase(b) && a.equalsIgnoreCase(c) && !a.equalsIgnoreCase("e"));
    }

    private void humanMove() {
        int position;
        int board = this.activeState.getActiveBoard();
        assert(board >= 0 && board <= 9);

        if(board == 0) { //equals 0 means that we are free to choose
            System.out.println("Enter the board: ");
            board = scanner.nextInt();
        }
        System.out.println("Enter the position: ");
        position = scanner.nextInt();
        this.activeState = result(this.activeState, new Action(board, position));
    }

    private void aiMove() {
        /*before starting the search, our depth should be zero because the cutoff function
        forces either maxVal() or minVal() to return a value and stop recursion after depth 10.
        That's why we want to start from zero depth every time*/
        activeState.setDepth(0);
        activeState = result(activeState,hMinimaxDecision(activeState));
    }

    private State result(State s, Action a) {
        State sResult = s.copy(s);

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
