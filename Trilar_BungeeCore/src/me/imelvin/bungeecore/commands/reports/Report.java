package me.imelvin.bungeecore.commands.reports;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.ReportHandler;
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
			if (args.length < 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /report <message>"));
			} else {
				StringBuilder msg = new StringBuilder();
				for (String arg : args) {
					msg.append(arg).append(" ");
				}
				msg = new StringBuilder(ChatColor.translateAlternateColorCodes('&', msg.toString()).trim());
				if (sender instanceof ProxiedPlayer) {
					ReportHandler.makeReport(sender.getName(), msg.toString());
				} else {
					ReportHandler.makeReport("CONSOLE", msg.toString());
				}
				sender.sendMessage(new TextComponent(Main.PREFIX + "You have successfully created a report ticket! Our Staff Team will take action as soon as possible."));
				Chat.msgAllOps(Main.PREFIX + "Player " + sender.getName() + " has created a report.");
			}
	}
}
