package me.yhl;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{ 
	 
public static Plugin instance;
 
	public static Plugin getPlugin() {
	return instance;
	}

	public void onEnable() {
	instance = this;
	registerEvents(this, new OnDeath());
	}
	 
	public void onDisable() {
	instance = null;
	 
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
	for (Listener listener : listeners) {
	Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
	}
	}
 
}
