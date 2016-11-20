/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Mark Miller
 */
public abstract class Piece {
    
   int moveCount;
   private Color color;
   ArrayList<int []> pieceHistory;
    
   public Piece(Color c){
       moveCount = 0;
       color = c;
   }
   public Color getColor(){
       return color;
   }
   @Override
   public abstract String toString();
   public abstract boolean isValidMove(int row, int column, Board board);
   public abstract ArrayList<int []> getValidMoves();
   
   
}
