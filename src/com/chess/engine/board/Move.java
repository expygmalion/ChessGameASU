package com.chess.engine.board;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import static com.chess.engine.board.Board.*;


/**
 * The type Move.
 */
public abstract class Move {
    /**
     * The Board.
     */
    protected final Board board;
    /**
     * The Moved piece.
     */
    protected final Piece movedPiece;
    /**
     * The Destination coordinate.
     */
    protected final int destinationCoordinate;
    /**
     * The Is first move.
     */
    protected final boolean isFirstMove;
    /**
     * The constant NULL_MOVE.
     */
    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = (movedPiece != null) ? movedPiece.isFirstMove() : false;  // Prevent NullPointerException
    }
    private Move(final Board board, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = null;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = false;
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getDestinationCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate()
                && getMovedPiece() == otherMove.getMovedPiece();
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    /**
     * Gets board.
     *
     * @return the board
     */
// Taj Add: Create getters:
    public Board getBoard() {
        return this.board;
    }

    /**
     * Gets destination coordinate.
     *
     * @return the destination coordinate
     */
    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    /**
     * Gets moved piece.
     *
     * @return the moved piece
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    /**
     * Is attack boolean.
     *
     * @return the boolean
     */
    public boolean isAttack (){
        return false;
    }

    /**
     * Is castling move boolean.
     *
     * @return the boolean
     */
    public boolean isCastlingMove(){
        return false;
    }

    /**
     * Get attacked piece piece.
     *
     * @return the piece
     */
    public Piece getAttackedPiece(){
        return null;
    }
    // End getter Methods


    /**
     * Hashcode int.
     *
     * @return the int
     */
    public abstract int hashcode(); // todo Added Mishkat

    /**
     * Execute board.
     *
     * @return the board
     */
//Added Rawan
    // Message_Taj: To improve redundancy, I pulled this upwards.
    public Board execute() {
        final Builder builder = new Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {

            //TODO HASHCODES
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance()); // Why the error?
        return builder.build();
    }

    /**
     * Gets attack piece.
     *
     * @return the attack piece
     */
    public abstract Piece getAttackPiece(); // todo added Mishkat

    /**
     * The type Major move.
     */
    public static final class MajorMove extends Move {

        /**
         * Instantiates a new Major move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         */
        public MajorMove(final Board board, final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public Piece getAttackPiece() {
            return null;
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }


    }

    /**
     * The type Attack move.
     */
    public static class AttackMove extends Move {
        /**
         * The Attacked piece.
         */
        final Piece attackedPiece;

        /**
         * Instantiates a new Attack move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param attackedPiece         the attacked piece
         */
        public AttackMove(final Board board, final Piece movedPiece,
                          final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
        // Added Ola
        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()) {
                if(!this.attackedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance());
            return builder.build();
        }

        @Override
        public Piece getAttackPiece() {
            return null;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public boolean isAttack(){
            return true;
        }
        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }
        @Override
        public boolean equals(final Object other) {
            if(this == other){
                return true;
            }
            if (!(other instanceof  AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }
        // End Add
    }


    /**
     * The type Pawn move.
     */
// Added Rawan
    public static final class PawnMove extends Move {

        /**
         * Instantiates a new Pawn move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         */
        public PawnMove(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        // todo Added Mishkat

        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public boolean equals (final Object other){
            return this ==other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public Piece getAttackPiece() {
            return null;
        }

        @Override
        public String toString() {
            if (movedPiece.getPieceType() == Piece.PieceType.PAWN) {
                return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
            }
            return super.toString();
        }
        // End add

    }

    /**
     * The type Pawn attack move.
     */
    public static class PawnAttackMove extends AttackMove {

        /**
         * Instantiates a new Pawn attack move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param attackedPiece         the attacked piece
         */
        public PawnAttackMove(final Board board, final Piece movedPiece,
                              final int destinationCoordinate, Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
        @Override
        public boolean equals (final Object other ){
            return this==other || other instanceof PawnAttackMove && super.equals(other);
        }
        @Override
        public String toString (){
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0,1) + "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
//        the function above shows that when the pawn kill a piece it recorde the kill in the file following by X indicate that it has been removed.
    } // End Add

    /**
     * The type Pawn en passant attack move.
     */
// Added Mishkat
    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        /**
         * Instantiates a new Pawn en passant attack move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param attackedPiece         the attacked piece
         */
        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destinationCoordinate,
                                       Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }



        @Override
        public boolean equals (final Object other ){
            return this==other || other instanceof  PawnEnPassantAttackMove && super.equals(other);
        }
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }}

            for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()){
                if (!piece.equals((this.getAttackedPiece()))) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance());
            return builder.build();
        }

    }// End Add

    /**
     * The type Pawn promotion.
     */
// Added Mishkat
//    TODO <Mishkat>
    public static class PawnPromotion extends Move{

        /**
         * The Decorated move.
         */
        final Move decoratedMove;
        /**
         * The Promoted pawn.
         */
        final Pawn promotedPawn;

        /**
         * Instantiates a new Pawn promotion.
         *
         * @param decoratedMove the decorated move
         */
        public PawnPromotion(final Move decoratedMove) {
            super(decoratedMove.getBoard(),decoratedMove.getMovedPiece(),decoratedMove.getDestinationCoordinate());
            this.decoratedMove=decoratedMove;
            this.promotedPawn=(Pawn) decoratedMove.getMovedPiece();

        }
        @Override
        public int hashcode(){
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }
        @Override
        public boolean equals(final Object other ){
            return this == other || other instanceof  PawnPromotion && (super.equals(other));
        }




        @Override
        public Board execute(){
            final Board pawnMoveBoard = this.decoratedMove.execute();
            final Board.Builder builder = new Builder();
            for(final Piece piece : pawnMoveBoard.currentPlayer().getActivePieces()){
                if(!this.promotedPawn.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : pawnMoveBoard.currentPlayer().getopponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
            builder.setMoveMaker(pawnMoveBoard.currentPlayer().getAlliance());
            return builder.build();


        }

        @Override
        public Piece getAttackPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public boolean isAttack(){
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()) + "-" +
                    BoardUtils.getPositionAtCoordinate(this.destinationCoordinate) + "=Q" ;
        }

    }

    /**
     * The type Pawn jump.
     */
// Added Mishkat
    public static final class PawnJump extends Move {

        /**
         * Instantiates a new Pawn jump.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         */
        public PawnJump(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public int hashcode() {
            return 0;
        }
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
        // Added Ola
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance());
            return builder.build();

        } // End Add

        @Override
        public Piece getAttackPiece() {
            return null;
        }
    } // End Add
    // Add this as a new inner class in the Move class
    public static class CheckMateMove extends Move {
        private final Move decoratedMove;
        private final boolean isCheckmate;

        public CheckMateMove(final Move decoratedMove, final boolean isCheckmate) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.isCheckmate = isCheckmate;
        }

        @Override
        public Board execute() {
            final Board moveBoard = this.decoratedMove.execute();
            if (isCheckmate) {
                System.out.println("Checkmate! " + moveBoard.currentPlayer().getopponent().toString() + " wins!");
            }
            return moveBoard;
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof CheckMateMove && super.equals(other);
        }

        @Override
        public int hashcode() {
            return decoratedMove.hashCode();
        }

        @Override
        public Piece getAttackPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return decoratedMove.toString() + "#"; // Chess notation for checkmate
        }
    }

    /**
     * The type Castle move.
     */
