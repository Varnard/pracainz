package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;

/**
 * klasa symulujaca robota
 * Created by Varn on 2015-10-12.
 */
public class Robot {

    private Wspolrzedne nastepnaPozycja;
    private Wspolrzedne obecnaPozycja;
    private Zadanie aktualneZadanie;

    Robot(Wspolrzedne obecnaPozycja)
    {
        this.obecnaPozycja = obecnaPozycja;
        aktualneZadanie = new Zadanie();
    }

    public void noweZadanie(Zlecenie zlecenie,Mapa mapa)
    {
        aktualneZadanie = new Zadanie(obecnaPozycja,zlecenie.getKoniec(),zlecenie.getCel(),mapa);
    }

    public void setNastepnaPozycja(Wspolrzedne nastepnaPozycja)
    {
        this.nastepnaPozycja = nastepnaPozycja;
    }

    public boolean isReady()
    {
        if (aktualneZadanie.isWykonane()) return true;
        else return false;
    }

    public Wspolrzedne getnastepnaPozycja()
    {
        return nastepnaPozycja;
    }

    public Wspolrzedne getobecnaPozycja()
    {
        return obecnaPozycja;
    }

    public synchronized void wykonajKrok()
    {
        obecnaPozycja=nastepnaPozycja;
    }

     public void wykonujZadanie()
    {
        if (!aktualneZadanie.isWykonane())
        {
            setNastepnaPozycja(aktualneZadanie.getNastepnaPozycja());
            wykonajKrok();
            aktualneZadanie.krokWykonany();
        }
    }

    public synchronized void draw(Canvas canvas, int rozmiarMapy)
    {
        int wb = canvas.getHeight()/rozmiarMapy;

        aktualneZadanie.draw(canvas,rozmiarMapy);
        // Paint paint = new Paint();
       // paint.setColor(0xf0ff00ff);u
        ShapeDrawable bloczek = new ShapeDrawable();
        bloczek.getPaint().setColor(0xf0ffff00);
        bloczek.setBounds(obecnaPozycja.getX()*wb, obecnaPozycja.getY()*wb,
                         (obecnaPozycja.getX()+1)*wb,(obecnaPozycja.getY()+1)*wb);
        bloczek.draw(canvas);

    }

}
