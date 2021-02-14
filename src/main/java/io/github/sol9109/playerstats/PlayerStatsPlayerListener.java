package io.github.sol9109.playerstats;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerStatsPlayerListener implements Listener {

	private final PlayerStats plugin;
	public PlayerStatsPlayerListener(PlayerStats plugin) { this.plugin = plugin; }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), plugin.playerDB));
		Player player = (Player)event.getPlayer();
		
		// Attempts to read the player's stored data and place it into a PlayerData object
		PlayerData playerData = new PlayerData();
		Map<String, Object> map = null;
		try { map = fileConfig.getConfigurationSection(player.getUniqueId().toString()).getValues(false); } catch (Exception e) { }
		playerData.setMap(map);
		plugin.getLogger().info(player.getUniqueId() + "\n" + playerData.toString());
		
		// Places player's stored data into player metadata
		for (String key : map.keySet()) { 
			player.setMetadata(key, new FixedMetadataValue(plugin, map.get(key))); 
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), plugin.playerDB));
		Player player = (Player)event.getPlayer();
		
		// Retrieves player meta data and stores it into PlayerData object
		PlayerData playerData = new PlayerData();
		Map<String, Object> map = playerData.getMap();
		for (String key : map.keySet()) {
			List<MetadataValue> values = player.getMetadata(key);
			if (!values.isEmpty()) map.put(key, values.get(0).value());
		}
		playerData.setMap(map);
		
		// Write player meta data to the players yml file
		try { fileConfig.createSection(player.getUniqueId().toString(), playerData.getMap()); } catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save player data for uuid: " + player.getUniqueId());
		}
		
		// Save changes made to the players yml file
		try { fileConfig.save(new File(plugin.getDataFolder(), plugin.playerDB)); } catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Failed to save file " + plugin.playerDB);
		}
	}
}
