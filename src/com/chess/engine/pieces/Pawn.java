package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO MISHKAT Creates the Class and provides structure and functionality
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 9, 7};

    public Pawn(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, true); // Omer added a new parameter (piecetype)
    }

    public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }


// count 8 squares to get the opposite move

    // Added Ahmed
    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    } // End Add

    // Added Rawan
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(),
                move.getMovedPiece().getPieceAlliance());
    }// End Add
// implementation method
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
//        the move has a class in the previous code
        final List<Move> legalMoves = new ArrayList();
// I added this now!

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
             int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getPieceAlliance().getDirection() * currentCandidateOffset);

//             would apply 8 for black and -8 for white

//            override from previous code
//            the formula above calculate the movement of the black pawn(because they move in the positive direction)
//            to solve this we must declare a methode in the alliance class returns direction

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
//non-attacking move

            else if (currentCandidateOffset == 16 && this.isFirstMove &&
                    ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
// todo  need to Do PROMOTION
                final int behindCandidateDestinationCoordinate = this.piecePosition +
                        (this.pieceAlliance.getDirection() * 8);
//the conditions above represent the positions of the black and white

                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
                }
            }

//     the pawn jump
            else if (currentCandidateOffset == 7) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }

            else if (currentCandidateOffset == 9) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.PawnAttackMove(
                                board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }

//kill in diagonal
//            promotion is missing


        }
// I sing the song of marching men
        return ImmutableList.copyOf(legalMoves);


    }
}