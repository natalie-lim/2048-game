package org.cis1200.twentyfortyeight;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import javax.swing.*;

public class RunMyGame implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("2048");
        frame.setLocation(GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game Board
        final org.cis1200.twentyfortyeight.GameBoard board = new org.cis1200.twentyfortyeight.GameBoard(
                status
        );
        frame.add(board);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Start/Restart Game");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton home = new JButton("Return Home");
        home.addActionListener(e -> board.home());
        control_panel.add(home);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> board.instructions());
        control_panel.add(instructions);

        final JButton resume = new JButton("Resume Game");
        resume.addActionListener(e -> board.resume());
        control_panel.add(resume);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.home();
    }
}