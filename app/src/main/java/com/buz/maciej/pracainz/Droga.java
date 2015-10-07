package com.buz.maciej.pracainz;

import java.util.Queue;

/**
 * klasa wyznaczajaca droge
 * Created by Varn on 2015-10-06.
 */
public class Droga {

    Queue kolejka;

    int[][] mapa;
    final  int[] poczatek = new int[2];
    final  int[] koniec = new int[2];
    boolean obliczona;

    Droga(int rozmiar, int[] npoczatek, int[] nkoniec, boolean[][] krawedzie)
    {
        mapa = new int[rozmiar][rozmiar];
        poczatek[0] = npoczatek[0];
        poczatek[1] = npoczatek[1];
        koniec[0]=nkoniec[0];
        koniec[1]=nkoniec[1];
        obliczona=false;

        for (int i=0; i<rozmiar;i++)
        {
            for (int j=0; j<rozmiar; j++)
            {
                if (krawedzie[i][j]==true) mapa[i][j]=-2;
                if (krawedzie[i][j]==false) mapa[i][j]=-1;
            }
        }
    }

}
