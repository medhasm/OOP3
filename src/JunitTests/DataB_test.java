package JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameClient.My_Data_Base;

class DataB_test {

	

	@Test
	void testGamesPlayed() {
		My_Data_Base db=new My_Data_Base();
		int x=db.gamesPlayed(206953127);
		System.out.println(x);// TODO
	}

	@Test
	void testMyBestResults() {
		My_Data_Base db=new My_Data_Base();
		HashMap<Integer, String> best=db.myBestResults(206953127);
		System.out.println(best);
	}

	@Test
	void testGameBestResults() {
		My_Data_Base db=new My_Data_Base();
		HashMap<String, String> best=db.gameBestResults();
		//System.out.println(best.get(206953127));
	}

	
}
