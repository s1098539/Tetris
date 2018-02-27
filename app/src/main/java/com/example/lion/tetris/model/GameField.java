package com.example.lion.tetris.model;

import com.example.lion.tetris.enumeration.GameSpot;

/**
 * Created by lion on 16/02/2018.
 */

public class GameField {
    private GameSpot[][] gameSpots;

    public GameField() {

    }

    public GameSpot[][] getGameSpots() {
        return gameSpots;
    }

    public void setGameSpots(GameSpot[][] gameSpots) {
        this.gameSpots = gameSpots;
    }

    public GameSpot getGameSpot(int x, int y) {
        return gameSpots[x][y];
    }

    public void setGameSpot(GameSpot gameSpot, int x, int y) {
        gameSpots[x][y] = gameSpot;
    }
}
