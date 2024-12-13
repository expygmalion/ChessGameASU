package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.user.BPlayer;
import com.chess.engine.user.Player;
import com.chess.engine.user.WPlayer;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

//TODO Taj Creates the Board Class with Empty Body, To be used by other classes under his purview
//TODO Ahmed sets the general structure of the Board
//TODO Omer adds the functionalities requires for the Player Interactions

public class Board {

    private final List<Tile> gameBoard;
    // Added Ahmed
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    // End Add

    // Added Omer
    private final WPlayer whiteplayer;
    private final BPlayer blackplayer;
    private final Player currentPlayer;
    // End Add


    private Board(Builder builder){

        this.gameBoard = createGameBoard(builder);
        // Added Ahmed
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard,Alliance.BLACK);
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
        // End Add

        // Added Omer
        this.whiteplayer = new WPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackplayer = new BPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer=builder.nextMoveMaker.choosePlayer(this.whiteplayer, this.blackplayer); // Rawan
        // End Add

    }
    // Added Omer
    public Player whitePlayer(){
        return this.whiteplayer;
    }
    public Player blackPlayer(){
        return this.blackplayer;
    }
    public Player currentPlayer(){
        return this.currentPlayer;
    }
    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }
    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    } // End Add


    // Added Ahmed
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < BoardUtils.NUM_TILES ; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i+1)% BoardUtils.NUM_TILES_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }// End Add

    // Added Ahmed
    private static String prettyPrint(Tile tile) {
        return tile.toString();
    }// End Add

    // Added Taj
    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces){
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }// End Add

    // Added Ahmed
    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
                                                    final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();

        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }// End Add

    // Added Taj
    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }// End Add

    // Added Ahmed
    private static List<Tile>createGameBoard(final Builder builder){
        final Tile[] tiles= new Tile[BoardUtils.NUM_TILES];
        for (int i=0; i<BoardUtils.NUM_TILES ; i++){
            tiles[i]= Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    } // End Add

    // Added Ahmed
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // black standard
        //Strong
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece (new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece (new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        // Pawns
        builder.setPiece(new Pawn(8, Alliance.BLACK));
        builder.setPiece(new Pawn(9, Alliance.BLACK));
        builder.setPiece(new Pawn(10, Alliance.BLACK));
        builder.setPiece(new Pawn(11, Alliance.BLACK));
        builder.setPiece(new Pawn(12, Alliance.BLACK));
        builder.setPiece(new Pawn(13, Alliance.BLACK));
        builder.setPiece(new Pawn(14, Alliance.BLACK));
        builder.setPiece(new Pawn(15, Alliance.BLACK));

        // White standard
        // Strong
        builder.setPiece(new Rook(56, Alliance.WHITE));
        builder.setPiece(new Knight(57, Alliance.WHITE));
        builder.setPiece(new Bishop(58, Alliance.WHITE));
        builder.setPiece(new Queen(59, Alliance.WHITE));
        builder.setPiece(new King(60, Alliance.WHITE));
        builder.setPiece(new Bishop(61, Alliance.WHITE));
        builder.setPiece(new Knight(62, Alliance.WHITE));
        builder.setPiece(new Rook(63, Alliance.WHITE));
        // Pawns
        builder.setPiece(new Pawn(48, Alliance.WHITE));
        builder.setPiece(new Pawn(49, Alliance.WHITE));
        builder.setPiece(new Pawn(50, Alliance.WHITE));
        builder.setPiece(new Pawn(51, Alliance.WHITE));
        builder.setPiece(new Pawn(52, Alliance.WHITE));
        builder.setPiece(new Pawn(53, Alliance.WHITE));
        builder.setPiece(new Pawn(54, Alliance.WHITE));
        builder.setPiece(new Pawn(55, Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    } // End Add

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whiteplayer.getlegalMoves(), this.blackplayer.getlegalMoves()));
    }

    // Added Ahmed
    public static class Builder{
        Map<Integer, Piece>   boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;

        public  Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
                    return this;
        }

        // Added Ahmed
        public Board build(){
            return new Board(this);
        } // End Add

        // Added Ola
        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        } // End Add
    }
}// End Add
