package org.cis1200.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GameOver extends JPanel {
    private int score;
    private String highscoreMessage;

    public GameOver(int score) {
        this.score = score;
    }

    public void getHighScore() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/java/org/cis1200/twentyfortyeight/highscores.txt")
            );
            String highScoreString = reader.readLine();
            reader.close();
            int currentHighScore = score;
            if(highScoreString != null) {
                currentHighScore = Integer.parseInt(highScoreString);
            }
            if (score >= currentHighScore) {
                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(
                                "src/main/java/org/cis1200/twentyfortyeight/highscores.txt"
                        )
                    );
                bw.write("" + score);
                bw.close();
                currentHighScore = score;
            }
            highscoreMessage = "Highscore: " + currentHighScore;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetFile() {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                            "src/main/java/org/cis1200/twentyfortyeight/gameState.txt"
                    )
            );
            bw.close();
        } catch (IOException e) {
throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        resetFile();
        getHighScore();
        g.setColor(MyColor.getColor(2));
        g.fillRect(0, 0, GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("Times", Font.BOLD, 80));
        g.drawString("GAME OVER", 50, 210);
        g.setFont(new Font("Times", Font.BOLD, 50));
        g.drawString("Score: " + score, 165, 310);
        g.setFont(new Font("Times", Font.BOLD, 25));
        g.drawString(highscoreMessage, 190, 450);
    }
}
