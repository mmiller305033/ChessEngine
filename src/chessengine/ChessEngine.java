
package chessengine;

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
       Board b = new Board();
       b.printBoard();
       
       Scanner r = new Scanner(System.in);
       b.movePiece(r.nextInt(),r.nextInt() ,r.nextInt() ,r.nextInt() );
       b.printBoard();
    }
    
}
