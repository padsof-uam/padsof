package padsof.gui.utils;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import padsof.gui.Application;

import com.toedter.calendar.JDateChooser;

/**
 * Generate a form automatically using label names.
 * This class supports fluent/fluid syntax, so most methods
 * return the instance itself.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class FormGenerator
{
	private List<String> fields = new ArrayList<String>();
	private List<Component> components = new ArrayList<Component>();
	private boolean isInnerPanel = true;
	private String title = null;
	private List<JButton> buttons = new ArrayList<JButton>();
	private Map<String, List<? extends Object>> options = new HashMap<String, List<? extends Object>>();
	private int leftMargin = 0;
	private int topMargin = 0;
	private int rightMargin = 0;
	private int bottomMargin = 0;
	private int fieldWidth = 20;
	private JButton firstButton;

	/**
	 * Add fields (label names) to the generator.
	 * 
	 * @param fields
	 *            Varying number of fields.
	 * @return The instance.
	 */
	public FormGenerator addFields(String... fields)
	{
		for (String field : fields)
			this.fields.add(field);

		return this;
	}

	public FormGenerator addOptionField(String field, List<? extends Object> options)
	{
		this.fields.add(field);

		this.options.put(field, options);

		return this;
	}

	/**
	 * Sets if the panel will be used as an inner panel.
	 * 
	 * @param inner
	 *            False if this is a standalone panel.
	 * @return The instance.
	 */
	public FormGenerator setIsInnerPanel(boolean inner)
	{
		isInnerPanel = inner;

		return this;
	}

	/**
	 * Sets the innter margins of the panel.
	 * 
	 * @param left
	 *            Left margin
	 * @param top
	 *            Top margin
	 * @param right
	 *            Right margin
	 * @param bottom
	 *            Bottom margin
	 * @return The instance
	 */
	public FormGenerator setInnerMargins(int left, int top, int right,
			int bottom)
	{
		leftMargin = left;
		topMargin = top;
		rightMargin = right;
		bottomMargin = bottom;

		return this;
	}

	/**
	 * Set the title of the form.
	 * 
	 * @param title
	 *            Title
	 * @return The instance
	 */
	public FormGenerator setTitle(String title)
	{
		this.title = title;

		return this;
	}

	/**
	 * Adds a button to the last row of the form.
	 * 
	 * @param button
	 *            Button.
	 * @return The instance
	 */
	public FormGenerator addButton(JButton button)
	{
		this.buttons.add(button);

		return this;
	}

	/**
	 * Generates the form and returns a JPanel with all the fields
	 * in it.
	 * 
	 * @return The panel.
	 */
	@SuppressWarnings("unchecked")
	public JPanel generateForm()
	{
		GroupLayoutHelper layoutHelper = new GroupLayoutHelper();

		layoutHelper.setInnerMargins(leftMargin, topMargin, rightMargin,
				bottomMargin);
		layoutHelper.setIsInnerPanel(isInnerPanel);

		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		components.clear();
		
		FocusListener focusListener = new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(firstButton != null)
					Application.getInstance().setDefaultButton(firstButton);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				Application.getInstance().setDefaultButton(null);
			}
			
		};
		
		for (String field : fields)
		{
			JLabel label = new JLabel(field + ": ");

			Component comp = generateComponentFor(field);
			label.setLabelFor(comp);
			comp.addFocusListener(focusListener);

			Dimension size = comp.getSize();
			comp.setMinimumSize(new Dimension(
					fieldWidth > 0 ? fieldWidth : 30, 
					size.height > 0 ? size.height : 12));
			
			labels.add(label);
			components.add(comp);
		}

		layoutHelper.addColumn(labels);
		layoutHelper.addColumn(components);

		layoutHelper.linkVerticalSize(components);
		
		JPanel panel;

		if (title != null || buttons.size() > 0)
		{
			List<Component> column = new ArrayList<Component>();

			if (title != null)
				column.add(generateTitleLabel());

			column.add(layoutHelper.setIsInnerPanel(true).generatePanel());

			if (buttons.size() > 0)
			{
				firstButton = buttons.get(0);
				column.add(generateButtonPanel());
			}

			panel = GroupLayoutHelper.fluidGenerateGroupLayout(column)
					.generatePanel();
		}
		else
		{
			panel = layoutHelper.generatePanel();
		}

		return panel;
	}

	private Component generateComponentFor(String field)
	{
		if (isPasswordField(field))
			return new JPasswordField();
		else if (isDateField(field))
			return generateDateField();
		else if (isOptionField(field))
			return generateOptionField(field);
		else
			return new JTextField();
	}
	
	/**
	 * Returns the JDateChooser of a date field. Throws ComponentNotFoundException
	 * if the field doesn't exist or isn't a date field.
	 * 
	 * @param field
	 *            Field name
	 * @return JDateChooser Date chooser.
	 * @throws ComponentNotFoundException
	 *             if the field doesn't exists or isn't a date field.
	 */
	public JDateChooser getChooserField(String field)
	{
		int index = fields.indexOf(field);

		if (index == -1)
			throw new ComponentNotFoundException("Component not found", field);

		if (!isDateField(field))
			throw new ComponentNotFoundException("That's not a date element",
					field);

		return (JDateChooser) components.get(index);
	}

	private Component generateDateField()
	{
		JDateChooser chooser = new JDateChooser();
		chooser.setDate(Calendar.getInstance().getTime());
		return chooser;
	}

	private boolean isOptionField(String field)
	{
		return options.containsKey(field);
	}

	private Component generateOptionField(String field)
	{
		JComboBox<Object> comboBox = new JComboBox<Object>();
		
		List<? extends Object> options = this.options.get(field);
		
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>();
		
		if(options != null)
			for(Object option: options)
				model.addElement(option);
		
		comboBox.setModel(model);
		return comboBox;
	}

	public void setOptionsModel(String field, List<? extends Object> options)
	{
		int index = this.fields.indexOf(field);
		
		if(index == -1 || !isOptionField(field))
			throw new ComponentNotFoundException("Campo no encontrado, o no es un campo de excepciones", field);
		
		@SuppressWarnings("unchecked")
		JComboBox<Object> comboBox = (JComboBox<Object>) this.components.get(index);
		
		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<Object>();
		
		for(Object option: options)
			model.addElement(option);
		
		comboBox.setModel(model);
	}

	private boolean isPasswordField(String field)
	{
		return field.matches("[Cc]ontraseña") || field.matches("[Pp]assword");
	}

	private boolean isDateField(String field)
	{
		return field.indexOf("Fecha") != -1 || field.indexOf("fecha") != -1;
	}

	/**
	 * Returns the string value of a given field. Throws
	 * ComponentNotFoundException if that field doesn't exist.
	 * 
	 * @param field
	 *            Field name
	 * @return String value
	 * @throws ComponentNotFoundException
	 *             if the field is not found.
	 */
	public String getValueFor(String field)
	{
		int index = fields.indexOf(field);

		if (index == -1)
			throw new ComponentNotFoundException("Component not found", field);

		if (isPasswordField(field))
		{
			JPasswordField p = (JPasswordField) components.get(index);
			return new String(p.getPassword()); // Security issues if we let the
												// password around without
												// clearing it after use...
												// well.
		}
		else if (isDateField(field))
		{
			JDateChooser dc = (JDateChooser) components.get(index);
			return dc.getDate().toString();
		}
		else if (isOptionField(field))
		{
			JComboBox<?> box = (JComboBox<?>) components.get(index);
			if(box.getSelectedItem() == null)
				return "";
			else
				return box.getSelectedItem().toString();
		}
		else
		{
			JTextField tf = (JTextField) components.get(index);
			return tf.getText();
		}
	}

	/**
	 * Gets the selected option of a given field. Throws
	 * ComponentNotFoundException
	 * if the field doesn't exist or isn't an option field.
	 * 
	 * @param field
	 *            Field name
	 * @return Selected item.
	 */
	public Object getSelectedOption(String field)
	{
		int index = fields.indexOf(field);

		if (index == -1)
			throw new ComponentNotFoundException("Component not found", field);

		if (!isOptionField(field))
			throw new ComponentNotFoundException("That's not a field element",
					field);

		JComboBox<?> box = (JComboBox<?>) components.get(index);
		return box.getSelectedItem();
	}

	/**
	 * Returns the Date value of a date field. Throws ComponentNotFoundException
	 * if the field doesn't exist or isn't a date field.
	 * 
	 * @param field
	 *            Field name
	 * @return Date value
	 * @throws ComponentNotFoundException
	 *             if the field doesn't exists or isn't a date field.
	 */
	public Date getDateFor(String field)
	{
		JDateChooser dc = getChooserField(field);
		
		return dc.getDate();
	}

	private JPanel generateButtonPanel()
	{
		JPanel container = new JPanel();
		container.setLayout(new FlowLayout());

		for (JButton button : buttons)
			container.add(button);

		return container;
	}

	private JLabel generateTitleLabel()
	{
		JLabel titleLabel = new JLabel(title);
		GuiUtils.applyTitleStyle(titleLabel);
		
		return titleLabel;
	}

	/**
	 * Sets the minimun width of the fields.
	 * 
	 * @param width
	 *            Width in pixels.
	 */
	public void setMinimumFieldWidth(int width)
	{
		fieldWidth = width;
	}
}
