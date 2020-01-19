package gameClient;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.peer.MouseInfoPeer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import org.json.JSONException;
import org.json.JSONObject;


import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import gameClient.Logger_KML;


import utils.Point3D;
import utils.StdDraw;
import dataStructure.*;
import java.awt.event.MouseAdapter;

public class Game_Client extends  JFrame  implements ActionListener, MouseListener  {
	public static ArrayList<Fruit> Fruit=new ArrayList<Fruit>(); 
	public static ArrayList<Robot> Robots=new ArrayList<Robot>();
	
	public static game_service game;
	public static DGraph graph;
	public static Graph_Algo gg ;
	double maxX=0,maxY=0,minX=0,minY=0;
	public static final double Epsilon=0.0001;
	public int scenario;
	public long time;
	
	
	public Game_Client()
	{
		this.setSize(900,900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String level= JOptionPane.showInputDialog(this,"Please insert Level between [0,23]");
		scenario =Integer.parseInt(level);
		
		if (scenario<=23&&scenario>=0)
			 game = Game_Server.getServer(scenario); // you have [0,23] games
		else 
			game=Game_Server.getServer(0);
		String g = game.getGraph();
		gg = new Graph_Algo();
		graph=new DGraph();
		graph.init(g);
		gg.init(g);
		min_max();
		setFruits();
		setR();
		setRobots(game.getRobots().size());
		time=game.timeToEnd();
		System.out.println(game.toString());
		this.addMouseListener(this);
		this.setVisible(true);
	}
	public void setFruits()
	{
		
		
		this.Fruit=new ArrayList<Fruit>();
		Iterator<String> f_iter = game.getFruits().iterator();
		while (f_iter.hasNext())
		{
			Fruit f=new Fruit();
			f.init(f_iter.next());
			this.Fruit.add(f);
		}
		Fruit_Comperator c=new Fruit_Comperator();
		this.Fruit.sort(c);
	}
	public void setR()
	{
		JSONObject robots;
	try {
		   robots = new JSONObject(game.toString());
           robots = robots.getJSONObject("GameServer");
           int rs = robots.getInt("robots");
		
		    int src_node = 0; 
		  for(int a = 0;a<rs;a++) {
			   game.addRobot(src_node+a);
		  }
		}
	catch (JSONException e) {
		e.printStackTrace();
	}
	}
	public void setRobots(int robots_num)
	{
		this.Robots=new ArrayList<Robot>();
		try {
	            	 for (String robot : game.getRobots()) 
	            	 {
	                     Robot robot_tmp = new Robot(robot);
	                     game.addRobot(robot_tmp.getid());
	                     
	                     this.Robots.add( robot_tmp);
	            	 }
	            
		}catch (Exception e) {
			
		}
	}
	public void update()
	{
		
	     setFruits();
	     setRobots(game.getRobots().size());
	}
	public void run()
	{
		game.startGame();
		int index=0;
		long time=50;
		Thread t=new Thread();
			while(game.isRunning()) {
			
				game.move();
				
				try {
					if(index%2==0) {
						
						update();
						repaint();}
					
						t.sleep(time);
						index++;				
			} 
				
				
				catch (InterruptedException e) {e.printStackTrace();}	
			}
				
			
	
		String results = game.toString();
		System.out.println("Game Over: "+results);	
		this.setVisible(false);
		System.exit(0);
		
	}

	
	public void paint(Graphics g2d)
	{
			
			super.paint(g2d);
				Collection<node_data> node=graph.getV();
		        Iterator<node_data> nodes=node.iterator();
		
		        node=graph.getV();
		        nodes=node.iterator();
			while(nodes.hasNext()) 
			{
				node_data n=nodes.next();
				g2d.setColor(Color.BLUE);
				double x_=scale(n.getLocation().x(),minX,maxX,50,850);
				double y_=scale(n.getLocation().y(),minY,maxY,200,700); 
				Point3D p_=new Point3D(x_, y_);
				//Image img=new ImageIcon(this.getClass().getResource("/apple.png")).getImage();
				g2d.fillOval(p_.ix(), p_.iy(), 10, 10);
				g2d.setFont(new Font("deafult", Font.BOLD,14));	
				g2d.setColor(Color.BLUE);
				String key=n.getKey()+"";
				
				g2d.drawString(key, p_.ix(), p_.iy());
				Collection<edge_data> edg=graph.getE(n.getKey());	


				Iterator<edge_data> itr=edg.iterator();
				while(itr.hasNext()) {

					edge_data s=itr.next();
					double x=scale(n.getLocation().x(),minX,maxX,50,850);
					double y=scale(n.getLocation().y(),minY,maxY,200,700); 
					Point3D p=new Point3D(x, y);
					double x1=scale(graph.getNode(s.getDest()).getLocation().x(),minX,maxX,50,850);
					double y1=scale(graph.getNode(s.getDest()).getLocation().y(),minY,maxY,200,700); 
					Point3D p2=new Point3D(x1, y1);
					g2d.setColor(Color.RED);
					g2d.setFont(new Font("deafult", Font.BOLD,14));
					String weight = s.getWeight() + "";
					
					g2d.drawLine(p.ix(), p.iy(), p2.ix(), p2.iy());
					
					//g.drawString(Double.toString(s.getWeight()), (int)((p.x()+(int)p2.x())/2),	(int)((p.y()+(int)p2.y())/2));
				

					g2d.setColor(Color.YELLOW);
					int x2=(int) ((0.8*p2.ix())+ (0.2*p.ix()));
					int y2 =(int)((0.8*p2.iy())+ (0.2*p.iy()));
					g2d.fillOval(x2-5,y2-5,10,10);
					
				}
				
				}
			
				this.time=game.timeToEnd()/1000;
				g2d.setColor(Color.BLACK);
		        g2d.setFont(new Font("Arial", Font.BOLD, 20));
		        g2d.drawString("Time left: " + (this.time ), 50, 100);
		        
		        this.game.toString();
		        g2d.setColor(Color.RED);
		        g2d.setFont(new Font("game", Font.BOLD, 20));
		        g2d.drawString(game.toString(), 50, 50);		        
			paintRobot(g2d);
			paintfruit(g2d);
			

	}
	public  void paintRobot(Graphics g)
	{
		
		
		setRobots(game.getRobots().size());
		ArrayList<Robot> robots=this.Robots;
		for (int i=0 ;i<robots.size();i++)
		{
			Robot r=robots.get(i);
			int x=(int)(scale(r.getpos().x(),minX,maxX,50,850));
			int y=(int)(scale(r.getpos().y(),minY,maxY,200,700));
			g.setColor(Color.BLACK);
			g.drawOval(x-7,y-7, 30, 30);
			g.setFont(new Font("Arial", Font.BOLD, 15));
		}
		
	}
	public void paintfruit( Graphics g2d)
	{
		
		setFruits();
		for (int i=0 ;i<this.Fruit.size();i++)
			{
				Fruit fr=this.Fruit.get(i);
				if (fr.getType()==1)
				{
					g2d.setColor(Color.CYAN);
					int x=(int)(scale(fr.getPOS().x(),minX,maxX,50,850));
					int y=(int)(scale(fr.getPOS().y(),minY,maxY,200,700));
					g2d.fillOval(x-7,y-7, 20, 20);
				}
				if(fr.getType()==-1)
				{
					g2d.setColor(Color.GREEN);
					int x=(int)(scale(fr.getPOS().x(),minX,maxX,50,850));
					int y=(int)(scale(fr.getPOS().y(),minY,maxY,200,700));
					g2d.fillOval(x-7,y-7, 20, 20);
				}
			}
		 
	}
	private void min_max()
	{
		node_data n;
		Iterator<node_data> nodes=graph.getV().iterator();
		if (nodes.hasNext())
		{
			n=nodes.next();
			maxX=n.getLocation().x();
			maxY=n.getLocation().y();
			minX=n.getLocation().x();
			minY=n.getLocation().y();
		}
		while (nodes.hasNext())
		{
			n=nodes.next();
			Point3D p=n.getLocation();
			if (p.x()<minX)
				minX=p.x();
			if(p.x()>maxX)
				maxX=p.x();
			if (p.y()<minY)
				minY=p.y();
			if(p.y()>maxY)
				maxY=p.y();
		}
	}
	private static double scale(double data, double r_min, double r_max, 
			double t_min, double t_max)
	{
		
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {

		for (int i = 0; i < Robots.size(); i++) {
	            Robot robot = Robots.get(i);
	            if (robot.getdest() == -1) {
	                String dst_str = JOptionPane.showInputDialog(this, "Please insert robot " + robot.getid() + " next node destination");
	                try {
	                    int dest = Integer.parseInt(dst_str);
	                    this.game.chooseNextEdge(robot.getid(), dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(time));
						System.out.println(game.getRobots().get(i));
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(this, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}	
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		Game_Client game=new Game_Client();
		game.run();
	}
	
}
