package com.chess.gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.chess.engine.user.MoveTransition;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.Lists;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.border.Border;

import static javax.swing.SwingUtilities.isLeftMouseButton; // imported from API
import static javax.swing.SwingUtilities.isRightMouseButton; // imported from API


public class Table {
    private final  JFrame gameFrame;
    private final BoardPanel boardPanel;
    private BoardDirection boardDirection;
    private final MoveLog moveLog;
    private final static Dimension OuterFrameDimensions = new Dimension(800,600);
    private final static Dimension BoardPanelDimensions = new Dimension(400, 350);
    private final static Dimension TilePanelDimensions = new Dimension(10, 10);
    private Board CHESSBOARD; // should not be final
    private Board FileBoard;
    private static String ICONPATH = "ArtWork/Icons/";
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private final GameHistoryPanel gameHistoryPanel;
    private final DeadPiecePanel deadPiecePanel;



    public GameHistoryPanel getGameHistoryPanel() {
        return gameHistoryPanel;
    }

    public DeadPiecePanel getDeadPiecePanel() {
        return deadPiecePanel;
    }

    public Table() {
        this.gameHistoryPanel = new GameHistoryPanel();
        this.deadPiecePanel = new DeadPiecePanel();
        this.gameFrame = new JFrame("Chess Game");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OuterFrameDimensions);
        this.gameFrame.setVisible(true); // Display the frame
        this.CHESSBOARD = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.deadPiecePanel, BorderLayout.WEST);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.moveLog = new MoveLog();
        this.FileBoard = Board.CreateFileBoard();
    }
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;

    }
    private void saveBoardStateToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("board_state.txt", false))) { // Set append to false
            // Convert the current board state to a string representation
            StringBuilder boardState = new StringBuilder();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                Tile tile = CHESSBOARD.getTile(i);
                if (tile.isTileOccupied()) {
                    Piece piece = tile.getPiece();
                    boardState.append(piece.getPieceType().toString())
                            .append(piece.getPieceAlliance().toString().substring(0, 1)) // Alliance abbreviation (W/B)
                            .append(i) // Tile coordinate
                            .append(" ");
                } else {
                    boardState.append("empty ").append(i).append(" "); // Indicate empty tile
                }
            }
            boardState.append("\n"); // Add a newline after each board state
            writer.write(boardState.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenu = new JMenuItem("Flip Board");
        flipBoardMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(CHESSBOARD);
            }
        });
        preferencesMenu.add(flipBoardMenu);
        return preferencesMenu;
    }

    public enum BoardDirection {
        NORMAL{
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles){return boardTiles;}
            @Override
            BoardDirection opposite(){
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles){return Lists.reverse(boardTiles);}
            @Override
            BoardDirection opposite(){
                return NORMAL;
            }
        };
        abstract List<TilePanel> traverse(List<TilePanel> boardTiles);
        abstract BoardDirection opposite();


    }

    private JMenu createFileMenu() {

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("‘Upload PGN");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening PGN");
            }
        });
        fileMenu.add(openPGN);
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;
        public BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BoardPanelDimensions);
            setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
            setBackground(new Color(97, 68, 34));
            validate();
        }
        public void drawBoard(final Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) { // boardtiles is norm
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            revalidate();
            repaint();
        }
    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        void clear() {
            this.moves.clear();
        }

        Move removeMove(final int index) {
            return this.moves.remove(index);
        }

        boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }

    }
    public class TilePanel extends JPanel {
        private final int tileID;

        public TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TilePanelDimensions);
            assignTileColor();
            assignTilePieceIcon(CHESSBOARD);
            // TODO Step 2: Add Mouse Listener
            //Taj Added
            addMouseListener(new MouseListener() {
                public void mouseClicked(final MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        // Right-click resets selection
                        System.out.print("Cancelling... Selection");
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        SwingUtilities.invokeLater(() -> boardPanel.drawBoard(CHESSBOARD)); // removing highlight

                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            // First click: select the piece
                            sourceTile = CHESSBOARD.getTile(tileID);
                            humanMovedPiece = sourceTile.getPiece();

                            // Ensure the selected piece belongs to the current player
                            if (humanMovedPiece == null || humanMovedPiece.getPieceAlliance() != CHESSBOARD.currentPlayer().getAlliance()) {
                                sourceTile = null; // Deselect if the piece doesn't belong to the current player
                            }
                        } else {
                            // Second click: attempt to move
                            destinationTile = CHESSBOARD.getTile(tileID);
                            final Move move = Move.MoveFactory.createMove(
                                    CHESSBOARD,
                                    sourceTile.getTileCoordinate(),
                                    destinationTile.getTileCoordinate()
                            );

                            // Explicitly check for NULL_MOVE
                            if (move != Move.NULL_MOVE) {
                                final MoveTransition transition = CHESSBOARD.currentPlayer().makeMove(move);

                                if (transition.getMoveStatus().isDone()) {
                                    CHESSBOARD = transition.getTransitionBoard();
                                    moveLog.addMove(move);
                                    System.out.println("Move successful: " + move + "State Saved");
                                    saveBoardStateToFile();


                                } else {
                                    System.out.println("Invalid move: " + transition.getMoveStatus().statusPrint());
                                }
                            } else {
                                System.out.println("No legal move found between " +
                                        sourceTile.getTileCoordinate() + " and " +
                                        destinationTile.getTileCoordinate());
                            }

                            // Reset selection
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }

                        SwingUtilities.invokeLater(() -> {
                            gameHistoryPanel.redo(CHESSBOARD, moveLog);
                            deadPiecePanel.redo(moveLog);
                            boardPanel.drawBoard(CHESSBOARD);

                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
                //End Add
            });
            validate();
        }
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }
        private void assignTilePieceIcon(final Board board){
            this.removeAll();
            if(board.getTile(this.tileID).isTileOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(ICONPATH
                            + board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0,1)
                            + board.getTile(this.tileID).getPiece().toString()+ ".png"));

                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    throw new RuntimeException(e); //or e.printStackTrace()
                }
            }
        }
        /* private void highlightLegals(final Board board) {
            if(true) {
                for(final Move move : pieceLegalMoves(board)) {
                    if(move.getDestinationCoordinate() == this.tileID) {
                        try {
                            OverlayLayout overlay = new OverlayLayout(this); // Set Overlay layout for Tile Panel
                            BufferedImage image = ImageIO.read(new File("ArtWork/Icons/Dot.png"));
                            add(new JLabel(new ImageIcon(image)));   // Add the dot on top of this tile panel
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }*/


        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);}
            return Collections.emptyList();
        }



        // Not preferred perhaps
        private void assignTileColor() {
            final Color lightTileColor = new Color(235, 236, 208);
            final Color darkTileColor = new Color(115, 149, 82);
            final Color checkTileColor = new Color(255, 10, 100); // Red color for the tile in check

            int row = tileID / BoardUtils.NUM_TILES_PER_ROW;  // ODD
            int col = tileID % BoardUtils.NUM_TILES_PER_ROW; // EVEN
            final Border highlightBorder = BorderFactory.createLineBorder(
                    ((row + col) % 2 == 0 ?
                            new Color(92, 0, 0) :
                            new Color(1, 44, 0))
                    , 3);

            // Highlight the king's tile if in check
            if (CHESSBOARD.currentPlayer().isInCheck()) {
                final Piece kingPiece = CHESSBOARD.currentPlayer().getPlayerKing();
                if (kingPiece != null && kingPiece.getPiecePosition() == this.tileID) {
                    setBackground(checkTileColor);
                    setBorder(null); // Optional: No border when in check
                    return;
                }
            }

            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == CHESSBOARD.currentPlayer().getAlliance()) {
                for (final Move move : pieceLegalMoves(CHESSBOARD)) {
                    if (move.getDestinationCoordinate() == this.tileID) {
                        setBorder(highlightBorder); // Add a border for the highlight
                        setBackground((row + col) % 2 == 0 ? lightTileColor : darkTileColor);
                        return;
                    }
                }
            }

            setBorder(null); // Remove border when not highlighted
            setBackground((row + col) % 2 == 0 ? lightTileColor : darkTileColor);
        }


    }
}
