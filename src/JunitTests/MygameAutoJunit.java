package JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.Fruit;
import dataStructure.Robot;
import dataStructure.graph;
import gameClient.MyGameGUIauto;
import utils.Point3D;

class MygameAutoJunit {
	int scenario_num = 2;
	game_service game = Game_Server.getServer(scenario_num);
	Fruit fruit=new Fruit();



	@Test
	void testGetFE() {
		int src = 9;
		int dest=8;
		
		Iterator<String> fruite=game.getFruits().iterator();
		String s=fruite.next();
		fruit.init(s);
		
	
		
		DGraph graph=new DGraph();
		graph.init(game.getGraph());
		EdgeData edge=MyGameGUIauto.GetFE((DGraph)graph, fruit);
		assertEquals(edge.getSrc(),src);
		assertEquals(edge.getDest(),dest);
		
		
	}
/*
	@Test
	void testNextNode() {
		int dest=0;
		Iterator<String> fruite=game.getFruits().iterator();
		String s=fruite.next();
		fruit.init(s);
		MyGameGUIauto gg;
		DGraph graph=new DGraph();
		graph.init(game.getGraph());
		Graph_Algo gg1=new Graph_Algo();
		gg1.init(game.getGraph());
		EdgeData edge=MyGameGUIauto.GetFE((DGraph)graph, fruit);
		System.out.println(edge.getDest());
		Robot r=new Robot(9, 0, 0.0, fruit);
		game.addRobot(9);
		game.startGame();
		
		dest=MyGameGUIauto.nextNode(graph, gg1, r);
		game.stopGame();
		System.out.println(dest);
	//	assertEquals(dest,edge.getDest());	
		
		
	}
	
*/
}
