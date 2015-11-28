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

    ShapeDrawable drawingBlock;

    private Context context;

    private int size;

    private boolean[][] edges;

    private boolean[] buffer;

    public Map(Context context, int version)
    {
        this.context=context;
        if (version==1) size =20;
        if (version==2) size =40;
        edges = new boolean[size][size];
        buffer = new boolean[2*(size * size)];
        loadMap(version);
        drawingBlock = new ShapeDrawable();
    }

    public boolean[][] getEdges()
    {
        return edges;
    }

    public void draw(Canvas canvas)
    {
        int bs = canvas.getHeight()/ size;                                                                           //oblicza wielkosc pojedynczego bloczka do rysowania
        for (int i=0; i< size; i++)
        {
            for ( int j=0; j< size; j++)
            {
                if (edges[i][j]==false) drawingBlock.getPaint().setColor(0xffffffff);
                if (edges[i][j]==true) drawingBlock.getPaint().setColor(0xff565789);
                drawingBlock.setBounds(i * bs, j * bs, (i + 1) * bs, (j + 1) * bs);
                drawingBlock.draw(canvas);
            }
        }
    }

    private void loadMap(int whichVersion)
    {

       if (whichVersion==1)
       {
           InputStream is = context.getResources().openRawResource(R.raw.mapa2);
           BufferedInputStream buf = new BufferedInputStream(is);

           if (is != null) {
               int i=0;
               int tmp=0;
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
                int tmp=0;
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
