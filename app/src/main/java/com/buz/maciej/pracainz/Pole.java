package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-10-07.
 */
public class Pole {

    private int x;
    private int y;
    private int wartosc;

    Pole(){};

    Pole(int x, int y, int wartosc)
    {
        this.x=x;
        this.y=y;
        this.wartosc=wartosc;
    }


    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWartosc() {
        return wartosc;
    }
}

