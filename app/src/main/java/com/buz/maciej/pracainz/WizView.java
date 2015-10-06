package com.buz.maciej.pracainz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class WizView extends View {

    private ShapeDrawable bloczek;

    private boolean[][] mapa = new boolean[10][10];

    private boolean[] test = new boolean[200];


    public WizView(Context context)
    {
        super(context);
        zaladujMape();
    }

    public WizView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        zaladujMape();
    }

    public WizView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        zaladujMape();
    }


    @Override
    protected void onDraw(Canvas canvas)  {
        super.onDraw(canvas);

        bloczek = new ShapeDrawable();

        for (int i=0; i<10; i++)
        {
            for ( int j=0; j<10; j++)
            {
                if (mapa[i][j]==false) bloczek.getPaint().setColor(0xffffffff);
                if (mapa[i][j]==true) bloczek.getPaint().setColor(0xff565789);
                bloczek.setBounds(i* 40, j * 40, (i+1)*40, (j+1)*40);
                bloczek.draw(canvas);
            }
        }

        invalidate();
    }

     private void zaladujMape()
     {
         InputStream is = this.getResources().openRawResource(R.raw.mapa);

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
                mapa[i][j]=(test[k]);
                 k++;
             }
         }
     }
}

