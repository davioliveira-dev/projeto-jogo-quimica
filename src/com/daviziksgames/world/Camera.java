package com.daviziksgames.world;

public class Camera {
    public static int x,y;

    public static int clamp(int xAtual, int xMin, int xMax){
        if(xAtual < xMin)
        {
            xAtual = xMin;
        }
        if(xAtual > xMax)
        {
            xAtual = xMax;
        }
        return xAtual;

    }

}
