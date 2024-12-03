package org.cis1200.twentyfortyeight;

import org.cis1200.Game;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MyGame {

    public Box[][] boardArr;
    private LinkedList<Box> moddedBoxes;
    private ArrayList<RowCol> availRowCols;
    public int score;
    public GameOver gameOver;
    private boolean canSpawn;

    public static class RowCol {
        private int r;
        private int c;

        public RowCol(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public void setC(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setR(int c) {
            this.c = c;
        }
    }

    public MyGame() {
        this.boardArr = new Box[4][4];
        this.moddedBoxes = new LinkedList<>();
        this.availRowCols = updateRowsCols();
        this.canSpawn = true;
        spawnBox();
        this.canSpawn = true;
        spawnBox();
        this.score = 2;
        this.gameOver = null;
    }

    // for testing
    public MyGame(Box[][] boardArr) {
        this.boardArr = boardArr;
        this.moddedBoxes = new LinkedList<>();
        this.availRowCols = updateRowsCols();
        this.score = 2;
        this.gameOver = null;
        this.canSpawn = true;
    }

    // for saved game state
    public MyGame(int[][] intArr) {
        this.boardArr = new Box[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (intArr[r][c] != 0) {
                    Box box = new Box(r, c, intArr[r][c]);
                    this.boardArr[r][c] = box;
                }
            }
        }
        this.canSpawn = true;
        this.moddedBoxes = new LinkedList<>();
        this.availRowCols = updateRowsCols();
        this.score = 2;
        this.gameOver = null;
    }

    public void saveState() {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("src/main/java/org/cis1200/twentyfortyeight/gameState.txt")
            );
            bw.write(toString());
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<RowCol> updateRowsCols() {
        // also gets high score
        ArrayList<RowCol> results = new ArrayList<>();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (boardArr[r][c] == null) {
                    results.add(new RowCol(r, c));
                } else {
                    int val = boardArr[r][c].getValue();
                    if (val > score) {
                        this.score = val;
                    }
                }
            }
        }
        return results;
    }

    // for testing and save gamestate
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Box current = boardArr[r][c];
                if (current != null) {
                    result.append(current.getValue());
                    result.append(" ");
                } else {
                    result.append(0);
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    public void resetBoxes() {
        if (moddedBoxes != null) {
            for (Box b : moddedBoxes) {
                b.setBeenMod(false);
            }
            moddedBoxes = new LinkedList<>();
        }
    }

    public void drawSquares(Graphics g) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (boardArr[r][c] != null) {
                    boardArr[r][c].draw(g);
                }
            }
        }
    }

    public void spawnBox() {
        if (!availRowCols.isEmpty()) {
            if (canSpawn) {
                int idx = (int) (Math.random() * availRowCols.size());
                int val = (int) ((Math.random() * 2) + 1) * 2;
                RowCol curr = availRowCols.get(idx);
                availRowCols.remove(idx);
                int r = curr.getR();
                int c = curr.getC();
                boardArr[r][c] = new Box(r, c, val);
            }
            canSpawn = false;
        } else {
            gameOver = new GameOver(score);
        }
        printArr();
    }

    public void drawGameOver(Graphics g) {
        gameOver.draw(g);
    }

    public void printArr() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Box b = boardArr[r][c];
                if (b == null) {
                    System.out.print("null ");
                } else {
                    System.out.print(b.getValue() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void goSideRepeatCode(int r, int c) {
        Box current = boardArr[r][c];
        if (current != null) {
            boolean hitBox = false;
            // left to right
            for (int i = c + 1; i < 4; i++) {
                Box toTheLeft = boardArr[r][i];
                if (!hitBox && toTheLeft != null && toTheLeft.getValue() == current.getValue()
                        && moddedBoxes != null
                        && !current.beenMod && !toTheLeft.beenMod) {
                    boardArr[r][i] = null;
                    current.setBeenMod(true);
                    moddedBoxes.add(current);
                    current.incValue();
                } else if (toTheLeft != null) {
                    hitBox = true;
                }
            }
        }
    }

    public void goLeft() {
        for (int r = 0; r < 4; r++) {
            // left to right
            for (int c = 0; c < 4; c++) {
                goSideRepeatCode(r, c);
            }
        }
        resetBoxes();
        slideLeft();
        this.availRowCols = updateRowsCols();
    }

    public void goRight() {
        for (int r = 0; r < 4; r++) {
            // left to right
            for (int c = 3; c >= 0; c--) {
                goSideRepeatCode(r, c);
            }
        }
        resetBoxes();
        slideRight();
        this.availRowCols = updateRowsCols();
    }

    public void slideRight() {
        boolean smthSlid = false;
        for (int r = 0; r < 4; r++) {
            // right to left
            for (int c = 2; c >= 0; c--) {
                int newCol = 3;
                Box current = boardArr[r][c];
                if (current != null) {
                    boolean hitBox = false;
                    // left to right
                    for (int i = c + 1; i < 4; i++) {
                        if (!hitBox && boardArr[r][i] != null) {
                            newCol = i - 1;
                            hitBox = true;
                        }
                    }
                    boardArr[r][newCol] = current;
                    current.setCol(newCol);
                    if (newCol != c) {
                        boardArr[r][c] = null;
                        smthSlid = true;
                    }
                }
            }
        }
        if (smthSlid) {
            canSpawn = true;
        } else {
            canSpawn = false;
        }
    }

    public void slideLeft() {
        boolean smthSlid = false;
        for (int r = 0; r < 4; r++) {
            // left to right
            for (int c = 1; c < 4; c++) {
                int newCol = 0;
                Box current = boardArr[r][c];
                if (current != null) {
                    boolean hitBox = false;
                    for (int i = c - 1; i >= 0; i--) {
                        if (!hitBox && boardArr[r][i] != null) {
                            newCol = i + 1;
                            hitBox = true;
                        }
                    }
                    boardArr[r][newCol] = current;
                    current.setCol(newCol);
                    if (newCol != c) {
                        boardArr[r][c] = null;
                        smthSlid = true;
                    }
                }
            }
        }
        if (smthSlid) {
            canSpawn = true;
        } else {
            canSpawn = false;
        }
    }

    public void goVertRepeatCode(int r, int c) { // rows/cols switched for side-to-side
        Box current = boardArr[r][c];
        if (current != null) {
            boolean hitBox = false;
            for (int i = r + 1; i < 4; i++) {
                Box toDown = boardArr[i][c];
                if (!hitBox && toDown != null && toDown.getValue() == current.getValue()
                        && !current.beenMod && !toDown.beenMod) {
                    boardArr[i][c] = null;
                    current.setBeenMod(true);
                    moddedBoxes.add(current);
                    current.incValue();
                } else if (toDown != null) {
                    hitBox = true;
                }
            }
        }
    }

    public void goUp() {
        for (int c = 0; c < 4; c++) {
            for (int r = 3; r >= 0; r--) {
                goVertRepeatCode(r, c);
            }
        }
        resetBoxes();
        slideUp();
        this.availRowCols = updateRowsCols();
    }

    public void goDown() {
        for (int c = 0; c < 4; c++) {
            for (int r = 3; r >= 0; r--) {
                goVertRepeatCode(r, c);
            }
        }
        resetBoxes();
        slideDown();
        this.availRowCols = updateRowsCols();
    }

    public void slideUp() {
        boolean smthSlid = false;
        for (int c = 0; c < 4; c++) {
            // up to down
            for (int r = 1; r < 4; r++) {
                int newRow = 0;
                Box current = boardArr[r][c];
                if (current != null) {
                    boolean hitBox = false;
                    for (int i = r - 1; i >= 0; i--) {
                        if (!hitBox && boardArr[i][c] != null) {
                            newRow = i + 1;
                            hitBox = true;
                        }
                    }
                    boardArr[newRow][c] = current;
                    current.setRow(newRow);
                    if (newRow != r) {
                        boardArr[r][c] = null;
                        smthSlid = true;
                    }
                }
            }
        }
        if (smthSlid) {
            canSpawn = true;
        } else {
            canSpawn = false;
        }
    }

    public void slideDown() {
        boolean smthSlid = false;
        for (int c = 0; c < 4; c++) {
            // down to up
            for (int r = 2; r >= 0; r--) {
                int newRow = 3;
                Box current = boardArr[r][c];
                if (current != null) {
                    boolean hitBox = false;
                    // up to down
                    for (int i = r + 1; i < 4; i++) {
                        if (!hitBox && boardArr[i][c] != null) {
                            newRow = i - 1;
                            hitBox = true;
                        }
                    }
                    boardArr[newRow][c] = current;
                    current.setRow(newRow);
                    if (newRow != r) {
                        boardArr[r][c] = null;
                        smthSlid = true;
                    }
                }
            }
        }
        if (smthSlid) {
            canSpawn = true;
        } else {
            canSpawn = false;
        }
    }
}
