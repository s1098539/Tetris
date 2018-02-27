package com.example.lion.tetris.controler;

import com.example.lion.tetris.model.CurrentGameValues;

/**
 * Created by lion on 22/02/2018.
 */

public class CurrentGameValuesController {

    CurrentGameValues currentGameValues;

    public CurrentGameValuesController() {
    }

    public int getGameSpeed() {
        int level = currentGameValues.getLevel();
        if (level > 28) return 20;
        if (level > 18) return 30;
        if (level > 15) return 50;
        if (level > 12) return 70;
        if (level > 9) return 80;
        if (level == 9) return 100;
        if (level == 8) return 130;
        if (level == 7) return 220;
        if (level == 6) return 300;
        if (level == 5) return 380;
        if (level == 4) return 470;
        if (level == 3) return 550;
        if (level == 2) return 630;
        if (level == 1) return 720;
        return 800;
    }

    private void nextLevel() {
        currentGameValues.setLevel(currentGameValues.getLevel()+1);
        currentGameValues.setThisLevelLines(0);
    }

    private void addToScore(int points) {
        currentGameValues.setScore(currentGameValues.getScore()+points);
    }

    public void madeLine(int amount) {
        int points;
        switch (amount) {
            case 1: points = currentGameValues.getPointsFor1Line();
            break;
            case 2: points = currentGameValues.getPointsFor2Lines();
            break;
            case 3: points = currentGameValues.getPointsFor3Lines();
            break;
            case 4: points = currentGameValues.getPointsFor4Lines();
            break;
            default: points = 0;
        }
        points = points*(currentGameValues.getLevel()+1);
        addToScore(points);
        addLevelLine(amount);

    }

    private void addLevelLine(int amount) {
        currentGameValues.setThisLevelLines(currentGameValues.getThisLevelLines()+amount);
        if (currentGameValues.getThisLevelLines() > 4) nextLevel();
    }

    public void set(CurrentGameValues currentGameValues) {
        this.currentGameValues = currentGameValues;
    }

    public String getScore() {
        return String.valueOf(currentGameValues.getScore());
    }

    public String getLevel() {
        return String.valueOf(currentGameValues.getLevel());
    }
}
