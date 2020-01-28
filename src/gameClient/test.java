package gameClient;

import java.util.LinkedList;
import java.util.List;

public class test {
public static void main(String[] args) {
	LinkedList<Integer> lst=new LinkedList<Integer>();
	lst.add(1);
	lst.add(2);
	lst.add(3);
	lst.add(4);
	lst.add(5);
	lst.addFirst(10);;
	//System.out.println(lst.get(0));
	System.out.println(lst);
	//int x= (int)(Math.random()*10);
	//System.out.println(x);
	for(int i=0;i<5;i++)
	{
		if(i==3)
			continue;
		
		System.out.println(i);
		if(i==4)
			System.out.println("hii");
	}
}
}
