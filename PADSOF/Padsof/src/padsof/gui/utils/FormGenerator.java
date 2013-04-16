package padsof.gui.utils;

import java.awt.*;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class FormGenerator
{
	private List<String> fields = new ArrayList<String>();
	private boolean isInnerPanel = true;
	private String title = null;
	private List<JButton> buttons = new ArrayList<JButton>();
	
	public FormGenerator addFields(String...fields)
	{
		for(String field: fields)
			this.fields.add(field);
		
		return this;
	}
	
	public FormGenerator setIsInnerPanel(boolean inner)
	{
		isInnerPanel = inner;
		
		return this;
	}
	
	public FormGenerator setTitle(String title)
	{
		this.title = title;
		
		return this;
	}
	
	public FormGenerator addButton(JButton button)
	{
		this.buttons.add(button);
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public JPanel generateForm() throws NoSuchObjectException
	{
		GroupLayoutHelper layoutHelper = new GroupLayoutHelper();
		
		layoutHelper.setIsInnerPanel(isInnerPanel);
		
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		ArrayList<JTextField> textFields = new ArrayList<JTextField>();
		
		for(String field: fields)
		{
			JLabel label = new JLabel(field + ": ");
			JTextField textField = new JTextField();
			textField.setMinimumSize(new Dimension(200, 18));
			label.setLabelFor(textField);
			
			labels.add(label);
			textFields.add(textField);
		}
		
		layoutHelper.addColumn(labels);
		layoutHelper.addColumn(textFields);
		
		layoutHelper.linkVerticalSize(textFields);
		
		JPanel panel;
		
		if(title != null || buttons.size() > 0)
		{
			List<Component> column = new ArrayList<Component>();
			
			if(title != null)
				column.add(generateTitleLabel());
			
			column.add(layoutHelper.setIsInnerPanel(true).generatePanel());
			
			if(buttons.size() > 0)
				column.add(generateButtonPanel());
			
			panel = GroupLayoutHelper.fluidGenerateGroupLayout(column).generatePanel();
		}
		else
		{
			panel = layoutHelper.generatePanel();
		}
		
		return panel;
	}
	
	private JPanel generateButtonPanel()
	{
		JPanel container = new JPanel();
		container.setLayout(new FlowLayout());
		
		for(JButton button: buttons)
			container.add(button);
		
		return container;
	}

	private JLabel generateTitleLabel()
	{
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(titleLabel.getFont().deriveFont((float) 20.0));
		
		return titleLabel;
	}
}
