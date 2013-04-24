package padsof.gui.utils;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;

/**
 * A helper class to generate complex layouts using GroupLayout as a base.
 * This class can be used with fluent syntax, with the static method
 * fluidGenerateGroupLayout being the starting point.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public class GroupLayoutHelper
{
	private List<Component> horizontallyLinked = new ArrayList<Component>();
	private List<Component> verticallyLinked = new ArrayList<Component>();
	private List<List<? extends Component>> columns = new ArrayList<List<? extends Component>>();
	private int leftMargin = 0;
	private int rightMargin = 0;
	private int topMargin = 0;
	private int bottomMargin = 0;
	private boolean innerPanel = false;

	/**
	 * Adds a column represented as a list of components.
	 * 
	 * @param column
	 *            List of components.
	 */
	public void addColumn(List<? extends Component> column)
	{
		columns.add(column);
	}

	/**
	 * Adds a column represented as a list of components.
	 * 
	 * @param components
	 *            Varying arguments of the components.
	 */
	public void addColumn(Component... components)
	{
		List<Component> column = new ArrayList<Component>();

		for (Component c : components)
			column.add(c);

		addColumn(column);
	}

	/**
	 * Generates the GroupLayout based on the columns given and referring to a
	 * given container.
	 * 
	 * @param container
	 *            Container
	 * @return GroupLayout
	 */
	public GroupLayout generateLayout(Container container)
	{
		GroupLayout layout = new GroupLayout(container);

		layout.setAutoCreateContainerGaps(!innerPanel);
		layout.setAutoCreateGaps(true);

		Group horizontal = generateHorizontalGroup(layout);
		Group vertical = generateVerticalGroup(layout);

		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);

		if (horizontallyLinked.size() > 0)
			layout.linkSize(SwingConstants.HORIZONTAL, horizontallyLinked
					.toArray(new Component[horizontallyLinked.size()]));

		if (verticallyLinked.size() > 0)
			layout.linkSize(SwingConstants.VERTICAL, verticallyLinked
					.toArray(new Component[verticallyLinked.size()]));

		return layout;
	}

	private Group generateVerticalGroup(GroupLayout layout)
	{
		Group vertical = layout.createSequentialGroup();

		if (topMargin > 0)
			vertical.addGap(topMargin);

		int maxColumnHeight = findMaxColumnHeight();

		for (int i = 0; i < maxColumnHeight; i++)
		{
			ParallelGroup rowGroup = layout
					.createParallelGroup(GroupLayout.Alignment.LEADING);

			for (List<? extends Component> column : columns)
			{
				if (i >= column.size())
					rowGroup.addComponent(Box.createGlue());
				else
					rowGroup.addComponent(column.get(i));
			}

			vertical.addGroup(rowGroup);
		}

		if (bottomMargin > 0)
			vertical.addGap(bottomMargin);

		return vertical;
	}

	private Group generateHorizontalGroup(GroupLayout layout)
	{
		Group horizontal = layout.createSequentialGroup();

		if (leftMargin > 0)
			horizontal.addGap(leftMargin);

		int maxColumnHeight = findMaxColumnHeight();

		for (List<? extends Component> column : columns)
		{
			ParallelGroup columnGroup = layout
					.createParallelGroup(GroupLayout.Alignment.LEADING);
			int i;

			for (i = 0; i < column.size(); i++)
				columnGroup.addComponent(column.get(i));

			for (; i < maxColumnHeight; i++)
				columnGroup.addComponent(Box.createGlue());

			horizontal.addGroup(columnGroup);
		}

		if (rightMargin > 0)
			horizontal.addGap(rightMargin);

		return horizontal;
	}

	private int findMaxColumnHeight()
	{
		int max = -1;

		for (List<? extends Component> column : columns)
			if (column.size() > max)
				max = column.size();

		return max;
	}

	/**
	 * Generates the panel with the defined layout.
	 * 
	 * @return Panel.
	 */
	public JPanel generatePanel()
	{
		JPanel panel = new JPanel();
		GroupLayout layout = generateLayout(panel);

		panel.setLayout(layout);

		return panel;
	}

	/**
	 * Generates a panel containing the layout centered.
	 * 
	 * @return Centered JPanel.
	 */
	public JPanel generateCenteredPanel()
	{
		JPanel container = new JPanel();
		container.setLayout(new FlowLayout());

		container.add(generatePanel());

		return container;
	}

	private boolean componentExists(Component c)
	{
		for (List<? extends Component> column : columns)
			if (column.contains(c))
				return true;

		return false;
	}

	/**
	 * Links the horizontal sizes of the given components.
	 * 
	 * @param components
	 *            Components
	 * @return The instance.
	 * @throws ComponentNotFoundException
	 *             if any of the components isn't added in the layout.
	 */
	public GroupLayoutHelper linkHorizontalSize(Component... components)
			throws ComponentNotFoundException
	{
		for (Component c : components)
		{
			if (!componentExists(c))
				throw new ComponentNotFoundException(
						"Add all components before linking horizontal size.",
						c.toString());
			horizontallyLinked.add(c);
		}

		return this;
	}

	/**
	 * Links the vertical sizes of the given components.
	 * 
	 * @param components
	 *            Components
	 * @return The instance.
	 * @throws ComponentNotFoundException
	 *             if any of the components isn't added in the layout.
	 */
	public GroupLayoutHelper linkVerticalSize(Component... components)
			throws ComponentNotFoundException
	{
		for (Component c : components)
		{
			if (!componentExists(c))
				throw new ComponentNotFoundException(
						"Add all components before linking vertical size.",
						c.toString());
			verticallyLinked.add(c);
		}

		return this;
	}

	/**
	 * Links the horizontal sizes of the given components.
	 * 
	 * @param components
	 *            Components
	 * @return The instance.
	 * @throws ComponentNotFoundException
	 *             if any of the components isn't added in the layout.
	 */
	public GroupLayoutHelper linkHorizontalSize(
			List<? extends Component> components)
			throws ComponentNotFoundException
	{
		return linkHorizontalSize(components.toArray(new Component[components
				.size()]));
	}

	/**
	 * Links the vertical sizes of the given components.
	 * 
	 * @param components
	 *            Components
	 * @return The instance.
	 * @throws ComponentNotFoundException
	 *             if any of the components isn't added in the layout.
	 */
	public GroupLayoutHelper linkVerticalSize(
			List<? extends Component> components)
			throws ComponentNotFoundException
	{
		return linkVerticalSize(components.toArray(new Component[components
				.size()]));
	}

	/**
	 * Use this method to remove inner margins from this panel, so it can be
	 * used as a nested panel without adding extra indent levels.
	 * 
	 * @param innerPanel
	 * @return The instance.
	 */
	public GroupLayoutHelper setIsInnerPanel(boolean innerPanel)
	{
		this.innerPanel = innerPanel;

		return this;
	}

	/**
	 * Sets the inner margins of the layout.
	 * 
	 * @param left
	 *            Left margin.
	 * @param top
	 *            Top margin.
	 * @param right
	 *            Right margin.
	 * @param bottom
	 *            Bottom margin
	 * @return The instance.
	 */
	public GroupLayoutHelper setInnerMargins(int left, int top, int right,
			int bottom)
	{
		leftMargin = left;
		topMargin = top;
		rightMargin = right;
		bottomMargin = bottom;

		return this;
	}
	
	public void setAsLayoutOf(Container container)
	{
		container.setLayout(this.generateLayout(container));
	}

	/**
	 * Generates a new instance of GroupLayoutHelper with a list of lists, each
	 * one representing a column. This allows generating the layout using fluent
	 * syntax.
	 * 
	 * @param columns
	 *            Varying arguments, each one being a list of components
	 *            representing a column.
	 * @return GroupLayoutHelper.
	 */
	public static GroupLayoutHelper fluidGenerateGroupLayout(
			List<? extends Component>... columns)
	{
		GroupLayoutHelper helper = new GroupLayoutHelper();

		for (List<? extends Component> column : columns)
			helper.addColumn(column);

		return helper;
	}
}
