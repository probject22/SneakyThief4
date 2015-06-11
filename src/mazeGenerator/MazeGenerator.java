package mazeGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MazeGenerator {
	
	private int[][] maze;
	
	private final int WALL = 1;
	private final int PASSAGE = 0;
	
	public MazeGenerator(int x, int y){
		maze = new int[x][y];
		//makeEmpty();
	}
	
	private void makeEmpty(){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = PASSAGE;
			}
		}
	}
	
	private void makeFull(){
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = WALL;
			}
		}
	}
	
	
	public void prim(){
		prim(1);
	}
	
	public void makeWalls(){
		for(int i = 0; i < maze.length; i ++){
			maze[i][0] = WALL;
			maze[i][maze.length - 1] = WALL;
		}
		for(int i = 0; i < maze[0].length; i ++){
			maze[0][i] = WALL;
			maze[maze[0].length-1][i] = WALL;
		}
	}
	
	Random r = new Random(10);
	
	public void prim(double difficulty){
		
		if(difficulty == 0){
			makeEmpty();
			makeWalls();
			return;
		}
		makeFull();
		
		List<C> passages = new ArrayList<>();
		List<C> walls = new ArrayList<>();
		List<C> eWalls = new ArrayList<>();
		
		C start = new C(1,1);
		
		passages.add(start);
		walls.addAll(start.neighbours());
		while(!walls.isEmpty()){
			C wall = walls.remove(walls.size() == 1 ? 0 : r.nextInt(walls.size()-1));
			int count = 0;
			for(C c : wall.neighbours()) count += passages.contains(c) ? 1 : 0;
			
			if(count == 1){
				passages.add(wall);
				for(C c: wall.neighbours())
					if( !passages.contains(c) )
						walls.add(c);
			} else {
				if(! eWalls.contains(wall))
				eWalls.add(wall);
			}
		}

		
		//free the corners
		List<C> corners = new ArrayList<>();
		
		corners.add(new C(1			,1						));
		corners.add(new C(maze.length-2,1					));
		corners.add(new C(1			, maze[0].length - 2	));
		corners.add(new C(maze.length-2, maze[0].length - 2	));
		
		for (Iterator<C> iterator = corners.iterator(); iterator.hasNext();) {
			C c = iterator.next();
			int count = 0;
			//System.out.println(c.neighbours());
			for(C n : c.neighbours()){
				if(!passages.contains(n))
					count++;
			}
			if(count >= 3){
				//System.out.println("yeah");
				passages.add( c.x == 1 ? new C(c.x+1,c.y) : new C(c.x-1,c.y));
			}
		}
		
		passages.addAll(corners);
		
		
		
		int remove = Math.min((int) (((maze.length - 1) * (maze[0].length -1)-passages.size())*(1-difficulty)), eWalls.size());
		
		
		for(int i = 0; i < remove; i++)
			//if(!eWalls.isEmpty())
				passages.add(eWalls.remove(eWalls.size() <= 1 ? 0 :r.nextInt(eWalls.size())));
		for(C c : passages)
			maze[c.x][c.y] = 0;
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
