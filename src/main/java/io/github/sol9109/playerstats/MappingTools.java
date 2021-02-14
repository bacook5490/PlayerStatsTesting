package io.github.sol9109.playerstats;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MappingTools {

	// Returns a map based on class field data
	public static Map<String, Object> getMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try { map.put(field.getName(), field.get(obj)); } catch (Exception e) {}
		}
		return map;
	}
	
	// Sets class fields based on data in a map
	public static void setMap(Object obj, Map<String, Object> map) {
		if (map == null || map.isEmpty()) return;
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try { field.set(obj, map.get(field.getName())); } catch (Exception e) {}
		}
	}
}
