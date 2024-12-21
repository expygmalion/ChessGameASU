package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

//TODO Taj Creates the Class and provides structure and functionality

/**
 * The type Tile.
 */
public abstract class Tile {


    /**
     * The Tile coordinate.
     */
    protected final int tileCoordinate;

    /**
     * Gets tile coordinate.
     *
     * @return the tile coordinate
     */
    public int getTileCoordinate() {
        return tileCoordinate;
    }
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();


    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));

        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    /**
     * Create tile tile.
     *
     * @param tileCoordinate the tile coordinate
     * @param piece          the piece
     * @return the tile
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {

        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }



    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * Is tile occupied boolean.
     *
     * @return the boolean
     */
    public abstract boolean isTileOccupied();

    /**
     * Gets piece.
     *
     * @return the piece
     */
    public abstract Piece getPiece();

    /**
     * The type Empty tile.
     */
    public static final class EmptyTile extends Tile {
        /**
         * Instantiates a new Empty tile.
         *
         * @param coordinate the coordinate
         */
        EmptyTile(int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
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

    /**
     * The type Occupied tile.
     */
    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;

        /**
         * Instantiates a new Occupied tile.
         *
         * @param tileCoordinate the tile coordinate
         * @param pieceOnTile    the piece on tile
         */
        OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }
        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ?
                    getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }

    }
}