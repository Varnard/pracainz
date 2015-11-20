package com.buz.maciej.pracainz;

/**
 * Created by Varn on 2015-11-08.
 */
public class WatekGlowny extends Thread{

    private boolean aktywny;

    Mapa mapa;
    Zadanie zad1;

    Pole poczatek0 = new Pole(1,1,0);
    Pole koniec0 = new Pole(2,2,-1);
    Droga Trasa0;
    Pole poczatek2 = new Pole(3,2,0);
    Pole koniec2 = new Pole(15,18,-1);
    Droga Trasa2;

    WatekGlowny(Mapa mapa)
    {
        this.mapa=mapa;
        zad1= new Zadanie( new Pole(2,16),new Pole(17,2),new Pole(1,2),mapa);
        Trasa0 = new Droga(poczatek0,koniec0,mapa);
        Trasa2 = new Droga(poczatek2,koniec2,mapa);
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
            // update game state
            // render state to the screen
        }
    }
}

