package com.buz.maciej.pracainz;


/**
 * Created by Varn on 2015-11-27.
 */
public class Zlecenie {

    private Wspolrzedne cel;
    private Wspolrzedne koniec;

    Zlecenie()
    {
        cel = new Wspolrzedne();
        koniec = new Wspolrzedne();
    }

    Zlecenie(Wspolrzedne cel, Wspolrzedne koniec)
    {
        this.cel = cel;
        this.koniec = koniec;
    }

    public Wspolrzedne getCel() {
        return cel;
    }

    public void setCel(Wspolrzedne cel) {
        this.cel = cel;
    }

    public Wspolrzedne getKoniec() {
        return koniec;
    }

    public void setKoniec(Wspolrzedne koniec) {
        this.koniec = koniec;
    }
}
