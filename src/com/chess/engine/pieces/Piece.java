package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.google.common.hash.HashCode;

import java.util.Collection;


//TODO Taj creates the abstract class piece. To be extended by types.
//TODO In each subsequent stage of production, this class will be mutated

/**
 * The type Piece.
 */
public abstract class Piece {
    /**
     * The Piece position.
     */
    protected  int piecePosition;
    /**
     * The Piece alliance.
     */
    protected  Alliance pieceAlliance;
    /**
     * The Is first move.
     */
    protected  boolean isFirstMove; //Ahmed Added
    /**
     * The Piece type.
     */
    protected  PieceType pieceType;     // Added Omer
    private int cachedHashcode;

    /**
     * Instantiates a new Piece.
     */
    public Piece() {
    this.piecePosition = piecePosition;
    this.pieceAlliance = pieceAlliance;
    this.isFirstMove = isFirstMove;
    this.pieceType = pieceType;
    this.cachedHashcode = cachedHashcode;

}

    /**
     * Gets piece type.
     *
     * @return the piece type
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Instantiates a new Piece.
     *
     * @param pieceType     the piece type
     * @param piecePosition the piece position
     * @param pieceAlliance the piece alliance
     * @param isFirstMove   the is first move
     */
    public Piece(final PieceType pieceType, // Added by Omer
                 final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.cachedHashcode = computeHashCode();
        this.isFirstMove = isFirstMove; //Ahmed Added
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


    /**
     * Gets piece alliance.
     *
     * @return the piece alliance
     */
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    /**
     * Calculate legal moves collection.
     *
     * @param board the board
     * @return the collection
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    /**
     * Move piece piece.
     *
     * @param move the move
     * @return the piece
     */
    public abstract Piece movePiece(final Move move); // Added Rawan

    /**
     * Gets piece position.
     *
     * @return the piece position
     */
// we use integer instead of int because it is needed for collections,
    // we ensure compatibility with guava Library and other libs
    public Integer getPiecePosition() { return this.piecePosition; }


    /**
     * Gets piece value.
     *
     * @return the piece value
     */
// Taj Added
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    } // End Add

    /**
     * The enum Piece type.
     */
// Ahmed Added pieces
    public enum PieceType{
        /**
         * The Pawn.
         */
        PAWN("P", 100) {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        /**
         * The Knight.
         */
        KNIGHT("N", 300) {
            @Override                   // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        /**
         * The Bishop.
         */
        BISHOP("B", 300) {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        /**
         * The Rook.
         */
        ROOK("R", 500) {
            @Override                      // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return true;
            }
        },
        /**
         * The Queen.
         */
        QUEEN("Q", 900) {
            @Override                    // Added By Omer
            public boolean isKing() {
                return false;
            }

            @Override                   // Added By Ola
            public boolean isRook() {
                return false;
            }
        },
        /**
         * The King.
         */
        KING("K", 1000) {
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
        private int pieceValue;

        PieceType(final String pieceName, final int pieceValue){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
        // Added Ahmed
        @Override
        public String toString(){
            return this.pieceName;
        }
        // End Add

        /**
         * Is king boolean.
         *
         * @return the boolean
         */
        public abstract boolean isKing();// Added Omer

        /**
         * Is rook boolean.
         *
         * @return the boolean
         */
        public abstract boolean isRook(); // Added Ola

        /**
         * Gets piece value.
         *
         * @return the piece value
         */
        public int getPieceValue() {
            return this.pieceValue;
        }
    }

    /**
     * Is first move boolean.
     *
     * @return the boolean
     */
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
}
