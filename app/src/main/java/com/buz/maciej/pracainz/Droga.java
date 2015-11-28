package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


/**
 * klasa wyznaczajaca droge
 * Created by Varn on 2015-10-06.
 */
public class Droga {

    private Stack trasa = new Stack();

    Pole[][] mapa;
    Wspolrzedne poczatek;
    Wspolrzedne koniec;
    int rozmiar;

    Droga(Wspolrzedne poczatek, Wspolrzedne koniec, Mapa mapa)
    {
        rozmiar=mapa.getRozmiar();
        this.mapa = new Pole[rozmiar][rozmiar];
        this.poczatek = poczatek;
        this.koniec = koniec;
        for (int i=0; i<rozmiar;i++)
        {
            for (int j=0; j<rozmiar; j++)
            {
                if (mapa.getKrawedzie()[i][j]==true) this.mapa[i][j]= new Pole(i,j,-2);
                if (mapa.getKrawedzie()[i][j]==false) this.mapa[i][j]= new Pole(i,j,-1);
            }
        }
    }

    public void obliczTrase()
    {
        LinkedList kolejka = new LinkedList();
        Pole aktualne;

        kolejka.add(new Pole(poczatek.getX(),poczatek.getY(),0));
        try {
            while (kolejka.isEmpty() == false) {
               aktualne = (Pole) kolejka.remove();
                mapa[aktualne.getX()][aktualne.getY()].setWartosc(aktualne.getWartosc());

                 for (Pole sprawdzane: zwrocSasiednieBok(aktualne))
                 {
                    if (sprawdzane.getWartosc()==-1)
                    {
                        sprawdzane.setWartosc(aktualne.getWartosc()+1);
                        mapa[sprawdzane.getX()][sprawdzane.getY()].setWartosc(sprawdzane.getWartosc());
                        kolejka.add(sprawdzane);
                    }
                }

                for (Pole sprawdzane: zwrocSasiednieSkos(aktualne))
                {
                    if (sprawdzane.getWartosc()==-1)
                    {
                        sprawdzane.setWartosc(aktualne.getWartosc()+1.5);
                        mapa[sprawdzane.getX()][sprawdzane.getY()].setWartosc(sprawdzane.getWartosc());
                        kolejka.add(sprawdzane);
                    }
                }


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        trasa.push(mapa[koniec.getX()][koniec.getY()]);

        double i= mapa[koniec.getX()][koniec.getY()].getWartosc();

        while(i>0)
        {
            for (Pole sprawdzane: zwrocSasiednie((Pole)trasa.peek()))
            {
                if (sprawdzane.getWartosc()==((Pole)trasa.peek()).getWartosc()-1.5)
                {
                    trasa.add(sprawdzane);
                    break;
                }
                if (sprawdzane.getWartosc()==((Pole)trasa.peek()).getWartosc()-1)
                {
                    trasa.add(sprawdzane);
                    break;
                }
            }
            i--;
        }

    }

    public Stack zwrocTrase()
    {
        return trasa;
    }

    public synchronized Pole zwrocNastepnePole()
    {
        return (Pole)trasa.peek();
    }

    public synchronized void poleWykonane()
    {
     trasa.pop();
    }

    public synchronized void draw(Canvas canvas)
    {
        int wb = canvas.getHeight()/rozmiar;                     //oblicza wielkosc pojedynczego bloczka do rysowania
        ShapeDrawable bloczek = new ShapeDrawable();

        bloczek.setShape(new OvalShape());

        bloczek.getPaint().setColor(0xffff0000);
        Iterator iterator = trasa.iterator();

        while (iterator.hasNext())
        {
            Pole next = (Pole)iterator.next();
                bloczek.setBounds(next.getX()*wb + (wb/4), next.getY() * wb + (wb/4),
                                 (next.getX()+1)*wb - (wb/4),(next.getY()+1)*wb - (wb/4));
                bloczek.draw(canvas);
        }

    }

    private Pole[] zwrocSasiednie(Pole baza)
    {
        Pole[] sasiedzi = new Pole[8];
        Boolean w1=false, w2=false, w3=false, w4=false;
        if (baza.getX()+1<rozmiar)w1=true;
        if (baza.getX()>0)w2=true;
        if (baza.getY()+1<rozmiar)w3=true;
        if (baza.getY()>0)w4=true;

          if (w2 & w4)sasiedzi[0] = new Pole(baza.getX() - 1, baza.getY() - 1, mapa[baza.getX() - 1][baza.getY() - 1].getWartosc());
        else  sasiedzi[0] = new Pole(baza.getX(), baza.getY(), -2);
        if (w4)sasiedzi[1] = new Pole(baza.getX(), baza.getY() - 1, mapa[baza.getX()][baza.getY() - 1].getWartosc());
        else  sasiedzi[1] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1 & w4)sasiedzi[2] = new Pole(baza.getX() + 1, baza.getY() - 1, mapa[baza.getX() + 1][baza.getY() - 1].getWartosc());
        else  sasiedzi[2] = new Pole(baza.getX(), baza.getY(), -2);
        if (w2)sasiedzi[3] = new Pole(baza.getX() - 1, baza.getY(), mapa[baza.getX() - 1][baza.getY()].getWartosc());
        else  sasiedzi[3] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1)sasiedzi[4] = new Pole(baza.getX() + 1, baza.getY(), mapa[baza.getX() + 1][baza.getY()].getWartosc());
        else  sasiedzi[4] = new Pole(baza.getX(), baza.getY(), -2);
        if (w2 & w3)sasiedzi[5] = new Pole(baza.getX() - 1, baza.getY() + 1, mapa[baza.getX() - 1][baza.getY() + 1].getWartosc());
        else  sasiedzi[5] = new Pole(baza.getX(), baza.getY(), -2);
        if (w3)sasiedzi[6] = new Pole(baza.getX(), baza.getY() + 1, mapa[baza.getX()][baza.getY() + 1].getWartosc());
        else  sasiedzi[6] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1 & w3)sasiedzi[7] = new Pole(baza.getX() + 1, baza.getY() + 1, mapa[baza.getX() + 1][baza.getY() + 1].getWartosc());
        else  sasiedzi[7] = new Pole(baza.getX(), baza.getY(), -2);
        return sasiedzi;
    }

    private Pole[] zwrocSasiednieBok(Pole baza)
    {
        Pole[] sasiedzi = new Pole[4];
        Boolean w1=false, w2=false, w3=false, w4=false;
        if (baza.getX()+1<rozmiar)w1=true;
        if (baza.getX()>0)w2=true;
        if (baza.getY()+1<rozmiar)w3=true;
        if (baza.getY()>0)w4=true;

        if (w4)sasiedzi[0] = new Pole(baza.getX(), baza.getY() - 1, mapa[baza.getX()][baza.getY() - 1].getWartosc());
        else  sasiedzi[0] = new Pole(baza.getX(), baza.getY(), -2);
        if (w2)sasiedzi[1] = new Pole(baza.getX() - 1, baza.getY(), mapa[baza.getX() - 1][baza.getY()].getWartosc());
        else  sasiedzi[1] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1)sasiedzi[2] = new Pole(baza.getX() + 1, baza.getY(), mapa[baza.getX() + 1][baza.getY()].getWartosc());
        else  sasiedzi[2] = new Pole(baza.getX(), baza.getY(), -2);
        if (w3)sasiedzi[3] = new Pole(baza.getX(), baza.getY() + 1, mapa[baza.getX()][baza.getY() + 1].getWartosc());
        else  sasiedzi[3] = new Pole(baza.getX(), baza.getY(), -2);
        return sasiedzi;
    }

    private Pole[] zwrocSasiednieSkos(Pole baza)
    {
        Pole[] sasiedzi = new Pole[4];
        Boolean w1=false, w2=false, w3=false, w4=false;
        if (baza.getX()+1<rozmiar)w1=true;
        if (baza.getX()>0)w2=true;
        if (baza.getY()+1<rozmiar)w3=true;
        if (baza.getY()>0)w4=true;

        if (w2 & w4)sasiedzi[0] = new Pole(baza.getX() - 1, baza.getY() - 1, mapa[baza.getX() - 1][baza.getY() - 1].getWartosc());
        else  sasiedzi[0] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1 & w4)sasiedzi[1] = new Pole(baza.getX() + 1, baza.getY() - 1, mapa[baza.getX() + 1][baza.getY() - 1].getWartosc());
        else  sasiedzi[1] = new Pole(baza.getX(), baza.getY(), -2);
        if (w2 & w3)sasiedzi[2] = new Pole(baza.getX() - 1, baza.getY() + 1, mapa[baza.getX() - 1][baza.getY() + 1].getWartosc());
        else  sasiedzi[2] = new Pole(baza.getX(), baza.getY(), -2);
        if (w1 & w3)sasiedzi[3] = new Pole(baza.getX() + 1, baza.getY() + 1, mapa[baza.getX() + 1][baza.getY() + 1].getWartosc());
        else  sasiedzi[3] = new Pole(baza.getX(), baza.getY(), -2);
        return sasiedzi;
    }

}

