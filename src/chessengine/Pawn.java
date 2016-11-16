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
    
    public Pawn(int[] STARTPOS, boolean DIRECTION) {
        super(STARTPOS);
        direction = DIRECTION;
    }

    @Override
    public boolean isValidMove(int row, int col, Board board) {
        return true;
    }

    @Override
    public ArrayList<int[]> getValidMoves() {
        ArrayList<int []> ValidMoves = new ArrayList<>();
        currentPos[0] += 1;
        ValidMoves.add(currentPos);
        return ValidMoves;
    }

    public boolean getDirection(){
        return direction;
    }
    
}
