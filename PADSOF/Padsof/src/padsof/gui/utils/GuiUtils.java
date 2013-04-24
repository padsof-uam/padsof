package padsof.gui.utils;

import java.awt.FlowLayout;

import javax.swing.*;

public class GuiUtils
{
	public static void applyTitleStyle(JLabel label)
	{
		label.setFont(label.getFont().deriveFont((float) 20.0));
	}
	
	public static JPanel generateButtonPanel(JButton... buttons)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		for(JButton b : buttons)
			panel.add(b);
		
		return panel;
	}
}
