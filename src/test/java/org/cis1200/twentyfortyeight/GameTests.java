package org.cis1200.twentyfortyeight;

import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void testWriter() throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("src/test/java/org/cis1200/twentyfortyeight/test")
        );
        bw.write("hellwoaowiejf");
        bw.close();
    }

    @Test
    public void testIntConstructor() {
        int[][] intBox = { { 0, 0, 0, 0 }, { 4, 0, 0, 0 }, { 0, 0, 0, 0 }, { 8, 4, 0, 4 } };
        MyGame intGame = new MyGame(intBox);
        Box[][] first = new Box[4][4];
        first[3][0] = new Box(3, 0, 8);
        first[3][1] = new Box(3, 1, 4);
        first[3][3] = new Box(3, 3, 4);
        first[1][0] = new Box(1, 0, 4);
        MyGame game = new MyGame(first);
        assertEquals(intGame.toString(), game.toString());
    }

    /*
     * null null null null
     * 4 null null null
     * null null null null
     * 8 4 null 4
     */
    @Test
    public void testGoRightNotDoubleAdd() {
        Box[][] first = new Box[4][4];
        first[3][0] = new Box(3, 0, 8);
        first[3][1] = new Box(3, 1, 4);
        first[3][3] = new Box(3, 3, 4);
        first[1][0] = new Box(1, 0, 4);
        MyGame game = new MyGame(first);
        game.goRight();
        String expected = "____\n___4\n____\n__88\n";
        assertEquals(expected, game.toString());
    }

    @Test
    public void testGoLeftNotDoubleAdd() {
        Box[][] first = new Box[4][4];
        first[3][0] = new Box(3, 0, 8);
        first[3][1] = new Box(3, 1, 4);
        first[3][3] = new Box(3, 3, 4);
        first[1][0] = new Box(1, 0, 4);
        MyGame game = new MyGame(first);
        game.goLeft();
        String expected = "____\n4___\n____\n88__\n";
        assertEquals(expected, game.toString());
    }

    /**
     * 2 null null null
     * null null null null
     * null null null 8
     * 4 null null 16
     */
    @Test
    public void testGoUp() {
        Box[][] first = new Box[4][4];
        first[0][0] = new Box(0, 0, 2);
        first[2][3] = new Box(2, 3, 8);
        first[3][0] = new Box(3, 2, 4);
        first[3][3] = new Box(3, 3, 16);
        MyGame game = new MyGame(first);
        game.goUp();
        String expected = "2__8\n4__16\n____\n____\n";
        assertEquals(expected, game.toString());
    }

    /**
     * null null null null
     * 4 null null null
     * 4 null null null
     * 4 null null null
     */
    @Test
    public void testGoDown() {
        Box[][] first = new Box[4][4];
        first[1][0] = new Box(1, 0, 4);
        first[2][0] = new Box(2, 0, 4);
        first[3][0] = new Box(3, 0, 4);
        MyGame game = new MyGame(first);
        game.goUp();
        String expected = "8___\n4___\n____\n____\n";
        assertEquals(expected, game.toString());
    }

    /**
     * null null null null
     * 2 null null null
     * null null null null
     * null 4 4 32
     */
    @Test
    public void testSlideLeft() {
        Box[][] first = new Box[4][4];
        first[1][0] = new Box(1, 0, 2);
        first[3][1] = new Box(3, 1, 4);
        first[3][2] = new Box(3, 2, 4);
        first[3][3] = new Box(3, 3, 32);
        MyGame game = new MyGame(first);
        game.goLeft();
        String expected = "____\n2___\n____\n832__\n";
        assertEquals(expected, game.toString());
    }
}
