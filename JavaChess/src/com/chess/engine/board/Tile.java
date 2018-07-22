//logical packages
package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.HashMap;


    public abstract class Tile {
        /* only accessed by subclasses and when set as final, cannot be set again */
        /* To make tileCoordinate immutable */
        protected final int tileCoordinate;

        private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

        private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
            final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                emptyTileMap.put(i, new EmptyTile(i));
            }
            return ImmutableMap.copyOf(emptyTileMap);
        }

        /* so that only method allowed to use to make Tile is with this method */
        public static Tile createTile(final int tileCoordinate, final Piece piece) {
            return piece != null ? new OccupiedTile(tileCoordinate, piece): EMPTY_TILES_CACHE.get(tileCoordinate);
        }

        private Tile(int tileCoordinate) {
            this.tileCoordinate = tileCoordinate;
        }

        /* whether the tile is occupied or not */
        public abstract boolean isTileOccupied();

        /* Retrieve the piece from a given tile */
        public abstract Piece getPiece();

        public int getTileCoordinate() {
            return this.tileCoordinate;
        }

        /* the subclass for empty tiles */
    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override /*if tile is occupied, print it out, if not print out a hyphen */
        public String toString() {
            return "-";
        }
        @Override
        public boolean isTileOccupied(){
            return false;
        }
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /* When there's a piece in that given tile */
    public static final class OccupiedTile extends Tile {
        /* private so that it can in no way be referenced and it is final */
        private final Piece pieceOnTile;
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override /*if tile is occupied, print it out, if not print out a hyphen */
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}

