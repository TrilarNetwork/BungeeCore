package me.imelvin.bungeecore.events;

import me.imelvin.bungeecore.commands.Staffchat;
import me.imelvin.bungeecore.handlers.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvents implements Listener {
	private final String STAFF = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Staff-Chat" + ChatColor.DARK_AQUA + "] " + ChatColor.AQUA;
	private final String HELPER = ChatColor.GOLD + "[" + ChatColor.YELLOW + "Helper Staff-Chat" + ChatColor.GOLD + "] " + ChatColor.YELLOW;
	private final String MOD = ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Moderator Staff-Chat" + ChatColor.DARK_BLUE + "] " + ChatColor.BLUE;
	private final String ADMIN = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Admin Staff-Chat" + ChatColor.DARK_AQUA + "] " + ChatColor.AQUA;
	private final String OWNER = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Owner/Developer Staff-Chat" + ChatColor.DARK_AQUA + "] " + ChatColor.AQUA;

	@EventHandler
	public void onChat(ChatEvent e) {
		if (e.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			if (Staffchat.global.contains(p.getName()) || e.getMessage().startsWith("!")) {
				Chat.msgAllOps(STAFF + "[" + p.getServer().getInfo().getName() + "] " + p.getName() + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			} else if (Staffchat.helpers.contains(p.getName())) {
				Chat.msgAllHelpers(HELPER + "[" + p.getServer().getInfo().getName() + "] " + p.getName() + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			} else if (Staffchat.moderator.contains(p.getName())) {
				Chat.msgAllMods(MOD + "[" + p.getServer().getInfo().getName() + "] " + p.getName() + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			} else if (Staffchat.admins.contains(p.getName())) {
				Chat.msgAllAdmin(ADMIN + "[" + p.getServer().getInfo().getName() + "] " + p.getName() + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			} else if (Staffchat.owners.contains(p.getName())) {
				Chat.msgAllOwners(OWNER + "[" + p.getServer().getInfo().getName() + "] " + p.getName() + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			}
		}
	}
}
