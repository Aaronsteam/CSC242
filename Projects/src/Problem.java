import java.util.HashSet;
import java.util.Scanner;

public class Problem {
    Scanner scanner = new Scanner(System.in);
    private HashSet<Action> actions;
    private State activeState;
    private int boardSize = 9;

    HashSet<Action> applicableActions(State s) {
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
        if(spot.equalsIgnoreCase("e")) return true;
        return false;
    }

    public void startGame(){

        System.out.println("Welcome to the 9x9 game");
        System.out.println("Do you want to play first?");
        if(scanner.next().equalsIgnoreCase("y")){
            while(isTerminalState(this.activeState)) {
                humanMove();
                aiMove();
            }
        } else {
            while(isTerminalState(this.activeState)) {
                aiMove();
                humanMove();
            }
        }
    }

    private static boolean isTerminalState(State s) {

    }

    private void humanMove() {
        int position;
        int board = this.activeState.activeBoard;
        assert(board >=0 && board <= 9);
        if(board == 0) { //equals 0 means that we are free to choose
            System.out.println("Enter the board: ");
            board = scanner.nextInt();
        }
        System.out.println("Enter the position: ");
        position = scanner.nextInt();
        this.activeState = result(this.activeState, new Action(board, position));
    }

    private State result(State s, Action a) {
        State sResult = s.copy(s);
        //adding the move on the board
        sResult.setBoard(a.getBoard(), a.getPosition(), s.activePlayer);

        //changing the active move (X or O)
        if(sResult.activePlayer.equalsIgnoreCase("X")) {
            sResult.activePlayer = "O";
        } else if(sResult.activePlayer.equalsIgnoreCase("O")) {
            sResult.activePlayer = "X";
        }

        return sResult;
    }

    private void aiMove() {
        hMinimax(activeState);
        activeState = result(activeState,/*something to put here*/);
    }

    private int hMinimax(State s){
        Action a;
        if(cutOff(s)) return eval(s);
        if(s.activePlayer.equalsIgnoreCase("X")) {
            //max
            hMinimax(result(s,a));
        }
        if(s.activePlayer.equalsIgnoreCase("O")) {
            //min
            hMinimax(result(s,a));
        }
    }

    private int eval(State s) {

    }

    private boolean cutOff(State s){

    }
}
