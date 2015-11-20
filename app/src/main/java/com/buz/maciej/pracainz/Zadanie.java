package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

/**
 * Created by Varn on 2015-11-08.
 */
public class Zadanie {

    private Pole poczatek;
    private Pole koniec;
    private Pole cel;
    private Droga trasa1;
    private Droga trasa2;
    private boolean wykonane;
    private boolean rozpoczete;
    private boolean odkladanie;
    private int rozmiar;

    Zadanie(Pole poczatek, Pole koniec, Pole cel, Mapa mapa)
    {
        this.poczatek=poczatek;
        this.cel=cel;
        this.koniec=koniec;
        trasa1 = new Droga(poczatek,cel,mapa);
        trasa2 = new Droga(cel,koniec,mapa);
        wykonane=false;
        rozpoczete=false;
        odkladanie=false;
        this.rozmiar=mapa.getRozmiar();
        trasa1.obliczTrase();
        trasa2.obliczTrase();

    }

    public void setWykonane(boolean wykonane)
    {
        this.wykonane = wykonane;
    }

    public void setRozpoczete(boolean rozpoczete)
    {
        this.rozpoczete = rozpoczete;
    }

    public void setOdkladanie(boolean odkladanie)
    {
        this.odkladanie = odkladanie;
    }

    public boolean getWykonane()
    {
        return wykonane;
    }

    public boolean getRozpoczete()
    {
        return rozpoczete;
    }

    public boolean getodkladanie()
    {
        return odkladanie;
    }

    public void draw(Canvas canvas)
    {
        int wb = canvas.getHeight()/rozmiar;

        ShapeDrawable bloczek = new ShapeDrawable();

        trasa1.draw(canvas);
        trasa2.draw(canvas);
        bloczek.getPaint().setColor(0xA00000ff);
        bloczek.setBounds(poczatek.getX() * wb, poczatek.getY() * wb,
                (poczatek.getX() + 1) * wb, (poczatek.getY() + 1) * wb);
        bloczek.draw(canvas);
        bloczek.getPaint().setColor(0xA000ff00);
        bloczek.setBounds(koniec.getX() * wb, koniec.getY() * wb,
                (koniec.getX() + 1) * wb, (koniec.getY() + 1) * wb);
        bloczek.draw(canvas);
        bloczek.getPaint().setColor(0xA000ffff);
        bloczek.setBounds(cel.getX() * wb, cel.getY() * wb,
                (cel.getX() + 1) * wb, (cel.getY() + 1) * wb);
        bloczek.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(wb);
        canvas.drawText("1",(poczatek.getX() * wb)+5, ((poczatek.getY() + 1) * wb) - 5, paint);
        canvas.drawText("2", (cel.getX() * wb)+5, ((cel.getY() + 1) * wb) - 5, paint);
        canvas.drawText("3", (koniec.getX() * wb)+5, ((koniec.getY() + 1) * wb) - 5, paint);

    }

}
