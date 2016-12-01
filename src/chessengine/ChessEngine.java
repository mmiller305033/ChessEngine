
package chessengine;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Mark Miller
 */
public class ChessEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
      Board b = new Board();
      b.printBoard();
      int row = reader.nextInt();
      int col = reader.nextInt();
//      b.movePiece(b.board[reader.nextInt()][reader.nextInt()],reader.nextInt() , reader.nextInt());
//       System.out.print("");
//      b.printBoard();
//      b.movePiece(b.board[reader.nextInt()][reader.nextInt()],reader.nextInt() , reader.nextInt());
//       System.out.print("");
//      b.printBoard();
//      b.movePiece(b.board[reader.nextInt()][reader.nextInt()],reader.nextInt() , reader.nextInt());
//       System.out.print("");
//      b.printBoard();
//      b.movePiece(b.board[reader.nextInt()][reader.nextInt()],reader.nextInt() , reader.nextInt());
//       System.out.print("");
//      b.printBoard();
//      b.movePiece(b.board[reader.nextInt()][reader.nextInt()],reader.nextInt() , reader.nextInt());
      //b.promotePawn(b.board[reader.nextInt()][reader.nextInt()], reader.next());
      
      int i = 0;
      while(i < b.getBishopMoves(b.board[row][col]).size()){
          System.out.println(Arrays.toString(b.getBishopMoves(b.board[row][col]).get(i)));
          i++;
      } 
      
      System.out.print("");
      b.printBoard();
      
    }
    
}
