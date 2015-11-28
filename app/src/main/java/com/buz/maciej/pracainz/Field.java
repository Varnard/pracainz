package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-10-07.
 */
public class Field extends Coordinates {

    private double value;

    Field(){
        super();
        this.value =0;
    }

    Field(int x, int y, double value)
    {
        super(x,y);
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

