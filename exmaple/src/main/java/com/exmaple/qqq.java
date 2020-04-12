package com.exmaple;

public class qqq {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100000; i++) {
			if(i%2==1 
					&& i%3 == 0 
					&& i%4 == 1 
					&& i%5 == 4 
					&& i%6 == 3 
					&& i%7 == 0
					&& i%8 == 1 
					&& i%9 == 0 ) {
				System.out.println(i);
			}
		}
	}

}
