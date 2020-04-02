package me.imelvin.bungeecore;

import me.imelvin.bungeecore.commands.Alert;
import me.imelvin.bungeecore.commands.Maintenance;
import me.imelvin.bungeecore.commands.Msg;
import me.imelvin.bungeecore.commands.Reload;
import me.imelvin.bungeecore.commands.Reply;
import me.imelvin.bungeecore.commands.Socialspy;
import me.imelvin.bungeecore.commands.Staffchat;
import me.imelvin.bungeecore.commands.ban.Ban;
import me.imelvin.bungeecore.commands.ban.Baninfo;
import me.imelvin.bungeecore.commands.ban.Banip;
import me.imelvin.bungeecore.commands.ban.Kick;
import me.imelvin.bungeecore.commands.ban.Mute;
import me.imelvin.bungeecore.commands.ban.Tempban;
import me.imelvin.bungeecore.commands.ban.Unban;
import me.imelvin.bungeecore.commands.ban.Unbanip;
import me.imelvin.bungeecore.commands.reports.CheckReport;
import me.imelvin.bungeecore.commands.reports.CloseReport;
import me.imelvin.bungeecore.commands.reports.PurgeReports;
import me.imelvin.bungeecore.commands.reports.Report;
import me.imelvin.bungeecore.commands.reports.Reports;
import me.imelvin.bungeecore.commands.warnings.Delwarn;
import me.imelvin.bungeecore.commands.warnings.Warn;
import me.imelvin.bungeecore.commands.warnings.Warnings;
import me.imelvin.bungeecore.events.ChatEvents;
import me.imelvin.bungeecore.events.Disconnect;
import me.imelvin.bungeecore.events.PostLogin;
import me.imelvin.bungeecore.sql.MySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	public static ConfigManager c = ConfigManager.getInstance();
	public static MySQL sql;
	public static Plugin p;
	public static String hostname;
	public static int port;
	public static String db;
	public static String user;
	public static String pass;
	public static final String PREFIX = ChatColor.WHITE + "[" + ChatColor.GOLD + "Trilar" + ChatColor.WHITE + "]" + ChatColor.YELLOW;
	
	public void onEnable() {
		p = this;
		c.setup(p);
		init();
		if (stringCheck(hostname) && stringCheck(db) && stringCheck(user) && stringCheck(pass)) {
			sql = new MySQL(hostname, port, db, user, pass);
			if (!sql.hasConnection()) {
				sql.openConnection();
			}
			sql.createTable();
		} else {
			ProxyServer.getInstance().getLogger().info("Input data for database incorrect. Have you checked the format?");
		}
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Ban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Baninfo());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Banip());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Kick());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Mute());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Tempban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Unban());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Unbanip());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CheckReport());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new CloseReport());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new PurgeReports());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Report());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Reports());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Delwarn());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Warn());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Warnings());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Alert());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Maintenance());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Msg());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Reload());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Reply());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Socialspy());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Staffchat());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatEvents());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new Disconnect());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PostLogin());
	}
	
	public void onDisable() {
		if (sql.hasConnection()) {
			sql.closeConnection();
		}
	}
	
	public static void init() {
		hostname = c.getConfig().getString("settings.database.hostname");
		port = c.getConfig().getInt("settings.database.port");
		db = c.getConfig().getString("settings.database.database");
		user = c.getConfig().getString("settings.database.username");
		pass = c.getConfig().getString("settings.database.password");
	}

	private boolean stringCheck(String toCheck) {
		return (!toCheck.isEmpty() && !toCheck.isBlank());
	}
}
