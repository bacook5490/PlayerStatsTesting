package io.github.sol9109.playerstats;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerStats extends JavaPlugin{

	// Listener Declarations
	private final PlayerStatsPlayerListener playerListener = new PlayerStatsPlayerListener(this); 
	
	// Filename Declarations
	public final String configDB = "config.yml";
	public final String playerDB = "players.yml";
	
	@Override
	public void onEnable() {
		createConfigFile(configDB);
		createConfigFile(playerDB);
		registerEvents();
		registerCommands();
	}
	 
	@Override
    public void onDisable() {
		// TODO save player data on shutdown
		// TODO save player data at intervals
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
		getCommand("resetstats").setExecutor(new PlayerStatsCommandExecutor(this));
		getCommand("changestats").setExecutor(new PlayerStatsCommandExecutor(this));
		getCommand("showstats").setExecutor(new PlayerStatsCommandExecutor(this));
		getLogger().info("Command Registration Finished.");
	}
	
	// Attempts to create a config file with the given filename if one cannot be found
	private void createConfigFile(String filename) {
		File file = new File(getDataFolder(), filename);
		if (!file.exists()) {
			getLogger().info("Creating " + filename);
			YamlConfiguration.loadConfiguration(file);
		}
	}
}
