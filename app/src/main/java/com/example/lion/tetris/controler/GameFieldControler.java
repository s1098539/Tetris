package com.example.lion.tetris.controler;

import android.graphics.Point;

import com.example.lion.tetris.activity.game.GameActivity;
import com.example.lion.tetris.enumeration.GameSpot;
import com.example.lion.tetris.model.GameField;
import com.example.lion.tetris.model.Tetraminos;

import static com.example.lion.tetris.enumeration.GameSpot.clear;

/**
 * Created by lion on 16/02/2018.
 */

public class GameFieldControler {

    GameField gameField;
    Tetraminos tetraminos;
    TetraminosControler tetraminosControler;
    CurrentGameValuesController currentGameValuesController;
    GameActivity gameActivity;

    public GameFieldControler() {

    }

    public void newEmptyGameField() {
        GameSpot[][] spots = new GameSpot[10][20];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y<20; y++) {
                spots[x][y] = clear;
            }
        }
        gameField.setGameSpots(spots);
    }

    private boolean checkIfSelf(int x, int y) {
        int x2, y2;
        for (int i = 0; i < 4; i++) {
            x2 = tetraminos.getPoints()[i].x;
            y2 = tetraminos.getPoints()[i].y;
            if (x2 == x && y2 == y) {
                return true;
            }
        }
        return false;
    }

    public boolean validateDown() {
        int y, x;
        for (int i = 0; i < 4; i++) {
            y = tetraminos.getPoints()[i].y;
            if (y > 18) {
                return false;
            }
            x = tetraminos.getPoints()[i].x;
            if (gameField.getGameSpot(x,y+1) != clear && !checkIfSelf(x, y+1)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateLeft() {
        int y, x;
        for (int i = 0; i < 4; i++) {
            x = tetraminos.getPoints()[i].x;
            if (x < 1) {
                return false;
            }
            y = tetraminos.getPoints()[i].y;
            if (gameField.getGameSpot(x-1, y) != clear && !checkIfSelf(x-1, y)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateRight() {
        int y, x;
        for (int i = 0; i < 4; i++) {
            x = tetraminos.getPoints()[i].x;
            if (x > 8) {
                return false;
            }
            y = tetraminos.getPoints()[i].y;
            if (gameField.getGameSpot(x+1, y) != clear && !checkIfSelf(x+1, y)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateRotate() {
        Point[] currentPoints = tetraminos.getPoints();
        int rotation = tetraminos.getRotation();
        int newRotation = rotation + 1;
        if (newRotation == 4) newRotation = 0;
        int x = tetraminos.getPoints()[0].x - 2;
        int y = tetraminos.getPoints()[0].y - 1;
        tetraminosControler.newTetraminos(tetraminos.getShape(), newRotation);
        Point[] validationPoints = tetraminos.getPoints();
        tetraminos.setPoints(currentPoints);
        tetraminos.setRotation(rotation);
        int newX, newY;
        for (int i = 0; i<4; i++) {
            newX = validationPoints[i].x+x;
            newY = validationPoints[i].y+y;
            if (newX > 9 || newX < 0 || newY > 19 || newY < 0) {
                return false;
            }
            if (gameField.getGameSpot(newX, newY) != clear && !checkIfSelf(newX, newY)) {
                return false;
            }
        }
        return true;
    }

    public void saveLocation() {
        int x, y;
        for (int i = 0; i < 4; i++) {
            x = tetraminos.getPoints()[i].x;
            y = tetraminos.getPoints()[i].y;
            if (x<10 && x>-1 && y<20 && y>-1) {
                gameField.setGameSpot(tetraminos.getShape().getSpot(), x, y);
            }
        }
    }

    public void clearLocation() {
        int x, y;
        for (int i = 0; i < 4; i++) {
            x = tetraminos.getPoints()[i].x;
            y = tetraminos.getPoints()[i].y;
            if (x<10 && x>-1 && y<20 && y>-1) {
                gameField.setGameSpot(clear, x, y);
            }
        }
    }

    public void fullRows() {
        int blocks;
        int fullRowCounter = 0;
        for (int y = 19; y > -1; y--) {
            blocks = 0;
            for (int x = 0; x < 10; x++) {
                if (gameField.getGameSpot(x, y) != clear) {
                    blocks++;
                }
            }
            if (blocks == 10) {
                fullRowCounter++;
                for (int row = y; row > 0; row--) {
                    for (int x = 0; x <10; x++) {
                        if (y == 0) gameField.setGameSpot(clear, x, row);
                        else gameField.setGameSpot(gameField.getGameSpot(x, row-1), x, row);
                    }
                }
                y++;
            }
        }
        if (fullRowCounter > 0) {
            currentGameValuesController.madeLine(fullRowCounter);

        }

    }

    public void set(TetraminosControler tetraminosControler, GameField gameField, Tetraminos tetraminos, CurrentGameValuesController currentGameValuesController, GameActivity gameActivity) {
        this.tetraminosControler = tetraminosControler;
        this.gameField = gameField;
        this.tetraminos = tetraminos;
        this.currentGameValuesController = currentGameValuesController;
        this.gameActivity = gameActivity;
    }
}
