package com.daviziksgames.main;

import java.awt.*;

public class Menu {
    public String[] options = {"New Game", "Load Game" , "Exit"};
    public int currentOption = 0;
    public int maxOption = options.length - 1;
    public boolean up,down,enter;
    public boolean pause = false;


    public void tick(){
    	Game.gameState = "MENU";
        if(up)
        {
            up = false;
            currentOption--;
            if(currentOption < 0)
            {
                currentOption = maxOption;
            }
        }
        if(down)
        {
            down = false;
            currentOption++;
            if(currentOption > maxOption)
            {
                currentOption = 0;
            }
        }
        if(enter)
        {
            enter = false;
            if(options[currentOption] == "New Game" || options[currentOption] == "Continue")
            {
                Game.gameState = "NORMAL";
                pause = false;
            }
            else if(options[currentOption] == "Exit")
            {
            	System.exit(1);
            }
        }
    }
    public void render(Graphics g){
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(new Color(0,0,0,100));
        g.fillRect(0,0, Game.WIDTH * Game.SCALE, Game.HEIGHT*Game.SCALE);
        g.setColor(Color.yellow);
        g.setFont(new Font("arial",Font.BOLD,36));
        g.drawString(">GAME ZERO1<",(Game.WIDTH*Game.SCALE) / 2 - 160,(Game.HEIGHT*Game.SCALE) / 2 - 120);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,24));
        if(!pause)
        {
            g.drawString("New Game",(Game.WIDTH*Game.SCALE) / 2 - 70,200);
            g.drawString("Load Game",(Game.WIDTH*Game.SCALE) / 2 - 75,250);
            g.drawString("Exit",(Game.WIDTH*Game.SCALE) / 2 - 35,300);
        }
        else
        {
            g.drawString("Continue",(Game.WIDTH*Game.SCALE) / 2 - 60,200);
            g.drawString("Load Game",(Game.WIDTH*Game.SCALE) / 2 - 75,250);
            g.drawString("Exit",(Game.WIDTH*Game.SCALE) / 2 - 34,300);
        }
        
        if(options[currentOption] == "New Game")
        {
            g.drawString(">",(Game.WIDTH*Game.SCALE) / 2 - 100,197);
        }
        else if(options[currentOption] == "Load Game")
        {
            g.drawString(">",(Game.WIDTH*Game.SCALE) / 2 - 110,247);
        }
        else if(options[currentOption] == "Exit")
        {
            g.drawString(">",(Game.WIDTH*Game.SCALE) / 2 - 60,297);
        }
        else if(options[currentOption] == "Continue")
        {
            g.drawString(">",(Game.WIDTH*Game.SCALE) / 2 - 90,297);
        }
    }
}
