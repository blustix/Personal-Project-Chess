public class Piece {
    private char piece;
    private int index;
    private boolean[][] moves = new boolean[8][8];
    private boolean enpassant = false;

    public Piece (char piece, int index)
    {
      this.piece = piece;
      this.index = index;
    }

    public Piece()
    {

    }

    public char getPiece() {
      return piece;
    }

    public void setPiece(char piece) {
      this.piece = piece;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public void flush()
    {
      for (int i=0;i<8;i++)
      {
        for (int j=0;j<8;j++)
        {
        this.moves[i][j] = false;
        }
      }
    }

    public boolean[][] retrieve()
    {
      return moves;
    }

    public void setMoves(int x, int y, boolean a)
    {
      this.moves[x][y] = a;
    }
    
    public boolean validMoves(int x, int y)
    {
      return moves[x][y];
    }

    public void enpassant()
    {
      if(enpassant) enpassant = false;
      else enpassant = true;
    }

    public boolean isEN()
    {
      return enpassant;
    }
}
