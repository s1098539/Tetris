package com.example.lion.tetris.model;

import android.graphics.Point;

import com.example.lion.tetris.enumeration.TetraShape;

/**
 * Created by lion on 16/02/2018.
 */

public class Tetraminos {
    private Point[] points;
    private TetraShape shape;
    private int rotation;

    public Tetraminos(Point[] points, TetraShape shape, int rotation) {
        this.points = points;
        this.shape = shape;
        this.rotation = rotation;
    }

    public Tetraminos() {
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public TetraShape getShape() {
        return shape;
    }

    public void setShape(TetraShape shape) {
        this.shape = shape;
    }
}
