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

    Mapa mapa;

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

    int krok=0;


    public WizView(Context context)
    {
        super(context);
        mapa = new Mapa(context);
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa.getKrawedzie());
        Trasa = new Droga(10,poczatek,koniec,mapa.getKrawedzie());
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa.getKrawedzie());

    }

    public WizView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mapa = new Mapa(context);
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa.getKrawedzie());
        Trasa = new Droga(10,poczatek,koniec,mapa.getKrawedzie());
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa.getKrawedzie());

    }

    public WizView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mapa = new Mapa(context);
        Trasa0 = new Droga(10,poczatek0,koniec0,mapa.getKrawedzie());
        Trasa = new Droga(10,poczatek,koniec,mapa.getKrawedzie());
        Trasa2 = new Droga(10,poczatek2,koniec2,mapa.getKrawedzie());

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(40);

        mapa.draw(canvas);

        if (Tcase==0)Trasa0.draw(canvas, krok);
        if (Tcase==1)Trasa.draw(canvas, krok);
        if (Tcase==2)Trasa2.draw(canvas, krok);
        

        invalidate();
    }

    public void wyznaczTrase1()
    {
        Trasa.obliczTrase();
    }

      void wyznaczTrase2()
    {
        Trasa2.obliczTrase();
    }



}

