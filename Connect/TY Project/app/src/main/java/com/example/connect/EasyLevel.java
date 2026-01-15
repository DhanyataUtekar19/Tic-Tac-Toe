package com.example.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EasyLevel extends AppCompatActivity {

    private Button[][] buttons;
    private RandomXOGenerator xoGenerator;
    private char currentPlayer;
    private int player1Score;
    private int player2Score;
    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easylevel);

        initializeGame();
        setupUI();
        setupListeners();

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Apply animation when button is touched
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_hover_animation);
                        v.startAnimation(animation);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Handle button release event
                        Intent intent = new Intent(EasyLevel.this, LevelSelection.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }


    private void initializeGame() {
        xoGenerator = new RandomXOGenerator();
        currentPlayer = 'X'; // Start with player X
        player1Score = 0;
        player2Score = 0;
    }

    private void setupUI() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        initializeButtons(gridLayout);

        player1ScoreTextView = findViewById(R.id.player1Score);
        player2ScoreTextView = findViewById(R.id.player2Score);
        updateScoreDisplay();
    }

    private void setupListeners() {
        Button generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer = xoGenerator.generateXO();
                Toast.makeText(EasyLevel.this, "Current player: " + currentPlayer, Toast.LENGTH_SHORT).show();
            }
        });

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void initializeButtons(GridLayout gridLayout) {
        buttons = new Button[3][3];

        // Calculate button size based on screen width
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = screenWidth / 4; // Divide by 3 for a 3x3 grid

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setTextSize(20);
      //          buttons[row][col].setWidth(buttonSize);
                buttons[row][col].setHeight(buttonSize);
                buttons[row][col].setOnClickListener(new ButtonClickListener(row, col));
                gridLayout.addView(buttons[row][col]);
            }
        }
    }


    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X'; // Reset player to X
    }

    private void updateScoreDisplay() {
        player1ScoreTextView.setText("Player 1: " + player1Score);
        player2ScoreTextView.setText("Player 2: " + player2Score);
    }

    private void incrementPlayer1Score() {
        player1Score++;
        updateScoreDisplay();
    }

    private void incrementPlayer2Score() {
        player2Score++;
        updateScoreDisplay();
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(buttons[i][0].getText().toString(), buttons[i][1].getText().toString(), buttons[i][2].getText().toString()) ||
                    checkRowCol(buttons[0][i].getText().toString(), buttons[1][i].getText().toString(), buttons[2][i].getText().toString())) {
                return true;
            }
        }

        // Check diagonals
        return checkRowCol(buttons[0][0].getText().toString(), buttons[1][1].getText().toString(), buttons[2][2].getText().toString()) ||
                checkRowCol(buttons[0][2].getText().toString(), buttons[1][1].getText().toString(), buttons[2][0].getText().toString());
    }

    private boolean checkRowCol(String cell1, String cell2, String cell3) {
        return !cell1.isEmpty() && cell1.equals(cell2) && cell1.equals(cell3);
    }

    private class ButtonClickListener implements View.OnClickListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (buttons[row][col].getText().toString().isEmpty()) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkForWin()) {
                    if (currentPlayer == 'X') {
                        incrementPlayer1Score();
                        Toast.makeText(EasyLevel.this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
                    } else {
                        incrementPlayer2Score();
                        Toast.makeText(EasyLevel.this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
                    }
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }
}
