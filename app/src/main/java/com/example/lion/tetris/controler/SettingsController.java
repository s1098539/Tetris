package com.example.lion.tetris.controler;

import com.example.lion.tetris.model.Settings;

/**
 * Created by lion on 06/04/2018.
 */


public class SettingsController {

    Settings settings;

    public void set(Settings settings) {
        this.settings = settings;
    }

    public int sumOfOdds() {
        return settings.getI() + settings.getJ() + settings.getL() + settings.getO() + settings.getS() + settings.getT() + settings.getZ();
    }

    public int[] rangeI(){
        int[] range = {0, settings.getI()-1};
        return range;
    }

    public int[] rangeJ(){
        int[] range = {settings.getI(), settings.getJ()-1};
        return range;
    }

    public int[] rangeL(){
        int[] range = {settings.getJ(), settings.getL()-1};
        return range;
    }

    public int[] rangeO(){
        int[] range = {settings.getL(), settings.getO()-1};
        return range;
    }

    public int[] rangeS(){
        int[] range = {settings.getO(), settings.getS()-1};
        return range;
    }

    public int[] rangeT(){
        int[] range = {settings.getS(), settings.getT()-1};
        return range;
    }

    public int[] rangeZ(){
        int[] range = {settings.getT(), settings.getZ()-1};
        return range;
    }


}
