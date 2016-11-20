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
    Piece [][] board = new Piece[2][2];
  public Board(){
      board[1][0] = new Pawn(1,0,true);
      board[1][1] = new Pawn(1,1,true);
  } 
  
  public void printBoard(){
      for(int i = 0; i < board.length; i++){
          for(int j = 0; j < board[0].length; j++){
              System.out.print(board[i][j]);
          }
          System.out.println("");
      }
  }
  
  public void movePiece(int pieceRow, int pieceColumn, int newRow, int newColumn){
      board[newRow][newColumn] = board[pieceRow][pieceColumn];
       
      board[pieceRow][pieceColumn] = null;
       
  }
}
