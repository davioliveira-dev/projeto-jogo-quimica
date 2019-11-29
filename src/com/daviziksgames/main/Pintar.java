package com.daviziksgames.main;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Pintar extends JFrame {

    private static final long serialVersionUID = 1L;

    JLabel mensagem = new JLabel("Parabéns, você pegou uma bala!");


    public Pintar(){
        mensagem.setFont(new Font("Arial",Font.BOLD,22));
    }
    
}