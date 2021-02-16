package io.github.sol9109.playerstats;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class YMLTools {

	/**
	 * Writes data to a yml file.
	 * @param identifier The unique identifier used to locate data in the yml file
	 * @param obj The object whose data should be stored
	 * @param fileConfig The config containing information about the file being read
	 * @param plugin The reference to the plugin that this data is associated with
	 */
	public static void WriteData(String identifier, Object obj, FileConfiguration fileConfig, Plugin plugin) {
		try { fileConfig.createSection(identifier, MappingTools.mapFromObject(obj)); } catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save data for: " + identifier);
		}
	}
	
	/**
	 * Saves changes made to a yml file.
	 * @param filename The name of the file being saved
	 * @param fileConfig The config containing information about the file being read
	 * @param plugin The reference to the plugin that this data is associated with
	 */
	public static void SaveData(String filename, FileConfiguration fileConfig, Plugin plugin) {
		try { fileConfig.save(new File(plugin.getDataFolder(), filename)); } catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save file " + filename + " for plugin " + plugin.getName());
		}
	}
}
