import java.util.*;
import java.io.*;

public class Main {
  public static int [][] EvalKing = {
             {2 , 3, 1, 0, 0, 1, 3, 2},
             {2 , 2, 0, 0, 0, 0, 2, 2},
             {-1,-2,-2,-3,-3,-2,-2,-1},
             {-2,-3,-3,-4,-4,-3,-3,-2},
             {-3,-4,-4,-5,-5,-4,-4,-3},
             {-3,-4,-4,-5,-5,-4,-4,-3},
             {-3,-4,-4,-5,-5,-4,-4,-3},
             {-3,-4,-4,-5,-5,-4,-4,-3}};
  public static int [][] EvalQueen = {
             {-3,-2,-2,-1,-1,-2,-2,-3},
             {-2,0,0,0,0,1,0,-1},
             {-2,0,1,1,1,1,0,-2},
             {-1,0,1,1,1,1,0,0},
             {-1,0,1,1,1,1,0,-1},
             {-2,0,1,1,1,1,0,-2},
             {-2,0,0,0,0,0,0,-2},
             {-3,-2,-2,-1,-1,-2,-2,-3}};
  public static int [][] EvalRook = {
             {0,0,0,1,1,0,0,0},
             {-1,0,0,0,0,0,0,-1},
             {-1,0,0,0,0,0,0,-1},
             {-1,0,0,0,0,0,0,-1},
             {-1,0,0,0,0,0,0,-1},
             {-1,0,0,0,0,0,0,-1},
             {1,2,2,2,2,2,2,1},
             {0,0,0,0,0,0,0,0}};
  public static int [][] EvalBishop = {
             {-3,-2,-2,-2,-2,-2,-2,-3},
             {-2,1,0,0,0,0,1,-2},
             {-2,2,2,2,2,2,2,-2},
             {-2,0,2,2,2,2,0,-2},
             {-2,1,1,2,2,1,1,-2},
             {-2,0,1,2,2,1,0,-2},
             {-2,0,0,0,0,0,0,-2},
             {-3,-2,-2,-2,-2,-2,-2,-3}};
  public static int [][] EvalKnight = {
             {-6,-5,-4,-4,-4,-4,-5,-6},
             {-5,-3,0,1,1,0,-3,-5},
             {-4,1,2,2,2,2,1,-4},
             {-3,0,2,3,3,2,0,-3},
             {-3,1,2,3,3,2,1,-3},
             {-4,0,2,2,2,2,0,-4},
             {-5,-3,0,0,0,0,-3,-5},
             {-6,-5,-4,-4,-4,-4,-5,-6}};
  public static int [][] EvalPawn = {
             {0,0,0,0,0,0,0,0},
             {1,2,2,-3,-3,2,2,1},
             {1,-1,-2,0,0,-2,-1,1},
             {0,0,0,3,3,0,0,0},
             {1,1,2,3,3,2,1,1},
             {2,2,3,4,4,3,2,2},
             {6,6,6,6,6,6,6,6},
             {0,0,0,0,0,0,0,0}};
  public static Piece[][] board = new Piece[8][8];

  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for (int i = 0; i < 8; i++) {
      board[i] = new Piece[8];
      for (int j = 0; j < 8; j++) {
        board[i][j] = new Piece('0', 0);
      }
    }
    // Initializing Board

    start(board);

