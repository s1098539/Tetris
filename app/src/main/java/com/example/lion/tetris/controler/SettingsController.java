package com.example.lion.tetris.controler;

import com.example.lion.tetris.enumeration.TetraShape;
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

//    public int[] rangeI(){
//        int[] range = {0, settings.getI()-1};
//        return range;
//    }
//
//    public int[] rangeJ(){
//        int[] range = {settings.getI(), settings.getI()+ settings.getJ()-1};
//        return range;
//    }
//
//    public int[] rangeL(){
//        int[] range = {settings.getI() + settings.getJ(), settings.getI() + settings.getJ() + settings.getL()-1};
//        return range;
//    }
//
//    public int[] rangeO(){
//        int[] range = {settings.getL(), settings.getI() + settings.getJ() + settings.getL() + settings.getO()-1};
//        return range;
//    }
//
//    public int[] rangeS(){
//        int[] range = {settings.getO(), settings.getI() + settings.getJ() + settings.getL() + settings.getO() + settings.getS()-1};
//        return range;
//    }
//
//    public int[] rangeT(){
//        int[] range = {settings.getS(), settings.getI() + settings.getJ() + settings.getL() + settings.getO() + settings.getS() + settings.getT()-1};
//        return range;
//    }
//
//    public int[] rangeZ(){
//        int[] range = {settings.getT(), settings.getI() + settings.getJ() + settings.getL() + settings.getO() + settings.getS() + settings.getT() + settings.getZ()-1};
//        return range;
//    }

    public int[] range(TetraShape shape){
        int range[] = {0, 0};
        switch (shape) {
            case Z:
                range[0] += settings.getZ();
                range[1] += settings.getT();
            case T:
                range[0] += settings.getS();
                range[1] += settings.getT();
            case S:
                range[0] += settings.getO();
                range[1] += settings.getS();
            case O:
                range[0] += settings.getL();
                range[1] += settings.getO();
            case L:
                range[0] += settings.getJ();
                range[1] += settings.getL();
            case J:
                range[0] += settings.getI();
                range[1] += settings.getJ();
            case I:
                range[1] += settings.getI();

        }
        range[1]++;

        return range;
    }


}
