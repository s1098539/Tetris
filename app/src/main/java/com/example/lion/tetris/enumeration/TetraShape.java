package com.example.lion.tetris.enumeration;

import static com.example.lion.tetris.enumeration.GameSpot.*;

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
