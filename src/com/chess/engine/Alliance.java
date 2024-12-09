package com.chess.engine;


import com.chess.engine.Player.BlackPlayer;
import com.chess.engine.Player.Player;
import com.chess.engine.Player.WhitePlayer;

public enum Alliance {
    WHITE {
        //   override
        public  int getDirection(){
            return -1;
        }

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
        public Player choosePlayer(WhitePlayer whiteplayer, BlackPlayer blackplayer) {
            return blackplayer;
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
        public Player choosePlayer(final WhitePlayer whiteplayer,
                                   final BlackPlayer blackplayer) {
            return whiteplayer;
        } // End Add
    };





    public abstract int getDirection();

    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(final WhitePlayer whiteplayer,
                                        final BlackPlayer blackplayer);

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