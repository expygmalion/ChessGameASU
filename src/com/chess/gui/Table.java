package com.chess.gui;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final  JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static Dimension OuterFrameDimensions = new Dimension(600,600);
    private final static Dimension BoardPanelDimensions = new Dimension(400, 350);
    private final static Dimension TilePanelDimensions = new Dimension(10, 10);
    private final Board CHESSBOARD;
    private static String ICONPATH = "ArtWork/Icons/";


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
        final JMenuItem openPGN = new JMenuItem("Load PGN");
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
            validate();
        }
    }
    private class TilePanel extends JPanel {
        private final int tileID;

        public TilePanel(final BoardPanel boardPanel, final int tileID) {
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(TilePanelDimensions);
            assignTileColor();
            assignTilePieceIcon(CHESSBOARD);
            validate();
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
        // Not preferred perhaps
        private void assignTileColor() {
            // Defining the colors of the light and dark tiles (you can modify the colors here)
            final Color lightTileColor = new Color(240, 217, 181);  // Chess.com light square color (hex #F0D9B5)
            final Color darkTileColor = new Color(121, 85, 61);  // Chess.com dark square color (hex #795533)

            // Determine the row and column for the current tile
            int row = tileID / BoardUtils.NUM_TILES_PER_ROW;
            int col = tileID % BoardUtils.NUM_TILES_PER_ROW;

            // Assign color based on the parity of the sum of row and column (alternates colors)
            if ((row + col) % 2 == 0) {
                setBackground(lightTileColor); // Light square
            } else {
                setBackground(darkTileColor); // Dark square
            }
        }
    }
}
