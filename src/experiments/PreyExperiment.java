package experiments;

import java.util.Random;

public class PreyExperiment extends AbstractExperiment {

	public static void main(String[] args) {
		PreyExperiment exp = new PreyExperiment();
		exp.dummyExperiment(1000);
	}

	public PreyExperiment() {
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

	public void singleRun(String algorithm, int guards, int map, MapType mapType) {

	}

	public static void dummyExperiment(int repeats) {
		for (int i = 0; i < repeats; i++)
		for (Algorithm algorithm : Algorithm.values())
		for (MapType map_type : MapType.values())
		for (int n_guards = 0; n_guards < 10; n_guards++)
		for (int map_width = 95; map_width < 100; map_width++)
		for (int map_height = 95; map_height < 100; map_height++)
		for (Coordination multi_agent_coordination : Coordination.values()) {
			int map_area = map_width * map_height;
			String map_density = Double.toString(r.nextDouble());
			String success = r.nextBoolean() ? "-1" : "1";
			String run_time = Long.toString(r.nextLong());
			String[] v = new String[] { 
					algorithm.name(),
					success,
					run_time, 
					Integer.toString(n_guards),
					map_type.name(), 
					Integer.toString(map_area), 
					map_density,
					multi_agent_coordination.name()
					};
			values.add(v);
		}

		writeCsv("experiments/reverse_a_star/data/dummy.csv");

	}

	static Random r = new Random();

	private enum Coordination {
		none, bes;
	}

	private enum Algorithm {
		prey_a_star, reverse_a_star;
	}

	private enum MapType {
		blank, maze, rooms, walls, Us;
	}
}
