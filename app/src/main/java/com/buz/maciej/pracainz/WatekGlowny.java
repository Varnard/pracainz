package com.buz.maciej.pracainz;

import android.graphics.Canvas;

/**
 * Created by Varn on 2015-11-08.
 */
public class WatekGlowny extends Thread{

    private boolean aktywny;

    Mapa mapa;
    Zadanie zad1;
    Robot rob1;
    WizView wizView;

    Pole poczatek0 = new Pole(1,1,0);
    Pole koniec0 = new Pole(2,2,-1);
    Droga Trasa0;
    Pole poczatek2 = new Pole(3,2,0);
    Pole koniec2 = new Pole(15,18,-1);
    Droga Trasa2;

    WatekGlowny(Mapa mapa)
    {
        this.mapa=mapa;
        zad1= new Zadanie( new Wspolrzedne(2,16),new Wspolrzedne(17,2),new Wspolrzedne(1,2),mapa);
        zad1.rozpocznij();
        rob1= new Robot();
        rob1.setNastepnaPozycja(new Wspolrzedne(1, 10));
        rob1.wykonajKrok();
        Trasa0 = new Droga(poczatek0,koniec0,mapa);
        Trasa2 = new Droga(poczatek2,koniec2,mapa);
    }

    public void draw(Canvas canvas)
    {
        mapa.draw(canvas);
        zad1.draw(canvas, mapa.getRozmiar());
        rob1.draw(canvas, mapa.getRozmiar());
    }

    public void setAktywny(boolean aktywny)
    {
        this.aktywny = aktywny;
    }
    @Override
    public void run()
    {
        while (aktywny)
        {
            try
            {
                rob1.wykonujZadanie(zad1);
                sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}

