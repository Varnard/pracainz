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


public class WizView extends View {

    private ShapeDrawable bloczek;

    private int[][] mapa = new int[200][200];


    public WizView(Context context)
    {
        super(context);
    }

    public WizView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public WizView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bloczek = new ShapeDrawable();

        for (int i=0; i<100; i++)
        {
            for ( int j=0; j<100; j++)
            {
                if ((i+j)%2==0) bloczek.getPaint().setColor(0xffffffff);
                if ((i+j)%2==1) bloczek.getPaint().setColor(0xff565789);
                bloczek.setBounds(i* 4, j * 4, (i+1)*4, (j+1)*4);
                bloczek.draw(canvas);
            }
        }

        invalidate();
    }
}

