public class State {
    String[][] board;
    String activePlayer;
    int activeBoard; //0 value here means that we are free to choose whichever board

    public String[][] getBoard() {
        return board;
    }


    public void setBoard(int board, int position, String move) {
        this.board[board-1][position-1] = move;
    }

    public State copy(State s){
        State sCopy = new State();
        sCopy.board = s.board;
        sCopy.activePlayer = s.activePlayer;
        sCopy.activeBoard = s.activeBoard;
        return sCopy;

    }
}

