package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    /* Where the pieces will be moved to via tile #s*/
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public abstract Board execute();


    /* Major Piece move */
    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        /* When you make a move that's legal, it's going to return a new board to execute the move as the real board is immutable*/
        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move {
        /* Piece being attacked */

        final Piece attackedPiece;

        /* Piece Attacking */
        public AttackMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate,
                   final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }



}
