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

    public boolean activeBoardHasFreeSpace(int board) {
        for(int position = 0; position < boardSize; position++) {
            if(spotEmpty(getBoard()[board-1][position])) return true;
        }
        return false;
    }

    public boolean spotEmpty(String spot) {
        assert(spot.equalsIgnoreCase(" ") ||
                spot.equalsIgnoreCase("O") ||
                spot.equalsIgnoreCase("X"));
        return(spot.equalsIgnoreCase(" "));
    }


    public String getActivePlayer() { return activePlayer; }

    public void setActivePlayer(String activePlayer) { this.activePlayer = activePlayer; }

    public int getActiveBoard() { return activeBoard; }

    public void setActiveBoard(int activeBoard) { this.activeBoard = activeBoard; }

    public int getDepth() { return depth; }

    public void setDepth(int depth) { this.depth = depth; }

    public void incrementDepth() { depth++; }

    public void incrementCost() { cost++; }

    public int getCost() { return cost; }

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
        boolean empty = false;
        for(int firstRow = 0; firstRow < 3; firstRow++) {
            if(board[nBoard][firstRow].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][firstRow].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        empty = false;
        for(int secondRow = 3; secondRow < 6; secondRow++) {
            if(board[nBoard][secondRow].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][secondRow].equalsIgnoreCase(" ")) empty = true;
        }

        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 3
        empty = false;
        for(int thirdRow = 6; thirdRow < 9; thirdRow++) {
            if(board[nBoard][thirdRow].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][thirdRow].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        return num2;
    }

    //same thing as above but vertically
    private int num2Vertical(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
        boolean empty = false;
        for(int firstColumn = 0; firstColumn < 7; firstColumn += 3) {
            if(board[nBoard][firstColumn].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][firstColumn].equalsIgnoreCase(" ")) empty = true;

        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        empty = false;
        for(int secondColumn = 1; secondColumn < 8; secondColumn += 3) {
            if(board[nBoard][secondColumn].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][secondColumn].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 3
        empty = false;
        for(int thirdColumn = 2; thirdColumn < 9; thirdColumn += 3) {
            if(board[nBoard][thirdColumn].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][thirdColumn].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        return num2;

    }

    //same thing as above but diagonally
    private int num2Diagonal(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
        boolean empty = false;
        for(int lrDiagonal = 0; lrDiagonal < 9; lrDiagonal += 4) {
            if(board[nBoard][lrDiagonal].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][lrDiagonal].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 2
        empty = false;
        for(int rlDiagonal = 2; rlDiagonal < 7; rlDiagonal += 2) {
            if(board[nBoard][rlDiagonal].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][rlDiagonal].equalsIgnoreCase(" ")) empty = true;

        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        return num2;
    }

    public String[][] getBoard() { return board; }


    public void setBoard(int board, int position, String move) { this.board[board][position] = move; }

    public State copy(){
        State sCopy = new State();
        for(int board = 0; board < boardSize; board++) {
            System.arraycopy(this.board[board], 0, sCopy.board[board], 0, boardSize);
        }
        sCopy.activePlayer = this.activePlayer;
        sCopy.activeBoard = this.activeBoard;
        return sCopy;
    }

    public void displayBoard() {
        displayFirstThree();
        System.out.println();
        displayMiddleThree();
        System.out.println();
        displayLastThree();
        System.out.println();
    }
    private void displayFirstThree() {
        for(int line = 0; line < 3; line++) {
            for(int boardNum = 0; boardNum < 3; boardNum++) {
                displayLine(boardNum, line);
                System.out.print("    ");
            }
            System.out.println();
        }
    }

    private void displayMiddleThree() {
        for(int line = 0; line < 3; line++) {
            for(int boardNum = 3; boardNum < 6; boardNum++) {
                displayLine(boardNum, line);
                System.out.print("    ");
            }
            System.out.println();
        }
    }

    private void displayLastThree() {
        for(int line = 0; line < 3; line++) {
            for(int boardNum = 6; boardNum < 9; boardNum++) {
                displayLine(boardNum, line);
                System.out.print("    ");
            }
            System.out.println();
        }
    }

    private void displayLine(int boardNum, int line) {
        int[] bound;
        bound = getBound(line);
        for(int count = bound[0]; count < bound[1]; count++) {
            System.out.print(board[boardNum][count]);
            System.out.print("|");
        }
        System.out.print(board[boardNum][bound[1]]);
    }

   private int[] getBound(int line) {
       int[] bound = new int[2];
       if(line == 0) {
           bound[0] = 0;
           bound[1] = 2;
       } else if(line == 1) {
           bound[0] = 3;
           bound[1] = 5;
       } else if(line == 2) {
           bound[0] = 6;
           bound[1] = 8;
       } else {
           System.out.println("line = " + line);
           bound[0] = -1;
           bound[1] = -1;
       }
        assert(bound[0] != -1 && bound[1] != -1);
        return bound;
    }
}


