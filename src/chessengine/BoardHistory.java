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
    private Color color;
    private Piece piece;
    private int Row;
    private int Column;
    public BoardHistory(Color c, Piece p, int row, int column){
        color = c;
        piece = p;
        Row = row;
        Column = column;
    }
    
    public Color getColor(){
        return color;
    }
    
    public Piece getPiece(){
        return piece;
    }
    
    public int getRow(){
        return Row;
    }
    
    public int getColumn(){
        return Column;
    }
}
