package com.sg.cdf.core.persistence;

import java.lang.reflect.Type;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sg.cdf.core.CDF;

class JsonClassAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

	private static final String BUNDLE = "bundle";

	private static final String CLASS = "class";

	@Override
	public T deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonElement jClass = ((JsonObject) json).get(CLASS);
		String className = jClass.getAsString();
		JsonElement jBundle = ((JsonObject) json).get(BUNDLE);
		String bundleName = jBundle.getAsString();

		try {
			Class<?> clas = CDF.getBundleLoadedClass(bundleName, className);
			((JsonObject) json).remove(CLASS);
			((JsonObject) json).remove(BUNDLE);
			return context.deserialize(json, clas);
		} catch (Exception e) {
			throw new JsonParseException("Class has not registe in CDF. "
					+ e.getMessage());
		}
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonObject = context.serialize(src).getAsJsonObject();
		String name = src.getClass().getName();
		ClassLoader classLoader = src.getClass().getClassLoader();
		if (classLoader instanceof BundleReference) {
			Bundle bundle = ((BundleReference) classLoader).getBundle();
			jsonObject.add(BUNDLE, new JsonPrimitive(bundle.getSymbolicName()));
		}
		jsonObject.add(CLASS, new JsonPrimitive(name));
		return jsonObject;
	}

}
