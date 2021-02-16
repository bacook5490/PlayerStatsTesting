package io.github.sol9109.playerstats;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
		
		PlayerData playerData = MappingTools.mapFromFile(player.getUniqueId().toString(), new PlayerData(), fileConfig);
		plugin.getLogger().info(player.getUniqueId() + "\n" + playerData.toString());
		
		// Places player's stored data into player metadata
		Map<String, Object> map = MappingTools.mapFromObject(playerData);
		for (Field field : playerData.getClass().getDeclaredFields()) {
			player.setMetadata(field.getName(), new FixedMetadataValue(plugin, map.get(field.getName())));
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), plugin.playerDB));
		Player player = (Player)event.getPlayer();
		
		// Retrieves player meta data and stores it into PlayerData object
		PlayerData playerData = new PlayerData();
		Map<String, Object> map = MappingTools.mapFromObject(playerData);
		for (String key : map.keySet()) {
			List<MetadataValue> values = player.getMetadata(key);
			if (!values.isEmpty()) map.put(key, values.get(0).value());
		}
		playerData = MappingTools.mapToObject(playerData, map);
		
		YMLTools.WriteData(player.getUniqueId().toString(), playerData, fileConfig, plugin);
		YMLTools.SaveData(plugin.playerDB, fileConfig, plugin);
	}
}
