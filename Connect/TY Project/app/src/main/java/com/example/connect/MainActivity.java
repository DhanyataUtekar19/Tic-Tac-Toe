package com.example.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.btnStart);

        myButton.setOnTouchListener(new View.OnTouchListener() {
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
                        Intent intent = new Intent(MainActivity.this, LevelSelection.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }
}
