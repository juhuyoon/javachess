package com.chess.gui;


import com.chess.engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public final class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    /* making the menu*/
    public Table() {
        this.gameFrame = new JFrame("Chess with Java");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        /*PGN files lookup! */
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Open up the PGN File");
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

/*For the Board */
private class BoardPanel extends JPanel {
    final List<TilePanel> boardTiles;

    BoardPanel() {
        super(new GridLayout(8,8));
        this.boardTiles = new ArrayList<>();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final TilePanel tilePanel = new TilePanel(this, i); //creates indv. tile panel and the tileId of i.
            this.boardTiles.add(tilePanel);
            add(tilePanel);
        }
        setPreferredSize(BOARD_PANEL_DIMENSION);
        validate();
    }
}

/* For the tile */
private class TilePanel extends JPanel{
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super (new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }
        /*Light and Dark Tile color assignments */
    private void assignTileColor() {
            if(BoardUtils.FIRST_ROW[this.tileId] ||
               BoardUtils.THIRD_ROW[this.tileId] ||
               BoardUtils.FIFTH_ROW[this.tileId] ||
               BoardUtils.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SECOND_ROW[this.tileId] ||
                       BoardUtils.FOURTH_ROW[this.tileId] ||
                       BoardUtils.SIXTH_ROW[this.tileId] ||
                       BoardUtils.EIGHTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
    }
}
}
