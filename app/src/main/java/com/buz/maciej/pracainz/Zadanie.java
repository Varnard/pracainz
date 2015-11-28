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

    Zadanie()
    {
        wykonane=true;
    }

    Zadanie(Wspolrzedne poczatek, Wspolrzedne koniec, Wspolrzedne cel, Mapa mapa)
    {
        this.poczatek=poczatek;
        this.cel=cel;
        this.koniec=koniec;
        trasa1 = new Droga(poczatek,cel,mapa);
        trasa2 = new Droga(cel,koniec,mapa);
        wykonane=false;
        pobieranie=true;
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
        if (!wykonane)
        {
            int wb = canvas.getHeight()/rozmiarMapy;
            int outlineWidth=1;

            ShapeDrawable bloczek = new ShapeDrawable();
            ShapeDrawable outline = new ShapeDrawable();

            Paint paint = new Paint();
            paint.setColor(0xff000000);
            paint.setTextSize(wb+2);

            if (pobieranie)
            {
                trasa1.draw(canvas);
            }

            if (odkladanie)
            {
                trasa2.draw(canvas);
            }

            outline.getPaint().setColor(0xff000000);
            
            bloczek.getPaint().setColor(0xff00ffff);
            bloczek.setBounds(cel.getX() * wb+outlineWidth, cel.getY() * wb+outlineWidth,
                    (cel.getX() + 1) * wb-outlineWidth, (cel.getY() + 1) * wb-outlineWidth);
            outline.setBounds(cel.getX() * wb, cel.getY() * wb,
                    (cel.getX() + 1) * wb, (cel.getY() + 1) * wb);
            
            outline.draw(canvas);
            bloczek.draw(canvas);
            

            canvas.drawText("1", (cel.getX() * wb) + 3, ((cel.getY() + 1) * wb-1), paint);

            bloczek.getPaint().setColor(0xff00ff00);
            bloczek.setBounds(koniec.getX() * wb+outlineWidth, koniec.getY() * wb+outlineWidth,
                    (koniec.getX() + 1) * wb-outlineWidth, (koniec.getY() + 1) * wb-outlineWidth);
            outline.setBounds(koniec.getX() * wb, koniec.getY() * wb,
                    (koniec.getX() + 1) * wb, (koniec.getY() + 1) * wb);

            outline.draw(canvas);
            bloczek.draw(canvas);

            canvas.drawText("2", (koniec.getX() * wb) + 3, ((koniec.getY() + 1) * wb-1), paint);



        }
    }

}
