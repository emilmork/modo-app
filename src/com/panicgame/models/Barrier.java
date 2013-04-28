package com.panicgame.models;

public class Barrier {
	int[][]from;
	int[][]to;
	public Barrier(int[][]from,int[][]to){
		this.from = from;
		this.to = to;
	}
	
	public int[][] getFrom(){
		return from;
	}
	public int[][] getTo(){
		return to;
	}

}
