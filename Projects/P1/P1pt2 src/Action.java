public class Action {
    private int board;
    private int position;

    Action(int board, int position) {
        assert(board >= 0 && board <= 9 && position >= 0 && position <=9);
        this.board = board;
        this.position = position;
    }

    public int getBoard() {
        return board;
    }

    public int getPosition(){
        return position;
    }
}
