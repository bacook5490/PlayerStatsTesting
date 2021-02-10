package io.github.sol9109.playerstats;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerStatsPlayerListener implements Listener {

	private final PlayerStats plugin;
	public PlayerStatsPlayerListener(PlayerStats plugin) { this.plugin = plugin; }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = (Player)event.getPlayer();
		
		// loads config file containing player data
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "players.yml"));

		// sets player attributes based on data from config file
		plugin.getLogger().info("Attributes for uuid " + player.getUniqueId());
		for (String item : plugin.getAttributes()) {
			player.setMetadata(item, new FixedMetadataValue(plugin, playerData.getInt(player.getUniqueId() + "." + item)));
			plugin.getLogger().info(item + ": " + playerData.getInt(player.getUniqueId() + "." + item));
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = (Player)event.getPlayer();
		
		// loads config file containing player data
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "players.yml"));
		
		// stores player attributes in config file based on player metadata
		plugin.getLogger().info("Attributes for uuid " + player.getUniqueId());
		for (String item : plugin.getAttributes()) {
			playerData.set(player.getUniqueId() + "." + item, player.getMetadata(item).get(0).asInt());
			plugin.getLogger().info(item + ": " + player.getMetadata(item).get(0).asInt());
		}
		
		// saves changes to config file
		try {
			playerData.save(new File(plugin.getDataFolder(), "players.yml"));
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save player data for uuid " + player.getUniqueId());
		}
	}

}
