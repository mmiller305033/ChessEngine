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
public class Pawn extends Piece {
    private boolean direction; //true = up | false = down
    
    public Pawn(Color c, boolean DIRECTION) {
        super(c);
        direction = DIRECTION;
    }

    @Override
    public boolean isValidMove(int row, int col, Board board) {
        if(direction == true){
            return true;
        }
        else{
            return true;
        }
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
