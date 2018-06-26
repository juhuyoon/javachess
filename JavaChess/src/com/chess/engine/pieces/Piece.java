package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;
import java.util.List;


public abstract class Piece {
    /* piece positions */
    protected final int piecePosition;
    /* separating black and white */
    /* Alliance will be declared as an enum https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html*/
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove; //for pawn

    /*setting down the pieces */
    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //must adjust for pawn
        this.isFirstMove = false;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }


    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    /* method responsible for piece moves, returns the collection of moves in a set */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public enum PieceType{

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

    }


}

