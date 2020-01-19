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

}
