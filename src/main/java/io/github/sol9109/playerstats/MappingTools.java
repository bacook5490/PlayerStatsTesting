package io.github.sol9109.playerstats;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

public class MappingTools {

	/**
	 * Maps data from an object to a map.
	 * @param obj The object being used to map
	 * @return Returns a map based on fields and values in the passed object
	 */
	public static <T> Map<String, Object> mapFromObject(T obj) {
		Map<String, Object> map = new HashMap<>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try { map.put(field.getName(), field.get(obj)); } catch (Exception e) {}
		}
		return map;
	}
	
	/**
	 * Maps data from a map to an object.
	 * @param obj The object that the data should be mapped to
	 * @param map The map being used to map
	 */
	public static <T> T mapToObject(T obj, Map<String, Object> map) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try { field.set(obj, map.get(field.getName())); } catch (Exception e) { }
		}
		return obj;
	}
	
	/**
	 * Maps data from a yml file to an object. 
	 * @param identifier The unique identifier used to locate data in the yml file
	 * @param obj The object that the data should be mapped to
	 * @param fileConfig The config containing information about the file being read
	 * @return Returns the passed object filled with data from the yml file
	 */
	public static <T> T mapFromFile(String identifier, T obj, FileConfiguration fileConfig) {
		// Reads data from a yml file based on the passed in identifier
		Map<String, Object> map = null;
		try { map = fileConfig.getConfigurationSection(identifier).getValues(false); } catch (Exception e) { }
		// Maps the retreived data to the passed object
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try { field.set(obj, map.get(field.getName())); } catch (Exception e) { }
		}
		return obj;
	}
}
