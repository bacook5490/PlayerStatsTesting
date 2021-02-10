package io.github.sol9109.playerstats;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerStatsCommandExecutor implements CommandExecutor {

	private final PlayerStats plugin;
	
	public PlayerStatsCommandExecutor(PlayerStats plugin) { this.plugin = plugin; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(command.getName().toLowerCase()) {
			case "dsetattribute": {
				if (args.length != 3) {
					sender.sendMessage("Inavlid number of arguments!");
					return false;
				}
				
				Player player = Bukkit.getServer().getPlayer(args[0]);
				if (player == null) {
					sender.sendMessage("Player not found!");
					return false;
				}
				
				String attribute = args[1].toLowerCase();
				if (!plugin.getAttributes().contains(attribute)) {
					sender.sendMessage("Invalid attribute!");
					return false;
				}
				
				int value = Integer.parseInt(args[2]);
				//TODO value validation
				
				player.setMetadata(attribute, new FixedMetadataValue(plugin, value));
				return true;
			}
			case "showcharactersheet": {
				if (args.length != 1) {
					sender.sendMessage("Inavlid number of arguments!");
					return false;
				}
				
				Player player = Bukkit.getServer().getPlayer(args[0]);
				if (player == null) {
					sender.sendMessage("Player not found!");
					return false;
				}
				
				sender.sendMessage("Character Sheet\n--------------------");
				sender.sendMessage("Character Name: " + player.getDisplayName());
				for (String item : plugin.getAttributes()) {
					sender.sendMessage(item + ": " + player.getMetadata(item).get(0).asInt());
				}
				return true;
			}
		}
		return false;
	}
	
}
