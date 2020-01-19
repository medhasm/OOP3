package JunitTests;
import Server.Game_Server;
import Server.game_service;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import dataStructure.EdgeData;
import dataStructure.Fruit;
import dataStructure.edge_data;
import dataStructure.node;
import dataStructure.node_data;
import utils.Point3D;

class FruitJunit {
Fruit fruit=new Fruit();
int scenario_num = 2;
game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
@BeforeEach
void init() {
	fruit=new Fruit();
	
}

	@Test
	void testFruitDoubleIntPoint3D() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		fruit=new Fruit(value,type,p);
		assertEquals(fruit.getValue(), 5.0);
		assertEquals(fruit.getType(), 1);
		assertSame(fruit.getPOS(),p);
		
	}

	@Test
	void testFruitFruit() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		Fruit fruit1=new Fruit(value,type,p);
		
		fruit=new Fruit(fruit1);
		assertEquals(fruit.getValue(), 5.0);
		assertEquals(fruit.getType(), 1);
		assertSame(fruit.getPOS(),p);
		
	}



	@Test
	void testGetValue() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		Fruit fruit1=new Fruit(value,type,p);
		
		fruit=new Fruit(fruit1);
		assertEquals(fruit.getValue(), 5.0);
	}

	@Test
	void testGetType() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		Fruit fruit1=new Fruit(value,type,p);
		
		fruit=new Fruit(fruit1);
		
		assertEquals(fruit.getType(), 1);
	}

	@Test
	void testGetPOS() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		Fruit fruit1=new Fruit(value,type,p);
		
		fruit=new Fruit(fruit1);

		assertSame(fruit.getPOS(),p);
	}
	@Test
	void testInitJason() {
		Iterator<String> fruite=game.getFruits().iterator();
		String s=fruite.next();
		fruit.init(s);
		Point3D p=new Point3D(35.197656770719604,32.10191878639921,0.0);
		assertEquals(fruit.getValue(),5.0);
		assertEquals(fruit.getType(),-1);
		assertEquals(p,fruit.getPOS());
		
		

	}

}
