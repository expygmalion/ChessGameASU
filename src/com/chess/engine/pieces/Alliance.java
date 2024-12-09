package com.chess.engine.pieces;


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
    };


    //    here above we must make an implement




    public abstract int getDirection();

    public abstract boolean isWhite();
    public abstract boolean isBlack();

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