package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
		
		GridLayout thislayout = new GridLayout(2, 5);
		this.setLayout(thislayout);
		
		SpinnerNumberModel agentmodel = new SpinnerNumberModel(1, 1, 10,1);
		SpinnerNumberModel agentmodel2 = new SpinnerNumberModel(1, 1, 10,1);
		JSpinner thiefinput = new JSpinner(agentmodel);
		JSpinner guardinput = new JSpinner(agentmodel2);
		this.add(pausebutton);
		this.add(stopbutton);
		this.add(guardinput);
		this.add(thiefinput);
	}
}

