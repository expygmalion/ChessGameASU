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

    public Collection<Move> calculateLegalMoves(final Board board) {
//        the move has a class in the previous code
        final List<Move> legalMoves = new ArrayList();
// I added this now!
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getPieceAlliance().getDirection() * currentCandidateOffset);
//             would apply 8 for black and -8 for white

//            override from previous code
//            the formula above calculate the movement of the black pawn(because they move in the positive direction)
//            to solve this we must declare a methode in the alliance class returns direction

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//                Pawn Promotion:
               if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
             legalMoves.add(new Move.PawnPromotion(new Move.PawnMove(board,this,candidateDestinationCoordinate)));

               }
               else {
                legalMoves.add(new Move.PawnMove(board, this, candidateDestinationCoordinate) );
            }}
//non-attacking move

            else if (currentCandidateOffset == 16 && this.isFirstMove &&
                    (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))
//the conditions above represent the positions of the black and white
            {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);


                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            }

//     the pawn jump
//            todo <Mishkat>
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {

                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {

                            legalMoves.add(new Move.PawnPromotion(new Move.MajorMove(board, this, candidateDestinationCoordinate)));

                        }else {

                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }}
                }
                else if (board.getEnPassantPawn()!= null){
                    if (board.getEnPassantPawn().getPiecePosition()== (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance!= pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this,candidateDestinationCoordinate,pieceOnCandidate));
//                       here if the opponent pawn in the adjacent direction (next to you)  and the same row the pawn will make the En Passant Move and move to the diagonal direction after killing the opponent pawn.
//                            En Passant Move related to Pawn Jump Move.
//                            here if the Off Set =7 && the pawn is black the diagonal move representing by +7 squares according to it Alliance.
                        }
                    }
                }

            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                        } else {

                            legalMoves.add(new Move.PawnPromotion(new Move.MajorMove(board, this, candidateDestinationCoordinate)));

                        }


                    }
                }else if (board.getEnPassantPawn()!= null){
                    if (board.getEnPassantPawn().getPiecePosition()== (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance!= pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this,candidateDestinationCoordinate,pieceOnCandidate));
//                       here if the opponent pawn in the adjacent direction (next to you)  and the same row the pawn will make the En Passant Move and move to the diagonal direction after killing the opponent pawn.
//                            if the pawn is White the diagonal move represent -9 according to Withe Alliance.
//                            REMEMBER THERE IS ONLY ONE EN PASSANT MOVE EVER IN THE BOARD
                        }
                    }
                }
            }
//kill in diagonal


        }
// I sing the song of marching men
        return ImmutableList.copyOf(legalMoves);


    }
   public Piece getPromotionPiece(){
        return new Queen(this.pieceAlliance,this.piecePosition,false);
//       for simplicity the promotion always goes to Queen.
//       set to false cause this is not the first move for Queen.
   }
}