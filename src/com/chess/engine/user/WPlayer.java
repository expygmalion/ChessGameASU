package com.chess.engine.user;

import com. chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class WPlayer extends Player{

    public WPlayer(final Board board,
                   final Collection<Move> whiteStandardLegalMoves,
                   final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {

        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {

        return Alliance.WHITE;
    }

    @Override
    public Player getopponent() {

        return this.board.blackPlayer();
    }
// Added Ola
    @Override
    protected Collection<Move> calculateKingCastles(
            final Collection<Move> playerLegals,
            final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // White's King-side Castle
            if (!this.board.getTile(61).isTileFilled() &&
                    !this.board.getTile(62).isTileFilled()) {

                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileFilled() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new KingSideCastleMove(
                                this.board,
                                this.playerKing,
                                62,
                                (Rook) rookTile.getPiece(),
                                rookTile.getTileCoordinate(),
                                61
                        ));
                    }
                }
            }

            // White's Queen-side Castle
            if (!this.board.getTile(59).isTileFilled() &&
                    !this.board.getTile(58).isTileFilled() &&
                    !this.board.getTile(57).isTileFilled()) {

                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isTileFilled() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttacksOnTile(58, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(59, opponentsLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()
                ) {
                    kingCastles.add(new QueenSideCastleMove(
                            this.board,
                            this.playerKing,
                            58,
                            (Rook) rookTile.getPiece(),
                            rookTile.getTileCoordinate(),
                            59
                    ));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    } // End Add
}

