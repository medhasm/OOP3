package JunitTests;

import java.util.Iterator;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.Fruit;
import dataStructure.Robot;
import gameClient.MyGameGUIauto;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int scenario_num = 2;
		game_service game = Game_Server.getServer(scenario_num);
		int dest=0;
		Fruit fruit=new Fruit();
		Iterator<String> fruite=game.getFruits().iterator();
		String s=fruite.next();
		fruit.init(s);
		DGraph graph=new DGraph();
		graph.init(game.getGraph());
		Graph_Algo gg1=new Graph_Algo();
		gg1.init(game.getGraph());
		EdgeData edge=MyGameGUIauto.GetFE((DGraph)graph, fruit);
		Robot r=new Robot(9, 0, 0.0, fruit);
		if(MyGameGUIauto.nextNode(graph, gg1, r)!=-1) {
		dest=MyGameGUIauto.nextNode(graph, gg1, r);
		}
		game.addRobot(edge.getSrc());
	}

}
