package com.example.lion.tetris.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lion.tetris.R;
import com.example.lion.tetris.activity.game.GameActivity;
import com.example.lion.tetris.activity.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Console;

public class MenuActivity extends AppCompatActivity {

    Button play, settings, highscores, logout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        auth = FirebaseAuth.getInstance();

        play = (Button) findViewById(R.id.buttonPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        settings = (Button) findViewById(R.id.buttonSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        highscores = (Button) findViewById(R.id.buttonHighscores);
        highscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HighscoresActivity.class);
                startActivity(intent);
            }
        });



        logout = (Button) findViewById(R.id.buttonLogout);
        if (auth.getCurrentUser() == null) {
            logout.setText(R.string.menu_login);
        } else logout.setText(R.string.menu_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    auth.signOut();
                    logout.setText(R.string.menu_login);
                    Toast.makeText(getApplication(), R.string.menu_toast_logout, Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
