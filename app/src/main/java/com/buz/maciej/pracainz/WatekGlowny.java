package com.buz.maciej.pracainz;

import android.graphics.Canvas;

import java.util.LinkedList;

/**
 * Created by Varn on 2015-11-08.
 */
public class WatekGlowny extends Thread{

    private boolean aktywny;

    Mapa mapa;
    Robot rob1;

    private LinkedList listaZlecen = new LinkedList();

    WatekGlowny(Mapa mapa)
    {
        this.mapa=mapa;
        Zlecenie zlecenie1 = new Zlecenie(new Wspolrzedne(1,2),new Wspolrzedne(37,2));
        Zlecenie zlecenie2 = new Zlecenie(new Wspolrzedne(23,23),new Wspolrzedne(2,29));
        Zlecenie zlecenie3 = new Zlecenie(new Wspolrzedne(38,38),new Wspolrzedne(35,1));
        Zlecenie zlecenie4 = new Zlecenie(new Wspolrzedne(1,5),new Wspolrzedne(38,18));
        rob1= new Robot(new Wspolrzedne(1, 10));
        listaZlecen.add(zlecenie1);
        listaZlecen.add(zlecenie2);
        listaZlecen.add(zlecenie3);
        listaZlecen.add(zlecenie4);
    }

    public void draw(Canvas canvas)
    {
        mapa.draw(canvas);
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
                if ((!listaZlecen.isEmpty()) & rob1.isReady())
                {
                    rob1.noweZadanie((Zlecenie)listaZlecen.pop(),mapa);
                }
                else rob1.wykonujZadanie();
                sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}

