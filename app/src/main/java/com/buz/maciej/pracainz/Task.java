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

    Task()
    {
        done =true;
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
            nextField= path2.getNextPosition();
            if (path2.end.jestRowne(nextField))
            {
                done =true;
                deliveringPackage =false;
            }
        }

        if(gettingPackage)
        {
            nextField= path1.getNextPosition();
            if (path1.end.jestRowne(nextField))
            {
                deliveringPackage =true;
                gettingPackage =false;
                path1.fieldDone();                          //usuniecie zdublowanego pola
            }
        }
        return nextField;
    }

    public void stepDone()
    {
        if(deliveringPackage)
        {
            path2.fieldDone();
        }

        if(gettingPackage)
        {
            path1.fieldDone();
        }
    }

    public void draw(Canvas canvas,int rozmiarMapy)
    {
        if (!done)
        {
            int wb = canvas.getHeight()/rozmiarMapy;
            int outlineWidth=1;

            ShapeDrawable bloczek = new ShapeDrawable();
            ShapeDrawable outline = new ShapeDrawable();

            Paint paint = new Paint();
            paint.setColor(0xff000000);
            paint.setTextSize(wb+2);

            if (gettingPackage)
            {
                path1.draw(canvas);
            }

            if (deliveringPackage)
            {
                path2.draw(canvas);
            }

            outline.getPaint().setColor(0xff000000);
            
            bloczek.getPaint().setColor(0xff00ffff);
            bloczek.setBounds(goal.getX() * wb + outlineWidth, goal.getY() * wb + outlineWidth,
                    (goal.getX() + 1) * wb - outlineWidth, (goal.getY() + 1) * wb - outlineWidth);
            outline.setBounds(goal.getX() * wb, goal.getY() * wb,
                    (goal.getX() + 1) * wb, (goal.getY() + 1) * wb);
            
            outline.draw(canvas);
            bloczek.draw(canvas);
            

            canvas.drawText("1", (goal.getX() * wb) + 3, ((goal.getY() + 1) * wb - 1), paint);

            bloczek.getPaint().setColor(0xff00ff00);
            bloczek.setBounds(end.getX() * wb + outlineWidth, end.getY() * wb + outlineWidth,
                    (end.getX() + 1) * wb - outlineWidth, (end.getY() + 1) * wb - outlineWidth);
            outline.setBounds(end.getX() * wb, end.getY() * wb,
                    (end.getX() + 1) * wb, (end.getY() + 1) * wb);

            outline.draw(canvas);
            bloczek.draw(canvas);

            canvas.drawText("2", (end.getX() * wb) + 3, ((end.getY() + 1) * wb-1), paint);



        }
    }

}
