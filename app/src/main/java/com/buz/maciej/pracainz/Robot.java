package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * klasa symulujaca robota
 * Created by Varn on 2015-10-12.
 */
public class Robot {

    private Coordinates nextPosition;
    private Coordinates currentPosition;
    private Task currentTask;

    Robot(Coordinates currentPosition)
    {
        this.currentPosition = currentPosition;
        currentTask = new Task();
    }

    public void newTask(Request request, Map map)
    {
        currentTask = new Task(currentPosition,request.getEnd(),request.getGoal(),map);
    }

    public void setNextPosition(Coordinates nextPosition)
    {
        this.nextPosition = nextPosition;
    }

    public boolean isReady()
    {
        if (currentTask.isDone()) return true;
        else return false;
    }

    public synchronized void move()
    {
        currentPosition = nextPosition;
    }

     public void continueWorking()
    {
        if (!currentTask.isDone())
        {
            setNextPosition(currentTask.getNextPosition());
            move();
            currentTask.stepDone();
        }
    }

    public synchronized void draw(Canvas canvas, int mapSize)
    {
        int bs = canvas.getHeight()/mapSize;
        int outlineWidth = 2;

        currentTask.draw(canvas, mapSize);

        ShapeDrawable robot = new ShapeDrawable();
        ShapeDrawable outline = new ShapeDrawable();

        robot.setShape(new OvalShape());
        robot.setBounds(currentPosition.getX() * bs + outlineWidth, currentPosition.getY() * bs + outlineWidth,
                (currentPosition.getX() + 1) * bs - outlineWidth, (currentPosition.getY() + 1) * bs - outlineWidth);
        robot.getPaint().setColor(0xffffff00);

        outline.setShape(new OvalShape());
        outline.setBounds(currentPosition.getX() * bs, currentPosition.getY() * bs,
                (currentPosition.getX() + 1) * bs, (currentPosition.getY() + 1) * bs);
        outline.getPaint().setColor(0xff000000);

        outline.draw(canvas);
        robot.draw(canvas);



    }

}
