package dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Point3D;

public class Robot {
	public  ArrayList<Integer> arr=new ArrayList<Integer>();
	int src;
	Point3D pos;
	int id;
	int dest;
	double value;
	double speed;
	private Fruit RF =new Fruit();
	Point3D fpos;
	public   LinkedList<node_data> path=new LinkedList<node_data>();

	public  Robot(String JASON) {
		 try {
			JSONObject line = new JSONObject(JASON);
			JSONObject ttt = line.getJSONObject("Robot");
			this.id = ttt.getInt("id");
			this.src = ttt.getInt("src");
			this.dest = ttt.getInt("dest");
			Point3D p=new Point3D(ttt.getString("pos"));
			this.pos=p;
			this.path=this.getpath();
		 }catch(JSONException e) {e.printStackTrace();
		 
		 }
	}
	public Robot(int src, int id, double value,Fruit f ) {
		this.src=src;
		this.id=id;
		this.value=value;
		this.RF=f;
		this.path=this.getpath();

	}
	public Robot(int src, int id, Point3D pos, int dest, double value) 
	{
		this.src = src;
		this.id = id;
		this.pos = pos;
		this.dest = dest;
		this.value = value;
		this.path=this.getpath();
	}
	
	public void SetFruit(Fruit f) {
		RF=f;	
	}
	public Fruit getFruit() {
		return this.RF;
	}
	public void setfpos(Fruit f) {
		this.fpos=new Point3D(f.getPOS());
		
	}
	public Point3D getfpos() {
		return this.fpos;
		
	}

	public void init(String JASON) {
		 try {
			JSONObject line = new JSONObject(JASON);
			JSONObject ttt = line.getJSONObject("Robot");
			this.id = ttt.getInt("id");
			this.src = ttt.getInt("src");
			this.dest = ttt.getInt("dest");
			this.speed=ttt.getDouble("speed");
			Point3D p=new Point3D(ttt.getString("pos"));
			this.pos=p;
			this.path=this.getpath();
		 }catch(JSONException e) {e.printStackTrace();
		 
		 }
	}

	/**
	 * return the source of this robot 
	 * @return
	 */
	public int getsrc()
	{
		return this.src;
	}
	
	/**
	 * 	 return the dest of this robot 
	 * @return
	 */
	public int getdest()
	{
		return this.dest;
	}
	
	/**
	 * 	 return the ID of this robot 
	 * @return
	 */
	public int getid()
	{
		return this.id;
	}
	
	/**
	 * 	 return the Positions of this robot 
	 * @return
	 */
	public Point3D getpos()
	{
		return this.pos;
	}
	
	/**
	 * 	 return the value of this robot 
	 * @return
	 */
	public double getvalue()
	{
		return this.value;
	}

	public LinkedList<node_data> getpath()
	{
		return this.path;
	}
	public void setpath(LinkedList<node_data> p)
	{
		this.path=p;
	}
	public double getSpeed() {
		return this.speed;
	}
	public boolean isStucked() {
		int numofsrc=0,numofdest=0;
		for(int i=0;i<arr.size();i++) {
		if(arr.get(i)==arr.get(0)) numofsrc++;
		if(arr.get(i)==arr.get(1)) numofdest++;
	}
		if(numofsrc==numofdest && numofsrc==3) return true;
		return false;
	}
	public void arr()
	{
		System.out.println(this.arr);

		this.arr=new ArrayList<Integer>();
	}
	public void addint(int a) {
		arr.add(a);
	}
}

