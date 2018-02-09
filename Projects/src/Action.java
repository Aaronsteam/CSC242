public class Action {
    int board;
    int position;

    public Action(int board, int position) {
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
