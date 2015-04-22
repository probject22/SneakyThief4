package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class ControlPanel extends JPanel {
	public ControlPanel()
	{
		//Components of the Control Panel
		
		JButton startbutton = new JButton("Start");
		JButton pausebutton = new JButton("Pause");
		JButton stopbutton = new JButton("Stop");
		JSpinner speedinput = new JSpinner();
		this.add(startbutton);
		this.add(pausebutton);
		this.add(stopbutton);
		this.add(speedinput);
	}
}
