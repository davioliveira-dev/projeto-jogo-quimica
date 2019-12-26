package com.daviziksgames.entities;

import com.daviziksgames.main.Game;
import com.daviziksgames.world.Camera;
import com.daviziksgames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{

    private double speed = 0.6;
    private int maskX = 8;
    private int maskY = 8;
    private int maskWidth = 5;
    private int maskHeight = 5;
    private int frames = 0,maxFrames = 4,index = 0,maxIndex = 1;
    private BufferedImage[] sprites;
    private int life = 20;
    private boolean isDamaged = false;
    private int damageFrames = 10;
    private int damageCurrent = 0;


    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, null);
        sprites = new BufferedImage[2];
        for(int i = 0; i < 2; i++){
            sprites[i] = Game.spritesheet.getSprite(112+(i*16),16,16,16);
        }
    }

    public void tick() {

        if (!this.isCollidingWithPlayer()) {
            if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY()) && !isColliding((int) (x + speed), this.getY()) && World.doorOpen((int) (x + speed), this.getY()) && !Game.doorLocked) {
                x += speed;
            } else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY()) && !isColliding((int) (x - speed), this.getY()) && World.doorOpen((int) (x - speed), this.getY()) && !Game.doorLocked) {
                x -= speed;
            }
            if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed)) && !isColliding(this.getX(), (int) (y + speed)) && World.doorOpen(this.getX(), (int) (y + speed)) && !Game.doorLocked) {
                y += speed;
            } else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed)) && !isColliding(this.getX(), (int) (y - speed)) && World.doorOpen(this.getX(), (int) (y - speed)) && !Game.doorLocked) {
                y -= speed;
            }
        } else {
            if (Game.rand.nextInt(100) < 10) {
                Game.player.life -= 8;
                Game.player.isDamaged = true;
            }
        }
        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;
            }
        }
        isCollidingWithBullet();
        if (life <= 0) {
            destroySelf();
            return;
        }
        if (isDamaged)
        {
            this.damageCurrent++;
            if(this.damageCurrent == this.damageFrames)
            {
                this.damageCurrent = 0;
                this.isDamaged = false;
            }
        }
    }

    public void destroySelf(){
        Game.enemies.remove(this);
        Game.entities.remove(this);
    }

    public void isCollidingWithBullet(){
        for(int i = 0; i < Game.bullets.size(); i++){
            Entity e = Game.bullets.get(i);
            if(isColliding(this,e))
            {
                isDamaged = true;

                life--;
                Game.entities.remove(e);
                return;
            }
        }
    }

    public boolean isCollidingWithPlayer(){
        Rectangle enemyCurrent = new Rectangle(this.getX() + maskX,this.getY() + maskY,maskWidth,maskHeight);
        Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
        return enemyCurrent.intersects(player);
    }


    public boolean isColliding(int xnext,int ynext){
        Rectangle enemyCurrent = new Rectangle(xnext + maskX,ynext + maskY,maskWidth,maskHeight);
        for(int i = 0; i < Game.enemies.size(); i++){
            Enemy e = Game.enemies.get(i);
            if(e == this)
            {
                continue;
            }
            Rectangle targetEnemy = new Rectangle(e.getX() + maskX,e.getY()+ maskY,maskWidth,maskHeight);
            if(enemyCurrent.intersects(targetEnemy))
            {
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g){
        if(!isDamaged)
        {
            g.drawImage(sprites[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
        }
        else
        {
            g.drawImage(ENEMYFEEDBACK_EN,this.getX() - Camera.x,this.getY() - Camera.y,null);
        }

    }

}
