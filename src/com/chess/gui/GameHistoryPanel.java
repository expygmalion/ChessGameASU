package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.gui.Table.MoveLog;

class GameHistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 40);

    GameHistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    // Added Khalid

    void redo(final Board board, final MoveLog moveHistory) {
        int currentRow = 0;
        this.model.clear();
        StringBuilder whiteMoveBuilder = new StringBuilder();
        StringBuilder blackMoveBuilder = new StringBuilder();

        // Iterate over moves to display them in traditional notation format
        for (int i = 0; i < moveHistory.size(); i++) {
            final Move move = moveHistory.getMoves().get(i);
            final String moveText = move.toString();

            if (move.getMovedPiece().getPieceAlliance().isWhite()) {
                whiteMoveBuilder.append(moveText).append(" ");
            } else {
                blackMoveBuilder.append(moveText).append(" ");
            }

            // After White's and Black's move, add the move to the model
            if (move.getMovedPiece().getPieceAlliance().isBlack() || i == moveHistory.size() - 1) {
                if (whiteMoveBuilder.length() > 0) {
                    this.model.setValueAt(currentRow + 1 + ". " + whiteMoveBuilder.toString(), currentRow, 0);
                }
                if (blackMoveBuilder.length() > 0) {
                    this.model.setValueAt(currentRow + 1 + ". " + blackMoveBuilder.toString(), currentRow, 1);
                    currentRow++;
                }

                // Reset move builders for next turn
                whiteMoveBuilder.setLength(0);
                blackMoveBuilder.setLength(0);
            }
        }

        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private static String calculateCheckAndCheckMateHash(final Board board) {
        if(board.currentPlayer().isInCheckMate()) {
            return "#";
        } else if(board.currentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    } // End Add


    // Added Ahmed
    private static class Row {

        private String whiteMove;
        private String blackMove;

        Row() {
        }

        public String getWhiteMove() {
            return this.whiteMove;
        }

        public String getBlackMove() {
            return this.blackMove;
        }

        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }

        public void setBlackMove(final String move) {
            this.blackMove = move;
        }

    } //End Add


    // Added Kaballo
    private static class DataModel extends DefaultTableModel {

        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        DataModel() {
            this.values = new ArrayList<>();
        }

        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null) {
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            final Row currentRow = this.values.get(row);
            if(col == 0) {
                return currentRow.getWhiteMove();
            } else if (col == 1) {
                return currentRow.getBlackMove();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object aValue,
                               final int row,
                               final int col) {
            final Row currentRow;
            if(this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if(col == 0) {
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else  if(col == 1) {
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, col);
            }
        }

        @Override
        public Class<?> getColumnClass(final int col) {
            return Move.class;
        }

        @Override
        public String getColumnName(final int col) {
            return NAMES[col];
        }
    } // End Add
}