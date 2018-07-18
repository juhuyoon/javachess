package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;
import java.util.List;


public abstract class Piece {

    protected final PieceType pieceType;
    /* piece positions */
    protected final int piecePosition;
    /* separating black and white */
    /* Alliance will be declared as an enum https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html*/
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove; //for pawn
    private final int cachedHashCode; //anytime interacting with collections of objects, implement hashcode & equals method

    /*setting down the pieces */
    Piece(  final PieceType pieceType,
            final int piecePosition,
            final Alliance pieceAlliance) {
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //must adjust for pawn
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;

    }

    @Override
    public boolean equals(final Object other ) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
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

    public PieceType getPieceType() {
        return this.pieceType;
    }

    /* method responsible for piece moves, returns the collection of moves in a set */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    /* Take in a move, apply it to existing piece, and return a new piece that can be mutable with an updated piece position*/
    public abstract Piece movePiece(Move move);

    public enum PieceType{

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
                }
                },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();

    }


}

