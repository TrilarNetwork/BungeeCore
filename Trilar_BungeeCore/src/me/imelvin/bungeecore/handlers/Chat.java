package me.imelvin.bungeecore.handlers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Chat {

	public static void msgAllOps(String str) {
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getPermissions().contains("trilar.staffchat") || p.getPermissions().contains("trilar.staffchat.*"))  {
				p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
				ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', str));
			}
		}
	}
	
	public static void msgAllHelpers(String str) {
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getPermissions().contains("trilar.helper") || p.getGroups().contains("helper") || p.getPermissions().contains("trilar.staffchat.*"))  {
				p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
				ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', str));
			}
		}
	}
	
	public static void msgAllMods(String str) {
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getPermissions().contains("trilar.moderator") || p.getGroups().contains("moderator") || p.getPermissions().contains("trilar.staffchat.*"))  {
				p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
				ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', str));
			}
		}
	}
	
	public static void msgAllAdmin(String str) {
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getPermissions().contains("trilar.admin") || p.getGroups().contains("admin") || p.getPermissions().contains("trilar.staffchat.*"))  {
				p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
				ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', str));
			}
		}
	}
	
	public static void msgAllOwners(String str) {
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			if (p.getPermissions().contains("trilar.owner") || p.getPermissions().contains("trilar.developer") 
					|| p.getGroups().contains("owner") || p.getGroups().contains("developer") || p.getPermissions().contains("trilar.staffchat.*"))  {
				p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', str)));
				ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', str));
			}
		}
	}
}
