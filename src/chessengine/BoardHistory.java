/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import java.awt.Color;

/**
 *
 * @author Mark Miller
 */
public class BoardHistory {
    private Piece piece;
    private int newRow;
    private int newColumn;
    private int initialRow;
    private int initialColumn;
    public BoardHistory(Piece p, int initialrow, int initialcolumn ,int newrow, int newcolumn){
        
        piece = p;
        newRow = newrow;
        newColumn = newcolumn;
        initialRow = initialrow;
        initialColumn = initialcolumn;
    }
    
    public Piece getPiece(){
        return piece;
    }
    
    public int getnewRow(){
        return newRow;
    }
    
    public int getnewColumn(){
        return newColumn;
    }
    
    @Override
    public String toString(){
       return "The " + getColor() + " " + piece.toString() + " from [" + initialRow +"," + initialColumn + "] moved to [" + newRow +"," + newColumn + "]";
    }
    public String getColor(){
        if(piece.getColor() == Color.WHITE){
            return "white";
        }
        else{
            return "black";
        }
    }
}
