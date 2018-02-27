package com.example.lion.tetris.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lion.tetris.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        TextView gameOver = (TextView) findViewById(R.id.gameOverText);
        gameOver.setText(R.string.game_over);
    }
}
