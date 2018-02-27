package com.example.lion.tetris.controler;

import android.graphics.Point;

import com.example.lion.tetris.R;
import com.example.lion.tetris.enumeration.TetraShape;
import com.example.lion.tetris.model.Tetraminos;
import java.util.Random;

import static com.example.lion.tetris.enumeration.TetraShape.*;

/**
 * Created by lion on 16/02/2018.
 */

public class TetraminosControler {

    Random random;
    Tetraminos tetraminos;
    GameFieldControler gameFieldControler;

    public TetraminosControler() {
    }

    public int getColor() {
//        return tetraminos.getShape().getSpot().getDrawable();
        switch (tetraminos.getShape()) {
            case I: return R.drawable.red;
            case J: return R.drawable.yellow;
            case L: return R.drawable.magenta;
            case O: return R.drawable.blue;
            case S: return R.drawable.cyan;
            case T: return R.drawable.green;
            default: return R.drawable.orange;
        }
    }

    public void newRandomTetraminos() {
        random = new Random();
        int randomShape = random.nextInt(7);
        int randomDirection = random.nextInt(4);
        TetraShape shape;
        switch (randomShape) {
            case 0: shape = I;
                break;
            case 1: shape = J;
                break;
            case 2: shape = L;
                break;
            case 3: shape = O;
                break;
            case 4: shape = S;
                break;
            case 5: shape = T;
                break;
            default: shape = Z;
        }
        newTetraminos(shape, randomDirection);
    }

    public void newTetraminos(TetraShape shape, int rotation) {
        tetraminos.setShape(shape);
        tetraminos.setRotation(rotation);
        setPoints();
    }

    private void setPoints() {
        switch (tetraminos.getShape()) {
            case I: setIPoints();
                break;
            case J: setJPoints();
                break;
            case L: setLPoints();
                break;
            case O: setOPoints();
                break;
            case S: setSPoints();
                break;
            case T: setTPoints();
                break;
            case Z: setZPoints();
                break;
        }
    }

    public void moveDown() {
        Point[] oldPoints = tetraminos.getPoints();
        Point[] newPoints = new Point[4];
        for (int i = 0; i < 4; i++) {
            newPoints[i] = new Point(Integer.valueOf(oldPoints[i].x), Integer.valueOf(oldPoints[i].y) + 1);
        }
        tetraminos.setPoints(newPoints);
    }

    public void moveRight() {
        Point[] oldPoints = tetraminos.getPoints();
        Point[] newPoints = new Point[4];
        for(int i = 0; i<4; i++) {
            newPoints[i] = new Point(Integer.valueOf(oldPoints[i].x+1),Integer.valueOf(oldPoints[i].y));
        }
        tetraminos.setPoints(newPoints);
    }

    public void moveLeft() {
        Point[] oldPoints = tetraminos.getPoints();
        Point[] newPoints = new Point[4];
        for(int i = 0; i<4; i++) {
            newPoints[i] = new Point(Integer.valueOf(oldPoints[i].x-1),Integer.valueOf(oldPoints[i].y));
        }
        tetraminos.setPoints(newPoints);
    }

    public void rotate() {
        int currentRotation = tetraminos.getRotation();
        int newRotation = currentRotation+1;
        if (newRotation == 4) newRotation = 0;
        int x = tetraminos.getPoints()[0].x - 2;
        int y = tetraminos.getPoints()[0].y - 1;
        Point[] oldPoints = tetraminos.getPoints();
        newTetraminos(tetraminos.getShape(), newRotation);
        Point[] setupPoints = tetraminos.getPoints();
        tetraminos.setPoints(oldPoints);
        Point[] newPoints = new Point[4];
        int newX, newY;
        boolean valid = true;
        for (int i = 0; i<4; i++) {
            newX = setupPoints[i].x+x;
            newY = setupPoints[i].y+y;
            if (newY<19) {
                newPoints[i] = new Point(newX, newY);
            } else {
                valid = false;
            }
        }
        if (valid) tetraminos.setPoints(newPoints);
    }

    private void setIPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(3,1);
                        points[2] = new Point(0,1);
                        points[3] = new Point(1,1);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(2,2);
                        points[2] = new Point(2,3);
                        points[3] = new Point(2,0);
        }
        tetraminos.setPoints(points);
    }

    private void setJPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:     points[0] = new Point(2,1);
                        points[1] = new Point(1,1);
                        points[2] = new Point(3,1);
                        points[3] = new Point(3,2);
                    break;
            case 1:     points[0] = new Point(2,1);
                        points[1] = new Point(2,2);
                        points[2] = new Point(2,0);
                        points[3] = new Point(3,0);
                    break;
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(3,1);
                        points[2] = new Point(1,1);
                        points[3] = new Point(1,0);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(2,2);
                        points[3] = new Point(1,2);
        }
        tetraminos.setPoints(points);
    }

    private void setLPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:     points[0] = new Point(2,1);
                        points[1] = new Point(1,1);
                        points[2] = new Point(3,1);
                        points[3] = new Point(1,2);
                    break;
            case 1:     points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(1,0);
                        points[3] = new Point(2,2);
                    break;
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(3,0);
                        points[2] = new Point(1,1);
                        points[3] = new Point(3,1);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(3,2);
                        points[3] = new Point(2,2);
        }
        tetraminos.setPoints(points);
    }

    private void setOPoints() {
        Point[] points = new Point[4];
        points[0] = new Point(2,1);
        points[1] = new Point(2,2);
        points[2] = new Point(1,1);
        points[3] = new Point(1,2);
        tetraminos.setPoints(points);
    }

    private void setSPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(3,1);
                        points[2] = new Point(2,2);
                        points[3] = new Point(1,2);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(3,1);
                        points[3] = new Point(3,2);
        }
        tetraminos.setPoints(points);
    }

    private void setTPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:     points[0] = new Point(2,1);
                        points[1] = new Point(3,1);
                        points[2] = new Point(1,1);
                        points[3] = new Point(2,2);
                    break;
            case 1:     points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(2,2);
                        points[3] = new Point(3,1);
                    break;
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(1,1);
                        points[2] = new Point(3,1);
                        points[3] = new Point(2,0);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(2,0);
                        points[2] = new Point(2,2);
                        points[3] = new Point(1,1);
        }
        tetraminos.setPoints(points);
    }

    private void setZPoints() {
        Point[] points = new Point[4];
        switch (tetraminos.getRotation()) {
            case 0:
            case 2:     points[0] = new Point(2,1);
                        points[1] = new Point(1,1);
                        points[2] = new Point(2,2);
                        points[3] = new Point(3,2);
                    break;
            default:    points[0] = new Point(2,1);
                        points[1] = new Point(3,0);
                        points[2] = new Point(3,1);
                        points[3] = new Point(2,2);
        }
        tetraminos.setPoints(points);
    }

    public void set(GameFieldControler gameFieldControler, Tetraminos tetraminos) {
        this.gameFieldControler = gameFieldControler;
        this.tetraminos = tetraminos;
    }
}
