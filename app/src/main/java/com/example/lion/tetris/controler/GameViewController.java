package com.example.lion.tetris.controler;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.SurfaceHolder;

import com.example.lion.tetris.R;
import com.example.lion.tetris.activity.GameActivity;
import com.example.lion.tetris.activity.GameView;
import com.example.lion.tetris.model.GameField;

/**
 * Created by lion on 20/02/2018.
 */

public class GameViewController {

    Canvas canvas;
    GameView gameView;
    GameField gameField;
    TextPaint textPaint;
    CurrentGameValuesController currentGameValuesController;
    GameActivity gameActivity;
    int pxLeft, pxRight, pxTop, pxBottom, blockSize;
    Typeface typeface;

    public GameViewController() {
    }

    private void setTextPaint() {
        if (textPaint == null) {
            textPaint = new TextPaint();
            textPaint.setTextSize(50);
            textPaint.setColor(ContextCompat.getColor(gameActivity, R.color.text));
            typeface = Typeface.createFromAsset(gameActivity.getAssets(), "font/ka1.ttf");
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.LEFT);
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
        canvas = surfaceHolder.lockCanvas();
        setTextPaint();
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
            canvas.drawText(currentGameValuesController.getScore(), blockSize * 7, pxTop - blockSize * 0.1f , textPaint);
            canvas.drawText(currentGameValuesController.getLevel(), blockSize * 2.1f, pxTop - blockSize * 0.1f, textPaint);
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
