package com.example.lion.tetris.model;

/**
 * Created by lion on 22/02/2018.
 */

public class CurrentGameValues {
    int level = 0;
    int score = 0;
    int pointsFor1Line = 40;
    int pointsFor2Lines = 100;
    int pointsFor3Lines = 300;
    int pointsFor4Lines = 1200;
    int thisLevelLines = 0;

    public int getThisLevelLines() {
        return thisLevelLines;
    }

    public void setThisLevelLines(int thisLevelLines) {
        this.thisLevelLines = thisLevelLines;
    }

    public CurrentGameValues() {
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {

        return level;
    }

    public int getScore() {
        return score;
    }

    public int getPointsFor1Line() {
        return pointsFor1Line;
    }

    public int getPointsFor2Lines() {
        return pointsFor2Lines;
    }

    public int getPointsFor3Lines() {
        return pointsFor3Lines;
    }

    public int getPointsFor4Lines() {
        return pointsFor4Lines;
    }
}
