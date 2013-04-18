package padsof.gui.utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class FormGenerator
{
	private List<String> fields = new ArrayList<String>();
	private List<Component> components = new ArrayList<Component>();
	private boolean isInnerPanel = true;
	private String title = null;
	private List<JButton> buttons = new ArrayList<JButton>();
	private int leftMargin = 0;
	private int topMargin = 0;
	private int rightMargin = 0;
	private int bottomMargin = 0;
	
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
	
	public FormGenerator setInnerMargins(int left, int top, int right, int bottom)
	{
		leftMargin = left;
		topMargin = top;
		rightMargin = right;
		bottomMargin = bottom;
	
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
	public JPanel generateForm()
	{
		GroupLayoutHelper layoutHelper = new GroupLayoutHelper();
		
		layoutHelper.setInnerMargins(leftMargin, topMargin, rightMargin, bottomMargin);
		layoutHelper.setIsInnerPanel(isInnerPanel);
		
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		components.clear();
		
		for(String field: fields)
		{
			JLabel label = new JLabel(field + ": ");
			
			Component comp = generateComponentFor(field);
			label.setLabelFor(comp);
	
			labels.add(label);
			components.add(comp);
		}
		
		layoutHelper.addColumn(labels);
		layoutHelper.addColumn(components);
		
		layoutHelper.linkVerticalSize(components);
		
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
	
	private Component generateComponentFor(String field)
	{
		if(isPasswordField(field))
			return new JPasswordField();
		else if (isDateField(field))
			return new JDateChooser();
		else
			return new JTextField();
	}
	
	private boolean isPasswordField(String field)
	{
		return field.matches("[Cc]ontraseña") || field.matches("[Pp]assword");
	}
	
	private boolean isDateField(String field)
	{
		return field.indexOf("Fecha") != -1 || field.indexOf("fecha") != -1;
	}
	
	public String getValueFor(String field) 
	{
		int index = fields.indexOf(field);
		
		if(index == -1)
			throw new ComponentNotFoundException("Component not found", field);
		
		if(isPasswordField(field))
		{
			JPasswordField p = (JPasswordField) components.get(index);
			return new String(p.getPassword()); // Security issues if we let the password around without clearing it after use... well.
		}
		else if(isDateField(field))
		{
			JDateChooser dc = (JDateChooser) components.get(index);
			return dc.getDate().toString();
		}
		else
		{
			JTextField tf = (JTextField) components.get(index);
			return tf.getText();
		}		
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
