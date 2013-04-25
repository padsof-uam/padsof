package padsof.gui.utils;

import java.awt.*;

import javax.swing.*;

public class GuiUtils
{
	public static void applyTitleStyle(JLabel label)
	{
		label.setFont(label.getFont().deriveFont((float) 20.0));
	}
	
	public static JPanel generateButtonPanel(Component... buttons)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		for(Component b : buttons)
			panel.add(b);
		
		return panel;
	}
}
