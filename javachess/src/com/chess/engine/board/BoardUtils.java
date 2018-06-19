package com.chess.engine.board;

public class BoardUtils {
    //if piece is at first column, that position will be true, the rest will be false.
    public static final boolean[] First_COLUMN = null;
    public static final boolean[] SECOND_COLUMN = null;
    public static final boolean[] SEVENTH_COLUMN = null;
    public static final boolean[] EIGHTH_COLUMN = null;

    //just to make sure no one can instantiate
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate");
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
