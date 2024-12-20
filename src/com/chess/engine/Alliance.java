package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.user.BPlayer;
import com.chess.engine.user.Player;
import com.chess.engine.user.WPlayer;

public enum Alliance {
    WHITE {
        //   override
        public  int getDirection(){
            return -1;
        }

        // Added Mishkat
        @Override
        public int getOppositeDirection() {return 1; }

        @Override
        public boolean isPawnPromotionSquare(int position) {
         return BoardUtils.EIGHT_RANK[position];
        }
        // End Add

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        // Added Rawan
        @Override
        public Player choosePlayer(WPlayer whiteplayer, BPlayer blackplayer) {
            return whiteplayer;
        }  // End Add

    },
    BLACK {
        public int getDirection() {
            return 1;

        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        // Added Rawan
        @Override
        public Player choosePlayer(final WPlayer whiteplayer,
                                   final BPlayer blackplayer) {
            return blackplayer;
        } // End Add

        // Added Mishkat
        @Override
        public int getOppositeDirection() {return -1; }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.FIRST_RANK[position];
        }
        // End Add
    };





    public abstract int getDirection();

    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract int getOppositeDirection();
    public abstract boolean isPawnPromotionSquare(int position);

    public abstract Player choosePlayer(final WPlayer whiteplayer,
                                        final BPlayer blackplayer);

    public Alliance getPieceAlliance() {


        if (this.isWhite()){
            return WHITE;
        };
        if (this.isBlack()){
            return BLACK;
        }
        else {
            return null;
        }
    }
}