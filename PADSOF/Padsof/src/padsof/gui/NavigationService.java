package padsof.gui;

import padsof.gui.views.View;

public interface NavigationService
{
	public void navigate(Class<? extends View> to);
}
