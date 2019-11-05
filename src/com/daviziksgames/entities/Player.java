package com.daviziksgames.entities;

import com.daviziksgames.main.Game;
import com.daviziksgames.world.Camera;
import com.daviziksgames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public boolean right,left,up,down;
    private int rightDir = 0,leftDir = 1;
    private int dir = rightDir;
    private double speed = 1.4;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage rightPlayerDamage;
    private BufferedImage leftPlayerDamage;
    private int frames = 0,maxFrames = 4,index = 0,maxIndex = 3;
    private boolean moved = false;
    public  double life = 100;
    public static double maxLife = 100;
    public int ammo = 0;
    public boolean isDamaged = false;
    private int damageFrames = 0;
    private boolean hasGun = false;
    public boolean shoot = false;
    public boolean mouseShoot;
    public int mX,mY;


    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        rightPlayerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
        leftPlayerDamage = Game.spritesheet.getSprite(16,16,16,16);
        for(int i = 0; i < 4; i++){
            rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16),0,16,16);
        }
        for(int i = 0; i < 4; i++){
           leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16),16,16,16);
        }

    }
    public void tick(){
    	
        moved = false;
            if(right && World.isFree((int)(x+speed),this.getY()))
        {
            moved = true;
            dir = rightDir;
            x+=speed;
        }
        else if(left && World.isFree((int)(x-speed),this.getY()))
        {
            moved = true;
            dir = leftDir;
            x-=speed;
        }
        if(up && World.isFree(this.getX(),(int)(y - speed)))
        {
            moved = true;
            y-=speed;
        }
        else if(down && World.isFree(this.getX(),(int)(y + speed)))
        {
            moved = true;
            y+=speed;
        }
        if(moved){
            frames++;
            if(frames == maxFrames){
                frames = 0;
                index++;
                if(index > maxIndex){
                    index = 0;
                }
            }
        }
        checkCollisionLifePack();
        checkCollisionAmmo();
        checkCollisionGun();
        if(isDamaged)
        {
        	this.damageFrames++;
        	if(this.damageFrames == 8)
        	{
        		this.damageFrames = 0;
        		isDamaged = false;
        	}
        }
        updateCamera();

        if(shoot)
        {
            shoot = false;
            if(hasGun && ammo > 0)
            {
                ammo--;
                int dX,pX,pY;
                pY = 7;
                if(dir == rightDir)
                {
                    pX = 18;
                    dX = 1;
                }
                else
                {
                    pX = -8;
                    dX = -1;
                }
                Bullet bullet = new Bullet(this.getX() + pX,this.getY() + pY,3,3,null,dX,0);
                Game.bullets.add(bullet);
            }

        }
        if(mouseShoot)
        {
            mouseShoot = false;
            if(hasGun && ammo > 0)
            {
                ammo--;
                int pX = 0;
                int pY = 8;
                double angle = 0;
                if(dir == rightDir)
                {
                    pX = 18;
                    angle = Math.atan2(mY - (this.getY() + pY - Camera.y) , mX - (this.getX() + pX - Camera.y));

                }
                else
                {
                    pX = -8;
                    angle = Math.atan2(mY - (this.getY() + pY - Camera.y) , mX - (this.getX() + pX - Camera.y));
                }
                double dX = Math.cos(angle);
                double dY = Math.sin(angle);
                Bullet bullet = new Bullet(this.getX() + pX,this.getY() + pY,3,3,null,dX,dY);
                Game.bullets.add(bullet);
            }
        }

        if(life <= 0) 
        {
        	life = 0;
            Game.gameState = "GAME_OVER";
        }
    }
    public void updateCamera(){
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2),0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2),0,World.HEIGHT*16 - Game.HEIGHT);
    }
    public void checkCollisionGun() {
        for (int i = 0; i < Game.entities.size(); i++){
            Entity currentEntity = Game.entities.get(i);
            if(currentEntity instanceof Weapon)
            {
                if(isColliding(this,currentEntity))
                {
                    hasGun = true;
                    Game.entities.remove(currentEntity);
                }
            }
        }
    }


    public void checkCollisionAmmo() {
    	for (int i = 0; i < Game.entities.size(); i++){
            Entity currentEntity = Game.entities.get(i);
            if(currentEntity instanceof Ammo)
            {
                if(isColliding(this, currentEntity))
                {
                	ammo+=10;
                    Game.entities.remove(currentEntity);
                }
            }
        }
    }
    

    public void checkCollisionLifePack(){
        for (int i = 0; i < Game.entities.size(); i++){
            Entity currentEntity = Game.entities.get(i);
            if(currentEntity instanceof LifePack)
            {
                if(isColliding(this,currentEntity))
                {
                    life += 10;
                    if(life >= 100){
                        life = 100;
                        Game.entities.remove(currentEntity);
                    }
                }
            }
        }
    }

    public void render(Graphics g){
    	if(!isDamaged) 
    	{
    		if(dir == rightDir)
            {
                g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y - z,null);
                if(hasGun)
                {
                    g.drawImage(GUN_RIGHT,this.getX()+10 - Camera.x,this.getY()+3 - Camera.y - z,null);
                }
            }
            else if(dir == leftDir)
            {
                g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y - z,null);
                if(hasGun)
                {
                    g.drawImage(GUN_LEFT,this.getX()-12 - Camera.x,this.getY()+3 - Camera.y - z,null);
                }
            }
    	}
    	else
    	{
    	    if(dir == rightDir)
    	    {
                g.drawImage(rightPlayerDamage,this.getX() - Camera.x, this.getY() - Camera.y - z,null);
                if(hasGun)
                {
                    g.drawImage(GUN_RIGHT,this.getX()+10 - Camera.x,this.getY()+3 - Camera.y - z,null);
                }
            }
    	    else if(dir == leftDir)
    	    {
                g.drawImage(leftPlayerDamage,this.getX() - Camera.x, this.getY() - Camera.y - z,null);
                if(hasGun)
                {
                    g.drawImage(GUN_LEFT,this.getX()-12 - Camera.x,this.getY()+3 - Camera.y - z,null);
                }
            }
    	}
    }
    
}
