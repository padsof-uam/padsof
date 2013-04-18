package padsof.gui;

import padsof.gui.views.View;

public interface NavigationService
{
	public <V extends View> void navigate(Class<V> to);
}
