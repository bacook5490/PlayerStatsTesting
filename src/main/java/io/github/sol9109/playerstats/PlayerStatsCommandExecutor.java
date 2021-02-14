package io.github.sol9109.playerstats;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class PlayerStatsCommandExecutor implements CommandExecutor {

	private final PlayerStats plugin;
	
	public PlayerStatsCommandExecutor(PlayerStats plugin) { this.plugin = plugin; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(command.getName().toLowerCase()) {
			case "resetstats": {
				if (args.length != 1) {
					sender.sendMessage("Inavlid number of arguments!");
					return false;
				}
				
				Player player = sender.getServer().getPlayer(args[0]);
				
				if (player == null) {
					sender.sendMessage("Player must be online!");
					return false;
				}
				
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), plugin.playerDB));
				PlayerData playerData = new PlayerData();
				Map<String, Object> map = MappingTools.getMap(playerData);
				
				// Places player's stored data into player metadata
				for (String key : map.keySet()) { 
					player.setMetadata(key, new FixedMetadataValue(plugin, map.get(key))); 
				}
				
				// Write player meta data to the players yml file
				try { fileConfig.createSection(player.getUniqueId().toString(), MappingTools.getMap(playerData)); } catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "Failed to save player data for uuid: " + player.getUniqueId());
					return false;
				}
				
				// Save changes made to the players yml file
				try { fileConfig.save(new File(plugin.getDataFolder(), plugin.playerDB)); } catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE, "Failed to save file " + plugin.playerDB);
					return false;
				}
				
				sender.sendMessage(player.getDisplayName() + "\n" + playerData.toString());
				
				return true;
			}
			case "changestats": {
				if (args.length != 7) {
					sender.sendMessage("Inavlid number of arguments!");
					return false;
				}
				
				Player player = sender.getServer().getPlayer(args[0]);
				
				if (player == null) {
					sender.sendMessage("Player must be online!");
					return false;
				}
				
				// Retrieves player meta data and stores it into PlayerData object
				FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), plugin.playerDB));
				PlayerData playerData = new PlayerData();
				Map<String, Object> map = MappingTools.getMap(playerData);
				for (String key : map.keySet()) {
					List<MetadataValue> values = player.getMetadata(key);
					if (!values.isEmpty()) map.put(key, values.get(0).value());
				}
				MappingTools.setMap(playerData, map);
				
				playerData.setCombatClass(args[1]);
				playerData.setLevel(Integer.parseInt(args[2]));
				playerData.setExperience(Integer.parseInt(args[3]));
				playerData.setMoney(Integer.parseInt(args[4]));
				playerData.setStr(Integer.parseInt(args[5]));
				playerData.setDex(Integer.parseInt(args[6]));
				
				map = MappingTools.getMap(playerData);
				
				// Places player's stored data into player metadata
				for (String key : map.keySet()) { 
					player.setMetadata(key, new FixedMetadataValue(plugin, map.get(key))); 
				}
				
				// Write player meta data to the players yml file
				try { fileConfig.createSection(player.getUniqueId().toString(), MappingTools.getMap(playerData)); } catch (Exception e) {
					plugin.getLogger().log(Level.SEVERE, "Failed to save player data for uuid: " + player.getUniqueId());
					return false;
				}
				
				// Save changes made to the players yml file
				try { fileConfig.save(new File(plugin.getDataFolder(), plugin.playerDB)); } catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE, "Failed to save file " + plugin.playerDB);
					return false;
				}
				
				return true;
			}
			case "showstats": {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Command can only be ran by a player!");
					return false;
				}
				
				// Retrieves player meta data and stores it into PlayerData object
				Player player = (Player)sender;
				PlayerData playerData = new PlayerData();
				Map<String, Object> map = MappingTools.getMap(playerData);
				for (String key : map.keySet()) {
					List<MetadataValue> values = player.getMetadata(key);
					if (!values.isEmpty()) map.put(key, values.get(0).value());
				}
				MappingTools.setMap(playerData, map);
				
				sender.sendMessage(player.getDisplayName() + "\n" + playerData.toString());
				return true;
			}
		}
		return false;
	}
	
}
