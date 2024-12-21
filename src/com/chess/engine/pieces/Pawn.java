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
      // CANDIDATE_MOVE_COORDINATE TO PAWN_MOVE_OFFSETS
    private final static int[]  PAWN_MOVE_OFFSETS = {8, 16, 9, 7};

    public Pawn(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance); // Omer added a new parameter (piecetype)

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
        return new Pawn(move.getTargetPosition(),
                move.getMovedPiece().getPieceAlliance());
    }// End Add
// implementation method

    public Collection<Move> calculateLegalMoves(final Board board) {
//        the move has a class in the previous code
        final List<Move> legalMoves = new ArrayList();
// I added this now!
        for (final int currentCandidateOffset :  PAWN_MOVE_OFFSETS) {

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getPieceAlliance().getDirection() * currentCandidateOffset);
//             would apply 8 for black and -8 for white

//            override from previous code
//            the formula above calculate the movement of the black pawn(because they move in the positive direction)
//            to solve this we must declare a methode in the alliance class returns direction

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileFilled()) {
//   need to Do PROMOTION
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
//non-attacking move

            else if (currentCandidateOffset == 16 && this.isFirstMove &&
                    (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))
//the conditions above represent the positions of the black and white
            {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);


                if (!board.getTile(behindCandidateDestinationCoordinate).isTileFilled() &&
                        !board.getTile(candidateDestinationCoordinate).isTileFilled()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            }

//     the pawn jump
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileFilled()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileFilled()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

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