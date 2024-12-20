package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO Khalid Creates the Class and provides structure and functionality
public class Queen extends Piece {

    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance, true); //Omer added a new parameter (piecetype)
    }
//todo <Mishkat>
    public Queen(Alliance pieceAlliance, int piecePosition, boolean b) {
        super();
    }
//End Add
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
                        || isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))
                {
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if (this.pieceAlliance != pieceAlliance) {
//                            todo <Mishkat>
                            legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
//                            End Add
                        }
                        break;
                    }
                }
            }
        }

        return List.copyOf(legalMoves); // Using List.copyOf for immutability
    }
    // Added Rawan
    @Override
    public Piece movePiece(Move move) {
        return new Queen( move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }// End Add

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }
    // Added Ahmed
    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }// End Add

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset
                == -7 || candidateOffset == 9);
    }
}