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

    public Pawn(Color c, int row, int column) {
        super(c, row, column);

    }
    
    @Override
    public String toString() {
        return "Pawn";
    }

}
