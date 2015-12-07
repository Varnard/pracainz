package com.buz.maciej.pracainz;

import android.graphics.Canvas;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Varn on 2015-11-08.
 */
public class SystemThread extends Thread{

    private boolean active;
    private boolean rozruch;

    Map map;

    private LinkedList requestList = new LinkedList();
    private LinkedList<Robot> robotList = new LinkedList();                                              //todo ogarnac

    SystemThread(Map map)
    {
        this.map = map;
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
            } catch (Exception e)                                                                           //czeka az zaladuje sie wszystko
            {
                e.printStackTrace();
            }
            rozruch=false;
        }
        while (active)
        {
            try
            {

                for (Robot iterator : robotList)
                {
                    if ((!requestList.isEmpty()) & iterator.isReady())
                    {
                        iterator.newTask((Request) requestList.pop(), map);
                        iterator.pause();
                    }
                    else iterator.continueWorking();

                    if (requestList.isEmpty() & iterator.isReady() & !iterator.isReturning())
                    {
                        iterator.returnBase(map);
                    }
                }
                sleep(75);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}

