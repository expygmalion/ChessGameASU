package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;


//TODO Taj creates the abstract class piece. To be extended by types.

public abstract class Piece {
    // Added Omer
    protected final PieceType pieceType;
    // End Add
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove; //Ahmed Added

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Piece(final PieceType pieceType, // Added by Omer
                 final int piecePosition,
                 final Alliance pieceAlliance) {
        this.pieceType = pieceType;
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

    // Ahmed Added pieces
    public enum PieceType{
        PAWN("P") {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override                      // Added By Omer
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return true;
            }
        };

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

        public abstract boolean isKing();   // Added By Omer
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}
