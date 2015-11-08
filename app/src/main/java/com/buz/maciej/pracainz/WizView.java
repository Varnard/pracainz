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

    private WatekGlowny watek;


    int Tcase;

    int krok=0;


    public WizView(Context context)
    {
        super(context);
        init(context);
    }

    public WizView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public WizView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);

    }

    void init(Context context)
    {
        Mapa mapa = new Mapa(context,2);
        watek = new WatekGlowny(mapa);
        watek.setAktywny(true);
        watek.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        watek.mapa.draw(canvas);

        if (Tcase==0)watek.Trasa0.draw(canvas, krok);
        if (Tcase==1)watek.Trasa.draw(canvas, krok);
        if (Tcase==2)watek.Trasa2.draw(canvas, krok);
        

        invalidate();
    }

    public void wyznaczTrase1()
    {
        watek.Trasa.obliczTrase();
    }               //zle miejsce

      void wyznaczTrase2()
    {
        watek.Trasa2.obliczTrase();
    }                   //zle miejsce



}

