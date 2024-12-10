package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.google.common.hash.HashCode;

import java.util.Collection;


//TODO Taj creates the abstract class piece. To be extended by types.
//TODO In each subsequent stage of production, this class will be mutated

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove; //Ahmed Added
    protected final PieceType pieceType;     // Added Omer
    private final int cachedHashcode;



    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Piece(final PieceType pieceType, // Added by Omer
                 final int piecePosition,
                 final Alliance pieceAlliance) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.cachedHashcode = computeHashCode();
        //TODO more work here???
        this.isFirstMove = false; //Ahmed Added
    }

        // Added Rawan
    private int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    } // End Add

    @Override
    public int hashCode() {
        return this.computeHashCode();
    }

    //Added Rawan
    @Override
    public boolean equals(final Object other){
        if (this==other){
            return true;
        }
        if (!(other instanceof Piece))
        {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition()  &&
                pieceType==otherPiece.getPieceType()           &&
                pieceAlliance == otherPiece.getPieceAlliance() &&
                isFirstMove==otherPiece.isFirstMove();
    } // End Add


    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(final Move move); // Added Rawan

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

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override                      // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return true;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
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

        public abstract boolean isKing();// Added Omer
        public abstract boolean isRook(); // Added Ola
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}
