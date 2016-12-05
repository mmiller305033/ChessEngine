
package chessengine;

import java.awt.Color;
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
        
        Board b = new Board();
        Scanner reader = new Scanner(System.in);
        boolean endGame = false;
        System.out.println("CHESS ENGINE 1.0 ALPHA\nTo move pieces in this game the console expects four numbers\nas input.  The order and position of these numbers must follow the designated pattern");
        System.out.println("Example. (1 2 3 4) this will move the piece at [1,2] to [3,4].\nREMINDER: Don't forget! The board starts at [0,0] and ends at [7,7].");
        System.out.println("If you would like to end the game in a draw enter -1\ninto all four expected inputs \n");
        while (b.isWinner() == false && endGame == false) {
            b.printBoard();
            System.out.println("It is " + b.whoseTurn() + "'s turn");
            int row = reader.nextInt();
            int column = reader.nextInt();
            int newRow = reader.nextInt();
            int newColumn = reader.nextInt();
            if (row != -1 && column != -1 && newRow != -1 && newColumn != -1) {
                if ((row >= 0 && row <= 7) && (column >= 0 && column <= 7) && (newRow >= 0 && newRow <= 7) && (newColumn >= 0 && newColumn <= 7)) {
                    if (b.getPiece(row, column) != null) {
                        b.movePiece(b.getPiece(row, column), newRow, newColumn);
                    }
                    else{
                        System.out.println("There is no piece currently at [" + row + "," + column + "] please select another piece.");
                    }
                } else {
                    System.out.println("The position you have entered is outside the board.\nDon't forget! The board starts at [0,0] and ends at [7,7].");
                }
            } else {
                endGame = true;
            }
            System.out.println("");
            System.out.println("Current Game History:");
            int i = 0;
            while (i < b.getHistory().size()) {
                System.out.println(b.getHistory().get(i));
                i++;
            }
            System.out.println("");

        }
        System.out.print(b.getWinner());

    }
}

      
   
    

