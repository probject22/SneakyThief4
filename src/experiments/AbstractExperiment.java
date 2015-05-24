package experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExperiment {
	protected List<String> names;
	protected List<double[]> values;
	
	public AbstractExperiment(){
		names = new ArrayList<>();
		values = new ArrayList<>();
	}
	
	public void writeCsv(String path){
		
		try{
			File file = new File(path);
			file.createNewFile();
			
			FileWriter writer = new FileWriter(file);
			
			//write names
			for (String name : names) {
				writer.append(name);
				writer.append(", ");
			}
			writer.append("\n");
			
			//write values
			for (double[] v : values) {
				for (double d : v) {
					writer.append(Double.toString(d));
					writer.append(", ");
				}
				writer.append("\n");
			}
			
			writer.flush();
			writer.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
}