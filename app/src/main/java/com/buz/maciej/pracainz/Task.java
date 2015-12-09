package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

/**
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
            int bs = canvas.getHeight()/mapSize;
            int outlineWidth=1;

            ShapeDrawable bloczek = new ShapeDrawable();
            ShapeDrawable outline = new ShapeDrawable();

            Paint paint = new Paint();
            paint.setColor(0xff000000);
            paint.setTextSize(bs + 2);

            if (!deliveringPackage)
            {
                path1.draw(canvas);

                outline.getPaint().setColor(0xff000000);

                bloczek.getPaint().setColor(0xff00ffff);
                bloczek.setBounds(goal.getX() * bs + outlineWidth, goal.getY() * bs + outlineWidth,
                        (goal.getX() + 1) * bs - outlineWidth, (goal.getY() + 1) * bs - outlineWidth);
                outline.setBounds(goal.getX() * bs, goal.getY() * bs,
                        (goal.getX() + 1) * bs, (goal.getY() + 1) * bs);

                outline.draw(canvas);
                bloczek.draw(canvas);

                canvas.drawText("1", (goal.getX() * bs) + 3, ((goal.getY() + 1) * bs - 1), paint);
            }

            if (!gettingPackage)
            {
                path2.draw(canvas);

                bloczek.getPaint().setColor(0xff00ff00);
                bloczek.setBounds(end.getX() * bs + outlineWidth, end.getY() * bs + outlineWidth,
                        (end.getX() + 1) * bs - outlineWidth, (end.getY() + 1) * bs - outlineWidth);
                outline.setBounds(end.getX() * bs, end.getY() * bs,
                        (end.getX() + 1) * bs, (end.getY() + 1) * bs);

                outline.draw(canvas);
                bloczek.draw(canvas);

                canvas.drawText("2", (end.getX() * bs) + 3, ((end.getY() + 1) * bs - 1), paint);

            }


        }
    }

}
