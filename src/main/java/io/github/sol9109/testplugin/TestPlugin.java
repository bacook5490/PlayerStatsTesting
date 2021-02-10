package io.github.sol9109.testplugin;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin{

	// Listener Declarations
	private final TestPluginPlayerListener playerListener = new TestPluginPlayerListener(this); 
	
	@Override
	public void onEnable() {
		createConfigFile("config.yml");
		createConfigFile("players.yml");
		registerEvents();
		registerCommands();
	}
	 
	@Override
    public void onDisable() {
		// TODO save player data on shutdown
    }
	
	// Registers Events
	private void registerEvents() {
		getLogger().info("Event Registration Started.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener,  this);
		getLogger().info("Event Registration Finished.");
	}
	
	// Registers Commands
	private void registerCommands() {
		getLogger().info("Command Registration Started.");
		getCommand("dsetattribute").setExecutor(new TestPluginCommandExecutor(this));
		getCommand("showcharactersheet").setExecutor(new TestPluginCommandExecutor(this));
		getLogger().info("Command Registration Finished.");
	}
	
	// Attempts to create a config file with the given filename if one cannot be found
	private void createConfigFile(String filename) {
		File file = new File(getDataFolder(), filename);
		if (!file.exists()) {
			getLogger().info("Creating " + filename + ".");
			YamlConfiguration.loadConfiguration(file);
		} else { 
			getLogger().info(filename + " found."); 
		}
	}

	// Returns a list of all attributes that the player has
	public ArrayList<String> getAttributes() {
		ArrayList<String> list = new ArrayList<>();
		list.add("level");
		list.add("exp");
		list.add("money");
		list.add("str");
		list.add("dex");
		list.add("int");
		list.add("wis");
		list.add("cha");
		list.add("def");
		return list;
	}
}
