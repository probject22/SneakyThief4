package newGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import core.Map;
import core.Simulator;

public class Start {
	public static boolean startsim = false;
	private Map map = null;
	private int guardno = 0;
	private int thiefno = 0;
	private int algorithmval;
	private int mapval;
	private double mapcomplex;
	private int width = 0;
	public static void main(String[] args){
		Start start = new Start();
		while(!startsim){
			
		}
	}
	
	public Start(){
		startPanel();
	}
	public void startPanel(){
		JFrame startframe = new JFrame("Sneaky Thief");
		startframe.setSize(400, 300);
		startframe.setLocationRelativeTo(null);
		startframe.setResizable(true);
		startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int layoutheight = 12;
		int layoutwidth = 2;
		GridLayout startlayout = 
				new GridLayout(layoutheight,layoutwidth);
		startframe.setLayout(startlayout);
		JLabel startlabel = new JLabel("Please select your variables.");
		JLabel algorithms = new JLabel("Algorithms:");
		String[] algorithmlist = {"A-Star","Real-Time A-Star","Learning Real-Time A-Star", "Rob A-Star"};
		JComboBox algorithmbox = new JComboBox(algorithmlist);
		JLabel widthheight = new JLabel("Width / Height:");
		JTextField sizeinput = new JTextField(150);
		JLabel complexityheight = new JLabel("Complexity:");
		JTextField complexinput = new JTextField("0.5");
		JLabel maptype = new JLabel("Map type:");
		String[] maplist = {"Default","Rooms","Maze", "U-Map"};
		JComboBox mapbox = new JComboBox(maplist);
		JLabel guardlabel = new JLabel("Guard amount:");
		JTextField guardinput = new JTextField(5);
		JLabel thieflabel = new JLabel("Thief amount:");
		JTextField thiefinput = new JTextField(1);
		JButton beginbutton = new JButton("Begin Simulation");
		beginbutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(algorithmlist.equals("A-Star")){
					algorithmval = 0;
				}
				else if(algorithmlist.equals("Real-Time A-Star")){
					algorithmval = 1;
				}
				else if(algorithmlist.equals("Learning Real-Time A-Star")){
					algorithmval = 2;
				}
				else if(algorithmlist.equals("Rob A-Star")){
					algorithmval = 3;
				}
				if(maplist.equals("Default")){
					mapval = 0;
				}
				else if(maplist.equals("Rooms")){
					mapval = 1;
				}
				else if(maplist.equals("Maze")){
					mapval = 2;
				}
				else if(maplist.equals("U-Map")){
					mapval = 3;
				}
				guardno = Integer.parseInt(guardinput.getText());
				thiefno = Integer.parseInt(thiefinput.getText());
				width = Integer.parseInt(sizeinput.getText());
				mapcomplex = Double.parseDouble(complexinput.getText());
				//new Simulator();
				//TODO: Get map with chosen specifications
				System.out.println(width + " " + guardno+ " " + thiefno + " " + algorithmval + " "+mapval);
			}
		});
		startframe.add(startlabel);
		startframe.add(algorithms);
		startframe.add(algorithmbox);
		startframe.add(widthheight);
		startframe.add(sizeinput);
		startframe.add(maptype);
		startframe.add(mapbox);
		startframe.add(guardlabel);
		startframe.add(guardinput);
		startframe.add(thieflabel);
		startframe.add(thiefinput);
		startframe.setVisible(true);
		startframe.add(beginbutton);
		
	}
}


