package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

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
            final Builder builder = new Builder();
            /* Goes through current player and traverse through the pieces. */
            for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                } /*if not moved, then place them on the board of that designated spot */
            }
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            /*Represents the move piece after a player has made their move and moves the moved piece*/
            builder.setPiece(null);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
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
