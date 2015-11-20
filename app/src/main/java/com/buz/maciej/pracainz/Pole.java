package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-10-07.
 */
public class Pole {

    private int x;
    private int y;
    private double wartosc;

    Pole(){
        this.x=0;
        this.y=0;
        this.wartosc=0;
    }

    Pole(int x, int y)
    {
        this.x=x;
        this.y=y;
        this.wartosc=0;
    }

    Pole(int x, int y, double wartosc)
    {
        this.x=x;
        this.y=y;
        this.wartosc=wartosc;
    }

    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWartosc() {
        return wartosc;
    }
}

