package com.buz.maciej.pracainz;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class WizView extends View {

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
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        ((SystemActivity)getContext()).thread.draw(canvas);
        invalidate();
    }

}

