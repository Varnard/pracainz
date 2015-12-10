package com.buz.maciej.pracainz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Varn on 2015-11-08.
 */
public class SystemThread extends Thread{

    private boolean active;
    private boolean rozruch;
    public int time=1;

    private Map map;
    private Context context;

    private LinkedList<Request> requestList = new LinkedList<>();
    private LinkedList<Robot> robotList = new LinkedList<>();

    SystemThread(Context context)
    {
        map = new Map(context,2);
        this.context = context;
        rozruch = true;
        robotList.add(new Robot(new Coordinates(19, 26)));
        robotList.add(new Robot(new Coordinates(21, 26)));
        robotList.add(new Robot(new Coordinates(19, 27)));
        requestList.add(new Request(new Coordinates(1,2),new Coordinates(37,2)));
        requestList.add(new Request(new Coordinates(23,23),new Coordinates(2,29)));
        requestList.add(new Request(new Coordinates(38,38),new Coordinates(35,1)));
        requestList.add(new Request(new Coordinates(10,5),new Coordinates(2,36)));
        requestList.add(new Request(new Coordinates(33,34),new Coordinates(1,2)));
        requestList.add(new Request(new Coordinates(10,38),new Coordinates(1,34)));
        requestList.add(new Request(new Coordinates(5,1),new Coordinates(18,38)));
        requestList.add(new Request(new Coordinates(2,24),new Coordinates(2,36)));
    }

    public void draw(Canvas canvas)
    {
        map.draw(canvas);
        for (Robot iterator: robotList)
        {
            iterator.drawNextPosition(canvas, map.getSize());
            iterator.drawTask(canvas, map.getSize());
        }

        for (Robot iterator: robotList)
        {
            iterator.drawRobot(canvas, map.getSize());
        }

    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
    @Override
    public void run()
    {
        for (Robot iterator : robotList)
        {
            iterator.pause();
        }
        if (rozruch)
        {
            try
            {
                sleep(500);
            } catch (Exception e)                                                                    //czeka az zaladuje sie wszystko
            {
                e.printStackTrace();
            }
            rozruch=false;
        }
        while (active)
        {
            resolveCollisions();
            try
            {
                for (Robot iterator : robotList)
                {
                    iterator.move();
                    iterator.determineNextPosition();

                    if (iterator.isTaskless())
                    {
                        if (requestList.isEmpty())
                        {
                            if (!iterator.isReturning())iterator.returnBase(map);
                        }
                        else {
                            iterator.newTask(requestList.pop(),map);
                        }
                    }

                }
                updateInfo();
                sleep(time * 75);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public void reset()
    {
        /*
        requestList = new LinkedList<>();
        robotList = new LinkedList<>();

            rozruch = true;
            robotList.add(new Robot(new Coordinates(19, 26)));
            robotList.add(new Robot(new Coordinates(21, 26)));
            robotList.add(new Robot(new Coordinates(19, 27)));*/

            requestList.add(new Request(new Coordinates(10, 2), new Coordinates(20, 2)));
            requestList.add(new Request(new Coordinates(25,2),new Coordinates(1,2)));
            requestList.add(new Request(new Coordinates(2, 38), new Coordinates(38, 38)));

        /*
            requestList.add(new Request(new Coordinates(1,2),new Coordinates(37,2)));
            requestList.add(new Request(new Coordinates(23,23),new Coordinates(2,29)));
            requestList.add(new Request(new Coordinates(38,38),new Coordinates(35,1)));
            requestList.add(new Request(new Coordinates(10,5),new Coordinates(2,36)));
            requestList.add(new Request(new Coordinates(33,34),new Coordinates(1,2)));
            requestList.add(new Request(new Coordinates(10,38),new Coordinates(1,34)));
            requestList.add(new Request(new Coordinates(5,1),new Coordinates(18,38)));
            requestList.add(new Request(new Coordinates(2,24),new Coordinates(2,36)));*/

    }

    private void updateInfo()
    {
        Handler sendToUI = new Handler(Looper.getMainLooper());
        sendToUI.post(new Runnable() {
            public void run()
            {
                Iterator iterator = robotList.iterator();

                Robot robot= (Robot)iterator.next();
                TextView textView = (TextView) ((Activity)context).findViewById(R.id.textViewID1);
                textView.setText(robot.getID().toString());
                textView = (TextView) ((Activity)context).findViewById(R.id.textViewStatus1);
                textView.setText(robot.getStatus());

                int i=0;

                for (int j=0;j<4;j++)
                {
                    if (iterator.hasNext())
                    {
                        robot=(Robot)iterator.next();
                        i++;
                    }

                    switch (i)
                    {
                        case 1:
                        {
                            textView = (TextView) ((Activity) context).findViewById(R.id.textViewID2);
                            textView.setText(robot.getID().toString());
                            textView = (TextView) ((Activity) context).findViewById(R.id.textViewStatus2);
                            textView.setText(robot.getStatus());
                            break;
                        }

                        case 2:
                        {
                            textView = (TextView) ((Activity) context).findViewById(R.id.textViewID3);
                            textView.setText(robot.getID().toString());
                            textView = (TextView) ((Activity) context).findViewById(R.id.textViewStatus3);
                            textView.setText(robot.getStatus());
                            break;
                        }
                    }
                }
            }
        });
    }

    private void resolveCollisions()
    {
        LinkedList<Coordinates> occupied = new LinkedList<>();

        for (Robot rIterator : robotList)
        {
            occupied.add(rIterator.getCurrentPosition());
        }

        for (Robot rIterator : robotList)
        {
            boolean collision = false;
            for (Coordinates cIterator : occupied)
            {
                if (!(rIterator.getCurrentPosition().equals(rIterator.getNextPosition())) &&
                        rIterator.getNextPosition().equals(cIterator))collision=true;
            }

            if (collision)
            {
                if (!rIterator.isWaiting())
                {
                    for (Robot rIterator2 : robotList) {
                        if (rIterator.getNextPosition().equals(rIterator2.getCurrentPosition()) &&
                                rIterator.getCurrentPosition().equals(rIterator2.getNextPosition())) {
                            rIterator.passObstacle(rIterator.getNextPosition(), map);
                            rIterator.determineNextPosition();
                            rIterator2.pause();
                            break;
                        } else rIterator.pause();
                    }
                }
            }
            else occupied.add(rIterator.getNextPosition());
        }
    }

}


