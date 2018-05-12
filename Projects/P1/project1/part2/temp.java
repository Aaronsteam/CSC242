
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
            if(board[nBoard][firstRow].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        lineCount = 0; //setting back to zero to check horizontal 3
        empty = false;
        for(int thirdRow = 6; thirdRow < 9; thirdRow++) {
            if(board[nBoard][thirdRow].equalsIgnoreCase(move)) lineCount++;
            if(board[nBoard][firstRow].equalsIgnoreCase(" ")) empty = true;
        }
        assert(lineCount < 3);
        if(lineCount == 2 && empty == true) num2++;

        return num2;
    }

    //same thing as above but vertically
    private int num2Vertical(String move, int nBoard) {
        int num2 = 0;
        int lineCount = 0;
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

