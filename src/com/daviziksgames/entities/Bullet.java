package com.daviziksgames.entities;

import com.daviziksgames.main.Game;
import com.daviziksgames.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Entity{

    private double dX;
    private double dY;
    private double speed = 4;
    private int life = 30, currentLife = 0;


    public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dX, double dY) {
        super(x, y, width, height, sprite);
        this.dX = dX;
        this.dY = dY;
    }

    public void tick(){
        x += dX * speed;
        y += dY * speed;
        currentLife++;
        if(currentLife == life)
        {
            Game.bullets.remove(this);
            return;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,(int)width,(int)height);
    }
}
