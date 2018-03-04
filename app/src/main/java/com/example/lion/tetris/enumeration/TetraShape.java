package com.example.lion.tetris.enumeration;

import static com.example.lion.tetris.enumeration.GameSpot.blue;
import static com.example.lion.tetris.enumeration.GameSpot.cyan;
import static com.example.lion.tetris.enumeration.GameSpot.green;
import static com.example.lion.tetris.enumeration.GameSpot.magenta;
import static com.example.lion.tetris.enumeration.GameSpot.orange;
import static com.example.lion.tetris.enumeration.GameSpot.red;
import static com.example.lion.tetris.enumeration.GameSpot.yellow;

/**
 * Created by lion on 16/02/2018.
 */

public enum TetraShape {
    I(red) , J(yellow), L(magenta), O(blue), S(cyan), T(green), Z(orange);

    private GameSpot spot;

    TetraShape(GameSpot spot) {
        this.spot = spot;
    }

    public GameSpot getSpot() {
        return spot;
    }
}
