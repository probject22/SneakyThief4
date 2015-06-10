package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import core.Simulator;

public class ControlPanel extends JPanel {
	private int guardno;
	private int thiefno;
	private int speedvalue;
	SpinnerNumberModel agentmodel = new SpinnerNumberModel(1, 1, 10,1);
	SpinnerNumberModel agentmodel2 = new SpinnerNumberModel(1, 1, 10,1);
	SpinnerNumberModel speedmodel = new SpinnerNumberModel(1, 0.2, 2, 0.2);
	JSpinner thiefinput = new JSpinner(agentmodel);
	JSpinner guardinput = new JSpinner(agentmodel2);
	JSpinner speedinput = new JSpinner(speedmodel);

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
		
		GridLayout thislayout = new GridLayout(3, 2);
		this.setLayout(thislayout);
		
		this.add(pausebutton);
		this.add(stopbutton);
		this.add(guardinput);
		this.add(thiefinput);
		this.add(speedinput);
		
	}
	
	public void readInputs(){
		guardno = (int) guardinput.getValue();
		thiefno = (int) thiefinput.getValue();
		speedvalue = (int) speedinput.getValue();
	}
}

