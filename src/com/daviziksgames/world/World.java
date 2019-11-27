package com.daviziksgames.world;

import com.daviziksgames.entities.*;
import com.daviziksgames.graficos.Spritesheet;
import com.daviziksgames.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    public static Tile[] tiles;
    public static int WIDTH,HEIGHT;
    public static final int TILE_SIZE = 16;
    public static int zplayer;
    public World(String path){
        try {
            BufferedImage map = ImageIO.read(new FileInputStream(path));
            int[] pixels = new int [map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0,0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
            for(int xx = 0; xx < map.getWidth();xx++){
                for(int yy = 0; yy < map.getHeight();yy++){

                    int pixelAtual = pixels[xx + (yy * map.getWidth())];

                    tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.TILE_FLOOR);

                    if(pixelAtual == 0xFF000000)
                    {
                        //Floor(Chao)
                        tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.TILE_FLOOR);
                    }
                    else if(pixelAtual == 0xFFFFFFFF)
                    {
                        //Parede
                        tiles[xx + (yy*WIDTH)] = new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.TILE_WALL);
                    }
                    else if(pixelAtual == 0xFF0600FF)
                    {
                        //Player
                        Game.player.setX(xx*16);
                        Game.player.setY(yy*16);
                    }
                    else if(pixelAtual == 0xFFFF0000)
                    {
                        //Enemy
                        Enemy en = new Enemy(xx*TILE_SIZE,yy*TILE_SIZE,16,16, Entity.ENEMY_EN);
                        Game.entities.add(en);
                        Game.enemies.add(en);
                    }
                    else if(pixelAtual == 0xFF36553E)
                    {
                        //Weapon
                        Game.entities.add(new Weapon(xx*TILE_SIZE,yy*TILE_SIZE,16,16, Entity.WEAPON_EN));

                    }
                    else if(pixelAtual == 0xFFA60071)
                    {
                        //Life Pack
                        Game.entities.add(new LifePack(xx*TILE_SIZE,yy*TILE_SIZE,16,16, Entity.LIFEPACK_EN));

                    }
                    else if(pixelAtual == 0xFF00CA34)
                    {
                        //Bullet
                        Game.entities.add(new Ammo(xx*TILE_SIZE,yy*TILE_SIZE,16,16, Entity.BULLET_EN));

                    }
                    /*else if(pixelAtual == 0xFF//cor//){
                        Game.entities.add(new Chest(xx*TILE_SIZE,yy*TILE_SIZE,16,16,Entity.CHEST_EN));
                    }*/
                    /*else if(pixelAtual == 0xFF//cor//){
                        Game.entities.add(new Key(xx*TILE_SIZE,yy*TILE_SIZE,16,16,Entity.KEY_EN));
                    }*/
                    

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void restartGame(String level){
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("res/spritesheet.png");
        Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32,0,16,16));
        Game.entities.add(Game.player);
        Game.world = new World("res/"+level);
    }

    public static boolean isFree(int xNext, int yNext){
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        if( !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
                tiles[x2 + (y2*World.WIDTH)] instanceof WallTile ||
                tiles[x3 + (y3*World.WIDTH)] instanceof WallTile ||
                tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
        		){
        	return true;
        }
        return false;
    }

    public void render(Graphics g){
        int xStart = Camera.x / TILE_SIZE;
        int yStart = Camera.y / TILE_SIZE;
        int xFinal = xStart + (Game.WIDTH / TILE_SIZE);
        int yFinal = yStart + (Game.HEIGHT / TILE_SIZE);
        for(int xx = xStart; xx <= xFinal; xx++){
            for(int yy = yStart; yy <= yFinal; yy++){
                if(xx < 0 || yy< 0 || xx >= WIDTH || yy >= HEIGHT)
                {
                    continue;
                }
                Tile tile = tiles[xx + (yy*WIDTH)];
                tile.render(g);
            }
        }
    }
}
