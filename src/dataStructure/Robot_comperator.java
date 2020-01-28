package dataStructure;

import java.util.Comparator;

public class Robot_comperator  implements Comparator<Robot>{

	public Robot_comperator() {;}
	
	@Override
	public int compare(Robot r1, Robot r2) {
	
    	if(r1.getSpeed()-r2.getSpeed()<0) return -1;
    		else if(r1.getSpeed()-r2.getSpeed()>0) return 1;
    	return 0;//there value are equal****
	}
}
