package padsof.gui.controllers.utils;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener
{
	public String value() default "__SINGLE__";
}
