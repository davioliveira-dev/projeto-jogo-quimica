package com.daviziksgames.graficos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Spritesheet {

    private BufferedImage spritesheet;

    public Spritesheet(String path){
        try {
            spritesheet = ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage getSprite(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);

    }





}
