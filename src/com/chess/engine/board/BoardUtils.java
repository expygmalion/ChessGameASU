package com.chess.engine.board;

import javax.swing.text.Position;
import java.util.*;

/**
 * The type Board utils.
 */
// TODO Taj creates the BoardUtils Class and provides structure and functionality
public class BoardUtils {

    /**
     * The constant FIRST_COLUMN.
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    /**
     * The constant SECOND_COLUMN.
     */
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    /**
     * The constant THIRD_COLUMN.
     */
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    /**
     * The constant FOURTH_COLUMN.
     */
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    /**
     * The constant FIFTH_COLUMN.
     */
    public static final boolean[] FIFTH_COLUMN = initColumn(4);
    /**
     * The constant SIXTH_COLUMN.
     */
    public static final boolean[] SIXTH_COLUMN = initColumn(5);
    /**
     * The constant SEVENTH_COLUMN.
     */
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    /**
     * The constant EIGHTH_COLUMN.
     */
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    /**
     * The constant EIGHT_RANK.
     */
// Ranks (Rows, from 1 at the bottom up top 8)
    public static final boolean[] EIGHT_RANK = initRow(0);
    /**
     * The constant SEVENTH_RANK.
     */
    public static final boolean[] SEVENTH_RANK = initRow(8);
    /**
     * The constant SIXTH_RANK.
     */
    public static final boolean[] SIXTH_RANK = initRow(16);
    /**
     * The constant FIFTH_RANK.
     */
    public static final boolean[] FIFTH_RANK = initRow(24);
    /**
     * The constant FOURTH_RANK.
     */
    public static final boolean[] FOURTH_RANK = initRow(32);
    /**
     * The constant THIRD_RANK.
     */
    public static final boolean[] THIRD_RANK = initRow(40);
    /**
     * The constant SECOND_RANK.
     */
    public static final boolean[] SECOND_RANK = initRow(48);
    /**
     * The constant FIRST_RANK.
     */
    public static final boolean[] FIRST_RANK = initRow(56);
    /**
     * The constant NUM_TILES.
     */
    public static final int NUM_TILES = 64;
    /**
     * The constant NUM_TILES_PER_ROW.
     */
    public static final int NUM_TILES_PER_ROW = 8;
    /**
     * The constant START_TILE_INDEX.
     */
    public static final int START_TILE_INDEX = 0;

    /**
     * The constant ALGEBRAIC.
     */
//Added for Gui Porposes: Create a generally accepted notation
    public static final List<String> ALGEBRAIC  = initiAlgebraicNotation();
    /**
     * The Pos to co.
     */
    public final Map<String, Integer> POS_TO_CO = initPostitionToCoordinateMap();
    // End Add
    private BoardUtils() {
        throw new RuntimeException("Cannot Instantiate An Object From This Class");
    }


    private static boolean[] initColumn(int columnNumber) {
        try {
            final boolean[] column = new boolean[NUM_TILES];
            do {
                column[columnNumber] = true;
                columnNumber += NUM_TILES_PER_ROW;
            } while (columnNumber < NUM_TILES);
            return column;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing column at index " + columnNumber, e);
        }
    }

    private static boolean[] initRow(int rowNumber) {
        try {
            final boolean[] row = new boolean[NUM_TILES];
            do {
                row[rowNumber] = true;
                rowNumber++;
            } while (rowNumber % NUM_TILES_PER_ROW != 0);
            return row;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing row at index " + rowNumber, e);
        }
    }

    /**
     * Is valid tile coordinate boolean.
     *
     * @param coordinate the coordinate
     * @return the boolean
     */
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate <= NUM_TILES;
    }

    private Map<String, Integer> initPostitionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    //todo come back later and fix those
    private static List<String> initiAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    /**
     * Gets coordinate at position.
     *
     * @param position the position
     * @return the coordinate at position
     */
    public int getCoordinateAtPosition(final String position) {
        return POS_TO_CO.get(position);
    }

    /**
     * Gets position at coordinate.
     *
     * @param coordinate the coordinate
     * @return the position at coordinate
     */
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC.get(coordinate);
    }

}
