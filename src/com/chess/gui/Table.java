package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private final static Dimension OuterFrameDimensions = new Dimension(600,600);
    private final static Dimension BoardPanelDimensions = new Dimension(400, 350);
    private final static Dimension TilePanelDimensions = new Dimension(10, 10);
    private final static int moderator = 10;
    private Board CHESSBOARD; // should not be final
    private static String ICONPATH = "ArtWork/Icons/";
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    public Table() {
        this.gameFrame = new JFrame("Chess Game");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OuterFrameDimensions);
        this.gameFrame.setVisible(true); // Display the frame
        this.CHESSBOARD = Board.createStandardBoard();

        boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    }
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;

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
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.BLACK);
            validate();
        }
        public void drawBoard(final Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            revalidate();
            repaint();
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
                        sourceTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(e)){
                        if (sourceTile == null) {
                            // First click: select the piece
                            sourceTile = CHESSBOARD.getTile(tileID);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
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
                                    System.out.println("Move successful: " + move);
                                } else {
                                    System.out.println("Invalid move: " + move);
                                    System.out.println("Move status: " + transition.getMoveStatus());
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

                        SwingUtilities.invokeLater(() -> boardPanel.drawBoard(CHESSBOARD));
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
            final Color lightTileColor = new Color(238, 238, 210);
            final Color darkTileColor = new Color(118, 150, 86);
            final Border highlightBorder = BorderFactory.createLineBorder(new Color(255, 0, 0), 3);

            int row = tileID / BoardUtils.NUM_TILES_PER_ROW;
            int col = tileID % BoardUtils.NUM_TILES_PER_ROW;

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
