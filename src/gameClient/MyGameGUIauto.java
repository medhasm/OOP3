package gameClient;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.Fruit;
import dataStructure.Fruit_Comperator;
import dataStructure.Robot;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;

public class MyGameGUIauto  extends JFrame implements MouseListener,ActionListener{
public static ArrayList<Fruit> Fruit=new ArrayList<Fruit>(); //seder
public static ArrayList<Robot> Robots=new ArrayList<Robot>();
public static List<Fruit> fruit12=new LinkedList<Fruit>(Fruit);
public static game_service game;
public static DGraph graph;
public static Graph_Algo gg ;
double maxX=0,maxY=0,minX=0,minY=0;
public static long time;
public static final double Epsilon=0.00001;
private Image img,robot,apple,banana;
private int hight=900;
private int width=900;
private static  Logger_KML kml;
private int scenario;
private int id;
public static int  t;
private Image mBuffer_image;
private Graphics mBuffer_graphics;
	
	public MyGameGUIauto()  {
		this.setTitle("The Maza Of Waze");
		this.setSize(width, hight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		img = Toolkit.getDefaultToolkit().createImage("11.jpg");
		robot = Toolkit.getDefaultToolkit().createImage("Robot.png");
		apple = Toolkit.getDefaultToolkit().createImage("apple.png");
		banana = Toolkit.getDefaultToolkit().createImage("banana.png");
		//String ID= JOptionPane.showInputDialog("Please insert your ID");
		//id =Integer.parseInt(ID);
		id=206953127;
		String level= JOptionPane.showInputDialog(this,"Please insert Level between [0,23]");
		scenario =Integer.parseInt(level);
		
		/**if (scenario<=23&&scenario>=0)
		{
			Game_Server.login(id);
			 game = Game_Server.getServer(scenario); // you have [0,23] games
		}else {
			Game_Server.login(id);
			game=Game_Server.getServer(0);
		}*/
		Game_Server.login(id);
		 game = Game_Server.getServer(scenario); 
		String g = game.getGraph();
		gg = new Graph_Algo();
		graph=new DGraph();
		graph.init(g);
		gg.init(g);
		min_max();
		t=150;
		kml=new Logger_KML(scenario);
		JSONObject line;
		String info = game.toString();

		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			System.out.println(info);
			System.out.println(g);						
		    	
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext()) 
			{
				Fruit f=new Fruit();
				String s=f_iter.next();
				f.init(s);
				this.Fruit.add(f);
				f.set_boolean(false);
			}

			int src_node = 0; 
			int c= 0;
			Robot b ;
			int af=0;
			for(int a = 0;a<rs ;a++) {
				if(Fruit.get(c)!=null  && c<Fruit.size()){
					
					EdgeData edge=GetFE(graph,Fruit.get(c));
							src_node= edge.getSrc();
							b=new Robot(src_node,a,0,Fruit.get(c));
							Robots.add(b);
							game.addRobot(src_node);	
				}
				else 
				{
					af=(int)Math.random()*(graph.getV().size()-1);
					b=new Robot(af,a,0,null);
					Robots.add(b);
					game.addRobot(af);
				}
					c++;
				}
			}
		
		catch (JSONException e) {e.printStackTrace();}
		
