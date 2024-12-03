package org.cis1200.twentyfortyeight;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class GameBoard extends JPanel {

    private MyGame tfe; // model for the game
    private JLabel status; // current status text
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;
    public static final int UNIT_SIZE = BOARD_HEIGHT / 4;
    private int theme = 0;
    private boolean homePage;
    private boolean instructionsPage;
    private boolean gameBoardPage;

    public GameBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        this.status = statusInit;
        this.homePage = true;
        this.instructionsPage = false;
        this.gameBoardPage = false;

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    tfe.goLeft();
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    tfe.goRight();
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tfe.goDown();
                    ;
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    tfe.goUp();
                    actions();
                }
            }

            public void actions() {
                tfe.spawnBox();
                tfe.resetBoxes();
                tfe.saveState();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                theme++;
                if (theme > 2) {
                    theme = 0;
                }
                MyColor.setScheme(theme);
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (homePage) {
            g.setColor(MyColor.getColor(2));
            g.fillRect(0, 0, GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);
            g.setColor(Color.white);
            g.setFont(new Font("Times", Font.BOLD, 80));
            g.drawString("2048", 190, 300);
            g.setFont(new Font("Times", Font.PLAIN, 15));
            g.drawString("click anywhere to change color scheme", 157, 550);
        } else if (gameBoardPage) {
            // box
            tfe.drawSquares(g);
            g.drawLine(0, 0, BOARD_WIDTH, 0);
            g.drawLine(0, 0, 0, BOARD_HEIGHT);
            g.drawLine(BOARD_WIDTH, 0, BOARD_WIDTH, BOARD_HEIGHT);
            g.drawLine(0, BOARD_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT);
            // dividers
            g.drawLine(UNIT_SIZE, 0, UNIT_SIZE, BOARD_HEIGHT);
            g.drawLine(UNIT_SIZE * 2, 0, UNIT_SIZE * 2, BOARD_HEIGHT);
            g.drawLine(UNIT_SIZE * 3, 0, UNIT_SIZE * 3, BOARD_HEIGHT);
            g.drawLine(0, UNIT_SIZE, BOARD_WIDTH, UNIT_SIZE);
            g.drawLine(0, UNIT_SIZE * 2, BOARD_WIDTH, UNIT_SIZE * 2);
            g.drawLine(0, UNIT_SIZE * 3, BOARD_WIDTH, UNIT_SIZE * 3);
            if (tfe.gameOver != null) {
                tfe.drawGameOver(g);
            }
        } else if (instructionsPage) {
            g.setColor(MyColor.getColor(2));
            g.fillRect(0, 0, GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);
            g.setColor(Color.white);
            g.setFont(new Font("Times", Font.BOLD, 40));
            g.drawString("Instructions", 180, 110);
            g.setFont(new Font("Times", Font.PLAIN, 20));
            int xBullet = 120;
            g.drawString("- use the arrow keys", xBullet, 200);
            g.drawString("- click to change colors", xBullet, 250);
            g.drawString("- high scores listed at the end", xBullet, 300);
            g.drawString("- try to get to 2048", xBullet, 350);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public void reset() {
        tfe = new MyGame();
        this.gameBoardPage = true;
        this.homePage = false;
        this.instructionsPage = false;
        requestFocusInWindow();
        repaint();
    }

    public void instructions() {
        tfe = new MyGame();
        this.gameBoardPage = false;
        this.homePage = false;
        this.instructionsPage = true;
        requestFocusInWindow();
        repaint();
    }

    public void home() {
        this.homePage = true;
        this.gameBoardPage = false;
        this.instructionsPage = false;
        tfe = new MyGame();
        status.setText("Running...");
        requestFocusInWindow();
        repaint();
    }

    public void resume() {
        this.gameBoardPage = true;
        this.homePage = false;
        this.instructionsPage = false;
        try {
            // 0=null
            int[][] savedGameArr = new int[4][4];
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/java/org/cis1200/twentyfortyeight/gameState.txt")
            );
            int intRepOfChar = reader.read();
            if (intRepOfChar!= -1) {
                StringBuilder numStr = new StringBuilder();
                char theChar = (char) (intRepOfChar);
                for (int r = 0; r < 4; r++) {
                    for (int c = 0; c < 4; c++) {
                        while (theChar != ' ') {
                            numStr.append(String.valueOf(theChar));
                            theChar = (char) (reader.read());
                        }
                        if (!numStr.isEmpty()) {
                            int val = Integer.parseInt(numStr.toString());
                            savedGameArr[r][c] = val;
                        }
                        theChar = (char) (reader.read());
                        numStr = new StringBuilder();
                    }
                }
                tfe = new MyGame(savedGameArr);
            } else {
                tfe = new MyGame();
            }
            reader.close();
            status.setText("Running...");
            requestFocusInWindow();
            repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
