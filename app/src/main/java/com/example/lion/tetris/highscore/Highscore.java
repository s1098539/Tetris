package com.example.lion.tetris.highscore;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by lion on 11/03/2018.
 */

public class Highscore{
    private String name;
    private String score;
    private String level;
    private String date;

    public Highscore() {
    }

    public Highscore(String name, String score, String level, String date) {
        this.name = name;
        this.score = score;
        this.level = level;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
