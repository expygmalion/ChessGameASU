package com.chess.engine.board;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;


public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    public Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }


    //Added Rawan
    // Message_Taj: To inmprove redundancy, I pulled this upwards.
    public Board execute() {
        final Builder builder = new Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {

            //TODO HASHCODES
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance()); // Why the error?
        return builder.build();
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }


    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece,
                          final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            // Move Part
            return null;
        }
    }


    // Added Rawan
    public static final class PawnMove extends Move {

        public PawnMove(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board, final Piece movedPiece,
                              final int destinationCoordinate, Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    } // End Add

    // Added Mishkat
    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destinationCoordinate,
                                       Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }// End Add

    // Added Mishkat
    public static final class PawnJump extends Move {

        public PawnJump(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    } // End Add

    // Added Taj
    static abstract class CastleMove extends Move {

        public CastleMove(final Board board, final Piece movedPiece,
                          final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    } // End Add

    // Added Taj
    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board, final Piece movedPiece,
                                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    } // End Add

    // Added Taj
    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board, final Piece movedPiece,
                                   final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    } // End Add

    // Added Rawan
    public static final class NullMove extends Move {

        public NullMove(final Board board, final Piece movedPiece,
                                   final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        } // End Add
    }

    //TODO these extended move classes will help us print into PGN format
}

