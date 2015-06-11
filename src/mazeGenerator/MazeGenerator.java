package mazeGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class MazeGenerator {
	
	private int[][] maze;
	
	public MazeGenerator(int x, int y){
		maze = new int[x][y];
		//makeEmpty();
	}
	
	private void makeEmpty(){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = 0;
			}
		}
	}
	
	private void makeFull(){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = 1;
			}
		}
	}
	
	public void prim(){
		
		makeFull();
		
		List<C> passages = new ArrayList<>();
		List<C> walls = new ArrayList<>();
		
		C start = new C(1,1);
		
		passages.add(start);
		walls.addAll(start.neighbours());
		while(!walls.isEmpty()){
			Collections.shuffle(walls, new Random(10));
			C wall = walls.get(0);
			int count = 0;
			for(C c : wall.neighbours()) count += passages.contains(c) ? 1 : 0;
			
			if(count == 1){
				passages.add(wall);
				for(C c: wall.neighbours())
					if( !passages.contains(c) )
						walls.add(c);
			}
			
			walls.remove(wall);
		}

		for(C c : passages)
			maze[c.x][c.y] = 0;
		
		maze[1][1] = 0;
		maze[maze.length-2][1] = 0;
		maze[maze.length-2][maze[maze.length-2].length-2] = 0;
		maze[1][maze[1].length - 2] = 0;
	}
	
	private void randomWalls(double complexity){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if(Math.random() < complexity)
					maze[i][j] = 1;
			}
		}
	}
	
	public String toString(){
		String s = "";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) 
					s += maze[i][j] == 0 ? " " : "x";
			s += "\n";
		}
		return s;
	}
	
	private class C{
		
		C(int x, int y){
			this.x = x;
			this.y = y;
		}
		int x;
		int y;
		public List<C> neighbours(){
			List<C> n = new ArrayList<>();
			n.add(new C(x-1,y));
			n.add(new C(x,y-1));
			n.add(new C(x+1,y));
			n.add(new C(x,y+1));
			n.add(new C(x+1,y+1));
			n.add(new C(x+1,y-1));
			n.add(new C(x-1,y+1));
			n.add(new C(x-1,y-1));
			List<C> r = new ArrayList<>();
			for (C c : n) if(c.x < 1 || c.x >= maze.length-1 || c.y < 1 || c.y >= maze[0].length-1) r.add(c);
			for (C c : r) n.remove(c);
			return n;
		}
		public boolean equals(Object o){
			return (o instanceof C) ? x==((C)o).x && y == ((C)o).y : false;
		}
		public String toString(){
			return "(x: " +  x + ", y: " + y + ")";
		}
	}
	
	public int[][] getMaze(){
		return maze;
	}
	
	public static void main(String[] args){
		MazeGenerator gen = new MazeGenerator(100,100);
		gen.prim();
		System.out.print(gen.toString());
	}
	
}
