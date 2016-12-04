/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessengine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mark Miller
 */
public class Board {
    private int turnCount;
    private Piece[][] board = new Piece[8][8];
    private ArrayList<BoardHistory> history = new ArrayList<>();

    public Board() {
        turnCount = 0;
        
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

        // White
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
        board[7][2] = new Bishop(Color.WHITE, 7, 2);
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
        System.out.println("Checking legal move");
        if (isLegalMove(piece, newRow, newColumn)) {
            history.add(new BoardHistory(piece, piece.getRow(), piece.getColumn(), newRow, newColumn)); //creates a BoardHistory object to store all moves made over the course of the game
            System.out.println("History Added");
            addPiece(board[piece.getRow()][piece.getColumn()], newRow, newColumn);
            System.out.println("Piece Added");
            removePiece(piece);
            System.out.println("Piece removed");
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

    public boolean isLegalMove(Piece p, int newRow, int newColumn) {//in testing
        if (p != null) {
            ArrayList<int[]> legalMoves = getLegalMoves(p);
            int i = 0;
            while (i < legalMoves.size()) {
                if (legalMoves.get(i)[0] == newRow && legalMoves.get(i)[1] == newColumn) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public ArrayList<int[]> getValidMoves(Piece p, Piece [][] b) { //A valid move does not look for kings in check.
        if (p != null) {
            switch (p.toString()) {
                case "Pawn":
                    return getPawnMoves(p,b);
                case "King":
                    return getKingMoves(p,b);
                case "Queen":
                    return getQueenMoves(p,b);
                case "Bishop":
                    return getBishopMoves(p,b);
                case "Rook":
                    return getRookMoves(p,b);
                case "Knight":
                    return getKnightMoves(p,b);
            }
        }
        return null;
    }

    public ArrayList<int[]> getLegalMoves(Piece p) { //A legal move checks for a king in check
        ArrayList<int[]> legalMoves = new ArrayList<>();
        ArrayList<int[]> validMoves = getValidMoves(p, board);
        ArrayList<int[]> opponentsNextMoves = new ArrayList<>();
        int tempRow = p.getRow();
        int tempColumn = p.getColumn();
        boolean inCheck = false;

        Piece[][] tempBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {                    //COPIES BOARD INTO A TEMPORARY BOARD
            for (int j = 0; j < 8; j++) {                //THE TEMPORARY BOARD IS USED TO SEE IF A KING
                tempBoard[i][j] = board[i][j];           //WILL BE PUT INTO CHECK WHEN A PIECE MOVES
            }                                            //TO ONE OF ITS VALID MOVES
        }

        int validMoveCount = 0;
        while (validMoveCount < validMoves.size()) {

            for (int i = 0; i < 8; i++) {                    //COPIES BOARD INTO A TEMPORARY BOARD
                for (int j = 0; j < 8; j++) {                //THE TEMPORARY BOARD IS USED TO SEE IF A KING
                    tempBoard[i][j] = board[i][j];           //WILL BE PUT INTO CHECK WHEN A PIECE MOVES
                }                                            //TO ONE OF ITS VALID MOVES
            }
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]] = p;                              //MOVES PIECE(P) ON TEMP BOARD
            tempBoard[tempRow][tempColumn] = null;                                                                            //TO THE CURRENT VALID MOVE
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setRow(validMoves.get(validMoveCount)[0]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setColumn(validMoves.get(validMoveCount)[1]);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) { //NESTED FOR LOOP TO CHECK EACH PIECE
                    if (tempBoard[i][j] != null && p.getColor() != tempBoard[i][j].getColor()) {
                        int tempMoveCount = 0;
                        while (tempMoveCount < getValidMoves(tempBoard[i][j], tempBoard).size()) {  //GETS ALL VALID MOVES FOR EACH OPPOSING PIECE ON THE BOARD
                            opponentsNextMoves.add(getValidMoves(tempBoard[i][j], tempBoard).get(tempMoveCount));
                            tempMoveCount++;
                        }
                    }
                }
            }

            int checkCount = 0;
            inCheck = false;
            while (checkCount < opponentsNextMoves.size()) {
                if (p.getColor() == Color.WHITE) {
                    if (opponentsNextMoves.get(checkCount)[0] == findWhiteKing()[0] && opponentsNextMoves.get(checkCount)[1] == findWhiteKing()[1]) {
                        inCheck = true;
                    }
                } else if (p.getColor() == Color.BLACK) {
                    if (opponentsNextMoves.get(checkCount)[0] == findBlackKing()[0] && opponentsNextMoves.get(checkCount)[1] == findBlackKing()[1]) {
                        inCheck = true;
                    }
                }
                checkCount++;
            }
            if (inCheck == false) {
                legalMoves.add(validMoves.get(validMoveCount));
            }
            
            opponentsNextMoves.clear();
            tempBoard[tempRow][tempColumn] = p;
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setRow(validMoves.get(validMoveCount)[0]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[1]].setColumn(validMoves.get(validMoveCount)[1]);
            tempBoard[validMoves.get(validMoveCount)[0]][validMoves.get(validMoveCount)[0]] = null;

            validMoveCount++;
        }
        p.setRow(tempRow);
        p.setColumn(tempColumn);
        return legalMoves;
    }
    public int[] findWhiteKing(){
        int [] kingCoordinates = new int [2];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] != null && board[i][j].toString().equals("King") && board[i][j].getColor() == Color.WHITE){
                    kingCoordinates[0] = i;
                    kingCoordinates[1] = j;
                    return kingCoordinates;
                }
            }
        }
        return null;
    }
    
    public int[] findBlackKing(){
        int [] kingCoordinates = new int [2];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] != null && board[i][j].toString().equals("King") && board[i][j].getColor() == Color.BLACK){
                    kingCoordinates[0] = i;
                    kingCoordinates[1] = j;
                    return kingCoordinates;
                }
            }
        }
        return null;
    }

    //ONLY SEND PAWNS
    private ArrayList<int[]> getPawnMoves(Piece p, Piece [][] b) { //MAKE PRIVATE AFTER TESTING
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
                    if (b[p.getRow() - 1][p.getColumn()] == null) {
                        int[] moves = new int[2];
                        moves[0] = p.getRow() - 1;
                        moves[1] = p.getColumn();
                        pawnMoves.add(moves);
                    }
                    //---------------------------------END RULE 2------------------------------------------------------------

                    //----------------------RULE 3: IF WHITE IS NOT ON AN EDGE AND CAN TAKE A BLACK PIECE.-------------------
                    if (p.getColumn() != 0 && p.getColumn() != 7) { 
                        if (b[p.getRow() - 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                        if (b[p.getRow() - 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, LEFT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() - 1;
                            pawnMoves.add(moves);
                        }
                    } 
                    //---------------------RULE 3 (LEFT): IF WHITE IS ON LEFT EDGE AND CAN TAKE A BLACK PIECE------------------
                    else if (p.getColumn() == 0) { 
                        if (b[p.getRow() - 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.BLACK) { //WHITE MOVES UP ONE, RIGHT ONE.
                            int[] moves = new int[2];
                            moves[0] = p.getRow() - 1;
                            moves[1] = p.getColumn() + 1;
                            pawnMoves.add(moves);
                        }
                    } 
                    //---------------------RULE 3 (RIGHT): IF WHITE IS ON RIGHT EDGE AND CAN TAKE BLACK PIECE------------------
                    else if (p.getColumn() == 7) { 
                        if (b[p.getRow() - 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.BLACK) { //up one right one
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
                if (b[p.getRow() + 1][p.getColumn()] == null) { 
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + 1;
                    moves[1] = p.getColumn();
                    pawnMoves.add(moves);
                }
                //---------------------------------------------END RULE 2------------------------------------------------------
                
                //-------------------------RULE 3: IF BLACK IS NOT ON AN EDGE AND CAN TAKE A WHITE PIECE.----------------------
                if (p.getColumn() != 0 && p.getColumn() != 7) { 
                    if (b[p.getRow() + 1][p.getColumn() + 1] != null && b[p.getRow() + 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                    if (b[p.getRow() + 1][p.getColumn() - 1] != null && b[p.getRow() + 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() - 1;
                        pawnMoves.add(moves);
                    }
                } 
                //-----------------------RULE 3 (LEFT): IF BLACK IS ON LEFT EDGE AND CAN TAKE A WHITE PIECE--------------------
                else if (p.getColumn() == 0) { 
                    if (b[p.getRow() + 1][p.getColumn() + 1] != null && b[p.getRow() - 1][p.getColumn() + 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, RIGHT 1.
                        int[] moves = new int[2];
                        moves[0] = p.getRow() + 1;
                        moves[1] = p.getColumn() + 1;
                        pawnMoves.add(moves);
                    }
                } 
                //-----------------------RULE 3 (RIGHT): IF WHITE IS ON RIGHT EDGE AND CAN TAKE BLACK PIECE--------------------
                else if (p.getColumn() == 7) { 
                    if (b[p.getRow() + 1][p.getColumn() - 1] != null && b[p.getRow() - 1][p.getColumn() - 1].getColor() == Color.WHITE) { //BLACK MOVES DOWN 1, LEFT 1.
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
    private ArrayList<int[]> getKingMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> kingMoves = new ArrayList<>();
        
        //------------------------------------CASE: TOP LEFT CORNER---------------------------------------
        if(p.getRow() == 0 && p.getColumn() == 0){ 
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            } 
        }
        //-------------------------------------END TOP LEFT CORNER----------------------------------------
        
        //------------------------------------CASE: TOP RIGHT CORNER--------------------------------------
        else if(p.getRow() == 0 && p.getColumn() == 7){
            //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        //-------------------------------------END TOP RIGHT CORNER---------------------------------------
        
        //-----------------------------------CASE: BOTTOM RIGHT CORNER------------------------------------
        else if(p.getRow() == 7 && p.getColumn() == 7){
            //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            
        }
        //------------------------------------END BOTTOM RIGHT CORNER-------------------------------------
        
        //------------------------------------CASE: BOTTOM LEFT CORNER------------------------------------
        else if(p.getRow() == 7 && p.getColumn() == 0){
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        //-----------------------------------------CASE: TOP SIDE-----------------------------------------
        else if(p.getRow() == 0){ 
           //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
        }
        
        
        //----------------------------------------CASE: RIGHT SIDE----------------------------------------
        else if(p.getColumn() == 7){ 
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        
        
        //----------------------------------------CASE: BOTTOM SIDE---------------------------------------
        else if(p.getRow() == 7){ 
            //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
        }
        
        
        //-----------------------------------------CASE: LEFT SIDE----------------------------------------
        else if(p.getColumn() == 0){ 
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
        }
        //---------------------------------------CASE: NON-CORNER/EDGE------------------------------------
        else{
            //CHECKS UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn()] == null || b[p.getRow() - 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() + 1] == null || b[p.getRow() - 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() + 1] == null || b[p.getRow()][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS RIGHT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() + 1] == null || b[p.getRow() + 1][p.getColumn() + 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 1;
                kingMoves.add(moves);
            }
            //CHECKS DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn()] == null || b[p.getRow() + 1][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn();
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, DOWN ONE FOR VALID MOVE
            if(b[p.getRow() + 1][p.getColumn() - 1] == null || b[p.getRow() + 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE FOR VALID MOVE
            if(b[p.getRow()][p.getColumn() - 1] == null || b[p.getRow()][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow();
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
            //CHECKS LEFT ONE, UP ONE FOR VALID MOVE
            if(b[p.getRow() - 1][p.getColumn() - 1] == null || b[p.getRow() - 1][p.getColumn() - 1].getColor() != b[p.getRow()][p.getColumn()].getColor()){ 
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 1;
                kingMoves.add(moves);
            }
        }
            
        return kingMoves;
    }

    //ONLY SEND QUEENS
    private ArrayList<int[]> getQueenMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> queenMoves = new ArrayList<>();
        
        //-----------------------------------RULE 1: MOVING UP/LEFT--------------------------------------------------
        if (p.getRow() != 0 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE UP/LEFT (i) IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow() + i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
            while (hitPiece == false && (p.getColumn() + i <= 7) && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow() + i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
        
        
        //-----------------------------------RULE 4: MOVING UP-------------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn()] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 4----------------------------------------------------------

        //-----------------------------------RULE 5: MOVING DOWN-----------------------------------------------------
        if (p.getRow() != 7) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() + i <= 7)) {
                if (b[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //---------------------------------------END RULE 5---------------------------------------------------------

        //-----------------------------------RULE 6: MOVING RIGHT---------------------------------------------------
        if (p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() + i <= 7)) {
                if (b[p.getRow()][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow()][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow()][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 6---------------------------------------------------------

        //-----------------------------------RULE 7: MOVING LEFT-----------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getColumn() - i >= 0)) {
                if (b[p.getRow()][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                } 
                else if (b[p.getRow()][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow()][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    queenMoves.add(moves);
                }
                i++;
            }
        }
        //----------------------------------------END RULE 7---------------------------------------------------------
        return queenMoves;
    }

    //ONLY SEND BIHSOPS
    private ArrayList<int[]> getBishopMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> bishopMoves = new ArrayList<>();
        if(p != null){
        //-----------------------------------RULE 1: MOVING UP/LEFT--------------------------------------------------
        if (p.getRow() != 0 && p.getColumn() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0) && (p.getColumn() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE UP/LEFT (i) IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow() + i][p.getColumn() - i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() - i;
                    bishopMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
            while (hitPiece == false && (p.getColumn() + i < 7) && (p.getRow() > 0)) {
                if (b[p.getRow() - i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow() + i][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn() + i;
                    bishopMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
        }
        return bishopMoves;
    }

    //ONLY SEND ROOKS
    private ArrayList<int[]> getRookMoves(Piece p, Piece [][] b) { //MAKE PRIVATE AFTER TESTING
        ArrayList<int[]> rookMoves = new ArrayList<>();

        //-----------------------------------RULE 1: MOVING UP-------------------------------------------------------
        if (p.getRow() != 0) {
            int i = 1;
            boolean hitPiece = false;
            while (hitPiece == false && (p.getRow() - i >= 0)) {
                if (b[p.getRow() - i][p.getColumn()] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() - i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                } 
                else if (b[p.getRow() - i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() - i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow() + i][p.getColumn()] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow() + i;
                    moves[1] = p.getColumn();
                    rookMoves.add(moves);
                } 
                else if (b[p.getRow() + i][p.getColumn()].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow() + i][p.getColumn()].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE BELOW IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow()][p.getColumn() + i] == null) { //IF THERE IS NO PIECE BELOW THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() + i;
                    rookMoves.add(moves);
                } 
                else if (b[p.getRow()][p.getColumn() + i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                         //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow()][p.getColumn() + i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE TO THE RIGHT IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
                if (b[p.getRow()][p.getColumn() - i] == null) { //IF THERE IS NO PIECE ABOVE THEN IT IS A VALID MOVE.
                    int[] moves = new int[2];
                    moves[0] = p.getRow();
                    moves[1] = p.getColumn() - i;
                    rookMoves.add(moves);
                } 
                else if (b[p.getRow()][p.getColumn() - i].getColor() == b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS THE SAME COLOR IT IS NOT A VALID MOVE.
                    hitPiece = true;                                                                                       //THE WHILE LOOP WILL STOP SEARCHING FOR MOVES.
                } 
                else if (b[p.getRow()][p.getColumn() - i].getColor() != b[p.getRow()][p.getColumn()].getColor()) { //IF THE PIECE ABOVE IS NOT THE SAME COLOR IT IS A VALID MOVE.
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
    private ArrayList<int[]> getKnightMoves(Piece p, Piece [][] b) {
        ArrayList<int[]> knightMoves = new ArrayList<>();
        //----------------------------------------RULE 1: DOWN 2, RIGHT 1---------------------------------------
        if(p.getRow() + 2 <= 7 && p.getColumn() + 1 <= 7){
            if(b[p.getRow() + 2][p.getColumn() + 1] == null || p.getColor() != b[p.getRow() + 2][p.getColumn() + 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 2;
                moves[1] = p.getColumn() + 1;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 1---------------------------------------------
        
        //-----------------------------------------RULE 2: DOWN 1, RIGHT 2--------------------------------------
        if(p.getRow() + 1 <= 7 && p.getColumn() + 2 <= 7){
            if(b[p.getRow() + 1][p.getColumn() + 2] == null || p.getColor() != b[p.getRow() + 1][p.getColumn() + 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() + 2;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 2---------------------------------------------
                
        //------------------------------------------RULE 3: UP 1, RIGHT 2---------------------------------------
        if(p.getRow() - 1 >= 0 && p.getColumn() + 2 <= 7){
            if(b[p.getRow() - 1][p.getColumn() + 2] == null || p.getColor() != b[p.getRow() - 1][p.getColumn() + 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() + 2;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 3---------------------------------------------
                
        //------------------------------------------RULE 4: UP 2, RIGHT 1---------------------------------------
        if(p.getRow() - 2 >= 0 && p.getColumn() + 1 <= 7){
            if(b[p.getRow() - 2][p.getColumn() + 1] == null || p.getColor() != b[p.getRow() - 2][p.getColumn() + 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 2;
                moves[1] = p.getColumn() + 1;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 4---------------------------------------------
                
        //-------------------------------------------RULE 5: UP 2, LEFT 1---------------------------------------
        if(p.getRow() - 2 >= 0 && p.getColumn() - 1 >= 0){
            if(b[p.getRow() - 2][p.getColumn() - 1] == null || p.getColor() != b[p.getRow() - 2][p.getColumn() - 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 2;
                moves[1] = p.getColumn() - 1;
                knightMoves.add(moves);
            }
        }
        //------------------------------------------------END RULE 5--------------------------------------------
                
        //-------------------------------------------RULE 6: UP 1, LEFT 2---------------------------------------
        if(p.getRow() - 1 >= 0 && p.getColumn() - 2 >= 0){
            if(b[p.getRow() - 1][p.getColumn() - 2] == null || p.getColor() != b[p.getRow() - 1][p.getColumn() - 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() - 1;
                moves[1] = p.getColumn() - 2;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 6---------------------------------------------
                
        //------------------------------------------RULE 7: DOWN 1, LEFT 2--------------------------------------
        if(p.getRow() + 1 <= 7 && p.getColumn() - 2 >= 0){
            if(b[p.getRow() + 1][p.getColumn() - 2] == null || p.getColor() != b[p.getRow() + 1][p.getColumn() - 2].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 1;
                moves[1] = p.getColumn() - 2;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 7---------------------------------------------
                
        //------------------------------------------RULE 8: DOWN 2, LEFT 1--------------------------------------
        if(p.getRow() + 2 <= 7 && p.getColumn() - 1 >= 0){
            if(b[p.getRow() + 2][p.getColumn() - 1] == null || p.getColor() != b[p.getRow() + 2][p.getColumn() - 1].getColor()){
                int [] moves = new int [2];
                moves[0] = p.getRow() + 2;
                moves[1] = p.getColumn() - 1;
                knightMoves.add(moves);
            }
        }
        //-----------------------------------------------END RULE 8---------------------------------------------
        return knightMoves;
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
    
    public ArrayList<BoardHistory> getHistory(){
        return history;
    }
    
    public Piece getPiece(int row, int column){
        return board[row][column];
    }
}
