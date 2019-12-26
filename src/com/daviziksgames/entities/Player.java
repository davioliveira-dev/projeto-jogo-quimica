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
    public int contador = 1;
    public static boolean resources = true;


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
        checkCollisionQuestionMark();
            if(right && World.isFree((int)(x+speed),this.getY()) && World.doorOpen((int)(x+speed),this.getY()))
        {
            moved = true;
            dir = rightDir;
            x+=speed;
        }
        else if(left && World.isFree((int)(x-speed),this.getY())&& World.doorOpen((int)(x-speed),this.getY()))
        {
            moved = true;
            dir = leftDir;
            x-=speed;
        }
        if(up && World.isFree(this.getX(),(int)(y - speed)) && World.doorOpen(this.getX(),(int)(y - speed)))
        {
            moved = true;
            y-=speed;
        }
        else if(down && World.isFree(this.getX(),(int)(y + speed)) && World.doorOpen(this.getX(),(int)(y + speed)))
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

    public void checkCollisionQuestionMark() {
        for (int i = 0; i < Game.entities.size(); i++){
            Entity currentEntity = Game.entities.get(i);
            if(currentEntity instanceof QuestionMark)
            {
                if(isColliding(this,currentEntity))
                {
                    Game.message("Pergunta número "+Game.CUR_LEVEL+" : ",true);
                    switch (Game.CUR_LEVEL){
                        case 1:
                            Game.question(Game.CUR_LEVEL,"<html>Quantos elementos existem na tabela periódica?<ul>" +
                                    "<li>A<= 90</li>" + "<li>B<= 110</li>" + "<li>C<= 113</li>" + "<li>D<= 90</li>"+ "<li>E<= 118</li>"+
                                    "</ul></html>","E",true);
                            break;
                        case 2:
                            Game.question(Game.CUR_LEVEL,"<html>Ferro (Z = 26), manganês (Z = 25) e cromo (Z = 24) são: <ul>"+
                                    "<li>A<= Metais Alcalinos</li>" + "<li>B<= Metais Alcalinoterrosos</li>" + "<li>C<= Elementos de Transição</li>" +
                                    "<li>D<= Lantanídios</li>"+ "<li>E<= Calcogênios</li>"+
                                    "</ul></html>","c",true);
                            break;
                        case 3:
                            Game.question(Game.CUR_LEVEL,"<html>Qual é o número atômico do carbono?<ul>" +
                                    "<li>A<= 8</li>"+"<li>B<= 6</li>"+"<li>C<= 7</li>"+"<li>D<= 9</li>"+"<li>E<= 5</li>","b",true);
                            break;
                        case 4:
                            Game.question(Game.CUR_LEVEL,"<html>O ouro faz parte de qual grupo?<ul>" +
                                    "<li>A <= Metais Alcalinos </li>"+"<li>B <= Família do Boro </li>"+"<li>C <= Metais de Transição </li>"+
                                    "<li>D <= Halogênios </li>" + "<li>E <= Nenhuma das Anteriores </li>","c",true);
                            break;
                        case 5:
                            Game.question(Game.CUR_LEVEL,"<html>O propanol é um exemplo de qual função orgânica: ?<ul>" +
                                    "<li>A <= Eter </li>"+"<li>B <= Alcool </li>"+"<li>C <= Cetona </li>"+
                                    "<li>D <= Aldeído </li>" + "<li>E <= Amida </li>","b",true);
                            break;
                        case 6:
                            Game.question(Game.CUR_LEVEL,"<html>Digite a alternativa que indica corretamente o número da família" +
                                    " e do período ocupado pelo elemento cujo número atômico é igual a 42: <ul>" +
                                    "<li>A <= Família 1, 3º período. </li>"+"<li>B <= Família 14, 4º período </li>"+"<li>C <= Família 16, 1º período </li>"+
                                    "<li>D <= Família 3, 4º período. </li>" + "<li>E <= Família 6, 5º período </li>","e",true);
                            break;
                        case 7:
                            Game.question(Game.CUR_LEVEL,"<html>O aldeído valérico, mais conhecido por pentanal, apresenta que fórmula molecular?<ul>" +
                                    "<li>A <= C5 H10 O </li>"+"<li>B <= C5 H5 O2 </li>"+"<li>C <= C5 H10 O2 </li>"+
                                    "<li>D <= C5 H8 O </li>" + "<li>E <= C5 H12 O </li>","c",true);
                            break;
                        case 8:
                            Game.question(Game.CUR_LEVEL,"<html>Quando você diz: Mãe, a água do café já está fervendo você está se referindo ao fenômeno da: ?<ul>" +
                                    "<li>A <= Vaporização </li>"+"<li>B <= Ebulição </li>"+"<li>C <= Liquefação </li>"+
                                    "<li>D <= Solidificação </li>" + "<li>E <= Nenhuma das anteriores </li>","b",true);
                            break;
                        case 9:
                            //A que função a acetona pertence ?
                            Game.question(Game.CUR_LEVEL,"<html>A que função a acetona pertence ?<ul>" +
                                    "<li>A <= Aldeído </li>"+"<li>B <= Fenol </li>"+"<li>C <= Cetona </li>"+
                                    "<li>D <= Enol </li>" + "<li>E <= Fenil </li>","c",true);
                            break;
                        case 10:
                            Game.question(Game.CUR_LEVEL,"<html>Um balão contém em seu interior 2,0 L de gás He na temperatura de 25 °C. " +
                                    "Esse balão foi introduzido em um recipiente com nitrogênio líquido para reduzir a temperatura do gás para -193 °C (80 K)" +
                                    ", mantendo a pressão inalterada. Considerando o comportamento ideal do gás, o volume do balão será reduzido a aproximadamente<ul>" +
                                    "<li>A <= 0,54 L </li>"+"<li>B <= 0,81 L </li>"+"<li>C <= 1,08 L </li>"+
                                    "<li>D <= 1,35 L </li>" + "<li>E <= 1,60 L </li>","a",true);
                            break;
                    }
                    Game.player.right = false;
                    Game.player.left = false;
                    Game.player.up = false;
                    Game.player.down = false;
                    Game.entities.remove(currentEntity);
                    contador++;
                }
            }
        }
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
