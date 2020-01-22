package dataStructure;

import java.util.ArrayList;

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
	public  Robot(String JASON) {
		 try {
			JSONObject line = new JSONObject(JASON);
			JSONObject ttt = line.getJSONObject("Robot");
			this.id = ttt.getInt("id");
			this.src = ttt.getInt("src");
			this.dest = ttt.getInt("dest");
			Point3D p=new Point3D(ttt.getString("pos"));
			this.pos=p;
		 }catch(JSONException e) {e.printStackTrace();
		 
		 }
	}
	public Robot(int src, int id, double value,Fruit f ) {
		this.src=src;
		this.id=id;
		this.value=value;
		this.RF=f;
	}
	public Robot(int src, int id, Point3D pos, int dest, double value) 
	{
		this.src = src;
		this.id = id;
		this.pos = pos;
		this.dest = dest;
		this.value = value;
	}
	
	public void SetFruit(Fruit f) {
		RF=f;	
	}
	public Fruit getFruit() {
		return this.RF;
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
	public double getSpeed() {
		return this.speed;
	}
	public boolean isStucked() {
		int numofsrc=0,numofdest=0;
		for(int i=0;i<arr.size();i++) {
		if(arr.get(i)==arr.get(0)) numofsrc++;
		if(arr.get(i)==arr.get(1)) numofdest++;
	}
		if(numofsrc==numofdest) return true;
		return false;
	}
	public void initarr() {
		this.arr=new ArrayList<Integer>();
	}
	public void addint(int a) {
		arr.add(a);
	}
}
