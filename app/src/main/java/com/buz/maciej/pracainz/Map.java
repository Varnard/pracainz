package com.buz.maciej.pracainz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * klasa obsługująca mape
 * Created by Varn on 2015-10-19.
 */
public class Map {

    private Context context;                                                                        //todo poprawic patrz requestmanager

    private int size;

    private boolean[][] edges;

    private boolean[] buffer;


    public Map(Context context, int version)
    {
        this.context=context;
        if (version==1) size = 20;
        if (version==2) size = 40;
        if (version==3) size = 50;
        edges = new boolean[size][size];
        buffer = new boolean[2*(size * size)];
        loadMap(version);
    }

    public boolean[][] getEdges()
    {
        return edges;
    }

    public void draw(Canvas canvas)
    {
        ShapeDrawable drawingBlock = new ShapeDrawable();

        double bs = (double)canvas.getHeight()/size;                                                             //oblicza wielkosc pojedynczego bloczka do rysowania
        for (int i=0; i< size; i++)
        {
            for ( int j=0; j< size; j++)
            {
                if (edges[i][j]==false) drawingBlock.getPaint().setColor(0xffffffff);
                if (edges[i][j]==true) drawingBlock.getPaint().setColor(0xff565789);
                drawingBlock.setBounds((int)(i * bs),(int)(j * bs),(int)((i + 1) * bs),(int)((j + 1) * bs));
                drawingBlock.draw(canvas);
            }
        }

        drawingBlock.getPaint().setColor(0xff000000);                                                  //rysowanie ramki dookola mapy
        drawingBlock.setBounds(0, 0, canvas.getWidth()-1, (int)(bs/4));
        drawingBlock.draw(canvas);
        drawingBlock.setBounds(0, 0,(int) (bs/4), canvas.getHeight()-1);
        drawingBlock.draw(canvas);
        drawingBlock.setBounds(0,canvas.getHeight()-(int)(bs/4)-1,canvas.getWidth()-1,canvas.getHeight()-1);
        drawingBlock.draw(canvas);
        drawingBlock.setBounds(canvas.getWidth()-(int)(bs/4)-1,0,canvas.getWidth()-1,canvas.getHeight()-1);
        drawingBlock.draw(canvas);


    }

    private void loadMap(int whichVersion)
    {

       if (whichVersion==1)
       {
           InputStream is = context.getResources().openRawResource(R.raw.mapa2);
           BufferedInputStream buf = new BufferedInputStream(is);

           if (is != null) {
               int i=0;
               int tmp;
               try {
                   while ((tmp=buf.read()) != -1) {
                       if(tmp==48)
                       {
                           buffer[i] = false;
                           i++;
                       }
                       if(tmp==49)
                       {
                           buffer[i] = true;
                           i++;
                       }
                   }
                   is.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

        if (whichVersion==2)
        {
            InputStream is = context.getResources().openRawResource(R.raw.mapa3);
            BufferedInputStream buf = new BufferedInputStream(is);

            if (is != null) {
                int i=0;
                int tmp;
                try {
                    while ((tmp=buf.read()) != -1) {
                        if(tmp==48)
                        {
                            buffer[i] = false;
                            i++;
                        }
                        if(tmp==49)
                        {
                            buffer[i] = true;
                            i++;
                        }
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (whichVersion==3) {
            InputStream is = context.getResources().openRawResource(R.raw.mapa4);
            BufferedInputStream buf = new BufferedInputStream(is);

            if (is != null) {
                int i = 0;
                int tmp;
                try {
                    while ((tmp = buf.read()) != -1) {
                        if (tmp == 48) {
                            buffer[i] = false;
                            i++;
                        }
                        if (tmp == 49) {
                            buffer[i] = true;
                            i++;
                        }
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





        int k=0;
        for (int i=0; i< size; i++)
        {
            for (int j=0;j< size;j++)
            {
                edges[j][i]=(buffer[k]);
                k++;
            }
        }
    }

    public int getSize()
    {
        return size;
    }
}
