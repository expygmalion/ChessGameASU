package com.chess.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {
    private final  JFrame gameFrame;
    private static Dimension OuterFrameDimensions = new Dimension(800, 800);

    public Table() {
        this.gameFrame = new JFrame("Chess Game");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OuterFrameDimensions);
        this.gameFrame.setVisible(true); // Display the frame

    }
    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());

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
        return fileMenu;
    }
}
