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
   int [] currentPos;
   ArrayList<int []> moveHistory;
    
   public Piece(int [] STARTPOS){
       moveCount = 0;
       currentPos = STARTPOS;
   }
   
   public void move(int row, int col, Board board){
       if(isValidMove(row,col, board)){
           currentPos[0] = row;
           currentPos[1] = col;
           moveHistory.add(currentPos);
       }
   }
   
   public abstract boolean isValidMove(int row, int col, Board board);
   public abstract ArrayList<int []> getValidMoves();
   
}
