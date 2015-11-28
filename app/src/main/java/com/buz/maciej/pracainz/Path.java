package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


/**
 * klasa wyznaczajaca droge
 * Created by Varn on 2015-10-06.
 */
public class Path {

    private Stack route = new Stack();

    Field[][] map;
    Coordinates start;
    Coordinates end;
    int size;

    Path(Coordinates start, Coordinates end, Map map)
    {
        size = map.getSize();
        this.map = new Field[size][size];
        this.start = start;
        this.end = end;
        for (int i=0; i< size;i++)
        {
            for (int j=0; j< size; j++)
            {
                if (map.getEdges()[i][j]==true) this.map[i][j]= new Field(i,j,-2);
                if (map.getEdges()[i][j]==false) this.map[i][j]= new Field(i,j,-1);
            }
        }
    }

    public void makeRoute()
    {
        LinkedList queue = new LinkedList();
        Field temp;

        queue.add(new Field(start.getX(), start.getY(), 0));
        try {
            while (queue.isEmpty() == false) {
               temp = (Field) queue.remove();
                map[temp.getX()][temp.getY()].setValue(temp.getValue());

                 for (Field iterator: returnNeighboursOnSides(temp))
                 {
                    if (iterator.getValue()==-1)
                    {
                        iterator.setValue(temp.getValue() + 1);
                        map[iterator.getX()][iterator.getY()].setValue(iterator.getValue());
                        queue.add(iterator);
                    }
                }

                for (Field iterator: returnNeighboursOnCorners(temp))
                {
                    if (iterator.getValue()==-1)
                    {
                        iterator.setValue(temp.getValue() + 1.5);
                        map[iterator.getX()][iterator.getY()].setValue(iterator.getValue());
                        queue.add(iterator);
                    }
                }


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        route.push(map[end.getX()][end.getY()]);

        double i= map[end.getX()][end.getY()].getValue();

        while(i>0)
        {
            for (Field iterator: returnNeighbours((Field) route.peek()))
            {
                if (iterator.getValue()==((Field) route.peek()).getValue()-1.5)
                {
                    route.add(iterator);
                    break;
                }
                if (iterator.getValue()==((Field) route.peek()).getValue()-1)
                {
                    route.add(iterator);
                    break;
                }
            }
            i--;
        }

    }

    public synchronized Field getNextPosition()
    {
        return (Field) route.peek();
    }

    public synchronized void fieldDone()
    {
     route.pop();
    }

    public synchronized void draw(Canvas canvas)
    {
        int bs = canvas.getHeight()/ size;                     //oblicza wielkosc pojedynczego bloczka do rysowania
        ShapeDrawable drawingBlock = new ShapeDrawable();

        drawingBlock.setShape(new OvalShape());

        drawingBlock.getPaint().setColor(0xffff0000);
        Iterator iterator = route.iterator();

        while (iterator.hasNext())
        {
            Field next = (Field)iterator.next();
                drawingBlock.setBounds(next.getX() * bs + (bs / 4), next.getY() * bs + (bs / 4),
                        (next.getX() + 1) * bs - (bs / 4), (next.getY() + 1) * bs - (bs / 4));
                drawingBlock.draw(canvas);
        }

    }

    private Field[] returnNeighbours(Field base)
    {
        Field[] neighbours = new Field[8];
        Boolean c1=false, c2=false, c3=false, c4=false;
        if (base.getX()+1< size)c1=true;
        if (base.getX()>0)c2=true;
        if (base.getY()+1< size)c3=true;
        if (base.getY()>0)c4=true;

          if (c2 & c4)neighbours[0] = new Field(base.getX() - 1, base.getY() - 1, map[base.getX() - 1][base.getY() - 1].getValue());
        else  neighbours[0] = new Field(base.getX(), base.getY(), -2);
        if (c4)neighbours[1] = new Field(base.getX(), base.getY() - 1, map[base.getX()][base.getY() - 1].getValue());
        else  neighbours[1] = new Field(base.getX(), base.getY(), -2);
        if (c1 & c4)neighbours[2] = new Field(base.getX() + 1, base.getY() - 1, map[base.getX() + 1][base.getY() - 1].getValue());
        else  neighbours[2] = new Field(base.getX(), base.getY(), -2);
        if (c2)neighbours[3] = new Field(base.getX() - 1, base.getY(), map[base.getX() - 1][base.getY()].getValue());
        else  neighbours[3] = new Field(base.getX(), base.getY(), -2);
        if (c1)neighbours[4] = new Field(base.getX() + 1, base.getY(), map[base.getX() + 1][base.getY()].getValue());
        else  neighbours[4] = new Field(base.getX(), base.getY(), -2);
        if (c2 & c3)neighbours[5] = new Field(base.getX() - 1, base.getY() + 1, map[base.getX() - 1][base.getY() + 1].getValue());
        else  neighbours[5] = new Field(base.getX(), base.getY(), -2);
        if (c3)neighbours[6] = new Field(base.getX(), base.getY() + 1, map[base.getX()][base.getY() + 1].getValue());
        else  neighbours[6] = new Field(base.getX(), base.getY(), -2);
        if (c1 & c3)neighbours[7] = new Field(base.getX() + 1, base.getY() + 1, map[base.getX() + 1][base.getY() + 1].getValue());
        else  neighbours[7] = new Field(base.getX(), base.getY(), -2);
        return neighbours;
    }

    private Field[] returnNeighboursOnSides(Field base)
    {
        Field[] neighbours = new Field[4];
        Boolean c1=false, c2=false, c3=false, c4=false;
        if (base.getX()+1< size)c1=true;
        if (base.getX()>0)c2=true;
        if (base.getY()+1< size)c3=true;
        if (base.getY()>0)c4=true;

        if (c4)neighbours[0] = new Field(base.getX(), base.getY() - 1, map[base.getX()][base.getY() - 1].getValue());
        else  neighbours[0] = new Field(base.getX(), base.getY(), -2);
        if (c2)neighbours[1] = new Field(base.getX() - 1, base.getY(), map[base.getX() - 1][base.getY()].getValue());
        else  neighbours[1] = new Field(base.getX(), base.getY(), -2);
        if (c1)neighbours[2] = new Field(base.getX() + 1, base.getY(), map[base.getX() + 1][base.getY()].getValue());
        else  neighbours[2] = new Field(base.getX(), base.getY(), -2);
        if (c3)neighbours[3] = new Field(base.getX(), base.getY() + 1, map[base.getX()][base.getY() + 1].getValue());
        else  neighbours[3] = new Field(base.getX(), base.getY(), -2);
        return neighbours;
    }

    private Field[] returnNeighboursOnCorners(Field base)
    {
        Field[] neighbours = new Field[4];
        Boolean c1=false, c2=false, c3=false, c4=false;
        if (base.getX()+1< size)c1=true;
        if (base.getX()>0)c2=true;
        if (base.getY()+1< size)c3=true;
        if (base.getY()>0)c4=true;

        if (c2 & c4)neighbours[0] = new Field(base.getX() - 1, base.getY() - 1, map[base.getX() - 1][base.getY() - 1].getValue());
        else  neighbours[0] = new Field(base.getX(), base.getY(), -2);
        if (c1 & c4)neighbours[1] = new Field(base.getX() + 1, base.getY() - 1, map[base.getX() + 1][base.getY() - 1].getValue());
        else  neighbours[1] = new Field(base.getX(), base.getY(), -2);
        if (c2 & c3)neighbours[2] = new Field(base.getX() - 1, base.getY() + 1, map[base.getX() - 1][base.getY() + 1].getValue());
        else  neighbours[2] = new Field(base.getX(), base.getY(), -2);
        if (c1 & c3)neighbours[3] = new Field(base.getX() + 1, base.getY() + 1, map[base.getX() + 1][base.getY() + 1].getValue());
        else  neighbours[3] = new Field(base.getX(), base.getY(), -2);
        return neighbours;
    }

}

