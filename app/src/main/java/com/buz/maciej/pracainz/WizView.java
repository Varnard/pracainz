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
import java.util.Iterator;
import java.util.Stack;


public class WizView extends View {

    private ShapeDrawable bloczek;

    private boolean[][] mapa = new boolean[10][10];

    private boolean[] test = new boolean[200];

    Pole poczatek0 = new Pole(1,1,0);
    Pole koniec0 = new Pole(2,2,-1);
    Droga Trasa0;
    Pole poczatek = new Pole(2,2,0);
    Pole koniec = new Pole(7,7,-1);
    Droga Trasa;
    Pole poczatek2 = new Pole(5,5,0);
    Pole koniec2 = new Pole(1,5,-1);
    Droga Trasa2;
    int Tcase;

    public WizView(Context context)
    {
        super(context);
        zaladujMape();
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa);
        Trasa = new Droga(10,poczatek,koniec,mapa);
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa);

    }

    public WizView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        zaladujMape();
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa);
        Trasa = new Droga(10,poczatek,koniec,mapa);
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa);

    }

    public WizView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        zaladujMape();
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa);
        Trasa = new Droga(10,poczatek,koniec,mapa);
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa);

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
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



        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(40);
        if (Tcase==0)rysujTrase(Trasa0, canvas, paint);
        if (Tcase==1)rysujTrase(Trasa, canvas, paint);
        if (Tcase==2)rysujTrase(Trasa2, canvas, paint);
        


        invalidate();
    }

    public void wyznaczTrase1()
    {
        Trasa.obliczTrase();
    }

    public void wyznaczTrase2()
    {
        Trasa2.obliczTrase();
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
                mapa[j][i]=(test[k]);
                 k++;
             }
         }
     }

    private void rysujTrase(Droga Trasa, Canvas canvas, Paint paint)
    {
        for (int i=0; i<10; i++)
        {
            for ( int j=0; j<10; j++)
            {
                for (Integer k=-2; k<100;k++)
                {
                    if (Trasa.mapa[i][j].getWartosc()==k)
                    {
                        if (k<0)
                        {
                            canvas.drawText(k.toString(), (i * 40) + 5, ((j + 1) * 40) - 5, paint);
                        }
                        if (k>-1&k<10)
                        {
                            canvas.drawText(k.toString(), (i * 40) + 10, ((j + 1) * 40) - 5, paint);
                        }
                        if (k>9)
                        {
                            canvas.drawText(k.toString(), (i * 40), ((j + 1) * 40) - 5, paint);
                        }
                    }
                }
            }
        }

        bloczek.getPaint().setColor(0x600000ff);
        bloczek.setBounds(Trasa.poczatek.getX() * 40, Trasa.poczatek.getY() * 40,
                (Trasa.poczatek.getX() + 1) * 40, (Trasa.poczatek.getY() + 1) * 40);
        bloczek.draw(canvas);
        bloczek.getPaint().setColor(0x6000ff00);
        bloczek.setBounds(Trasa.koniec.getX() * 40, Trasa.koniec.getY() * 40,
                (Trasa.koniec.getX() + 1) * 40, (Trasa.koniec.getY() + 1) * 40);
        bloczek.draw(canvas);

        bloczek.getPaint().setColor(0x60ff0000);
        //Stack sciezka=Trasa.zwrocTrase();
        Iterator iterator = ((Stack)Trasa.zwrocTrase()).iterator();

        while (iterator.hasNext())
        {
            Pole next = (Pole)iterator.next();
            bloczek.setBounds(next.getX()*40, next.getY() * 40,(next.getX()+1)*40,(next.getY()+1)*40);
            bloczek.draw(canvas);
        }
        
    }
}

