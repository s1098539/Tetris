package com.example.lion.tetris.controler;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.SurfaceHolder;

import com.example.lion.tetris.R;
import com.example.lion.tetris.activity.game.GameActivity;
import com.example.lion.tetris.activity.game.GameView;
import com.example.lion.tetris.model.GameField;

/**
 * Created by lion on 20/02/2018.
 */

public class GameViewController {

    Canvas canvas;
    GameView gameView;
    GameField gameField;
    TextPaint textPaintScore, textPaintLevel;
    CurrentGameValuesController currentGameValuesController;
    GameActivity gameActivity;
    int pxLeft, pxRight, pxTop, pxBottom, blockSize, blinkScore=6, blinkLevel=6;
    String score="0", level="";
    Typeface typeface;

    public GameViewController() {
    }

    private void setTextPaint() {
        if (textPaintScore == null) {
            textPaintScore = new TextPaint();
            textPaintScore.setTextSize(50);
            textPaintScore.setColor(ContextCompat.getColor(gameActivity, R.color.text));
            typeface = Typeface.createFromAsset(gameActivity.getAssets(), "font/ka1.ttf");
            textPaintScore.setTypeface(typeface);
            textPaintScore.setTextAlign(Paint.Align.LEFT);
            textPaintLevel = new TextPaint();
            textPaintLevel.setTextSize(50);
            textPaintLevel.setColor(ContextCompat.getColor(gameActivity, R.color.text));
            textPaintLevel.setTypeface(typeface);
            textPaintLevel.setTextAlign(Paint.Align.LEFT);
        }
    }

    public void calculateViewCoordinates() {
        int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
        blockSize = widthPixels/14;
        gameView.setBlockSize(blockSize);
        pxLeft = widthPixels/2-(blockSize*5);
        pxRight = widthPixels/2+(blockSize*5);
        pxBottom = heightPixels;
        pxTop = heightPixels-(blockSize*20);
    }

    public int getWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getHeightPixels() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void draw(SurfaceHolder surfaceHolder) {
        setTextPaint();
        if (!score.equals(currentGameValuesController.getScore())) {
            blinkScore = 0;
        }
        if(blinkScore < 6) {
            if(blinkScore%2 == 0) textPaintScore.setColor(ContextCompat.getColor(gameActivity, R.color.colorAccent));
            else textPaintScore.setColor(ContextCompat.getColor(gameActivity, R.color.text));
            blinkScore++;
        }
        if (!level.equals(currentGameValuesController.getLevel())) {
            blinkLevel = 0;
        }
        if(blinkLevel < 6) {
            if(blinkLevel%2 == 0) textPaintLevel.setColor(ContextCompat.getColor(gameActivity, R.color.colorAccent));
            else textPaintLevel.setColor(ContextCompat.getColor(gameActivity, R.color.text));
            blinkLevel++;
        }
        canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawBitmap(gameView.background, 0, 0, null);

            int vX, vY, colorIndex;
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 10; x++) {
                    vX = pxLeft + x * blockSize;
                    vY = pxTop + y * blockSize;
                    colorIndex = gameField.getGameSpot(x, y).getIndex();
                    if (colorIndex < 7) {
                        canvas.drawBitmap(gameView.bitmaps[colorIndex], vX, vY, null);
                    }
                }
            }

            score = currentGameValuesController.getScore();
            level = currentGameValuesController.getLevel();
            canvas.drawText(score, blockSize * 7, pxTop - blockSize * 0.1f , textPaintScore);
            canvas.drawText(level, blockSize * 2.1f, pxTop - blockSize * 0.1f, textPaintLevel);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void set(GameView gameView, GameField gameField, CurrentGameValuesController currentGameValuesController, GameActivity gameActivity) {
        this.gameView = gameView;
        this.gameField = gameField;
        this.currentGameValuesController = currentGameValuesController;
        this.gameActivity = gameActivity;
    }
}
