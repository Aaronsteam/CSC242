public class State {
    private String[][] board;
    private String activePlayer;
    private int activeBoard;
    private int cost = 0;
    private int depth = 0;
    private int boardSize = 9;

    State() {
        board = new String[9][9];
        activePlayer = "X";
        activeBoard = 0; //0 value here means that we are free to choose whichever board
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getActiveBoard() {
        return activeBoard;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void incrementDepth() {
        depth++;
    }

    public void incrementCost() {
        cost++;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int heuristic() {
        int heuristic = 0;
        for(int board = 0; board < boardSize; board++) {
            heuristic += num2Horizontal("X", board) + num2Vertical("X", board) + num2Diagonal("X", board) - num2Horizontal("O", board) - num2Vertical("O", board) - num2Diagonal("O", board);
        }
        return heuristic;
    }

    //checks how many Xs or Os (String move) we can find in board number "nBoard" horizontally
    private int num2Horizontal(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
        for(int firstRow = 0; firstRow < 3; firstRow++) {
            if(board[nBoard][firstRow].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        for(int secondRow = 3; secondRow < 6; secondRow++) {
            if(board[nBoard][secondRow].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        lineCount = 0; //setting back to zero to check horizontal 3
        for(int thirdRow = 6; thirdRow < 9; thirdRow++) {
            if(board[nBoard][thirdRow].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        return num2;
    }

    //same thing as above but vertically
    private int num2Vertical(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
        for(int firstColumn = 0; firstColumn < 7; firstColumn += 3) {
            if(board[nBoard][firstColumn].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        for(int secondColumn = 1; secondColumn < 8; secondColumn += 3) {
            if(board[nBoard][secondColumn].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        lineCount = 0; //setting back to zero to check horizontal 3
        for(int thirdColumn = 2; thirdColumn < 9; thirdColumn += 3) {
            if(board[nBoard][thirdColumn].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        return num2;

    }

    //same thing as above but diagonally
    private int num2Diagonal(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
        for(int lrDiagonal = 0; lrDiagonal < 9; lrDiagonal += 4) {
            if(board[nBoard][lrDiagonal].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        for(int rlDiagonal = 2; rlDiagonal < 7; rlDiagonal += 2) {
            if(board[nBoard][rlDiagonal].equalsIgnoreCase(move)) lineCount++;
        }
        assert(lineCount < 3);
        if(lineCount == 2) num2++;

        return num2;
    }

    public String[][] getBoard() {
        return board;
    }


    public void setBoard(int board, int position, String move) {
        this.board[board][position] = move;
    }

    public State copy(State s){
        State sCopy = new State();
        sCopy.board = s.board;
        sCopy.activePlayer = s.activePlayer;
        sCopy.activeBoard = s.activeBoard;
        return sCopy;

    }

    public void displayBoard() {
        for(int board = 0; board < boardSize; board++) {
            for(int position = 0; position < boardSize; position++) {
                System.out.println(this.board[board][position]);
                if((position + 1) % 3 == 0) System.out.println();
                else System.out.print("|");
            }
            System.out.println();
            System.out.println();
        }
    }
}

