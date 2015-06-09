package experiments;

import java.util.Random;

public class PreyExperiment extends AbstractExperiment {
	
	public static void main(String[] args){
		PreyExperiment exp = new PreyExperiment();
		exp.experiment(1000);
	}
	
	public PreyExperiment(){
		super();
		names.add("algorithm");
		names.add("success");
		names.add("run_time");
		names.add("n_guards");
		names.add("map_type");
		names.add("map_area");
		names.add("map_density");
		names.add("multi_agent_coordination");
		
		
	}
	
	public void singleRun(String algorithm, int guards, int map, enum map_type){
		
	}
	
	public static void experiment(int repeats){
		for(int i= 0; i < repeats;i++){
			for(int j = 0;j<repeats;j++){
				
			}
			for(MapType mt : MapType.values()){
			}
			String algorithm = r.nextBoolean() ? "prey_a_star" : "reverse_a_star";
			String success = r.nextBoolean() ? "-1" : "1";
			String run_time = Long.toString(r.nextLong());
			String n_guards = Integer.toString(r.nextInt(100));
			String map_type = MapType.random().toString();
			String map_area = Integer.toString(r.nextInt(4000));
			String map_density = Double.toString(r.nextDouble());
			String multi_agent_coordination = r.nextBoolean() ? "none" : "bes";
			
			String[] v = new String[]{
				algorithm, success, run_time, 
				n_guards, map_type, map_area, 
				map_density, multi_agent_coordination	
			};
			values.add(v);
		}
		
		writeCsv("experiments/reverse_a_star/data/dummy.csv");
		
	}
	
	static Random r = new Random();

	private enum MapType {
		blank, maze, rooms, walls, Us;
		
		public String toString(){
			return name();
		}
		
		static MapType random(){
			switch (r.nextInt(5)){
			case 0:
				return blank;
			case 1:
				return maze;
			case 2:
				return rooms;
			case 3:
				return walls;
			case 4:
				return Us;
			default:
				return null;
			}
		}
	}
}
