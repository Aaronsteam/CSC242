public class State {
    private String[] board;
    private String activePlayer;

    State() {
        board = new String[9];
        activePlayer = "X";
    }

    public boolean spotEmpty(String spot) {
        assert(spot.equalsIgnoreCase(" ") ||
                spot.equalsIgnoreCase("O") ||
                spot.equalsIgnoreCase("X"));
        return(spot.equalsIgnoreCase(" "));
    }

    public String getActivePlayer() { return activePlayer; }

    public void setActivePlayer(String activePlayer) { this.activePlayer = activePlayer; }

    public String[] getBoard() { return board; }

    public void setBoard(int position, String move) { board[position] = move; }

    public State copy(){
        State sCopy = new State();

        System.arraycopy(board, 0, sCopy.board, 0, 9);

        sCopy.activePlayer = activePlayer;

        return sCopy;
    }

    public void displayBoard() {
        for(int i = 0 ; i < 9; i++) {
            System.out.print(board[i]);
            if((i + 1) % 3 == 0) System.out.println();
            else System.out.print("|");
        }
    }
}


