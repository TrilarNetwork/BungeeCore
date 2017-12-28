package me.imelvin.bungeecore.commands;

import me.imelvin.bungeecore.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Alert extends Command {

	public Alert() {
		super("alert");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String msg = ChatColor.translateAlternateColorCodes('&', sb.toString()).trim();
			for (ProxiedPlayer ap : Main.p.getProxy().getPlayers()) {
				ap.sendMessage(new TextComponent(msg));
			}
	}
}
