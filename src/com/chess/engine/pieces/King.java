package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.Collection;


//TODO Mishkat creates the class King and provides structure and functionality
// Subsequent Additions are prevalent for advanced functionalities

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
//    represent the diagonal moves from its position
//    •	Diagonal moves: -9, -7, 7, 9.
//	•	Horizontal moves: -1, 1.
//	•	Vertical moves: -8, 8.

    public King(final int piecePostion,final Alliance pieceAlliance) {
        super(PieceType.KING, piecePostion, pieceAlliance); // Omer added a new parameter (piecetype)
    }
//    override

    // Added Ahmed
    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
    //  End Add

    // Added Rawan
    @Override
    public King movePiece(final Move move) {
        return new King (move.getDestinationCoordinate(),
                move.getMovedPiece().getPieceAlliance());
    } // End Add
//    implement move method

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCadidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCadidateOffset;

            if (isFirstColumnExclusion(this.piecePosition,currentCadidateOffset)||
                    isEighthColumnExclusion(this.piecePosition,currentCadidateOffset)) {
                continue;
            }


            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
//                ensures the destination coordinate lies within the valid board range (0–63 for a chessboard).

                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
//                    	If it’s an opponent’s piece, creates an AttackMove object to represent a capture.

                }


            }


        }


        return ImmutableList.copyOf(legalMoves);
//        ensuring that it cannot be modified after calculation.

    }


    private static boolean isFirstColumnExclusion (final int currentPosition , final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-9|| candidateOffset==-1 ||
                candidateOffset==7 );
//        	•	-9: Top-left diagonal.
//	•	-1: Horizontal left move.
//	•	7: Bottom-left diagonal.
    }
    private static boolean isEighthColumnExclusion (final int currentPosition , final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);


    }
}