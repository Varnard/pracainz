package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
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
    private int id;
    static int count=0;


    Robot(Coordinates basePosition)
    {
        this.basePosition = basePosition;
        this.currentPosition = basePosition;
        this.nextPosition = basePosition;
        currentTask = new Task();
        count++;
        id=count;
    }

    public void newTask(Request request, Map map)
    {
        currentTask = new Task(currentPosition,request.getEnd(),request.getGoal(),map);
        returning=false;
    }

    public void returnBase(Map map)
    {
        currentTask= new Task(currentPosition,basePosition,basePosition,map);
        returning=true;
    }

    public void pause()
    {
        waiting=true;
    }

    public void passObstacle(Coordinates obstacle,Map map)
    {
        currentTask.remakeRoute(obstacle,currentPosition,map);
        nextPosition=currentPosition;

    }
    public boolean isReturning()
    {
        return returning;
    }

    public boolean isWaiting()
    {
        return waiting;
    }

    public void setNextPosition(Coordinates nextPosition)
    {
        this.nextPosition = nextPosition;
    }

    public Coordinates getCurrentPosition() {
        return currentPosition;
    }

    public Coordinates getNextPosition() {
        return nextPosition;
    }


    public boolean isTaskless()
    {
        if (currentTask.isDone()) return true;
        else return false;
    }

    public synchronized void move()
    {
        if (!waiting)currentPosition = nextPosition;
    }

     public void determineNextPosition()
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
            }
            else unloading();

            if (currentTask.loadSignal())loading();
        }
    }

    private void loading()
    {
        waiting=true;
    }

    private void unloading()
    {
        waiting=true;
    }

    public void drawTask(Canvas canvas, int mapSize)
    {
        currentTask.draw(canvas, mapSize);
    }

    public void drawNextPosition(Canvas canvas, int mapSize)
    {
        int bs = canvas.getHeight() / mapSize;
        ShapeDrawable drawingBlock = new ShapeDrawable();

        drawingBlock.setShape(new OvalShape());
        drawingBlock.getPaint().setColor(0xffff0000);
        drawingBlock.setBounds(nextPosition.getX() * bs + (bs / 4), nextPosition.getY() * bs + (bs / 4),
                (nextPosition.getX() + 1) * bs - (bs / 4), (nextPosition.getY() + 1) * bs - (bs / 4));
        drawingBlock.draw(canvas);
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

        robot.getPaint().setColor(0xffafff00);


        switch (id)
        {
            case 1:
            {
                robot.getPaint().setColor(0xff00ff00);
                break;
            }
            case 2:
            {
                robot.getPaint().setColor(0xffffff00);
                break;
            }
            case 3:
            {
                robot.getPaint().setColor(0xff00ffff);
                break;
            }

        }


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
