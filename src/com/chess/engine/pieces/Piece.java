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
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }
    public PieceType getPieceType() {
        return this.pieceType;
    }
    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(final Move move); // Added Rawan

    // we use integer instead of int because it is needed for collections,
    // we ensure compatibility with guava Library and other libs
    public Integer getPiecePosition() { return this.piecePosition; }

    public boolean isFirstMove() {
        return isFirstMove;
    }
    //added rawan
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
        //end
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



    // Ahmed Added pieces
    public enum PieceType{
        PAWN(100, "P") {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT(300, "N") {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        BISHOP(330,"B") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        ROOK(500,"R") {
            @Override                      // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return true;
            }
        },
        QUEEN(900,"Q") {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        KING(10000,"K") {
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
        PieceType(final int pieceValue,final String pieceName){
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


}
