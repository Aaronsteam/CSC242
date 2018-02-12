import java.util.HashSet;
import java.util.Scanner;
//call new state successor
public class Problem {
    private Scanner scanner = new Scanner(System.in);
    private State activeState;
    private int numPositions = 9;

    Problem() {
        activeState = new State();
        activeState = setEmpty();
    }

    private State setEmpty() {
        State s = new State();

        for(int position = 0; position < numPositions; position++) {
            s.setBoard(position," ");
        }
        return s;
    }

    private HashSet<Action> applicableActions(State s) {
        HashSet<Action> hApplicableActions = new HashSet<>();

        assert (s.getBoard().length == numPositions);

        //case where we are free to choose
        assert(s.getBoard().length == 9);
        for(int j = 0; j < s.getBoard().length; j++) {
            if(s.spotEmpty(s.getBoard()[j])) {
                hApplicableActions.add(new Action(j+1));
            }
        }
        return hApplicableActions;
    }



    public void startGame(){

        System.err.println("Welcome to the 3x3 game");
        System.err.println("Do you want to play first?");
        if(scanner.nextLine().equalsIgnoreCase("y")){
            while(true) {
                humanMove();
                aiMove();
                activeState.displayBoard();
                if(isTerminalState(activeState)) break;
            }
        } else {
            while(true) {
                aiMove();
                activeState.displayBoard();
                if(isTerminalState(activeState)) break;
                humanMove();
            }
        }

        System.err.println("Game over!");
    }

    private boolean isTerminalState(State s) { return (isDraw(s) || isWon(s)); }

    private boolean isDraw(State s) {

            for(int position = 0; position < numPositions; position++) {
                if(s.getBoard()[position].equalsIgnoreCase(" ")) return false;
            }
        return true;
    }

    private boolean isWon(State s) {

        return (checkHorizontal(s) || checkVertical(s) || checkDiagonal(s));

    }

    private boolean checkHorizontal(State s) {
        return(areEqualNotEmpty(s.getBoard()[0], s.getBoard()[1], s.getBoard()[2]) ||
                areEqualNotEmpty(s.getBoard()[3], s.getBoard()[4], s.getBoard()[5]) ||
                areEqualNotEmpty(s.getBoard()[6], s.getBoard()[7], s.getBoard()[8]));

    }

    private boolean checkVertical(State s) {
        return(areEqualNotEmpty(s.getBoard()[0], s.getBoard()[3], s.getBoard()[6]) ||
                areEqualNotEmpty(s.getBoard()[1], s.getBoard()[4], s.getBoard()[7]) ||
                areEqualNotEmpty(s.getBoard()[2], s.getBoard()[5], s.getBoard()[8]));
    }

    private boolean checkDiagonal(State s) {
        return(areEqualNotEmpty(s.getBoard()[0], s.getBoard()[4], s.getBoard()[8]) ||
                areEqualNotEmpty(s.getBoard()[2], s.getBoard()[4], s.getBoard()[6]));
    }

    //checks if the moves are equal and non empty
    private boolean areEqualNotEmpty(String a, String b, String c) {
        return (a.equalsIgnoreCase(b) && a.equalsIgnoreCase(c) && !a.equalsIgnoreCase(" "));
    }

    private void humanMove() {
        int position;
        do {
            System.err.println("Enter the position: ");
            position = scanner.nextInt();
        } while (!activeState.spotEmpty(activeState.getBoard()[position-1]));

        activeState = result(activeState, new Action(position));

    }

    private void aiMove() {
        /*before starting the search, our depth should be zero because the cutoff function
        forces either maxVal() or minVal() to return a value and stop recursion after depth 10.
        That's why we want to start from zero depth every time*/

        Action decision = minimaxDecision(activeState);
        activeState = result(activeState, decision);

    }

    private State result(State s, Action a) {
        State sResult = s.copy();

        //adding the move on the board
        sResult.setBoard(a.getPosition()-1, s.getActivePlayer());

        //changing the active move (X or O)
        if(sResult.getActivePlayer().equalsIgnoreCase("X")) {
            sResult.setActivePlayer("O");
        } else if(sResult.getActivePlayer().equalsIgnoreCase("O")) {
            sResult.setActivePlayer("X");
        }


        return sResult;
    }

    private Action minimaxDecision(State s){
        int max = Integer.MIN_VALUE;
        int minVal;
        Action aDecision = new Action(0);
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
        if(isTerminalState(s)) return utility(s);

        int v = Integer.MIN_VALUE;

        for(Action a: applicableActions(s)) {
            v = Math.max(v, minVal(result(s,a)));
        }
        return v;
    }

    private int minVal(State s) {
        if(isTerminalState(s)) return utility(s);

        int v = Integer.MAX_VALUE;

        for(Action a: applicableActions(s)) {
            v = Math.min(v, maxVal(result(s,a)));
        }
        return v;
    }

    private int utility(State s) {
        //X is maximizing, O is minimizing
        if ((!s.spotEmpty(s.getBoard()[0]) && s.getBoard()[0].equalsIgnoreCase(s.getBoard()[1]) && s.getBoard()[1].equalsIgnoreCase(s.getBoard()[2]))) {
            if (s.getBoard()[0].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[3]) && s.getBoard()[3].equalsIgnoreCase(s.getBoard()[4]) && s.getBoard()[4].equalsIgnoreCase(s.getBoard()[5]))) {
            if(s.getBoard()[0].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[6]) && s.getBoard()[6].equalsIgnoreCase(s.getBoard()[7]) && s.getBoard()[7].equalsIgnoreCase(s.getBoard()[8]))) {
            if(s.getBoard()[6].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[0]) && s.getBoard()[0].equalsIgnoreCase(s.getBoard()[3]) && s.getBoard()[1].equalsIgnoreCase(s.getBoard()[6]))) {
            if (s.getBoard()[0].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[1]) && s.getBoard()[1].equalsIgnoreCase(s.getBoard()[4]) && s.getBoard()[4].equalsIgnoreCase(s.getBoard()[7]))) {
            if(s.getBoard()[0].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[2]) && s.getBoard()[2].equalsIgnoreCase(s.getBoard()[5]) && s.getBoard()[5].equalsIgnoreCase(s.getBoard()[8]))) {
            if(s.getBoard()[6].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[2]) && s.getBoard()[0].equalsIgnoreCase(s.getBoard()[4]) && s.getBoard()[4].equalsIgnoreCase(s.getBoard()[8]))) {
            if(s.getBoard()[0].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        if ((!s.spotEmpty(s.getBoard()[2]) && s.getBoard()[2].equalsIgnoreCase(s.getBoard()[4]) && s.getBoard()[4].equalsIgnoreCase(s.getBoard()[6]))) {
            if(s.getBoard()[2].equalsIgnoreCase("x")) return 1;
            return -1;
        }
        return 0;
    }
}
