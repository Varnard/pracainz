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

    private int size;

    private boolean[][] edges;

    public Map(Context context, int version)
    {
        if (version==1) size = 20;
        if (version==2) size = 40;
        if (version==3) size = 50;
        edges = new boolean[size][size];
        loadMap(version, context);
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

    private void loadMap(int whichVersion, Context context)
    {

        InputStream is = new InputStream()
        {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        BufferedInputStream buf = new BufferedInputStream(is);


        if (whichVersion==1)
        {
           is = context.getResources().openRawResource(R.raw.mapa2);
           buf = new BufferedInputStream(is);

        }

        if (whichVersion==2)
        {
            is = context.getResources().openRawResource(R.raw.mapa3);
            buf = new BufferedInputStream(is);
        }

        if (whichVersion==3)
        {
            is = context.getResources().openRawResource(R.raw.mapa4);
            buf = new BufferedInputStream(is);
        }

        if (is != null)
        {
            int i = 0;
            int column;
            int row;
            int tmp;
            try {
                while ((tmp = buf.read()) != -1) {
                    column=i%size;
                    row=i/size;
                    switch (tmp)
                    {
                        case 48:
                        {
                            edges[column][row] = false;
                            i++;
                            break;
                        }
                        case 49:
                        {
                            edges[column][row] = true;
                            i++;
                            break;
                        }
                    }
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize()
    {
        return size;
    }
}