		this.addMouseListener(this);
		this.setVisible(true);
	}
		public void paintComponents(Graphics g ) {
				super.paint(g);
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setBackground(new Color(240, 240, 240));
		        g2d.drawImage(img,0,0,this);

		        
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
						g2d.fillOval(p_.ix(), p_.iy(), 10, 10);
						g2d.setFont(new Font("deafult", Font.BOLD,20));	
						g2d.setColor(Color.BLUE);
						String key=n.getKey()+"";
						
						g2d.drawString(key, p_.ix(), p_.iy());
						Collection<edge_data> edg=graph.getE(n.getKey());	


						Iterator<edge_data> itr=edg.iterator();
						while(itr.hasNext())
						{

							edge_data s=itr.next();
							double x=scale(n.getLocation().x(),minX,maxX,50,850);
							double y=scale(n.getLocation().y(),minY,maxY,200,700); 
							Point3D p=new Point3D(x, y);
							double x1=scale(graph.getNode(s.getDest()).getLocation().x(),minX,maxX,50,850);
							double y1=scale(graph.getNode(s.getDest()).getLocation().y(),minY,maxY,200,700); 
							Point3D p2=new Point3D(x1, y1);
							g2d.setColor(Color.RED);
							g2d.drawLine(p.ix(), p.iy(), p2.ix(), p2.iy());
							g2d.setFont(new Font("deafult", Font.BOLD,20));
							//String weight = s.getWeight() + "";
                            
							
                            g2d.setColor(Color.YELLOW);
							int x2=(int) ((0.8*p2.ix())+ (0.2*p.ix()));
							int y2 =(int)((0.8*p2.iy())+ (0.2*p.iy()));
							g2d.fillOval(x2-5,y2-5,10,10);
						}
					}
					
					time=game.timeToEnd()/1000;
					g2d.setColor(Color.BLACK);
					g2d.setFont(new Font("Arial", Font.BOLD, 20));
					g2d.drawString("Time left: " + (this.time ), 50, 150);
					
					g2d.setColor(Color.RED);
					g2d.setFont(new Font("Arial", Font.BOLD, 20));
					g2d.drawString(game.toString(), 20, 50);
					
					paintfruit(g2d);
					paintRobot(g2d);
			      
		}
		public void paint(Graphics g)
		{
			mBuffer_image = createImage(900,900 );
	        mBuffer_graphics = mBuffer_image.getGraphics();

	        // Draw on the new "canvas"
	        paintComponents(mBuffer_graphics);

	        // "Switch" the old "canvas" for the new one
	        g.drawImage(mBuffer_image, 0, 0, this);
		}
		private void paintfruit(Graphics2D g2d)
		{
			for (int i=0 ;i<this.Fruit.size();i++)
			{
		
				Fruit fr=this.Fruit.get(i);
				if (fr.getType()==1)
				{
					g2d.setColor(Color.CYAN);
					int x=(int)(scale(fr.getPOS().x(),minX,maxX,50,850));
					int y=(int)(scale(fr.getPOS().y(),minY,maxY,200,700));
					//g2d.fillOval(x-7,y-7, 20, 20);
					g2d.drawImage(apple,x-15,y-15,this);
				}
				if(fr.getType()==-1)
				{
					g2d.setColor(Color.GREEN);
					int x=(int)(scale(fr.getPOS().x(),minX,maxX,50,850));
					int y=(int)(scale(fr.getPOS().y(),minY,maxY,200,700));
					//g2d.fillOval(x-7,y-7, 20, 20);
					g2d.drawImage(banana,x-15,y-15,this);
				}
			}
		}
		
		private void paintRobot(Graphics2D g2d)
		{
			ArrayList<Robot> robots=this.Robots;
			List<String> rob = game.getRobots();
	        for (int i = 1; i <= rob.size(); i++) {
	        g2d.drawString(rob.get(i - 1), 20, 90);
	     }
			
	     for (int i=0 ;i<robots.size();i++)
			{
				Robot r=robots.get(i);
       			int x=(int)(scale(r.getpos().x(),minX,maxX,50,850));
				int y=(int)(scale(r.getpos().y(),minY,maxY,200,700));
		        g2d.drawImage(robot,x-15,y-15,this);
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
		
		public void run()
		{
			game.startGame();
			int ind=0;
			long time=150;
			int jj = 0;
			while(game.isRunning()) {
			try {	
				
					
					this.repaint();
					moveRobots(game, gg,graph);
					Thread.sleep(t);
					ind++;
					
					jj++;				
			} 
				catch (InterruptedException e) {e.printStackTrace();}	
			}
			kml.endKml(scenario);
			String remark=kml.getString();
			game.sendKML(remark);
			String results = game.toString();
			System.out.println("Game Over: "+results);
			this.setVisible(false);
			this.setDefaultCloseOperation(0);
		}
		
		
	
	private double scale(double data, double r_min, double r_max, 
			double t_min, double t_max)
	{
		
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen .
	 * @param game
	 * @param gg
	 * @param log
	 */
	
	public static void moveRobots(game_service game,  Graph_Algo gg ,DGraph graph) {
		List<String> log = game.move();
		if(log!=null)
		{
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++)
			{
				String robot_json = log.get(i);
				Robot demo=new Robot(robot_json);
				for (Robot r : Robots) 
				{
					if(r.getid()==demo.getid()) 
					{
						LinkedList<node_data> p=Robots.get(r.getid()).getpath();
						Robots.get(r.getid()).init(robot_json);
						Robots.get(r.getid()).setpath(p);
					}
				}
				
			}
			setfruit();
			fruit12=new LinkedList<Fruit>(Fruit);
			int dest=0;
			for(Robot r:Robots) 
			{
				if(r.getdest()==-1)
				{
					dest=nextNode(graph,gg,r);
					game.chooseNextEdge(r.getid(), dest);
					kml.placemark(r.getpos(), r.getid());
					System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
				}
			}
		}
		}
		
	public static  void setfruit()
	{
		int i=0;
		Fruit=new ArrayList<Fruit>();
		Iterator<String> f_iter = game.getFruits().iterator();
		while (f_iter.hasNext())
		{
			Fruit f=new Fruit();
			f.init(f_iter.next());
			Fruit.add(f);
			f.setedge(GetFE(graph, f));
			f.set_boolean(Fruit.get(i).get_boolean());
			i++;
		}
		
		Fruit_Comperator c=new Fruit_Comperator();
		Fruit.sort(c);
	}
	public void setRobots()
	{
		this.Robots=new ArrayList<Robot>();
		try {
	           for (String robot : game.getRobots()) 
	           	 {
	                  Robot robot_tmp = new Robot(robot);
	                  game.addRobot(robot_tmp.getid());                 
	                  this.Robots.add( robot_tmp);
	             }
		}catch (Exception e) {}
	}
	public static EdgeData GetFE(DGraph graph,Fruit f) {
		if (f==null) return null;
		Iterator<node_data> itr=graph.getV().iterator();
		
		while(itr.hasNext()) {
			node_data node=itr.next();
			Iterator<edge_data> edg=graph.getE(node.getKey()).iterator();
			while(edg.hasNext() ) {
				edge_data edge=edg.next();
				Point3D s=graph.getNode(edge.getSrc()).getLocation();
				Point3D d=graph.getNode(edge.getDest()).getLocation();
				Point3D ff=f.getPOS();
				double dist1=s.distance2D(d);
				double a=s.distance2D(ff);
				double b=d.distance2D(ff);
				double dist2=a+b;
				double gap=Math.abs(dist2-dist1);
				if (gap<=Epsilon)
				{
					if(f.getType()== -1)
					{
						if(edge.getSrc()>edge.getDest()) 
						{ 
							f.setedge(edge);
							return (EdgeData) edge;
						}
						
						else
						{
							f.setedge(graph.getEdge(edge.getDest(), edge.getSrc()));
							return (EdgeData) graph.getEdge(edge.getDest(), edge.getSrc());
						}
					}
					else 
					{
						if(edge.getSrc()<edge.getDest()) 
						{	
							f.setedge(edge);
							return (EdgeData) edge;
						}
						else {
							f.setedge(graph.getEdge(edge.getSrc(), edge.getDest()));
							return (EdgeData) graph.getEdge(edge.getSrc(), edge.getDest());
						}
						}
				}
			}
		}
		
		return null;
	}
	
	public static int nextNode(DGraph g, Graph_Algo gg,Robot robot) {
		  
	        LinkedList<node_data> path=robot.getpath();
	        System.out.println(path.size());
	        double min_path=Double.MAX_VALUE;
	        Fruit fsrc=new Fruit();
	        if(path.isEmpty()) {
	        	for(int f=0;f<Fruit.size();f++)
	        	{
	        		if (Fruit.get(f).get_boolean()==true)
	        			continue;
	        		edge_data f_edge=Fruit.get(f).getedge();
	        		Fruit.get(f).set_boolean(true);
	        		double p=(gg.shortestPathDist(robot.getsrc(), f_edge.getSrc())+(f_edge.getWeight()));
	        				//Fruit.get(f).getValue();
	        		
	        		if(min_path>p)
	        		{
	        			min_path=p;
	        			path=(LinkedList<node_data>)gg.shortestPath(robot.getsrc(), f_edge.getSrc());
	        			fsrc=Fruit.get(f);
	        			if(!(g.getNode(Fruit.get(f).getedge().getDest()).getKey()==robot.getdest()))
	        				path.add(g.getNode(Fruit.get(f).getedge().getDest()));	
	        		}  
	        }
	      }
	       for(int i=0;i<path.size();i++)
	       {
	    	   node_data n=path.get(i);
	    	   if(n.getKey()==robot.getsrc())
	    	   {
	    		   path.remove(i);
	    	   }
	       }
	        robot.setpath(path);
	        t=125;
	       if(!path.isEmpty())
	       {	
	    	   if(path.size()==1)
	    		   t=105;
	    	   node_data n = path.get(0);
		        path.remove(0);
		        robot.setpath(path);
		        return n.getKey();
	       }
	        return -1;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		MyGameGUIauto gg=new MyGameGUIauto();
		gg.run();
	}
}