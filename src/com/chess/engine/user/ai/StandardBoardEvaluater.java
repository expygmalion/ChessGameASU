package com.chess.engine.user.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.user.Player;

public class StandardBoardEvaluater implements BoardEvaluator {

    private static final int CHECK_BONUS=50;
    private static final int CHECK_MATE_BONUS =10000 ;
    private static final int DEPTH_BONUS=100;
    private static final int CASTLE_BONUS = 60;

    @Override
    public int evaluate(final Board board,
                        final int depth) {
        return scorePlayer(board,board.whitePlayer(),depth) -
                scorePlayer(board,board.whitePlayer(),depth);
        //if white has an advantage the score will be +ve and the opposite
    }

    private int scorePlayer(Board board, Player player, int depth) {
        return pieceValue(player)+
                mobility(player)+
                check(player)+
                checkmate(player,depth)+
                castled(player);
        //+checkmate ,check,castled,mobility....
    }

    private static int castled(Player player) {
        return player.isCastled()? CASTLE_BONUS: 0;
    }

    //if we found checkmate at a higher depth(sooner before depth reaches 0)we want to give bigger bonus
    private static int checkmate(Player player,int depth) {
        return player.getopponent().isInCheckMate()? CHECK_MATE_BONUS*depthBonus(depth):0;
    }

    private static int depthBonus(int depth) {
        return depth== 0?1 :DEPTH_BONUS *depth;
    }


    private static int check(final Player player) {
        return player.getopponent().isInCheck()? CHECK_BONUS:0;
    }

    //how many legal moves does the player has in that position
    private static int mobility(Player player) {
     return player.getlegalMoves().size();
    }

    private static int pieceValue(final Player player){
        int pieceValueScore=0;
        for (final Piece piece:player.getActivePieces()){
            pieceValueScore+=piece.getPieceValue();
        }
        return pieceValueScore;
    }

}
