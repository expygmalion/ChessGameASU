package com.chess.engine.pieces;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import java.util.Collection;


public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
        protected final boolean isFirstMove; //Ahmed Added

    public Piece(int piecePosition, Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = false; //Ahmed Added
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

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
        @Override
        public String toString(){
            return this.pieceName;
        }
        // End Ahmed Added
    }

}
