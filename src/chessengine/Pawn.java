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
public class Pawn extends Piece {
    private boolean direction; //true = up | false = down
    
    public Pawn(int row, int column, boolean DIRECTION) {
        super(row, column);
        direction = DIRECTION;
    }

    @Override
    public boolean isValidMove(int row, int col, Board board) {
        return true;
    }

    @Override
    public ArrayList<int[]> getValidMoves() {
        ArrayList<int []> ValidMoves = new ArrayList<>();
        
        return ValidMoves;
    }

    public boolean getDirection(){
        return direction;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
    
}
