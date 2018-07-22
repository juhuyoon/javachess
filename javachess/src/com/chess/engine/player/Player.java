package com.chess.engine.player;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
    final Collection<Move> legalMoves,
    final Collection<Move> opponentMoves) {

        this.board = board;
        this.playerKing = establishKing();
        /* to do castling, must know the opponent's moves as well before you castle(as king must not die)*/
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();

    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();

        for (final Move move : moves) {
            if(piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    /* to make sure the King exists */
    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
        return (King) piece;
    }
    }
        throw new RuntimeException("Should not reach here! Not a valid board!");

}
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }


    public boolean isInCheck() {
        return this.isInCheck;
    }

        //NEW METHODS TO IMPLEMENT
    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }


    /* to calculate if the King can escape on a secondary 'imaginary' board */
        protected boolean hasEscapeMoves() {
            for(final Move move: this.legalMoves) {
                final MoveTransition transition = makeMove(move);
                if(transition.getMoveStatus().isDone()) {
                    return true;
                }
            }
            return false;
}

    public boolean isCastled() {

        return false;
    }

    public MoveTransition makeMove(final Move move) {
        /* If move is not legal, do not switch to new board, stay at same board*/
        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        /* Are there any attacks on Player's King, and if so, then you cannot make a move that exposes king to check. */
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }



    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals);
}
