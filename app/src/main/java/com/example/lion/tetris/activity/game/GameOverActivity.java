package com.example.lion.tetris.activity.game;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lion.tetris.R;
import com.example.lion.tetris.activity.MenuActivity;
import com.example.lion.tetris.database.DatabaseHelper;
import com.example.lion.tetris.database.DatabaseInfo;
import com.example.lion.tetris.highscore.Highscore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GameOverActivity extends AppCompatActivity {

    Button toMenu, submit;
    EditText nameInput;
    Intent intent;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String userID, name, score, level, date;
    Highscore highscore;
    int intCurrentScore, intHighScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            TextView textViewLevel = (TextView) findViewById(R.id.textViewLevel);
            score = b.getString("score");
            level = b.getString("level");
            textViewLevel.setText(getString(R.string.game_over_level) + level);
            TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
            textViewScore.setText(getString(R.string.game_over_score) + score);
        }

        nameInput = (EditText) findViewById(R.id.submitName);

        intent = new Intent(GameOverActivity.this, MenuActivity.class);

        toMenu = (Button) findViewById(R.id.buttonToMenu);
        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        submit = (Button) findViewById(R.id.buttonSubmitScore);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameInput.getText() != null) {
                    auth = FirebaseAuth.getInstance();
                    createHighscore();
                    submitLocal();
                    submitCloud();
                    startActivity(intent);
                }
            }
        });

    }

    private void createHighscore() {
        name = nameInput.getText().toString();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        highscore = new Highscore(name, score, level, date);
        if (auth.getCurrentUser() != null) {
            userID = auth.getCurrentUser().getUid();
        }
    }

    private void submitCloud() {
            if (auth.getCurrentUser() != null) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("highScores").child(auth.getCurrentUser().getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(Highscore.class) != null) {
                            intHighScore = Integer.valueOf(dataSnapshot.getValue(Highscore.class).getScore());
                            intCurrentScore = Integer.valueOf(highscore.getScore());
                            if (intHighScore < intCurrentScore) {
                                reference.setValue(highscore);
                            }
                        } else reference.setValue(highscore);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
    }

    private void submitLocal() {
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.LocalHighscoreColumn.NAME, highscore.getName());
        values.put(DatabaseInfo.LocalHighscoreColumn.SCORE, highscore.getScore());
        values.put(DatabaseInfo.LocalHighscoreColumn.LEVEL, highscore.getLevel());
        values.put(DatabaseInfo.LocalHighscoreColumn.DATE, highscore.getDate());

        dbHelper.insert(DatabaseInfo.LocalHighscoreTables.LOCALHIGHSCORE, null, values);
    }
}
