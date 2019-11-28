package com.daviziksgames.graficos;

import com.daviziksgames.entities.Player;
import com.daviziksgames.main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(8,4,50,8);
        g.setColor(Color.green);
        g.fillRect(8,4,(int)((Game.player.life / Player.maxLife)*50),8);
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,7));
        g.drawString((int)(Game.player.life) + "/" + (int)(Player.maxLife),18,11);
    }

}
