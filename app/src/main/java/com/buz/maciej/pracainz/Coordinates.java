package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-11-22.
 */
public class Coordinates {
    private int x;
    private int y;

    public Coordinates(){
        this.x=0;
        this.y=0;
    }

    Coordinates(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    public boolean equals(Coordinates coordinates2)
    {
        if (coordinates2.getX()==x && coordinates2.getY()==y)return true;
        else return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
