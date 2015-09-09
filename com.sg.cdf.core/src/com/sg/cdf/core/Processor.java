package com.sg.cdf.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IStatus;

import com.sg.cdf.core.request.IDistributionJob;


public abstract class Processor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8483551832307971208L;

	public abstract IStatus run(IDistributionJob job);

	public final void setParameterValue(String parameter, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		boolean b = putFieldValue(parameter, value);
		if (!b) {
			b = invokeSet(parameter, value);
		}
	}

	private boolean invokeSet(String parameter, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method[] methods = getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equalsIgnoreCase("set"+parameter)) {
				methods[i].invoke(this, value);
				return true;
			}
		}
		return false;
	}

	private boolean putFieldValue(String parameter, Object value) {
		try {
			Field field = getClass().getField(parameter);
			field.set(this, value);
			return true;
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return false;
	}
}
