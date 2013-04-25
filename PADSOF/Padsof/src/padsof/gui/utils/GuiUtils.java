package padsof.gui.utils;

import java.awt.*;

import javax.swing.*;

/**
 * Some util functions for GUI elements.
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
public class GuiUtils
{
	/**
	 * Applies a title style to a label.
	 * @param label Label.
	 */
	public static void applyTitleStyle(JLabel label)
	{
		label.setFont(label.getFont().deriveFont((float) 20.0));
	}
	
	/**
	 * Generates a horizontal panel with the given components, using FlowLayout.
	 * @param components Components.
	 * @return JPanel.
	 */
	public static JPanel generateLinePanel(Component... components)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		for(Component b : components)
			panel.add(b);
		
		return panel;
	}
}
