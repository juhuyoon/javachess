package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {

    //cannot have an immutable array, but CAN have an immutable list in Java!
    private final List<Tile> gameBoard;  //captures board
    private final Collection<Piece> whitePieces;  //captures white pieces
    private final Collection<Piece> blackPieces;  //captures black pieces

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private Board(final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);


        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    /*To string method */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i< BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

            public Player blackPlayer() {
                return this.blackPlayer;
            }

            public Player currentPlayer() {
                return this.currentPlayer;
            }


            /* extracting the pieces after accounting for them on board*/
            public Collection<Piece> getBlackPieces() {
                return this.blackPieces;
            }

            /* extracting the pieces after accounting for them on board */
            public Collection<Piece> getWhitePieces() {
                return this.whitePieces;
            }


            /* get piece is tile is occupied, white piece prints out different from a black piece. */
            private static String prettyPrint(final Tile tile) {
                return tile.toString();
            }


            /* a method where a collection of pieces are passed in and used to calculate the legal moves*/
            private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
                final List<Move> legalMoves = new ArrayList<>();
                for(final Piece piece: pieces) {
                    //get a collection back of all LegalMoves and return list
                    legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /* method to return a collection of pieces onto the gameboard based on the alliance*/
    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
                                                           final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile: gameBoard) {
            if(tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if(piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    //method that will populate a list of tiles numbered 0 ~ 63 for board
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i= 0; i < BoardUtils.NUM_TILES; i++) { //in loop, map a piece onto a tile
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i)); //boardConfig.get(i) = get piece associated with it and get the tile
        }
        return ImmutableList.copyOf(tiles);
    }

    //default board position
    public static Board createStandardBoard() {

        final Builder builder = new Builder();
        //Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));

        //White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
        }

    /* Java Builder, mutable fields on the builder, then build runs, and immutable board is created based on the builder.  */
    public static class Builder {

        Map<Integer, Piece> boardConfig;
        //only keep track of the person moving
        Alliance nextMoveMaker;

        //exposing builder constructor as public
        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        //calling builder back to whichever action it got called from
        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

}
