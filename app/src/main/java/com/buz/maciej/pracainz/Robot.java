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
    private boolean unloaded;
    private Task currentTask;
    private Integer id;
    private String status;
    static int count=0;


    Robot(Coordinates basePosition)
    {
        this.basePosition = basePosition;
        this.currentPosition = basePosition;
        this.nextPosition = basePosition;
        unloaded=true;
        waiting=false;
        returning=true;
        status="idle";
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

    public Integer getID()
    {
        return id;
    }

    public String getStatus()
    {
        return status;
    }

    public Coordinates getCurrentPosition() {
        return currentPosition;
    }

    public Coordinates getNextPosition() {
        return nextPosition;
    }


    public boolean isTaskless()
    {
        return (currentTask.isDone() && !waiting && unloaded);
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
                if (returning)status="returning";
                else status="working";
            }
            else
            {
                if (returning)status="idle";
                else unloading();
            }

            if (currentTask.loadSignal() && !returning)loading();
        }
    }

    private void loading()
    {
        unloaded=false;
        status="loading";
        waiting=true;
    }

    private void unloading()
    {
        unloaded=true;
        status="unloading";
        waiting=true;
    }

    public void drawTask(Canvas canvas, int mapSize)
    {
        currentTask.draw(canvas, mapSize);
    }

    public void drawNextPosition(Canvas canvas, int mapSize)
    {
        double bs = (double)canvas.getHeight() / mapSize;
        ShapeDrawable drawingBlock = new ShapeDrawable();

        drawingBlock.setShape(new OvalShape());
        drawingBlock.getPaint().setColor(0xffff0000);
        drawingBlock.setBounds((int)(nextPosition.getX() * bs + (bs / 4)),(int) (nextPosition.getY() * bs + (bs / 4)),
                (int)((nextPosition.getX() + 1) * bs - (bs / 4)),(int) ((nextPosition.getY() + 1) * bs - (bs / 4)));
        drawingBlock.draw(canvas);
    }

    public synchronized void drawRobot(Canvas canvas, int mapSize)
    {
        double bs = (double)canvas.getHeight()/mapSize;
        int outlineWidth = 2;

        ShapeDrawable robot = new ShapeDrawable();
        ShapeDrawable outline = new ShapeDrawable();

        robot.setShape(new OvalShape());
        robot.setBounds((int)(currentPosition.getX() * bs + outlineWidth),(int) (currentPosition.getY() * bs + outlineWidth),
                (int)((currentPosition.getX() + 1) * bs - outlineWidth),(int)((currentPosition.getY() + 1) * bs - outlineWidth));

        robot.getPaint().setColor(0xffffaf00);


        outline.setShape(new OvalShape());
        outline.setBounds((int)(currentPosition.getX() * bs),(int)(currentPosition.getY() * bs),
                (int)((currentPosition.getX() + 1) * bs),(int)((currentPosition.getY() + 1) * bs));
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
