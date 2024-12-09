package com.chess.engine.pieces;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;


//TODO Taj creates the abstract class piece. To be extended by types.

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove; //Ahmed Added

    public Piece(int piecePosition, Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        //TODO more work here???
        this.isFirstMove = false; //Ahmed Added
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    // we use integer instead of int because it is needed for collections,
    // we ensure compatibility with guava Library and other libs
    public Integer getPiecePosition() { return this.piecePosition; }

    // Ahmed Added
    public enum PieceType{
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private String pieceName;
        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }
        // Added Ahmed
        @Override
        public String toString(){
            return this.pieceName;
        }
        // End Add
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}
