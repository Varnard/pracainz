package com.buz.maciej.pracainz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


/**
 * klasa wyznaczajaca droge
 * Created by Varn on 2015-10-06.
 */
public class Droga {

    private LinkedList kolejka = new LinkedList();
    private Stack trasa = new Stack();
    private ShapeDrawable bloczek;

    Pole[][] mapa;
    Pole poczatek;
    Pole koniec;
    int rozmiar;

    Droga(Pole poczatek, Pole koniec, Mapa map)
    {
        rozmiar=map.getRozmiar();
        mapa = new Pole[rozmiar][rozmiar];
        this.poczatek = poczatek;
        this.poczatek.setWartosc(0);
        this.koniec = koniec;
        this.koniec.setWartosc(-1);


        for (int i=0; i<rozmiar;i++)
        {
            for (int j=0; j<rozmiar; j++)
            {
                if (map.getKrawedzie()[i][j]==true) mapa[i][j]= new Pole(i,j,-2);
                if (map.getKrawedzie()[i][j]==false) mapa[i][j]= new Pole(i,j,-1);
            }
        }
    }

    public void obliczTrase()
    {
        Pole aktualne;

        kolejka.add(poczatek);
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


    public Pole zwrocNastepnePole()
    {
     return (Pole)trasa.peek();
    }


    public void poleWykonane()
    {
     trasa.pop();
    }

    public void draw(Canvas canvas)
    {
        int wb = canvas.getHeight()/rozmiar;                                                                           //oblicza wielkosc pojedynczego bloczka do rysowania
        bloczek = new ShapeDrawable();
        /*
        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(wb/2);


        for (int i=0; i<rozmiar; i++)
        {
            for ( int j=0; j<rozmiar; j++)
            {
                    Double k = mapa[i][j].getWartosc();
                   canvas.drawText(k.toString(), (i * wb), ((j + 1) * wb) - 5, paint);

            }
        }*/

        bloczek.getPaint().setColor(0xA0ff0000);
        Iterator iterator = trasa.iterator();

        while (iterator.hasNext())
        {
            Pole next = (Pole)iterator.next();
                bloczek.setBounds(next.getX()*wb, next.getY() * wb,(next.getX()+1)*wb,(next.getY()+1)*wb);   //<--
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

