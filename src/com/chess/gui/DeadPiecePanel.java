package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

class DeadPiecePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // Panel styling constants
    private static final Color PANEL_BACKGROUND = new Color(255, 255, 255);
    private static final Color NORTH_PANEL_COLOR = new Color(214, 214, 214);    // Dark color for black pieces
    private static final Color SOUTH_PANEL_COLOR = new Color(244, 244, 244);    // Lighter color for white pieces
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    // Sizing constants
    private static final Dimension TAKEN_PIECES_PANEL_DIMENSION = new Dimension(80, 400);  // Adjusted panel size
    private static final int GRID_ROWS = 8;
    private static final int GRID_COLS = 2;
    private static final int PIECE_IMAGE_SIZE = 20;  // Smaller piece size for captured pieces

    // Cache for scaled piece images
    private static final Map<String, ImageIcon> pieceImageCache = new HashMap<>();

    private final JPanel northPanel;  // Panel for black captured pieces
    private final JPanel southPanel;  // Panel for white captured pieces

    public DeadPiecePanel() {
        super(new BorderLayout());

        // Initialize panel properties
        setBackground(PANEL_BACKGROUND);
        setBorder(PANEL_BORDER);
        setPreferredSize(TAKEN_PIECES_PANEL_DIMENSION);

        // Create sub-panels for captured pieces
        this.northPanel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 2, 2));  // Added gaps
        this.southPanel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 2, 2));  // Added gaps

        // Style sub-panels
        this.northPanel.setBackground(NORTH_PANEL_COLOR);
        this.southPanel.setBackground(SOUTH_PANEL_COLOR);

        // Add sub-panels to main panel
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the panel with newly captured pieces
     * @param moveLog The log containing all moves and captured pieces
     */
    public void redo(final MoveLog moveLog) {
        // Clear existing pieces
        southPanel.removeAll();
        northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        // Extract captured pieces from move log
        for(final Move move : moveLog.getMoves()) {
            if(move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if(takenPiece.getPieceAlliance().isBlack()){
                    blackTakenPieces.add(takenPiece);
                } else {
                    throw new RuntimeException("Invalid piece alliance encountered!");
                }
            }
        }

        // Sort pieces by value (pawns first, queen last)
        Collections.sort(whiteTakenPieces, Comparator.comparingInt(Piece::getPieceValue));
        Collections.sort(blackTakenPieces, Comparator.comparingInt(Piece::getPieceValue));

        // Display white captured pieces
        for (final Piece takenPiece : whiteTakenPieces) {
            addPieceToPanel(takenPiece, southPanel);
        }

        // Display black captured pieces
        for (final Piece takenPiece : blackTakenPieces) {
            addPieceToPanel(takenPiece, northPanel);
        }

        validate();
        repaint();
    }

    /**
     * Adds a piece image to the specified panel, using cached images when possible
     * @param piece The captured piece to display
     * @param panel The panel to add the piece to
     */
    private void addPieceToPanel(final Piece piece, final JPanel panel) {
        try {
            // Create a unique key for this piece type
            String pieceKey = piece.getPieceAlliance().toString().substring(0, 1) + piece.toString();

            // Get cached image or create new one
            ImageIcon pieceIcon = pieceImageCache.get(pieceKey);
            if (pieceIcon == null) {
                // Load and scale the image
                BufferedImage original = ImageIO.read(new File("Artwork/Icons/" + pieceKey + ".png"));
                Image scaled = original.getScaledInstance(PIECE_IMAGE_SIZE, PIECE_IMAGE_SIZE, Image.SCALE_SMOOTH);
                pieceIcon = new ImageIcon(scaled);

                // Cache the scaled image
                pieceImageCache.put(pieceKey, pieceIcon);
            }

            // Add the piece to the panel
            panel.add(new JLabel(pieceIcon));

        } catch (final IOException e) {
            System.err.println("Error loading piece image: " + e.getMessage());
        }
    }

    /**
     * Clears the image cache, useful when changing piece styles or freeing memory
     */
    public static void clearImageCache() {
        pieceImageCache.clear();
    }
}