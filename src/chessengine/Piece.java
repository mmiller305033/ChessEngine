/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import java.util.ArrayList;

/**
 *
 * @author Mark Miller
 */
public abstract class Piece {
    
   int moveCount;
   int Row;
   int Col;
   ArrayList<int []> pieceHistory;
    
   public Piece(int row, int column){
       moveCount = 0;
       Row = row;
       Col = column;
   }
   
   public void move(int row, int column, Board board){
       if(isValidMove(row, column, board)){
           Row = row;
           Col = column;
           int [] arr = new int [2];
           arr[0] = row;
           arr[1] = column;
           pieceHistory.add(arr);
       }
   }
   
   @Override
   public abstract String toString();
   public abstract boolean isValidMove(int row, int column, Board board);
   public abstract ArrayList<int []> getValidMoves();
   
}
