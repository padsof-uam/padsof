package padsof.gui.layout;

import java.awt.Container;
import java.util.*;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class GroupLayoutHelper
{
	private List<List<JComponent>> columns = new ArrayList<List<JComponent>>();
	private int leftMargin = 0;
	private int rightMargin = 0;
	private int topMargin = 0;
	private int bottomMargin = 0;
	
	public void addColumn(List<JComponent> column)
	{
		columns.add(column);
	}
	
	public GroupLayout generateLayout(Container container)
	{
		GroupLayout layout = new GroupLayout(container);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		Group horizontal = generateHorizontalGroup(layout);	
		Group vertical = generateVerticalGroup(layout);
		
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		
		return layout;
	}

	private Group generateVerticalGroup(GroupLayout layout)
	{
		Group vertical = layout.createSequentialGroup();
		
		if(topMargin > 0)
			vertical.addGap(topMargin);
		
		int maxColumnHeight = findMaxColumnHeight();
		
		for(int i = 0; i < maxColumnHeight; i++)
		{
			ParallelGroup rowGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
			
			for(List<JComponent> column : columns)
			{
				if(i >= column.size())
					rowGroup.addComponent(Box.createGlue());
				else
					rowGroup.addComponent(column.get(i));
			}
		}
		
		if(bottomMargin > 0)
			vertical.addGap(bottomMargin);
		
		return vertical;
	}
	
	private Group generateHorizontalGroup(GroupLayout layout)
	{
		Group horizontal = layout.createSequentialGroup();
		
		if(leftMargin > 0)
			horizontal.addGap(leftMargin);
		
		int maxColumnHeight = findMaxColumnHeight();
		
		for(List<JComponent> column: columns)
		{
			ParallelGroup columnGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
			int i;
			
			for(i = 0; i < column.size(); i++)
				columnGroup.addComponent(column.get(i));
			
			for(; i < maxColumnHeight; i++)
				columnGroup.addComponent(Box.createGlue());
			
			horizontal.addGroup(columnGroup);
		}
		
		if(rightMargin > 0)
			horizontal.addGap(rightMargin);
		
		return horizontal;
	}
	
	private int findMaxColumnHeight()
	{
		int max = -1;
		
		for(List<JComponent> column: columns)
			if(column.size() > max)
				max = column.size();
		
		return max;
	}

	public JPanel generatePanel()
	{
		JPanel panel = new JPanel();
		GroupLayout layout = generateLayout(panel);
		panel.setLayout(layout);
		
		return panel;
	}
	
	public void setInnerMargins(int left, int top, int right, int bottom)
	{
		leftMargin = left;
		topMargin = top;
		rightMargin = right;
		bottomMargin = bottom;
	}
	
	public static JPanel generateGroupLayout(List<JComponent>... columns)
	{
		GroupLayoutHelper helper = new GroupLayoutHelper();
		
		for(List<JComponent> column : columns)
			helper.addColumn(column);
		
		return helper.generatePanel();
	}
}
