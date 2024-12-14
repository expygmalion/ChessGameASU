package com.chess.engine.user.ai;

import com.chess.engine.board.Board;
//by convention the more +ve the number then WHITE wins and opposite
public interface BoardEvaluator {
    int evaluate(Board board,int depth);

}
