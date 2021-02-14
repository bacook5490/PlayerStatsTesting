package io.github.sol9109.playerstats;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

	// TODO Create base class so any class be be mapped to and from yml files
	
	private String combatClass;
	private int level;
	private int experience;
	private int money;
	private int str;
	private int dex;
	
	// Constructors
	public PlayerData() {
		combatClass = "Adventurer";
	}
	
	// Getters
	
	// Setters
	public void setCombatClass(String combatClass) { this.combatClass = combatClass; }
	public void setLevel(int level) { this.level = level; }
	public void setExperience(int experience) { this.experience = experience; }
	public void setMoney(int money) { this.money = money; }
	public void setStr(int str) { this.str = str; }
	public void setDex(int dex) { this.dex = dex; }
	
	// Returns a map based on class field data
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		for (Field field : PlayerData.class.getDeclaredFields()) {
			field.setAccessible(true);
			try { map.put(field.getName(), field.get(this)); } catch (Exception e) {}
		}
		return map;
	}
	
	// Sets class fields based on data in a map
	public void setMap(Map<String, Object> map) {
		if (map == null || map.isEmpty()) return;
		for (Field field : PlayerData.class.getDeclaredFields()) {
			field.setAccessible(true);
			try { field.set(this, map.get(field.getName())); } catch (Exception e) {}
		}
	}
	
	@Override
	public String toString() {
		return "class: " + combatClass + "\nlevel: " + level + "\nexperience: " + experience + "\nmoney: " + money + "\nstr: " + str + "\ndex: " + dex;
	}
}
