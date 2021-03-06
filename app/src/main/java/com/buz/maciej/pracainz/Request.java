package com.buz.maciej.pracainz;


/**
 * Zlecenie
 * Created by Varn on 2015-11-27.
 */
public class Request {

    private Coordinates goal;
    private Coordinates end;
    private int id=0;

    Request()
    {
        goal = new Coordinates();
        end = new Coordinates();
    }

    Request(Coordinates goal, Coordinates end, int id)
    {
        this.goal = goal;
        this.end = end;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Coordinates getGoal() {
        return goal;
    }

    public Coordinates getEnd() {
        return end;
    }
}
