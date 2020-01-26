package gameClient;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;


public class Start_Game extends Thread{

	private game_service game;
	private int scenario;
	int choose;
	
	public Start_Game()
	{
		JFrame jframe=new JFrame();
		//jframe.setBounds(200, 0, 500, 500);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("The Maze Of Waze");
        jframe.setVisible(true);
		
        String str=JOptionPane.showInputDialog("Auto = 1\n "+"Manual = 2");
        choose=Integer.valueOf(str);
        if(choose ==1)
        {
        	choose=1;
        	
        }
        
        if(choose==2)
        {
        	choose=2;
        	
        }
        
        jframe.setVisible(false);

        
	}
	@Override
	public void run() {
		
		 
         if (choose == 1) {
        		
        	 MyGameGUIauto auto = new MyGameGUIauto();
        	 auto.run();
         }
       
       if(choose==2)     
       {
           Game_Client game_client=new Game_Client();
           game_client.run();
       }

	}	
	public static void main(String[] args) {
		Start_Game game=new Start_Game();
		game.start();
	}
}