// Added Ola
    static abstract class CastleMove extends Move {
        /**
         * The Castle rook.
         */
        protected final Rook castleRook;
        /**
         * The Castle rook start.
         */
        protected final int castleRookStart;
        /**
         * The Castle rook destination.
         */
        protected final int castleRookDestination;

        /**
         * Instantiates a new Castle move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param castleRook            the castle rook
         * @param castleRookStart       the castle rook start
         * @param castleRookDestination the castle rook destination
         */
        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        /**
         * Gets castle rook.
         *
         * @return the castle rook
         */
        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }
        //Added Ola
        @Override
        public Board execute() {
            final Builder builder = new Builder();

            // Retain all opponent pieces
            for (final Piece piece : this.board.currentPlayer().getopponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            // Retain all allied pieces except the king and rook involved in castling
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!piece.equals(this.movedPiece) && !piece.equals(this.castleRook)) {
                    builder.setPiece(piece);
                }
            }

            // Move the king
            builder.setPiece(this.movedPiece.movePiece(this));

            // Move the rook
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance()));

            // Update the move maker
            builder.setMoveMaker(this.board.currentPlayer().getopponent().getAlliance());

            return builder.build();
        } // End Add
        //        todo <Mishkat>
        @Override
        public int hashCode (){
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }
        public boolean equals(final Object other ){
            if (this==other ){
                return true;

            }
            if (!(other instanceof CastleMove)) {
                return false;

            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove)&& this.castleRook.equals(otherCastleMove.getCastleRook());
        }
