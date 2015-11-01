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
public class Mapa {

    ShapeDrawable bloczek;

    private Context context;

    private int rozmiar;

    private boolean[][] krawedzie;

    private boolean[] test;

    public Mapa (Context context, int wersja)
    {
        this.context=context;
        if (wersja==1)rozmiar=10;
        if (wersja==2)rozmiar=20;
        krawedzie = new boolean[rozmiar][rozmiar];
        test = new boolean[2*(rozmiar*rozmiar)];
        zaladujMape(wersja);
        bloczek = new ShapeDrawable();
    }

    public boolean[][] getKrawedzie()
    {
        return krawedzie;
    }

    public void draw(Canvas canvas)
    {
        int wb = canvas.getHeight()/rozmiar;                                                                           //oblicza wielkosc pojedynczego bloczka do rysowania
        for (int i=0; i<rozmiar; i++)
        {
            for ( int j=0; j<rozmiar; j++)
            {
                if (krawedzie[i][j]==false) bloczek.getPaint().setColor(0xffffffff);
                if (krawedzie[i][j]==true) bloczek.getPaint().setColor(0xff565789);
                bloczek.setBounds(i* wb, j * wb, (i+1)*wb, (j+1)*wb);
                bloczek.draw(canvas);
            }
        }
    }

    private void zaladujMape(int ktorawersja)
    {

       if (ktorawersja==1)
       {
           InputStream is = context.getResources().openRawResource(R.raw.mapa);
           BufferedInputStream buf = new BufferedInputStream(is);

           if (is != null) {
               int i=0;
               int tmp=0;
               try {
                   while ((tmp=buf.read()) != -1) {
                       if(tmp==48)
                       {
                           test[i] = false;
                           i++;
                       }
                       if(tmp==49)
                       {
                           test[i] = true;
                           i++;
                       }
                   }
                   is.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }

        if (ktorawersja==2)
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
                            test[i] = false;
                            i++;
                        }
                        if(tmp==49)
                        {
                            test[i] = true;
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
        for (int i=0; i<rozmiar; i++)
        {
            for (int j=0;j<rozmiar;j++)
            {
                krawedzie[j][i]=(test[k]);
                k++;
            }
        }
    }

    public int getRozmiar()
    {
        return rozmiar;
    }
}
