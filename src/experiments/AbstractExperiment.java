package experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExperiment {
	protected static List<String> names = new ArrayList<>();
	protected static List<String[]> values = new ArrayList<>();
	
	public AbstractExperiment(){
	}
	
	public static void writeCsv(String path){
		
		try{
			File file = new File(path);
			file.createNewFile();
			
			FileWriter writer = new FileWriter(file);
			
			//write names
			for (String name : names) {
				writer.append(name);
				writer.append(",");
			}
			writer.append("\n");
			
			//write values
			for (String[] v : values) {
				for (String d : v) {
					writer.append(d);
					writer.append(",");
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
