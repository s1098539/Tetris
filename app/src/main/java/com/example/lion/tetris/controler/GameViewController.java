package com.example.lion.tetris.controler;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.widget.TextView;

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
    Paint paint = new Paint();
    TextPaint textPaint = new TextPaint();
    CurrentGameValuesController currentGameValuesController;
    GameActivity gameActivity;
    int pxLeft;
    int pxRight;
    int pxTop;
    int pxBottom;
    int blockSize;

    public GameViewController() {
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
            textPaint.setTextSize(60);
            textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(currentGameValuesController.getScore(), blockSize * 7, pxTop - blockSize * 0.1f , textPaint);
            textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
            canvas.drawText(currentGameValuesController.getLevel(), blockSize * 2.1f, pxTop - blockSize * 0.1f, textPaint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public void set(GameView gameView, GameField gameField, CurrentGameValuesController currentGameValuesController) {
        this.gameView = gameView;
        this.gameField = gameField;
        this.currentGameValuesController = currentGameValuesController;
    }
}
