package experiments;

import java.util.ArrayList;
import java.util.Random;

import core.Simulator;
import core.Map;
import core.sprite.SpriteManager;
import dataContainer.Coordinate;
import dataContainer.GridState;

public class PreyExperiment extends AbstractExperiment {


	public static void main(String[] args) {
		PreyExperiment exp = new PreyExperiment();
		exp.dummyExperiment(20);
	}

	public PreyExperiment() {
		super();
		names.add("Thief_algorithm");
		names.add("Guards_algorithms");
		names.add("Thief_success");
		names.add("run_time");
		names.add("n_guards");
		names.add("map_Guard_density");
		names.add("map_type");
		names.add("map_area");

	}

	public void singleRun(String algorithm, int guards, int map, MapType mapType) {

	}

	public static void dummyExperiment(int repeats) {
		
		
			//for (MapType map_type : MapType.values())
			double guardDensity = 0.02;
					//for(double guardDensity = 0.01; guardDensity < 0.02; guardDensity += 0.001)
						for (int i = 0; i < repeats; i++){

							//String name = "/" + map_type.name() + "/maze-100x100-"+ i+ ".map";
							String name = "/maze/maze-"+ i+ ".map";
							Map map = new Map(name);
							int map_area = 100 * 100;
							int movableCoords = 0;
							GridState[][] grid = map.getCopyOfMap();
							ArrayList<Coordinate> placable = new ArrayList<Coordinate>();
			
							for (int j = 1; j < grid.length-1; j++)
								for (int k = 1; k < grid.length-1; k++){
									if (grid[j][k].moveable()){
										movableCoords++;
										placable.add(new Coordinate(j,k,0));
									}
								}
							int nGuards = (int) (movableCoords * guardDensity);
							System.out.println("number of Guards : " + nGuards);
							System.out.println("map : " + i);
							Simulator sim = new Simulator(nGuards,placable,map);
							int win = sim.preyWinLoss;
							double time = sim.preyTime;
							
							SpriteManager spriteManager = SpriteManager.instance();
							spriteManager.getAgentList().clear();
							System.out.println("agents " + spriteManager.getAgentList().size());
							System.gc();
							Runtime.getRuntime().gc();
							
							
							//save measurement
							String[] measurements =  new String[8];

							measurements[0] = "none";
							measurements[1] = "none(RobStar)";
							measurements[2] = Double.toString(win==1 ? 1 : 0);
							measurements[3] = Double.toString(time);
							measurements[4] = Double.toString(nGuards);
							measurements[5] = Double.toString(guardDensity);
							measurements[6] = "maze";//map_type.name();
							measurements[7] = Double.toString(map_area);
							
							//System.out.println(measurements.toString());
							values.add(measurements);
							
							}

		writeCsv("experiments/prey/data/dummy.csv");

	}

	static Random r = new Random();

	private enum Coordination {
		bes;
	}

	private enum Algorithm {
		none,prey_a_star, reverse_a_star;
	}

	private enum MapType {
		RoomMaps, Umaps;
	}
}
