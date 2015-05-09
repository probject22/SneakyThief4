package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import core.Simulator;

public class ControlPanel extends JPanel {
	public ControlPanel()
	{
		//Components of the Control Panel
		
		JButton pausebutton = new JButton("Start/Pause");
		pausebutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Simulator.setPause(!Simulator.getPause());
			}
		});
		JButton stopbutton = new JButton("Stop");
		stopbutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Simulator.setStop(true);
			}
		});
		JSpinner speedinput = new JSpinner();
		this.add(pausebutton);
		this.add(stopbutton);
		this.add(speedinput);
	}
}

