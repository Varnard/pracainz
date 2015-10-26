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

    int krok;
    private ShapeDrawable bloczek;

    Pole[][] mapa;
    Pole poczatek;
    Pole koniec;
    int rozmiar;
    Boolean obliczona;

    Droga(int rozmiar, Pole poczatek, Pole koniec, boolean[][] krawedzie)
    {
        mapa = new Pole[rozmiar][rozmiar];
        this.rozmiar=rozmiar;
        this.poczatek = poczatek;
        this.koniec = koniec;


        for (int i=0; i<rozmiar;i++)
        {
            for (int j=0; j<rozmiar; j++)
            {
                if (krawedzie[i][j]==true) mapa[i][j]= new Pole(i,j,-2);
                if (krawedzie[i][j]==false) mapa[i][j]= new Pole(i,j,-1);
            }
        }
        obliczona=false;
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
                        mapa[sprawdzane.getX()][sprawdzane.getY()].setWartosc(aktualne.getWartosc()+1);
                        kolejka.add(sprawdzane);
                    }
                }


            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        trasa.push(mapa[koniec.getX()][koniec.getY()]);

        int i= mapa[koniec.getX()][koniec.getY()].getWartosc();

        while(i!=0)
        {
            for (Pole sprawdzane: zwrocSasiednie((Pole)trasa.peek()))
            {
             if (sprawdzane.getWartosc()==((Pole)trasa.peek()).getWartosc()-1)
             {
               trasa.add(sprawdzane);
                 break;
             }
            }
            i--;
        }

    obliczona=true;
    }

    public Stack zwrocTrase()
    {
        return trasa;
    }

    public void bladTrasy()
    {
        obliczona=false;
    }

    public Pole zwrocNastepnePole()
    {
     return (Pole)trasa.peek();
    }


    public void poleWykonane()
    {
     trasa.pop();
    }

    public void draw(Canvas canvas, int krok)
    {
        Paint paint = new Paint();

        bloczek = new ShapeDrawable();

        for (int i=0; i<10; i++)
        {
            for ( int j=0; j<10; j++)
            {
                for (Integer k=-2; k<100;k++)
                {
                    if (mapa[i][j].getWartosc()==k)
                    {
                        if (k<0)
                        {
                            canvas.drawText(k.toString(), (i * 40) + 5, ((j + 1) * 40) - 5, paint);
                        }
                        if (k>-1&k<10)
                        {
                            canvas.drawText(k.toString(), (i * 40) + 10, ((j + 1) * 40) - 5, paint);
                        }
                        if (k>9)
                        {
                            canvas.drawText(k.toString(), (i * 40), ((j + 1) * 40) - 5, paint);
                        }
                    }
                }
            }
        }

        bloczek.getPaint().setColor(0x600000ff);
        bloczek.setBounds(poczatek.getX() * 40, poczatek.getY() * 40,
                (poczatek.getX() + 1) * 40, (poczatek.getY() + 1) * 40);
        bloczek.draw(canvas);
        bloczek.getPaint().setColor(0x6000ff00);
        bloczek.setBounds(koniec.getX() * 40, koniec.getY() * 40,
                (koniec.getX() + 1) * 40, (koniec.getY() + 1) * 40);
        bloczek.draw(canvas);

        bloczek.getPaint().setColor(0x60ff0000);
        //Stack sciezka=Trasa.zwrocTrase();
        Iterator iterator = trasa.iterator();

        int tmpkrok = krok;
        while (iterator.hasNext())
        {
            Pole next = (Pole)iterator.next();
            if (tmpkrok>0)
            {
                bloczek.setBounds(next.getX()*40, next.getY() * 40,(next.getX()+1)*40,(next.getY()+1)*40);
                bloczek.draw(canvas);
            }
            tmpkrok--;
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

