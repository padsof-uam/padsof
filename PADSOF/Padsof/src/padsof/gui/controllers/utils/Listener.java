package padsof.gui.controllers.utils;

import java.lang.annotation.*;

/**
 * Marks a method as a Listener for a command.
 * @author Víctor de Juan Sanz - Guillermo Julián Moreno
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener
{
	public String value() default "__SINGLE__";
}
