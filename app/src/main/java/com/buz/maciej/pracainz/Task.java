package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

/**
 * Klasa obsługująca zadanie dla robota
 * Created by Varn on 2015-11-08.
 */
public class Task {

    private Coordinates start;
    private Coordinates end;
    private Coordinates goal;
    private Path path1;
    private Path path2;
    private boolean done;
    private boolean gettingPackage;
    private boolean deliveringPackage;
    private boolean load;

    Task()
    {
        done = true;
    }

    Task(Coordinates start, Coordinates end, Coordinates goal, Map map)
    {
        this.start = start;
        this.goal = goal;
        this.end = end;
        path1 = new Path(start, goal,map);
        path2 = new Path(goal, end,map);
        done = false;
        gettingPackage = true;
        deliveringPackage = false;
        load=false;
        path1.makeRoute();
        path2.makeRoute();

    }

    public boolean isDone()
    {
        return done;
    }

    public Coordinates getNextPosition()
    {
        Coordinates nextField = new Coordinates();

        if(deliveringPackage)
        {
            load=false;
            if (path2.isDone())
            {
                deliveringPackage = false;
                done = true;
                nextField = end;
            }
            else nextField = path2.getNextPosition();
        }

        if(gettingPackage)
        {
            if (path1.isDone())
            {
                deliveringPackage = true;
                gettingPackage = false;
                nextField = goal;
                load=true;
            }
            else nextField = path1.getNextPosition();
        }
        return nextField;
    }

    public boolean loadSignal()
    {
        return load;
    }

    public synchronized void remakeRoute(Coordinates obstacle, Coordinates coordinates,Map map)
    {
        if (gettingPackage)
        {
            path1 = new Path(coordinates,goal,map);
            path1.passObstacle(obstacle);
            path2 = new Path (goal, end, map);
            path2.passObstacle(obstacle);
        }

        if (deliveringPackage)
        {
            path2 = new Path (coordinates, end, map);
            path2.passObstacle(obstacle);
        }
    }

    public synchronized void draw(Canvas canvas,int mapSize)
    {
        if (!done)
        {
            double bs = (double)canvas.getHeight()/mapSize;
            int outlineWidth=1;

            ShapeDrawable drawingBlock = new ShapeDrawable();
            ShapeDrawable outline = new ShapeDrawable();

            Paint paint = new Paint();
            paint.setColor(0xff000000);
            paint.setTextSize((int)bs + 2);

            if (!deliveringPackage)
            {
                path1.draw(canvas);

                outline.getPaint().setColor(0xff000000);

                drawingBlock.getPaint().setColor(0xff00ffff);
                drawingBlock.setBounds((int) (goal.getX() * bs + outlineWidth), (int) (goal.getY() * bs + outlineWidth),
                        (int) ((goal.getX() + 1) * bs - outlineWidth), (int) ((goal.getY() + 1) * bs - outlineWidth));
                outline.setBounds((int)(goal.getX() * bs),(int)(goal.getY() * bs),
                        (int)((goal.getX() + 1) * bs),(int)((goal.getY() + 1) * bs));

                outline.draw(canvas);
                drawingBlock.draw(canvas);

                canvas.drawText("1", ((int)(goal.getX() * bs) + 3),(int)(((goal.getY() + 1) * bs - 1)), paint);
            }

            if (!gettingPackage)
            {
                path2.draw(canvas);

                drawingBlock.getPaint().setColor(0xff00ff00);
                drawingBlock.setBounds((int) (end.getX() * bs + outlineWidth), (int) (end.getY() * bs + outlineWidth),
                        (int) ((end.getX() + 1) * bs - outlineWidth), (int) ((end.getY() + 1) * bs - outlineWidth));
                outline.setBounds((int)(end.getX() * bs),(int)(end.getY() * bs),
                        (int)((end.getX() + 1) * bs),(int)((end.getY() + 1) * bs));

                outline.draw(canvas);
                drawingBlock.draw(canvas);

                canvas.drawText("2", ((int)(end.getX() * bs) + 3),(int)(((end.getY() + 1) * bs - 1)), paint);

            }


        }
    }

}
