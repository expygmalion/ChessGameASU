package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

// King class
public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
    private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;

    // Constructor for King
    public King(final int piecePosition, final Alliance pieceAlliance, final boolean kingSideCastleCapable, final boolean queenSideCastleCapable) {
        super(PieceType.KING, piecePosition, pieceAlliance, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(final Alliance alliance,
                final int piecePosition,
                final boolean isFirstMove,
                final boolean isCastled,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) {
        super(PieceType.KING, piecePosition, alliance, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }

    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    @Override
    public King movePiece(final Move move) {
        return new King(this.pieceAlliance, move.getDestinationCoordinate(), false, move.isCastlingMove(), false, false);
    }

    // Calculate legal moves for King
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            // Checking for exclusions based on position (first and eighth columns)
            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }

            final Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);

            if (pieceAtDestination == null) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else {
                final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                if (this.pieceAlliance != pieceAtDestinationAlliance) {
                    legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                }
            }
        }

        // Castling logic
        if (this.isFirstMove() && !this.isCastled) {
            if (this.kingSideCastleCapable) {
                // Logic to check if castling is possible (no pieces between King and Rook)
                // Add castling move to legalMoves
            }
            if (this.queenSideCastleCapable) {
                // Logic to check if castling is possible (no pieces between King and Rook)
                // Add castling move to legalMoves
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    // Check if the move is on the first column
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
    }

    // Check if the move is on the eighth column
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);
    }
}
