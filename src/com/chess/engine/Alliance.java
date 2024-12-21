package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.user.BPlayer;
import com.chess.engine.user.Player;
import com.chess.engine.user.WPlayer;

/**
 * The enum Alliance.
 */
public enum Alliance {
    /**
     * The White.
     */
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
    /**
     * The Black.
     */
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


    /**
     * Gets direction.
     *
     * @return the direction
     */
    public abstract int getDirection();

    /**
     * Is white boolean.
     *
     * @return the boolean
     */
    public abstract boolean isWhite();

    /**
     * Is black boolean.
     *
     * @return the boolean
     */
    public abstract boolean isBlack();

    /**
     * Gets opposite direction.
     *
     * @return the opposite direction
     */
    public abstract int getOppositeDirection();

    /**
     * Is pawn promotion square boolean.
     *
     * @param position the position
     * @return the boolean
     */
    public abstract boolean isPawnPromotionSquare(int position);

    /**
     * Choose player player.
     *
     * @param whiteplayer the whiteplayer
     * @param blackplayer the blackplayer
     * @return the player
     */
    public abstract Player choosePlayer(final WPlayer whiteplayer,
                                        final BPlayer blackplayer);

    /**
     * Gets piece alliance.
     *
     * @return the piece alliance
     */
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