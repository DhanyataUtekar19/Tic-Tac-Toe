package com.example.connect;

import java.util.Random;

public class RandomXOGenerator {
    private Random random;
    private char lastWinningMove; // Variable to store the last winning move

    public RandomXOGenerator() {
        random = new Random();
        lastWinningMove = ' '; // Initialize to a default value
    }

    // Method to generate a random X or O, considering the last winning move
    public char generateXO() {
        char xo;
        // If lastWinningMove is 'X', generate 'O'; if lastWinningMove is 'O', generate 'X'; otherwise, generate randomly
        if (lastWinningMove == 'X') {
            xo = 'O';
        } else if (lastWinningMove == 'O') {
            xo = 'X';
        } else {
            // Generate a random number (0 or 1)
            int randomNumber = random.nextInt(2);
            // Return 'X' if randomNumber is 0, otherwise return 'O'
            xo = (randomNumber == 0) ? 'X' : 'O';
        }
        return xo;
    }

    // Method to set the last winning move
    public void setLastWinningMove(char move) {
        lastWinningMove = move;
    }
}
