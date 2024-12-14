package com.chess.engine.user.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.user.MoveTransition;

//Move execute uses the minimax algorithm to calculate the best move

public class MiniMax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth){
        this.boardEvaluator=new StandardBoardEvaluate();
        this.searchDepth=searchDepth;
    }

    @Override
    public String toString (){
       return "MiniMax" ;
    }

    @Override
    public Move execute(Board board) {

        final long startTime= System.currentTimeMillis(); //how long will it take to execute this method

        Move bestMove =null;

int highestSeenValue=Integer.MIN_VALUE;
int lowestSeenValue=Integer.MAX_VALUE;
int currentValue;

System.out.println(board.currentPlayer()+"THINKING with depth ="+this.searchDepth);

int numMoves =board.currentPlayer().getlegalMoves().size() ;

for(final Move move:board.currentPlayer().getlegalMoves()){

final MoveTransition moveTransition=board.currentPlayer().makeMove(move);
if (moveTransition.getMoveStatus().isDone()){

    currentValue= board.currentPlayer().getAlliance().isWhite()?
            min(moveTransition.getTransitionBoard(),this.searchDepth -1):
            max(moveTransition.getTransitionBoard(),this.searchDepth -1);

     if (board.currentPlayer().getAlliance().isWhite()&& currentValue>=highestSeenValue){
         highestSeenValue=currentValue;
         bestMove=move;
     } else if(board.currentPlayer().getAlliance().isBlack()&& currentValue<=lowestSeenValue){
         lowestSeenValue=currentValue;
         bestMove=move;
     }

   }
}
final long executionTime =System.currentTimeMillis()-startTime;

return bestMove;
    }
    public int min(final Board board ,
                   final int depth){
        if (depth==0||isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board,depth);
        }
        /*lowestSeenValue is assigned to the highest number possible and we are
         never going to evaluate a board to the score(no valid board can have this possible value)*/
        int lowestSeenValue=Integer.MAX_VALUE;
        for (final Move move:board.currentPlayer().getlegalMoves()){
            final MoveTransition moveTransition= board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue=max(moveTransition.getTransitionBoard(),depth-1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue=currentValue;
                }

            }
        }
        return lowestSeenValue;
    }
 //game over
    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isInCheckMate()||
                board.currentPlayer().isInStaleMate() ;
    }

    public int max(final Board board,
                   final int depth){
        if (depth==0||isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board,depth);
        }
        /*highestSeenValue is an impossibly small number that we will never evaluate to  */
        int highestSeenValue=Integer.MIN_VALUE;
        for (final Move move:board.currentPlayer().getlegalMoves()){
            final MoveTransition moveTransition= board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue=min(moveTransition.getTransitionBoard(),depth-1);
                if (currentValue <= highestSeenValue) {
                     highestSeenValue=currentValue;
                }

            }
        }
        return highestSeenValue;
    }


}
