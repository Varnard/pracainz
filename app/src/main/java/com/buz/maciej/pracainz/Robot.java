package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * klasa symulujaca robota
 * Created by Varn on 2015-10-12.
 */
public class Robot {

    private Coordinates basePosition;
    private Coordinates nextPosition;
    private Coordinates currentPosition;
    private boolean returning;
    private boolean waiting;
    private Task currentTask;

    Robot(Coordinates basePosition)
    {
        this.basePosition=basePosition;
        this.currentPosition = basePosition;
        currentTask = new Task();
    }

    public void newTask(Request request, Map map)
    {
        currentTask = new Task(currentPosition,request.getEnd(),request.getGoal(),map);
        returning=false;
    }

    public void returnBase(Map map)
    {
        currentTask= new Task(currentPosition,basePosition,new Coordinates(12,29),map);
        returning=true;
    }

    public void pause()
    {
        waiting=true;
    }

    public boolean isReturning()
    {
        return returning;
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
        if (waiting)
        {
            waiting=false;
        }
        else
        {
            if (!currentTask.isDone())
            {
                setNextPosition(currentTask.getNextPosition());
                move();
                currentTask.stepDone();
            }
        }
    }

    public void drawTask(Canvas canvas, int mapSize)
    {
        currentTask.draw(canvas, mapSize);
    }

    public synchronized void drawRobot(Canvas canvas, int mapSize)
    {
        int bs = canvas.getHeight()/mapSize;
        int outlineWidth = 2;

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
    public void draw(Canvas canvas, int mapSize)
    {
        currentTask.draw(canvas, mapSize);
        drawRobot(canvas, mapSize);
    }

}
