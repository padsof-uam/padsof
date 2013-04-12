package padsof.gui.layout;

import java.awt.*;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;

public class GroupLayoutHelper
{
	private List<Component> horizontallyLinked = new ArrayList<Component>();
	private List<Component> verticallyLinked = new ArrayList<Component>();
	private List<List<? extends Component>> columns = new ArrayList<List<? extends Component>>();
	private int leftMargin = 0;
	private int rightMargin = 0;
	private int topMargin = 0;
	private int bottomMargin = 0;
	
	public void addColumn(List<? extends Component> column)
	{
		columns.add(column);
	}
	
	public void addColumn(Component... components)
	{
		List<Component> column = new ArrayList<Component>();
		
		for(Component c : components)
			column.add(c);
		
		addColumn(column);
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
		
		if(horizontallyLinked.size() > 0)
			layout.linkSize(SwingConstants.HORIZONTAL, horizontallyLinked.toArray(new Component[horizontallyLinked.size()]));
		
		if(verticallyLinked.size() > 0)
			layout.linkSize(SwingConstants.VERTICAL, verticallyLinked.toArray(new Component[verticallyLinked.size()]));
		
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
			
			for(List<? extends Component> column : columns)
			{
				if(i >= column.size())
					rowGroup.addComponent(Box.createGlue());
				else
					rowGroup.addComponent(column.get(i));
			}
			
			vertical.addGroup(rowGroup);
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
		
		for(List<? extends Component> column: columns)
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
		
		for(List<? extends Component> column: columns)
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
	
	public GroupLayoutHelper linkHorizontalSize(Component... components) throws NoSuchObjectException
	{
		for(Component c : components)
		{
			if(!componentExists(c))
				throw new NoSuchObjectException("Component " + c.toString() + " doesn't exist in the layout.");
			horizontallyLinked.add(c);
		}
		
		
		return this;
	}
	
	private boolean componentExists(Component c)
	{
		for(List<? extends Component> column : columns)
			if(column.contains(c))
				return true;
		
		return false;
	}

	public GroupLayoutHelper linkVerticalSize(Component... components) throws NoSuchObjectException
	{
		for(Component c : components)
		{
			if(!componentExists(c))
				throw new NoSuchObjectException("Component " + c.toString() + " doesn't exist in the layout.");
			verticallyLinked.add(c);
		}
		
		return this;
	}
	
	public GroupLayoutHelper setInnerMargins(int left, int top, int right, int bottom)
	{
		leftMargin = left;
		topMargin = top;
		rightMargin = right;
		bottomMargin = bottom;
		
		return this;
	}
	
	public static GroupLayoutHelper fluidGenerateGroupLayout(List<? extends Component>... columns)
	{
		GroupLayoutHelper helper = new GroupLayoutHelper();
		
		for(List<? extends Component> column : columns)
			helper.addColumn(column);
		
		return helper;
	}
}
