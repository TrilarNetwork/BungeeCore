package me.imelvin.bungeecore;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager {
	boolean isSetup = false;
	static ConfigManager instance = new ConfigManager();
	File cfile;
	Configuration config;
	YamlConfiguration prov = (YamlConfiguration) ConfigurationProvider.getProvider(YamlConfiguration.class);
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdirs();
		}
		
		cfile = new File(p.getDataFolder(), "config.yml");
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				ProxyServer.getInstance().getLogger().info("Trilar Core >> Could not create config.yml");
			}
		}
		try {
			config = prov.load(new File(p.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();	
		}
		if (this.config.get("settings.talkInStaffChat") == null) {
			this.config.set("settings.talkInStaffChat", "!");
		}
		if (this.config.get("settings.banMessage") == null) {
			this.config.set("settings.banMessage", "&cYou cannot join as you have been banned for: \n%reason%");
		}
		if (this.config.get("settings.kickOnWarn") == null) {
			this.config.set("settings.kickOnWarn", true);
		}
		if (this.config.get("settings.database.hostname") == null) {
			this.config.set("settings.database.hostname", "tropicalhost.nl");
		}
		if (this.config.get("settings.database.port") == null) {
			this.config.set("settings.database.port", 3306);
		}
		if (this.config.get("settings.database.database") == null) {
			this.config.set("settings.database.database", "mininetw_tbungee");
		}
		if (this.config.get("settings.database.username") == null) {
			this.config.set("settings.database.username", "mininetw_tbungee");
		}
		if (this.config.get("settings.database.password") == null) {
			this.config.set("settings.database.password", "Trilar2018");
		}
		saveConfig();
		isSetup = true;
	}
	
	
	public void saveConfig() {
		try {
			prov.save(config, cfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Configuration getConfig() {
		return this.config;
	}
	
	public void reloadConfig() {
		try {
			config = prov.load(cfile);
			Main.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
