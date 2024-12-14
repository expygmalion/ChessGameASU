package com.chess.engine.user.ai;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public interface MoveStrategy {
Move execute(Board board,int depth);
}
