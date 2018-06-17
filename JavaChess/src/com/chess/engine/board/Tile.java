//logical packages
package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    /*only accessed by subclasses and when set as final, cannot be set again */
    /* To make tileCoordinate immutable */
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    /* whether the tile is occupied or not */
    public abstract boolean isTileOccupied();

    /* Retrieve the piece off a given tile */
    public abstract Piece getPiece();

    /* the subclass for empty tiles */
    public static final class EmptyTile extends Tile{

        EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
    /* When there's a piece in that given tile */
    public static final class OccupiedTile extends Tile{
        /* private so that it can in no way be referenced and it is final */
        private final Piece pieceOnTile;

        OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
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
