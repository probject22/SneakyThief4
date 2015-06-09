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
	
	public static void experiment(int repeats){
		Random r = new Random();
		
		for(int i= 0; i < repeats;i++){
			
			String algorithm = r.nextBoolean() ? "prey_a_star" : "reverse_a_star";
			String success = r.nextBoolean() ? "-1" : "1";
			String run_time = Long.toString(r.nextLong());
			String n_guards = Integer.toString(r.nextInt(100));
			int a = r.nextInt(5);
			String map_type;
			if (a == 0) map_type = "blank";
			else if(a == 1)map_type = "maze";
			else if(a == 2)map_type = "rooms";
			else if(a == 3)map_type = "walls";
			else map_type = "Us";
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

}
