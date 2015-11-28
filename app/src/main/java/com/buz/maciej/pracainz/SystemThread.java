package com.buz.maciej.pracainz;

import android.graphics.Canvas;

import java.util.LinkedList;

/**
 * Created by Varn on 2015-11-08.
 */
public class SystemThread extends Thread{

    private boolean active;

    Map map;
    Robot rob1;

    private LinkedList requestList = new LinkedList();

    SystemThread(Map map)
    {
        this.map = map;
        Request request1 = new Request(new Coordinates(1,2),new Coordinates(37,2));
        Request request2 = new Request(new Coordinates(23,23),new Coordinates(2,29));
        Request request3 = new Request(new Coordinates(38,38),new Coordinates(35,1));
        Request request4 = new Request(new Coordinates(1,5),new Coordinates(38,18));
        rob1= new Robot(new Coordinates(1, 10));
        requestList.add(request1);
        requestList.add(request2);
        requestList.add(request3);
        requestList.add(request4);
    }

    public void draw(Canvas canvas)
    {
        map.draw(canvas);
        rob1.draw(canvas, map.getSize());
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
    @Override
    public void run()
    {
        while (active)
        {
            try
            {
                if ((!requestList.isEmpty()) & rob1.isReady())
                {
                    rob1.newTask((Request) requestList.pop(), map);
                }
                else rob1.continueWorking();
                sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}

