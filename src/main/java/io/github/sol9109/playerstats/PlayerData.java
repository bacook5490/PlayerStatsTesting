package io.github.sol9109.playerstats;

public class PlayerData {

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
	
	@Override
	public String toString() {
		return "class: " + combatClass + "\nlevel: " + level + "\nexperience: " + experience + "\nmoney: " + money + "\nstr: " + str + "\ndex: " + dex;
	}
}