//        End Add
    } // End Add


    /**
     * The type King side castle move.
     */
// Added Taj
    public static final class KingSideCastleMove extends CastleMove {

        /**
         * Instantiates a new King side castle move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param castleRook            the castle rook
         * @param castleRookStart       the castle rook start
         * @param castleRookDestination the castle rook destination
         */
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination /* for king */) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        //        todo <Mishkat>
        @Override
        public boolean equals (final Object other ){
            return this==other || other instanceof  KingSideCastleMove && super.equals(other);
        }

        @Override
        public Piece getAttackedPiece() {
            return null;
        }
//End Add

        @Override
        public String toString(){
            return "O-O";
        }

        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public Piece getAttackPiece() {
            return null;
        }


    } // End Add

    /**
     * The type Queen side castle move.
     */
// Added Rawan
    public static final class QueenSideCastleMove extends CastleMove {

        /**
         * Instantiates a new Queen side castle move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param castleRook            the castle rook
         * @param castleRookStart       the castle rook start
         * @param castleRookDestination the castle rook destination
         */
        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }


        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public Piece getAttackPiece() {
            return null;
        }

        //        todo <Mishkat>
        @Override
        public boolean equals (final Object other ){
            return this==other || other instanceof  QueenSideCastleMove && super.equals(other);
        }

        @Override
        public Piece getAttackedPiece() {
            return null;
        }

        //End Add

        //Added Ola
        @Override
        public String toString(){
            return "O-O-O";
        }


    } // End Add

    /**
     * The type Null move.
     */
// Added Rawan
    public static final class NullMove extends Move {

        /**
         * Instantiates a new Null move.
         */
        public NullMove() {
            super(null, null, -1);
            }

        @Override
        public int hashcode() {
            return 0;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot Instantiate, Null Move Not Made");
        } // End Rawan

        @Override
        public Piece getAttackPiece() {
            return null;
        }

    }


    //TODO these extended move classes will help us print into PGN format


    /**
     * The type Move creator.
     */
// Added Rawan
    public static class MoveCreator {

        private MoveCreator() {
            throw new RuntimeException("Not instantiatable!");
        }

        /**
         * Create move move.
         *
         * @param board                 the board
         * @param currentCoordinate     the current coordinate
         * @param destinationCoordinate the destination coordinate
         * @return the move
         */
        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    } // End Add

    /**
     * The type Major attack move.
     */
    public static class MajorAttackMove extends  AttackMove{

        /**
         * Instantiates a new Major attack move.
         *
         * @param board                 the board
         * @param movedPiece            the moved piece
         * @param destinationCoordinate the destination coordinate
         * @param attackedPiece         the attacked piece
         */
        public MajorAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
        @Override
        public boolean equals (final Object other ){
            return this==other || other instanceof  MajorAttackMove && super.equals(other);
        }
        @Override
        public String toString (){
            return movedPiece.getPieceType() +"x"+ BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }
    // Added Rawan
    private int getCurrentCoordinate() {
        return this.getMovedPiece().getPiecePosition();
    } // End Add
}

