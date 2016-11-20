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
    Piece [][] board = new Piece[1][1];
  public Board(){
      board[0][0] = new Pawn(0,0,true);
      board[1][0] = new Pawn(1,0,true);
  } 
  
  public void printBoard(){
      for(int i = 0; i < board.length; i++){
          for(int j = 0; j < board[j].length; j++){
              System.out.println(board[i][j]);
          }
      }
  }
}
