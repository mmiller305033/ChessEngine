/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

/**
 *
 * @author Mark Miller
 */
public class Board {
    Piece [][] board = new Piece[8][8];
  public Board(){
      
  } 
  
  public void printBoard(){
        for (Piece[] board1 : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board1[j]);
                System.out.print("");
            }
            System.out.println("");
        }
  }
  
  public void movePiece(int pieceRow, int pieceColumn, int newRow, int newColumn){
      for(int i = 0; i < board[pieceRow][pieceColumn].getValidMoves().size(); i++){
          if(board[pieceRow][pieceColumn].getValidMoves().get(i)[0] == newRow && board[pieceRow][pieceColumn].getValidMoves().get(i)[1] == newColumn ){
              board[newRow][newColumn] = board[pieceRow][pieceColumn];
              board[pieceRow][pieceColumn] = null;
              
          }
      }
      
       
  }
}
