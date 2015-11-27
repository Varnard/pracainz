package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-10-07.
 */
public class Pole extends Wspolrzedne {

    private double wartosc;

    Pole(){
        super();
        this.wartosc=0;
    }

    Pole(int x, int y, double wartosc)
    {
        super(x,y);
        this.wartosc=wartosc;
    }

    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    public double getWartosc() {
        return wartosc;
    }
}

