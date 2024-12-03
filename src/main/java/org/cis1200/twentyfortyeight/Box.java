package org.cis1200.twentyfortyeight;

import java.awt.*;

public class Box {
    public static final int SIZE = 100;
    public int c;
    public int r;
    public int value;
    public boolean beenMod;

    public Box(int r, int c, int value) {
        this.c = c;
        this.r = r;
        this.value = value;
        this.beenMod = false;
    }

    public boolean isBeenMod() {
        return beenMod;
    }

    public void setBeenMod(boolean bool) {
        beenMod = bool;
    }

    public void setRow(int r) {
        this.r = r;
    }

    public void setCol(int c) {
        this.c = c;
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }

    public int getValue() {
        return this.value;
    }

    public void incValue() {
        this.value = value * 2;
    }

    public void draw(Graphics g) {
        int unit = GameBoard.UNIT_SIZE;
        g.setColor(MyColor.getColor(value));
        g.fillRect(this.c * unit, this.r * unit, unit, unit);
        g.setColor(Color.white);
        g.setFont(new Font("Times", Font.BOLD, 50));
        if (value < 10) {
            g.drawString(("" + getValue()), this.c * unit + (unit / 3) + 10, this.r * unit + 100);
        } else if (value < 100) {
            g.drawString(("" + getValue()), this.c * unit + (unit / 3) - 10, this.r * unit + 100);
        } else if (value < 1000) {
            g.drawString(("" + getValue()), this.c * unit + (unit / 3) - 30, this.r * unit + 100);
        } else {
            g.drawString(("" + getValue()), this.c * unit + (unit / 3) - 50, this.r * unit + 100);
        }
    }
}
