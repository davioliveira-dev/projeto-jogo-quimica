package com.daviziksgames.graficos;
import java.util.*;
import com.daviziksgames.main.Game;

public class Alert{
    Timer timer;

public Alert(int seconds){
    int delay = seconds;
    timer = new Timer();
    timer.schedule(new RemindTask(),delay);
}

class RemindTask extends TimerTask{
    public void run(){
        Game.teste = false;
        timer.cancel();
    }
}

}