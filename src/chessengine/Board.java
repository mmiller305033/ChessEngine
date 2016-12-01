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
public class Board {

    Piece[][] board = new Piece[8][8];
    ArrayList<BoardHistory> history = new ArrayList<>();

    public Board() {
        //Black
        board[0][0] = new Rook(Color.BLACK, 0, 0);
        board[0][1] = new Knight(Color.BLACK, 0, 1);
        board[0][2] = new Bishop(Color.BLACK, 0, 2);
        board[0][3] = new Queen(Color.BLACK, 0, 3);
        board[0][4] = new King(Color.BLACK, 0, 4);
        board[0][5] = new Bishop(Color.BLACK, 0, 5);
        board[0][6] = new Knight(Color.BLACK, 0, 6);
        board[0][7] = new Rook(Color.BLACK, 0, 7);
        board[1][0] = new Pawn(Color.BLACK, 1, 0);
        board[1][1] = new Pawn(Color.BLACK, 1, 1);
        board[1][2] = new Pawn(Color.BLACK, 1, 2);
        board[1][3] = new Pawn(Color.BLACK, 1, 3);
        board[1][4] = new Pawn(Color.BLACK, 1, 4);
        board[1][5] = new Pawn(Color.BLACK, 1, 5);
        board[1][6] = new Pawn(Color.BLACK, 1, 6);
        board[1][7] = new Pawn(Color.BLACK, 1, 7);

        //White
        board[6][0] = new Pawn(Color.WHITE, 6, 0);
        board[6][1] = new Pawn(Color.WHITE, 6, 1);
        board[6][2] = new Pawn(Color.WHITE, 6, 2);
        board[6][3] = new Pawn(Color.WHITE, 6, 3);
        board[6][4] = new Pawn(Color.WHITE, 6, 4);
        board[6][5] = new Pawn(Color.WHITE, 6, 5);
        board[6][6] = new Pawn(Color.WHITE, 6, 6);
        board[6][7] = new Pawn(Color.WHITE, 6, 7);
        board[7][0] = new Rook(Color.WHITE, 7, 0);
        board[7][1] = new Knight(Color.WHITE, 7, 1);
        board[3][4] = new Bishop(Color.WHITE, 3, 4);
        board[7][3] = new Queen(Color.WHITE, 7, 3);
        board[7][4] = new King(Color.WHITE, 7, 4);
        board[7][5] = new Bishop(Color.WHITE, 7, 5);
        board[7][6] = new Knight(Color.WHITE, 7, 6);
        board[7][7] = new Rook(Color.WHITE, 7, 7);
    }

