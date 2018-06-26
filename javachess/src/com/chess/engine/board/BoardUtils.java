package com.chess.engine.board;

public class BoardUtils {
    //if piece is at first column, that position will be true, the rest will be false.
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    /*this is for the pawn's specific placements and first move. */
    public static final boolean[] SECOND_ROW = initRow(8); //tileID that begins the row
    public static final boolean[] SEVENTH_ROW = initRow(48); //tileID that begins the row

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    //just to make sure no one can instantiate
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate");
    }

    private static boolean[] initColumn(int columnNumber) {
        /* declares boolean array of size 64, takes columnNumber, then take true #, add 8. */
        final boolean[] column = new boolean[64];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while(columnNumber < NUM_TILES);

        return column;
    }

    /*If given 0, would start at the first row, while less than 8, would set values to true.  */
    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % NUM_TILES_PER_ROW != 0);

        return row;
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