    getMoves();
    int whitePromo = 2;
    int blackPromo = 2;

    
    game: while (true) // game start
    {
      
      display(board);
      getMoves();
      
      while (true) {
        System.out.println("White to move");
        if (whiteCheck()) {
          System.out.println("You are in check!");
        }
        StringTokenizer st = new StringTokenizer(in.readLine());
        char cPiece = st.nextToken().charAt(0);
        int cIndex = Integer.parseInt(st.nextToken());
        
        if (isWhite(cPiece)) {
          System.out.println("Move to?");
          StringTokenizer xd = new StringTokenizer(in.readLine());

          int x = Integer.parseInt(xd.nextToken());
          int y = Integer.parseInt(xd.nextToken());
          int oldx = 0;
          int oldy = 0;

          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              if (board[i][j].getIndex() == cIndex && board[i][j].getPiece() == cPiece) {
                oldx = i;
                oldy = j;
              }
            }
          }
          
          //System.out.println(oldx + " " + oldy);

          char capturepiece = board[x][y].getPiece();
          int captureindex = board[x][y].getIndex();   
          
          if (board[oldx][oldy].validMoves(x,y)) {
            
            board[oldx][oldy].setIndex(0);
            board[oldx][oldy].setPiece('0');
            board[x][y].setIndex(cIndex);
            board[x][y].setPiece(cPiece);
            board[oldx][oldy].flush();
            board[x][y].flush();
            getMoves();
            

            if (whiteCheck()) {
              System.out.println("Cannot put self in check");
              board[x][y].setIndex(captureindex);
              board[x][y].setPiece(capturepiece);
              board[oldx][oldy].setIndex(cIndex);
              board[oldx][oldy].setPiece(cPiece);
            } else {
              if (board[x][y].getPiece() == 'p' && x == 0) {
                board[x][y].setPiece('q');
                board[x][y].setIndex(whitePromo);
                whitePromo++;
                board[oldx][oldy].flush();
                board[x][y].flush();
                break;
              }
              if (cPiece == 'p')
              {
                if(board[x+1][y].getPiece() == 'P' && board[x+1][y].isEN())
                {
                  board[x][y+1].setPiece('0');
                  board[x][y+1].setIndex(0);
                  board[x][y+1].flush();
                }
              }
              board[oldx][oldy].flush();
              board[x][y].flush();
              if(cPiece == 'p' && x == 4 && oldx == 6)
              {
                board[x][y].enpassant();
              }
              break;
            }
            
          }

        } else {
          System.out.println("Invalid");
          continue;
        }
    
      }
      getMoves();
      display(board);
      LinkedList<Eval> AIMoves = aiCalc();
      
      while (true) {
        System.out.println("Black to move");
        if(AIMoves.isEmpty())
        {
          System.out.println("You win");
          break game;
        }
        Eval move = AIMoves.poll();
        System.out.println(move.PI());
        String[] PI = move.PI().split(" ");
        
        char cPiece = PI[0].charAt(0);
        int cIndex = Integer.parseInt(PI[1]);
        
        if (isBlack(cPiece)) {
          System.out.println("Move to?");
          

          String[] xy = move.xy().split(" ");

          int x = Integer.parseInt(xy[0]);
          int y = Integer.parseInt(xy[1]);
          int oldx = 0;
          int oldy = 0;

          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              if (board[i][j].getIndex() == cIndex && board[i][j].getPiece() == cPiece) {
                oldx = i;
                oldy = j;
              }
            }
          }
          
          //System.out.println(oldx + " " + oldy);

          char capturepiece = board[x][y].getPiece();
          int captureindex = board[x][y].getIndex();   
          
          if (board[oldx][oldy].validMoves(x,y)) {
            
            board[oldx][oldy].setIndex(0);
            board[oldx][oldy].setPiece('0');
            board[x][y].setIndex(cIndex);
            board[x][y].setPiece(cPiece);
            board[oldx][oldy].flush();
            board[x][y].flush();
            getMoves();

            if (blackCheck()) {
              System.out.println("Cannot put self in check");
              board[x][y].setIndex(captureindex);
              board[x][y].setPiece(capturepiece);
              board[oldx][oldy].setIndex(cIndex);
              board[oldx][oldy].setPiece(cPiece);
             
          } else {
              if (board[x][y].getPiece() == 'P' && x == 0) {
                board[x][y].setPiece('Q');
                board[x][y].setIndex(whitePromo);
                blackPromo++;
                board[oldx][oldy].flush();
                board[x][y].flush();
                break;
              }
            if (cPiece == 'P')
              {
                if(board[x-1][y].getPiece() == 'p' && board[x-1][y].isEN())
                {
                  board[x][y-1].setPiece('0');
                  board[x][y-1].setIndex(0);
                  board[x][y-1].flush();
                }
              }
            if(cPiece == 'P' && x == 3 && oldx == 1)
            {
              board[x][y].enpassant();
            }
            board[oldx][oldy].flush();
            board[x][y].flush();
            break;
          }
          
        }

      } else {
        System.out.println("Invalid");
        continue;
      }
    }
    
  }
  
  }
  
  public static LinkedList<Eval> aiCalc()
  {
    LinkedList<Eval> All = new LinkedList <Eval>();
    for (int i=0;i<8;i++)
    {
      for (int j=0;j<8;j++)
      {
        if(isBlack(board[i][j].getPiece()))
        {
          boolean[][] temp = board[i][j].retrieve();
          int [][] posVal = posEval(board[i][j].getPiece());
          
          for (int k=0;k<8;k++)
          {
            for (int l=0;l<8;l++)
            {
              if(temp[k][l])
              {
                if(board[k][l].getPiece() == 'p')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 10 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                if(board[k][l].getPiece() == 'b')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 30 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                if(board[k][l].getPiece() == 'n')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 30 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                if(board[k][l].getPiece() == 'r')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 50 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                if(board[k][l].getPiece() == 'q')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 90 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                if(board[k][l].getPiece() == 'k')
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 900 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
                else
                {
                  String xy = Integer.toString(k) + " " + Integer.toString(l);
                  int value = 0 + posVal[k][l];
                  String PI = Character.toString(board[i][j].getPiece()) + " " + Integer.toString(board[i][j].getIndex());
                  All.add(new Eval(xy, value, PI));
                }
              }
            }
          }
        }
      }
    }
    Collections.sort(All, Collections.reverseOrder());
    return All;
  }

  public static int[][] posEval(char piece)
  {
    
    if (piece == 'K')
    {
      return EvalKing;    
    }
    if (piece == 'Q')
    {
      return EvalQueen;
    }
    if(piece == 'R')
    {
      return EvalRook;
    }
    if(piece == 'B')
    {
      return EvalBishop;
    }
    if(piece == 'N')
    {
      return EvalKnight;
    }
    else
    {
      return EvalPawn;
    }
  }

  public static boolean whiteCheck() {
    int kx = 0;
    int ky = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        int piece = (int) board[i][j].getPiece();
        if (piece == 107) {
          kx = i;
          ky = j;
        }
      }
    }
    for  (int i=0;i<8;i++)
    {
      for (int j=0;j<8;j++)
      {
        if (board[i][j].validMoves(kx, ky)){
          return true;
        } 
      }
    }
    return false;
    
    
  }

  public static boolean blackCheck() {
    int kx = 0;
    int ky = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        int piece = (int) board[i][j].getPiece();
      
        if (piece == 75) {
          kx = i;
          ky = j;
        }
      }
    }
    for  (int i=0;i<8;i++)
    {
      for (int j=0;j<8;j++)
      {
        if (board[i][j].validMoves(kx, ky)){
          return true;
        } 
      }
    }
    return false;
  }

  public static void getMoves() // Support method to get all valid moves
  {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if (board[x][y].getPiece() == 'R') // Black rook
        {
          blackRook(x, y);
        }
        if (board[x][y].getPiece() == 'r') // white Rook
        {
          whiteRook(x, y);
        }

        if (board[x][y].getPiece() == 'B') // Black bishop
        {
          blackBishop(x, y);
        }

        if (board[x][y].getPiece() == 'b') // White bishop
        {
          whiteBishop(x, y);
        }

        if (board[x][y].getPiece() == 'Q') // Black Queen
        {
          blackQueen(x, y);
        }
        if (board[x][y].getPiece() == 'q') // White Queen
        {
          whiteQueen(x, y);
        }

        if (board[x][y].getPiece() == 'N') // Black Knight
        {
          blackKnight(x, y);
        }
        if (board[x][y].getPiece() == 'n') // White Knight
        {
          whiteKnight(x, y);
        }

        if (board[x][y].getPiece() == 'P') // Black Pawn
        {
          blackPawn(x, y);
        }
        if (board[x][y].getPiece() == 'p') // White Pawn
        {
          whitePawn(x, y);
        }
        if (board[x][y].getPiece() == 'K') // Black King
        {
          blackKing(x, y);
        }
        if (board[x][y].getPiece() == 'k') {
          whiteKing(x, y);
        }
      }
    }

  }

  public static void whiteKing(int x, int y) {
    if (x + 1 < 8) {
      if (isClear(board[x + 1][y].getPiece()) || isBlack(board[x + 1][y].getPiece())) {
        board[x][y].setMoves(x+1, y,true);
      }
    }
    if (x - 1 >= 0) {
      if (isClear(board[x - 1][y].getPiece()) || isBlack(board[x - 1][y].getPiece())) {
        board[x][y].setMoves(x-1, y,true);
      }
    }

    if (y + 1 < 8) {
      if (isClear(board[x][y + 1].getPiece()) || isBlack(board[x][y + 1].getPiece())) {
        board[x][y].setMoves(x, y+1,true);
      }
    }
    if (y - 1 >= 0) {
      if (isClear(board[x][y - 1].getPiece()) || isBlack(board[x][y - 1].getPiece())) {
        board[x][y].setMoves(x, y-1,true);
      }
    }

    if (x + 1 < 8 && y + 1 < 8) {
      if (isClear(board[x + 1][y + 1].getPiece()) || isBlack(board[x + 1][y + 1].getPiece())) {
        board[x][y].setMoves(x+1, y+1,true);
      }
    }
    if (x + 1 < 8 && y - 1 >= 0) {
      if (isClear(board[x + 1][y - 1].getPiece()) || isBlack(board[x + 1][y - 1].getPiece())) {
        board[x][y].setMoves(x+1, y-1,true);
      }
    }

    if (x - 1 >= 0 && y + 1 < 8) {
      if (isClear(board[x - 1][y + 1].getPiece()) || isBlack(board[x - 1][y + 1].getPiece())) {
        board[x][y].setMoves(x-1, y+1,true);
      }
    }
    if (x - 1 >= 0 && y - 1 >= 0) {
      if (isClear(board[x - 1][y - 1].getPiece()) || isBlack(board[x - 1][y - 1].getPiece())) {
        board[x][y].setMoves(x-1, y-1,true);
      }
    }
  }

  public static void blackKing(int x, int y) {
    
    if (x + 1 < 8) {
      if (isClear(board[x + 1][y].getPiece()) || isWhite(board[x + 1][y].getPiece())) {
        board[x][y].setMoves(x + 1, y,true);
      }
    }
    if (x - 1 >= 0) {
      if (isClear(board[x - 1][y].getPiece()) || isWhite(board[x - 1][y].getPiece())) {
        board[x][y].setMoves(x-1, y,true);
      }
    }

    if (y + 1 < 8) {
      if (isClear(board[x][y + 1].getPiece()) || isWhite(board[x][y + 1].getPiece())) {
        board[x][y].setMoves(x, y+1,true);
      }
    }
    if (y - 1 >= 0) {
      if (isClear(board[x][y - 1].getPiece()) || isWhite(board[x][y - 1].getPiece())) {
        board[x][y].setMoves(x, y-1,true);
      }
    }

    if (x + 1 < 8 && y + 1 < 8) {
      if (isClear(board[x + 1][y + 1].getPiece()) || isWhite(board[x + 1][y + 1].getPiece())) {
        board[x][y].setMoves(x+1, y+1,true);
      }
    }
    if (x + 1 < 8 && y - 1 >= 0) {
      if (isClear(board[x + 1][y - 1].getPiece()) || isWhite(board[x + 1][y - 1].getPiece())) {
        board[x][y].setMoves(x+1, y-1,true);
      }
    }

    if (x - 1 >= 0 && y + 1 < 8) {
      if (isClear(board[x - 1][y + 1].getPiece()) || isWhite(board[x - 1][y + 1].getPiece())) {
        board[x][y].setMoves(x-1, y+1,true);
      }
    }
    if (x - 1 >= 0 && y - 1 >= 0) {
      if (isClear(board[x - 1][y - 1].getPiece()) || isWhite(board[x - 1][y - 1].getPiece())) {
        board[x][y].setMoves(x-1, y-1,true);
      }
    }
    
  }

  public static void whitePawn(int x, int y) {
    
    if (x == 6) {
      if (isClear(board[x - 2][y].getPiece())) {
        board[x][y].setMoves(x-2, y,true);
      }
    }
    if (x - 1 >= 0 && isClear(board[x - 1][y].getPiece())) {
     board[x][y].setMoves(x-1, y,true);
    }
    if (x - 1 >= 0 && y - 1 >= 0) {
      if (isBlack(board[x - 1][y - 1].getPiece())) {
        board[x][y].setMoves(x-1, y-1,true);
      }
    }
    if (x - 1 >= 0 && y + 1 < 8) {
      if (isBlack(board[x - 1][y + 1].getPiece())) {
        board[x][y].setMoves(x-1, y+1,true);
      }
    }
    if (x == 3 && y + 1 < 8 && board[x][y + 1].isEN()) {
      board[x][y].setMoves(x, y+1,true);
    }
    if (x == 3 && y - 1 >= 0 && board[x][y - 1].isEN()) {
      board[x][y].setMoves(x, y-1,true);
    }
    
  }

  public static void blackPawn(int x, int y) {
   
    if (x == 1) {
      if (isClear(board[x + 2][y].getPiece())) {
        board[x][y].setMoves(x+2, y,true);
      }
    }
    if (x + 1 < 8 && isClear(board[x + 1][y].getPiece())) {
      board[x][y].setMoves(x+1, y,true);
    }
    if (x + 1 < 8 && y + 1 < 8) {
      if (isBlack(board[x + 1][y + 1].getPiece())) {
        board[x][y].setMoves(x+1, y+1,true);
      }
    }
    if (x + 1 < 8 && y - 1 >= 0) {
      if (isBlack(board[x + 1][y - 1].getPiece())) {
        board[x][y].setMoves(x+1, y-1,true);
      }
    }
    if (x == 4 && x + 1 < 8 && y + 1 < 8 && board[x][y + 1].isEN()) {
      board[x][y].setMoves(x, y+1,true);
    }
    if (x == 4 && x + 1 < 8 && y - 1 >= 0 && board[x][y - 1].isEN()) {
      board[x][y].setMoves(x, y-1,true);
    }
    
  }

  public static void whiteKnight(int x, int y) {
    
    if (x + 1 < 8 && y + 2 < 8) {
      if (isBlack(board[x + 1][y + 2].getPiece()) || isClear(board[x + 1][y + 2].getPiece())) {
        board[x][y].setMoves(x+1, y+2,true);
      }
    }
    if (x + 1 < 8 && y - 2 >= 0) {
      if (isBlack(board[x + 1][y - 2].getPiece()) || isClear(board[x + 1][y - 2].getPiece())) {
        board[x][y].setMoves(x+1, y-2,true);
      }
    }
    if (x - 1 >= 0 && y + 2 < 8) {
      if (isBlack(board[x - 1][y + 2].getPiece()) || isClear(board[x - 1][y + 2].getPiece())) {
        board[x][y].setMoves(x, y-1,true);
      }
    }
    if (x - 1 >= 0 && y - 2 >= 0) {
      if (isBlack(board[x - 1][y - 2].getPiece()) || isClear(board[x - 1][y - 2].getPiece())) {
        board[x][y].setMoves(x-1, y-2,true);
      }
    }

    if (x + 2 < 8 && y + 1 < 8) {
      if (isBlack(board[x + 2][y + 1].getPiece()) || isClear(board[x + 2][y + 1].getPiece())) {
        board[x][y].setMoves(x+2, y+1,true);
      }
    }
    if (x + 2 < 8 && y - 1 >= 0) {
      if (isBlack(board[x + 2][y - 1].getPiece()) || isClear(board[x + 2][y - 1].getPiece())) {
        board[x][y].setMoves(x+2, y-1,true);
      }
    }
    if (x - 2 >= 0 && y + 1 < 8) {
      if (isBlack(board[x - 2][y + 1].getPiece()) || isClear(board[x - 2][y + 1].getPiece())) {
        board[x][y].setMoves(x-2, y+1,true);
      }
    }
    if (x - 2 >= 0 && y - 1 >= 0) {
      if (isBlack(board[x - 2][y - 1].getPiece()) || isClear(board[x - 2][y - 1].getPiece())) {
        board[x][y].setMoves(x-2, y-1,true);
      }
    }
    
  }

  public static void blackKnight(int x, int y) {
    
    if (x + 1 < 8 && y + 2 < 8) {
      if (isWhite(board[x + 1][y + 2].getPiece()) || isClear(board[x + 1][y + 2].getPiece())) {
        board[x][y].setMoves(x+1, y+2,true);
      }
    }
    if (x + 1 < 8 && y - 2 >= 0) {
      if (isWhite(board[x + 1][y - 2].getPiece()) || isClear(board[x + 1][y - 2].getPiece())) {
        board[x][y].setMoves(x+1, y-2,true);
      }
    }
    if (x - 1 >= 0 && y + 2 < 8) {
      if (isWhite(board[x - 1][y + 2].getPiece()) || isClear(board[x - 1][y + 2].getPiece())) {
        board[x][y].setMoves(x-1, y+2,true);
      }
    }
    if (x - 1 >= 0 && y - 2 >= 0) {
      if (isWhite(board[x - 1][y - 2].getPiece()) || isClear(board[x - 1][y - 2].getPiece())) {
        board[x][y].setMoves(x-1, y-2,true);
      }
    }

    if (x + 2 < 8 && y + 1 < 8) {
      if (isWhite(board[x + 2][y + 1].getPiece()) || isClear(board[x + 2][y + 1].getPiece())) {
        board[x][y].setMoves(x+2, y+1,true);
      }
    }
    if (x + 2 < 8 && y - 1 >= 0) {
      if (isWhite(board[x + 2][y - 1].getPiece()) || isClear(board[x + 2][y - 1].getPiece())) {
        board[x][y].setMoves(x+2, y-1,true);
      }
    }
    if (x - 2 >= 0 && y + 1 < 8) {
      if (isWhite(board[x - 2][y + 1].getPiece()) || isClear(board[x - 2][y + 1].getPiece())) {
        board[x][y].setMoves(x-2, y+1,true);
      }
    }
    if (x - 2 >= 0 && y - 1 >= 0) {
      if (isWhite(board[x - 2][y - 1].getPiece()) || isClear(board[x - 2][y - 1].getPiece())) {
        board[x][y].setMoves(x-2, y-1,true);
      }
    }
    
  }

  public static void whiteQueen(int x, int y) {
    
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0 && ty - 1 >= 0) {
      tx--;
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0 && ty + 1 < 8) {
      tx--;
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty - 1 >= 0) {
      tx++;
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty + 1 < 8) {
      tx++;
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0) {
      tx--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }

      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8) {
      tx++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }

      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty + 1 < 8) {
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty - 1 >= 0) {
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    
  }

  public static void blackQueen(int x, int y) {
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0 && ty - 1 >= 0) {
      tx--;
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0 && ty + 1 < 8) {
      tx--;
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty - 1 >= 0) {
      tx++;
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);

    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty + 1 < 8) {
      tx++;
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0) {
      tx--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8) {
      tx++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty + 1 < 8) {
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty - 1 >= 0) {
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    
  }

  public static void whiteBishop(int x, int y) {
    
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0 && ty - 1 >= 0) {
      tx--;
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0 && ty + 1 < 8) {
      tx--;
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty - 1 >= 0) {
      tx++;
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty + 1 < 8) {
      tx++;
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    
  }

  public static void blackBishop(int x, int y) {
    
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0 && ty - 1 >= 0) {
      tx--;
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx - 1 >= 0 && ty + 1 < 8) {
      tx--;
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty - 1 >= 0) {
      tx++;
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8 && ty + 1 < 8) {
      tx++;
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }

  }

  public static void blackRook(int x, int y) {
    
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0) {
      tx--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8) {
      tx++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty + 1 < 8) {
      ty++;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty - 1 >= 0) {
      ty--;
      if (isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isWhite(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    
  }

  public static void whiteRook(int x, int y) {
    
    int tx = x;
    int ty = y;
    while (tx - 1 >= 0) {
      tx--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }

      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (tx + 1 < 8) {
      tx++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }

      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);;
    }
    tx = x;
    ty = y;
    while (ty + 1 < 8) {
      ty++;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    tx = x;
    ty = y;
    while (ty - 1 >= 0) {
      ty--;
      if (isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        board[x][y].setMoves(tx, ty,true);
        break;
      }
      if (!isBlack(board[tx][ty].getPiece()) && !isClear(board[tx][ty].getPiece())) {
        break;
      }
      board[x][y].setMoves(tx, ty,true);
    }
    
  }

  public static boolean isBlack(char piece) // is the location a black piece
  {
    int temp = (int) piece;
    if (temp < 97 && temp > 64) {
      return true;
    }
    {
      return false;
    }
  }

  public static boolean isWhite(char piece) // is the location a white piece
  {
    int temp = (int) piece;
    if (temp >= 97) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isClear(char piece) // Is the location a '0'
  {
    int temp = (int) piece;
    if (temp == 48) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean[][] flush() {
    boolean[][] validMoves = new boolean[8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        validMoves[i][j] = false;
      }
    }
    return validMoves;
  }

  public static void display(Piece[][] board)// Support method to display all pieces
  {
    for (int a = 0; a < 8; a++) {
      for (int b = 0; b < 8; b++) {
        System.out.print(board[a][b].getPiece() + "" + board[a][b].getIndex() + " ");
      }
      System.out.println();
    }
  }

  public static void start(Piece[][] board)// Support method to set up board
  {
    board[0][0].setPiece('R');
    board[0][0].setIndex(1);
    board[0][1].setPiece('N');
    board[0][1].setIndex(1);
    board[0][2].setPiece('B');
    board[0][2].setIndex(1);
    board[0][3].setPiece('Q');
    board[0][3].setIndex(1);
    board[0][4].setPiece('K');
    board[0][4].setIndex(1);
    board[0][5].setPiece('B');
    board[0][5].setIndex(2);
    board[0][6].setPiece('N');
    board[0][6].setIndex(2);
    board[0][7].setPiece('R');
    board[0][7].setIndex(2);

    board[1][0].setPiece('P');
    board[1][0].setIndex(1);
    board[1][1].setPiece('P');
    board[1][1].setIndex(2);
    board[1][2].setPiece('P');
    board[1][2].setIndex(3);
    board[1][3].setPiece('P');
    board[1][3].setIndex(4);
    board[1][4].setPiece('P');
    board[1][4].setIndex(5);
    board[1][5].setPiece('P');
    board[1][5].setIndex(6);
    board[1][6].setPiece('P');
    board[1][6].setIndex(7);
    board[1][7].setPiece('P');
    board[1][7].setIndex(8);

    board[7][0].setPiece('r');
    board[7][0].setIndex(1);
    board[7][1].setPiece('n');
    board[7][1].setIndex(1);
    board[7][2].setPiece('b');
    board[7][2].setIndex(1);
    board[7][3].setPiece('q');
    board[7][3].setIndex(1);
    board[7][4].setPiece('k');
    board[7][4].setIndex(1);
    board[7][5].setPiece('b');
    board[7][5].setIndex(2);
    board[7][6].setPiece('n');
    board[7][6].setIndex(2);
    board[7][7].setPiece('r');
    board[7][7].setIndex(2);

    board[6][0].setPiece('p');
    board[6][0].setIndex(1);
    board[6][1].setPiece('p');
    board[6][1].setIndex(2);
    board[6][2].setPiece('p');
    board[6][2].setIndex(3);
    board[6][3].setPiece('p');
    board[6][3].setIndex(4);
    board[6][4].setPiece('p');
    board[6][4].setIndex(5);
    board[6][5].setPiece('p');
    board[6][5].setIndex(6);
    board[6][6].setPiece('p');
    board[6][6].setIndex(7);
    board[6][7].setPiece('p');
    board[6][7].setIndex(8);
  }

}
