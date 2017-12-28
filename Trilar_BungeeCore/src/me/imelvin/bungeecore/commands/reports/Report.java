package me.imelvin.bungeecore.commands.reports;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.ReportHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Report extends Command {
	
	public Report() {
		super("report");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
			if (args.length < 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /report <message>"));
			} else {
				String msg = "";
				for (int i = 0; i < args.length; i++) {
					msg += args[i] + " ";
				}
				msg = ChatColor.translateAlternateColorCodes('&', msg).trim();
				if (sender instanceof ProxiedPlayer) {
					ReportHandler.makeReport(sender.getName(), msg);
				} else {
					ReportHandler.makeReport("CONSOLE", msg);
				}
				sender.sendMessage(new TextComponent(Main.prefix + "You have successfully created a report ticket! Our Staff Team will take action as soon as possible."));
				Chat.msgAllOps(Main.prefix + "Player " + sender.getName() + " has created a report.");
			}
	}
}
