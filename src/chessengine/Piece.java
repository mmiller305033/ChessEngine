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
    private boolean activated;
    private int moveCount;
    private int Row;
    private int Column;
    private Color color;
   public Piece(Color c, int row, int column){
       moveCount = 0;
       color = c;
       Row = row;
       Column = column;
       activated = false;
   }
   
   @Override
   public abstract String toString();
   
   public void activatePiece(){
       activated = true;
   }
   public void deactivatePiece(){
       activated = false;
   }
   public boolean getActivationStatus(){
       return activated;
   } 
   public int getRow(){
       return Row;
   }
   public int getColumn(){
       return Column;
   }
   public Color getColor(){
       return color;
   }
   public int getMoveCount(){
       return moveCount;
   }
   public void setRow(int newRow){
       Row = newRow;
   }
   public void setColumn(int newColumn){
       Column = newColumn;
   }
   
   public void incrementMoveCount(){
       moveCount += 1;
       
   }
}
