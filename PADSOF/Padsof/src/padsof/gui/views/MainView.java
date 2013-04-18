package padsof.gui.views;

import java.awt.EventQueue;
import java.rmi.NoSuchObjectException;

import javax.swing.*;

public class MainView
{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainView window = new MainView();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws NoSuchObjectException 
	 */
	public MainView() throws NoSuchObjectException
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NoSuchObjectException 
	 */
	private void initialize() throws NoSuchObjectException
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		View pene = new RegisterClientView();
		pene.show(frame);
		
	
	}
}
