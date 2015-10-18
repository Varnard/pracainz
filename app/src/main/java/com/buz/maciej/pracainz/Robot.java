package com.buz.maciej.pracainz;

/**
 * klasa symulujaca robota
 * Created by Varn on 2015-10-12.
 */
public class Robot {

    private Pole nastPoz;
    private Pole obecnaPoz;

    public void setNastPoz(Pole nastPoz)
    {
        this.nastPoz = nastPoz;
    }

    public Pole getNastPoz()
    {
        return nastPoz;
    }

    public Pole getObecnaPoz()
    {
        return obecnaPoz;
    }

    public void wykonajKrok()
    {
        obecnaPoz=nastPoz;
    }

    public void czekaj()
    {

    }

    public void zaladuj()
    {

    }

    public void rozladuj ()
    {

    }
}
