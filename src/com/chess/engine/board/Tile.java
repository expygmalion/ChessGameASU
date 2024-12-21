package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

//TODO Taj Creates the Class and provides structure and functionality

public abstract class Tile {


    protected final int tileCoordinate;
    public int getTileCoordinate() {
        return tileCoordinate;
    }
         //EMPTY_TILES_CACHE to AllEmptyTiles
    private static final Map<Integer, EmptyTile> AllEmptyTiles = createAllPossibleEmptyTiles();


    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));

        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {

        return piece != null ? new FilledTile(tileCoordinate, piece) : AllEmptyTiles.get(tileCoordinate);
    }



    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
        //isTileOccupied()
    public abstract boolean isTileFilled();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        EmptyTile(int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
        }
        @Override

        public boolean isTileFilled() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
          //OccupiedTile  to  FilledTile
    public static final class FilledTile extends Tile {
        private final Piece pieceOnTile;

        FilledTile(int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileFilled() {
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