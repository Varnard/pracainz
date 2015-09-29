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

    public WizView(Context context) {
        super(context);
    }

    public WizView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WizView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bloczek = new ShapeDrawable();
        bloczek.getPaint().setColor(0xff565789);
        bloczek.setBounds(200, 300, 500, 400);

        bloczek.draw(canvas);
        invalidate();
    }
}

