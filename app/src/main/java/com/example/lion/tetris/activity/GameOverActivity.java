package com.example.lion.tetris.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lion.tetris.R;

public class GameOverActivity extends AppCompatActivity {

    Button toMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            TextView textViewLevel = (TextView) findViewById(R.id.textViewLevel);
            textViewLevel.setText(getString(R.string.game_over_level) + b.getString("level"));
            TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
            textViewScore.setText(getString(R.string.game_over_score) + b.getString("score"));
        }

        toMenu = (Button) findViewById(R.id.buttonToMenu);
        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
