package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

/**
 * Created by Varn on 2015-11-08.
 */
public class Zadanie {

    private Wspolrzedne poczatek;
    private Wspolrzedne koniec;
    private Wspolrzedne cel;
    private Droga trasa1;
    private Droga trasa2;
    private boolean wykonane;
    private boolean pobieranie;
    private boolean odkladanie;

    Zadanie(Wspolrzedne poczatek, Wspolrzedne koniec, Wspolrzedne cel, Mapa mapa)
    {
        this.poczatek=poczatek;
        this.cel=cel;
        this.koniec=koniec;
        trasa1 = new Droga(poczatek,cel,mapa);
        trasa2 = new Droga(cel,koniec,mapa);
        wykonane=false;
        pobieranie=false;
        odkladanie=false;
        trasa1.obliczTrase();
        trasa2.obliczTrase();

    }

    public void rozpocznij()
    {
        this.pobieranie = true;
    }

    public boolean isWykonane()
    {
        return wykonane;
    }

    public Wspolrzedne getNastepnaPozycja()
    {
        Wspolrzedne nastepnePole = new Wspolrzedne();

        if(odkladanie)
        {
            nastepnePole=trasa2.zwrocNastepnePole();
            if (trasa2.koniec.jestRowne(nastepnePole))
            {
                wykonane=true;
                odkladanie=false;
            }
        }

        if(pobieranie)
        {
            nastepnePole=trasa1.zwrocNastepnePole();
            if (trasa1.koniec.jestRowne(nastepnePole))
            {
                odkladanie=true;
                pobieranie=false;
                trasa1.poleWykonane();                          //usuniecie zdublowanego pola
            }
        }
        return nastepnePole;
    }

    public void krokWykonany()
    {
        if(odkladanie)
        {
            trasa2.poleWykonane();
        }

        if(pobieranie)
        {
            trasa1.poleWykonane();
        }
    }

    public void draw(Canvas canvas,int rozmiarMapy)
    {
        int wb = canvas.getHeight()/rozmiarMapy;

        ShapeDrawable bloczek = new ShapeDrawable();

        trasa1.draw(canvas);
        trasa2.draw(canvas);

        if (!wykonane)
        {
            if (pobieranie)
            {
                bloczek.getPaint().setColor(0xA00000ff);
                bloczek.setBounds(poczatek.getX() * wb, poczatek.getY() * wb,
                        (poczatek.getX() + 1) * wb, (poczatek.getY() + 1) * wb);
                bloczek.draw(canvas);
            }
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
            if (pobieranie)
            {
                canvas.drawText("1",(poczatek.getX() * wb) + 5, ((poczatek.getY() + 1) * wb) - 5, paint);
            }
            canvas.drawText("2", (cel.getX() * wb) + 5, ((cel.getY() + 1) * wb) - 5, paint);
            canvas.drawText("3", (koniec.getX() * wb)+5, ((koniec.getY() + 1) * wb) - 5, paint);

        }
    }

}
