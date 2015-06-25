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

public class StartGui {
	public static boolean startsim = false;
	private Map map = null;
	private int guardno = 0;
	private int thiefno = 0;
	private int algorithmval;
	private int mapval;
	private double mapcomplex;
	private int width = 0;
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args){
		StartGui start = new StartGui();
		while(!startsim){
			
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public StartGui(){
		startPanel();
	}
	public void startPanel(){
		JFrame startframe = new JFrame("Sneaky Thief");
		startframe.setSize(400, 1000);
		startframe.setLocationRelativeTo(null);
		startframe.setResizable(true);
		startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int layoutheight = 15;
		int layoutwidth = 2;
		GridLayout startlayout = 
				new GridLayout(layoutheight,layoutwidth);
		startframe.getContentPane().setLayout(startlayout);
		JLabel line1 = new JLabel("");
		JLabel line2 = new JLabel("");
		JLabel line3 = new JLabel("");
		JLabel startlabel = new JLabel("Please select your variables.");
		JLabel pathFinderLabel = new JLabel("Algorithms:");
		String[] algorithmlist = {"A-Star", "Rob A-Star"};
		JComboBox<String> pathFinderinput = new JComboBox<String>(algorithmlist);
		String[] widthheight = {"20","50","100", "200"};
		JLabel sizeLabel = new JLabel("Size:");
		JComboBox<String> sizeinput = new JComboBox<String>(widthheight);
		JLabel complexLabel = new JLabel("Complexity:");
		String[] complexlist = {"0.0","0.1","0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"};
		JComboBox<String> complexinput = new JComboBox<String>(complexlist);
		JLabel maptype = new JLabel("Map type:");
		String[] maplist = {"Default","Rooms","Maze", "U-Map"};
		JComboBox<String> mapbox = new JComboBox<String>(maplist);
		JLabel guardlabel = new JLabel("Guard amount:");
		JTextField guardinput = new JTextField(5);
		JLabel thieflabel = new JLabel("Thief amount:");
		JTextField thiefinput = new JTextField(1);
		
		JLabel guardCatchLabel = new JLabel("Algorithms:");
		String[] guardCatchType = {"BES", "A-Star", "off"};
		JComboBox<String> guardCatchBox = new JComboBox<String>(guardCatchType);
		
		JLabel explorationLabel = new JLabel("Algorithms:");
		String[] explorationType = {"Random", "Closesed", "off"};
		JComboBox<String> explorationBox = new JComboBox<String>(explorationType);
		
		JLabel covarageLabel = new JLabel("Algorithms:");
		String[] covarageType = {"Random", "Closesed", "off"};
		JComboBox<String> covarageBox = new JComboBox<String>(explorationType);
		
		JButton beginbutton = new JButton("Begin Simulation");
		beginbutton.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e)
			{
				algorithmval = pathFinderinput.getSelectedIndex();
				mapval = mapbox.getSelectedIndex();
				guardno = Integer.parseInt(guardinput.getText());
				thiefno = Integer.parseInt(thiefinput.getText());
				width = Integer.parseInt((String) sizeinput.getSelectedItem());
				mapcomplex = Double.parseDouble((String) complexinput.getSelectedItem());
				//new Simulator();
				//TODO: Get map with chosen specifications
				System.out.println(width + " " + guardno+ " " + thiefno + " " + algorithmval + " "+mapval);
			}
		});
		//Map settings
		startframe.getContentPane().add(startlabel);
		startframe.getContentPane().add(line1);
		startframe.getContentPane().add(sizeLabel);
		startframe.getContentPane().add(sizeinput);
		
		startframe.getContentPane().add(maptype);
		startframe.getContentPane().add(mapbox);
		
		startframe.getContentPane().add(complexLabel);
		startframe.getContentPane().add(complexinput);
		
		startframe.getContentPane().add(pathFinderLabel);
		startframe.getContentPane().add(pathFinderinput);
		
		//Guard
		startframe.getContentPane().add(guardlabel);
		startframe.getContentPane().add(guardinput);
		
		startframe.getContentPane().add(explorationLabel);
		startframe.getContentPane().add(explorationBox);
		
		startframe.getContentPane().add(covarageLabel);
		startframe.getContentPane().add(covarageBox);
		
		startframe.getContentPane().add(guardCatchLabel);
		startframe.getContentPane().add(guardCatchBox);

		
		//Thief
		startframe.getContentPane().add(thieflabel);
		startframe.getContentPane().add(startlabel);
		startframe.getContentPane().add(line3);
		
		startframe.setVisible(true);
		startframe.getContentPane().add(beginbutton);
		
	}
}


