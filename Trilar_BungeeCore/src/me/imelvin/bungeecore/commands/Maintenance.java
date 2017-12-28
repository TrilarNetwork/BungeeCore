package me.imelvin.bungeecore.commands;

import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Maintenance extends Command {
	public static boolean m = false;

	public Maintenance() {
		super("maintenance");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (m) {
			m = false;
			p.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have disabled Maintenance Mode!"));
		} else {
			m = true;
			p.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have enabled Maintenance Mode!"));
		}
	}
}
