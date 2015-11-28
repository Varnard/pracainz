package com.buz.maciej.pracainz;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public class WizView extends View {

    private SystemThread thread;

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
        Map map = new Map(context,2);
        thread = new SystemThread(map);
        thread.setActive(true);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        thread.draw(canvas);
        invalidate();
    }

}

