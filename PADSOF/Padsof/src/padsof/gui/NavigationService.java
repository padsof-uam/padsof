package padsof.gui;

import padsof.gui.views.View;

/**
 * The interface represeting a navigation service.
 * 
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 */
public interface NavigationService
{
	/**
	 * Navigates to a given page.
	 * @param to Page to navigate.
	 */
	public <V extends View> void navigate(Class<V> to);
	
	/**
	 * Navigates back to the last page.
	 */
	public void goBack();
}
