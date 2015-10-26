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

    private boolean[][] krawedzie = new boolean[10][10];

    private boolean[] test = new boolean[200];

    public Mapa (Context context)
    {
        this.context=context;
        zaladujMape();
        bloczek = new ShapeDrawable();
    }

    public boolean[][] getKrawedzie()
    {
        return krawedzie;
    }

    public void draw(Canvas canvas)
    {
        for (int i=0; i<10; i++)
        {
            for ( int j=0; j<10; j++)
            {
                if (krawedzie[i][j]==false) bloczek.getPaint().setColor(0xffffffff);
                if (krawedzie[i][j]==true) bloczek.getPaint().setColor(0xff565789);
                bloczek.setBounds(i* 40, j * 40, (i+1)*40, (j+1)*40);
                bloczek.draw(canvas);
            }
        }
    }

    private void zaladujMape()
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
        int k=0;
        for (int i=0; i<10; i++)
        {
            for (int j=0;j<10;j++)
            {
                krawedzie[j][i]=(test[k]);
                k++;
            }
        }
    }
}
