package com.example.lion.tetris.activity.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.lion.tetris.R;
import com.example.lion.tetris.controler.GameViewController;

/**
 * Created by lion on 20/02/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    Resources resources;
    private Bitmap blue, cyan, green, magenta, orange, red, yellow;
    public Bitmap background;
    public Bitmap[] bitmaps;
    public int[] drawables;
    public GameViewController gameViewController;
    private GameActivity gameActivity;
    private int blockSize;
    private boolean created = false;


    public GameView(Context context, Resources resources) {
        super(context);
        this.resources = resources;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!created) {
            gameViewController.calculateViewCoordinates();
            background = BitmapFactory.decodeResource(resources, R.drawable.background);
            background = Bitmap.createScaledBitmap(background, gameViewController.getWidthPixels(), gameViewController.getHeightPixels(), true);
            bitmaps = new Bitmap[]{blue, cyan, green, magenta, orange, red, yellow};
            drawables = new int[]{R.drawable.blue, R.drawable.cyan, R.drawable.green, R.drawable.magenta, R.drawable.orange, R.drawable.red, R.drawable.yellow};
            for (int i = 0; i < 7; i++) {
                bitmaps[i] = BitmapFactory.decodeResource(resources, drawables[i]);
                bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i], blockSize, blockSize, true);
            }
            gameViewController.draw(surfaceHolder);
            gameActivity.startDropDownThread();
            created = true;
        }
        gameActivity.hideSystemBars();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void set(GameViewController gameViewController, GameActivity gameActivity) {
        this.gameViewController = gameViewController;
        this.gameActivity = gameActivity;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }
}
