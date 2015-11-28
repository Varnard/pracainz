package com.buz.maciej.pracainz;


/**
 * Created by Varn on 2015-11-27.
 */
public class Request {

    private Coordinates goal;
    private Coordinates end;

    Request()
    {
        goal = new Coordinates();
        end = new Coordinates();
    }

    Request(Coordinates goal, Coordinates end)
    {
        this.goal = goal;
        this.end = end;
    }

    public Coordinates getGoal() {
        return goal;
    }

    public void setGoal(Coordinates goal) {
        this.goal = goal;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }
}
