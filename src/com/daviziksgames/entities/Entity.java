package com.daviziksgames.entities;

import com.daviziksgames.main.Game;
import com.daviziksgames.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    protected double x;
    protected double y;
    protected int z;
    protected double width;
    protected double height;
    private BufferedImage sprite;
    private int maskX;
    private int maskY;
    private int maskWidth;
    private int maskHeight;
    public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(96,0,16,16);
    public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(112,0,16,16);
    public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(96,16,16,16);
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112,16,16,16);
    public static BufferedImage ENEMYFEEDBACK_EN = Game.spritesheet.getSprite(144,16,16,16);
    public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128,0,16,16);
    public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(144,0,16,16);
    




    public Entity(int x, int y, int width, int height,BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.maskX = 0;
        this.maskY = 0;
        this.maskWidth = width;
        this.maskHeight = height;
    }
    public void setMask(int maskX,int maskY,int maskWidth, int maskHeight){
        this.maskX = maskX;
        this.maskY = maskY;
        this.maskWidth = maskWidth;
        this.maskHeight = maskHeight;
    }
    public void setX(int newX){
        this.x = newX;
    }
    public void setY(int newY){
        this.y = newY;
    }

    public int getX() {
        return (int)this.x;
    }

    public int getY() {
        return (int)this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void tick(){

    }
    public static boolean isColliding(Entity e1, Entity e2){
        Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX,e1.getY() + e1.maskY,e1.maskWidth,e1.maskHeight);
        Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX,e2.getY() + e2.maskY,e2.maskWidth,e2.maskHeight);
        if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
        	return true;
        }
        return false;
    }

    public void render(Graphics g){
        g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
    }


}
