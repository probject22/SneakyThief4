package mapBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class MapSaver {
	PrintWriter writer = null;
	boolean fileSizeSet = false;
	int width, height = 0;
	int lineCounter = 0;
	public MapSaver(String filePath){
		newFile(filePath);
	}
	
	public void setSize(int width, int heigth){
		writer.println(heigth);
		writer.println(width);
		this.width = width;
		this.height = heigth;
		fileSizeSet = true;
	}
	
	public void addLine(String line){
		if (!fileSizeSet){
			System.err.println("the mapsize is not set");
			//return;
		}
		if (line.length() != width){
			System.err.println("the length of the line is not correct");
			//return;
		}
		//System.out.println(line);
		writer.println(line);
		lineCounter++;
	}
	public void closeMap(){
		System.out.println("linecounter="+lineCounter);
		if (lineCounter != height){
			System.err.println("the mapfile is corrupted");
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * returns true if the file is made successfully
	 * @param filePath
	 * @return
	 */
	private boolean newFile(String filePath){
		try {
			writer = new PrintWriter(filePath, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			writer = null;
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			writer = null;
			return false;
		}
		return true;
	}
}
