package com.example.lion.tetris.model;

/**
 * Created by lion on 06/04/2018.
 */

public class Settings {
    int I, J, L, O, S, T, Z;

    public Settings() {
    }

    public Settings(int i, int j, int l, int o, int s, int t, int z) {
        I = i;
        J = j;
        L = l;
        O = o;
        S = s;
        T = t;
        Z = z;
    }

    public void setAll(int i, int j, int l, int o, int s, int t, int z) {
        setI(i);
        setJ(j);
        setL(l);
        setO(o);
        setS(s);
        setT(t);
        setZ(z);
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }

    public int getJ() {
        return J;
    }

    public void setJ(int j) {
        J = j;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getO() {
        return O;
    }

    public void setO(int o) {
        O = o;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }
}