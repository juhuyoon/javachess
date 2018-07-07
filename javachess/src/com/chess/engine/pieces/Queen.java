package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    /* all possible moves available for the Queen */
    /* the move vector coordinates here are from the Rook positions and the Bishop positions */
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7,-1, 1, 7, 8, 9 };

    public Queen(final Alliance pieceAlliance,
                 final int piecePosition) {
        super(pieceType.QUEEN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        /* loop through each possible moveset vector for the bishop, and for every possible vector, check if that starting position is valid */
        /* Then apply the offset to the new position moving towards, and if it's valid, then check if it's occupied by either the enemy or ally*/
        /* After doing that check, if it's not occupied, keep moving/sliding to desired location */
        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                /* when it's the off cases to the rule */
                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }
                /*else just do what is normally done */
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        // if that space is occupied/filled, determine if ally or enemy
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); //new move to remove enemy
                        }
                        break; /* to break out of the loop of movement if there is another piece is in the way */
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }


    /* conversion of item to string */
    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }

    /* all possible exclusions/bad cases of the piece */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }
}
