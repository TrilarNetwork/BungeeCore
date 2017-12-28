package me.imelvin.bungeecore.commands.reports;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.Numbers;
import me.imelvin.bungeecore.handlers.ReportHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CloseReport extends Command {

	public CloseReport() {
		super("closereport");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length < 1 || args.length > 2) {
			sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /closereport <id> [-r]"));
		} else if (args.length == 1) {
			if (Numbers.isNumber(args[0])) {
				int key = Numbers.getInt(args[0]);
				if (ReportHandler.getStatus(key).equals("&cCLOSED")) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "That report has already been closed!"));
				} else {
					ReportHandler.closeReport(key);
					sender.sendMessage(new TextComponent(Main.prefix + "You have successfully closed report #" + key));
					Chat.msgAllOps(Main.prefix + "Player " + sender.getName() + " has closed report #" + key);
				}
			} else {
				sender.sendMessage(new TextComponent(ChatColor.RED + "You must enter a number! (id)"));
			}
		} else if (args.length == 2 && args[1].equalsIgnoreCase("-r")) {
			if (Numbers.isNumber(args[0])) {
				int key = Numbers.getInt(args[0]);
				if (ReportHandler.getStatus(key).equals("&cCLOSED")) {
					ReportHandler.openReport(key);
					sender.sendMessage(
							new TextComponent(Main.prefix + "You have successfully re-opened report #" + key));
					Chat.msgAllOps(Main.prefix + "Player " + sender.getName() + " has re-opened report #" + key);
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "That report has already been closed!"));
				}
			} else {
				sender.sendMessage(new TextComponent(ChatColor.RED + "You must enter a number! (id)"));
			}
		} else {
			sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /closereport <id> [-r]"));
		}
	}
}
