package com.buz.maciej.pracainz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * klasa obsługująca mape
 * Created by Varn on 2015-10-19.
 */
public class RequestManager {

    private int size;

    private int [][] IdMap;


    public RequestManager(Context context, int version)
    {
        if (version==1) size = 20;
        if (version==2) size = 40;
        if (version==3) size = 50;
        IdMap = new int[size][size];
        loadMap(version, context);
    }

    public Request getRequest(int requestId, int exitId)
    {
        exitId=-exitId;
        Coordinates goal = new Coordinates();
        Coordinates end = new Coordinates();
        for (int i=0;i<size;i++)
        {
            for (int j=0;j<size;j++)
            {
                if (IdMap[i][j]==requestId)goal = new Coordinates(i,j);
                if (IdMap[i][j]==exitId)end = new Coordinates(i,j);
            }
        }

        return new Request(goal,end,requestId);
    }

    private void loadMap(int whichVersion, Context context) {

        if (whichVersion == 3) {
            InputStream is = context.getResources().openRawResource(R.raw.request4);
            BufferedInputStream buf = new BufferedInputStream(is);

            if (is != null) {
                int i = 0;
                int column;
                int row;
                int rId=1;
                int eId=-1;
                int tmp;
                try {
                    while ((tmp = buf.read()) != -1) {
                        column=i%size;
                        row=i/size;
                        switch (tmp)
                        {
                            case 48:
                            {
                                IdMap[column][row] = 0;
                                i++;
                                break;
                            }
                            case 49:
                            {
                                IdMap[column][row] = rId;
                                rId++;
                                i++;
                                break;
                            }

                            case 50:
                            {
                                IdMap[column][row] = eId;
                                eId--;
                                i++;
                                break;
                        }

                        }
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
