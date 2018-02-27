package com.example.lion.tetris.enumeration;

import com.example.lion.tetris.R;

/**
 * Created by lion on 16/02/2018.
 */

public enum GameSpot {
    red(R.drawable.red, 5), yellow(R.drawable.yellow, 6), orange(R.drawable.orange,4 ), green(R.drawable.green, 2), cyan(R.drawable.cyan, 1), blue(R.drawable.blue, 0), magenta(R.drawable.magenta,3 ), clear(R.drawable.clear, 7);

    private int drawable;
    private int index;

    GameSpot(int drawable, int index) {
        this.drawable = drawable;
        this.index = index;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getIndex() {
        return index;
    }
}
