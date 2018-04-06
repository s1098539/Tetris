package com.example.lion.tetris.activity.game;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.lion.tetris.R;
import com.example.lion.tetris.controler.CurrentGameValuesController;
import com.example.lion.tetris.controler.GameFieldControler;
import com.example.lion.tetris.controler.GameViewController;
import com.example.lion.tetris.controler.SettingsController;
import com.example.lion.tetris.controler.TetraminosControler;
import com.example.lion.tetris.database.DatabaseSettingsHelper;
import com.example.lion.tetris.database.DatabaseSettingsInfo;
import com.example.lion.tetris.enumeration.GameSpot;
import com.example.lion.tetris.model.CurrentGameValues;
import com.example.lion.tetris.model.GameField;
import com.example.lion.tetris.model.Settings;
import com.example.lion.tetris.model.Tetraminos;

public class GameActivity extends AppCompatActivity {

    TetraminosControler tetraminosControler;
    GameFieldControler gameFieldControler;
    GameViewController gameViewController;
    CurrentGameValuesController currentGameValuesController;

    Tetraminos tetraminos;
    GameField gameField;
    Boolean rotating, keepSlamming;
    GameView gameView;
    SurfaceHolder surfaceHolder;
    ConstraintLayout gameLayout;
    CurrentGameValues currentGameValues;
    Thread thread;
    Point points;
    Settings settings;
    SettingsController settingsController;
    Boolean running = true, moved = false, pause = false;
    private float xStart, xCurrent, yStart, yEnd, deltaX, deltaY;
    static final int minSwipeDistance = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialise();
        getLocalSettingsValues();
        setControllers();

        setContentView(R.layout.activity_game);
        gameLayout = findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);
        surfaceHolder = gameView.getHolder();
        surfaceHolder.addCallback(gameView);

        gameFieldControler.newEmptyGameField();

        tetraminosControler.newRandomTetraminos();
        rotating = false;
    }

    public void getLocalSettingsValues() {
        int i=10, j=10, l=10, o=10, s=10, t=10, z=10;
        DatabaseSettingsHelper dbHelper = DatabaseSettingsHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseSettingsInfo.LocalSettingsTables.LOCALSETTINGS, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();
        if (rs.getCount() > 0) {

            i = Integer.parseInt(rs.getString(rs.getColumnIndex("I")));
            j = Integer.parseInt((String) rs.getString(rs.getColumnIndex("J")));
            l = Integer.parseInt((String) rs.getString(rs.getColumnIndex("L")));
            o = Integer.parseInt((String) rs.getString(rs.getColumnIndex("O")));
            s = Integer.parseInt((String) rs.getString(rs.getColumnIndex("S")));
            t = Integer.parseInt((String) rs.getString(rs.getColumnIndex("T")));
            z = Integer.parseInt((String) rs.getString(rs.getColumnIndex("Z")));
        }
        rs.close();
        settings.setAll(i, j, l, o, s, t, z);
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        unpause();
    }

    public void hideSystemBars() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = event.getX();
                yStart = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                xCurrent = event.getX();
                deltaX = xCurrent - xStart;
                if (deltaX > minSwipeDistance || deltaX*-1 > minSwipeDistance) {
                    xStart = xCurrent;
                    moved = true;
                    if (deltaX > 0) moveRight();
                    else moveLeft();
                    xStart = xCurrent;
                }
                break;
            case MotionEvent.ACTION_UP:
                yEnd = event.getY();
                deltaY = yEnd - yStart;
                if (!moved) {
                    if (deltaY > minSwipeDistance) slamDown();
                    else rotate();
                }
                moved = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void initialise() {
        settings = new Settings();
        gameView = new GameView(this, getResources());
        tetraminosControler = new TetraminosControler();
        gameFieldControler = new GameFieldControler();
        gameViewController = new GameViewController();
        currentGameValuesController = new CurrentGameValuesController();
        tetraminos = new Tetraminos();
        gameField = new GameField();
        currentGameValues = new CurrentGameValues();
        settingsController = new SettingsController();
    }

    private void setControllers() {
        gameView.set(gameViewController, this);
        tetraminosControler.set(gameFieldControler, tetraminos, settingsController);
        gameFieldControler.set(tetraminosControler, gameField, tetraminos, currentGameValuesController, this);
        gameViewController.set(gameView, gameField, currentGameValuesController, this);
        currentGameValuesController.set(currentGameValues);
        settingsController.set(settings);
    }

    public void gameOver() {
        running = false;
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        Bundle b = new Bundle();
        b.putString("level", currentGameValuesController.getLevel());
        b.putString("score", currentGameValuesController.getScore());
        intent.putExtras(b);
        startActivity(intent);
    }

    private void rotate() {
        rotating = true;
        if (gameFieldControler.validateRotate()) {
            gameFieldControler.clearLocation();
            tetraminosControler.rotate();
            gameFieldControler.saveLocation();
            gameViewController.draw(surfaceHolder);
        }
        rotating = false;
    }

    private void moveDown() {
        if (gameFieldControler.validateDown()) {
            gameFieldControler.clearLocation();
            tetraminosControler.moveDown();
            gameFieldControler.saveLocation();
        } else {
            gameFieldControler.saveLocation();
            gameFieldControler.fullRows();
            newTetraminos();
            keepSlamming = false;
        }
        gameViewController.draw(surfaceHolder);
    }

    private void slamDown() {
        keepSlamming = true;
        while (keepSlamming) {
            moveDown();
        }
    }

    private void moveLeft() {
        if (gameFieldControler.validateLeft()) {
            gameFieldControler.clearLocation();
            tetraminosControler.moveLeft();
            gameFieldControler.saveLocation();
            gameViewController.draw(surfaceHolder);
        }
    }

    private void moveRight() {
        if (gameFieldControler.validateRight()) {
            gameFieldControler.clearLocation();
            tetraminosControler.moveRight();
            gameFieldControler.saveLocation();
            gameViewController.draw(surfaceHolder);
        }
    }

    private void newTetraminos() {
        tetraminosControler.newRandomTetraminos();
        for (int i = 0; i < 4; i++) {
            points = tetraminos.getPoints()[i];
            if(gameField.getGameSpot(points.x, points.y) != GameSpot.clear) {
                gameOver();
            }
        }
    }

    public void pause() {
        pause = true;
    }

    public void unpause() {
        pause = false;
    }

    public void startDropDownThread() {
        thread = new Thread() {
            @Override public void run() {
                Boolean moved = false;
                while (running) {
                    if (!pause) {
                        try {
                            Thread.sleep(currentGameValuesController.getGameSpeed());
                            while (!moved) {
                                if (!rotating) {
                                    moveDown();
                                    moved = true;
                                }
                            }
                            moved = false;
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }

        };
        thread.start();
    }
}
