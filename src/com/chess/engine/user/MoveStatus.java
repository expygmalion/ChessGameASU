package com.chess.engine.user;

public enum MoveStatus {
    DONE {
        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public String statusPrint() {
            return "Done";
        }
    },
    ILLEGAL_MOVE {
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String statusPrint() {
            return "Illegal Move";
        }
    },  LEAVES_PLAYER_IN_CHECK{
        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String statusPrint() {
            return "Player in check";
        }
    };
    public abstract boolean isDone();
    public abstract String statusPrint();
}