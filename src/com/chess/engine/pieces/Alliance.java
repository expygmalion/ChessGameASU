package com.chess.engine.pieces;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1; // White pieces move "up" on the board.
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
        @Override
        public int getDirection() {
            return 1; // Black pieces move "down" on the board.
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

    // Abstract methods to enforce implementation in enum constants.
    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    // Returns the Alliance constant (self-reference for enums).
    public Alliance getPieceAlliance() {
        return this; // Since the enum itself represents WHITE or BLACK, simply return it.
    }
}
