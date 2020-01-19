package JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import dataStructure.Fruit;
import dataStructure.Robot;
import utils.Point3D;

class RobotJunit {
	Robot robot;
	Fruit fruit;
	int scenario_num = 2;
	game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
@BeforeEach
void init() {
	
	 fruit=new Fruit();
	 robot=new Robot(1,0,3.0,fruit);
	
}
	@Test
	void testRobotIntIntDoubleFruit() {
		
		assertEquals(robot.getid(), 0);
		assertEquals(robot.getsrc(), 1);
		assertSame(robot.getFruit(),fruit);
	}

	@Test
	void testRobotIntIntPoint3DIntDouble() {
		int src= 1;
		int id=0;
		int dest=3;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		robot=new Robot(src,id,p,dest,value);
		assertEquals(robot.getsrc(),1);
		assertEquals(robot.getdest(),3);
		assertEquals(robot.getvalue(),5.0);
		assertEquals(robot.getid(),0);
		assertSame(robot.getpos(),p);
		
		
	}

	@Test
	void testSetFruit() {
		int type= 1;
		double value=5.0;
		Point3D p=new Point3D(3,4,0);
		Fruit fruit1=new Fruit(value,type,p);
		robot.SetFruit(fruit1);
		assertSame(robot.getFruit(),fruit1);
	}

@Test
void testInitjason() {
	Point3D pos=new Point3D(35.19341035835351,32.10610841680672,0.0);
	
	Robot f=new Robot(2, 0, pos, -1, 3.0);
	game.addRobot(2);
	game.chooseNextEdge(0,3);
	game.startGame();
	List<String> log=game.move();
	if(log!=null) {
		robot.init(log.get(0));
		System.out.println(log.get(0));
	
	}
	
	game.stopGame();
	assertEquals(robot.getsrc(),f.getsrc());
	assertEquals(robot.getdest(),f.getdest());
	assertEquals(robot.getvalue(),f.getvalue());
	assertEquals(robot.getid(),f.getid());
	assertEquals(robot.getpos(),f.getpos());
	
}



}
