
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
        
       Board b = new Board();
       Scanner reader = new Scanner(System.in);
       while(b.isWinner() == false){
           b.printBoard();
           int row = reader.nextInt();
           int column = reader.nextInt();
           int newRow = reader.nextInt();
           int newColumn = reader.nextInt();
           b.movePiece(b.getPiece(row, column), newRow, newColumn);
           b.printBoard();
           if(reader.next().equals("history")){
               int i = 0;
               while(i < b.getHistory().size()){
                   System.out.println(b.getHistory().get(i));
                   i++;
               }
           }
       }
       

         
         
       
         
         
         
     
    }
}

      
   
    