    public void printBoard() {
        for (Piece[] board1 : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (board1[j] != null) {
                    System.out.print(board1[j].toString().substring(0, 1));
                    System.out.print(" ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
    }

    public void movePiece(Piece piece, int newRow, int newColumn) {
        if (isLegalMove(piece, newRow, newColumn)) {
            history.add(new BoardHistory(piece, piece.getRow(), piece.getColumn(), newRow, newColumn)); //creates a BoardHistory object to store all moves made over the course of the game
            addPiece(board[piece.getRow()][piece.getColumn()], newRow, newColumn);
            removePiece(piece);
            piece.setColumn(newColumn);
            piece.setRow(newRow);
            piece.incrementMoveCount();
        }
    }

    public void addPiece(Piece p, int Row, int Column) {
        board[Row][Column] = p;
    }

    public void removePiece(Piece p) {
        board[p.getRow()][p.getColumn()] = null;
    }

    public boolean isLegalMove(Piece p, int newRow, int newColumn) {
        if (p != null) {
            switch (p.toString()) {
                case "Pawn":
                    int i = 0;
                    while (i < getPawnMoves(board[p.getRow()][p.getColumn()]).size()) {
                        if (newRow == getPawnMoves(board[p.getRow()][p.getColumn()]).get(i)[0] && newColumn == getPawnMoves(board[p.getRow()][p.getColumn()]).get(i)[1]) {
                            return true;
                        }
                        i++;
                    }
                    break;
                case "Rook":

            }
        }
        return false;
    }

    public ArrayList<int[]> getValidMoves(Piece p) { //A valid move does not look for kings in check.

        return null;
    }

    public ArrayList<int[]> getLegalMoves(Piece p) { //A legal move checks for a king in check
        return null;

    }

    //ONLY SEND PAWNS
    public ArrayList<int[]> getPawnMoves(Piece p) { //MAKE PRIVATE AFTER TESTING
        ArrayList<int[]> pawnMoves = new ArrayList<>();
        if (p != null) {
            //----------------------------------------------WHITE MOVES-----------------------------------------------------
            if (p.getColor() == Color.WHITE) {
                if (p.getRow() != 0) {
                    //--------------------RULE 1: FIRST MOVE FOR WHITE.  UP 1 OR UP 2.--------------------------------------
                    if (p.getMoveCount() == 0) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - 1;
                        moves[1] = p.getColumn();
                        pawnMoves.add(moves);
                        int[] move = new int[2];
                        move[0] = p.getRow() - 2;
                        move[1] = p.getColumn();
                        pawnMoves.add(move);
                    }
                    //---------------------------------END RULE 1------------------------------------------------------------

                    //---------------------RULE 2: REGULAR MOVE FOR WHITE.  UP 1.--------------------------------------------
                    if (board[p.getRow() - 1][p.getColumn()] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - 1;
                        moves[1] = p.getColumn();
                        pawnMoves.add(moves);
                    }
                    //---------------------------------END RULE 2------------------------------------------------------------

                    //----------------------RULE 3: IF WHITE IS NOT ON AN EDGE AND CAN TAKE A BLACK PIECE.-------------------
                    if (p.getColumn() != 0 && p.getColumn() != 7) { 
                        if (board[p.getRow() - 1][p.getColumn() + 1] != null && board[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                        if (board[p.getRow() - 1][p.getColumn() - 1] != null && board[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, LEFT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() - 1;
                            pawnMoves.add(moves);
                        }
                    } 
                    //---------------------RULE 3 (LEFT): IF WHITE IS ON LEFT EDGE AND CAN TAKE A BLACK PIECE------------------
                    else if (p.getColumn() == 0) { 
                        if (board[p.getRow() - 1][p.getColumn() + 1] != null && board[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                    } 
                    //---------------------RULE 3 (RIGHT): IF WHITE IS ON RIGHT EDGE AND CAN TAKE BLACK PIECE------------------
                    else if (p.getColumn() == 7) { 
                        if (board[p.getRow() - 1][p.getColumn() - 1] != null && board[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //up one right one
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() - 1;
                            pawnMoves.add(moves);
                        }
                    }
                    //-------------------------------------------END RULE 3----------------------------------------------------
                }
            } 
            //--------------------------------------------------BLACK MOVES----------------------------------------------------
            else if (p.getRow() != 7) {
                //-------------------------------RULE 1: FIRST MOVE FOR BLACK.  DOWN 1 OR DOWN 2.------------------------------
                if (p.getMoveCount() == 0) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + 1;
                    moves[1] = p.getColumn();
                    pawnMoves.add(moves);
                    int[] move = new int[2];
                    move[0] = p.getRow() + 2;
                    move[1] = p.getColumn();
                    pawnMoves.add(move);
                }
                //---------------------------------------------END RULE 1------------------------------------------------------
                
                //------------------------------------RULE 2: REGULAR MOVE FOR BLACK.  DOWN 1.---------------------------------
                if (board[p.getRow() + 1][p.getColumn()] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + 1;
                    moves[1] = p.getColumn();
                    pawnMoves.add(moves);
                }
                //---------------------------------------------END RULE 2------------------------------------------------------
                
                //-------------------------RULE 3: IF BLACK IS NOT ON AN EDGE AND CAN TAKE A WHITE PIECE.----------------------
                if (p.getColumn() != 0 && p.getColumn() != 7) { 
                    if (board[p.getRow() + 1][p.getColumn() + 1] != null && board[p.getRow() + 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                    if (board[p.getRow() + 1][p.getColumn() - 1] != null && board[p.getRow() + 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() - 1;
                        pawnMoves.add(moves);
                    }
                } 
                //-----------------------RULE 3 (LEFT): IF BLACK IS ON LEFT EDGE AND CAN TAKE A WHITE PIECE--------------------
                else if (p.getColumn() == 0) { 
                    if (board[p.getRow() + 1][p.getColumn() + 1] != null && board[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                } 
                //-----------------------RULE 3 (RIGHT): IF WHITE IS ON RIGHT EDGE AND CAN TAKE BLACK PIECE--------------------
                else if (p.getColumn() == 7) { 
                    if (board[p.getRow() + 1][p.getColumn() - 1] != null && board[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() - 1;
                        pawnMoves.add(moves);
                    }
                }
                //---------------------------------------------END RULE 3-------------------------------------------------------
            }
            return pawnMoves;

        }
        return null;
    }

    //ONLY SEND KINGS
    private ArrayList<int[]> getKingMoves(Piece p) {
        return null;
    }

    //ONLY SEND QUEENS
    private ArrayList<int[]> getQueenMoves(Piece p) {
        ArrayList<int[]> queenMoves = new ArrayList<>();
        
        //-----------------------------------RULE 1: MOVING UP/LEFT--------------------------------------------------
        if (p.getRow() != 0 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                if (board[p.getRow() - i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE UP/LEFT (i) IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 1----------------------------------------------------------
        
        //-----------------------------------RULE 2: MOVING DOWN/LEFT------------------------------------------------
        if (p.getRow() != 7 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7) && (p.getColumn() - i >= 0)) {
                if (board[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 2---------------------------------------------------------
        
        //-----------------------------------RULE 3: MOVING UP/RIGHT------------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() >= 0)) {
                if (board[p.getRow() - i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------
        
        //-----------------------------------RULE 3: MOVING DOWN/RIGHT-----------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() + i <= 7)) {
                if (board[p.getRow() + i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------
        
        
        //-----------------------------------RULE 1: MOVING UP-------------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (board[p.getRow() - i][p.getColumn()] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn()].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn()].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 1----------------------------------------------------------

        //-----------------------------------RULE 2: MOVING DOWN-----------------------------------------------------
        if (p.getRow() != 7) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7)) {
                if (board[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn()].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn()].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 2---------------------------------------------------------

        //-----------------------------------RULE 3: MOVING RIGHT---------------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7)) {
                if (board[p.getRow()][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow()][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow()][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------

        //-----------------------------------RULE 4: MOVING LEFT-----------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() - i >= 0)) {
                if (board[p.getRow()][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (board[p.getRow()][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow()][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 4---------------------------------------------------------
        return null;
    }

    //ONLY SEND BIHSOPS
    public ArrayList<int[]> getBishopMoves(Piece p) {
        ArrayList<int[]> bishopMoves = new ArrayList<>();
        //-----------------------------------RULE 1: MOVING UP/LEFT--------------------------------------------------
        if (p.getRow() != 0 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                if (board[p.getRow() - i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE UP/LEFT (i) IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 1----------------------------------------------------------
        
        //-----------------------------------RULE 2: MOVING DOWN/LEFT------------------------------------------------
        if (p.getRow() != 7 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7) && (p.getColumn() - i >= 0)) {
                if (board[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 2---------------------------------------------------------
        
        //-----------------------------------RULE 3: MOVING UP/RIGHT------------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() >= 0)) {
                if (board[p.getRow() - i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------
        
        //-----------------------------------RULE 3: MOVING DOWN/RIGHT-----------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() + i <= 7)) {
                if (board[p.getRow() + i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------
        
        return bishopMoves;
    }

    //ONLY SEND ROOKS
    public ArrayList<int[]> getRookMoves(Piece p) { //MAKE PRIVATE AFTER TESTING
        ArrayList<int[]> rookMoves = new ArrayList<>();

        //-----------------------------------RULE 1: MOVING UP-------------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (board[p.getRow() - i][p.getColumn()] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                } 
                else if (board[p.getRow() - i][p.getColumn()].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() - i][p.getColumn()].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 1----------------------------------------------------------

        //-----------------------------------RULE 2: MOVING DOWN-----------------------------------------------------
        if (p.getRow() != 7) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7)) {
                if (board[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                } 
                else if (board[p.getRow() + i][p.getColumn()].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow() + i][p.getColumn()].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 2---------------------------------------------------------

        //-----------------------------------RULE 3: MOVING RIGHT---------------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7)) {
                if (board[p.getRow()][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    rookMoves.add(moves);
                } 
                else if (board[p.getRow()][p.getColumn() + i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow()][p.getColumn() + i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 3---------------------------------------------------------

        //-----------------------------------RULE 4: MOVING LEFT-----------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() - i >= 0)) {
                if (board[p.getRow()][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    rookMoves.add(moves);
                } 
                else if (board[p.getRow()][p.getColumn() - i].getColor() == board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (board[p.getRow()][p.getColumn() - i].getColor() != board[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    rookMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 4---------------------------------------------------------

        return rookMoves;
    }

    //ONLY SEND KNIGHTS
    private ArrayList<int[]> getKnightMoves(Piece p) {
        return null;
    }

    public void promotePawn(Piece p, String newPiece) {
        int tempRow;
        int tempColumn;
        Color tempColor;
        switch (newPiece) {
            case "Queen":
                tempRow = p.getRow();       //Storing piece's properties into temp variables 
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Queen(tempColor, tempRow, tempColumn), tempRow, tempColumn); //Promoting pawn to new piece with the same properties (Ex. Color, Row, and Column)
                break;
            case "Rook":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Rook(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;
            case "Bishop":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Bishop(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;
            case "Knight":
                tempRow = p.getRow();
                tempColumn = p.getColumn();
                tempColor = p.getColor();
                removePiece(p);
                addPiece(new Knight(tempColor, tempRow, tempColumn), tempRow, tempColumn);
                break;

        }
    }

    public boolean isWinner() {
        return false;
    }

    public String getWinner() {
        return "no winner";
    }
}
